package edu.njau.chatclient.biz;

import edu.njau.common.User;
//�û���ҵ����
public class ClientUser {

	public String checkLogin(User user) {
		return new ClientConServer().sendInfoToServer(user);
	}


}
