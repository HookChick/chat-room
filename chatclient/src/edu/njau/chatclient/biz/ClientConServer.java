package edu.njau.chatclient.biz;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import edu.njau.chatclient.tools.ClientConServerThread;
import edu.njau.chatclient.tools.Tools;
import edu.njau.common.Message;
import edu.njau.common.User;

public class ClientConServer {

	private Socket socket;
	public String sendInfoToServer(User user) {
		try {
			socket = new Socket("172.31.36.69",8200);
			ObjectOutput objectOutput = new ObjectOutputStream(socket.getOutputStream());
			objectOutput.writeObject(user);
			
			ObjectInput objectInput = new ObjectInputStream(socket.getInputStream());
			Message msg = (Message)objectInput.readObject();
			if(msg == null){
				return null;
			}
			//�� msg ���ص���Ϣ�������жϵ�¼�����
			if(msg.getMessageType().equals(Tools.Login_success_msg)){
				//��½�ɹ�,�ʹ���һ�����˺źͷ������˱���ͨѶ���ӵ��߳�
				Tools.users = msg.getUsers(); //��ȡ���������ص��û��б����
				ClientConServerThread clientConServerThread=new ClientConServerThread(socket);
				clientConServerThread.start();
				//�ѵ�¼�ɹ��ĸ��û��Ŀͻ������ӷ��������̼߳��뼯����
				Tools.clientConServerThreadMap.put((User)user,clientConServerThread);
				return Tools.Login_success_msg;
			}
			socket.close();
			if(msg.getMessageType().equals(Tools.Login_nameNotFound)){
				return Tools.Login_nameNotFound;
			}
			if(msg.getMessageType().equals(Tools.Login_pwdError)){
				return Tools.Login_pwdError;
			}
			if(msg.getMessageType().equals(Tools.Login_repeat)){
				return Tools.Login_repeat;
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			return "ServerNotUP";
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
