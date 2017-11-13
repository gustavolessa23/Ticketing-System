package gustavolessa.ticketing.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Login extends JFrame{

	private JTextField userField = null;
	private JPasswordField passField = null;
	private String userID;

	gustavolessa.ticketing.controller.Controller controller= new gustavolessa.ticketing.controller.Controller(this);
	
	public String getUserField() {
		return userField.getText();
	}
	public String getPassField() {
		return new String(passField.getPassword());
	}

	public Login(){

		setSize(300,300);
		setVisible(true);
		this.setLayout(new GridLayout(6,1));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Login");

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
	}
	
	public static void main(String[] args) {
		new Login();
	}
}
