package edu.njau.chatserver.dao;

import java.util.List;

import edu.njau.common.User;

public interface ServerUserDAO {
	
	User getUser();

	String getInfo(User user); //获取登陆成功与否，不成功是名字不存在，还是密码错误
	
	List<User> getAll();
}
