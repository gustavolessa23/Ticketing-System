package gustavolessa.ticketing.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
		String[] result = gustavolessa.ticketing.model.DatabaseOperations.login(username, password);
		String userID = result[0];
		String userType = result[1];
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
		if(password.equals(confirmPassword)) {
			String userType = admin.getNewUserType();
			String result = gustavolessa.ticketing.model.DatabaseOperations.registerNewUser(username, password, userType);	
			JOptionPane.showMessageDialog(admin, result);
		} else {
			JOptionPane.showMessageDialog(admin, "Passwords don't match!");
		}
	}
	
	public void changePass() {
		String username = admin.getChangePassUsernameField();
		String password = admin.getChangePassPasswordField();
		String confirmPassword = admin.getChangePassConfirmPasswordField();
		if(password.equals(confirmPassword)) {
			String userType = admin.getChangePassUserType();
			String result = gustavolessa.ticketing.model.DatabaseOperations.changePass(username, password, userType);	
			JOptionPane.showMessageDialog(admin, result);
		} else {
			JOptionPane.showMessageDialog(admin, "Passwords don't match!");
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
		String [] result = gustavolessa.ticketing.model.DatabaseOperations.getPriorityNames();
		return result;
	}
	
	public int addTicket(String priority, String techId, String description) {
		int result = gustavolessa.ticketing.model.DatabaseOperations.addTicket(priority, techId, description);
		return result;
	}

	public void viewTicket(int idToView) {
		ResultSet result = gustavolessa.ticketing.model.DatabaseOperations.viewTicketDetails(idToView);
		tech.viewTicketDetails(result);
	}
	
	public int updateTicket(String ticketId, String techNumber, String priority, String description) {
		int result = gustavolessa.ticketing.model.DatabaseOperations.updateTicket(ticketId, techNumber, priority, description);
		return result;
	}
	
	public int deleteTicket(String ticketId) {
		int result = gustavolessa.ticketing.model.DatabaseOperations.deleteTicket(ticketId);
		return result;
	}

	public int closeTicket(String ticketId) {
		int result = gustavolessa.ticketing.model.DatabaseOperations.closeTicket(ticketId);
		return result;
	}
	
	//Method to retrieve from Database a 2d Array containing all Tickets info and return int[] of number of open, closed and total tickets.
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
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("login")){
			login();
			
		} else if(e.getActionCommand().equals("addUserButton")){
			addUser();
			
		} else if(e.getActionCommand().equals("changePassButton")){
			changePass();
		} else if(e.getActionCommand().equals("adminLogout")){
			logout("Admin");
		} else if(e.getActionCommand().equals("techLogout")){
			logout("Tech");
		} else if(e.getActionCommand().equals("managerLogout")){
			logout("Manager");
		} else if(e.getActionCommand().equals("newTicketButton")){
			tech.addTicketWindow();
		} else if(e.getActionCommand().equals("viewTicketsButton")){
			try {
				tech.viewTicketsWindow(getTicketsInfo());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else if(e.getActionCommand().equals("managerRefresh")){
			manager.updateDisplayedData();
		} else if(e.getActionCommand().equals("close")){
			System.exit(0);
		}

	}
	
	

	private String[][] getTicketsInfo() throws SQLException {
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

          if(StringUtils.isBlank(timeRetrieved)) {
        	  	data[rowCounter][5] = "Ticket open";
          } else {
        	  	long seconds = Long.parseLong(timeRetrieved);
        	  	String timeTaken = "";
          	long day = TimeUnit.SECONDS.toDays(seconds);        
          	long hours = TimeUnit.SECONDS.toHours(seconds) - (day *24);
          	long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds)* 60);
          	long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) *60);
              if(day > 0) {
            	  	timeTaken = day+"d "+hours+"h "+minute+"min";
              } else {
            	  	if(hours > 0) {
            	  		timeTaken = hours+"h "+minute+"min";
            	  	} else {
            	  		if(minute > 0) {
            	  			timeTaken = minute+"min";
            	  		} else {
            	  			timeTaken = second+" seconds";
            	  		}
            	  	}
              }
              data[rowCounter][5] = timeTaken;
          }
          
          rowCounter++;  
        }
		return data;	
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}





}
