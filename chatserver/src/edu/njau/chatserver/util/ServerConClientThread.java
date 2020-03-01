package edu.njau.chatserver.util;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

import edu.njau.chatserver.model.ParseXml;
import edu.njau.common.Message;
import edu.njau.common.User;

public class ServerConClientThread extends Thread {
	private Socket socket;
	
	public ServerConClientThread(Socket socket) {
		this.socket = socket;
	}
	
	public Socket getSocket() {
		return socket;
	}

	@Override
	public void run() {
		while(true){
			//该线程就不断接收客户端的信息.
			try {
				ObjectInput obinput = new ObjectInputStream(socket.getInputStream());
				Message message = (Message)obinput.readObject();
				if(Tools.msg_common_message.equals(message.getMessageType())){//普通消息类型，就进行转发
					Tools.mainFrameList.get(0).showMessage(message);
					ServerConClientThread serverConClient = null;
					for(User u:Tools.serverConClientThreadMap.keySet()){
						if(u.getUname().equals(message.getReceiveName())){ //取得当前接收人的线程完成转发过程
							serverConClient = Tools.serverConClientThreadMap.get(u);
							break;
						}
					}
					ObjectOutput objectOutput;
					if(serverConClient == null){ //预发送消息的客户不在线
						Tools.getLogger().info(message.getSendName()+"给"+message.getReceiveName()+"发送了一条消息,但"+message.getReceiveName()+"不在线");
						objectOutput = new ObjectOutputStream(socket.getOutputStream());
						message.setContext(Tools.message_call_back);
						message.setMessageType(Tools.msg_friendNotInLine_message);
					}else{ //在线转发给该客户
						Tools.getLogger().info("成功转发："+message.getSendName()+"给"+message.getReceiveName()+"内容："+message.getContext());
						message.setMessageType(Tools.msg_common_message);
						ParseXml parseXML = new ParseXml();
						String content = parseXML.pasrse();
						if(content.contains(message.getContext())){ //屏蔽消息
							message.setContext("疑似不良消息,被服务器屏蔽*****...");
						}
						objectOutput = new ObjectOutputStream(serverConClient.socket.getOutputStream());
					}
					objectOutput.writeObject(message);
				}else if(Tools.msg_offLine_message.equals(message.getMessageType())){
					//用户下线的通知
					String offineLineName = message.getSendName();
					Tools.getLogger().info(offineLineName+"下线了");
					User user = null;
					for(User u:Tools.serverConClientThreadMap.keySet()){ //通过名字找到该用户
						if(offineLineName.equals(u.getUname())){
							user =u;
						}
					}
					for(User u:Tools.userList){ //把Tools.userList的该用户设置为下线
						if(u.equals(user)){
							u.setOnLine(false);
						}
					}
					ServerConClientThread serverConClientThread =Tools.serverConClientThreadMap.get(user);
					Tools.serverConClientThreadMap.remove(user);
					user.setOnLine(false);
					//通知每一位在线用户
					if(Tools.serverConClientThreadMap.size()>0){//不是最后一个退出该系统
						serverConClientThread.notityOther(user); 
					}
					serverConClientThread.stop();//之后销毁自己
				}else if(Tools.msg_refuse_message.equals(message.getMessageType())){
					Tools.getLogger().info(message.getReceiveName()+"拒绝了"+message.getSendName()+"的聊天请求");
					ServerConClientThread serverConClient = null;
					for(User u:Tools.serverConClientThreadMap.keySet()){
						if(u.getUname().equals(message.getSendName())){ //取得当前接收人的线程完成转发过程
							serverConClient = Tools.serverConClientThreadMap.get(u);
							break;
						}
					}
					ObjectOutput objectOutput = new ObjectOutputStream(serverConClient.socket.getOutputStream());
					objectOutput.writeObject(message);
				}
				
			} catch (IOException e) {
				Tools.getLogger().warn("该线程和客户端的连接被重置", e);
				return;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		}
	}

	public void notityOther(User user) {//通知其他在线用户
		for(User u:Tools.serverConClientThreadMap.keySet()){
			if(!u.equals(user)){
				Message msg = new Message();
				msg.setMessageType(Tools.msg_update_OnLineFriend);
				msg.setReceiveName(u.getUname());
				msg.setUsers(Arrays.asList(user));
				try {
					ObjectOutput objectOutput = new ObjectOutputStream(Tools.serverConClientThreadMap.get(u).socket.getOutputStream());
					objectOutput.writeObject(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
