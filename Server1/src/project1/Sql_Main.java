package project1;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.mysql.jdbc.Statement;

public class Sql_Main {
	static Connection conn;
	static Statement sql;
	static ResultSet res;
	static boolean Modify_Password_lock=false;
	static boolean Add_User_lock=false;
	static boolean Delete_User_lock=false;
	static boolean Add_Movie_Lock=false;
	static boolean Change_Ticket_lock=false;
	static boolean Modify_Performance_Seat_lock=false;
	static boolean User_BAN_lock=false;
	static boolean Modify_Plan_lock=false;
	static boolean Del_Plan_lock=false;
	static boolean Del_Movie_lock=false;
	static boolean Del_Performance_lock=false;
	static boolean User_BANED_Cancel_lock=false;
	static boolean Server_Maintenance_lock=false;
	static boolean Server_Maintenance_Fin_lock=false;
	static boolean Add_Sale_lock=false;
	public Connection getConnection()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("SQL Driver loaded");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("SQL Driver Fail");
		}
		try {
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/TABLE","USER","PASSWORD");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	public Sql_Main()
	{
		
		conn=this.getConnection();
		try {
			sql=(Statement) conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public static boolean True_User(User us1)
	{
		try {
			res=sql.executeQuery("select count(*) from tb_User where username=\""
+us1.getUsername()+"\" and password=\""+us1.getPassword()+"\"");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int count=0;
		try {
			while(res.next())
			{
				count =res.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(count==0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	public static boolean True_Username(User us1)
	{
		try {
			res=sql.executeQuery("select count(*) from tb_User where username=\""
+us1.getUsername()+"\"");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int count=0;
		try {
			while(res.next())
			{
				count =res.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(count==0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	public static boolean Modify_Password(User us1,String newPassword)
	{
		if(Modify_Password_lock)return false;
		Modify_Password_lock=true;
		if(!True_User(us1))
		{
			Modify_Password_lock=false;
			return false;
		}
		else
		{
			try {
				sql.executeUpdate("update tb_User set password=\""+newPassword+"\" where username=\""+us1.getUsername()+"\"");
				Modify_Password_lock=false;
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				Modify_Password_lock=false;
				return false;
			}
		}
		
	}
	public static String Get_Permission(User use1)
	{
		try {
			res=sql.executeQuery("select permission from tb_User where username=\""
					+use1.getUsername()+"\"");
			while(res.next())
			{
				return res.getString("permission");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "3";
	}
	public static String Get_Performance_FromID(String Performance_ID)
	{
		try {
			res=sql.executeQuery("select * from tb_Performance where ID=\""
					+Performance_ID+"\"");
			String str="";
			while(res.next())
			{
				str+= res.getString("ID");
				str+="@"+ res.getString("rows");
				str+="@"+ res.getString("columns");
				str+="@"+ res.getString("seat");
			}
			return str;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Error";
	}
	public static boolean Add_User(User newUser)
	{
		if(Add_User_lock)return false;
		Add_User_lock=true;
		if(True_User(newUser))
		{
			Add_User_lock=false;
			return false;
		}
		else
		{
			try {
				sql.executeUpdate("insert into tb_User values (\""+newUser.getUsername()
				+"\",\""+newUser.getPassword()+"\",\""+newUser.getPermission()+"\",\"0\")");
				Add_User_lock=false;
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Add_User_lock=false;
				return false;
			}
		}
	}
	public static boolean Delete_User(User oldUser)
	{
		if(Delete_User_lock)return false;
		Delete_User_lock=true;
		if(!True_Username(oldUser))
		{
			Delete_User_lock=false;
			return false;
		}
		else
		{
			try {
				sql.executeUpdate("delete from tb_User where username=\""+oldUser.getUsername()+"\"");
				Delete_User_lock=false;
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Delete_User_lock=false;
				return false;
			}
		}
	}

	public static void User_Online(User user)
	{
		try {
			sql.executeUpdate("update tb_User set online=\"1\" where username=\""+user.getUsername()+"\"");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static boolean User_Offline(User user)
	{
		try {
			if(User_BANED(user))return true;
			sql.executeUpdate("update tb_User set online=\"0\" where username=\""+user.getUsername()+"\"");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
    public static boolean User_Isline(User user)
    {
    	try {
			res=sql.executeQuery("select count(*) from tb_User where username=\""
+user.getUsername()+"\" and online=\"1\"");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int count=0;
		try {
			while(res.next())
			{
				count =res.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(count==0)
		{
			return false;
		}
		else
		{
			return true;
		}
    }
    public static boolean Server_Isline()

    {
    	try {
			res=sql.executeQuery("select count(*) from tb_Server where online=\"1\"");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int count=0;
		try {
			while(res.next())
			{
				count =res.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(count==0)
		{
			return false;
		}
		else
		{
			return true;
		}
    }

	public static boolean Add_Movie(String moviename, String director, String staring,
			String story, String price) {
		if(Add_Movie_Lock)return false;
		Add_Movie_Lock=true;
		try {
			sql.executeUpdate("insert into tb_Movie values (\"" + moviename
			+ "\",\"" + director
			+ "\",\"" + staring 
			+ "\",\""+story
			+"\",\""+price+"\")");
			Add_Movie_Lock=false;
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Add_Movie_Lock=false;
			return false;
		}
	}
    public static ArrayList<String[]> Get_Movie()
    {
    	ArrayList<String[]> alist=new ArrayList<String[]>();
    	try {
    		
			res=sql.executeQuery("select * from tb_Movie");
			while(res.next())
			{
				
				String Moviename=res.getString("moviename");
				String Director=res.getString("director");
				String Staring=res.getString("staring");
				String Pirce=res.getString("price");
				String story=res.getString("story");
				String[]strings={Moviename,Director,Staring,Pirce,story};
				alist.add(strings);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return alist;
    }
    public static ArrayList<String[]> Get_All_Movie_Name()
    {
    	ArrayList<String[]> alist=new ArrayList<String[]>();
    	try {
    		
			res=sql.executeQuery("select moviename from tb_Movie");
			while(res.next())
			{
				
				String Moviename=res.getString("moviename");
				String[]strings={Moviename};
				alist.add(strings);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return alist;
    }
    public static ArrayList<String[]> Get_Preformance()
    {
    	ArrayList<String[]> alist=new ArrayList<String[]>();
    	try {
    		
			res=sql.executeQuery("select * from tb_Performance");
			while(res.next())
			{
				String str1=res.getString("ID");
				String str2=res.getString("rows");
				String str3=res.getString("columns");
				String str4=res.getString("seat");
				String[]strings={str1,str2,str3,str4};
				alist.add(strings);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return alist;
    }
    public static ArrayList<String[]> Get_All_PreformanceID()
    {
    	ArrayList<String[]> alist=new ArrayList<String[]>();
    	try {
    		
			res=sql.executeQuery("select ID from tb_Performance");
			while(res.next())
			{
				String str1=res.getString("ID");
				String[]strings={str1};
				alist.add(strings);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return alist;
    }
    public static ArrayList<String[]> Get_Plan()
    {
    	ArrayList<String[]> alist=new ArrayList<String[]>();
    	try {
    		
			res=sql.executeQuery("select * from tb_Plan");
			while(res.next())
			{
				
				String str1=res.getString("ID");
				String str2=res.getString("moviename");
				String str3=res.getString("time");
				String str4=res.getString("performance");
				String str5=res.getString("rows");
				String str6=res.getString("columns");
				String str7=res.getString("seat");
				String str8=res.getString("price");
				String[]strings={str1,str2,str3,str4,str5,str6,str7,str8};
				alist.add(strings);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return alist;
    }
    public static ArrayList<String[]> Get_AllUer()
    {
    	ArrayList<String[]> alist=new ArrayList<String[]>();
    	try {
    		
			res=sql.executeQuery("select * from tb_User");
			while(res.next())
			{
				
				String str1=res.getString("username");
				String str2=res.getString("password");
				String str3=res.getString("permission");
				String str4=res.getString("online");
				String[]strings={str1,str2,str3,str4};
				alist.add(strings);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return alist;
    }
    public static ArrayList<String[]> Get_Sale_Data()
    {
    	ArrayList<String[]> alist=new ArrayList<String[]>();
    	try {
    		
			res=sql.executeQuery("select * from tb_Sale");
			while(res.next())
			{
				
				String str1=res.getString("ID");
				String str2=res.getString("planid");
				String str3=res.getString("number");
				String str4=res.getString("price");
				String str5=res.getString("type");
				String str6=res.getString("Date");
				String[]strings={str1,str2,str3,str4,str5,str6};
				alist.add(strings);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return alist;
    }
    public static boolean Change_Ticket(String Plan_ID,String newSeat,String Flag,String FlagSeat)
    {
		if (Change_Ticket_lock)
			return false;
		Change_Ticket_lock = true;
		String seat=Get_Sale_Seat(Plan_ID);
		if(seat==null)
		{
			Change_Ticket_lock = false;
			return false;
		}
		if(Get_Sale_Number(FlagSeat)==0)
		{
			Change_Ticket_lock = false;
			return false;
		}
		if(!Sale_Te(FlagSeat,seat, Flag))
		{
			Change_Ticket_lock = false;
			return false;
		}
		if(Plan_ID==null)
		{
			Change_Ticket_lock = false;
			return false;
		}
		try {
			int result;
			result = sql.executeUpdate("update tb_Plan set seat=\"" + newSeat + "\" where ID=\"" + Plan_ID + "\"");
			if (result != 0)
			{
				if(Add_Sale(Plan_ID, newSeat,Flag,FlagSeat))
				{
					Change_Ticket_lock = false;
					return true;
				}
				Change_Ticket_lock = false;
				return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
			Change_Ticket_lock = false;
		}
		Change_Ticket_lock = false;
		return false;
    }
    public static boolean Sale_Te(String Seat1,String Seat2,String flag)
    {
    	if(flag.compareTo("Sale")==0)
    	{
    		
    		for(int i=0;i<Seat1.length();i++)
    		{
    			if(Seat1.charAt(i)=='1'&&Seat2.charAt(i)=='2')
    				{
    				return false;
    				}
    		}
    		return true;
    	}
    	else if(flag.compareTo("Cancel")==0)
    	{
    		for(int i=0;i<Seat1.length();i++)
    		{
    			if(Seat1.charAt(i)=='1'&&Seat2.charAt(i)=='1')
    			{
        			return false;
    			}
    		}
    		return true;
    	}
    	return false;
    }
    public static int Get_Sale_Type(String str,String Flag)
    {
    	if(Flag.compareTo("Sale")==0)return 1;
    	if(Flag.compareTo("Cancel")==0)return 2;
    	return 0;
    }
    public static String Get_Sale_Seat(String PlanID)
	{
		try {
			res=sql.executeQuery("select seat from tb_Plan where ID=\""+PlanID+"\"");
			while(res.next())
			{
				return res.getString("seat");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
    public static int Get_Sale_Number(String FlagSeat)
    {
    	int number=0;
		for(int i=0;i<FlagSeat.length();i++)
		{
			if(FlagSeat.charAt(i)=='1')number++;
		}
    	return number;
    }
    public static boolean Add_Sale(String Plan_ID,String Seat,String Flag,String FlagSeat)
    {
		if (Add_Sale_lock)
			return false;
		Add_Sale_lock = true;
		try {
			int result;
			String ID=Get_Sale_ID();
			String Price=Get_PlanPrice(Plan_ID);
			if(ID==null||Price==null)
			{
				Add_Sale_lock=false;
				return false;
			}
			Double id=Double.valueOf(ID);
			id++;
			ID=""+id;
			ID=ID.substring(0, ID.indexOf("."));
			String number=""+Get_Sale_Number(FlagSeat);
			String type=""+Get_Sale_Type(Seat,Flag);
			double price= Double.valueOf(Price);
			price*=Get_Sale_Number(FlagSeat);
			SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd");
			String date_Str="1900-1-1";
			date_Str=tempDate.format(new Date());
			result = sql.executeUpdate("insert into tb_Sale values(\""+ID+"\""+",\""
			+Plan_ID+"\","
			+"\""+number+"\",\""+price+"\",\""+type+"\",\""+date_Str+"\")");
			result = sql.executeUpdate("update tb_Sale_ID set ID=\"" + ID + "\"");
			if (result != 0)
			{
				Add_Sale_lock = false;
				return true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
			Add_Sale_lock = false;
		}
		Add_Sale_lock = false;
		return false;
    }
    
    public static String Get_Sale_ID()
	{
		try {
			res=sql.executeQuery("select ID from tb_Sale_ID");
			while(res.next())
			{
				return res.getString("ID");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
    public static String Get_PlanPrice(String PlanID)
	{
		try {
			res=sql.executeQuery("select price from tb_Plan where ID=\""+PlanID+"\"");
			while(res.next())
			{
				return res.getString("price");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
    public static boolean Modify_Performance_Seat(String Performance_ID,
    		String Performace_Rows,
    		String Performance_Columns,
    		String Performance_newSeat)
    {
    	if(Modify_Performance_Seat_lock)return false;
    	Modify_Performance_Seat_lock=true;
    	try {
    		int result;
			result=sql.executeUpdate("update tb_Performance set seat=\""+Performance_newSeat+"\""
					+", Rows=\""+Performace_Rows+"\""
					+", Columns=\""+Performance_Columns+"\""
					+" where ID=\""+Performance_ID+"\"");
			if(result!=0)
			{
				Modify_Performance_Seat_lock=false;
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Modify_Performance_Seat_lock=false;
		}
    	try {
    		int result;
			result=sql.executeUpdate("insert into tb_Performance values(\""+Performance_ID+"\""
					+", \""+Performace_Rows+"\""
					+", \""+Performance_Columns+"\""
					+", \""+Performance_newSeat+"\")");
			if(result!=0)
			{
				Modify_Performance_Seat_lock=false;
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Modify_Performance_Seat_lock=false;
		}
    	Modify_Performance_Seat_lock=false;
    	return false;
    }
    public static boolean User_BAN(User user)
	{
    	if(User_BAN_lock)return false;
    	User_BAN_lock=true;
		try {
			sql.executeUpdate("update tb_User set online=\"2\" where username=\""+user.getUsername()+"\"");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			User_BAN_lock=false;
			return false;
		}
		User_BAN_lock=false;
		return true;
	}
    public static boolean Modify_Plan
    (String Plan_ID,String MovieName,String Time,String PerformanceID,
    		String Rows,String Columns,String Seat,String Price)
	{
    	if(Modify_Plan_lock)return false;
    	Modify_Plan_lock=true;
    	int result;
    	try {
			result=sql.executeUpdate("update tb_Plan set Moviename=\""+MovieName+"\""
					+", Time=\""+Time+"\""
					+", Performance=\""+PerformanceID+"\""
					+", Rows=\""+Rows+"\""
					+", Columns=\""+Columns+"\""
					+", Seat=\""+Seat+"\""
					+", Price=\""+Price+"\""
					+" where ID=\""+Plan_ID+"\"");
			if(result!=0)
				{
				Modify_Plan_lock=false;
				return true;
				}
    		result=sql.executeUpdate("insert into tb_Plan values(\""+Plan_ID+"\""
					+",\""+MovieName+"\""
					+",\""+Time+"\""
					+",\""+PerformanceID+"\""
					+",\""+Rows+"\""
					+",\""+Columns+"\""
					+",\""+Seat+"\""
					+",\""+Price+"\""
					+ ")");
			if(result!=0)
				{
				Modify_Plan_lock=false;
				return true;
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Modify_Plan_lock=false;
		}
    	Modify_Plan_lock=false;
    	return false;
	}
    public static boolean Del_Plan(String Plan_ID)
	{
    	if(Del_Plan_lock)return false;
    	Del_Plan_lock=true;
		try {
			sql.executeUpdate("Delete from tb_Plan where ID=\""+Plan_ID+"\"");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Del_Plan_lock=false;
			return false;
		}
		Del_Plan_lock=false;
		return true;
	}
    public static boolean Del_Movie(String Movie_Name)
   	{
    	if(Del_Movie_lock)return false;
    	Del_Movie_lock=true;
   		try {
   			sql.executeUpdate("Delete from tb_Movie where moviename=\""+Movie_Name+"\"");
   		} catch (SQLException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   			Del_Movie_lock=false;
   			return false;
   		}
   		Del_Movie_lock=false;
   		return true;
   	}
    public static boolean Del_Performance(String Performance_ID)
	{
    	if(Del_Performance_lock)return false;
    	Del_Performance_lock=true;
		try {
			sql.executeUpdate("Delete from tb_Performance where ID=\""+Performance_ID+"\"");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Del_Performance_lock=false;
			return false;
		}
		Del_Performance_lock=false;
		return true;
	}
    
    public static boolean User_BANED_Cancel(User user)
	{
    	if(User_BANED_Cancel_lock)return false;
    	User_BANED_Cancel_lock=true;
		try {
			sql.executeUpdate("update tb_User set online=\"0\" where username=\""+user.getUsername()+"\"");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			User_BANED_Cancel_lock=false;
			return false;
		}
		User_BANED_Cancel_lock=false;
		return true;
	}
    public static boolean Server_Maintenance()
    {
    	if(Server_Maintenance_lock)return false;
    	Server_Maintenance_lock=true;
		try {
			sql.executeUpdate("update tb_Server set online=\"0\"");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Server_Maintenance_lock=false;
			return false;
		}
		Server_Maintenance_lock=false;
		return true;
    }
    public static boolean Server_Maintenance_Fin()
    {
    	if(Server_Maintenance_Fin_lock)return false;
    	Server_Maintenance_lock=true;
		try {
			sql.executeUpdate("update tb_Server set online=\"1\"");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Server_Maintenance_lock=false;
			return false;
		}
		Server_Maintenance_lock=false;
		return true;
    }
    public static boolean User_BANED(User user)
    {
    	try {
			res=sql.executeQuery("select count(*) from tb_User where username=\""
+user.getUsername()+"\" and online=\"2\"");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int count=0;
		try {
			while(res.next())
			{
				count =res.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(count==0)
		{
			return false;
		}
		else
		{
			return true;
		}
    }
}

