package gustavolessa.ticketing.model;

import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DatabaseOperations {

	public static String[] login(String username, String password){
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
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/ticketing?user=root&password=");

			stmt = conn.createStatement();

			String un = username;
			String pw = password;

			rs = stmt.executeQuery("SELECT * FROM users WHERE name = '"+un+"' AND password = '"+pw+"'");


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

	public static String registerNewUser(String username, String password, String userType){
		String result = "";
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

			if (stmt.executeUpdate("INSERT INTO `users` (`name`, `password`, `type`) VALUES ('"+username+"', '"+password+"', '"+userType+"');") > 0) {
				result = "User Registered!";
            } else {
            		result = "User could not be registered!";
            }
			

		} catch (SQLException ex) {
			// handle any errors
			result = "Some error!";
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}    
		return result;
	}

	public static String changePass(String username, String password, String userType){

		String result = "";
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();

		}catch(Exception e ){}


		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn =
					DriverManager.getConnection("jdbc:mysql://127.0.0.1/ticketing?user=root&password=");

			stmt = conn.createStatement();
			
			int confirm = stmt.executeUpdate("UPDATE `users` SET `password` = '"+password+"' WHERE `name` = '"+username+"' AND `type` = '"+userType+"';");
			if (confirm > 0 ) {
				result = "Password Changed for user: "+username;
            } else {
            		result = "Password could not be changed!";
            }
			

		} catch (SQLException ex) {
			// handle any errors
			result = "Some error occurred";
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}    
		return result;
		
	}

	public static int addTicket(String priority, String techId, String description) {
		int result = 0;
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();

		}catch(Exception e ){}


		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/ticketing?user=root&password=");

			stmt = conn.createStatement();

			result = stmt.executeUpdate("INSERT INTO `tickets` (`priority`, `tech_id`, `description`, `creation_date`) VALUES ('"+priority+"', '"+Integer.parseInt(techId.trim())+"', '"+description+"', UNIX_TIMESTAMP());");
	

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}   
		return result;
	}
	
	public static int updateTicket(String ticketId, String techNumber, String priority, String description) {
		int result = 0;
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();

		}catch(Exception e ){}


		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/ticketing?user=root&password=");

			stmt = conn.createStatement();
		

			result = stmt.executeUpdate("UPDATE `tickets` SET `tech_id` = '"+Integer.parseInt(techNumber.trim())+"', `description` = '"+description+"', `priority` = '"+priority+"' WHERE `id` = '"+Integer.parseInt(ticketId.trim())+"';");
	

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}   
		return result;
	}
	
	public static int closeTicket(String ticketId) {
		int result = 0;
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();

		}catch(Exception e ){}


		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/ticketing?user=root&password=");

			stmt = conn.createStatement();
			
			result = stmt.executeUpdate("UPDATE `tickets` SET `close_date` = UNIX_TIMESTAMP(), `time_taken` = (UNIX_TIMESTAMP() - `creation_date`) WHERE `id` = '"+Integer.parseInt(ticketId.trim())+"';");
	

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}   
		return result;
	}
	
	public static int deleteTicket(String ticketId) {
		int result = 0;
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();

		}catch(Exception e ){}


		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/ticketing?user=root&password=");

			stmt = conn.createStatement();

			result = stmt.executeUpdate("DELETE FROM `tickets` WHERE `id` = '"+Integer.parseInt(ticketId.trim())+"';");
	

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}   
		return result;
	}
	
	public static ResultSet retrieveTickets() {

		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();

		}catch(Exception e ){}


		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/ticketing?user=root&password=");

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			rs = stmt.executeQuery("SELECT t.id, FROM_UNIXTIME(creation_date, '%d/%m/%Y %H:%i') AS creation_date, FROM_UNIXTIME(close_date, '%d/%m/%Y %H:%i') AS close_date, tech_id, name, description, priority, time_taken FROM tickets t INNER JOIN users u ON t.`tech_id` = u.`id`;");

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}    
		return rs;
	}
	
	public static ResultSet viewTickets() {

		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();

		}catch(Exception e ){}


		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/ticketing?user=root&password=");

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			rs = stmt.executeQuery("SELECT t.id, FROM_UNIXTIME(creation_date, '%d/%m/%Y %H:%i') AS creation_date, FROM_UNIXTIME(close_date, '%d/%m/%Y %H:%i') AS close_date, tech_id, name, description, priority, time_taken FROM tickets t INNER JOIN users u ON t.`tech_id` = u.`id`;");

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}    
		return rs;
	}
	
	public static ResultSet getStaff(String type) {

		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();

		}catch(Exception e ){}


		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/ticketing?user=root&password=");

			stmt = conn.createStatement();

			rs = stmt.executeQuery("SELECT * FROM users WHERE type = '"+type+"'");

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}    
		return rs;
	}
	
	public static String[] getPriorityNames() {
		ArrayList<String> priorityNames = new ArrayList<String>();
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();

		}catch(Exception e ){}


		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/ticketing?user=root&password=");

			stmt = conn.createStatement();

			rs = stmt.executeQuery("SELECT DISTINCT priority FROM tickets;");


			while(rs.next()){
				priorityNames.add(rs.getString("priority"));
			} 

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}   
		
		//Populate string holding priority options. It checks according to existing tickets. If there's less than 3 options registered (at least one type missing), the default options will be used
		//If a new option is inserted and used, it will automatically be included in the list.
		String[] defaultOptions = {"Normal","Longterm", "Urgent"};
		String[] result = priorityNames.toArray(new String[priorityNames.size()]);
		if((priorityNames.toArray(new String[priorityNames.size()]).length) < 3) {
			return defaultOptions;
		} else {
			return result;
		}
	}

	public static ResultSet viewTicketDetails(int idToView) {
		
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();

		}catch(Exception e ){}


		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/ticketing?user=root&password=");

			stmt = conn.createStatement();

			rs = stmt.executeQuery("SELECT t.id, FROM_UNIXTIME(creation_date, '%d/%m/%Y %H:%i') AS creation_date, FROM_UNIXTIME(close_date, '%d/%m/%Y %H:%i') AS close_date, tech_id, name, description, priority, time_taken FROM tickets t INNER JOIN users u "
					+ "ON t.`tech_id` = u.`id` WHERE t.id = '"+idToView+"';");


		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}    
		return rs;
	}


}