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
import java.net.Socket;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Response extends Thread {
		
	public Socket client;
	public InputStream is;
	public OutputStream os;
	public ObjectOutputStream oos;
	public BufferedReader reader;
	public ObjectInputStream ois;
	public PrintWriter pw;
	public User user1;
	public int num = 0;
	// public static long heartTime = 0;
	// public long heartMaxTime = 4000;
	public boolean heartTime = true;
	public boolean Useronline = true;
	public boolean lock = false;

	public Response(Socket client, User user1, ObjectInputStream ois) {
		this.client = client;
		this.user1 = user1;
		this.ois = ois;
	}

	public void run() {
		try {
			is = client.getInputStream();
			os = client.getOutputStream();
			oos = new ObjectOutputStream(os);
			ServerMain.oosList.add(oos);
			// ois = new ObjectInputStream(is);
			reader = new BufferedReader(new InputStreamReader(is,"GBK"));
//			reader = new BufferedReader(new InputStreamReader(is));
			// reader=new BufferedReader(new
			// InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(os);
			// heartTime=System.currentTimeMillis();
			String msg;
			isOnlie();
			System.out.println("用户:" + user1.getUsername() + "登录成功");
			while (Useronline) {
				// reader=new BufferedReader(new
				// InputStreamReader(client.getInputStream()));
				// System.out.println("wating");
				if (!Useronline)
					break;
				if (lock)
					continue;
				msg = reader.readLine();
				// if(msg.compareTo("Heart")!=0)
				// {
				// num++;
				// System.out.println("请求次数"+num);
				// }
				// System.out.println("用户:" + user1.getUsername() + "发送指令:" +
				// msg);
//				Response_Msg newresponse = new Response_Msg(this, msg);
//				newresponse.start();
				respos(msg);
				// newresponse.join(1100);
				// newresponse.interrupt();
				Thread.sleep(50);
			}
			// is.close();
			// os.close();
			// oos.close();
			// client.close();
			// Sql_Main.User_Offline(user1);
			// System.out.println("用户" + user1.getUsername() + "离线");
			userOffline(oos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();

		}
		// catch (ClassNotFoundException e) {
		// // TODO Auto-generated catch block
		// // e.printStackTrace();
		// }
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void isOnlie() {
		Timer online = new Timer();
		online.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// System.out.println("并发"+(System.currentTimeMillis()-Response.heartTime));
				// System.out.println(System.currentTimeMillis());
				// System.out.println(Response.heartTime);
				// if((System.currentTimeMillis()-Response.heartTime)>heartMaxTime)
				// {
				// Useronline=false;
				// System.out.println("1111");
				// System.out.println("结束"+(System.currentTimeMillis()-Response.heartTime));
				// online.cancel();
				// }
				if(!Sql_Main.User_Isline(user1))
				{
					heartTime = false;
				}
				if (heartTime)
					heartTime = false;
				else {
					Useronline = false;
					System.out.println("结束线程");
					userOffline(oos);
					online.cancel();

				}
			}
		}, 1, 10000);
	}

	public void userOffline(ObjectOutputStream oos) {
		if (oos != null) {
			try {
				is.close();
				os.close();
				oos.close();
				client.close();
				ServerMain.oosList.remove(ServerMain.oosList.indexOf(oos));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
			}
			Sql_Main.User_Offline(user1);
			System.out.println("用户" + user1.getUsername() + "离线");
		}
	}

	public void respos(String msg) {

		lock = true;
		try {

			switch (msg) {
			case "ModifyPassword":
				User_Msg us1 = (User_Msg) ois.readObject();
				if (ServerMain.sqlmain.Modify_Password(us1.us, us1.msg)) {
					System.out.println("用户:" + us1.us.getUsername() + "修改密码为:" + us1.msg);
					pw.println("True");
					pw.flush();
				} else {
					pw.println("False");
					pw.flush();
				}

				break;
			
			case "Add_User":
				User newUser = (User) ois.readObject();
				System.out.println("用户:" + user1.getUsername() + "添加用户:" + newUser.getUsername() + "密码"
						+ newUser.getPassword() + "新用户权限:" + newUser.getPermission());
				if (newUser.getPermission().compareTo("0") == 0 && user1.getPermission().compareTo("999") != 0) {
					pw.println("Permission Denied");
					pw.flush();
					break;
				} else if ((user1.getPermission().compareTo("0") != 0) && user1.getPermission().compareTo("999") != 0) {
					pw.println("Permission Denied");
					pw.flush();
					break;
				} else {
					if (Sql_Main.Add_User(newUser)) {
						pw.println("Add User Successfully ");
						pw.flush();
						break;
					} else {
						pw.println("Add User Failed");
						pw.flush();
						break;
					}
				}
				// break;
			case "Delete_User":
				// ois=new ObjectInputStream(client.getInputStream());
				User olduser;
				olduser = (User) ois.readObject();
				if ((user1.getPermission().compareTo("0") != 0 || Sql_Main.Get_Permission(olduser).compareTo("0") == 0)
						&& user1.getPermission().compareTo("999") != 0) {
					pw.println("Permission Denied");
					pw.flush();
					break;
				} else {
					if (Sql_Main.Delete_User(olduser)) {
						System.out.println("管理员:" + user1.getUsername() + " 删除用户" + olduser.getUsername() + "成功");
						pw.println("Delete User Successfully ");
						pw.flush();
						break;
					} else {
						pw.println("Delete User Failed");
						pw.flush();
						break;
					}
				}
				// break;
			case "Ban_User":
				// ois=new ObjectInputStream(client.getInputStream());
				User newBanuser;
				newBanuser = (User) ois.readObject();
				if ((user1.getPermission().compareTo("0") != 0
						|| Sql_Main.Get_Permission(newBanuser).compareTo("0") == 0)
						&& user1.getPermission().compareTo("999") != 0) {
					pw.println("Permission Denied");
					pw.flush();
					break;
				}

				else {
					if (Sql_Main.User_BAN(newBanuser)) {
						System.out.println("管理员:" + user1.getUsername() + " 封禁用户" + newBanuser.getUsername() + "成功");
						pw.println("Ban User Successfully ");
						pw.flush();
						break;
					} else {
						pw.println("Ban User Failed");
						pw.flush();
						break;
					}
				}
				// break;
			case "Offline_User":
				// ois=new ObjectInputStream(client.getInputStream());
				User newOfflineuser;
				newOfflineuser = (User) ois.readObject();
				if ((user1.getPermission().compareTo("0") != 0
						|| Sql_Main.Get_Permission(newOfflineuser).compareTo("0") == 0)
						&& user1.getPermission().compareTo("999") != 0) {
					pw.println("Permission Denied");
					pw.flush();
					break;
				}

				else {
					if (Sql_Main.User_Offline(newOfflineuser)) {
						System.out.println(
								"管理员:" + user1.getUsername() + " 强制下线用户" + newOfflineuser.getUsername() + "成功");
						pw.println("Offline User Successfully ");
						pw.flush();
						break;
					} else {
						pw.println("Offline User Failed");
						pw.flush();
						break;
					}
				}
			case "User_Baned_Cancel":
				// ois=new ObjectInputStream(client.getInputStream());
				User newCancelBaneduser;
				newCancelBaneduser = (User) ois.readObject();
				if ((user1.getPermission().compareTo("0") != 0
						|| Sql_Main.Get_Permission(newCancelBaneduser).compareTo("0") == 0)
						&& user1.getPermission().compareTo("999") != 0) {
					pw.println("Permission Denied");
					pw.flush();
					break;
				}

				else {
					if (Sql_Main.User_BANED_Cancel(newCancelBaneduser)) {
						System.out.println(
								"管理员:" + user1.getUsername() + " 解封用户" + newCancelBaneduser.getUsername() + "成功");
						pw.println("Successfully ");
						pw.flush();
						break;
					} else {
						pw.println("Failed");
						pw.flush();
						break;
					}
				}
			case "Get_TB_MOVIE":
				oos.writeObject(new Get_TB_ArrayList(Sql_Main.Get_Movie()));
				break;
			case "Get_TB_Preformance":
				oos.writeObject(new Get_TB_ArrayList(Sql_Main.Get_Preformance()));
				break;
			case "Get_TB_Plan":
				oos.writeObject(new Get_TB_ArrayList(Sql_Main.Get_Plan()));
				break;
			case "Get_TB_AllUser":
				// System.out.println(o.getClass()+"===="+ oos);
				oos.writeObject(new Get_TB_ArrayList(Sql_Main.Get_AllUer()));
				break;
			case "Get_All_Movie_Name":
				oos.writeObject(new Get_TB_ArrayList(Sql_Main.Get_All_Movie_Name()));
				break;
			case "Get_All_PreformanceID":
				oos.writeObject(new Get_TB_ArrayList(Sql_Main.Get_All_PreformanceID()));
				break;
			case"Get_Sale_Data":
				oos.writeObject(new Get_TB_ArrayList(Sql_Main.Get_Sale_Data()));
				break;
			case "Change_Ticket":
				String Plan_ID = reader.readLine();
				String newSeat = reader.readLine();
				String Flag = reader.readLine();
				String FlagSeat = reader.readLine();
				if (Sql_Main.Change_Ticket(Plan_ID, newSeat,Flag,FlagSeat)) {
					pw.println("Successfully");
					pw.flush();
				} else {
					pw.println("Failed");
					pw.flush();
				}
				break;
			case "Server_Maintenance":
				if (Sql_Main.Server_Maintenance()) {
					pw.println("Successfully");
					pw.flush();
				} else {
					pw.println("Failed");
					pw.flush();
				}
				break;
			case"Server_Maintenance_Fin":
				if (Sql_Main.Server_Maintenance_Fin()) {
					pw.println("Successfully");
					pw.flush();
				} else {
					pw.println("Failed");
					pw.flush();
				}
				break;
			case "Add_Movie":
				String Movie_str=reader.readLine();
				String[] Movie_Data=Movie_str.split("@");
				if (Sql_Main.Add_Movie(Movie_Data[0], Movie_Data[1], Movie_Data[2]
						, Movie_Data[3], Movie_Data[4])) {
					pw.println("Successfully");
					pw.flush();
				} else {
					pw.println("Failed");
					pw.flush();
				}

				break;
			
			case "Del_Movie":
				String Movie_Name=reader.readLine();
				if (Sql_Main.Del_Movie(Movie_Name)) {
					pw.println("Successfully");
					pw.flush();
				} else {
					pw.println("Failed");
					pw.flush();
				}

				break;
			case "Del_Plan":
				String Plan_ID1=reader.readLine();
				if (Sql_Main.Del_Plan(Plan_ID1)) {
					pw.println("Successfully");
					pw.flush();
				} else {
					pw.println("Failed");
					pw.flush();
				}
				break;
			case "Del_Performance":
				String Performance_ID1=reader.readLine();
				if (Sql_Main.Del_Performance(Performance_ID1)) {
					pw.println("Successfully");
					pw.flush();
				} else {
					pw.println("Failed");
					pw.flush();
				}
				break;
			case "Modify_Performance_Seat":
				String Performance_ID = reader.readLine();
				String Performance_Rows=reader.readLine();
				String Performance_Columns=reader.readLine();
				String Performance_newSeat = reader.readLine();
				if (Sql_Main.Modify_Performance_Seat(Performance_ID,Performance_Rows,
						Performance_Columns, Performance_newSeat)) {
					pw.println("Successfully");
					pw.flush();
				} else {
					pw.println("Failed");
					pw.flush();
				}
				break;
			case "Modify_Plan":
				String PlanData_Str=reader.readLine();
				String[] PlanData=PlanData_Str.split("@");
				if(Sql_Main.Modify_Plan(PlanData[0], PlanData[1], 
						PlanData[2], PlanData[3], PlanData[4],
						PlanData[5], PlanData[6], PlanData[7]))
				{
					pw.println("Successfully");
					pw.flush();
				} else {
					pw.println("Failed");
					pw.flush();
				}
				break;
			case "Get_Performance_FromID":
				String Performance_ID2 = reader.readLine();
				pw.println(Sql_Main.Get_Performance_FromID(Performance_ID2));
				pw.flush();
				break;
			case "Heart":
				pw.println("Online");
				pw.flush();
				// heartTime = System.currentTimeMillis();
				if (Sql_Main.User_Isline(user1))
					heartTime = true;
				else {
					heartTime = false;
				}
				if(user1.getPermission().compareTo("999")!=0)
				{
					if(Sql_Main.Server_Isline())
						heartTime = true;
					else {
						heartTime = false;
					}
				}
				break;
			default:
				// clean_Socket_Input();
				byte[] by = new byte[1];
				try {
					while (is.read(by) != -1)
						;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			// Thread.sleep(50);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lock = false;

	}
}

//class Response_Msg extends Thread {
//	Response response;
//	String msg;
//
//	public Response_Msg(Response response, String msg) {
//		this.response = response;
//		this.msg = msg;
//	}
//
//	public void clean_Socket_Input() {
//		byte[] by = new byte[1];
//		try {
//			while (response.is.read(by) != -1)
//				;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public void run() {
//		response.lock = true;
//		try {
//
//			switch (msg) {
//			case "ModifyPassword":
//				User_Msg us1 = (User_Msg) response.ois.readObject();
//				if (ServerMain.sqlmain.Modify_Password(us1.us, us1.msg)) {
//					System.out.println("用户:" + us1.us.getUsername() + "修改密码为:" + us1.msg);
//					response.pw.println("True");
//					response.pw.flush();
//				} else {
//					response.pw.println("False");
//					response.pw.flush();
//				}
//
//				break;
//			case "Add_User":
//				User newUser = (User) response.ois.readObject();
//				System.out.println("用户:" + response.user1.getUsername() + "添加用户:" + newUser.getUsername() + "密码"
//						+ newUser.getPassword() + "新用户权限:" + newUser.getPermission());
//				if (newUser.getPermission().compareTo("0") == 0
//						&& response.user1.getPermission().compareTo("999") != 0) {
//					response.pw.println("Permission Denied");
//					response.pw.flush();
//					break;
//				} else if ((response.user1.getPermission().compareTo("0") != 0)
//						&& response.user1.getPermission().compareTo("999") != 0) {
//					response.pw.println("Permission Denied");
//					response.pw.flush();
//					break;
//				} else {
//					if (Sql_Main.Add_User(newUser)) {
//						response.pw.println("Add User Successfully ");
//						response.pw.flush();
//						break;
//					} else {
//						response.pw.println("Add User Failed");
//						response.pw.flush();
//						break;
//					}
//				}
//				// break;
//			case "Delete_User":
//				// ois=new ObjectInputStream(client.getInputStream());
//				User olduser;
//				olduser = (User) response.ois.readObject();
//				if ((response.user1.getPermission().compareTo("0") != 0
//						|| Sql_Main.Get_Permission(olduser).compareTo("0") == 0)
//						&& response.user1.getPermission().compareTo("999") != 0) {
//					response.pw.println("Permission Denied");
//					response.pw.flush();
//					break;
//				} else {
//					if (Sql_Main.Delete_User(olduser)) {
//						System.out.println(
//								"管理员:" + response.user1.getUsername() + " 删除用户" + olduser.getUsername() + "成功");
//						response.pw.println("Delete User Successfully ");
//						response.pw.flush();
//						break;
//					} else {
//						response.pw.println("Delete User Failed");
//						response.pw.flush();
//						break;
//					}
//				}
//				// break;
//			case "Ban_User":
//				// ois=new ObjectInputStream(client.getInputStream());
//				User newBanuser;
//				newBanuser = (User) response.ois.readObject();
//				if ((response.user1.getPermission().compareTo("0") != 0
//						|| Sql_Main.Get_Permission(newBanuser).compareTo("0") == 0)
//						&& response.user1.getPermission().compareTo("999") != 0) {
//					response.pw.println("Permission Denied");
//					response.pw.flush();
//					break;
//				}
//
//				else {
//					if (Sql_Main.User_BAN(newBanuser)) {
//						System.out.println(
//								"管理员:" + response.user1.getUsername() + " 封禁用户" + newBanuser.getUsername() + "成功");
//						response.pw.println("Ban User Successfully ");
//						response.pw.flush();
//						break;
//					} else {
//						response.pw.println("Ban User Failed");
//						response.pw.flush();
//						break;
//					}
//				}
//				// break;
//			case "Offline_User":
//				// ois=new ObjectInputStream(client.getInputStream());
//				User newOfflineuser;
//				newOfflineuser = (User) response.ois.readObject();
//				if ((response.user1.getPermission().compareTo("0") != 0
//						|| Sql_Main.Get_Permission(newOfflineuser).compareTo("0") == 0)
//						&& response.user1.getPermission().compareTo("999") != 0) {
//					response.pw.println("Permission Denied");
//					response.pw.flush();
//					break;
//				}
//
//				else {
//					if (Sql_Main.User_Offline(newOfflineuser)) {
//						System.out.println("管理员:" + response.user1.getUsername() + " 强制下线用户"
//								+ newOfflineuser.getUsername() + "成功");
//						response.pw.println("Offline User Successfully ");
//						response.pw.flush();
//						break;
//					} else {
//						response.pw.println("Offline User Failed");
//						response.pw.flush();
//						break;
//					}
//				}
//			case "User_Baned_Cancel":
//				// ois=new ObjectInputStream(client.getInputStream());
//				User newCancelBaneduser;
//				newCancelBaneduser = (User) response.ois.readObject();
//				if ((response.user1.getPermission().compareTo("0") != 0
//						|| Sql_Main.Get_Permission(newCancelBaneduser).compareTo("0") == 0)
//						&& response.user1.getPermission().compareTo("999") != 0) {
//					response.pw.println("Permission Denied");
//					response.pw.flush();
//					break;
//				}
//
//				else {
//					if (Sql_Main.User_BANED_Cancel(newCancelBaneduser)) {
//						System.out.println("管理员:" + response.user1.getUsername() + " 解封用户"
//								+ newCancelBaneduser.getUsername() + "成功");
//						response.pw.println("Successfully ");
//						response.pw.flush();
//						break;
//					} else {
//						response.pw.println("Failed");
//						response.pw.flush();
//						break;
//					}
//				}
//			case "Get_TB_MOVIE":
//				response.oos.writeObject(new Get_TB_ArrayList(Sql_Main.Get_Movie()));
//				break;
//			case "Get_TB_Preformance":
//				response.oos.writeObject(new Get_TB_ArrayList(Sql_Main.Get_Preformance()));
//				break;
//			case "Get_TB_Plan":
//				response.oos.writeObject(new Get_TB_ArrayList(Sql_Main.Get_Plan()));
//				break;
//			case "Get_TB_AllUser":
//				// System.out.println(o.getClass()+"===="+response.oos);
//				response.oos.writeObject(new Get_TB_ArrayList(Sql_Main.Get_AllUer()));
//				break;
//
//			case "Change_Ticket":
//				String Plan_ID = response.reader.readLine();
//				String newSeat = response.reader.readLine();
//				if (Sql_Main.Change_Ticket(Plan_ID, newSeat)) {
//					response.pw.println("Successfully");
//					response.pw.flush();
//				} else {
//					response.pw.println("Failed");
//					response.pw.flush();
//				}
//				break;
//			case "Heart":
//				// System.out.println("heart");
//				response.pw.println("Online");
//				response.pw.flush();
//				Thread.sleep(50);
//				
//				// response.heartTime = System.currentTimeMillis();
//				if (Sql_Main.User_Isline(response.user1))
//					response.heartTime = true;
//				else {
//					response.heartTime = false;
//				}
//				break;
//			default:
//				// clean_Socket_Input();
//				byte[] by = new byte[1];
//				try {
//					while (response.is.read(by) != -1)
//						;
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				break;
//			}
//			// Thread.sleep(50);
//		} catch (ClassNotFoundException | IOException | InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		response.lock = false;
//	}
//
//}

class Get_TB_ArrayList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -251076747010815010L;
	/**
	 *
	 */
	private ArrayList<String[]> alist;

	public Get_TB_ArrayList(ArrayList<String[]> alist) {
		this.alist = alist;
	}

	public ArrayList<String[]> getalist() {
		return alist;
	}

	public void setalist(ArrayList<String[]> alist) {
		this.alist = alist;
	}

}