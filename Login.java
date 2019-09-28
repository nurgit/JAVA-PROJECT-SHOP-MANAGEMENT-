import java.lang.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener{
	private JLabel userLabel, passLabel;
	private JTextField userName;
	private JPasswordField userPass;
	private JButton loginBtn, registerBtn;
	private JPanel panel;
	
	Login(){
		super("Login");
		this.setSize(500,400);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		Font dfont = new Font("New Times Roman", 0, 20);
		this.setFont(dfont);
		panel = new JPanel();
		panel.setLayout(null);
		
		userLabel = new JLabel("User ID: ");
		userLabel.setFont(dfont);
		userLabel.setBounds(100,50,100,50);
		panel.add(userLabel);
		
		userName = new JTextField();
		userName.setBounds(210, 50, 200, 50);
		userName.setFont(dfont);
		panel.add(userName);
		
		passLabel = new JLabel("Password: ");
		passLabel.setFont(dfont);
		passLabel.setBounds(100,120, 100, 50);
		panel.add(passLabel);
		
		userPass = new JPasswordField();
		userPass.setBounds(210, 120, 200, 50);
		panel.add(userPass);
		
		registerBtn = new JButton("Register");
		registerBtn.addActionListener(this);
		registerBtn.setFont(dfont);
		registerBtn.setBounds(100, 190, 140, 90);
		panel.add(registerBtn);
		
		loginBtn = new JButton("Login");
		loginBtn.addActionListener(this);
		loginBtn.setFont(dfont);
		loginBtn.setBounds(270,190, 140, 90);
		panel.add(loginBtn);
		
		this.add(panel);
		

	}
	
	public void actionPerformed(ActionEvent ae){
		String tmp = ae.getActionCommand();
		if(tmp.equals("Register")){
			customerReg cr = new customerReg();
			cr.setVisible(true);
			this.setVisible(false);
		}
		else if(tmp.equals("Login")){
			Connection con=null;
			Statement st=null;
			ResultSet rs=null;
			connectionMGR cmgr = new connectionMGR(con, st);
			boolean flag=false;
			try{
				String query = "Select userID, password, status FROM login";
				rs = cmgr.getResult(query);
				//System.out.println("results received");
				while(rs.next()){
						String pass = rs.getString("password");
						String user = rs.getString("userID");
						int status = rs.getInt("status");
						if(user.equals(userName.getText()) && pass.equals(userPass.getText())){
							flag=true;
							if(status==0){
								customerHome ch = new customerHome(user);
								ch.setVisible(true);
								this.setVisible(false);
							}
							else if(status==1){
								employeeHome eh = new employeeHome(user);
								eh.setVisible(true);
								this.setVisible(false);
							}
							else{}
							
						}
						
				}
			}
			catch(Exception e){
				System.out.println("Exception: "+e.getMessage());
			}
			finally{
				try{
					if(con!=null)con.close();
					if(rs!=null)rs.close();
					if(st!=null)st.close();
				}
				catch(Exception e){
					System.out.println("Exception: "+e.getMessage());
				}
			}
			if(flag==false){
						JOptionPane.showMessageDialog(this,"<html><font face='New Times Roman' size='6'>Invalid ID or Password"); 
			}
			//else JOptionPane.showMessageDialog(this,"<html><font face='New Times Roman' size='6'>Login Successful!");
			
		}
		
	}
	
	
}