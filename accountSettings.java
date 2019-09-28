import java.lang.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.sql.*;

public class accountSettings extends JFrame implements ActionListener{
	private JLabel userNameLabel, oldPassLabel, newPassLabel, addressLabel;
	private JLabel info;
	private JTextField nameTF, addressTF;
	private JPasswordField oldPassTF, newPassTF;
	private JButton updateInfo, back, deleteAccount, logout;
	private JPanel panel;
	private Font dfont;
	private boolean employee;
	private String uID;
	
	public accountSettings(String userID, String userName, String address, int status){
		super("Account information for user: "+userID);
		this.setSize(800,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		uID = userID;
		
		if(status==0)employee=false;
		else employee=true;
		
		dfont = new Font("New Times Roman", 0, 20);
		panel = new JPanel();
		panel.setLayout(null);
		
		userNameLabel = new JLabel("User name: ");
		userNameLabel.setBounds(10,10, 200, 50);
		userNameLabel.setFont(dfont);
		panel.add(userNameLabel);
		
		nameTF = new JTextField(userName);
		nameTF.setFont(dfont);
		nameTF.setBounds(160, 10, 300, 50);
		panel.add(nameTF);
		
		oldPassLabel = new JLabel("*Old Password: ");
		oldPassLabel.setBounds(10,80,200,50);
		oldPassLabel.setFont(dfont);
		panel.add(oldPassLabel);
		
		oldPassTF = new JPasswordField();
		oldPassTF.setBounds(160, 80, 300, 50);
		panel.add(oldPassTF);
		
		newPassLabel = new JLabel("New Password: ");
		newPassLabel.setBounds(10, 150, 200, 50);
		newPassLabel.setFont(dfont);
		panel.add(newPassLabel);
		
		newPassTF = new JPasswordField();
		newPassTF.setBounds(160, 150, 300, 50);
		panel.add(newPassTF);
		
		addressLabel = new JLabel("Address: ");
		addressLabel.setFont(dfont);
		addressLabel.setBounds(10,220, 200, 50);
		if(employee)addressLabel.setEnabled(false);
		panel.add(addressLabel);
		
		addressTF = new JTextField(address);
		addressTF.setFont(dfont);
		addressTF.setBounds(160, 220, 300, 50);
		if(employee){
			addressTF.setEnabled(false);
			addressTF.setText("");
		}
		panel.add(addressTF);
		
		info = new JLabel("Modify any information and click 'Update' to save them");
		info.setFont(dfont);
		info.setBounds(10, 300, 500, 50);
		panel.add(info);
		
		updateInfo = new JButton("Update");
		updateInfo.setFont(dfont);
		updateInfo.setBounds(250,350, 210, 100);
		updateInfo.addActionListener(this);
		panel.add(updateInfo);
		
		back = new JButton("Back");
		back.setFont(dfont);
		back.setBounds(10, 350, 210, 100);
		back.addActionListener(this);
		panel.add(back);
		
		deleteAccount = new JButton("Delete Account");
		deleteAccount.setFont(dfont);
		deleteAccount.setBounds(580, 150, 200, 100);
		deleteAccount.addActionListener(this);
		deleteAccount.setBackground(Color.RED);
		if(employee)deleteAccount.setEnabled(false);
		panel.add(deleteAccount);
		
		logout = new JButton("Logout");
		logout.setFont(dfont);
		logout.setBounds(580,10,200,50);
		logout.addActionListener(this);
		logout.setBackground(Color.RED);
		panel.add(logout);
		
		this.add(panel);
	}
	
	public void actionPerformed(ActionEvent ae){
		String tmp = ae.getActionCommand();
		if(tmp.equals("Update")){
			try{
				if(nameTF.getText().equals("") || oldPassTF.getText().equals("") || newPassTF.getText().equals("") || (!employee && addressTF.getText().equals(""))){
					JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>Please fill out all the required fields");
					return;
				}
				Connection con=null;
				Statement st=null;
				ResultSet rs=null;
				connectionMGR cmgr = new connectionMGR(con,st);
				rs = cmgr.getResult("Select password FROM login WHERE userID='"+uID+"';");
				rs.next();
				
				if(!oldPassTF.getText().equals(rs.getString("password"))){
					JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>Old password does not match");
					return;
				}
				
				String loginQuery = "UPDATE login SET password='"+newPassTF.getText()+"' WHERE userID='"+uID+"';";
				cmgr.execute(loginQuery);
				
				String userQuery="";
				if(employee)userQuery = "UPDATE employee SET employeeName='"+nameTF.getText()+"' WHERE userID='"+uID+"';";
				else userQuery = "UPDATE customer SET customerName='"+nameTF.getText()+"', address='"+addressTF.getText()+"' WHERE userID='"+uID+"';";
				cmgr.execute(userQuery);
				JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>Information has been successfully updated");
				oldPassTF.setText("");
				newPassTF.setText("");
				
				if(con!=null)con.close();
				if(rs!=null)rs.close();
				if(st!=null)st.close();
			}
			catch(Exception e){
				JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>oops! something went wrong");
			}
		}
		else if(tmp.equals("Back")){
			if(employee){
				employeeHome eh = new employeeHome(uID);
				eh.setVisible(true);
				this.setVisible(false);
			}
			else{
				customerHome ch = new customerHome(uID);
				ch.setVisible(true);
				this.setVisible(false);
			}
		}
		else if(tmp.equals("Delete Account")){
			try{
				Connection con=null;
				Statement st=null;
				ResultSet rs=null;
				connectionMGR cmgr = new connectionMGR(con,st);
				String query="DELETE FROM customer WHERE userID='"+uID+"';";
				cmgr.execute(query);
				query = "DELETE FROM login WHERE userID='"+uID+"';";
				cmgr.execute(query);
				query = "DELETE FROM purchaseInfo WHERE userID='"+uID+"';";
				cmgr.execute(query);
				JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>User: "+uID+" Has been removed from database");
				if(con!=null)con.close();
				if(rs!=null)rs.close();
				if(st!=null)st.close();
				Login lg = new Login();
				lg.setVisible(true);
				this.setVisible(false);
			}
			catch(Exception e){
				JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>oops! something went wrong");
			}
			
		}
		else if(tmp.equals("Logout")){
			Login lg = new Login();
			lg.setVisible(true);
			this.setVisible(false);
		}
		
	}
	
	
	
	
	
}