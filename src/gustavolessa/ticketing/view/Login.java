package gustavolessa.ticketing.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
import javax.swing.WindowConstants;


public class Login extends JFrame {

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
		setSize(300,150);
		this.setLayout(new BorderLayout());
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setTitle("Ticketing System Login");
		this.setLocationRelativeTo(null);	
		this.addWindowListener(controller);
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

		JPanel fields = new JPanel();
		fields.setLayout(new GridLayout(2,1,5,5));
		JPanel userPanel = new JPanel();
		userPanel.setLayout(new FlowLayout());
		
		JLabel nameLabel = new JLabel("Username");
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		userPanel.add(nameLabel);

		userField = new JTextField(15);
		userPanel.add(userField);
		fields.add(userPanel);

		JPanel passPanel = new JPanel();
		passPanel.setLayout(new FlowLayout());
		
		JLabel passLabel = new JLabel("Password");
		passLabel.setHorizontalAlignment(JLabel.CENTER);
		passPanel.add(passLabel);
		passField = new JPasswordField(15);
		passPanel.add(passField);
		fields.add(passPanel);


		JPanel loginPanel = new JPanel();
		JButton login = new JButton("Login");
		login.addActionListener(controller);
		login.setActionCommand("login");
		loginPanel.add(login);
		this.add(fields, BorderLayout.CENTER);
		this.add(loginPanel, BorderLayout.SOUTH);
		
		getRootPane().setDefaultButton(login);
		validate();
		repaint();
		setVisible(true);

	}
	
	public static void main(String[] args) {
		new Login();
	}
}
