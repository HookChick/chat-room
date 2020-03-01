package edu.njau.chatclient.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import edu.njau.chatclient.biz.ClientUser;
import edu.njau.chatclient.tools.Tools;
import edu.njau.common.User;
//用户登录界面
public class LoginFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 8685488464545826580L;
	
	private JPanel contentPane;
	private JTextField nameField;
	private JPasswordField pwdField;
	private JButton loginButton;

	public LoginFrame() {
		this.setTitle("欢迎登录聊天系统");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(420, 200, 549, 308);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(180, 8, 321, 250);
		contentPane.add(panel);
		panel.setLayout(null);
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(this.getClass().getResource("/image/user.png")));
		label.setBounds(10, 20, 73, 71);
		panel.add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(this.getClass().getResource("/image/pwd.jpg")));
		label_1.setBounds(10, 105, 73, 71);
		panel.add(label_1);
		
		nameField = new JTextField();
		nameField.setBounds(113, 34, 185, 45);
		panel.add(nameField);
		nameField.setColumns(10);
		
		pwdField = new JPasswordField();
		pwdField.setColumns(10);
		pwdField.setBounds(113, 119, 185, 45);
		panel.add(pwdField);
		
		loginButton = new JButton("");
		loginButton.setIcon(new ImageIcon(this.getClass().getResource("/image/loginBtn.png")));
		loginButton.addActionListener(this);
		loginButton.setBounds(62, 206, 150, 30);
		panel.add(loginButton);
		
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(this.getClass().getResource("/image/back.png")));
		label_2.setBounds(0, 0, 533, 270);
		contentPane.add(label_2);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		User user = new User(nameField.getText().trim(),new String(pwdField.getPassword()));
		ClientUser clientUser = new ClientUser();
		String result = clientUser.checkLogin(user);
		if("ServerNotUP".equals(result)){
			JOptionPane.showMessageDialog(this, "服务器没有开启,请确保管理员开启服务器后登录", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(result != null){ //收到信息，表明服务器启动，且端口配置正确
			if(Tools.Login_success_msg.equals(result)){ 
				//登录成功
				MainFrameList mainFrameList = new MainFrameList(user.getUname());
				Tools.frameListMap.put(user.getUname(), mainFrameList);
				this.dispose();
			}else if(Tools.Login_nameNotFound.equals(result)){ 
				//用户名不存在，登录失败
				JOptionPane.showMessageDialog(this, "您的用户名不正确哦", "警告", JOptionPane.WARNING_MESSAGE);
			}else if(Tools.Login_pwdError.equals(result)){
				//密码错误
				JOptionPane.showMessageDialog(this, "您的用密码不正确哦", "警告", JOptionPane.WARNING_MESSAGE);
			}else if(Tools.Login_repeat.equals(result)){
				//同一账号重复登陆 
				JOptionPane.showMessageDialog(this, "您的账户已经登录了哦,不能重复登录啊", "警告", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
}
