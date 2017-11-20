package gustavolessa.ticketing.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class Login extends JFrame{

	private JTextField userField;
	private JPasswordField passField;
	private String userID;

	gustavolessa.ticketing.controller.Controller controller= new gustavolessa.ticketing.controller.Controller(this);
	
	public String getUserField() {
		return userField.getText();
	}
	public String getPassField() {
		return new String(passField.getPassword());
	}


	public Login(){
		// set the name of the application menu item
		setSize(300,300);
		this.setLayout(new GridLayout(5,1));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Ticketing System");
		this.setLocationRelativeTo(null);	
		//TODO Add KeyListener
		
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


		JLabel nameLabel = new JLabel("Username");
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(nameLabel);

		JPanel userPanel = new JPanel();
		userField = new JTextField(20);
		userPanel.add(userField);
		this.add(userPanel);

		JLabel passLabel = new JLabel("Password");
		passLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(passLabel);

		JPanel passPanel = new JPanel();
		passField = new JPasswordField(20);
		passPanel.add(passField);
		this.add(passPanel);


		JPanel loginPanel = new JPanel();
		JButton login = new JButton("Login");
		login.addActionListener(controller);
		login.setActionCommand("login");
		loginPanel.add(login);
		this.add(loginPanel);
		
		validate();
		repaint();
		setVisible(true);

	}
	
	public static void main(String[] args) {
		new Login();
	}
}
