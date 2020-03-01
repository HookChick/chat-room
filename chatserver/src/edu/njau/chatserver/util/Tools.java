package edu.njau.chatserver.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import edu.njau.chatserver.ui.MainFrame;
import edu.njau.common.User;

public class Tools {
	
	public final static String Login_success_msg ="success"; //客户端和服务器规定的协议表示身份正确登录成功
	public final static String Login_nameNotFound ="failedByName"; //表示用户名不存在登录失败
	public final static String Login_pwdError ="failedByPwd"; //表示密码错误登录失败
	public final static String Login_repeat ="failed"; //表示同一个账号重复登录登陆失败
	
	//服务器和客户端保持通讯的线程的集合，键为User对象
	public static Map<User,ServerConClientThread> serverConClientThreadMap = Collections.synchronizedMap(new HashMap<User, ServerConClientThread>());
	
	//保存在线用户的列表，只要有一个用户成功登录就加入该列表
	public static List<User> userList = Collections.synchronizedList(new ArrayList<User>());
	
	//发送消息的对方用户不在线的消息类型，此时由服务器转发回给发送方自己
	public static final String msg_friendNotInLine_message = "friendNotInLine_message";
	
	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//此消息类型表示通知用户更新用户列表
	public final static String msg_update_OnLineFriend="update_OnLineFriend";
	//消息类型表示消息为普通消息的类型
	public final static String msg_common_message="common_message";
	
	//消息类型表示当前用户下线的通知
	public final static String msg_offLine_message = "offLine_message";
	
	// 程序启动就只有一个MainFrame服务器的对象
	public static List<MainFrame> mainFrameList = new ArrayList<MainFrame>();
	
	//此消息控制当用户发送消息的对方不在线时回复给该客户端的消息内容
	public static final String message_call_back ="对方暂时不在线或隐身,请稍后联系。。。";
	
	//消息类型为拒绝对方的聊天请求
	public static final String msg_refuse_message = "refuse_message";
	
	public static Logger getLogger(){ //得到日志源
		return Logger.getLogger(Tools.class);
	}
}
