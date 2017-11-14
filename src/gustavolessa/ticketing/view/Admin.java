package gustavolessa.ticketing.view;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

//import gustavolessa.ticketing.controller.Controller;

public class Admin extends JFrame{

	gustavolessa.ticketing.controller.Controller controller = new gustavolessa.ticketing.controller.Controller(this);
	private JTextField addUsernameField;
	private JTextField addPasswordField;
	private JComboBox newUserTypesList;
	private JComboBox changePassUserTypesList;
	private String[] userTypes = {"Admin", "Tech Support", "Manager"};
	private String userID;
	private JTextField changePassUsernameField;
	private JTextField changePassPasswordField;
	private JTextField changePassConfirmPasswordField;

	//Create getters for private variables
	public String getNewUserType() {
		String selected = (String)newUserTypesList.getSelectedItem();
		if(selected.equals("Tech Support")) {
			return "Tech";
		} else {
		return selected;
		}
	}
	public String getChangePassUserType() {
		String selected = (String)changePassUserTypesList.getSelectedItem();
		if(selected.equals("Tech Support")) {
			return "Tech";
		} else {
		return selected;
		}
	}
	public String getAddUsernameField() {
		return addUsernameField.getText();
	}
	public String getAddPasswordField() {
		return addPasswordField.getText();
	}
	public String getChangePassUsernameField() {
		return changePassUsernameField.getText();
	}
	public String getChangePassPasswordField() {
		return changePassPasswordField.getText();
	}
	public String getChangePassConfirmPasswordField() {
		return changePassConfirmPasswordField.getText();
	}
	
	//Constructor that takes userID as parameter
	public Admin(String userID){
		//TODO change layout
		
		this.userID = userID;
		setSize(400,600);
		setVisible(true);
		this.setLayout(new GridLayout(3,1));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Admin");
		
		//Create panel "Add User", and create its components.
		JPanel addUserPanel = new JPanel();
		JLabel addUsernameLabel = new JLabel("Username: ");
		addUsernameField = new JTextField();
		addPasswordField = new JTextField();
		JLabel addUserPassLabel = new JLabel("Password: ");
		newUserTypesList = new JComboBox(userTypes);
		JButton addUserButton = new JButton("Register");
		
		//Add components to addUserPanel
		addUserPanel.setLayout(new GridLayout(6,1));
		addUserPanel.add(newUserTypesList);
		addUserPanel.add(addUsernameLabel);
		addUserPanel.add(addUsernameField);
		addUserPanel.add(addUserPassLabel);
		addUserPanel.add(addPasswordField);
		addUserPanel.add(addUserButton);
		
		//Create panel "Change Password", and create its components.
		JPanel changePassPanel = new JPanel();
		changePassUserTypesList = new JComboBox(userTypes);
		JLabel changePassUsernameLabel = new JLabel("Username: ");
		changePassUsernameField = new JTextField();
		JLabel changePassPassLabel = new JLabel("Password: ");
		changePassPasswordField = new JTextField();
		JLabel changePassConfirmPassLabel = new JLabel("Confirm password: ");
		changePassConfirmPasswordField = new JTextField();
		JButton changePassButton = new JButton("Change Password");
		
		//Add components to changePassPanel
		changePassPanel.setLayout(new GridLayout(8,1));
		changePassPanel.add(changePassUserTypesList);
		changePassPanel.add(changePassUsernameLabel);
		changePassPanel.add(changePassUsernameField);
		changePassPanel.add(changePassPassLabel);
		changePassPanel.add(changePassPasswordField);
		changePassPanel.add(changePassConfirmPassLabel);
		changePassPanel.add(changePassConfirmPasswordField);
		changePassPanel.add(changePassButton);
		
		//Add ActionListener and ActionCommands for buttons
		addUserButton.addActionListener(controller);
		addUserButton.setActionCommand("addUserButton");
		changePassButton.addActionListener(controller);
		changePassButton.setActionCommand("changePassButton");
		
		//Create and set borders with title for both panels
		Border addUserBorder = BorderFactory.createTitledBorder("Add User");
		addUserPanel.setBorder(addUserBorder);
		Border changePassBorder = BorderFactory.createTitledBorder("Change Password");
		changePassPanel.setBorder(changePassBorder);
		
		//Create panel and button to Logout
		JPanel logoutPanel = new JPanel();
		JButton logout = new JButton("Logout");
		logout.addActionListener(controller);
		logout.setActionCommand("adminLogout");
		logoutPanel.add(logout);
		
		

		//Add panel to frame
		this.add(addUserPanel);
		this.add(changePassPanel);
		this.add(logoutPanel);
		
		
		validate();
		repaint();
	}

}
