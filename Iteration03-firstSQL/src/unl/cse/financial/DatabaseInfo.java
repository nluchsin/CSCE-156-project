//This class returns a connection variable for connection to sql.
package unl.cse.financial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseInfo {

	public static final String url = "jdbc:mysql://cse.unl.edu/nluchsinger";
	public static final String username = "nluchsinger";
	public static final String password = "J3E-jw";
	
	//Returns connection variable
	public Connection getCon(){
		try{
			Connection con = DriverManager.getConnection(this.url, this.username, this.password);
			return con;
		}
		catch (SQLException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
