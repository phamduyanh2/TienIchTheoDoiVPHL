package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.html.Option;

import connect.NguoiDungService;
import ui.NhapLieuUi;

import connect.NguoiDung;

public class DangNhapUI extends JFrame {
	
	JTextField txtUser;
	JPasswordField txtPassWord;
	JButton btnLogin,btnExit;
	JRadioButton remember;
	File file = new File(System.getProperty("user.home")+"/Desktop/save.txt");
	
	public DangNhapUI (String Title)
	{
		super(Title);
		addControl();
		addEvent();
		
	}

	public void addControl()
	{
		Container con=getContentPane();
		//con.setLayout(new BorderLayout());
		JPanel pnMain=new JPanel();
		pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
		JPanel pnTitle=new JPanel();
		JLabel lblTitle=new JLabel("ĐĂNG NHẬP HỆ THỐNG");
		pnMain.add(pnTitle);
		pnTitle.add(lblTitle);
		con.add(pnMain);
		
		Font fontTitile=new Font("Ảrial",Font.BOLD, 20);
		lblTitle.setFont(fontTitile);
		lblTitle.setForeground(Color.blue);
		
		JPanel pnDangNhap=new JPanel();
		pnDangNhap.setLayout(new BoxLayout(pnDangNhap, BoxLayout.Y_AXIS));
		pnMain.add(pnDangNhap);
		
		JPanel pnUser=new JPanel();
		pnUser.setLayout(new FlowLayout());
		JLabel lblUser=new JLabel("Tên đăng nhập");
		txtUser=new JTextField(15);

		pnUser.add(lblUser);
		pnUser.add(txtUser);
		pnDangNhap.add(pnUser);

		JPanel pnPassWord=new JPanel();
		pnPassWord.setLayout(new FlowLayout());
		JLabel lblPassWord=new JLabel("Mật khẩu");
		txtPassWord=new JPasswordField(15);
		pnPassWord.add(lblPassWord);
		pnPassWord.add(txtPassWord);
		pnDangNhap.add(pnPassWord);
		remember = new JRadioButton("Nhớ mật khẩu");
		pnDangNhap.add(remember);
		
		JPanel pnButton=new JPanel();
		btnLogin=new JButton("Đăng nhập");
		btnExit=new JButton("Thoát");
		pnButton.add(btnLogin);
		pnButton.add(btnExit);
		pnDangNhap.add(pnButton);
		
		TitledBorder border=new TitledBorder(BorderFactory.createLineBorder(Color.GRAY),
				"Thông tin đăng nhập");
		pnDangNhap.setBorder(border);
		
		lblPassWord.setPreferredSize(lblUser.getPreferredSize());
		remember.setPreferredSize(lblUser.getPreferredSize());
	}
	
	public void addEvent()
	{
		
		
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				xuLyDangNhap();
				
			}
		});
		
		
		btnExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
				
			}
		});
		
		btnLogin.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if(remember.isSelected()){
	                  SAVE(); //Save This UserName and his PassWord     
	               }
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	
	protected void SAVE() {
		try {
            if(!file.exists()) file.createNewFile();  //if the file !exist create a new one

            BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
            bw.write(txtUser.getText()); //write the name
            bw.newLine(); //leave a new Line
            bw.write(txtPassWord.getPassword()); //write the password
            bw.close(); //close the BufferdWriter

        } catch (IOException e) { e.printStackTrace(); }     
		
	/*	If you use database than keep checkbox option in database. 
		Otherweise, If checkbox is selected than store your password in some
		hidden file at user path. Next time before login look at that password.
		
	 String path=System.getProperty("user.home")+"/.myapp";
    FileWriter file=new FileWriter(path);
    file.write("user_password");
    file.close();
		
		*/
		
	}
	
	public void LayMatKhau(){ //UPDATE ON OPENING THE APPLICATION

        try {
          if(file.exists()){    //if this file exists

            Scanner scan = new Scanner(file);   //Use Scanner to read the File

            txtUser.setText(scan.nextLine());  //append the text to name field
            txtPassWord.setText(scan.nextLine()); //append the text to password field
            scan.close();
          }

        } catch (FileNotFoundException e) {         
            e.printStackTrace();
        }                

   }//End OF UPDATE


	protected void xuLyDangNhap() {
		
		
		
		NguoiDung nd=NguoiDungService.dangNhap(txtUser.getText(), txtPassWord.getText());
		if(nd==null)
		{
			JOptionPane.showMessageDialog(null, "Đăng nhập thất bại");
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Đăng nhập thành công");
			
			
			// HIEN THI CHUONG TRINH CHINH SE CHAY TAI DAY
			
			// FORM THONG KE 
			
	//		ThongKeViPhamUi ui=new ThongKeViPhamUi("THỐNG KÊ VI PHẠM THEO TUYẾN ĐƯỜNG");
		//	ui.showWindow();
			
			// FORM NHAP LIEU 
			
			
			dispose();		// dong cua so dang nhap lai, chi hien thi 1 cua so lam viec ma thoi
			NhapLieuUi ui2=new NhapLieuUi("NHẬP SỐ LIỆU VI PHẠM HÀNH LANG TRÊN TUYẾN");
			ui2.showWinDow2();
			
			
			
			
		}
		
	}

	public void showWindow()
	{
		this.setSize(500, 240);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
}
