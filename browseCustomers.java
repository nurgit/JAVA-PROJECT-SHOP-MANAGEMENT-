import java.lang.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import javax.swing.table.*;

public class browseCustomers extends JFrame implements ActionListener{
	private JPanel panel;
	private JLabel customerID;
	private JTextField cIDTF;
	private JButton back,purchaseHistory,logout;
	private JTable pTable;
	private String uID;
	private Font dfont;

	public browseCustomers(String userID){
		super("Registered Customers");
		this.setSize(800,630);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		uID=userID;
		dfont = new Font("New Times Roman", 0, 20);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		pTable = new JTable();
		pTable.setEnabled(false);
		pTable.setRowHeight(35);
		pTable.setFont(dfont);
		showTable();
		JScrollPane sp = new JScrollPane(pTable);
		sp.setBounds(0,	0, 795, 350);
		panel.add(sp);
		
		customerID = new JLabel("Enter ID: ");
		customerID.setFont(dfont);
		customerID.setBounds(50,400,100,50);
		panel.add(customerID);
		
		cIDTF = new JTextField();
		cIDTF.setFont(dfont);
		cIDTF.setBounds(150, 400, 300, 50);
		panel.add(cIDTF);
		
		back = new JButton("Back");
		back.setFont(dfont);
		back.setBounds(10,470, 130,100);
		back.addActionListener(this);
		panel.add(back);
		
		logout = new JButton("Logout");
		logout.setFont(dfont);
		logout.setBounds(580,400,200,100);
		logout.addActionListener(this);
		logout.setBackground(Color.RED);
		panel.add(logout);
		
		purchaseHistory = new JButton("Show Purchase History");
		purchaseHistory.setFont(dfont);
		purchaseHistory.setBounds(150,470,300,100);
		purchaseHistory.addActionListener(this);
		panel.add(purchaseHistory);
		
		this.add(panel);

	}
	
	public void actionPerformed(ActionEvent ae){
		String tmp = ae.getActionCommand();
		if(tmp.equals("Back")){
			employeeHome eh = new employeeHome(uID);
			eh.setVisible(true);
			this.setVisible(false);
		}
		else if(tmp.equals("Show Purchase History")){
			boolean possible=false;
			try{
				Connection con=null;
				Statement st=null;
				ResultSet rs=null;
				connectionMGR cmgr = new connectionMGR(con,st);
				rs = cmgr.getResult("SELECT * FROM customer WHERE userID='"+cIDTF.getText()+"';");
				if(rs.next())possible=true;
				else JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>ID not found");		
			}
			catch(Exception e){
				System.out.println("Exception: "+e.getMessage());
			}
			if(possible){
				pHistory ph = new pHistory(cIDTF.getText(),uID, 1);
				ph.setVisible(true);
				this.setVisible(false);
			}
		}
		else if(tmp.equals("Logout")){
			Login lg = new Login();
			lg.setVisible(true);
			this.setVisible(false);
		}
	}
	
	public void showTable(){
		DefaultTableModel dtm = new DefaultTableModel();
		pTable.setModel(dtm);
		dtm.addColumn("Customer ID");
		dtm.addColumn("Customer Name");
		dtm.addColumn("Address");
		try{
				Connection con=null;
				Statement st=null;
				ResultSet rs=null;
				connectionMGR cmgr = new connectionMGR(con,st);
				
				rs = cmgr.getResult("SELECT * FROM customer");
				while(rs.next()){
					dtm.addRow(new Object[]{rs.getString("userID"), rs.getString("customerName"), rs.getString("address")});
				}
				
				if(con!=null)con.close();
				if(rs!=null)rs.close();
				if(st!=null)st.close();
				
		}
		catch(Exception e){}
		
	}
}