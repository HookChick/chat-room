package edu.njau.chatserver.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.njau.chatserver.dao.BaseDAO;
import edu.njau.chatserver.dao.ServerUserDAO;
import edu.njau.chatserver.util.Tools;
import edu.njau.common.User;

public class ServerUserDAOImpl extends BaseDAO implements ServerUserDAO {

	private User user;
	
	public User getUser() {
		return this.user;
	}

	public String getInfo(User u) {
		User tempUser = null;
		Connection con = this.getConnection();
		ResultSet res =null;
		Statement st = null;
		try {
			st = con.createStatement();
			res = st.executeQuery("select * from users where uname='"+u.getUname()+"'");
			if(res.next()){
				tempUser = new User(res.getInt(1),res.getString(2),res.getString(3),res.getString(4));
			}else{
				return Tools.Login_nameNotFound;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll(con, st, res);
		}
		if(tempUser.getPwd().equals(u.getPwd())){
			this.user = tempUser;
			return Tools.Login_success_msg;
		}else{
			return Tools.Login_pwdError;
		}
	}
	
	public List<User> getAll(){
		List<User> users = new ArrayList<User>();
		Connection con = this.getConnection();
		Statement st =null;
		ResultSet res =null;
		try {
			st = con.createStatement();
			res = st.executeQuery("select * from users");
			while(res.next()){
				users.add(new User(res.getInt(1),res.getString(2),res.getString(3),res.getString(4)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll(con, st, res);
		}
		return users;
	}
	
	private void closeAll(Connection con,Statement st,ResultSet res){
		try {
			if(res!=null){
				res.close();
			}
			if(st!=null){
				st.close();
			}
			if(con != null){
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
