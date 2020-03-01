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
	
	public final static String Login_success_msg ="success"; //�ͻ��˺ͷ������涨��Э���ʾ�����ȷ��¼�ɹ�
	public final static String Login_nameNotFound ="failedByName"; //��ʾ�û��������ڵ�¼ʧ��
	public final static String Login_pwdError ="failedByPwd"; //��ʾ��������¼ʧ��
	public final static String Login_repeat ="failed"; //��ʾͬһ���˺��ظ���¼��½ʧ��
	
	//�������Ϳͻ��˱���ͨѶ���̵߳ļ��ϣ���ΪUser����
	public static Map<User,ServerConClientThread> serverConClientThreadMap = Collections.synchronizedMap(new HashMap<User, ServerConClientThread>());
	
	//���������û����б�ֻҪ��һ���û��ɹ���¼�ͼ�����б�
	public static List<User> userList = Collections.synchronizedList(new ArrayList<User>());
	
	//������Ϣ�ĶԷ��û������ߵ���Ϣ���ͣ���ʱ�ɷ�����ת���ظ����ͷ��Լ�
	public static final String msg_friendNotInLine_message = "friendNotInLine_message";
	
	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//����Ϣ���ͱ�ʾ֪ͨ�û������û��б�
	public final static String msg_update_OnLineFriend="update_OnLineFriend";
	//��Ϣ���ͱ�ʾ��ϢΪ��ͨ��Ϣ������
	public final static String msg_common_message="common_message";
	
	//��Ϣ���ͱ�ʾ��ǰ�û����ߵ�֪ͨ
	public final static String msg_offLine_message = "offLine_message";
	
	// ����������ֻ��һ��MainFrame�������Ķ���
	public static List<MainFrame> mainFrameList = new ArrayList<MainFrame>();
	
	//����Ϣ���Ƶ��û�������Ϣ�ĶԷ�������ʱ�ظ����ÿͻ��˵���Ϣ����
	public static final String message_call_back ="�Է���ʱ�����߻�����,���Ժ���ϵ������";
	
	//��Ϣ����Ϊ�ܾ��Է�����������
	public static final String msg_refuse_message = "refuse_message";
	
	public static Logger getLogger(){ //�õ���־Դ
		return Logger.getLogger(Tools.class);
	}
}
