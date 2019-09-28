import java.lang.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.sql.*;

public class connectionMGR{
	private Statement s;
	public connectionMGR(Connection con, Statement st){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			//System.out.println("driver loaded");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/shop","root","");
			//System.out.println("connected");
			st = con.createStatement();
			//System.out.println("statement created");
		}
		catch(Exception e){
			System.out.println("Exception: "+e.getMessage());
		}
		s = st;
	}
	public ResultSet getResult(String query){
		ResultSet ret=null;
		try{
			ret = s.executeQuery(query);
		}
		catch(Exception e){}
		return ret;
	}
	public void execute(String query){
		try{
			s.execute(query);
		}
		catch(Exception e){
			System.out.println("Exception: "+e.getMessage());
		}
	}
	
}