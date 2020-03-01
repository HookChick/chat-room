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
			//���߳̾Ͳ��Ͻ��տͻ��˵���Ϣ.
			try {
				ObjectInput obinput = new ObjectInputStream(socket.getInputStream());
				Message message = (Message)obinput.readObject();
				if(Tools.msg_common_message.equals(message.getMessageType())){//��ͨ��Ϣ���ͣ��ͽ���ת��
					Tools.mainFrameList.get(0).showMessage(message);
					ServerConClientThread serverConClient = null;
					for(User u:Tools.serverConClientThreadMap.keySet()){
						if(u.getUname().equals(message.getReceiveName())){ //ȡ�õ�ǰ�����˵��߳����ת������
							serverConClient = Tools.serverConClientThreadMap.get(u);
							break;
						}
					}
					ObjectOutput objectOutput;
					if(serverConClient == null){ //Ԥ������Ϣ�Ŀͻ�������
						Tools.getLogger().info(message.getSendName()+"��"+message.getReceiveName()+"������һ����Ϣ,��"+message.getReceiveName()+"������");
						objectOutput = new ObjectOutputStream(socket.getOutputStream());
						message.setContext(Tools.message_call_back);
						message.setMessageType(Tools.msg_friendNotInLine_message);
					}else{ //����ת�����ÿͻ�
						Tools.getLogger().info("�ɹ�ת����"+message.getSendName()+"��"+message.getReceiveName()+"���ݣ�"+message.getContext());
						message.setMessageType(Tools.msg_common_message);
						ParseXml parseXML = new ParseXml();
						String content = parseXML.pasrse();
						if(content.contains(message.getContext())){ //������Ϣ
							message.setContext("���Ʋ�����Ϣ,������������*****...");
						}
						objectOutput = new ObjectOutputStream(serverConClient.socket.getOutputStream());
					}
					objectOutput.writeObject(message);
				}else if(Tools.msg_offLine_message.equals(message.getMessageType())){
					//�û����ߵ�֪ͨ
					String offineLineName = message.getSendName();
					Tools.getLogger().info(offineLineName+"������");
					User user = null;
					for(User u:Tools.serverConClientThreadMap.keySet()){ //ͨ�������ҵ����û�
						if(offineLineName.equals(u.getUname())){
							user =u;
						}
					}
					for(User u:Tools.userList){ //��Tools.userList�ĸ��û�����Ϊ����
						if(u.equals(user)){
							u.setOnLine(false);
						}
					}
					ServerConClientThread serverConClientThread =Tools.serverConClientThreadMap.get(user);
					Tools.serverConClientThreadMap.remove(user);
					user.setOnLine(false);
					//֪ͨÿһλ�����û�
					if(Tools.serverConClientThreadMap.size()>0){//�������һ���˳���ϵͳ
						serverConClientThread.notityOther(user); 
					}
					serverConClientThread.stop();//֮�������Լ�
				}else if(Tools.msg_refuse_message.equals(message.getMessageType())){
					Tools.getLogger().info(message.getReceiveName()+"�ܾ���"+message.getSendName()+"����������");
					ServerConClientThread serverConClient = null;
					for(User u:Tools.serverConClientThreadMap.keySet()){
						if(u.getUname().equals(message.getSendName())){ //ȡ�õ�ǰ�����˵��߳����ת������
							serverConClient = Tools.serverConClientThreadMap.get(u);
							break;
						}
					}
					ObjectOutput objectOutput = new ObjectOutputStream(serverConClient.socket.getOutputStream());
					objectOutput.writeObject(message);
				}
				
			} catch (IOException e) {
				Tools.getLogger().warn("���̺߳Ϳͻ��˵����ӱ�����", e);
				return;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		}
	}

	public void notityOther(User user) {//֪ͨ���������û�
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
