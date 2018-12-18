package project1;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.mysql.jdbc.log.Log;
//import com.sun.javafx.scene.traversal.TopMostTraversalEngine;

import java.awt.Font;
import java.awt.Frame;


public class Form5 {
	public static ImageIcon seat1=new ImageIcon(Form5.class.getResource("/Images/seat1.png"));
	public static ImageIcon seat2=new ImageIcon(Form5.class.getResource("/Images/seat2.png"));
	public static ImageIcon seat3=new ImageIcon(Form5.class.getResource("/Images/seat3.png"));
	public static ImageIcon seat4=new ImageIcon(Form5.class.getResource("/Images/seat4.png"));
//	private ImageIcon seat5=new ImageIcon(Form3.class.getResource("/Images/seat4.png"));
	public static int imagewide=65;
	public static int imagehight=65;
	private JFrame frame = new JFrame();
	public static int columns=2;
	public static int rows=2;
	public static String seat="1012";
	public static JLabel lblNewLabel_1 = new JLabel("");
	public static int Seat_ID=0;
	public static String Initseat="0000";
	public static String Selectedseat="0000";
	public static String Cancelseat="0000";
	public static JButton button1 = new JButton("出售选中座位");
	public static JButton button2 = new JButton("退票选中座位");
	private String Plan_ID="";
	private Form3 father;
	private boolean isflush=false;
	private boolean waiting=false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Form5 window = new Form5(2,2,"1012",null,null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the application.
	 */

	public Form5(int rows,int columns, String seat,Form3 father,String Plan_ID) {
		//super();
		this.columns = columns;
		this.rows = rows;
		this.seat = seat;
		this.father=father;
		this.Plan_ID=Plan_ID;
		Seat_ID=0;
		InitSeat();
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame.setTitle("选择座位");
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		
		
		frame.getContentPane().add(panel);
		repaintPanel(panel);
		JLabel label = new JLabel("舞台");
		label.setFont(new Font("宋体", Font.PLAIN, 30));
		label.setBounds(228, 0, 246, 59);
		frame.getContentPane().add(label);
		
		JLabel lblNewLabel = new JLabel("已选中座位:");
		lblNewLabel.setBounds(618, 90, 69, 15);
		frame.getContentPane().add(lblNewLabel);
		
		
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1.setForeground(Color.GREEN);
		lblNewLabel_1.setBounds(618, 115, 137, 213);
		frame.getContentPane().add(lblNewLabel_1);
		
		
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!CancelseatNull())
				{
					JOptionPane.showMessageDialog(null, "售票不能操作已出售票!");
					return;
				}
				else if(SelectedAndCancel())
				{
					JOptionPane.showMessageDialog(null, "未选中需要购买的票");
					return;
				}
				changeTicket("Sale");
				FlushForm(panel);
				return ;
			}
		});
		button1.setBounds(631, 364, 118, 30);
		frame.getContentPane().add(button1);
		
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!SelectedseatNull())
				{
					JOptionPane.showMessageDialog(null, "退票不能操作未出售的票!");
					return;
				}
				else if(CancelseatNull())
				{
					JOptionPane.showMessageDialog(null, "未选中需要退的票");
					return;
				}
				changeTicket("Cancel");
				FlushForm(panel);
				return;
			}
		});
		button2.setBounds(631, 409, 118, 30);
		frame.getContentPane().add(button2);
		
		JLabel label_1 = new JLabel("      ");
		label_1.setText("当前演出计划ID:"+Plan_ID);
		label_1.setBounds(618, 20, 166, 39);
		frame.getContentPane().add(label_1);
		button1.setVisible(true);
		button2.setVisible(true);
		FlushForm(panel);
		frame.setBounds(100, 100, 800, 600);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
				if(father!=null)father.Get_Plan_Data();
				frame.dispose();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

