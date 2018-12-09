package project1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CellEditorListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.mysql.jdbc.log.Log;

import java.awt.ScrollPane;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class Form3 {

	private JFrame frame;
	private JTable table;
	private static String JButton_url = "/Images/Icon.png";
	private ImageIcon JButton_ImageIcon = new ImageIcon(Form3.class.getResource(JButton_url));
	private DefaultTableModel model;
	public static int columns = 0;
	public static int rows = 0;
	public static String seat = "";
	public static Get_TB_ArrayList getmovie;
	public int flag = 0;
	JButton button_2 = new JButton("增加电影");
	JButton button_4 = new JButton("调整演出计划");
	JButton button_5 = new JButton("增加用户");
	JButton button_1 = new JButton("演出计划");
	JButton button = new JButton("演出厅信息");
	JButton btnNewButton_3 = new JButton("销售记录");
	JButton button_3 = new JButton("增加演出厅");
	JButton btnNewButton_2 = new JButton("用户管理");
	JButton btnNewButton = new JButton("影片信息");
	JButton button_6 = new JButton("删除演出计划");
	JButton button_7 = new JButton("删除演出厅");
	JButton button_9 = new JButton("进入维护");
	JButton button_8 = new JButton("维护完毕");
	JButton button_10 = new JButton("删除电影");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Form3 window = new Form3();
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
	public Form3() {
		initialize();

	}
	public Form3 _instance()
	{
		return this;
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.getContentPane().setLayout(null);
		model = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				// if(column==4)return true;
				// else
				return false;
			}

			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 3) {
					// return ImageIcon.class;
				}
				// if(columnIndex==4)
				// {
				// return JButton.class;
				// }
				return Object.class;

			}
		};
		table = new JTable(model);
		frame.setTitle("默笙忆影院管理系统   版本号"+Main._version+"v");
		button_5.setBackground(Color.WHITE);
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddUser();
			}
		});
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Del_Performance();
				Get_Preformance_Data();
			}
		});
		button_5.setBounds(128, 537, 112, 23);
		button_5.setVisible(false);
		button_2.setVisible(false);
		button_3.setVisible(false);
		frame.getContentPane().add(button_5);
		button_10.setVisible(false);
		// table.setDefaultRenderer(JButton.class, new ComboBoxCellRenderer());
		// Vector row=new Vector();
		// Vector colum=new Vector();

		// model.setDataVector(new Object[][]{
		//// {"毛驴历险记","刘吉吉","毛驴","250",new JButton(new ImageIcon(JButton_url))}
		// {"","","","",new JButton(JButton_ImageIcon)}
		// },
		// new String[]{
		// "电影名称","导演","主演","票价",""});
		// table.getColumnModel().getColumn(4).setCellRenderer(new
		// ComboBoxCellRenderer());
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (flag == 0) {
					if (table.getValueAt(table.getSelectedRow(), 4) != null && table.getSelectedColumn() == 4) {
						JOptionPane.showMessageDialog(null, "查看电影" + table.getValueAt(table.getSelectedRow(), 0));
						new Form4(((StringJButton) table.getValueAt(table.getSelectedRow(), 4)).str);
					}
				} else if (flag == 1) {
					if (table.getValueAt(table.getSelectedRow(), 4) != null && table.getSelectedColumn() == 4) {
						JOptionPane.showMessageDialog(null, "查看演出厅" + table.getValueAt(table.getSelectedRow(), 0));
						new Form7("查看演出厅", getthis(),
								((StringJButton) table.getValueAt(table.getSelectedRow(), 4)).str);
					}
				} else if (flag == 2) {
					if (table.getValueAt(table.getSelectedRow(), 8) != null && table.getSelectedColumn() == 8) {
						// JOptionPane.showMessageDialog(null, "获取详细信息");
						// new Form5(Form3.columns, Form3.rows, Form3.seat);
						int index = table.getSelectedRow();
						String[] str = getmovie.getalist().get(index);
//						for (String string : str) {
//							System.out.println(string);
//						}
						new Form5(Integer.valueOf(str[4]), Integer.valueOf(str[5]), str[6], getthis(),
								table.getValueAt(table.getSelectedRow(), 0).toString());
					}
				}else if(flag==3)
				{
					if (table.getValueAt(table.getSelectedRow(), 4) != null && table.getSelectedColumn() == 4)
					{
						JOptionPane.showMessageDialog(null, "操作用户"+table.getValueAt(table.getSelectedRow(), 0).toString());
						new Form2(table.getValueAt(table.getSelectedRow(), 0).toString(),
								table.getValueAt(table.getSelectedRow(), 3).toString(),_instance());
					}
				}
			}
		});
		// table.setBounds(87, 296, 454, 222);

		// frame.getContentPane().add(table);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(128, 88, 662, 446);
		frame.getContentPane().add(scrollPane);

		button_10.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Del_Movie();
			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// model.addRow(new Object[]{"11111","22222","33333","44444",new
				// JButton(new ImageIcon(JButton_url))});
