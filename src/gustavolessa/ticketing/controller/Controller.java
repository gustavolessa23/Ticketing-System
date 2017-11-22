package gustavolessa.ticketing.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import org.apache.commons.lang3.StringUtils;

public class Controller implements ActionListener, WindowListener {
	// Declare and instantiate Main class in order to be able to use it's methods and components
	gustavolessa.ticketing.view.Login login = null;
	gustavolessa.ticketing.view.Admin admin = null;
	gustavolessa.ticketing.view.Tech tech = null;
	gustavolessa.ticketing.view.Manager manager = null;

	// Constructor for controller. Takes a Main object as argument.
	public Controller(gustavolessa.ticketing.view.Login login) {
		this.login = login;
	}

	public Controller(gustavolessa.ticketing.view.Admin admin) {
		this.admin = admin;
	}

	public Controller(gustavolessa.ticketing.view.Tech tech) {
		this.tech = tech;
	}
	
	public Controller(gustavolessa.ticketing.view.Manager manager) {
		this.manager = manager;
	}
	
	public void logout(String userType) {
		int a = -1;
		switch(userType){
		case "Admin":
			a = JOptionPane.showConfirmDialog(admin, "Would you like to logout?", "Confirm", JOptionPane.YES_NO_OPTION);
			if (a==0) {
				admin.dispose();
			}
			break;
		case "Tech":
			a = JOptionPane.showConfirmDialog(admin, "Would you like to logout?", "Confirm", JOptionPane.YES_NO_OPTION);
			if (a==0) {
				tech.dispose();
			}
			break;
		case "Manager":
			a = JOptionPane.showConfirmDialog(admin, "Would you like to logout?", "Confirm", JOptionPane.YES_NO_OPTION);
			if (a==0) {
				manager.dispose();
			}
			break;
		default:
			System.out.println("User type could not be identified!");
			break;
		}
		if(a == 0) {
			new gustavolessa.ticketing.view.Login();
		}
	}
	
	public void login() {
		String username = login.getUserField();
		String password = login.getPassField();
		String userID;
		String userType;
		String[] result = new String[2];
		ResultSet rs = gustavolessa.ticketing.model.DatabaseOperations.login(username, password);
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
		userID = result[0];
		userType = result[1];
		if(userID == null || userType == null) {
			JOptionPane.showMessageDialog(login, "Login details don't match!");
		} else {
			switch(userType){
			case "Admin":
				System.out.println("User is Admin!");
				login.dispose();
				new gustavolessa.ticketing.view.Admin(result[0]);
				break;
			case "Tech":
				System.out.println("User is Tech Support!");
				login.dispose();
				new gustavolessa.ticketing.view.Tech(result[0]);
				break;
			case "Manager":
				System.out.println("User is Manager!");
				login.dispose();
				new gustavolessa.ticketing.view.Manager(result[0]);
				break;
			default:
				System.out.println("User type could not be identified!");
				break;
			}
			String id = result[0];
			System.out.println("ID: " + id);

			String sid = login.getUserField();
			System.out.println("UN: " + sid);
		}
	}
	
