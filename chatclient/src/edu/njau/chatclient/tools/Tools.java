package edu.njau.chatclient.tools;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.njau.chatclient.ui.ChatFrame;
import edu.njau.chatclient.ui.MainFrameList;
import edu.njau.common.User;

public class Tools {
	
	public final static String Login_success_msg ="success"; //客户端和服务器规定的协议表示身份正确登录成功
	public final static String Login_nameNotFound ="failedByName"; //表示用户名不存在登录失败
	public final static String Login_pwdError ="failedByPwd"; //表示密码错误登录失败
	public final static String Login_repeat ="failed"; //表示同一个账号重复登录登陆失败
	
	//当前用户的好友界面类，便于实现该用户和登陆的界面的区分，键是该用户的姓名
	public static Map<String,MainFrameList> frameListMap = new HashMap<String,MainFrameList>();
	
	public static List<User> users = new ArrayList<User>(); //实时保存服务端发来的用户列表信息，并随着服务端实时更新
	
	//客户端和服务器端保持通讯的线程的集合，键为User对象
	public static Map<User,ClientConServerThread> clientConServerThreadMap = Collections.synchronizedMap(new HashMap<User, ClientConServerThread>());
	
	//保存当前用户打开的聊天界面
	public static Map<String,ChatFrame> chatFrameMap = new HashMap<String, ChatFrame>();
	
	////消息类型为拒绝对方的聊天请求
	public static final String msg_refuse_message = "refuse_message";
	
	//消息类型表示当前用户下线的通知
	public final static String msg_offLine_message = "offLine_message";
	
	//此消息类型表示通知用户更新用户列表
	public static final String msg_update_OnLineFriend="update_OnLineFriend";
	
	//消息类型表示消息为普通消息的类型
	public static final String msg_common_message="common_message";
	
	//发送消息的对方用户不在线的消息类型，此时由服务器转发回给发送方自己
	public static final String msg_friendNotInLine_message = "friendNotInLine_message";
	
	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	public static int[] getLocation(int width, int height) { // 获取当前窗口的位置
		int location[] =new int[2];
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		location[0] = (dimension.width - width)/2;
		location[1] = (dimension.height - height)/2;
		return location;
	}
}