//				btnNewButton_2.setEnabled(true);
//				btnNewButton_3.setEnabled(true);
//				btnNewButton.setEnabled(false);
//				button.setEnabled(true);
//				button_1.setEnabled(true);
////				button_3.setEnabled(true);
//				button_5.setVisible(false);
//				button_2.setVisible(true);
//				button_4.setVisible(false);
//				button_6.setVisible(false);
//				button_3.setVisible(false);
//				button_7.setVisible(false);

				Get_Movie_Data();

			}
		});
		btnNewButton.setBounds(6, 121, 112, 23);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("修改密码");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Form1 form1 = new Form1();
				button_5.setVisible(false);

			}
		});
		btnNewButton_1.setBounds(643, 55, 112, 23);
		frame.getContentPane().add(btnNewButton_1);

		
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
//				btnNewButton_2.setEnabled(false);
//				btnNewButton_3.setEnabled(true);
//				btnNewButton.setEnabled(true);
//				button.setEnabled(true);
//				button_1.setEnabled(true);
//				button_3.setVisible(false);
//				button_5.setVisible(true);
//				button_2.setVisible(false);
//				button_4.setVisible(false);
//				button_6.setVisible(false);
//				button_7.setVisible(false);
				Get_AllUser_Data();
				
			}
		});
		btnNewButton_2.setBounds(6, 88, 112, 23);
		frame.getContentPane().add(btnNewButton_2);

		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Get_Preformance_Data();
//				btnNewButton_2.setEnabled(true);
//				btnNewButton_3.setEnabled(true);
//				btnNewButton.setEnabled(true);
//				button.setEnabled(false);
//				button_1.setEnabled(true);
//				button_3.setVisible(true);
//				button_5.setVisible(false);
//				button_2.setVisible(false);
//				button_4.setVisible(false);
//				button_6.setVisible(false);
//				button_7.setVisible(true);
			}
		});
		button.setBounds(6, 187, 112, 23);
		frame.getContentPane().add(button);

		
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
//				btnNewButton_2.setEnabled(true);
//				btnNewButton_3.setEnabled(true);
//				btnNewButton.setEnabled(true);
//				button.setEnabled(true);
//				button_1.setEnabled(false);
//				button_3.setVisible(false);
//				button_5.setVisible(false);
//				button_2.setVisible(false);
//				button_4.setVisible(true);
//				button_6.setVisible(true);
//				button_7.setVisible(false);
				Get_Plan_Data();
			}
		});
		button_1.setBounds(6, 154, 112, 23);
		frame.getContentPane().add(button_1);

		
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button_5.setVisible(false);
				new Form6(_instance());
			}
		});
		button_2.setBounds(128, 537, 112, 23);
		frame.getContentPane().add(button_2);

		
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnNewButton_2.setEnabled(true);
				btnNewButton_3.setEnabled(true);
				btnNewButton.setEnabled(true);
				button.setEnabled(true);
				button_1.setEnabled(true);
