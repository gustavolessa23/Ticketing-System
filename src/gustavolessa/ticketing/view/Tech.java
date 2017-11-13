package gustavolessa.ticketing.view;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Tech extends JFrame{
	
	static gustavolessa.ticketing.controller.Controller controller;
	private JTextField addUsernameField;
	private JTextField addPasswordField;
	private JComboBox priority;
	private JComboBox techEmployee;
	private String[][] techStaff;
	private String[] techNames;
	
	

	//Create getters for private variables
	public String getNewUserType() {
		String selected = (String)priority.getSelectedItem();
		if(selected.equals("Tech Support")) {
			return "Tech";
		} else {
		return selected;
		}
	}
	public String getChangePassUserType() {
		String selected = (String)techEmployee.getSelectedItem();
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

	public Tech(String userID){
		
		controller = new gustavolessa.ticketing.controller.Controller(this);
		techStaff = controller.getStaff("Tech");
		techNames = new String[techStaff.length];
		setSize(300,300);
		setVisible(true);
		this.setLayout(new GridLayout(6,1));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Tech Support");
	
		
		//Create panel "Add User", and create its components.
		JPanel addUserPanel = new JPanel();
		JLabel addUsernameLabel = new JLabel("Username: ");
		addUsernameField = new JTextField();
		addPasswordField = new JTextField();
		JLabel addUserPassLabel = new JLabel("Password: ");
		priority = new JComboBox(controller.getPriorityNames());

		for (int x = 0; x < techStaff.length; x++) {
			techNames[x] = techStaff[x][1];
		}
		techEmployee = new JComboBox(techNames);
		
		JButton addUserButton = new JButton("Register");
		
		//Add components to addUserPanel
		addUserPanel.setLayout(new GridLayout(6,1));
		addUserPanel.add(priority);
		addUserPanel.add(addUsernameLabel);
		addUserPanel.add(addUsernameField);
		addUserPanel.add(addUserPassLabel);
		addUserPanel.add(addPasswordField);
		addUserPanel.add(addUserButton);
		
		//Add ActionListener and ActionCommands for buttons
		addUserButton.addActionListener(controller);
		addUserButton.setActionCommand("addUserButton");
		
		//Create and set borders with title for both panels
		Border addUserBorder = BorderFactory.createTitledBorder("Add User");
		addUserPanel.setBorder(addUserBorder);
		
		//Create panel and button to Logout
		JPanel logoutPanel = new JPanel();
		JButton logout = new JButton("Logout");
		logout.addActionListener(controller);
		logout.setActionCommand("adminLogout");
		logoutPanel.add(logout);
		
		JButton newTicket = new JButton("Create new ticket");
		newTicket.addActionListener(controller);
		newTicket.setActionCommand("newTicketButton");
		this.add(newTicket);
		
		//Add panel to frame

	//	this.add(logoutPanel);
		
		validate();
		repaint();
	}
	
	public void addTicketWindow() {


		// Creating panel and items
		JPanel panel = new JPanel(new GridLayout(5, 1));
		JLabel priorityLabel = new JLabel("Priority: ");
		JLabel techStaffLabel = new JLabel("Tech Staff: ");
		JTextArea descriptionArea = new JTextArea();


        panel.add(priorityLabel);
        panel.add(priority);
        panel.add(techStaffLabel);
        panel.add(techEmployee);
        panel.add(descriptionArea);
        //panel.add(addTicketButton);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Ticket",
        		JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
        		String selectedTechId = null;
	        	for (int x = 0; x < techStaff.length; x++) {
	        		if(techStaff[x][1].equals(techEmployee.getSelectedItem())) {
	        			selectedTechId = techStaff[x][0];
	        		}
	        	}
	        	System.out.println(selectedTechId);
	        	controller.addTicket((String)priority.getSelectedItem(), selectedTechId, descriptionArea.getText());
        } else {
        	System.out.println("Cancelled");
        }
    }


}
