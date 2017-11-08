import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.sql.ResultSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Login extends JFrame implements ActionListener {

	JTextField userField = null;
	JTextField passField = null;

	public Login(){


		setSize(300,300);
		setVisible(true);
		this.setLayout(new GridLayout(6,1));
		setDefaultCloseOperation(EXIT_ON_CLOSE);

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
		passField = new JTextField(20);
		passPanel.add(passField);
		this.add(passPanel);


		JPanel loginPanel = new JPanel();
		JButton login = new JButton("Login");
		login.addActionListener(this);
		login.setActionCommand("login");
		loginPanel.add(login);
		this.add(loginPanel);


		JPanel registerPanel = new JPanel();
		JButton register = new JButton("Not registered? Click here.");
		register.addActionListener(this);
		register.setActionCommand("register");
		registerPanel.add(register);
		this.add(registerPanel);

		validate();
		repaint();



	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Login();
	}

	public void loginWithDatabase(){


		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();

		}catch(Exception e ){}


		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn =
					DriverManager.getConnection("jdbc:mysql://127.0.0.1/ticketing?user=root&password=");

			// Do something with the Connection
			stmt = conn.createStatement();

			// or alternatively, if you don't know ahead of time that
			// the query will be a SELECT...

			String un = userField.getText();
			String pw = passField.getText();

			if (stmt.execute("SELECT * FROM users WHERE name = '"+un+"' AND password = '"+pw+"'")) {
				rs = stmt.getResultSet();
			}

			// loop over results

			while(rs.next()){
				JOptionPane.showMessageDialog(this, "Logged in!");

				System.out.println("----------------------");
				switch(rs.getString("type")) {
				case "Admin":
					System.out.println("User is Admin!");
					break;
				case "Tech":
					System.out.println("User is Tech Support!");
					break;
				case "Manager":
					System.out.println("User is Manager!");
					break;
				default:
					System.out.println("User type could not be identified!");
					break;
				}
				String id = rs.getString("id");
				System.out.println("ID: " + id);

				String sid = rs.getString("name");
				System.out.println("UN: " + sid);
			} 

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}    
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// if you want to set a label for each of the buttons
		// and then redirect the user to a different part of the program
		// you can use the getActionCommand to check which button
		// has sent the request
		if(e.getActionCommand().equals("login")){

			loginWithDatabase();
		}
		else if(e.getActionCommand().equals("login")){

		}





	}

}
