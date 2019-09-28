import java.lang.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.sql.*;

public class customerHome extends JFrame implements ActionListener{
	private JLabel userNameLabel, userIDLabel, addressLabel;
	private JButton logout, browseProducts, purchaseHistory, accountSettings;
	private JPanel panel;
	private Font dfont;
	private String userName, uID, userAddress;
	public customerHome(String userID){
		super("Customer ID: "+userID);
		this.setSize(650,350);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		uID = userID;
		
		panel = new JPanel();
		panel.setLayout(null);
		dfont = new Font("New Times Roman", 0, 20);
		showCustomerInfo(userID);
		
		logout = new JButton("Logout");
		logout.setFont(dfont);
		logout.setBounds(330,10,300,50);
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
		
		purchaseHistory = new JButton("Purchase History");
		purchaseHistory.setBounds(330, 200, 300, 100);
		purchaseHistory.setFont(dfont);
		purchaseHistory.addActionListener(this);
		panel.add(purchaseHistory);
		
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
			accountSettings ca = new accountSettings(uID, userName, userAddress, 0);//0 to identify customers
			ca.setVisible(true);
			this.setVisible(false);
		}
		else if(tmp.equals("Browse Products")){
			browseProducts bp = new browseProducts(uID,0);
			bp.setVisible(true);
			this.setVisible(false);
		}
		else if(tmp.equals("Purchase History")){
			pHistory ph = new pHistory(uID,uID,0);
			ph.setVisible(true);
			this.setVisible(false);
		}
	}
	
	private void showCustomerInfo(String userID){
		try{
			Connection con=null;
			Statement st=null;
			ResultSet rs=null;
			connectionMGR cmgr = new connectionMGR(con,st);
			String query = "SELECT customerName, address from customer WHERE userID='"+userID+"';";
			rs = cmgr.getResult(query);
			rs.next();
			
			userName = rs.getString("customerName");
			userAddress = rs.getString("address");
			
			userNameLabel = new JLabel("Name: "+rs.getString("customerName"));
			userNameLabel.setFont(dfont);
			userNameLabel.setBounds(10,10,200,50);
			panel.add(userNameLabel);
			
			addressLabel = new JLabel("Address: "+rs.getString("address"));
			addressLabel.setFont(dfont);
			addressLabel.setBounds(10,70,300,50);
			panel.add(addressLabel);
			
			userIDLabel = new JLabel("User ID: "+userID);
			userIDLabel.setFont(dfont);
			userIDLabel.setBounds(10,130,200,50);
			panel.add(userIDLabel);
			
			
			if(con!=null)con.close();
			if(rs!=null)rs.close();
			if(st!=null)st.close();
		}
		catch(Exception e){}
	}
	
	
}