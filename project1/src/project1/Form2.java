package project1;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextField;

import project1.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Form2 {

	public static JFrame frame;
	public static JButton btnNewButton_1;
	public static String str;
	public static String username;
	public String online;
	private Form3 form3;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public Form2(String username,String online,Form3 form3) {
		this.username=username;
		this.online=online;
		this.form3=form3;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("用户管理");
		frame.getContentPane().setLayout(null);

//		JButton btnNewButton = new JButton("添加用户");
//		btnNewButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if (!textField.getText().toString().matches("[0-9a-zA-Z]{6,}"))// "[0-9a-zA-Z]{6}"
//				{
//					JOptionPane.showMessageDialog(null, "账号格式错误" + textField.getText().toString());
//					return;
//				}
//				if (!textField_1.getText().toString().matches("[0-9a-zA-Z]{6,}")) {
//					JOptionPane.showMessageDialog(null, "密码格式错误");
//					return;
//				}
//				if (textField_2.getText().toString().compareTo(textField_1.getText().toString()) != 0) {
//					JOptionPane.showMessageDialog(null, "两次输入不一致");
//					return;
//				}
//				Add_User adduser = new Add_User();
//
//				try {
//					adduser.start();
//					adduser.join(500);
//					adduser.interrupt();
//					JOptionPane.showMessageDialog(null, str);
//					str = "null";
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//
//				// Add_User();
//			}
//		});
//		btnNewButton.setBounds(10, 259, 93, 23);
//		frame.getContentPane().add(btnNewButton);

		btnNewButton_1 = new JButton("删除用户");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Delete_User deleteuser = new Delete_User();
					deleteuser.start();
					deleteuser.join(500);
					deleteuser.interrupt();
					JOptionPane.showMessageDialog(null, str);
					str = "null";
					form3.Get_AllUser_Data();
					frame.dispose();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// Delete_User();
			}
		});
		btnNewButton_1.setBounds(23, 179, 93, 23);
		frame.getContentPane().add(btnNewButton_1);

		JLabel label_3 = new JLabel("当前用户名:");
		label_3.setBounds(34, 10, 82, 30);
		frame.getContentPane().add(label_3);

		JLabel label_4 = new JLabel("      ");
//		label_4.addMouseListener(new MouseAdapter() {
//			public void mouseClicked(MouseEvent e) {
//				if (e.getClickCount() == 2) {
//					Form1.frame.setVisible(true);
//					frame.dispose();
//				}
//			}
//		});
		label_4.setBounds(119, 14, 113, 23);
		label_4.setText(""+username);
		frame.getContentPane().add(label_4);
		
		JButton button = new JButton("封禁用户");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Ban_User banuser = new Ban_User();
					banuser.start();
					banuser.join(500);
					banuser.interrupt();
					JOptionPane.showMessageDialog(null, str);
					str = "null";
					form3.Get_AllUser_Data();
					frame.dispose();
					
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// Delete_User();
			}
		});
		button.setBounds(23, 113, 93, 23);
		frame.getContentPane().add(button);
		
		JButton button_1 = new JButton("强制下线");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Offline_User offlineuer = new Offline_User();
					offlineuer.start();
					offlineuer.join(500);					
					offlineuer.interrupt();
					JOptionPane.showMessageDialog(null, str);
					str = "null";
					form3.Get_AllUser_Data();
					frame.dispose();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_1.setBounds(23, 146, 93, 23);
		frame.getContentPane().add(button_1);
		
		JButton button_2 = new JButton("修改密码");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button_2.setBounds(147, 146, 93, 23);
		frame.getContentPane().add(button_2);
		
		JButton button_3 = new JButton("解封用户");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Ban_User_Cancel banusercancel = new Ban_User_Cancel();
					banusercancel.start();
					banusercancel.join(500);
					banusercancel.interrupt();
					JOptionPane.showMessageDialog(null, str);
					str = "null";
					form3.Get_AllUser_Data();
					frame.dispose();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_3.setBounds(147, 113, 93, 23);
		frame.getContentPane().add(button_3);
		
		JLabel label = new JLabel("当前用户状态:");
		label.setBounds(23, 50, 93, 30);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("<dynamic>");
		label_1.setText(""+online);
		label_1.setBounds(119, 54, 113, 23);
		frame.getContentPane().add(label_1);
		frame.setBounds(100, 100, 400, 320);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
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
//					new User(Form2.textField.getText().toString(), Form2.textField_1.getText().toString()));
//			Login.oos.flush();
//			// Login.socket.shutdownOutput();
//			Form2.str = Login.reader.readLine();
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

class Delete_User extends Thread {
	public Delete_User() {

	}

	public void run() {
		try {
			// Login.pw=new PrintWriter(Login.socket.getOutputStream());
			// Login.oos=new ObjectOutputStream(Login.socket.getOutputStream());
			Login.pw.println("Delete_User");
			Login.pw.flush();
			Thread.sleep(50);
			Login.oos.writeObject(new User(Form2.username, "111111"));
			Login.oos.flush();
			Thread.sleep(50);
			// System.out.println(Form2.textField.getText().toString());
			// Login.socket.shutdownOutput();
			Form2.str = Login.reader.readLine();

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
class Ban_User extends Thread {
	public Ban_User() {

	}

	public void run() {
		try {
			// Login.pw=new PrintWriter(Login.socket.getOutputStream());
			// Login.oos=new ObjectOutputStream(Login.socket.getOutputStream());
			Login.pw.println("Ban_User");
			Login.pw.flush();
			Thread.sleep(50);
			Login.oos.writeObject(new User(Form2.username, "111111"));
			Login.oos.flush();
			Thread.sleep(50);
			// System.out.println(Form2.textField.getText().toString());
			// Login.socket.shutdownOutput();
			Form2.str = Login.reader.readLine();

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
class Ban_User_Cancel extends Thread {
	public Ban_User_Cancel() {

	}

	public void run() {
		try {
			// Login.pw=new PrintWriter(Login.socket.getOutputStream());
			// Login.oos=new ObjectOutputStream(Login.socket.getOutputStream());
			Login.pw.println("User_Baned_Cancel");
			Login.pw.flush();
			Thread.sleep(50);
			Login.oos.writeObject(new User(Form2.username, "111111"));
			Login.oos.flush();
			Thread.sleep(50);
			// System.out.println(Form2.textField.getText().toString());
			// Login.socket.shutdownOutput();
			Form2.str = Login.reader.readLine();

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
class Offline_User extends Thread {
	public Offline_User() {

	}

	public void run() {
		try {
			// Login.pw=new PrintWriter(Login.socket.getOutputStream());
			// Login.oos=new ObjectOutputStream(Login.socket.getOutputStream());
			Login.pw.println("Offline_User");
			Login.pw.flush();
			Thread.sleep(50);
			Login.oos.writeObject(new User(Form2.username, "111111"));
			Login.oos.flush();
			Thread.sleep(50);
			// System.out.println(Form2.textField.getText().toString());
			// Login.socket.shutdownOutput();
			Form2.str = Login.reader.readLine();

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
