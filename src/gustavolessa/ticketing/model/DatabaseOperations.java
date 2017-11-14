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

			result = stmt.executeUpdate("INSERT INTO `tickets` (`priority`, `tech_id`, `description`) VALUES ('"+priority+"', '"+Integer.parseInt(techId.trim())+"', '"+description+"');");
	

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

			result = stmt.executeUpdate("UPDATE `tickets` SET `close_date` = CURRENT_TIMESTAMP() WHERE `id` = '"+Integer.parseInt(ticketId.trim())+"';");
	

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

			rs = stmt.executeQuery("SELECT t.id, creation_date, close_date, tech_id, name, description, priority FROM tickets t INNER JOIN users u "
					+ "ON t.`tech_id` = u.`id`;");

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}    
		return rs;
	}
	
	public static String[][] getStaff(String type) {
		ArrayList<String> staffNames = new ArrayList<String>();
		ArrayList<String> staffNumbers = new ArrayList<String>();
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


			while(rs.next()){
				staffNames.add(rs.getString("name"));
				staffNumbers.add(rs.getString("id"));
			} 
			
			String[][] result = new String[staffNumbers.size()][2];
			for (int x = 0; x < staffNumbers.size(); x++) {
				result[x][0] = (String)staffNumbers.get(x);
				result[x][1] = (String)staffNames.get(x);
			}
			return result;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}    
		return null;
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
		String[] result = priorityNames.toArray(new String[priorityNames.size()]);

		return result;
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

			rs = stmt.executeQuery("SELECT t.id, creation_date, close_date, tech_id, name, description, priority FROM tickets t INNER JOIN users u "
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