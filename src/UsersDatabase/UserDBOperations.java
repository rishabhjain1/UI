package UsersDatabase;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDBOperations {
	
	private UserDBSession usr_dbConnection;
	
	public boolean doValidate(String usr_name) throws SQLException, ClassNotFoundException {
		
		usr_dbConnection = new UserDBSession();
		usr_name = "\'"+usr_name+"\'";
		boolean check;
		String query = "SELECT COUNT(*) FROM USER_AUTH WHERE USER_NAME = "+usr_name;
		ResultSet rs = usr_dbConnection.runQuery(query);
		rs.next();
		if(rs.getInt(1) == 0) {
			check = true;
		}
		else {
			check = false;
		}
		usr_dbConnection.close();
		return check;	
		
	}
	
	public void addAccountDetails(String usr_name, String usr_pass) throws ClassNotFoundException, SQLException {
		
		updateUserDatabase(usr_name, usr_pass);
		
	}
	
	private void updateUserDatabase(String usr_name, String usr_pass) throws ClassNotFoundException, SQLException {
		usr_dbConnection = new UserDBSession();
		usr_name = "\'"+usr_name+"\'";
		usr_pass = "\'"+usr_pass+"\'";
		String query = "INSERT INTO USER_AUTH (USER_NAME,USER_PASS) VALUES("+ usr_name +","+usr_pass+")";
		//String query ="INSERT INTO USER_AUTH (USER_NAME,USER_PASS)" + "VALUES(?,?)";
		ResultSet rs = usr_dbConnection.runQuery(query);	
		usr_dbConnection.close();
	}
	
	public boolean doAuthenticate(String usr_name, String usr_pass) throws SQLException, ClassNotFoundException {
		
		usr_dbConnection = new UserDBSession();
		usr_name = "\'"+usr_name+"\'";
		boolean check;
		
		
		String query = "SELECT USER_PASS FROM USER_AUTH WHERE USER_NAME = "+usr_name;
		ResultSet rs = usr_dbConnection.runQuery(query);		
		rs.next();
				
		if(rs.getString(1).equals( usr_pass)) {
			check = true;
		}
		else {
			check = false;
		}
		usr_dbConnection.close();
		return check;	
		
	}
	
	public int getAccessLevel(String usr_name) throws ClassNotFoundException, SQLException {
		usr_dbConnection = new UserDBSession();
		usr_name = "\'"+usr_name+"\'";
		String query = "SELECT USER_ACCESS FROM USER_AUTH WHERE USER_NAME = "+usr_name;
		ResultSet rs = usr_dbConnection.runQuery(query);
		rs.next();
		return rs.getInt(1);
	}
	
	
}
