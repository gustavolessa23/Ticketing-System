/**
 * Ticketing System
 * Author: Gustavo Lessa (https://github.com/gustavolessadublin)
 * 
 * December 2017 
 */

package gustavolessa.ticketing.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;


@SuppressWarnings("serial")
public class Login extends JFrame {

	private JTextField userField;
	private JPasswordField passField;
	private gustavolessa.ticketing.controller.Controller controller = new gustavolessa.ticketing.controller.Controller(this);
	
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
		//this.setLocationByPlatform(true);
		this.addWindowListener(controller);
		
		//Add Menu containing File -> Close
		if (System.getProperty("os.name").contains("Mac")) {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		}	      
		JMenuBar topBar = new JMenuBar();
		this.setJMenuBar(topBar);
		JMenu file = new JMenu("File");
		topBar.add(file);
		JMenuItem close = new JMenuItem("Close");
		file.add(close);
		close.addActionListener(controller);
		close.setActionCommand("close");

		 //Create panels for fields and labels             
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

		//Create panel for button
		JPanel loginPanel = new JPanel();
		JButton login = new JButton("Login");
		login.addActionListener(controller);
		login.setActionCommand("login");
		loginPanel.add(login);
		
		//Add panels to Frame
		this.add(fields, BorderLayout.CENTER);
		this.add(loginPanel, BorderLayout.SOUTH);
		
		getRootPane().setDefaultButton(login);
		validate();
		repaint();
		setVisible(true);

	}
	
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				Boolean readmeShowed = false;
				if(!readmeShowed) {
					new Notes();
					readmeShowed = true;
				}
				new Login();
			}
		});
	}
}
