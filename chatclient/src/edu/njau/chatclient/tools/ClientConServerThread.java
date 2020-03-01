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

//�ͻ��˺ͷ������˱���ͨѶ���߳�
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
			//���ϵض�ȡ�ӷ�������������Ϣ
			try {
				ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
				Message msg=(Message)ois.readObject();
				if(Tools.msg_friendNotInLine_message.equals(msg.getMessageType())){//������Ϣʱ�Է��û������ߣ��ɷ������Զ��ظ���
					ChatFrame chatFrame = Tools.chatFrameMap.get(msg.getSendName()+" "+msg.getReceiveName());
					chatFrame.showMessageBack(msg);
				}
				else if(Tools.msg_common_message.equals(msg.getMessageType())){//��ͨ��Ϣ����
					ChatFrame chatFrame = Tools.chatFrameMap.get(msg.getReceiveName()+" "+msg.getSendName());
					if(null == chatFrame){ //�Է�û�п����뷢����Ϣ�����������
						MainFrameList mainList = Tools.frameListMap.get(msg.getReceiveName());
						int option = mainList.showConfirmMessage(mainList, msg);
						if(option == JOptionPane.YES_OPTION){
							// �򿪺͸ú�������Ľ���
							ChatFrame chat = new ChatFrame(msg.getReceiveName(), msg.getSendName());
							chat.showMessage(msg);
							// �����������뵽�������������༯��
							Tools.chatFrameMap.put(msg.getReceiveName() + " " + msg.getSendName(),
									chat);
						}else{//�ܾ�����
							msg.setMessageType(Tools.msg_refuse_message);//��Ϣ����Ϊ�ܾ�
							msg.setContext(msg.getReceiveName()+"�ܾ���������");
							ObjectOutput objectOutput = new ObjectOutputStream(Tools.clientConServerThreadMap.get(this.getUserByOwnName(msg.getReceiveName())).getSocket().getOutputStream());
							objectOutput.writeObject(msg);
						}
					}else{
						chatFrame.showMessage(msg);
					}
				}else if(Tools.msg_update_OnLineFriend.equals(msg.getMessageType())){
					//�����û��б����Ϣ
					String receiverName = msg.getReceiveName();
					//�޸���Ӧ�ĺ����б�.
					MainFrameList mainFrameList=Tools.frameListMap.get(receiverName);
					if(null != mainFrameList)
					{
						mainFrameList.upateFriend(msg);
					}
				}else if(Tools.msg_refuse_message.equals(msg.getMessageType())){//�Է��ܾ�������Ϣ����
					ChatFrame chatFrame = Tools.chatFrameMap.get(msg.getSendName()+" "+msg.getReceiveName());
					chatFrame.showMessageBack(msg);
				}
						
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
			}
		}
	}

	private Object getUserByOwnName(String receiveName) { //�������ֻ�ö�Ӧ��User����
			for(User user : Tools.clientConServerThreadMap.keySet()){
				if(user.getUname().equals(receiveName)){
					return user;
				}
			}
			return null;
	}

}
