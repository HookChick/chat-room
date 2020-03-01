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
	//服务器，它在监听，等待某个客户端来连接
	public Server() {
		ServerSocket serverSocket;
		try {
			System.out.println("我在8200端口监听：");
			serverSocket = new ServerSocket(8200);
			while(true){
				Socket socket = serverSocket.accept();//等待客户端连接
				ObjectInput objectInput = new ObjectInputStream(socket.getInputStream());
				User user = (User)objectInput.readObject();
				Tools.getLogger().info("收到用户"+user.getUname()+"的登录请求");
				Message message = new Message();
				ObjectOutput objectOutput = new ObjectOutputStream(socket.getOutputStream());
				for(User currentu:Tools.userList){ 
					if(user.getUname().equals(currentu.getUname())&&currentu.isOnLine()){
						message.setMessageType(Tools.Login_repeat); //重复登录直接返回，也不用去数据库验证
						objectOutput.writeObject(message);
						break;
					}
				}
				ServerUserDAO serverUserDAO = new ServerUserDAOImpl();
				String msgType = serverUserDAO.getInfo(user);
				message.setMessageType(msgType);
				if(Tools.Login_success_msg.equals(message.getMessageType())){
					Tools.getLogger().info(user.getUname()+"上线了");
					//验证登录成功，就单开一个线程，让该线程与该客户端保持通讯.
					//还要把该用户登陆成功的消息发送到每一个在线用户的客户端，让每一个客户端更新朋友列表
					message.setUsers(Tools.userList); //把用户列表信息发送给该客户端
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
					
					Tools.mainFrameList.get(0).upateUserList(user);//服务器更新用户列表
					//每位用户上线就通知其他在线的用户
					if(Tools.serverConClientThreadMap.size()>1){//不是第一个登录该系统
						serverConClientThread.notityOther(user); 
					}
				}else{
					Tools.getLogger().error("用户"+user.getUname()+"登录失败");
					objectOutput.writeObject(message);
					socket.close();
				}
				
			}
		} catch (IOException e) {
			Tools.getLogger().fatal("创建端口失败",e);
			//创建端口失败
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	
}
