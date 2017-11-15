package gustavolessa.ticketing.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

//import gustavolessa.ticketing.controller.Controller;

public class Admin extends JFrame{

	gustavolessa.ticketing.controller.Controller controller = new gustavolessa.ticketing.controller.Controller(this);
	private JTextField addUsernameField;
	private JPasswordField addPasswordField;
	private JComboBox newUserTypesList;
	private JComboBox changePassUserTypesList;
	private String[] userTypes = {"Admin", "Tech Support", "Manager"};
	private String userID;
	private JTextField changePassUsernameField;
	private JPasswordField changePassPasswordField;
	private JPasswordField addUserConfirmPasswordField;
	private JPasswordField changePassConfirmPasswordField;

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
		return new String(addPasswordField.getPassword());
	}
	public String getChangePassUsernameField() {
		return changePassUsernameField.getText();
	}
	public String getChangePassPasswordField() {
		return new String(changePassPasswordField.getPassword());
	}
	public String getChangePassConfirmPasswordField() {
		return new String(changePassConfirmPasswordField.getPassword());
	}
	
	public String getAddUserConfirmPasswordField() {
		return new String(addUserConfirmPasswordField.getPassword());
	}
	
	//Constructor that takes userID as parameter
	public Admin(String userID){
		this.userID = userID;
		setSize(550,300);
		this.setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Admin Dashboard");

		this.setLocationRelativeTo(null);
		
		//Add Menu containing File -> Close
		System.setProperty("apple.laf.useScreenMenuBar", "true");
	      JMenuBar topBar = new JMenuBar();
	        this.setJMenuBar(topBar);
	        JMenu file = new JMenu("File");
	          topBar.add(file);
	              JMenuItem close = new JMenuItem("Close");
	              file.add(close);
		              close.addActionListener(controller);
		              close.setActionCommand("close");
		
		JPanel centerPanel = new JPanel(){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(700, 450);
            }
        };
		centerPanel.setLayout(new GridLayout(1,2));
		
		//Create panel "Add User", and create its components.
		JPanel addUserPanel = new JPanel(){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(300, 400);
            }
        };
		JLabel addUsernameLabel = new JLabel("Username: ");
		addUsernameField = new JTextField();
		addPasswordField = new JPasswordField();
		JLabel addUserPassLabel = new JLabel("Password: ");
		JLabel addUserConfirmPassLabel = new JLabel("Confirm password: ");
		addUserConfirmPasswordField = new JPasswordField();
		newUserTypesList = new JComboBox(userTypes);
		JButton addUserButton = new JButton("Register");
		
		//Add components to addUserPanel
		addUserPanel.setLayout(new GridLayout(8,1));
		addUserPanel.add(newUserTypesList);
		addUserPanel.add(addUsernameLabel);
		addUserPanel.add(addUsernameField);
		addUserPanel.add(addUserPassLabel);
		addUserPanel.add(addPasswordField);
		//TODO Make confirm password word to add users as well
		addUserPanel.add(addUserConfirmPassLabel);
		addUserPanel.add(addUserConfirmPasswordField);
		addUserPanel.add(addUserButton);
		
		//Create panel "Change Password", and create its components.
		JPanel changePassPanel = new JPanel(){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(300, 400);
            }
        };
		changePassUserTypesList = new JComboBox(userTypes);
		JLabel changePassUsernameLabel = new JLabel("Username: ");
		changePassUsernameField = new JTextField();
		JLabel changePassPassLabel = new JLabel("Password: ");
		changePassPasswordField = new JPasswordField();
		JLabel changePassConfirmPassLabel = new JLabel("Confirm password: ");
		changePassConfirmPasswordField = new JPasswordField();
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
		TitledBorder addUserBorder = BorderFactory.createTitledBorder("Add User");
		addUserBorder.setTitleJustification(TitledBorder.CENTER);
		addUserPanel.setBorder(addUserBorder);
		TitledBorder changePassBorder = BorderFactory.createTitledBorder("Change Password");
		changePassBorder.setTitleJustification(TitledBorder.CENTER);
		changePassPanel.setBorder(changePassBorder);

		
		//Create panel and button to Logout
		JPanel logoutPanel = new JPanel();
		JButton logout = new JButton("Logout");
		logout.addActionListener(controller);
		logout.setActionCommand("adminLogout");
		logoutPanel.add(logout);
		
		

		//Add panel to frame
		centerPanel.add(addUserPanel);
		centerPanel.add(changePassPanel);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(logoutPanel, BorderLayout.SOUTH);
		
		
		validate();
		repaint();
		setVisible(true);
	}

}
