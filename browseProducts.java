import java.lang.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import javax.swing.table.*;


public class browseProducts extends JFrame implements ActionListener{
	private JPanel panel;
	private boolean employee;
	private JTable pTable;
	private JButton buy,back,updateInfo,addProduct,removeProduct,generateID,logout;
	private JTextField pIDTF,amountTF,totalpriceTF, nameTF, priceTF;
	private JLabel pIDLabel, amountLabel, totalpriceLabel, priceLabel, nameLabel;
	private Font dfont;
	private String uID;
	
	public browseProducts(String userID,int status){
		super("Browse Products");
		this.setSize(800,760);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		uID=userID;
		
		dfont = new Font("New Times Roman", 0, 20);
		if(status==0)employee=false;
		else employee=true;
		
		panel = new JPanel();
		panel.setLayout(null);
		
		pTable = new JTable();
		//pTable.setBounds(10,10,750,100);
		pTable.setEnabled(false);
		pTable.setRowHeight(35);
		pTable.setFont(dfont);
		showTable();
		JScrollPane sp = new JScrollPane(pTable);
		sp.setBounds(0, 0, 795, 400);
		panel.add(sp);
		if(employee)employeeGUI();
		else customerGUI();
		
		back = new JButton("Back");
		back.setFont(dfont);
		back.setBounds(630,420,150, 50);
		back.addActionListener(this);
		panel.add(back);
		
		logout = new JButton("Logout");
		logout.setFont(dfont);
		logout.setBounds(630,480,150,50);
		logout.addActionListener(this);
		logout.setBackground(Color.RED);
		panel.add(logout);
		
		this.add(panel);
		
	}
	public void employeeGUI(){
		pIDLabel = new JLabel("Product ID: ");
		pIDLabel.setFont(dfont);
		pIDLabel.setBounds(10, 420, 200, 50);
		panel.add(pIDLabel);
		
		pIDTF = new JTextField();
		pIDTF.setFont(dfont);
		pIDTF.setBounds(180,420,150, 50);
		panel.add(pIDTF);
		
		amountLabel = new JLabel("Available Quantity: ");
		amountLabel.setFont(dfont);
		amountLabel.setBounds(10,600,200, 50);
		panel.add(amountLabel);
		
		amountTF = new JTextField();
		amountTF.setFont(dfont);
		amountTF.setBounds(180,600,150,50);
		panel.add(amountTF);
		
		generateID = new JButton("Generate Unique ID");
		generateID.setBounds(350, 420, 250, 50);
		generateID.setFont(dfont);
		generateID.addActionListener(this);
		panel.add(generateID);
		
		priceLabel = new JLabel("Price: ");
		priceLabel.setFont(dfont);
		priceLabel.setBounds(10, 540, 200, 50);
		panel.add(priceLabel);
		
		priceTF = new JTextField();
		priceTF.setFont(dfont);
		priceTF.setBounds(180, 540, 150, 50);
		panel.add(priceTF);
		
		nameLabel = new JLabel("Product Name: ");
		nameLabel.setFont(dfont);
		nameLabel.setBounds(10, 480, 200, 50);
		panel.add(nameLabel);
		
		nameTF = new JTextField();
		nameTF.setFont(dfont);
		nameTF.setBounds(180, 480, 150, 50);
		panel.add(nameTF);
		
		updateInfo = new JButton("Update");
		updateInfo.setFont(dfont);
		updateInfo.setBounds(10,660, 200, 50);
		updateInfo.addActionListener(this);
		panel.add(updateInfo);
		
		addProduct = new JButton("Add");
		addProduct.setFont(dfont);
		addProduct.setBounds(220, 660,200, 50);
		addProduct.addActionListener(this);
		panel.add(addProduct);
		
		removeProduct = new JButton("Remove");
		removeProduct.setFont(dfont);
		removeProduct.setBounds(430, 660, 200, 50);
		removeProduct.addActionListener(this);
		panel.add(removeProduct);
		
	}
	public void customerGUI(){
		this.setSize(800,650);
		pIDLabel = new JLabel("Enter product ID: ");
		pIDLabel.setFont(dfont);
		pIDLabel.setBounds(10, 420, 200, 50);
		panel.add(pIDLabel);
		
		pIDTF = new JTextField();
		pIDTF.setFont(dfont);
		pIDTF.setBounds(180,420,150, 50);
		panel.add(pIDTF);
		
		amountLabel = new JLabel("Enter quantity: ");
		amountLabel.setFont(dfont);
		amountLabel.setBounds(10,480,200, 50);
		panel.add(amountLabel);
		
		amountTF = new JTextField();
		amountTF.setFont(dfont);
		amountTF.setBounds(180,480,150,50);
		panel.add(amountTF);
		
		totalpriceLabel = new JLabel("Total Price: ");
		totalpriceLabel.setFont(dfont);
		totalpriceLabel.setBounds(10,540,150,50);
		panel.add(totalpriceLabel);
		
		totalpriceTF = new JTextField();
		totalpriceTF.setFont(dfont);
		totalpriceTF.setBounds(180,540, 150, 50);
		totalpriceTF.setEnabled(false);
		panel.add(totalpriceTF);
		
		buy = new JButton("Buy");
		buy.setFont(dfont);
		buy.setBounds(450, 420, 150,150);
		buy.addActionListener(this);
		panel.add(buy);
		
	}
	public void actionPerformed(ActionEvent ae){
		String tmp = ae.getActionCommand();
		if(tmp.equals("Buy")){
			try{
				Connection con=null;
				Statement st=null;
				ResultSet rs=null;
				connectionMGR cmgr = new connectionMGR(con,st);
				int amount = Integer.parseInt(amountTF.getText());
				String pid = pIDTF.getText();
				rs = cmgr.getResult("SELECT availableQuantity, price FROM product WHERE productID='"+pid+"';");
				rs.next();
				if(rs.getInt("availableQuantity")-amount>=0){
					totalpriceTF.setText(Double.toString(amount*rs.getDouble("price")));
					updatePurchaseInfo(pid, uID, amount, totalpriceTF.getText());//(product ID, user ID, quantity, total price);
					int newAmount = rs.getInt("availableQuantity")-amount;
					cmgr.execute("UPDATE product SET availableQuantity='"+Integer.toString(newAmount)+"' WHERE productID='"+pid+"';");
					showTable();
				}
				else JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>Invalid Input");
				if(con!=null)con.close();
				if(rs!=null)rs.close();
				if(st!=null)st.close();
			}
			catch(Exception e){
				JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>Invalid Input");
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
		else if(tmp.equals("Generate Unique ID")){
			try{
				Connection con=null;
				Statement st=null;
				ResultSet rs=null;
				connectionMGR cmgr = new connectionMGR(con,st);
				rs = cmgr.getResult("SELECT COUNT(*) AS uniq FROM product WHERE productID='P_0';");
				rs.next();
				int count = 0;
				String setID = "P_"+(Integer.toString(count));
				while(rs.getInt("uniq")!=0){
					count++;
					setID = "P_"+(Integer.toString(count));
					rs = cmgr.getResult("Select COUNT(*) AS uniq FROM product WHERE productID='"+setID+"';");
					rs.next();
				}
				pIDTF.setText(setID);
				if(con!=null)con.close();
				if(rs!=null)rs.close();
				if(st!=null)st.close();
			}
			catch(Exception e){}
		}
		else if(tmp.equals("Update")){
			if(pIDTF.getText().equals("") || nameTF.getText().equals("") || amountTF.getText().equals("") || priceTF.getText().equals("")){
				JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>Please enter valid data");
				return;
			}
			try{
				Connection con=null;
				Statement st=null;
				ResultSet rs=null;
				connectionMGR cmgr = new connectionMGR(con,st);
				//System.out.println("UPDATE product SET productName='"+nameTF.getText()+"', price='"+priceTF.getText()+"', availableQuantity='"+amountTF.getText()+"' WHERE productID='"+pIDTF.getText()+"';");
				cmgr.execute("UPDATE product SET productName='"+nameTF.getText()+"', price='"+priceTF.getText()+"', availableQuantity='"+amountTF.getText()+"' WHERE productID='"+pIDTF.getText()+"';");
				showTable();
			}
			catch(Exception e){
				JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>Please enter valid data");
			}
		}
		else if(tmp.equals("Add")){
			if(pIDTF.getText().equals("") || nameTF.getText().equals("") || amountTF.getText().equals("") || priceTF.getText().equals("")){
				JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>Please enter valid data");
				return;
			}
			try{
				Connection con=null;
				Statement st=null;
				ResultSet rs=null;
				connectionMGR cmgr = new connectionMGR(con,st);
				//System.out.println("INSERT INTO product VALUES('"+pIDTF.getText()+"','"+nameTF.getText()+"','"+priceTF.getText()+"','"+amountTF.getText()+"');");
				cmgr.execute("INSERT INTO product VALUES('"+pIDTF.getText()+"','"+nameTF.getText()+"','"+priceTF.getText()+"','"+amountTF.getText()+"');");
				showTable();
			}
			catch(Exception e){
				JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>Please enter valid data");
			}
		}
		else if(tmp.equals("Remove")){
			if(pIDTF.getText().equals("")){
				JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>Please enter product ID");
				return;
			}
			try{
				Connection con=null;
				Statement st=null;
				ResultSet rs=null;
				connectionMGR cmgr = new connectionMGR(con,st);
				cmgr.execute("DELETE FROM product WHERE productID='"+pIDTF.getText()+"';");
				cmgr.execute("DELETE FROM purchaseinfo WHERE productID='"+pIDTF.getText()+"';");
				showTable();
				JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>Deletion Successful");
			}
			catch(Exception e){
				System.out.println("Exception: "+e.getMessage());
			}
			
		}
		else if(tmp.equals("Logout")){
			Login lg = new Login();
			lg.setVisible(true);
			this.setVisible(false);
		}
	}
	
	public void updatePurchaseInfo(String pid, String userid, int _quantity, String totalPrice){
		String	quantity = Integer.toString(_quantity);
		try{
				Connection con=null;
				Statement st=null;
				ResultSet rs=null;
				connectionMGR cmgr = new connectionMGR(con,st);
				rs = cmgr.getResult("SELECT COUNT(*) AS uniq FROM purchaseinfo WHERE purchaseID='PU_0';");
				rs.next();
				int count = 0;
				String setID = "PU_"+(Integer.toString(count));
				while(rs.getInt("uniq")!=0){
					count++;
					setID = "PU_"+(Integer.toString(count));
					rs = cmgr.getResult("SELECT COUNT(*) AS uniq FROM purchaseinfo WHERE purchaseID='"+setID+"';");
					rs.next();
				}
				String purchaseID = setID;
				cmgr.execute("INSERT INTO purchaseinfo VALUES('"+purchaseID+"','"+pid+"','"+userid+"','"+quantity+"','"+totalPrice+"');");
				if(con!=null)con.close();
				if(rs!=null)rs.close();
				if(st!=null)st.close();
				
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>Invalid Input");
		}
		
	}
	
	public void showTable(){
		DefaultTableModel dtm = new DefaultTableModel();
		pTable.setModel(dtm);
		dtm.addColumn("Product ID");
		dtm.addColumn("Product Name");
		dtm.addColumn("Price");
		dtm.addColumn("Available Quantity");
		try{
				Connection con=null;
				Statement st=null;
				ResultSet rs=null;
				connectionMGR cmgr = new connectionMGR(con,st);
				
				rs = cmgr.getResult("SELECT * FROM product");
				while(rs.next()){
					dtm.addRow(new Object[]{rs.getString("productID"), rs.getString("productName"), "$"+rs.getString("price"), rs.getString("availableQuantity")});
				}
				
				if(con!=null)con.close();
				if(rs!=null)rs.close();
				if(st!=null)st.close();
				
		}
		catch(Exception e){}
		
	}
	
	
	
}