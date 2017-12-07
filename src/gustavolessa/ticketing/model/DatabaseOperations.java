package gustavolessa.ticketing.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	public static int intConnection(String query) {
		int result = 0;
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();

		}catch(Exception e ){}


		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/ticketing?user=root&password=");

			stmt = conn.createStatement();

			result = stmt.executeUpdate(query);

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}   
		return result;

	}

	public static ResultSet login(String username, String password){
		String query = "SELECT * FROM users WHERE name = '"+username+"' AND password = '"+password+"';";
		return defaultConnection(query); 
	}
	
	public static ResultSet getHash(String username) {
		String query = "SELECT * FROM users WHERE name = '"+username+"';";
		return defaultConnection(query);
	}

	public static int registerNewUser(String username, String password, String userType){
		String query = "INSERT INTO `users` (`name`, `password`, `type`) VALUES ('"+username+"', '"+password+"', '"+userType+"');";
		return intConnection(query);
	}

	public static int updateUserInfo(String id, String username, String password, String userType){
		String query = "UPDATE `users` SET `password` = '"+password+"', `name` = '"+username+"', `type` = '"+userType+"' WHERE `id`= '"+id+"';";
		return intConnection(query);		
	}

	public static int addTicket(String priority, String techId, String description) {
		String query = "INSERT INTO `tickets` (`priority`, `tech_id`, `description`, `creation_date`) VALUES ('"+priority+"', '"+Integer.parseInt(techId.trim())+"', '"+description+"', UNIX_TIMESTAMP());";
		return intConnection(query);
	}
	
	public static int updateTicket(String ticketId, String techNumber, String priority, String description) {
		String query = "UPDATE `tickets` SET `tech_id` = '"+Integer.parseInt(techNumber.trim())+"', `description` = '"+description+"', `priority` = '"+priority+"' WHERE `id` = '"+Integer.parseInt(ticketId.trim())+"';";
		return intConnection(query);
	}
	
	public static int closeTicket(String ticketId) {
		String query = "UPDATE `tickets` SET `close_date` = UNIX_TIMESTAMP(), `time_taken` = (UNIX_TIMESTAMP() - `creation_date`) WHERE `id` = '"+Integer.parseInt(ticketId.trim())+"';";
		return intConnection(query);
	}
	
	public static int deleteTicket(String ticketId) {
		String query = "DELETE FROM `tickets` WHERE `id` = '"+Integer.parseInt(ticketId.trim())+"';";
		return intConnection(query);
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
	
	public static ResultSet checkUsername(String name) {
		String query = "SELECT * FROM users WHERE name = '"+name+"';";
		return defaultConnection(query);
	}
	
	public static ResultSet getStaff(String type) {
		String query = "SELECT * FROM users WHERE type = '"+type+"';";
		return defaultConnection(query);
	}
	
	public static ResultSet getPriorityNames() {
		String query = "SELECT DISTINCT priority FROM tickets;";
		return defaultConnection(query);
	}

	public static ResultSet viewTicketDetails(String data) {	
		String query = "SELECT t.id, FROM_UNIXTIME(creation_date, '%d/%m/%Y %H:%i') AS creation_date, FROM_UNIXTIME(close_date, '%d/%m/%Y %H:%i') AS close_date, tech_id, name, description, priority, time_taken FROM tickets t INNER JOIN users u "
					+ "ON t.`tech_id` = u.`id` WHERE t.id = '"+data+"';";   
		return defaultConnection(query);
	}

}