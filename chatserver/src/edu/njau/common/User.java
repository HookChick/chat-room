package edu.njau.common;

import java.io.Serializable;

public class User implements Serializable{
	private static final long serialVersionUID = 2914888205801877880L;
	
	private int id;
	private String uname;
	private String pwd;
	private String sex;
	private boolean isOnLine; //在线与否的标志
	
	public boolean isOnLine() {
		return isOnLine;
	}
	public void setOnLine(boolean isOnLine) {
		this.isOnLine = isOnLine;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public User() {
		super();
	}
	public User(String uname, String pwd) {
		super();
		this.uname = uname;
		this.pwd = pwd;
	}
	public User(int id, String uname, String pwd, String sex) {
		super();
		this.id = id;
		this.uname = uname;
		this.pwd = pwd;
		this.sex = sex;
	}
	public User(int id, String uname, String pwd, String sex, boolean isOnLine) {
		super();
		this.id = id;
		this.uname = uname;
		this.pwd = pwd;
		this.sex = sex;
		this.isOnLine = isOnLine;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", uname=" + uname + ", pwd=" + pwd
				+ ", sex=" + sex + ", isOnLine=" + isOnLine + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof User){
			User u = (User)obj;
			if(u.getUname().equals(this.uname)&&u.getId()==this.id&&u.getPwd().equals(this.pwd)&&u.getSex().equals(this.sex)&&u.isOnLine == this.isOnLine){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	
}
