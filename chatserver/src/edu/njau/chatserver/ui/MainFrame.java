package edu.njau.chatserver.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import edu.njau.chatserver.dao.ServerUserDAO;
import edu.njau.chatserver.dao.impl.ServerUserDAOImpl;
import edu.njau.chatserver.model.Server;
import edu.njau.chatserver.util.Tools;
import edu.njau.common.Message;
import edu.njau.common.User;

public class MainFrame extends JFrame implements MouseListener, ActionListener {
	private static final long serialVersionUID = 7054406103817331545L;

	private ServerUserDAO serverUserDAO = new ServerUserDAOImpl();
	private JButton upServerBtn;
	private JLabel label_2;
	private static JTextArea textArea = new JTextArea();

	private JPanel centerPanel;
	private JList<String> userLists = new JList<String>();
	private static String[] names; // ��ǰ�û��б�

	public void init() {
		setTitle("���������");
		this.setBounds(400, 150, 555, 457);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel headerPanel = new JPanel();
		headerPanel.setForeground(new Color(160, 82, 45));
		headerPanel.setLayout(null);
		headerPanel.setBounds(0, 0, 542, 47);
		contentPane.add(headerPanel);

		JLabel lblNewLabel = new JLabel("�û���Ϣ");
		lblNewLabel.setForeground(new Color(0, 255, 127));
		lblNewLabel.setFont(new Font("����", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 15, 97, 37);
		headerPanel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("�û��б�");
		lblNewLabel_1.setForeground(new Color(0, 255, 127));
		lblNewLabel_1.setFont(new Font("����", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(385, 19, 60, 30);
		headerPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("2016:11:10 12:02:03");
		lblNewLabel_2.setForeground(new Color(210, 105, 30));
		lblNewLabel_2.setFont(new Font("����", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(401, 0, 141, 22);
		headerPanel.add(lblNewLabel_2);

		/*** �в���忪ʼ ****/
		centerPanel = new JPanel();
		centerPanel.setBounds(0, 0, 542, 342);
		contentPane.add(centerPanel);
		centerPanel.setLayout(null);
		
		userLists.setListData(getNameDatas());
		userLists.addMouseListener(this);
		JScrollPane scrollPane = new JScrollPane(userLists);
		
		scrollPane.setBounds(386, 49, 156, 293);
		centerPanel.add(scrollPane);
		
		JScrollPane msgScrollPane = new JScrollPane(textArea);
		textArea.setEditable(false);
		msgScrollPane.setBounds(0, 49, 387, 292);
		centerPanel.add(msgScrollPane);

		/** �ײ���忪ʼ */
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBounds(0, 339, 542, 70);
		contentPane.add(bottomPanel);
		bottomPanel.setLayout(null);

		JLabel label = new JLabel("������: 100");
		label.setForeground(new Color(0, 255, 255));
		label.setFont(new Font("����", Font.PLAIN, 15));
		label.setBounds(162, 21, 100, 39);
		bottomPanel.add(label);

		JLabel label_1 = new JLabel("��ǰ��������:0");
		label_1.setForeground(new Color(135, 206, 235));
		label_1.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		label_1.setBounds(283, 21, 130, 39);
		bottomPanel.add(label_1);
		
		label_2 = new JLabel("��ǰ״̬: �ѹر�");
		label_2.setForeground(new Color(0, 255, 255));
		label_2.setFont(new Font("����", Font.PLAIN, 15));
		label_2.setBounds(21, 21, 131, 39);
		bottomPanel.add(label_2);

		upServerBtn = new JButton("����������");
		upServerBtn.setForeground(new Color(30, 144, 255));
		upServerBtn.setBackground(Color.PINK);
		upServerBtn.addActionListener(this);
		upServerBtn.setBounds(425, 21, 107, 39);
		bottomPanel.add(upServerBtn);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private String[] getNameDatas() {// �տ����������ͳ�ʼ�����û�������û��б�
		Tools.userList = serverUserDAO.getAll();
		names = new String[Tools.userList.size()];
		for (int i=0;i<names.length;i++) {
			User user = Tools.userList.get(i);
					names[i] = user.getUname();
		}
		return names;
	}
	
	//��ʾ��Ϣ
	public void showMessage(Message msg){
		textArea.append(msg.getSendName()+" ��  "+msg.getReceiveName()+"\t"+msg.getSendTime()+"\n"+msg.getContext()+"\n");
	}
	
	public void upateUserList(User user) { // ÿÿ�пͻ��˳ɹ���½�͸����ܵ��û��б�
		if(user.isOnLine()){ //���ߵ�֪ͨ
			for(int i=0;i<names.length;i++){
				if(user.getUname().equals(names[i])){
					names[i]= "<html><font color=" + "red" + ">"
							+ user.getUname() + "</font></html>";
					break;
				}
			}
		}else{ //���ߵ�֪ͨ
			for(int i=0;i<names.length;i++){
				if(("<html><font color=" + "red" + ">"
						+ user.getUname() + "</font></html>").equals(names[i])){
					names[i]= user.getUname();
					break;
				}
			}
		}
		userLists.setListData(names);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == upServerBtn) {
			new Server();
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() >= 2) {
			String value = userLists.getSelectedValue();
			JOptionPane.showMessageDialog(this, "������"+value);
		}
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
