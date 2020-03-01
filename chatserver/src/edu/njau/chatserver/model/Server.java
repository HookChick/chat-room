package edu.njau.chatserver.model;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import edu.njau.chatserver.dao.ServerUserDAO;
import edu.njau.chatserver.dao.impl.ServerUserDAOImpl;
import edu.njau.chatserver.util.ServerConClientThread;
import edu.njau.chatserver.util.Tools;
import edu.njau.common.Message;
import edu.njau.common.User;

public class Server {
	//�����������ڼ������ȴ�ĳ���ͻ���������
	public Server() {
		ServerSocket serverSocket;
		try {
			System.out.println("����8200�˿ڼ�����");
			serverSocket = new ServerSocket(8200);
			while(true){
				Socket socket = serverSocket.accept();//�ȴ��ͻ�������
				ObjectInput objectInput = new ObjectInputStream(socket.getInputStream());
				User user = (User)objectInput.readObject();
				Tools.getLogger().info("�յ��û�"+user.getUname()+"�ĵ�¼����");
				Message message = new Message();
				ObjectOutput objectOutput = new ObjectOutputStream(socket.getOutputStream());
				for(User currentu:Tools.userList){ 
					if(user.getUname().equals(currentu.getUname())&&currentu.isOnLine()){
						message.setMessageType(Tools.Login_repeat); //�ظ���¼ֱ�ӷ��أ�Ҳ����ȥ���ݿ���֤
						objectOutput.writeObject(message);
						break;
					}
				}
				ServerUserDAO serverUserDAO = new ServerUserDAOImpl();
				String msgType = serverUserDAO.getInfo(user);
				message.setMessageType(msgType);
				if(Tools.Login_success_msg.equals(message.getMessageType())){
					Tools.getLogger().info(user.getUname()+"������");
					//��֤��¼�ɹ����͵���һ���̣߳��ø��߳���ÿͻ��˱���ͨѶ.
					//��Ҫ�Ѹ��û���½�ɹ�����Ϣ���͵�ÿһ�������û��Ŀͻ��ˣ���ÿһ���ͻ��˸��������б�
					message.setUsers(Tools.userList); //���û��б���Ϣ���͸��ÿͻ���
					objectOutput.writeObject(message);
					user = serverUserDAO.getUser();
					ServerConClientThread serverConClientThread=new ServerConClientThread(socket);
					for(User u:Tools.userList){
						if(u.equals(user)){
							u.setOnLine(true);
						}
					}
					user.setOnLine(true);
					Tools.serverConClientThreadMap.put(user, serverConClientThread); 
					serverConClientThread.start();
					
					Tools.mainFrameList.get(0).upateUserList(user);//�����������û��б�
					//ÿλ�û����߾�֪ͨ�������ߵ��û�
					if(Tools.serverConClientThreadMap.size()>1){//���ǵ�һ����¼��ϵͳ
						serverConClientThread.notityOther(user); 
					}
				}else{
					Tools.getLogger().error("�û�"+user.getUname()+"��¼ʧ��");
					objectOutput.writeObject(message);
					socket.close();
				}
				
			}
		} catch (IOException e) {
			Tools.getLogger().fatal("�����˿�ʧ��",e);
			//�����˿�ʧ��
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	
}
