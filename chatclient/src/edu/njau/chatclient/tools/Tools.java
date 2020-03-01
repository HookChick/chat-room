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
	
	public final static String Login_success_msg ="success"; //�ͻ��˺ͷ������涨��Э���ʾ�����ȷ��¼�ɹ�
	public final static String Login_nameNotFound ="failedByName"; //��ʾ�û��������ڵ�¼ʧ��
	public final static String Login_pwdError ="failedByPwd"; //��ʾ��������¼ʧ��
	public final static String Login_repeat ="failed"; //��ʾͬһ���˺��ظ���¼��½ʧ��
	
	//��ǰ�û��ĺ��ѽ����࣬����ʵ�ָ��û��͵�½�Ľ�������֣����Ǹ��û�������
	public static Map<String,MainFrameList> frameListMap = new HashMap<String,MainFrameList>();
	
	public static List<User> users = new ArrayList<User>(); //ʵʱ�������˷������û��б���Ϣ�������ŷ����ʵʱ����
	
	//�ͻ��˺ͷ������˱���ͨѶ���̵߳ļ��ϣ���ΪUser����
	public static Map<User,ClientConServerThread> clientConServerThreadMap = Collections.synchronizedMap(new HashMap<User, ClientConServerThread>());
	
	//���浱ǰ�û��򿪵��������
	public static Map<String,ChatFrame> chatFrameMap = new HashMap<String, ChatFrame>();
	
	////��Ϣ����Ϊ�ܾ��Է�����������
	public static final String msg_refuse_message = "refuse_message";
	
	//��Ϣ���ͱ�ʾ��ǰ�û����ߵ�֪ͨ
	public final static String msg_offLine_message = "offLine_message";
	
	//����Ϣ���ͱ�ʾ֪ͨ�û������û��б�
	public static final String msg_update_OnLineFriend="update_OnLineFriend";
	
	//��Ϣ���ͱ�ʾ��ϢΪ��ͨ��Ϣ������
	public static final String msg_common_message="common_message";
	
	//������Ϣ�ĶԷ��û������ߵ���Ϣ���ͣ���ʱ�ɷ�����ת���ظ����ͷ��Լ�
	public static final String msg_friendNotInLine_message = "friendNotInLine_message";
	
	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	public static int[] getLocation(int width, int height) { // ��ȡ��ǰ���ڵ�λ��
		int location[] =new int[2];
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		location[0] = (dimension.width - width)/2;
		location[1] = (dimension.height - height)/2;
		return location;
	}
}
