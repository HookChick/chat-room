package edu.njau.chatclient.biz;

import edu.njau.common.User;
//用户的业务类
public class ClientUser {

	public String checkLogin(User user) {
		return new ClientConServer().sendInfoToServer(user);
	}


}
