package edu.njau.chatclient.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.njau.chatclient.tools.ClientConServerThread;
import edu.njau.chatclient.tools.Tools;
import edu.njau.common.Message;
import edu.njau.common.User;

public class MainFrameList extends JFrame implements MouseListener {
	// ��¼�ɹ��ĺ���������
	private static final long serialVersionUID = 761880212376384955L;

	private String currentName;
	private JList userList;
	private static String[] names; // ��ǰ�û��б�

	public MainFrameList(String name) {
		this.currentName = name;
		this.init();
	}

	private void init() {
		this.setTitle("��ӭ" + this.currentName);
		this.setLocation(1000, 120);
		JPanel backPanel = new JPanel(); // �������
		backPanel.setLayout(null);
		this.addWindowListener(new WindowAdapter() {
			//�رմ��ھ�Ҫ�ͷŸ��̣߳�ͬʱ�����˳�����Ϣ���͸�������
			@Override
			public void windowClosing(WindowEvent e) {
				User currentUser = null;
				for(User user:Tools.clientConServerThreadMap.keySet()){
					if(MainFrameList.this.currentName.equals(user.getUname())){
						currentUser = user;
						break;
					}
				}
				Message msg = new Message();
				msg.setMessageType(Tools.msg_offLine_message);
				msg.setSendName(currentName);
				ClientConServerThread clientConServerThread = Tools.clientConServerThreadMap.get(currentUser);
				try {
					ObjectOutputStream objectOutpuStream = new ObjectOutputStream(clientConServerThread.getSocket().getOutputStream());
					objectOutpuStream.writeObject(msg);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				clientConServerThread.stop();//�����Լ�
				Tools.clientConServerThreadMap.remove(currentUser);
			}
		});
		
		userList = new JList(getNameDatas());
		userList.addMouseListener(this);
		JScrollPane jsp = new JScrollPane(userList);
		jsp.setBounds(0, 0, 200, 450);
		backPanel.add(jsp);
		this.add(backPanel);
		this.setSize(220, 500);
		this.setVisible(true);
	}
	
	public void upateFriend(Message msg) { // ���ݷ���˷�������Ϣ�����û��б�
		User user = msg.getUsers().get(0);
		if(user.isOnLine()){ //���ߵ�֪ͨ
			JOptionPane.showMessageDialog(this, user.getUname()+"������");
			for(int i=0;i<names.length;i++){
				if(user.getUname().equals(names[i])){
					names[i]= "<html><font color=" + "red" + ">"
							+ user.getUname() + "</font></html>";
					break;
				}
			}
		}else{ //���ߵ�֪ͨ
			JOptionPane.showMessageDialog(this, user.getUname()+"������");
			for(int i=0;i<names.length;i++){
				if(("<html><font color=" + "red" + ">"
						+ user.getUname() + "</font></html>").equals(names[i])){
					names[i]= user.getUname();
					break;
				}
			}
		}
		userList.setListData(names);
	}

	private String[] getNameDatas() {// �յ�½������ʼ�����û�������û��б�
		names = new String[Tools.users.size()];
		for (int i=0;i<names.length;i++) {
			User user = Tools.users.get(i);
			if (!this.currentName.equals(user.getUname())) {
				if (user.isOnLine()) {
					names[i] = "<html><font color=" + "red" + ">"
							+ user.getUname() + "</font></html>";
				} else {
					names[i] = user.getUname();
				}
			}
		}
		return names;
	}

	public int showConfirmMessage(MainFrameList mainFrame,Message msg){ //�����û���������ʱ����ʾ����Ϣ
		int option = JOptionPane.showConfirmDialog(mainFrame, msg.getSendName()+"���������,�Ƿ�ͬ��?", "������ʾ", JOptionPane.YES_NO_OPTION);
		return option;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() >= 2) {
			// �õ��ú��ѵ�����
			String friendName = (String)userList.getSelectedValue();
			if(friendName.endsWith("</html>")&&friendName.startsWith("<html>")){
				friendName = friendName.substring(22,(friendName.length()-14));
			}
			// �򿪸�����Ľ���
			ChatFrame chatFrame = new ChatFrame(this.currentName, friendName);
			// �����������뵽�������������༯��
			Tools.chatFrameMap.put(this.currentName + " " + friendName,
					chatFrame);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}
	@Override
	public void mouseReleased(MouseEvent e) {

	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}

}
