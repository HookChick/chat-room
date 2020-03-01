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
	private static String[] names; // 当前用户列表

	public void init() {
		setTitle("聊天服务器");
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

		JLabel lblNewLabel = new JLabel("用户消息");
		lblNewLabel.setForeground(new Color(0, 255, 127));
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 15, 97, 37);
		headerPanel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("用户列表");
		lblNewLabel_1.setForeground(new Color(0, 255, 127));
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(385, 19, 60, 30);
		headerPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("2016:11:10 12:02:03");
		lblNewLabel_2.setForeground(new Color(210, 105, 30));
		lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(401, 0, 141, 22);
		headerPanel.add(lblNewLabel_2);

		/*** 中部面板开始 ****/
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

		/** 底部面板开始 */
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBounds(0, 339, 542, 70);
		contentPane.add(bottomPanel);
		bottomPanel.setLayout(null);

		JLabel label = new JLabel("总人数: 100");
		label.setForeground(new Color(0, 255, 255));
		label.setFont(new Font("宋体", Font.PLAIN, 15));
		label.setBounds(162, 21, 100, 39);
		bottomPanel.add(label);

		JLabel label_1 = new JLabel("当前在线人数:0");
		label_1.setForeground(new Color(135, 206, 235));
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		label_1.setBounds(283, 21, 130, 39);
		bottomPanel.add(label_1);
		
		label_2 = new JLabel("当前状态: 已关闭");
		label_2.setForeground(new Color(0, 255, 255));
		label_2.setFont(new Font("宋体", Font.PLAIN, 15));
		label_2.setBounds(21, 21, 131, 39);
		bottomPanel.add(label_2);

		upServerBtn = new JButton("开启服务器");
		upServerBtn.setForeground(new Color(30, 144, 255));
		upServerBtn.setBackground(Color.PINK);
		upServerBtn.addActionListener(this);
		upServerBtn.setBounds(425, 21, 107, 39);
		bottomPanel.add(upServerBtn);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private String[] getNameDatas() {// 刚开启服务器就初始化该用户界面的用户列表
		Tools.userList = serverUserDAO.getAll();
		names = new String[Tools.userList.size()];
		for (int i=0;i<names.length;i++) {
			User user = Tools.userList.get(i);
					names[i] = user.getUname();
		}
		return names;
	}
	
	//显示消息
	public void showMessage(Message msg){
		textArea.append(msg.getSendName()+" 对  "+msg.getReceiveName()+"\t"+msg.getSendTime()+"\n"+msg.getContext()+"\n");
	}
	
	public void upateUserList(User user) { // 每每有客户端成功登陆就更新总的用户列表
		if(user.isOnLine()){ //上线的通知
			for(int i=0;i<names.length;i++){
				if(user.getUname().equals(names[i])){
					names[i]= "<html><font color=" + "red" + ">"
							+ user.getUname() + "</font></html>";
					break;
				}
			}
		}else{ //下线的通知
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
			JOptionPane.showMessageDialog(this, "你点击了"+value);
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