//				button_3.setEnabled(false);
				button_5.setVisible(false);
				new Form7("增加演出厅", getthis(), new String[] { "新的演出厅", "3", "3", "111111111" });
				button_2.setVisible(false);
			}
		});
		button_3.setBounds(128, 537, 112, 23);
		frame.getContentPane().add(button_3);

		
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Form8(_instance());
				button_5.setVisible(false);
				button_2.setVisible(false);
			}
		});
		button_4.setBounds(128, 537, 112, 23);
		frame.getContentPane().add(button_4);

		JLabel label = new JLabel("用户名:");
		label.setBounds(637, 10, 54, 15);
		frame.getContentPane().add(label);

		JLabel label_1 = new JLabel("      ");
		label_1.setText("" + Main.user1.getUsername());
		label_1.setBounds(701, 10, 83, 15);
		frame.getContentPane().add(label_1);

		JLabel label_2 = new JLabel("职务:");
		label_2.setBounds(647, 35, 54, 15);
		frame.getContentPane().add(label_2);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setText("" + Main.user1.getPermission());
		lblNewLabel.setBounds(701, 35, 83, 15);
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Server_Maintenance_Fin();
			}
		});
//		btnNewButton.setVisible(false);
//		btnNewButton_1.setVisible(false);
//		btnNewButton_2.setVisible(false);
//		button.setVisible(false);
//		button_1.setVisible(false);
//		button_2.setVisible(false);
//		button_3.setVisible(false);
//		button_4.setVisible(false);
		button_8.setVisible(false);
		button_9.setVisible(false);
		switch (Main.user1.getPermission()) {
		case "管理员":
			break;
		case "超级管理员":
			button_8.setVisible(true);
			button_9.setVisible(true);
			break;
		case "经理":
			break;
		case "售票员":
			break;
		case "游客":
//			btnNewButton.setVisible(true);
			btnNewButton_1.setVisible(false);
			btnNewButton_2.setVisible(false);
			button.setVisible(false);
			button_1.setVisible(false);
			button_2.setVisible(false);
			button_3.setVisible(false);
			button_4.setVisible(false);
			break;
		}

		frame.getContentPane().add(lblNewLabel);
		
		
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Get_Sale_Data();
			}
		});
		btnNewButton_3.setBounds(6, 220, 112, 23);
		button_2.setVisible(false);
		button_4.setVisible(false);
		frame.getContentPane().add(btnNewButton_3);
		button_6.setVisible(false);
		
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Del_Plan();
			}
		});
		button_6.setBounds(250, 537, 112, 23);
		frame.getContentPane().add(button_6);
		
		button_7.setVisible(false);
		button_7.setBounds(250, 537, 112, 23);
		frame.getContentPane().add(button_7);
		
		
		button_8.setBounds(6, 537, 112, 23);
		frame.getContentPane().add(button_8);
		
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Server_Maintenance();
			}
		});
		button_9.setBounds(6, 511, 112, 23);
		frame.getContentPane().add(button_9);
		button_10.setBounds(250, 537, 112, 23);
		
		frame.getContentPane().add(button_10);
		
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	public void Get_Movie_Data() {
		if(Login.lock)return;
		Login.lock=true;
		btnNewButton_2.setEnabled(true);
		btnNewButton_3.setEnabled(true);
		btnNewButton.setEnabled(false);
		button.setEnabled(true);
		button_1.setEnabled(true);
//		button_3.setEnabled(true);
		button_5.setVisible(false);
		button_2.setVisible(true);
		button_4.setVisible(false);
		button_6.setVisible(false);
		button_3.setVisible(false);
		button_7.setVisible(false);
		button_10.setVisible(true);
		flag = 0;
		model.setDataVector(
				new Object[][] {
						// {"毛驴历险记","刘吉吉","毛驴","250",new JButton(new
						// ImageIcon(JButton_url))}
						{ "", "", "", "", new JButton(JButton_ImageIcon) } },
				new String[] { "电影名称", "导演", "主演", "票价", "" });
		table.getColumnModel().getColumn(4).setCellRenderer(new ComboBoxCellRenderer());
		// table.addMouseListener(new MouseAdapter() {
		// public void mouseClicked(MouseEvent e)
		// {
		// if(table.getValueAt(table.getSelectedRow(),4)!=null&&table.getSelectedColumn()==4)
		// {
		// JOptionPane.showMessageDialog(null,
		// "查看电影"+table.getValueAt(table.getSelectedRow(),0));
		// }
		// }
		// });
		try {
			// System.out.println(model.getRowCount());
			for (int i = 0, m = model.getRowCount(); i < m; i++) {
				model.removeRow(0);
			}
			// System.out.println(model.getRowCount());
			Login.pw.println("Get_TB_MOVIE");
			Login.pw.flush();
			Thread.sleep(50);

			Get_TB_ArrayList getmovie = (Get_TB_ArrayList) Login.ois.readObject();
			Thread.sleep(50);
			for (String[] string : getmovie.getalist()) {
				// String Moviename=res.getString("moviename");
				// String Director=res.getString("director");
				// String Staring=res.getString("staring");
				// String Pirce=res.getString("price");
				model.addRow(
						new Object[] { string[0], string[1], string[2], string[3], new StringJButton(string, "查看信息") });
			}
			// ResultSet res=getmovie.getRes();
			// while(res.next())
			// {
			// String Moviename=res.getString("moviename");
			// String Director=res.getString("director");
			// String Staring=res.getString("staring");
			// String Pirce=res.getString("price");
			// model.addRow(new Object[]{Moviename,Director,Staring,Pirce,new
			// JButton(new ImageIcon(JButton_url))});
			// }

		} catch (IOException | InterruptedException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "未知异常,请重新登录");
			System.exit(0);
		}
