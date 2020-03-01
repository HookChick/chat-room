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
//�û���¼����
public class LoginFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 8685488464545826580L;
	
	private JPanel contentPane;
	private JTextField nameField;
	private JPasswordField pwdField;
	private JButton loginButton;

	public LoginFrame() {
		this.setTitle("��ӭ��¼����ϵͳ");
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
			JOptionPane.showMessageDialog(this, "������û�п���,��ȷ������Ա�������������¼", "����", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(result != null){ //�յ���Ϣ�������������������Ҷ˿�������ȷ
			if(Tools.Login_success_msg.equals(result)){ 
				//��¼�ɹ�
				MainFrameList mainFrameList = new MainFrameList(user.getUname());
				Tools.frameListMap.put(user.getUname(), mainFrameList);
				this.dispose();
			}else if(Tools.Login_nameNotFound.equals(result)){ 
				//�û��������ڣ���¼ʧ��
				JOptionPane.showMessageDialog(this, "�����û�������ȷŶ", "����", JOptionPane.WARNING_MESSAGE);
			}else if(Tools.Login_pwdError.equals(result)){
				//�������
				JOptionPane.showMessageDialog(this, "���������벻��ȷŶ", "����", JOptionPane.WARNING_MESSAGE);
			}else if(Tools.Login_repeat.equals(result)){
				//ͬһ�˺��ظ���½ 
				JOptionPane.showMessageDialog(this, "�����˻��Ѿ���¼��Ŷ,�����ظ���¼��", "����", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
}
