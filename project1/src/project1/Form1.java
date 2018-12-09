package project1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.TextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;

public class Form1 {

	public static JFrame frame;
	private JTextField txtA;
	private JTextField txtA_1;
	private JLabel label_2;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		
//	}

	/**
	 * Create the application.
	 */
	public Form1() {
//	    Mainform window = new Mainform();
//		window.frame.setVisible(true);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("修改密码");
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("原密码:");
		lblNewLabel.setBounds(60, 59, 54, 22);
		frame.getContentPane().add(lblNewLabel);
		
		txtA = new JTextField();
		txtA.setBounds(124, 96, 100, 21);
		frame.getContentPane().add(txtA);
		txtA.setColumns(10);
		
		JLabel label = new JLabel("新密码：");
		label.setBounds(60, 95, 54, 22);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("确认新密码：");
		label_1.setBounds(34, 139, 80, 22);
		frame.getContentPane().add(label_1);
		
		txtA_1 = new JTextField();
		txtA_1.setColumns(10);
		txtA_1.setBounds(124, 140, 100, 21);
		frame.getContentPane().add(txtA_1);
		
		JButton button = new JButton("确认修改");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(new String(passwordField.getPassword()).compareTo(Main.user1.getPassword())!=0)
				{
					JOptionPane.showMessageDialog(null, "原密码错误!");
					return;
				}
				if(txtA.getText().toString().compareTo(txtA_1.getText().toString())!=0)
				{
					JOptionPane.showMessageDialog(null, "两次输入不一致，请检查");
					return;
				}
				if(!(txtA.getText().toString().matches("[0-9a-zA-Z]{6,}")
						&&new String(txtA_1.getText()).matches("[0-9a-zA-z]{6,}")))
				{
					//System.out.println(text2.getPassword().toString());
					JOptionPane.showMessageDialog(null, "输入格式错误");
					return ;
				}
				Modify_Password();
			}
		});
		button.setBounds(131, 197, 93, 23);
		frame.getContentPane().add(button);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(124, 60, 100, 21);
		frame.getContentPane().add(passwordField);
		
		
		frame.setBounds(100, 100, 400, 320);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
	public void Modify_Password()
	{
		
		try {
//			Login.pw=new PrintWriter(Login.socket.getOutputStream());
			Login.pw.println("ModifyPassword");
			Login.pw.flush();
			Thread.sleep(50);
//			Login.oos=new ObjectOutputStream(Login.socket.getOutputStream());
			Login.oos.writeObject(new User_Msg(Main.user1, txtA.getText().toString()));
			Login.oos.flush();
			Thread.sleep(50);
			String re=Login.reader.readLine();
			if(re.compareTo("True")==0)
			{
				JOptionPane.showMessageDialog(null, "修改密码成功");
			}
			else
			{
				JOptionPane.showMessageDialog(null, "修改密码失败");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

//class Add_User extends Thread {
//	public Add_User() {
//
//	}
//
//	public void run() {
//		try {
//			// Login.pw=new PrintWriter(Login.socket.getOutputStream());
//			// Login.oos=new ObjectOutputStream(Login.socket.getOutputStream());
//			Login.pw.println("Add_User");
//			Login.pw.flush();
//			Thread.sleep(50);
//			Login.oos.writeObject(
//					new User(Form1.textField.getText().toString(), Form1.textField_1.getText().toString()));
//			Login.oos.flush();
//			// Login.socket.shutdownOutput();
//			Form1.str = Login.reader.readLine();
//			// JOptionPane.showMessageDialog(null, str);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return;
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//}