//		Login.num++;
//		System.out.println("次数"+Login.num);
		Login.lock=false;
	}
	public ArrayList<String[]> Getmov_Sort(ArrayList<String[]> list)
	{
		for (String[] strings : list) {
			for (String[] string1 : list) {
				if(Integer.valueOf(strings[0])<Integer.valueOf(string1[0]))
				{
					int index1=list.indexOf(strings);
					int index2=list.indexOf(string1);
					list.set(index1,string1);
					list.set(index2, strings);
					strings=string1;
				}
			}
		}
		return list;
	}
	public void Get_Preformance_Data() {
		if(Login.lock)return;
		Login.lock=true;
		btnNewButton_2.setEnabled(true);
		btnNewButton_3.setEnabled(true);
		btnNewButton.setEnabled(true);
		button.setEnabled(false);
		button_1.setEnabled(true);
		button_3.setVisible(true);
		button_5.setVisible(false);
		button_2.setVisible(false);
		button_4.setVisible(false);
		button_6.setVisible(false);
		button_7.setVisible(true);
		button_10.setVisible(false);
		flag = 1;
		model.setDataVector(new Object[][] { { "", "", "", "", new JButton(new ImageIcon(JButton_url)) } },
				new String[] { "演出厅号", "座位行数", "座位列数", "可用座位数量", "" });
		table.getColumnModel().getColumn(4).setCellRenderer(new ComboBoxCellRenderer());
		// table.addMouseListener(new MouseAdapter() {
		// public void mouseClicked(MouseEvent e)
		// {
		//
		// if(table.getValueAt(table.getSelectedRow(),4)!=null&&table.getSelectedColumn()==4)
		// {
		// JOptionPane.showMessageDialog(null,
		// "查看电影"+table.getValueAt(table.getSelectedRow(),0));
		// }
		// }
		// });
		try {
			for (int i = 0, m = model.getRowCount(); i < m; i++) {
				model.removeRow(0);
			}
			Login.pw.println("Get_TB_Preformance");
			Login.pw.flush();
			Thread.sleep(50);
			getmovie = (Get_TB_ArrayList) Login.ois.readObject();
			Thread.sleep(50);
			getmovie.setalist(Getmov_Sort(getmovie.getalist()));
			for (String[] string : getmovie.getalist()) {
				model.addRow(new Object[] { string[0], string[1], string[2], SeatToNumber(string[3], '1'),
						new StringJButton(string, "查看信息") });
			}

		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "未知异常，请重新登录");
			System.exit(0);
			
		}
		Login.lock=false;
	}
	public void Del_Plan() {
		String Plan_ID;
		if((Plan_ID=(String) table.getValueAt(table.getSelectedRow(), 0))==null)return;
		if(Login.lock)return;
		Login.lock=true;
		try {
			Login.pw.println("Del_Plan");
			Login.pw.flush();
			Thread.sleep(50);
			Login.pw.println(Plan_ID);
			Login.pw.flush();
			Thread.sleep(50);
			Plan_ID="Error";
			Plan_ID=Login.reader.readLine();
			JOptionPane.showMessageDialog(null, ""+Plan_ID);
			Login.lock=false;
			Get_Plan_Data();
		} catch ( InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "未知异常，请重新登录");
			System.exit(0);
			
		}
		Login.lock=false;
	}
	public void Del_Performance() {
		String Performance_ID;
		if((Performance_ID=(String) table.getValueAt(table.getSelectedRow(), 0))==null)return;
		if(Login.lock)return;
		Login.lock=true;
		try {
			Login.pw.println("Del_Performance");
			Login.pw.flush();
			Thread.sleep(50);
			Login.pw.println(Performance_ID);
			Login.pw.flush();
			Thread.sleep(50);
			Performance_ID="Error";
			Performance_ID=Login.reader.readLine();
			
			JOptionPane.showMessageDialog(null, ""+Performance_ID);
			
		} catch ( InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "未知异常，请重新登录");
			System.exit(0);
			
		}
		Login.lock=false;
	}
	public void Del_Movie() {
		String Movie_Name;
		if((Movie_Name=(String) table.getValueAt(table.getSelectedRow(), 0))==null)return;
		if(Login.lock)return;
		Login.lock=true;
		try {
			Login.pw.println("Del_Movie");
			Login.pw.flush();
			Thread.sleep(50);
			Login.pw.println(Movie_Name);
			Login.pw.flush();
			Thread.sleep(50);
			Movie_Name="Error";
			Movie_Name=Login.reader.readLine();
			
			JOptionPane.showMessageDialog(null, ""+Movie_Name);
			Login.lock=false;
			Get_Movie_Data();
			
		} catch ( InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "未知异常，请重新登录");
			System.exit(0);
			
		}
		Login.lock=false;
	}
	public void Server_Maintenance() {
		if(Login.lock)return;
		Login.lock=true;
		try {
			Login.pw.println("Server_Maintenance");
			Login.pw.flush();
			Thread.sleep(50);
			String str1="Error";
			str1=Login.reader.readLine();
			JOptionPane.showMessageDialog(null, ""+str1);
			Login.lock=false;
		} catch ( InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "未知异常，请重新登录");
			System.exit(0);
		}
		Login.lock=false;
	}
	public void Server_Maintenance_Fin() {
		if(Login.lock)return;
		Login.lock=true;
		try {
			Login.pw.println("Server_Maintenance_Fin");
			Login.pw.flush();
			Thread.sleep(50);
			String str1="Error";
			str1=Login.reader.readLine();
			JOptionPane.showMessageDialog(null, ""+str1);
			Login.lock=false;
		} catch ( InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "未知异常，请重新登录");
			System.exit(0);
		}
		Login.lock=false;
	}
	public void Get_Plan_Data() {
		if(Login.lock)return;
		Login.lock=true;
		btnNewButton_2.setEnabled(true);
		btnNewButton_3.setEnabled(true);
		btnNewButton.setEnabled(true);
		button.setEnabled(true);
		button_1.setEnabled(false);
		button_3.setVisible(false);
		button_5.setVisible(false);
		button_2.setVisible(false);
		button_4.setVisible(true);
		button_6.setVisible(true);
		button_7.setVisible(false);
		button_10.setVisible(false);
		flag = 2;
		model.setDataVector(
				new Object[][] {
						// {"毛驴历险记","刘吉吉","毛驴","250",new JButton(new
						// ImageIcon(JButton_url))}
						{ "", "", "", "", "", "", "", "", new JButton(new ImageIcon(JButton_url)) } },
				new String[] { "ID", "电影名", "放映时间", "演出厅号", "座位行数", "座位列数", "剩余座位", "票价", "" });
		table.getColumnModel().getColumn(8).setCellRenderer(new ComboBoxCellRenderer());
		// table.addMouseListener(new MouseAdapter() {
		// public void mouseClicked(MouseEvent e)
		// {
		// if(table.getValueAt(table.getSelectedRow(),8)!=null&&table.getSelectedColumn()==8)
		// {
		// JOptionPane.showMessageDialog(null, "获取详细信息");
		// //new Form5(Form3.columns, Form3.rows, Form3.seat);
		// int index=table.getSelectedRow();
		// String[] str=getmovie.getalist().get(index);
		// new Form5(Integer.valueOf(str[4]),Integer.valueOf(str[5]),str[6]);
		// }
		// }
		// });
		try {
			for (int i = 0, m = model.getRowCount(); i < m; i++) {
				model.removeRow(0);
			}
			Login.pw.println("Get_TB_Plan");
			Login.pw.flush();
			Thread.sleep(50);

			getmovie = (Get_TB_ArrayList) Login.ois.readObject();
			Thread.sleep(50);
			getmovie.setalist(Getmov_Sort(getmovie.getalist()));
			for (String[] string : getmovie.getalist()) {
				model.addRow(new Object[] { string[0], string[1], string[2], string[3], string[4], string[5],
						SeatToNumber(string[6], '1'), string[7], new JButton("详细信息") });
			}

		}catch (IOException | ClassNotFoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "未知异常，请重新登录");
			System.exit(0);
		}
		Login.lock=false;
	}
