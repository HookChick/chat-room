package edu.njau.chatclient.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.njau.chatclient.tools.Tools;
import edu.njau.common.Message;
import edu.njau.common.User;

public class ChatFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 8950289666188787652L;
	
	private JTextField textField;
	private JButton sendButton;
	private JButton closeButton;
	private JTextArea textArea;
	private JScrollPane msgscrollPane;
	
	private String ownName; //自己的性命
	private String friendName;//聊天对象的姓名

	public ChatFrame(String ownname,String friendName) {
		this.ownName = ownname;
		this.friendName = friendName;
		this.setTitle(ownName+"(自己)正在和 "+friendName+" 聊天");
		this.setIconImage((new ImageIcon(this.getClass().getResource("/image/qq.gif")).getImage()));//设置标头
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(300, 150, 423, 334);
		this.addWindowListener(new WindowAdapter() {
			//关闭该窗口就要释放该聊天界面的线程
			@Override
			public void windowClosing(WindowEvent e) {
				Tools.chatFrameMap.remove(ChatFrame.this.ownName+" "+ChatFrame.this.friendName);
			}
			
		});
		JPanel backPane = new JPanel();
		setContentPane(backPane);
		backPane.setLayout(null);
		
		JPanel headerPanel = new JPanel();
		headerPanel.setBounds(3, 1, 415, 242);
		backPane.add(headerPanel);
		headerPanel.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		msgscrollPane = new JScrollPane(textArea);
		msgscrollPane.setBounds(0, 0, 397, 233);
		headerPanel.add(msgscrollPane);
		
		JPanel bottompanel = new JPanel();
		bottompanel.setBounds(5, 238, 397, 57);
		backPane.add(bottompanel);
		bottompanel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 10, 211, 37);
		bottompanel.add(textField);
		textField.setColumns(10);
		
		sendButton = new JButton("发送");
		sendButton.addActionListener(this);
		sendButton.setBounds(226, 10, 80, 37);
		bottompanel.add(sendButton);
		
		closeButton = new JButton("关闭");
		closeButton.addActionListener(this);
		closeButton.setBounds(310, 10, 80, 37);
		bottompanel.add(closeButton);
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == sendButton){
			String content = this.textField.getText().trim();
			if(null == content || "".equals(content)){
				return;
			}
			Message m = new Message();
			m.setMessageType(Tools.msg_common_message);
			m.setContext(content);
			m.setSendName(this.ownName);
			m.setReceiveName(this.friendName);
			m.setSendTime(Tools.SDF.format(Calendar.getInstance().getTime()));
			this.textArea.append("你    "+m.getSendTime()+"\n"+content+"\n");
			this.textField.setText("");
			//发送消息给服务端
			try {
				ObjectOutput objectOutput = new ObjectOutputStream(Tools.clientConServerThreadMap.get(this.getUserByOwnName(this.ownName)).getSocket().getOutputStream());
				objectOutput.writeObject(m);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
		if(e.getSource() == closeButton){
			
		}
	}
	
	private User getUserByOwnName(String ownName2) {
		for(User user : Tools.clientConServerThreadMap.keySet()){
			if(user.getUname().equals(ownName2)){
				return user;
			}
		}
		return null;
	}

	//显示消息
	public void showMessage(Message msg){
		this.textArea.append(msg.getSendName()+"  "+msg.getSendTime()+"\n"+msg.getContext()+"\n");
	}

	public void showMessageBack(Message msg) {
		//显示自己发送消息给好友时好友不在线或好友拒绝和你聊天时服务器返回的消息    
			this.textArea.append(msg.getSendTime()+"服务器自动回复：\n"+msg.getContext()+"\n");
		
	}
}