	public void addUser() {
		String username = admin.getAddUsernameField();
		String password = admin.getAddPasswordField();
		String confirmPassword = admin.getAddUserConfirmPasswordField();
		String userType = admin.getNewUserType();
		String result="";
		if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password) && StringUtils.isNotBlank(userType)) {
			if(password.equals(confirmPassword)) {
				int retrieved = gustavolessa.ticketing.model.DatabaseOperations.registerNewUser(username, password, userType);	
				if (retrieved>0) {
					result = "User Registered!";
	            } else {
	            		result = "User could not be registered!";
	            }
				JOptionPane.showMessageDialog(admin, result);
				refreshAdmin();
			} else {
				JOptionPane.showMessageDialog(admin, "Passwords don't match!");
			}			
		} else {
			JOptionPane.showMessageDialog(admin, "All fields are required.");
		}
	}
	public void refreshAdmin() {
		String id = admin.getLoggedUserId();
		admin.dispose();
		new gustavolessa.ticketing.view.Admin(id);
	}
	
	public void refreshManager() {
		String id = manager.getUserID();
		manager.updateTicketStats();
		manager.dispose();
		new gustavolessa.ticketing.view.Manager(id);
	}
	
	public void refreshTech() {
		String id = tech.getUserId();
		tech.dispose();
		new gustavolessa.ticketing.view.Tech(id);
	}
	
	public void updateUserInfo() {
		String id = admin.getUpdateInfoUserId();
		String username = admin.getChangePassUsernameField();
		String password = admin.getChangePassPasswordField();
		String confirmPassword = admin.getChangePassConfirmPasswordField();
		String result="";
		if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password) && StringUtils.isNotBlank(id)) {
			if(password.equals(confirmPassword)) {
				String userType = admin.getChangePassUserType();
				int confirm = gustavolessa.ticketing.model.DatabaseOperations.updateUserInfo(id, username, password, userType);
				if (confirm > 0 ) {
					result = username+"'s info updated successfully!";
	            } else {
	            		result = "Info could not be updated!";
	            }
				JOptionPane.showMessageDialog(admin, result);
				refreshAdmin();
			} else {
				JOptionPane.showMessageDialog(admin, "Passwords don't match!");
			}
		} else {
			JOptionPane.showMessageDialog(admin, "All fields are required.");
		}
	}
	
	public String[][] getStaff(String type) {
		ArrayList<String> staffNames = new ArrayList<String>();
		ArrayList<String> staffNumbers = new ArrayList<String>();
		
		ResultSet rs = gustavolessa.ticketing.model.DatabaseOperations.getStaff(type);
		try {
			while(rs.next()){
				staffNames.add(rs.getString("name"));
				staffNumbers.add(rs.getString("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		String[][] result = new String[staffNumbers.size()][2];
		for (int x = 0; x < staffNumbers.size(); x++) {
			result[x][0] = (String)staffNumbers.get(x);
			result[x][1] = (String)staffNames.get(x);
		}
		return result;
	}
	
	public String[] getPriorityNames() {
		ArrayList<String> priorityNames = new ArrayList<String>();
		ResultSet rs = gustavolessa.ticketing.model.DatabaseOperations.getPriorityNames();
		try {
			while(rs.next()){
				priorityNames.add(rs.getString("priority"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
	
	public int addTicket(String priority, String techId, String description) {
		int result = gustavolessa.ticketing.model.DatabaseOperations.addTicket(priority, techId, description);
		return result;
	}

	public void viewTicket(String data) {
		if(StringUtils.isNotBlank(data)) {
			ResultSet result = gustavolessa.ticketing.model.DatabaseOperations.viewTicketDetails(data);
			tech.viewTicketDetails(result);
		}
	}
	
	public void updateTicket(String ticketId, String techNumber, String priority, String description) {
		int updateResponse = gustavolessa.ticketing.model.DatabaseOperations.updateTicket(ticketId, techNumber, priority, description);
	    	if(updateResponse > 0) {
	    		JOptionPane.showMessageDialog(tech, "Ticket updated successfully!");
	    		refreshTech();
	    	} else {
	    		JOptionPane.showMessageDialog(tech, "Ticket could not be updated!");
	    	}
	}

	public void deleteTicket(String ticketId) {
		if(StringUtils.isNotBlank(ticketId)) {
			int n = JOptionPane.showConfirmDialog(tech, "Would you like to delete this ticket?", "Confirmation", JOptionPane.YES_NO_OPTION);
			if (n==0) {		
				int deleteResponse = gustavolessa.ticketing.model.DatabaseOperations.deleteTicket(ticketId);
				if(deleteResponse > 0) {
					JOptionPane.showMessageDialog(tech, "Ticket deleted successfully!");
					refreshTech();
				} else {
					JOptionPane.showMessageDialog(tech, "Ticket could not be deleted!");
				}
			}
		}
	}

	public void closeTicket(String ticketId) {
		if(StringUtils.isNotBlank(ticketId)) {
			int x = JOptionPane.showConfirmDialog(tech, "Would you like to close this ticket?", "Confirmation", JOptionPane.YES_NO_OPTION);
			if (x==0) {	
				int closeResponse = gustavolessa.ticketing.model.DatabaseOperations.closeTicket(ticketId);
				if(closeResponse > 0) {
					JOptionPane.showMessageDialog(tech, "Ticket closed successfully!");
					refreshTech();
				} else {
					JOptionPane.showMessageDialog(tech, "Ticket could not be closed!");
				}
			}
		}
	}
	
	/**
	 * Method to retrieve from Database a 2d Array containing all Tickets info and return int[] of number of open, closed and total tickets.
	 * @return int[]
	 */
	public int[] retrieveTicketStats(){
		ResultSet rs = gustavolessa.ticketing.model.DatabaseOperations.viewTickets();
		int total = 0;
		int open = 0;
		int closed = 0;
		
		try {
			while(rs.next()) {
				total++;
				if(StringUtils.isBlank(rs.getString("close_date"))){
					open++;
				} else {
					closed++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int[] result = {open, closed, total};
		System.out.println(result[0]);
		System.out.println(result[1]);
		System.out.println(result[2]);

		return result;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("login")){
			login();
			
		} else if(e.getActionCommand().equals("addUserButton")){
			addUser();
			
		} else if(e.getActionCommand().equals("changePassButton")){
			updateUserInfo();
		} else if(e.getActionCommand().equals("adminLogout")){
			logout("Admin");
		} else if(e.getActionCommand().equals("techLogout")){
			logout("Tech");
		} else if(e.getActionCommand().equals("managerLogout")){
			logout("Manager");
		} else if(e.getActionCommand().equals("addTicket")){
			tech.addTicketWindow();
		} else if(e.getActionCommand().equals("closeTicket")){
			closeTicket(tech.checkRowId());
		} else if(e.getActionCommand().equals("deleteTicket")){
			deleteTicket(tech.checkRowId());
		} else if(e.getActionCommand().equals("editTicket")){
			viewTicket(tech.checkRowId());
		} else if(e.getActionCommand().equals("close")){
			System.exit(0);
		} else if(e.getActionCommand().equals("refreshAdmin")){
			refreshAdmin();
		} else if(e.getActionCommand().equals("refreshManager")){
			refreshManager();
		} else if(e.getActionCommand().equals("refreshTech")){
			refreshTech();
		}
	}
	
	/**
	 * Method to retrieve all tickets' info as a String[][]
	 * @return String [][]
	 * @throws SQLException
	 */
	public String[][] getTicketsInfo() throws SQLException {
		ResultSet rs = gustavolessa.ticketing.model.DatabaseOperations.viewTickets();
		ResultSetMetaData rsmd;
		String[][] data;
		int maxRow = 100;
		if (rs.last()) {
		    maxRow = rs.getRow();
		    rs.beforeFirst();
		}
		rsmd = rs.getMetaData();
		int maxColumn = rsmd.getColumnCount();
		//Create 2D array to store and display data
		if(maxColumn <=0){
			data = new String[100][100];
		} else {
		data = new String[maxRow][maxColumn];
		}
		
        // loop over results       
		int rowCounter = 0;
		while(rs.next()){
          String id = rs.getString("id");
          data[rowCounter][0] = id;
          String creationDate = rs.getString("creation_date");
          data[rowCounter][1] = creationDate;
          String closeDate = rs.getString("close_date");   
          data[rowCounter][2] = closeDate;                   
          String priority = rs.getString("priority");
          data[rowCounter][3] = priority;          
          String description = rs.getString("description");
          data[rowCounter][4] = description;         
          String timeRetrieved = rs.getString("time_taken");
          data[rowCounter][5] = tech.formatIntervalFromUnix(timeRetrieved);   
          rowCounter++;
		}
		return data;	
	}
	
	/**
	 * Method to retrieve amount of tickets designated to each tech ID, including user ID and name;
	 * @return String[][]
	 * @throws SQLException
	 */
	public String[][] getTicketsPerTech(){
		ResultSet rs = gustavolessa.ticketing.model.DatabaseOperations.viewTicketsPerTech();
		String[][] data;
		int maxRow = 5;
		try {
			if (rs.last()) {
			    maxRow = rs.getRow();
			    rs.beforeFirst();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		data = new String[maxRow][3];

        // loop over results       
		int rowCounter = 0;
		try {
			while(rs.next()){
			  String tech_id = rs.getString("id");
			  data[rowCounter][0] = tech_id;
			  String name = rs.getString("name");
			  data[rowCounter][1] = name;
			  String tickets = rs.getString("tickets");   
			  data[rowCounter][2] = tickets;
			  rowCounter++;  
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;	
	}
	
	/**
	 * Method to return all users info as a String[][]
	 * @return String[][]
	 * @throws SQLException
	 */
	public String[][] getUsersInfo() throws SQLException {
		ResultSet rs = gustavolessa.ticketing.model.DatabaseOperations.viewUsers();
		ResultSetMetaData rsmd;
		String[][] data;
		int maxRow = 100;
		if (rs.last()) {
			maxRow = rs.getRow();
			rs.beforeFirst();
		}
		rsmd = rs.getMetaData();

		int maxColumn = rsmd.getColumnCount();
		//Create 2D array to store and display data
		if(maxColumn <=0){
			data = new String[100][100];
		} else {
			data = new String[maxRow][maxColumn];
		}

		// loop over results       
		int rowCounter = 0;
		while(rs.next()){

			String id = rs.getString("id");
			data[rowCounter][0] = id;

			String name = rs.getString("name");
			data[rowCounter][1] = name;

			String password = rs.getString("password");   
			data[rowCounter][2] = password;

			String type = rs.getString("type");
			data[rowCounter][3] = type; 

			rowCounter++;  
		}
		return data;	
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if (JOptionPane.showConfirmDialog(e.getWindow(), "Are you sure to close?", "Confirmation?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
		    System.exit(0);
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	
	public void viewUser(int idToView) {
		// TODO create this method
		
	}
}
