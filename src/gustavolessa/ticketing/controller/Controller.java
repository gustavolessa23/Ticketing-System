package gustavolessa.ticketing.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
	
	public void loginPerformed(String[] result) {
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("login")){
			loginPerformed(gustavolessa.ticketing.model.LoginWithDatabase.loginWithDatabase(login.getUserField(), login.getPassField()));
	
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


}
