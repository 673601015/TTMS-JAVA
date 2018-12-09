package project1;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Panel;
import javax.swing.JPanel;
import java.awt.Image;
import javax.swing.SwingConstants;

//import javafx.scene.image.Image;

import javax.swing.JLabel;
import javax.swing.JButton;

public class Form4 {

	private JFrame frame;
	private String []data;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Form4 window = new Form4(null);
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
	public Form4(String []data) {
		this.data=data;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("电影名称:");
		label.setBounds(251, 63, 67, 15);
		frame.getContentPane().add(label);
		
		JLabel lblNewLabel = new JLabel("绝地求生神奇四匣");
		lblNewLabel.setText(data[0]);
		lblNewLabel.setBounds(315, 63, 251, 15);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel label_1 = new JLabel("导演:");
		label_1.setBounds(251, 108, 54, 15);
		frame.getContentPane().add(label_1);
		
		JLabel lblNewLabel_1 = new JLabel("蓝洞");
		lblNewLabel_1.setText(data[1]);
		lblNewLabel_1.setBounds(315, 108, 251, 15);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel label_2 = new JLabel("电影介绍:");
		label_2.setBounds(252, 200, 66, 15);
		frame.getContentPane().add(label_2);
		
		
		
		JLabel label_3 = new JLabel("主演:");
		label_3.setBounds(251, 154, 54, 15);
		frame.getContentPane().add(label_3);
		
		JLabel lblNewLabel_3 = new JLabel("何浩灰，刘吉吉，毛驴，王予象");
		lblNewLabel_3.setText(data[2]);
		lblNewLabel_3.setBounds(315, 148, 269, 26);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_2 = new JLabel("<html>职业跳伞选手教大家怎么快速跳伞，重新跳伞</html>");
		lblNewLabel_2.setText("<html>"+data[4]+"</html>");
		lblNewLabel_2.setBounds(315, 198, 151, 136);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2.setVerticalAlignment(SwingConstants.TOP);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel label_4 = new JLabel("");
		label_4.setBounds(32, 63, 168, 216);
		ImageIcon icon=new ImageIcon(Form4.class.getResource("/Images/Image3.png"));
		icon.setImage(icon.getImage().getScaledInstance((int)label_4.bounds().getWidth(), 
				(int) label_4.bounds().getHeight(),Image.SCALE_DEFAULT));
		label_4.setIcon(icon);
		frame.getContentPane().add(label_4);
		frame.setBounds(100, 100, 600, 480);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
}
