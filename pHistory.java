import java.lang.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import javax.swing.table.*;

public class pHistory extends JFrame implements ActionListener{
	private JPanel panel;
	private JLabel customerID;
	private JButton back,logout;
	private JTable pTable;
	private String uID,ownID;
	private Font dfont;
	private boolean employee;
	
	public pHistory(String userID, String _ownID, int status){
		super("Purchase history of "+userID);
		this.setSize(800,650);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		uID=userID;
		this.ownID=_ownID;
		
		dfont = new Font("New Times Roman", 0, 20);
		if(status==0)employee=false;
		else employee=true;
		
		panel = new JPanel();
		panel.setLayout(null);
		
		customerID = new JLabel("Showing purchase history of customer: "+userID);
		customerID.setFont(new Font("New Times Roman",1,25));
		customerID.setBounds(150,30, 550, 50);
		panel.add(customerID);
		
		back = new JButton("Back");
		back.setFont(dfont);
		back.setBounds(310, 100, 150, 90);
		back.addActionListener(this);
		panel.add(back);
		
		pTable = new JTable();
		pTable.setEnabled(false);
		pTable.setRowHeight(35);
		pTable.setFont(dfont);
		showTable();
		JScrollPane sp = new JScrollPane(pTable);
		sp.setBounds(0, 200, 795, 400);
		panel.add(sp);
		
		logout = new JButton("Logout");
		logout.setFont(dfont);
		logout.setBounds(550,100,200,90);
		logout.addActionListener(this);
		logout.setBackground(Color.RED);
		panel.add(logout);
		
		this.add(panel);
	}
	
	public void showTable(){
		DefaultTableModel dtm = new DefaultTableModel();
		pTable.setModel(dtm);
		dtm.addColumn("Product ID");
		dtm.addColumn("Product Name");
		dtm.addColumn("Quantity");
		dtm.addColumn("Total Price");
		try{
				Connection con=null;
				Statement st=null;
				ResultSet rs=null;
				connectionMGR cmgr = new connectionMGR(con,st);
				
				rs = cmgr.getResult("SELECT * FROM product, purchaseinfo WHERE product.productID=purchaseinfo.productID AND purchaseinfo.userID='"+uID+"';");
				while(rs.next()){
					dtm.addRow(new Object[]{rs.getString("productID"), rs.getString("productName"), rs.getString("quantity"), "$"+rs.getString("amount")});
				}
				
				if(con!=null)con.close();
				if(rs!=null)rs.close();
				if(st!=null)st.close();
				
		}
		catch(Exception e){}
		
	}
	
	public void actionPerformed(ActionEvent ae){
		String tmp = ae.getActionCommand();
		if(tmp.equals("Back")){
			if(employee){
				browseCustomers bc = new browseCustomers(ownID);
				bc.setVisible(true);
				this.setVisible(false);
			}
			else {
				customerHome ch = new customerHome(ownID);
				ch.setVisible(true);
				this.setVisible(false);
			}
		}
		else if(tmp.equals("Logout")){
			Login lg = new Login();
			lg.setVisible(true);
			this.setVisible(false);
		}
	}
	
	
}