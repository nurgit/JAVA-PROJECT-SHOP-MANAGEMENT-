import java.lang.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.sql.*;


public class employeeHome extends JFrame implements ActionListener{
	private JLabel userNameLabel, roleLabel, userIDLabel;
	private JButton logout, browseProducts, accountSettings, manageEmployees, checkCustomers;
	private JPanel panel;
	private Font dfont;
	private String uID, userName, role;
	
	public employeeHome(String userID){
		super("Employee ID: "+userID);
		this.setSize(800,350);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		uID = userID;
		
		panel = new JPanel();
		panel.setLayout(null);
		
		dfont = new Font("New Times Roman", 0, 20);
		showEmployeeInfo(userID);
		
		logout = new JButton("Logout");
		logout.setFont(dfont);
		logout.setBounds(640,10,150,50);
		logout.addActionListener(this);
		logout.setBackground(Color.RED);
		panel.add(logout);
		
		accountSettings = new JButton("Account Settings");
		accountSettings.setBounds(330,80,300,100);
		accountSettings.addActionListener(this);
		accountSettings.setFont(dfont);
		panel.add(accountSettings);
		
		browseProducts = new JButton("Browse Products");
		browseProducts.setBounds(10, 200, 300, 100);
		browseProducts.setFont(dfont);
		browseProducts.addActionListener(this);
		panel.add(browseProducts);
		
		manageEmployees = new JButton("Manage Employees");
		manageEmployees.setBounds(330, 200, 300, 100);
		manageEmployees.setFont(dfont);
		manageEmployees.addActionListener(this);
		if(!role.equals("manager"))manageEmployees.setEnabled(false);
		panel.add(manageEmployees);
		
		checkCustomers = new JButton("Customers");
		checkCustomers.setFont(dfont);
		checkCustomers.setBounds(640,80,150,220);
		checkCustomers.addActionListener(this);
		panel.add(checkCustomers);
		
		this.add(panel);
		
	}
	
	public void actionPerformed(ActionEvent ae){
		String tmp = ae.getActionCommand();
		if(tmp.equals("Logout")){
			Login lg = new Login();
			lg.setVisible(true);
			this.setVisible(false);
		}
		else if(tmp.equals("Account Settings")){
			accountSettings ca = new accountSettings(uID, userName, " ", 1);//0 to identify customers
			ca.setVisible(true);
			this.setVisible(false);
		}
		else if(tmp.equals("Manage Employees")){
			employeeManage em = new employeeManage(uID);
			em.setVisible(true);
			this.setVisible(false);
		}
		else if(tmp.equals("Browse Products")){
			browseProducts bp = new browseProducts(uID, 1);
			bp.setVisible(true);
			this.setVisible(false);
		}
		else if(tmp.equals("Customers")){
			browseCustomers bc = new browseCustomers(uID);
			bc.setVisible(true);
			this.setVisible(false);
		}
	}
	
	private void showEmployeeInfo(String userID){
		try{
			Connection con=null;
			Statement st=null;
			ResultSet rs=null;
			connectionMGR cmgr = new connectionMGR(con,st);
			String query = "SELECT employeeName, role from employee WHERE userID='"+userID+"';";
			rs = cmgr.getResult(query);
			rs.next();
			userName = rs.getString("employeeName");
			role = rs.getString("role");
			
			userNameLabel = new JLabel("Name: "+userName);
			userNameLabel.setFont(dfont);
			userNameLabel.setBounds(10,10,200,50);
			panel.add(userNameLabel);
			
			roleLabel = new JLabel("Role: "+role);
			roleLabel.setFont(dfont);
			roleLabel.setBounds(10,70,200, 50);
			panel.add(roleLabel);
			
			userIDLabel = new JLabel("User ID: "+userID);
			userIDLabel.setFont(dfont);
			userIDLabel.setBounds(10,130,200,50);
			panel.add(userIDLabel);
			
			
			if(con!=null)con.close();
			if(rs!=null)rs.close();
			if(st!=null)st.close();
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>this");
		}
	}
	
	
}