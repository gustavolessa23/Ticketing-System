package gustavolessa.ticketing.view;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Tech extends JFrame{
	
	String userID;
	gustavolessa.ticketing.controller.Controller controller = new gustavolessa.ticketing.controller.Controller(this);

	
	public Tech(String userID){
		
		this.userID = userID;
		setSize(300,300);
		setVisible(true);
		this.setLayout(new GridLayout(6,1));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JLabel admin = new JLabel();
		admin.setText("You are logged in as Tech Support, ID: "+ userID);
		this.add(admin);
		validate();
		repaint();
	}

}
