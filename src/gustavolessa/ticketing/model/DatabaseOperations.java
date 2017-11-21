package gustavolessa.ticketing.model;

import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DatabaseOperations {

	public static ResultSet defaultConnection(String query) {
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();

		}catch(Exception e ){}


		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/ticketing?user=root&password=");

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			rs = stmt.executeQuery(query);


		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}    
		return rs;
			
	}
	public static String[] login(String username, String password){
		String userID;
		String userType;
		String[] result = new String[2];
		String un = username;
		String pw = password;
		String query = "SELECT * FROM users WHERE name = '"+un+"' AND password = '"+pw+"';";

		ResultSet rs = defaultConnection(query);

		try {
			while(rs.next()){
				userID = rs.getString("id");
				userType = rs.getString("type");
				result[0] = userID;
				result[1] = userType;
			}
		} catch (SQLException e) {
			e.printStackTrace();
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

	public static String updateUserInfo(String id, String username, String password, String userType){

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
			
			int confirm = stmt.executeUpdate("UPDATE `users` SET `password` = '"+password+"', `name` = '"+username+"', `type` = '"+userType+"' WHERE `id`= '"+id+"';");
			if (confirm > 0 ) {
				result = username+"'s info updated successfully!";
            } else {
            		result = "Info could not be updated!";
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
		String query = "SELECT t.id, FROM_UNIXTIME(creation_date, '%d/%m/%Y %H:%i') AS creation_date, FROM_UNIXTIME(close_date, '%d/%m/%Y %H:%i') AS close_date, tech_id, name, description, priority, time_taken FROM tickets t INNER JOIN users u ON t.`tech_id` = u.`id`;";
		return defaultConnection(query);
	}
	
	public static ResultSet viewTicketsPerTech() {
		String query = "SELECT u.id, u.name, COUNT(t.id) AS tickets from users u LEFT JOIN tickets t ON t.tech_id = u.id WHERE u.type = 'Tech' GROUP BY u.id;";
		return defaultConnection(query);
	}
	
	public static ResultSet viewTickets() {
		String query = "SELECT t.id, FROM_UNIXTIME(creation_date, '%d/%m/%Y %H:%i') AS creation_date, FROM_UNIXTIME(close_date, '%d/%m/%Y %H:%i') AS close_date, tech_id, name, description, priority, time_taken FROM tickets t INNER JOIN users u ON t.`tech_id` = u.`id`;";
		return defaultConnection(query);
	}
	
	public static ResultSet viewUsers() {
		String query = "SELECT * FROM users;"; 
		return defaultConnection(query);
	}
	
	public static ResultSet getStaff(String type) {
		String query = "SELECT * FROM users WHERE type = '"+type+"';";
		return defaultConnection(query);
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
		
		String query = "SELECT t.id, FROM_UNIXTIME(creation_date, '%d/%m/%Y %H:%i') AS creation_date, FROM_UNIXTIME(close_date, '%d/%m/%Y %H:%i') AS close_date, tech_id, name, description, priority, time_taken FROM tickets t INNER JOIN users u "
					+ "ON t.`tech_id` = u.`id` WHERE t.id = '"+idToView+"';";   
		return defaultConnection(query);
	}

}