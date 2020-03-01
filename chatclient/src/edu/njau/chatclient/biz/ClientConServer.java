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
			//用 msg 返回的消息类型来判断登录的情况
			if(msg.getMessageType().equals(Tools.Login_success_msg)){
				//登陆成功,就创建一个该账号和服务器端保持通讯连接的线程
				Tools.users = msg.getUsers(); //获取服务器返回的用户列表情况
				ClientConServerThread clientConServerThread=new ClientConServerThread(socket);
				clientConServerThread.start();
				//把登录成功的该用户的客户端链接服务器的线程加入集合中
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
