package edu.njau.chatserver.dao;

import java.util.List;

import edu.njau.common.User;

public interface ServerUserDAO {
	
	User getUser();

	String getInfo(User user); //��ȡ��½�ɹ���񣬲��ɹ������ֲ����ڣ������������
	
	List<User> getAll();
}
