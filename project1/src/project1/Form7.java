package project1;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.text.Normalizer.Form;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Form7 {
	public static ImageIcon seat5 = new ImageIcon(Form7.class.getResource("/Images/seat5.png"));
	public static ImageIcon seat6 = new ImageIcon(Form7.class.getResource("/Images/seat6.png"));
	private JFrame frame;
	private Form3 father;
	private String[] data;
	private String Performance_ID;
	private String title;
	private int NewPerformance_ID;
	// Form5.rows = Integer.valueOf(data[1]);
	// Form5.columns = Integer.valueOf(data[2]);
	// Form5.seat = data[3];
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Form7 window = new Form7(null, null, null);
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
	public Form7(String title, Form3 father, String[] data) {
		this.title = title;
		this.data = data;
		this.father = father;
		this.Performance_ID = data[0];
		Form5.rows = Integer.valueOf(data[1]);
		Form5.columns = Integer.valueOf(data[2]);
		Form5.seat = data[3];
		initialize();

	}

	public String getNewPerformance_ID()
	{
		for (String[] str1 : Form3.getmovie.getalist()) {
			if(str1[0].compareTo(""+NewPerformance_ID)==0)
			{
				NewPerformance_ID++;
				return getNewPerformance_ID();
			}
		}
		return ""+NewPerformance_ID;
		
	}
	/**
	 * 
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle(title);
		frame.getContentPane().setLayout(null);
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		FlushForm(panel);
		JLabel label = new JLabel("舞台");
		label.setFont(new Font("宋体", Font.PLAIN, 30));
		label.setBounds(228, 0, 246, 59);
		frame.getContentPane().add(label);

		JLabel lblNewLabel = new JLabel("已选中座位:");
		lblNewLabel.setBounds(618, 90, 69, 15);
		frame.getContentPane().add(lblNewLabel);
		JLabel label_1 = new JLabel("      ");
		label_1.setText("当前演出厅ID:" + Performance_ID);
		label_1.setBounds(618, 20, 166, 39);
		frame.getContentPane().add(label_1);

		Form5.lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		Form5.lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		Form5.lblNewLabel_1.setForeground(Color.GREEN);
		Form5.lblNewLabel_1.setBounds(618, 115, 137, 213);
		frame.getContentPane().add(Form5.lblNewLabel_1);

		JButton button = new JButton("保存信息");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Save_Modify();
//				System.out.println("" + Form5.seat);
				frame.dispose();
				father.Get_Preformance_Data();
				frame.dispose();
			}
		});
		button.setBounds(634, 532, 117, 30);
		frame.getContentPane().add(button);

		JButton button_1 = new JButton("删除一列座位");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Del_Columns(panel);
			}
		});
		button_1.setBounds(634, 479, 117, 30);
		frame.getContentPane().add(button_1);

		JButton button_2 = new JButton("删除一行座位");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Del_Rows(panel);
			}
		});
		button_2.setBounds(634, 439, 117, 30);
		frame.getContentPane().add(button_2);

		JButton button_3 = new JButton("增加一列座位");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Add_Columns(panel);
			}
		});
		button_3.setBounds(634, 387, 117, 30);
		frame.getContentPane().add(button_3);

		JButton button_4 = new JButton("增加一行座位");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Add_Rows(panel);
			}
		});
		button_4.setBounds(634, 347, 117, 30);
		frame.getContentPane().add(button_4);

		JButton button_5 = new JButton("启用选择座位");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!Form5.SelectedseatNull()) {
					JOptionPane.showMessageDialog(null, "启用不能操作已启用的座位!");
					return;
				} else if (Form5.CancelseatNull()) {
					JOptionPane.showMessageDialog(null, "未选中需要启用的座位");
					return;
				}
				changeTicket();
				FlushForm(panel);
			}
		});
		button_5.setBounds(634, 287, 117, 30);
		frame.getContentPane().add(button_5);

		JButton button_6 = new JButton("禁用选择座位");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!Form5.CancelseatNull()) {
					JOptionPane.showMessageDialog(null, "禁用不能操作已禁用的座位!");
					return;
				}
				if (Form5.SelectedseatNull()) {
					JOptionPane.showMessageDialog(null, "未选中需要禁用的座位");
					return;
				}

				changeTicket();
				FlushForm(panel);
			}
		});
		button_6.setBounds(634, 247, 117, 30);
		frame.getContentPane().add(button_6);

		frame.setBounds(100, 100, 800, 600);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				father.Get_Preformance_Data();
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
		});
		frame.setVisible(true);
	}

	public void FlushForm(JPanel panel) {
		Form5.lblNewLabel_1.setText("");
		Form5.Seat_ID = 0;
		Form5.InitSeat();
		repaintPanel(panel);
		// System.out.println("data[0]"+data[0]+"data[1]"+data[1]+"data[2]"+data[2]);
	}
	public void Add_Rows(JPanel panel)
	{
		for(int i=0;i<Form5.columns;i++)
		{
			Form5.seat+="1";
		}
		Form5.rows++;
		Form5.InitSeat();
		FlushForm(panel);
	}
	public void Del_Rows(JPanel panel)
	{
		if(Form5.rows<2)return;
		for(int i=0;i<Form5.columns;i++)
		{
			Form5.seat=Form5.seat.substring(0,Form5.seat.length()-1);
		}
		Form5.rows--;
		Form5.InitSeat();
		FlushForm(panel);
	}
	public void Add_Columns(JPanel panel)
	{
		for(int i=0;i<Form5.rows;i++)
		{
			Form5.seat+="1";
		}
		Form5.columns++;
		Form5.InitSeat();
		FlushForm(panel);
//		int index;
//		for(int i=Form5.rows;i>0;i--)
//		{
//			index=i*Form5.columns;
//			index--;
//			Form5.seat=Form5.seat.substring(0,index)+"1"+Form5.seat.substring(index);
//		}
//		Form5.columns++;
//		Form5.InitSeat();
//		repaintPanel(panel);
	}
	public void Del_Columns(JPanel panel)
	{
		if(Form5.columns<2)return;
		for(int i=0;i<Form5.rows;i++)
		{
			Form5.seat=Form5.seat.substring(0,Form5.seat.length()-1);
		}
		Form5.columns--;
		Form5.InitSeat();
		FlushForm(panel);
	}
	public void repaintPanel(JPanel panel) {
		panel.removeAll();
		panel.setLayout(new GridLayout(0, Form5.columns));
		panel.setBounds(10, 69, 598, 493);
//		System.out.println(Form5.seat);
//		Form5.Seat_ID=0;
		for (int i = 0, m = Form5.seat.length(); i < m; i++) {
			if (Form5.seat.charAt(i) == '2') {
				panel.add(new JBtton_myjb1(Form5.seat3, 2));
			} else if (Form5.seat.charAt(i) == '1') {
				panel.add(new JBtton_myjb1(seat6, 1));
			} else {
				panel.add(new JBtton_myjb1(seat5, 0));
			}
		}
		// panel.setVisible(true);
		panel.repaint();
		// frame.repaint();
		frame.setVisible(true);
	}

	private void ChangeSeat(String newSeat, int flag) {
		if (flag == 0) {
			for (int i = 0, m = Form5.seat.length(); i < m; i++) {
				if (newSeat.charAt(i) == '1') {
					Form5.seat = Form5.seat.substring(0, i) + "0" + Form5.seat.substring(i + 1);
				}
			}
		}
		if (flag == 1) {
			for (int i = 0, m = Form5.seat.length(); i < m; i++) {
				if (newSeat.charAt(i) == '1') {
					Form5.seat = Form5.seat.substring(0, i) + "1" + Form5.seat.substring(i + 1);
				}

			}
		}
	}

	private void changeTicket() {

		if (!Form5.SelectedseatNull()) {
			ChangeSeat(Form5.Selectedseat, 0);
			// System.out.println(Form5.Selectedseat+"aaa");
		} else {
			// System.out.println(Cancelseat);
			ChangeSeat(Form5.Cancelseat, 1);
			// System.out.println(Form5.Cancelseat+"bbb");
		}

	}

	private void Save_Modify() {
		if (Login.lock)
			return;
		Login.lock = true;
		String str = "";
		if(this.Performance_ID.compareTo("新的演出厅")==0)
		{
			NewPerformance_ID=1;
			this.Performance_ID=getNewPerformance_ID();
		}
		try {
			Login.pw.println("Modify_Performance_Seat");
			Login.pw.flush();
			Thread.sleep(50);
			Login.pw.println(this.Performance_ID);
			Login.pw.flush();
			Thread.sleep(50);
			Login.pw.println(Form5.rows);
			Login.pw.flush();
			Thread.sleep(50);
			Login.pw.println(Form5.columns);
			Login.pw.flush();
			Thread.sleep(50);
			Login.pw.println(Form5.seat);
			Login.pw.flush();
			Thread.sleep(50);
			str = Login.reader.readLine();
			JOptionPane.showMessageDialog(null, str);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Login.lock = false;

	}
}

class JBtton_myjb1 extends JButton {
	private int ID;
	boolean flag = true;

	public JBtton_myjb1(ImageIcon icon, int Flag) {
		// this.setText(title);
		this.ID = Form5.Seat_ID;
		Form5.Seat_ID++;
		this.setBackground(Color.WHITE);
		JLabel label_jb = new JLabel();
		label_jb.setSize(Form5.imagewide, Form5.imagehight);
		this.setIcon(icon);
		if (Flag == 2) {
			// this.setEnabled(false);
			return;
		} else if (Flag == 1) {
			this.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if (!Form5.CancelseatNull()) {
						JOptionPane.showMessageDialog(null, "请取消已选中的不可用的座位再进行操作");
						return;
					}
					set_Color1();
					flushlable1();
				}
			});
		} else if (Flag == 0) {
			this.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (!Form5.SelectedseatNull()) {
						JOptionPane.showMessageDialog(null, "请取消已选中的可使用的座位再进行操作");
						return;
					}
					// TODO Auto-generated method stub
					set_Color2();
					flushlable2();
				}
			});
		}

	}

	public void set_Color1() {
		// Form5.button1.setVisible(true);
		// Form5.button2.setVisible(false);
		if (flag) {
			// this.setBackground(Color.GREEN);
			Form5.Selectedseat = Form5.Selectedseat.substring(0, ID) + "1" + Form5.Selectedseat.substring(ID + 1);
			this.setIcon(Form5.seat2);
			flag = false;
		} else {
			// this.setBackground(Color.red);
			Form5.Selectedseat = Form5.Selectedseat.substring(0, ID) + "0" + Form5.Selectedseat.substring(ID + 1);
			this.setIcon(Form7.seat6);
			flag = true;
		}
	}

	public void set_Color2() {
		// Form5.button1.setVisible(false);
		// Form5.button2.setVisible(true);
		if (flag) {
			// this.setBackground(Color.GREEN);
			Form5.Cancelseat = Form5.Cancelseat.substring(0, ID) + "1" + Form5.Cancelseat.substring(ID + 1);
			this.setIcon(Form5.seat2);
			flag = false;
		} else {
			// this.setBackground(Color.red);
			Form5.Cancelseat = Form5.Cancelseat.substring(0, ID) + "0" + Form5.Cancelseat.substring(ID + 1);
			this.setIcon(Form7.seat5);
			flag = true;
		}
	}

	public void flushlable1() {
		String text = "<html>";
		for (int i = 0, m = Form5.Selectedseat.length(); i < m; i++) {
			if (Form5.Selectedseat.charAt(i) == '1') {
				text += (i / Form5.columns + 1) + "行" + (i % Form5.columns + 1) + "列 <br>";
			} else {

			}
		}
		text += "</html>";
		Form5.lblNewLabel_1.setText(text);
	}

	public void flushlable2() {
		String text = "<html>";
		for (int i = 0, m = Form5.Cancelseat.length(); i < m; i++) {
			if (Form5.Cancelseat.charAt(i) == '1') {
				text += (i / Form5.columns + 1) + "行" + (i % Form5.columns + 1) + "列 <br>";
			} else {

			}
		}
		text += "</html>";
		Form5.lblNewLabel_1.setText(text);
	}
}