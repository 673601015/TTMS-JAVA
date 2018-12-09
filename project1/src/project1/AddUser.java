package project1;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddUser {

	public JFrame frame;
	public JTextField textField;
	public JTextField textField_1;
	public JTextField textField_2;
	public JComboBox comboBox = new JComboBox();

	/**
	 * Launch the application.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddUser window = new AddUser();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public AddUser() {
		initialize();
	}
	public AddUser _instance()
	{
		return this;
	}
	/**
	 * Initialize the contents of the frame.
	 * 
	 * @wbp.parser.entryPoint
	 */
	@SuppressWarnings("unchecked")
	private void initialize() {
		frame = new JFrame("增加用户");
		frame.setBounds(100, 100, 400, 320);
		frame.getContentPane().setLayout(null);

		JLabel label = new JLabel("新用户名:");
		label.setBounds(59, 62, 64, 15);
		frame.getContentPane().add(label);

		JLabel label_1 = new JLabel("密码:");
		label_1.setBounds(69, 105, 54, 15);
		frame.getContentPane().add(label_1);

		JLabel label_2 = new JLabel("确认密码:");
		label_2.setBounds(59, 149, 64, 15);
		frame.getContentPane().add(label_2);

		textField = new JTextField();
		textField.setBounds(133, 59, 106, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(133, 102, 106, 21);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBounds(133, 146, 106, 21);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);

		comboBox.setModel(new DefaultComboBoxModel(new String[] { "游客", "售票员", "经理", "管理员" }));
		comboBox.setBounds(133, 190, 106, 21);
		frame.getContentPane().add(comboBox);

		JLabel label_3 = new JLabel("新用户权限:");
		label_3.setBounds(48, 193, 75, 15);
		JButton btnNewButton = new JButton("确认增加");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				JOptionPane.showMessageDialog(null, "" + comboBox.getSelectedItem());
				if(!(textField.getText().toString().matches("[0-9a-zA-Z]{6,}")
						&&new String(textField_1.getText()).matches("[0-9a-zA-z]{6,}")))
				{
					//System.out.println(text2.getPassword().toString());
					JOptionPane.showMessageDialog(null, "账号或密码格式错误");
					return ;
				}
				if(textField_1.getText().toString().compareTo(
						textField_2.getText().toString())!=0)
				{
					JOptionPane.showMessageDialog(null, "两次密码输入不一致");
					return;
				}
				new Add_User(_instance()).start();
				
			}
		});
		btnNewButton.setBounds(119, 236, 93, 23);
		frame.getContentPane().add(btnNewButton);
		frame.getContentPane().add(label_3);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
}

class Add_User extends Thread {
	AddUser adduser;

	public Add_User(AddUser adduser) {
		this.adduser = adduser;
	}

	public void run() {
		try {
			// Login.pw=new PrintWriter(Login.socket.getOutputStream());
			// Login.oos=new ObjectOutputStream(Login.socket.getOutputStream());
			Login.pw.println("Add_User");
			Login.pw.flush();
			Thread.sleep(50);
			User newuser = new User(adduser.textField.getText().toString(), adduser.textField_1.getText().toString());
			switch (adduser.comboBox.getSelectedItem().toString()) {
			case "游客":
				newuser.setPermission("3");
				break;
			case "售票员":
				newuser.setPermission("2");
				break;
			case "经理":
				newuser.setPermission("1");
				break;
			case "管理员":
				newuser.setPermission("0");
				break;
			default:
				return;
			}
			Login.oos.writeObject(newuser);
			Login.oos.flush();
			Thread.sleep(50);
			// Login.socket.shutdownOutput();
			 String str = Login.reader.readLine();
			 JOptionPane.showMessageDialog(null, ""+str);
			 adduser._instance().frame.dispose();
			// JOptionPane.showMessageDialog(null, str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
