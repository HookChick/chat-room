package edu.njau.common;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable{
	private static final long serialVersionUID = 1395799595886611298L;
	
	private int sendId;//������id
	private int receiveId;//������id
	private String sendName; //����������
	private String receiveName; //����������
	private String context; //��Ϣ����
	private String sendTime; //��Ϣ����ʱ��
	private String messageType; //��Ϣ����
	private List<User> users; //���ܷ���˴������û��б�
	
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public int getSendId() {
		return sendId;
	}
	public void setSendId(int sendId) {
		this.sendId = sendId;
	}
	public int getReceiveId() {
		return receiveId;
	}
	public void setReceiveId(int receiveId) {
		this.receiveId = receiveId;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public Message() {
		super();
	}
	public Message(int sendId, int receiveId, String context,
			String sendTime, String messageType, List<User> users) {
		super();
		this.sendId = sendId;
		this.receiveId = receiveId;
		this.context = context;
		this.sendTime = sendTime;
		this.messageType = messageType;
		this.users = users;
	}
	public Message(String sendName, String receiveName, String context,
			String sendTime, String messageType, List<User> users) {
		super();
		this.sendName = sendName;
		this.receiveName = receiveName;
		this.context = context;
		this.sendTime = sendTime;
		this.messageType = messageType;
		this.users = users;
	}
	
	
}
