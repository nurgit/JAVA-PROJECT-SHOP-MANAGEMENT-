import java.lang.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.sql.*;

public class employeeManage extends JFrame implements ActionListener{
	private JLabel userNameLabel, userIDLabel, roleLabel, passLabel, salaryLabel;
	private JTextField userNameTF, userIDTF, passTF, salaryTF;
	private JComboBox roleCombo;
	private JButton findUser, addUser, delUser, updateInfo, back, generateID, generatePass, logout;
	private JPanel panel;
	private Font dfont;
	private String uID;
	
	public employeeManage(String userID){
		super("Employee management");
		this.setSize(650,420);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		uID = userID;
		
		dfont = new Font("New Times Roman", 0, 20);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		userNameLabel = new JLabel("Name: ");
		userNameLabel.setFont(dfont);
		userNameLabel.setBounds(10,130,100,50);
		panel.add(userNameLabel);
		
		userNameTF = new JTextField();
		userNameTF.setFont(dfont);
		userNameTF.setBounds(120, 130, 300, 50);
		panel.add(userNameTF);
		
		passLabel = new JLabel("Password: ");
		passLabel.setFont(dfont);
		passLabel.setBounds(10,70, 100, 50);
		panel.add(passLabel);
		
		passTF = new JTextField();
		passTF.setFont(dfont);
		passTF.setBounds(120, 70, 300, 50);
		passTF.setEnabled(false);
		panel.add(passTF);
		
		userIDLabel = new JLabel("User ID: ");
		userIDLabel.setFont(dfont);
		userIDLabel.setBounds(10,10, 100, 50);
		panel.add(userIDLabel);
		
		userIDTF = new JTextField();
		userIDTF.setFont(dfont);
		userIDTF.setBounds(120, 10, 300, 50);
		panel.add(userIDTF);
		
		generateID = new JButton("Generate ID");
		generateID.setFont(dfont);
		generateID.setBounds(430, 10, 200, 50);
		generateID.addActionListener(this);
		panel.add(generateID);
		
		generatePass = new JButton("Generate Pass");
		generatePass.setFont(dfont);
		generatePass.setBounds(430, 70, 200, 50);
		generatePass.addActionListener(this);
		panel.add(generatePass);
		
		roleLabel = new JLabel("Role: ");
		roleLabel.setBounds(10, 190, 100, 50);
		roleLabel.setFont(dfont);
		panel.add(roleLabel);
		
		String items[] = {"general","manager"};
		roleCombo = new JComboBox(items);
		roleCombo.setFont(dfont);
		roleCombo.setBounds(120, 190, 300, 50);
		panel.add(roleCombo);
		
		salaryLabel = new JLabel("Salary: ");
		salaryLabel.setFont(dfont);
		salaryLabel.setBounds(10, 250, 100, 50);
		panel.add(salaryLabel);
		
		salaryTF = new JTextField();
		salaryTF.setBounds(120, 250, 300, 50);
		salaryTF.setFont(dfont);
		panel.add(salaryTF);
		
		findUser = new JButton("Find User");
		findUser.setFont(dfont);
		findUser.setBounds(110,310, 150, 50);
		findUser.addActionListener(this);
		panel.add(findUser);
		
		addUser = new JButton("Add User");
		addUser.setFont(dfont);
		addUser.setBounds(270, 310, 150, 50);
		addUser.addActionListener(this);
		panel.add(addUser);
		
		delUser = new JButton("Remove User");
		delUser.setFont(dfont);
		delUser.setBounds(430, 310, 200, 50);
		delUser.setBackground(Color.RED);
		delUser.addActionListener(this);
		panel.add(delUser);
		
		back = new JButton("Back");
		back.setFont(dfont);
		back.setBounds(10,310, 90, 50);
		back.addActionListener(this);
		panel.add(back);
		
		updateInfo = new JButton("Update Info");
		updateInfo.setFont(dfont);
		updateInfo.setBounds(430,130,200,85);
		updateInfo.addActionListener(this);
		panel.add(updateInfo);
		
		logout = new JButton("Logout");
		logout.setFont(dfont);
		logout.setBounds(430,225, 200,75);
		logout.addActionListener(this);
		logout.setBackground(Color.RED);
		panel.add(logout);

		this.add(panel);
		
	}
	public void actionPerformed(ActionEvent ae){
		String tmp = ae.getActionCommand();
		if(tmp.equals("Back")){
			employeeHome eh = new employeeHome(uID);
			eh.setVisible(true);
			this.setVisible(false);
		}
		else if(tmp.equals("Generate ID")){
			Connection con=null;
			Statement st=null;
			ResultSet rs=null;
			connectionMGR cmgr = new connectionMGR(con, st);
			try{
				int count = 0;
				String setID = "E_"+(Integer.toString(count));
				rs = cmgr.getResult("Select COUNT(*) AS uniq FROM employee WHERE userID='"+setID+"';");
				rs.next();
				while(rs.getInt("uniq")!=0){
					count++;
					setID = "E_"+(Integer.toString(count));
					rs = cmgr.getResult("Select COUNT(*) AS uniq FROM employee WHERE userID='"+setID+"';");
					rs.next();
				}
				userIDTF.setText(setID);
				//System.out.println(setID);
				if(con!=null)con.close();
				if(st!=null)st.close();
				if(rs!=null)rs.close();
			}
			catch(Exception e){
				System.out.println("Exception: "+e.getMessage());
			}	
		}
		else if(userIDTF.getText().equals(uID)){
			JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>Cannot modify own information through this interface");
			return;
		}
		else if(tmp.equals("Generate Pass")){
			Random rand = new Random();
			int setPass = rand.nextInt(10000)+999;
			passTF.setText(Integer.toString(setPass));
		}
		else if(tmp.equals("Remove User")){
			if(userIDTF.getText().equals("")){
				JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>Please provide user ID to Add or Remove users");
				return;
			}
			try{
				Connection con=null;
				Statement st=null;
				ResultSet rs=null;
				connectionMGR cmgr = new connectionMGR(con,st);
				String query="DELETE FROM employee WHERE userID='"+userIDTF.getText()+"';";
				cmgr.execute(query);
				query = "DELETE FROM login WHERE userID='"+userIDTF.getText()+"';";
				cmgr.execute(query);
				JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>User: "+userIDTF.getText()+" Has been removed from database");
				if(con!=null)con.close();
				if(rs!=null)rs.close();
				if(st!=null)st.close();
			}
			catch(Exception e){}
		}
		else if(tmp.equals("Add User")){
			if(userIDTF.getText().equals("") || passTF.getText().equals("") || salaryTF.getText().equals("")){
				JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>Please fill out the required fields");
				return;
			}
			try{
				Connection con=null;
				Statement st=null;
				ResultSet rs=null;
				connectionMGR cmgr = new connectionMGR(con,st);
				String newName = userNameTF.getText();
				String newID = userIDTF.getText();
				String newPass = passTF.getText();
				String newRole = roleCombo.getSelectedItem().toString();
				String newSalary = salaryTF.getText();
				//System.out.println(newName+" "+newID+" "+newPass+" "+newRole+" "+newSalary);
				String query1 = "INSERT INTO employee VALUES('"+newID+"','"+newName+"','"+newRole+"','"+newSalary+"');";
				String query2 = "INSERT INTO login VALUES('"+newID+"','"+newPass+"','"+"1');";
				//System.out.println(query2);
				cmgr.execute(query1);
				cmgr.execute(query2);
				userNameTF.setText("");
				salaryTF.setText("");
				passTF.setText("");
				if(con!=null)con.close();
				if(rs!=null)rs.close();
				if(st!=null)st.close();
				JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>User: "+userIDTF.getText()+" Has been added to the database");
			}
			catch(Exception e){}
		}
		else if(tmp.equals("Find User")){
			if(userIDTF.getText().equals("")){
				JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>Please provide a user ID");
				return;
			}
			Connection con=null;
			Statement st=null;
			ResultSet rs=null;
			try{
				connectionMGR cmgr = new connectionMGR(con,st);
				rs = cmgr.getResult("SELECT employeeName, role, salary FROM employee WHERE userID='"+userIDTF.getText()+"';");
				if(rs.next()){
					userNameTF.setText(rs.getString("employeeName"));
					roleCombo.setSelectedItem(rs.getString("role"));
					salaryTF.setText(rs.getString("salary"));
					passTF.setText("");
				}
				else JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>User not found");
				if(con!=null)con.close();
				if(rs!=null)rs.close();
				if(st!=null)st.close();
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
				catch(Exception e){}
			}
			if(userNameTF.getText().equals(""))JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>User not found");
		}
		else if(tmp.equals("Update Info")){
			if(userIDTF.getText().equals("") || userNameTF.getText().equals("") || salaryTF.getText().equals("")){
				JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>Please fill out the required fields");
				return;
			}
			try{
				Connection con=null;
				Statement st=null;
				ResultSet rs=null;
				connectionMGR cmgr = new connectionMGR(con,st);
				String query = "UPDATE employee SET role='"+roleCombo.getSelectedItem().toString()+"', salary='"+salaryTF.getText()+"' WHERE userID='"+userIDTF.getText()+"';";
				cmgr.execute(query);
				JOptionPane.showMessageDialog(this, "<html><font face='New Times Roman' size='6'>Information Updated");
				if(con!=null)con.close();
				if(rs!=null)rs.close();
				if(st!=null)st.close();
			}
			catch(Exception e){}
		}
		else if(tmp.equals("Logout")){
			Login lg = new Login();
			lg.setVisible(true);
			this.setVisible(false);
		}
	}
	
}