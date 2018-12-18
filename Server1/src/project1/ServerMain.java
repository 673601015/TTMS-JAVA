package project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.print.attribute.standard.MediaSize.Other;

import com.mysql.fabric.Server;

public class ServerMain {
	private String Server_Version = "0.8.1";
	private String Client_Version = "0.8.1";
	private int port = 8888;
	private ServerSocket server;
	private Socket socket;
	private InputStream is;
	private ObjectInputStream ois;
	public static ArrayList<ObjectOutputStream> oosList;
	private PrintWriter pw;
	private User us1;
	public static Sql_Main sqlmain;

	void getServer() {
		try {
			oosList = new ArrayList<ObjectOutputStream>();
			server = new ServerSocket(port);
			System.out.println("Server start Successfully");
			sqlmain = new Sql_Main();
			System.out.println("Server Version:" + Server_Version);
			System.out.println("Client Version:" + Client_Version);
			while (true) {
				System.out.println("Waiting");
				socket = server.accept();
				is = socket.getInputStream();
				ois = new ObjectInputStream(is);
				us1 = (User) ois.readObject();
				// Thread.sleep(50);
				System.out.println("\n username:" + us1.getUsername() + "\n password:" + us1.getPassword());
				pw = new PrintWriter(socket.getOutputStream());
				boolean flag = true;
				String[] version = us1.getUsername().split("@");
				us1.setUsername(version[0]);
				us1.setPermission("3");
				if (sqlmain.True_User(us1)) {
					us1.setPermission(Sql_Main.Get_Permission(us1));
					// System.out.println(us1.getPermission());

					if (us1.getPermission().compareTo("999") != 0 && !Sql_Main.Server_Isline()) {
						System.out.println(us1.getPermission());
						pw.println("Server Maintenance");
						pw.flush();
						System.out.println("Server Maintenance");
					} else {
						if (version.length < 2) {
							flag = false;
						} else {
							if (version[1].compareTo(Client_Version) != 0) {
								flag = false;
							}
						}
						if (!flag) {
							pw.println("The version is too old,Please Update");
							pw.flush();
							System.out.println("Version Error");
						} else if (sqlmain.True_User(us1)) {
							if (Sql_Main.User_BANED(us1)) {
								pw.println("This User BANED");
								pw.flush();
								System.out.println("This User BANED");
							} else if (Sql_Main.User_Isline(us1)) {
								pw.println("This User online");
								pw.flush();
								System.out.println("This User online");
							} else {
								us1.setPermission(Sql_Main.Get_Permission(us1));
								pw.println(us1.getPermission());
								pw.flush();
								Sql_Main.User_Online(us1);
								new Response(socket, us1, ois).start();
							}

						} else {
							pw.println("Login Failed");
							pw.flush();
						}
					}
				}
				else
				{
					pw.println("Username or Password Error");
					pw.flush();
					System.out.println("Username or Password Error");
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ServerMain servermain = new ServerMain();
		servermain.getServer();
	}
}