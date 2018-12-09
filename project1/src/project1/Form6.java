package project1;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class Form6 {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextArea textField_4;
	private JTextArea textField_5;
	private Get_TB_ArrayList All_MovieName;
	Form3 father;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Form6 window = new Form6(null);
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
	public Form6(Form3 father) {
		this.father = father;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("增加电影");
		frame.setBounds(100, 100, 400, 361);
		frame.getContentPane().setLayout(null);
		// JFileChooser jfchooser=new JFileChooser();
		// jfchooser.setBounds(290, 12, 20, 21);
		// frame.getContentPane().add(jfchooser);
		JLabel lblNewLabel = new JLabel("电影名称:");
		lblNewLabel.setBounds(40, 40, 65, 15);

		frame.getContentPane().add(lblNewLabel);

		JLabel label = new JLabel("导演:");
		label.setBounds(40, 90, 54, 15);
		frame.getContentPane().add(label);

		JLabel label_1 = new JLabel("主演");
		label_1.setBounds(40, 115, 54, 15);
		frame.getContentPane().add(label_1);

		JLabel label_2 = new JLabel("参考票价:");
		label_2.setBounds(40, 65, 65, 15);
		frame.getContentPane().add(label_2);

		JLabel label_3 = new JLabel("剧情简介:");
		label_3.setBounds(40, 171, 65, 15);
		frame.getContentPane().add(label_3);

		JLabel label_4 = new JLabel("上传海报:");
		label_4.setBounds(40, 15, 65, 15);
		frame.getContentPane().add(label_4);

		textField = new JTextField();
		textField.setBounds(104, 12, 162, 21);
		textField.setEditable(false);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("选择文件");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfchooser = new JFileChooser(".");
				jfchooser.setFileFilter(new FileFilter() {

					@Override
					public String getDescription() {
						// TODO Auto-generated method stub
						return "*.png,*gif";
					}

					@Override
					public boolean accept(File f) {
						// TODO Auto-generated method stub
						return f.isDirectory() || f.getName().toLowerCase().endsWith(".png")
								|| f.getName().toLowerCase().endsWith(".gif");
					}
				});
				jfchooser.setDialogTitle("选择图片文件");
				int returnVal = jfchooser.showOpenDialog(null);
				if (JFileChooser.APPROVE_OPTION == returnVal) {
					textField.setText(jfchooser.getSelectedFile().getPath());
				}
			}
		});
		btnNewButton.setBounds(273, 11, 26, 23);
		frame.getContentPane().add(btnNewButton);

		textField_1 = new JTextField();
		// textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(104, 37, 280, 21);
		frame.getContentPane().add(textField_1);

		textField_2 = new JTextField();
		// textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setBounds(104, 62, 280, 21);
		frame.getContentPane().add(textField_2);

		textField_3 = new JTextField();
		// textField_3.setEditable(false);
		textField_3.setColumns(10);
		textField_3.setBounds(104, 87, 280, 21);
		frame.getContentPane().add(textField_3);

		textField_4 = new JTextArea();
		// textField_4.setEditable(false);
		textField_4.setColumns(10);
		textField_4.setBounds(104, 112, 280, 49);
		frame.getContentPane().add(textField_4);

		textField_5 = new JTextArea();
		textField_5.setBounds(104, 168, 280, 114);
		frame.getContentPane().add(textField_5);
		textField_5.setColumns(10);

		JButton btnNewButton_1 = new JButton("增加电影");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (TextisNULL()) {
					JOptionPane.showMessageDialog(null, "不能输入空值");
					return;
				}
				else if(!isPrice(textField_2.getText().toString()))
				{
					JOptionPane.showMessageDialog(null, "票价输入错误");
					return;
				}
				else if(ExMovieName(textField_1.getText().toString()))
				{
					JOptionPane.showMessageDialog(null, "已存在的电影名");
					return;
				}
				
				else {
					Add_Movie();
				}
			}
		});
//		textField_5.setHorizontalAlignment(JTextField.TOP);
//		textField_5.setHorizontalAlignment(JTextField.LEFT);
//		textField_5.setHorizontalAlignment(JTextField.LEADING);
		btnNewButton_1.setBounds(124, 292, 93, 23);
		frame.getContentPane().add(btnNewButton_1);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
	public boolean isPrice(String date_str){
		{	
			try {   
			   float fl=Float.valueOf(date_str);
			   if(fl<=0)return false;
			   return true;   
			} catch (Exception ex){
			    //ex.printStackTrace();  
			    return false;			}  
		}
	}
	private void Get_All_Movie_Name() {
		if (Login.lock)
			return;
		Login.lock = true;
		try {
			Login.pw.println("Get_All_Movie_Name");
			Login.pw.flush();
			Thread.sleep(50);

			All_MovieName = (Get_TB_ArrayList) Login.ois.readObject();
			Thread.sleep(50);

		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "未知异常，请重新登录");
			System.exit(0);
		}
		Login.lock = false;
	}

	public boolean TextisNULL() {
		// if(textField==null)
		// {
		// return true;
		// }
		if (textField_1 == null) {
			return true;
		}
		if (textField_2 == null) {
			return true;
		}
		if (textField_3 == null) {
			return true;
		}
		if (textField_4 == null) {
			return true;
		}
		if (textField_5 == null) {
			return true;
		}
		return false;
	}

	public boolean ExMovieName(String moviename)
	{
		Get_All_Movie_Name();
		for (String[] str : All_MovieName.getalist()) {
			if(str[0].compareTo(moviename)==0)return true;
		}
		return false;
	}
	public void Add_Movie() {
		String movieName = textField_1.getText().toString();
		String director = textField_3.getText().toString();
		String staring = textField_4.getText().toString();
		String story = textField_5.getText().toString();
		String price = textField_2.getText().toString();
		String str = movieName + "@" + director + "@" + staring + "@" + story + "@" + price;
//		System.out.println(str);
		if (Login.lock)
			return;
		Login.lock = true;
		try {
			Login.pw.println("Add_Movie");
			Login.pw.flush();
			Login.pw.println(str);
			Login.pw.flush();
			str = "Error";
			str = Login.reader.readLine();
			JOptionPane.showMessageDialog(null, "" + str);
			Login.lock=false;
			father.Get_Movie_Data();
			frame.dispose();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Login.lock=false;
	}
}
