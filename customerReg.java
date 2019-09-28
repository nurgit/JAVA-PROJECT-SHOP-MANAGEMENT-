import java.lang.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.sql.*;

public class customerReg extends JFrame implements ActionListener{
	private JLabel userIDLabel, userLabel, passLabel, userNameLabel, titleLabel,addressLabel;
	private JTextField userName,userAddress, givenID;
	private JPasswordField userPass;
	private JButton register,logout;
	private JPanel panel;
	private String setID;
	
	public customerReg(){
		super("Customer Registration");
		this.setSize(800,430);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		Font dfont = new Font("New Times Roman", 0, 20);
		
		titleLabel = new JLabel("Customer Registration");
		titleLabel.setFont(dfont);
		titleLabel.setBounds(300,10,200,30);
		panel.add(titleLabel);
		
		logout = new JButton("Login");
		logout.setFont(dfont);
		logout.setBounds(640,10,150,60);
		logout.addActionListener(this);
		panel.add(logout);
		
		userNameLabel = new JLabel("Name: ");
		userNameLabel.setFont(dfont);
		userNameLabel.setBounds(150,60,100,50);
		panel.add(userNameLabel);
		
		userName = new JTextField();
		userName.setFont(dfont);
		userName.setBounds(250, 60, 300, 50);
		panel.add(userName);
		
		passLabel = new JLabel("Password: ");
		passLabel.setFont(dfont);
		passLabel.setBounds(150,120, 100, 50);
		panel.add(passLabel);
		
		userPass = new JPasswordField();
		userPass.setBounds(250, 120, 300, 50);
		panel.add(userPass);
		
		addressLabel = new JLabel("Address: ");
		addressLabel.setFont(dfont);
		addressLabel.setBounds(150,180, 100, 50);
		panel.add(addressLabel);
		
		userAddress = new JTextField();
		userAddress.setFont(dfont);
		userAddress.setBounds(250,180, 300, 50);
		panel.add(userAddress);
		
		register = new JButton("Register");
		register.setFont(dfont);
		register.setBounds(300, 300, 200, 50);
		register.addActionListener(this);
		panel.add(register);
		
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		connectionMGR cmgr = new connectionMGR(con, st);
		try{
			int count = 0;
			setID = "C_"+(Integer.toString(count));
			rs = cmgr.getResult("Select COUNT(*) AS uniq FROM customer WHERE userID='"+setID+"';");
			rs.next();
			while(rs.getInt("uniq")!=0){
				count++;
				setID = "C_"+(Integer.toString(count));
				rs = cmgr.getResult("Select COUNT(*) AS uniq FROM customer WHERE userID='"+setID+"';");
				rs.next();
			}
			//System.out.println(setID);
			if(con!=null)con.close();
			if(st!=null)st.close();
			if(rs!=null)rs.close();
		}
		catch(Exception e){
			System.out.println("Exception: "+e.getMessage());
		}
		
		userIDLabel = new JLabel("User ID: ");
		userIDLabel.setFont(dfont);
		userIDLabel.setBounds(150, 240, 100, 50);
		panel.add(userIDLabel);
		
		givenID = new JTextField(setID);
		givenID.setEditable(false);
		givenID.setFont(dfont);
		givenID.setBounds(250, 240, 300, 50);
		givenID.setBackground(Color.WHITE);
		panel.add(givenID);
		this.add(panel);
	}
	
	public void actionPerformed(ActionEvent ae){
		String tmp = ae.getActionCommand();
		if(tmp.equals("Login")){
			Login lg = new Login();
			lg.setVisible(true);
			this.setVisible(false);
		}
		else if(tmp.equals("Register")){
			Connection con=null;
			Statement st=null;
			ResultSet rs=null;
			connectionMGR cmgr = new connectionMGR(con,st);
			try{
				String newName = userName.getText();
				String newPass = userPass.getText();
				String newAddress = userAddress.getText();
				String newID = setID;
				if(newName.equals("") || newPass.equals("") || newAddress.equals("")){
						JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>Please fill out all the required fields");
						return;
				}
				String query1 = "INSERT INTO customer VALUES('"+newID+"','"+newName+"','"+newAddress+"');";
				String query2 = "INSERT INTO login VALUES('"+newID+"','"+newPass+"','0');";// 0 is for Customers
				cmgr.execute(query1);
				cmgr.execute(query2);
				if(con!=null)con.close();
				if(rs!=null)rs.close();
				if(st!=null)st.close();
			}
			catch(Exception e){
				System.out.println("Exception: "+e.getMessage());
			}
			Login lg = new Login();
			lg.setVisible(true);
			this.setVisible(false);
			JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>Registration Successful!\n" + "<html><font face='New Times Roman' size='6'>   Your ID: "+setID);
			
		}
	}
	
	
}