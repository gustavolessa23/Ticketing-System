package gustavolessa.ticketing.controller;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;


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
		switch(userType){
		case "Admin":
			admin.dispose();
			break;
		case "Tech":
			tech.dispose();
			break;
		case "Manager":
			manager.dispose();
			break;
		default:
			System.out.println("User type could not be identified!");
			break;
		}
		new gustavolessa.ticketing.view.Login();
	}
	
	public void login() {
		String username = login.getUserField();
		String password = login.getPassField();
		String[] result = gustavolessa.ticketing.model.DatabaseOperations.loginWithDatabase(username, password);
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
		String userType = admin.getNewUserType();
		String result = gustavolessa.ticketing.model.DatabaseOperations.registerNewUser(username, password, userType);	
		JOptionPane.showMessageDialog(admin, result);
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
		return gustavolessa.ticketing.model.DatabaseOperations.getStaff(type);
	}
	
	public String[] getPriorityNames() {
		String [] result = gustavolessa.ticketing.model.DatabaseOperations.getPriorityNames();
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
		} else if(e.getActionCommand().equals("newTicketButton")){
			tech.addTicketWindow();
		} 

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

	public int addTicket(String priority, String techId, String description) {
		int result = gustavolessa.ticketing.model.DatabaseOperations.addTicket(priority, techId, description);
		return result;
	}


}