//	public Get_TB_ArrayList readobject()
//	{
//		try {
//			return  (Get_TB_ArrayList) Login.ois.readObject();
//		} catch (ClassNotFoundException | IOException e) {
//			// TODO Auto-generated catch block
//			System.out.println("重试");
//			return readobject();
//		}
//	}
	public void Get_AllUser_Data() {
		//while(true)
		//{
		if(Login.lock)return;
		Login.lock=true;
		btnNewButton_2.setEnabled(false);
		btnNewButton_3.setEnabled(true);
		btnNewButton.setEnabled(true);
		button.setEnabled(true);
		button_1.setEnabled(true);
		button_3.setVisible(false);
		button_5.setVisible(true);
		button_2.setVisible(false);
		button_4.setVisible(false);
		button_6.setVisible(false);
		button_7.setVisible(false);
		button_10.setVisible(false);
		flag = 3;
		model.setDataVector(new Object[][] { { "", "", "", "", new JButton(new ImageIcon(JButton_url)) } },
				new String[] { "用户名", "密码", "用户权限", "用户状态", "" });
		table.getColumnModel().getColumn(4).setCellRenderer(new ComboBoxCellRenderer());
		try {
			for (int i = 0, m = model.getRowCount(); i < m; i++) {
				model.removeRow(0);
			}
			Login.pw.println("Get_TB_AllUser");
			Login.pw.flush();
			Thread.sleep(50);
	 
			 getmovie = (Get_TB_ArrayList) Login.ois.readObject();
//			Get_TB_ArrayList getmovie = readobject();
			Thread.sleep(50);
			for (String[] string : getmovie.getalist()) {
				if(string[2].compareTo("999")==0)continue;
				model.addRow(new Object[] { string[0], string[1], 
						PermissionToString(string[2]),
						OnlineToString(string[3]),
						new StringJButton(string, "操作") });
			}

		} catch (InterruptedException | ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			byte[] by = new byte[1];
//			try {
//				while (Login.is.read(by) != -1)
//					;
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			JOptionPane.showMessageDialog(null, "未知异常，请重新登录");
			System.exit(0);
		}
//		Login.num++;
//		System.out.println("次数"+Login.num);
		Login.lock=false;
		//}
	}
	public void Get_Sale_Data() {
		//while(true)
		//{
		if(Login.lock)return;
		Login.lock=true;
		flag=999;
		btnNewButton_2.setEnabled(true);
		btnNewButton_3.setEnabled(false);
		btnNewButton.setEnabled(true);
		button.setEnabled(true);
		button_1.setEnabled(true);
		button_3.setVisible(false);
		button_5.setVisible(false);
		button_2.setVisible(false);
		button_4.setVisible(false);
		button_6.setVisible(false);
		button_7.setVisible(false);
		button_10.setVisible(false);
		model.setDataVector(new Object[][] { { "", "", "", "" ,""} },
				new String[] {"演出计划ID", "销售数量", "交易金额", "交易类型","交易时间" });
//		table.getColumnModel().getColumn(4).setCellRenderer(new ComboBoxCellRenderer());
		try {
			for (int i = 0, m = model.getRowCount(); i < m; i++) {
				model.removeRow(0);
			}
			Login.pw.println("Get_Sale_Data");
			Login.pw.flush();
			Thread.sleep(50);
			 getmovie = (Get_TB_ArrayList) Login.ois.readObject();
			Thread.sleep(50);
			for (String[] string : getmovie.getalist()) {
				model.addRow(new Object[] { string[1], 
						string[2], 
						string[3],
						TypeToWord(string[4]),
						string[5]});
			}

		} catch (InterruptedException | ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "未知异常，请重新登录");
			System.exit(0);
		}
		Login.lock=false;
		//}
	}
	private String TypeToWord(String Type)
	{
		if(Type.compareTo("1")==0)
		{
			return "售票";
		}
		if(Type.compareTo("2")==0)
		{
			return "退票";
		}
		return "未知类型";
	}
	private int SeatToNumber(String str, char a) {
		int count = 0;
		for (int i = 0, m = str.length(); i < m; i++) {
			if (str.charAt(i) == a)
				count++;
		}
		return count;
	}

	public Form3 getthis() {
		return this;
	}
	public String PermissionToString(String str)
	{
		switch(str)
		{
		case "0":
			return "管理员";
		case "1":
			return "经理";
		case "2":
			return  "售票员";
		case "3":
			return "游客";
		}
		return "null";
	}
	public String OnlineToString(String str)
	{
		switch(str)
		{
		case "0":
			return "离线";
		case "1":
			return "在线";
		case "2":
			return  "已被封禁";
		}
		return "null";
	}
}

class ComboBoxCellRenderer implements TableCellRenderer {
	private Color unselectedForeground;
	private Color unselectedBackground;
	private static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// noFocusBorder
		JButton button = (JButton) value;
		if (isSelected) {
			button.setForeground(table.getSelectionForeground());
			button.setBackground(table.getSelectionBackground());
		} else {
			button.setForeground((unselectedForeground != null) ? unselectedForeground : table.getForeground());
			button.setBackground((unselectedBackground != null) ? unselectedBackground : table.getBackground());
		}
		button.setFont(table.getFont());
		if (hasFocus) {
			button.setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
			if (!isSelected && table.isCellEditable(row, column)) {
				Color col;
				col = UIManager.getColor("Table.focusCellForeground");
				if (col != null) {
					button.setForeground(col);
				}
				col = UIManager.getColor("Table.focusCellBackground");
				if (col != null) {
					button.setBackground(col);
				}
			}
		} else {
			button.setBorder(noFocusBorder);
		}
		return button;

		// TODO Auto-generated method stub
		// return null;
	}

}

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

class StringJButton extends JButton {
	String[] str;

	// String title;
	public StringJButton(String[] str, String title) {
		super();
		this.setText(title);
		this.str = str;
	}
}