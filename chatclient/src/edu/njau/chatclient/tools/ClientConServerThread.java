package edu.njau.chatclient.tools;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import edu.njau.chatclient.ui.ChatFrame;
import edu.njau.chatclient.ui.MainFrameList;
import edu.njau.common.Message;
import edu.njau.common.User;

//客户端和服务器端保持通讯的线程
public class ClientConServerThread extends Thread{
	private Socket socket;

	public Socket getSocket() {
		return socket;
	}

	public ClientConServerThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		while(true){
			//不断地读取从服务器发来的消息
			try {
				ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
				Message msg=(Message)ois.readObject();
				if(Tools.msg_friendNotInLine_message.equals(msg.getMessageType())){//发送消息时对方用户不再线，由服务器自动回复的
					ChatFrame chatFrame = Tools.chatFrameMap.get(msg.getSendName()+" "+msg.getReceiveName());
					chatFrame.showMessageBack(msg);
				}
				else if(Tools.msg_common_message.equals(msg.getMessageType())){//普通消息类型
					ChatFrame chatFrame = Tools.chatFrameMap.get(msg.getReceiveName()+" "+msg.getSendName());
					if(null == chatFrame){ //对方没有开启与发送消息方的聊天界面
						MainFrameList mainList = Tools.frameListMap.get(msg.getReceiveName());
						int option = mainList.showConfirmMessage(mainList, msg);
						if(option == JOptionPane.YES_OPTION){
							// 打开和该好友聊天的界面
							ChatFrame chat = new ChatFrame(msg.getReceiveName(), msg.getSendName());
							chat.showMessage(msg);
							// 把聊天界面加入到管理聊天界面的类集合
							Tools.chatFrameMap.put(msg.getReceiveName() + " " + msg.getSendName(),
									chat);
						}else{//拒绝聊天
							msg.setMessageType(Tools.msg_refuse_message);//消息类型为拒绝
							msg.setContext(msg.getReceiveName()+"拒绝和你聊天");
							ObjectOutput objectOutput = new ObjectOutputStream(Tools.clientConServerThreadMap.get(this.getUserByOwnName(msg.getReceiveName())).getSocket().getOutputStream());
							objectOutput.writeObject(msg);
						}
					}else{
						chatFrame.showMessage(msg);
					}
				}else if(Tools.msg_update_OnLineFriend.equals(msg.getMessageType())){
					//更改用户列表的消息
					String receiverName = msg.getReceiveName();
					//修改相应的好友列表.
					MainFrameList mainFrameList=Tools.frameListMap.get(receiverName);
					if(null != mainFrameList)
					{
						mainFrameList.upateFriend(msg);
					}
				}else if(Tools.msg_refuse_message.equals(msg.getMessageType())){//对方拒绝聊天消息类型
					ChatFrame chatFrame = Tools.chatFrameMap.get(msg.getSendName()+" "+msg.getReceiveName());
					chatFrame.showMessageBack(msg);
				}
						
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
			}
		}
	}

	private Object getUserByOwnName(String receiveName) { //根据名字获得对应的User对象
			for(User user : Tools.clientConServerThreadMap.keySet()){
				if(user.getUname().equals(receiveName)){
					return user;
				}
			}
			return null;
	}

}
