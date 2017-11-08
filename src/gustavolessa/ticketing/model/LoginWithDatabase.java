package gustavolessa.ticketing.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LoginWithDatabase {

	public static String[] loginWithDatabase(String username, String password){
		String userID;
		String userType;
		String[] result = new String[2];
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();

		}catch(Exception e ){}


		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn =
					DriverManager.getConnection("jdbc:mysql://127.0.0.1/ticketing?user=root&password=");

			// Do something with the Connection
			stmt = conn.createStatement();

			// or alternatively, if you don't know ahead of time that
			// the query will be a SELECT...

			String un = username;
			String pw = password;

			if (stmt.execute("SELECT * FROM users WHERE name = '"+un+"' AND password = '"+pw+"'")) {
				rs = stmt.getResultSet();
			}

			// loop over results

			while(rs.next()){
				userID = rs.getString("id");
				userType = rs.getString("type");
				result[0] = userID;
				result[1] = userType;
			} 

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}    
		return result;
	}


}