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
	
	String userID;
	gustavolessa.ticketing.controller.Controller controller = new gustavolessa.ticketing.controller.Controller(this);
	String[] userTypes = {"Admin", "Tech Support", "Manager"};



	public Admin(String userID){
		
		this.userID = userID;
		setSize(400,600);
		setVisible(true);
		this.setLayout(new GridLayout(3,1));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JLabel admin = new JLabel();
		admin.setText("You are logged in as Admin, ID: "+ userID);
		this.add(admin);
		JPanel addUserPanel = new JPanel();
		JPanel changePassPanel = new JPanel();
		JLabel addUsernameLabel = new JLabel("Username: ");
		JTextField addUsernameField = new JTextField();
		JTextField addPasswordField = new JTextField();
		JLabel addUserPassLabel = new JLabel("Password: ");
		JComboBox userTypesList = new JComboBox(userTypes);
		JButton addUserButton = new JButton("Register");
		
		Border addUserBorder = BorderFactory.createTitledBorder("Add User");
		
		addUserPanel.setLayout(new GridLayout(5,1));
		addUserPanel.add(addUsernameLabel);
		addUserPanel.add(addUsernameField);
		addUserPanel.add(addUserPassLabel);
		addUserPanel.add(addPasswordField);
		addUserPanel.add(userTypesList);
		addUserPanel.add(addUserButton);
		addUserPanel.setBorder(addUserBorder);
		this.add(addUserPanel);
		
	
		validate();
		repaint();
	}

}
