package ShoppingDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBSession {
	
	private String DB_URL;
	private String USER ;
	private String PASS ;
	
	private Connection  conn;
	private Statement stmt;
	
	
	// constructor 
	public DBSession() throws SQLException, ClassNotFoundException {
		DB_URL = "jdbc:mysql://localhost:3306/PRODUCT_INVENTORY";
		USER = "root";
		PASS = "Msci@123";
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(DB_URL,USER,PASS);
		stmt = conn.createStatement();		
	}
	
	// resultset to run the queries
	public ResultSet runQuery(String query) throws SQLException{
		if (query.toLowerCase().startsWith("select")) { 
			return stmt.executeQuery(query); 
		}
		else { 
			stmt.executeUpdate(query);
			stmt.executeQuery("commit");
			return null; 
		}
	
	}
	
	// closing connection
	
	public void close() throws SQLException{
		stmt.close();
		conn.close();
	}
}