//		frame.setVisible(true);
	}
	public void FlushForm(JPanel panel)
	{
		if(isflush)return;
		isflush=true;
		while(waiting);
		lblNewLabel_1.setText("");
		Seat_ID=0;
		InitSeat();
		repaintPanel(panel);
		isflush=false;
	}
	public void repaintPanel(JPanel panel)
	{
		panel.removeAll();
		panel.setLayout(new GridLayout(0, columns));
		panel.setBounds(10, 69, 598, 493);
		for (int i = 0,m=seat.length(); i<m; i++) {
			if(seat.charAt(i)=='2')
			{
				panel.add(new JBtton_myjb(seat3, 2));
			}
			else if(seat.charAt(i)=='1')
			{
				panel.add(new JBtton_myjb(seat1, 1));
			}
			else
			{
				panel.add(new JBtton_myjb(null, 0));
			}
		}
//		panel.setVisible(true);
		panel.repaint();
		frame.setVisible(true);
	}
	public static void InitSeat()
	{
		Selectedseat="";
		Cancelseat="";
		for(int i=0,m=seat.length();i<m;i++)
		{
			Selectedseat+='0';
		}
		Cancelseat=Selectedseat;
		Initseat=Selectedseat;
	}
	public boolean SelectedAndCancel()
	{
		boolean flag=false;
		for(int i=0,m=Cancelseat.length();i<m;i++)
		{
			if(Selectedseat.charAt(i)=='1')flag=true;
			if(Cancelseat.charAt(i)=='1'&&flag)return true;
		}
		return false;
	}
	public static boolean SelectedseatNull()
	{
		for(int i=0,m=Selectedseat.length();i<m;i++)
		{
			if(Selectedseat.charAt(i)=='1')return false;
		}
		return true;
	}
	public static boolean CancelseatNull()
	{
		for(int i=0,m=Cancelseat.length();i<m;i++)
		{
			if(Cancelseat.charAt(i)=='1')return false;
		}
		return true;
	}
	private void changeTicket(String Flag)
	{
		if(Login.lock)return;
		Login.lock=true;
		waiting=true;
		String str="";
		if(!SelectedseatNull())
		{
			str=Selectedseat;
			ChangeSeat(Selectedseat, 1);
		}
		else
		{
//			System.out.println(Cancelseat);
			str=Cancelseat;
			ChangeSeat(Cancelseat, 2);
		}
		try {
//			System.out.println(str);
			Login.pw.println("Change_Ticket");
			Login.pw.flush();
			Thread.sleep(50);
			Login.pw.println(Plan_ID);
			Login.pw.flush();
			Thread.sleep(50);
			Login.pw.println(seat);
			Login.pw.flush();
			Thread.sleep(50);
			Login.pw.println(Flag);
			Login.pw.flush();
			Thread.sleep(50);
			Login.pw.println(str);
			Login.pw.flush();
			Thread.sleep(50);
			str=Login.reader.readLine();
			JOptionPane.showMessageDialog(null, str);
			Login.lock=false;
			waiting=false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void ChangeSeat(String newSeat,int flag)
	{
		if(flag==1)
		{
			for(int i=0,m=seat.length();i<m;i++)
			{
				if(newSeat.charAt(i)=='1')
				{
					seat=seat.substring(0,i)+"2"+seat.substring(i+1);
				}
			}
		}
		if(flag==2)
		{
			for(int i=0,m=seat.length();i<m;i++)
			{if(newSeat.charAt(i)=='1')
			{
				seat=seat.substring(0,i)+"1"+seat.substring(i+1);
			}
				
			}
		}
	}
}
class JBtton_myjb extends JButton
{
	private int ID;
	boolean flag=true;
	public JBtton_myjb(ImageIcon icon,int Flag)
	{
		//this.setText(title);
		this.ID=Form5.Seat_ID;
		Form5.Seat_ID++;
		this.setBackground(Color.WHITE);
		JLabel label_jb=new JLabel();
		label_jb.setSize(Form5.imagewide, Form5.imagehight);
		this.setIcon(icon);
		if(Flag==0)
		{
			this.setEnabled(false);
			return;
		}
		else if(Flag==1)
		{
			this.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(!Form5.CancelseatNull())
					{
						JOptionPane.showMessageDialog(null, "请取消已选中的已出售的座位再进行操作");
						return;
					}
					set_Color1();
					flushlable1();
				}
			});
		}
		else if(Flag==2)
		{
			this.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if(!Form5.SelectedseatNull())
					{
						JOptionPane.showMessageDialog(null, "请取消已选中的未出售的座位再进行操作");
						return;
					}
					// TODO Auto-generated method stub
					set_Color2();
					flushlable2();
				}
			});
		}
		
	}
	public void set_Color1()
	{
		if(flag)
		{
			Form5.Selectedseat=Form5.Selectedseat.substring(0,ID)+"1"+Form5.Selectedseat.substring(ID+1);
			this.setIcon(Form5.seat2);
			flag=false;
		}
		else
		{
			Form5.Selectedseat=Form5.Selectedseat.substring(0,ID)+"0"+Form5.Selectedseat.substring(ID+1);
			this.setIcon(Form5.seat1);
			flag=true;
		}
	}
	public void set_Color2()
	{
		if(flag)
		{
			Form5.Cancelseat=Form5.Cancelseat.substring(0,ID)+"1"+Form5.Cancelseat.substring(ID+1);
			this.setIcon(Form5.seat2);
			flag=false;
		}
		else
		{
			Form5.Cancelseat=Form5.Cancelseat.substring(0,ID)+"0"+Form5.Cancelseat.substring(ID+1);
			this.setIcon(Form5.seat3);
			flag=true;
		}
	}
	public void flushlable1()
	{
		String text="<html>";
		for(int i=0,m=Form5.Selectedseat.length();i<m;i++)
		{
			if(Form5.Selectedseat.charAt(i)=='1')
			{
				text+=(i/Form5.columns+1)+"行"+(i%Form5.columns+1)+"列 <br>";
			}
			else
			{
				
			}
		}
		text+="</html>";
		Form5.lblNewLabel_1.setText(text);
	}
	public void flushlable2()
	{
		String text="<html>";
		for(int i=0,m=Form5.Cancelseat.length();i<m;i++)
		{
			if(Form5.Cancelseat.charAt(i)=='1')
			{
				text+=(i/Form5.columns+1)+"行"+(i%Form5.columns+1)+"列 <br>";
			}
			else
			{
				
			}
		}
		text+="</html>";
		Form5.lblNewLabel_1.setText(text);
	}
}