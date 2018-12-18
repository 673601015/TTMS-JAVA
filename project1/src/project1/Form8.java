package project1;

import java.awt.Choice;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;

public class Form8 {

	private JFrame frame;
	private JTextField textField;
	private Get_TB_ArrayList All_PerformanceID;
	private Get_TB_ArrayList All_MovieName;
	private int NewPlanID=1;
	private String NewPlanID_str;
	private boolean GetNew_PlanID=false;
	JComboBox comboBox = new JComboBox();
	JComboBox comboBox_1 = new JComboBox();
	JComboBox comboBox_2 = new JComboBox();
	private JTextField textField_1;
	private Form3 father;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Form8 window = new Form8(null);
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
	public Form8(Form3 father) {
		this.father=father;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("调整演出计划");
		frame.getContentPane().setLayout(null);
		JButton button_2 = new JButton("保存修改");
		JLabel lblNewLabel = new JLabel("放映时间:");
		
		lblNewLabel.setBounds(10, 131, 70, 15);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("演出厅ID:");
		lblNewLabel_1.setBounds(10, 69, 70, 15);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("增加演出计划");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(GetNew_PlanID)return;
				GetNew_PlanID=true;
				NewPlanID=1;
				NewPlanID_str=GetNewPlanID();
				comboBox.addItem(NewPlanID+"(新演出计划)");
			}
		});
		btnNewButton.setBounds(194, 239, 114, 30);
		frame.getContentPane().add(btnNewButton);
		
		JLabel label = new JLabel("选择电影:");
		label.setBounds(10, 100, 64, 15);
		frame.getContentPane().add(label);
		
		
		comboBox.setBounds(90, 35, 131, 21);
		frame.getContentPane().add(comboBox);
		
		
		comboBox_1.setBounds(90, 66, 131, 21);
		frame.getContentPane().add(comboBox_1);
		
		textField = new JTextField();
		textField.setBounds(90, 128, 131, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton button = new JButton("读取信息");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton.setEnabled(true);
				button_2.setEnabled(true);
				comboBox.removeAllItems();
				Get_All_Movie_Name();
				Get_All_PreformanceID();
				for (String[] str1 : Form3.getmovie.getalist()) {
					comboBox.addItem(str1[0]);
				}
			}
		});
		comboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange()==ItemEvent.SELECTED)
				{
					comboBox_1.removeAllItems();
					comboBox_2.removeAllItems();
					String str=(String) comboBox.getSelectedItem();
					String PerformanceID = "";
					String MovieName="";
					String Time="";
					String Price="";
					for (String[] str1 : Form3.getmovie.getalist()) {
						if(str1[0].compareTo(str)==0)
						{
							PerformanceID=str1[3];
							MovieName=str1[1];
							Time=str1[2];
							Price=str1[7];
						}
					}
					for (String[] str1 : All_PerformanceID.getalist()) {
						if(str1[0].compareTo(PerformanceID)==0)
						{
							comboBox_1.addItem(str1[0]+"(当前演出厅)");
							comboBox_1.setSelectedItem(str1[0]+"(当前演出厅)");
						}
						else comboBox_1.addItem(str1[0]);
					}
					for (String[] str1 : All_MovieName.getalist()) {
						if(str1[0].compareTo(MovieName)==0)
							{
							
							comboBox_2.addItem(str1[0]+"(当前电影)");
							comboBox_2.setSelectedItem(str1[0]+"(当前电影)");
							}
						else comboBox_2.addItem(str1[0]);
					}
					textField.setText(Time);
					textField_1.setText(Price);
				}
			}
		});
		button.setBounds(291, 33, 93, 25);
		frame.getContentPane().add(button);
		
		JLabel lblid = new JLabel("演出计划ID:");
		lblid.setBounds(10, 38, 82, 15);
		frame.getContentPane().add(lblid);
		
		
		comboBox_2.setBounds(90, 97, 186, 21);
		frame.getContentPane().add(comboBox_2);
		
		
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Save();
			}
		});
		button_2.setBounds(33, 239, 114, 30);
		frame.getContentPane().add(button_2);
		
		JLabel label_1 = new JLabel("票价:");
		label_1.setBounds(10, 170, 70, 15);
		frame.getContentPane().add(label_1);
		btnNewButton.setEnabled(false);
		button_2.setEnabled(false);
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(90, 167, 131, 21);
		frame.getContentPane().add(textField_1);
		frame.setBounds(100, 100, 400, 320);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
	private String GetNewPlanID()
	{
		for (String[] str1 : Form3.getmovie.getalist()) {
			if(str1[0].compareTo(""+NewPlanID)==0)
			{
				NewPlanID++;
				return GetNewPlanID();
			}
		}
		return ""+NewPlanID;
	}
	public boolean isDate(String date_str){
		{	
			boolean baba =true;
			try {   
			    SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd");  
			    format.setLenient(false);  
			    Date date = format.parse(date_str);  
			    return baba;
			      
			} catch (Exception ex){
			    //ex.printStackTrace();  
			    return false;			}  
		}
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

	private void Get_All_Movie_Name()
	{
		if(Login.lock)return;
		Login.lock=true;
		try {
			Login.pw.println("Get_All_Movie_Name");
			Login.pw.flush();
			Thread.sleep(50);

			All_MovieName = (Get_TB_ArrayList) Login.ois.readObject();
			Thread.sleep(50);

		}catch (IOException | ClassNotFoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "未知异常，请重新登录");
			System.exit(0);
		}
		Login.lock=false;
	}
	private void Get_All_PreformanceID()
	{
		if(Login.lock)return;
		Login.lock=true;
		try {
			Login.pw.println("Get_All_PreformanceID");
			Login.pw.flush();
			Thread.sleep(50);

			All_PerformanceID = (Get_TB_ArrayList) Login.ois.readObject();
			Thread.sleep(50);

		}catch (IOException | ClassNotFoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "未知异常，请重新登录");
			System.exit(0);
		}
		Login.lock=false;
	}
	private void Save()
	{
		
		String ID,MovieName,Time,PerformanceID,Rows,Columns,Seat,Price;
		String[] str1;
		Time=textField.getText().toString();
		Price=textField_1.getText().toString();
		if(!isDate(Time))
		{
			JOptionPane.showMessageDialog(null, "日期格式错误,请检查输入");
			return;
		}
		if(!isPrice(Price))
		{
			JOptionPane.showMessageDialog(null, "票价输入错误,请检查输入");
			return;
		}
		if(GetNew_PlanID)ID=NewPlanID_str;
		else ID=(String) comboBox.getSelectedItem();
		PerformanceID=(String)comboBox_1.getSelectedItem();
		str1=PerformanceID.split("\\(");
		PerformanceID=str1[0];
		MovieName=(String) comboBox_2.getSelectedItem();
		str1=MovieName.split("\\(");
		MovieName=str1[0];
		
		JOptionPane.showMessageDialog(null, "MovieName:"+str1[0]);
		String Performance_Data=Get_Performance_FromID(PerformanceID);
		if(Performance_Data==null)
		{
			JOptionPane.showMessageDialog(null, "保存失败请稍后再试");
			return;
		}
		else
		{
			if(Login.lock)return;
			Login.lock=true;
			try {
				Login.pw.println("Modify_Plan");
				Login.pw.flush();
				Thread.sleep(50);
				Performance_Data=ID+"@"+MovieName+"@"+Time+"@"+Performance_Data+"@"+Price;
				Login.pw.println(Performance_Data);
				Login.pw.flush();
				Thread.sleep(50);
				String str=Login.reader.readLine();
				JOptionPane.showMessageDialog(null, ""+str);
			} catch (InterruptedException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Login.lock=false;
			father.Get_Plan_Data();
			frame.dispose();
			return;
		}
		
	}
	private String Get_Performance_FromID(String PerformanceID)
	{
		if(Login.lock)return null;
		Login.lock=true;
		try {
			Login.pw.println("Get_Performance_FromID");
			Login.pw.flush();
			Thread.sleep(50);
			Login.pw.println(PerformanceID);
			Login.pw.flush();
			Thread.sleep(50);
			String data_str=Login.reader.readLine();
			Thread.sleep(50);
			Login.lock=false;
			if(data_str.compareTo("Error")==0)return null;
			return data_str;
		}catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "未知异常，请重新登录");
			System.exit(0);
		}
		Login.lock=false;
		return null;
	}
}
