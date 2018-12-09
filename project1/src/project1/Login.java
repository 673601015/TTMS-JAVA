package project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

public class Login {
	private String address = "127.0.0.1";
	private int port = 8888;
	public static PrintWriter pw;
	public static OutputStream os;
	public static ObjectOutputStream oos;
	public static BufferedReader reader;
	public static ObjectInputStream ois;
	public static Socket socket;
	public static String str = "null";
	public static InputStream is;
	private long HeartTime = 0;
	private long HearMaxTime = 4000000;
	String Getmsg = "";
	public static boolean lock=false;
	public static int num=0;
	public Login() {
		super();
		try {
			socket = new Socket(address, port);
			// socket.setTcpNoDelay(true);
			os = socket.getOutputStream();
			is =socket.getInputStream();
			pw = new PrintWriter(os);
			oos = new ObjectOutputStream(os);
			// oos.writeObject(Main.user1);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"GBK"));
			// ois=new ObjectInputStream(socket.getInputStream());
			Login_oos loos = new Login_oos();
			Login.oos.writeObject(Main.user1);
			Login.oos.flush();
			Thread.sleep(50);
			Login.str = Login.reader.readLine();
//			loos.start();
//			loos.join(500);
//			loos.interrupt();
			// str=reader.readLine();
//			Thread.sleep(50);
//			System.out.println(str);
			if(str.compareTo("This User BANED")==0)
			{
				JOptionPane.showMessageDialog(null, "账号已被封禁，请联系管理员");
			}
			else if(str.compareTo("This User online")==0)
			{
				JOptionPane.showMessageDialog(null, "账号已在线");
			}
			else if(str.compareTo("Server Maintenance")==0)
			{
				JOptionPane.showMessageDialog(null, "服务器维护，请稍后再试");
			}
			else if(str.compareTo("Login Failed")==0)
			{
				JOptionPane.showMessageDialog(null, "登录失败");
			}
			else if(str.compareTo("Username or Password Error")==0)
			{
				JOptionPane.showMessageDialog(null, "账号或密码错误");
			}
			else if(str.compareTo("The version is too old,Please Update")==0)
			{
				JOptionPane.showMessageDialog(null, "当前版本过低，请更新后再登录");
			}
			else if (str.matches("[0-3]")||str.compareTo("999")==0) {
				switch (str) {
				case "0":
					Main.user1.setPermission("管理员");
					break;
				case "1":
					Main.user1.setPermission("经理");
					break;
				case "2":
					Main.user1.setPermission("售票员");
					break;
				case "3":
					Main.user1.setPermission("游客");
					break;
				case "999":
					Main.user1.setPermission("超级管理员");
					break;
				}
				String[]str=Main.user1.getUsername().split("@");
				Main.user1.setUsername(str[0]);
				JOptionPane.showMessageDialog(null, "登录成功，您的权限是:" + Main.user1.getPermission());
				Thread.sleep(50);
				ois = new ObjectInputStream(socket.getInputStream());
//				oos = new ObjectOutputStream(os);
				HeartTime=System.nanoTime();
				Heart();
				// new Form1();
				// new Form2();
				new Form3();
				Main.frame1.dispose();
			} else {
				JOptionPane.showMessageDialog(null, str);
			}
		} catch (ConnectException e) {
			JOptionPane.showMessageDialog(null, "服务器连接失败，请稍后再试");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Heart() {
		Timer heart = new Timer();

		heart.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				try {
					if (!Login.lock) {
						Login.lock = true;
						pw.println("Heart");
						pw.flush();
						Thread.sleep(50);
						Getmsg = reader.readLine();
						if (Getmsg.compareTo("Online") == 0) {
							HeartTime = System.nanoTime();
						}
						Login.lock = false;
					}
				} catch (InterruptedException | IOException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "网络连接中断");
					System.exit(0);
				}
			}
		}, 1, 2000);
	}

	public void isOnline() {
		Timer heart = new Timer();

		heart.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				if ((System.nanoTime()-HeartTime) > HearMaxTime) {
					JOptionPane.showMessageDialog(null, "网络连接中断");
					System.exit(0);
				}
			}
		}, 1, 500);
	}
}

class Login_oos extends Thread {
	public Login_oos() {

	}

	public void run() {
		try {
			Login.oos.writeObject(Main.user1);
			Login.oos.flush();
			Thread.sleep(50);
			Login.str = Login.reader.readLine();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}