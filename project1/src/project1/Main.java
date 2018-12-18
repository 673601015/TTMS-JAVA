package project1;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;

public class Main {
	public static String _version="0.8.1";
	private int FrameWide=400;
	private int FrameHight=320;
	static JFrame frame1;
	private JTextField text1;
	private JPasswordField text2;
	public static User user1;
	private ImageIcon BackgroundImageIcon=new ImageIcon(Main.class.getResource("/Images/Image2.png"));
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame1.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Form5.seat1.setImage(Form5.seat1.getImage().getScaledInstance(Form5.imagewide,
				Form5.imagehight,Image.SCALE_DEFAULT));
	    Form5.seat2.setImage(Form5.seat2.getImage().getScaledInstance(Form5.imagewide,
	    		Form5.imagehight,Image.SCALE_DEFAULT));
	    Form5.seat3.setImage(Form5.seat3.getImage().getScaledInstance(Form5.imagewide,
	    		Form5.imagehight,Image.SCALE_DEFAULT));
	    Form5.seat4.setImage(Form5.seat4.getImage().getScaledInstance(Form5.imagewide,
	    		Form5.imagehight,Image.SCALE_DEFAULT));
	    Form7.seat5.setImage(Form7.seat5.getImage().getScaledInstance(Form5.imagewide,
				Form5.imagehight,Image.SCALE_DEFAULT));
	    Form7.seat6.setImage(Form7.seat6.getImage().getScaledInstance(Form5.imagewide,
				Form5.imagehight,Image.SCALE_DEFAULT));
		frame1 = new JFrame();
		frame1.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame1.setBackground(Color.LIGHT_GRAY);
		frame1.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\eclipse\\project1\\src\\Icon.png"));

		frame1.setTitle("用户登录");
		frame1.getContentPane().setLayout(null);
		JPanel panel=new JPanel();
		panel.setLayout(null);

		text1 = new JTextField();
		text1.setBounds(115, 111, 117, 21);
		text1.setColumns(10);

		text2 = new JPasswordField();
		text2.setBounds(115, 142, 117, 21);
		text2.setColumns(10);

		JLabel label = new JLabel("账号");
		label.setFont(new Font("仿宋", Font.BOLD, 15));
		label.setBounds(70, 114, 54, 15);

		JLabel label_1 = new JLabel(" 密码");
		label_1.setFont(new Font("仿宋", Font.BOLD, 15));
		label_1.setBounds(61, 136, 44, 32);

		JButton btnNewButton = new JButton("登录");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!(text1.getText().toString().matches("[0-9a-zA-Z]{6,}")
						&&new String(text2.getPassword()).matches("[0-9a-zA-z]{6,}")))
				{
					JOptionPane.showMessageDialog(null, "账号或密码错误");
					return ;
				}
				
				user1=new User(text1.getText().toString()+"@"+_version, new String(text2.getPassword()));
				new Login();
			}
		});
		btnNewButton.setBounds(115, 197, 129, 32);

		JButton button = new JButton("退出");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		button.setBounds(115, 239, 129, 32);
		panel.setBounds(0, 0, FrameWide, FrameHight);
		panel.add(text1);
		panel.add(text2);
		panel.add(label);
		panel.add(label_1);
		panel.add(btnNewButton);
		panel.add(button);
		panel.setOpaque(false);
		JPanel BackGroundPanel=new JPanel();
		JLabel backgrouJLabel=new JLabel();
		BackGroundPanel.setBounds(0, 0, FrameWide, FrameHight);
		BackgroundImageIcon.setImage(BackgroundImageIcon.getImage().getScaledInstance(FrameWide,FrameHight , Image.SCALE_DEFAULT));
		backgrouJLabel.setIcon(BackgroundImageIcon);
		BackGroundPanel.add(backgrouJLabel);


		frame1.getContentPane().add(panel);

		JLabel label_2 = new JLabel("默笙忆影院管理系统");
		label_2.setFont(new Font("楷体", Font.BOLD, 35));
		label_2.setBounds(22, 10, 357, 56);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("版本");
		label_3.setText("版本:"+_version);
		label_3.setBounds(323, 295, 67, 15);
		panel.add(label_3);
		frame1.getContentPane().add(BackGroundPanel);
		frame1.setResizable(false);
		frame1.setBounds(100, 100, 400, 320);
		frame1.setLocationRelativeTo(null);
		frame1.setUndecorated(true);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
