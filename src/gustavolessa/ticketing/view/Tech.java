package gustavolessa.ticketing.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.junit.platform.commons.util.StringUtils;

public class Tech extends JFrame{
	
	//Set controller
	static gustavolessa.ticketing.controller.Controller controller;
	
	//Set class variables
	private JComboBox priority;
	private JComboBox techEmployee;
	private String[][] techStaff;
	private String[] techNames;

	
	public Tech(String userID){
		
		//Initialize controller
		controller = new gustavolessa.ticketing.controller.Controller(this);
		
		//Populate arrays techStaff, techName
		techStaff = controller.getStaff("Tech");
		techNames = new String[techStaff.length];
		for (int x = 0; x < techStaff.length; x++) {
			techNames[x] = techStaff[x][1];
		}
		
		//Populate JComboBoxes: priority and techEmployee
		priority = new JComboBox(controller.getPriorityNames());
		techEmployee = new JComboBox(techNames);		
		
		//Set frame settings
		setSize(300,300);
		setVisible(true);
		this.setLayout(new GridLayout(6,1));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Tech Support");

		//Create panel and button to Logout
		JPanel logoutPanel = new JPanel();
		JButton logout = new JButton("Logout");
		logout.addActionListener(controller);
		logout.setActionCommand("techLogout");
		logoutPanel.add(logout);
		
		//Create "New Ticket" button
		JButton newTicket = new JButton("Create new ticket");
		newTicket.addActionListener(controller);
		newTicket.setActionCommand("newTicketButton");
		
		//Create "View Tickets" button
		JButton viewTickets = new JButton("View tickets");
		viewTickets.addActionListener(controller);
		viewTickets.setActionCommand("viewTicketsButton");
		
		//Add items to frame
		this.add(newTicket);
		this.add(viewTickets);
		this.add(logoutPanel);
		
		validate();
		repaint();
	}
	
	//Method that displays JOptionPane to Add Ticket
	public void addTicketWindow() {
		//TODO change layout

		// Creating panel and items
		JPanel panel = new JPanel(new GridLayout(5, 1));
		JLabel priorityLabel = new JLabel("Priority: ");
		JLabel techStaffLabel = new JLabel("Tech Staff: ");
		JTextArea descriptionArea = new JTextArea();

		//Adding items to panel
        panel.add(priorityLabel);
        panel.add(priority);
        panel.add(techStaffLabel);
        panel.add(techEmployee);
        panel.add(descriptionArea);

        //Displaying the Pane
        int result = JOptionPane.showConfirmDialog(null, panel, "Add Ticket",
        		JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
        		String selectedTechId = null;
	        	for (int x = 0; x < techStaff.length; x++) {
	        		if(techStaff[x][1].equals(techEmployee.getSelectedItem())) {
	        			selectedTechId = techStaff[x][0];
	        		}
	        	}
	        	if(controller.addTicket((String)priority.getSelectedItem(), selectedTechId, descriptionArea.getText()) > 0){
	        		JOptionPane.showMessageDialog(this, "Ticket added successfully!");
	        	} else {
	        		JOptionPane.showMessageDialog(this, "Ticket could not be added!");
	        	}
        } else {
        	System.out.println("Cancelled");
        }
    }

	//Method to display the View Tickets Window
	public void viewTicketsWindow(ResultSet rs) {
		//TODO add button to perform the same as double click (open detailed view)
		//TODO add button to refresh
		ResultSetMetaData rsmd;
		try {
			int maxRow = 100;
			if (rs.last()) {
			    maxRow = rs.getRow();
			    rs.beforeFirst();
			}
			rsmd = rs.getMetaData();
		
		int maxColumn = rsmd.getColumnCount();
		
		//Create 2D array to store and display data
        int rowCounter = 0;
        String [][] data = new String[maxRow][maxColumn];
        
        // loop over results          
        while(rs.next()){
          
          String id = rs.getString("id");
          data[rowCounter][0] = id;
          
          String creationDate = rs.getString("creation_date");
          data[rowCounter][1] = creationDate;
          
          String closeDate = rs.getString("close_date");
   
          data[rowCounter][2] = closeDate;
          
          String priority = rs.getString("priority");
          data[rowCounter][3] = priority; 
          
          String description = rs.getString("description");
          data[rowCounter][4] = description;
          
          rowCounter++;  
        }
	
        //Set column names
        String[] columnNames = {"ID", "Creation Date", "Closure Date", "Priority", "Description"};
        
        //Panel for table
        JPanel panel = new JPanel();
        
        //Table non editable
        JTable table = new JTable(data, columnNames);
        table.setDefaultEditor(Object.class, null);
    
        pack();

        //Add Mouse Listener to table, that detects double clicks on rows.
        table.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
        		if (e.getClickCount() == 2) {
        			JTable target = (JTable) e.getSource();
        			int row = target.getSelectedRow();
        			int idToView = Integer.parseInt(data[row][0]);
        			controller.viewTicket(idToView);
        		}
        	}
        });

        //Create Scroll Pane for table
        JScrollPane scroll = new JScrollPane(table);
        
        //Add Scroll Pane to the Panel
        panel.add(scroll);

        //Displays JOptionPane
        int result = JOptionPane.showConfirmDialog(null, panel, "Add Ticket",
        		JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
	 
        } else {
        	System.out.println("Cancelled");
        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	//Method to display the details of a ticket
	public void viewTicketDetails(ResultSet rs) {
		
		//Set local variables
		String ticketId = null;
		String techId = null;
		String techName = null;
		String creationDate = null;
		String closeDate = null;
		String ticketPriority = null; 
		String description = null;
		
		//Get strings from ResultSet retrieved from database
		try {
			while(rs.next()){
				ticketId = rs.getString("id");
				techId = rs.getString("tech_id");
				techName = rs.getString("name");
				creationDate = rs.getString("creation_date");
				closeDate = rs.getString("close_date");
				ticketPriority = rs.getString("priority"); 
				description = rs.getString("description");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Create items to display info
		JLabel labelId = new JLabel("ID: ");
		JLabel fieldId = new JLabel("\t\t"+ticketId);
	
		JLabel labelTechId = new JLabel("Tech ID: ");

		JLabel labelCreated = new JLabel("Date created: ");
		JLabel fieldCreated = new JLabel("\t\t"+creationDate);

		
		JLabel labelClosed = new JLabel("Date closed: ");
		JLabel fieldClosed = new JLabel("\t\t"+closeDate);
		
		JLabel labelPriority = new JLabel("Priority: ");
		
		JLabel labelDescription = new JLabel("Description: ");
		JTextArea fieldDescription = new JTextArea(5,20);
		fieldDescription.setText(description);
		
		//Create "Close Ticket" button
		JButton closeTicketButton = new JButton("Close Ticket");
		closeTicketButton.addActionListener(controller);
		closeTicketButton.setActionCommand("closeTicketButton");
		
		//Create "Update Ticket" button
		JButton updateTicketButton = new JButton("Update");
		updateTicketButton.addActionListener(controller);
		updateTicketButton.setActionCommand("updateTicketButton");
		
		//Create "Delete Ticket" button
		JButton deleteTicketButton = new JButton("Delete Ticket");
		deleteTicketButton.addActionListener(controller);
		deleteTicketButton.setActionCommand("deleteTicketButton");
		
		//Create panels and sub panels
		JPanel panel = new JPanel(){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(320, 240);
            }
        };
		panel.setLayout(new GridLayout(3,1));
		
		JPanel subPanel1 = new JPanel();
		subPanel1.setLayout(new GridLayout(3,2));
		
		JPanel subPanel2 = new JPanel();
		subPanel2.setLayout(new GridLayout(3,2));
		
		JPanel subPanel3 = new JPanel();
		subPanel3.setLayout(new GridLayout(3,1));

		//Add Items to subpanels
		subPanel1.add(labelId);
		subPanel1.add(fieldId);
		subPanel1.add(labelTechId);
		techEmployee.setSelectedItem(techName);
		subPanel1.add(techEmployee);
		subPanel1.add(labelCreated);
		subPanel1.add(fieldCreated);
		
		subPanel2.add(labelClosed);
		subPanel2.add(fieldClosed);
		subPanel2.add(labelPriority);
		priority.setSelectedItem(ticketPriority);
		subPanel2.add(priority);
		subPanel2.add(labelDescription);
		
		subPanel3.add(closeTicketButton);
		subPanel3.add(updateTicketButton);
		subPanel3.add(deleteTicketButton);
		
		//Add items to main panel
		panel.add(subPanel1);
		panel.add(subPanel2);
		panel.add(fieldDescription);
		//panel.add(subPanel3);

		//Display JOptionPane
		int result;
		if(StringUtils.isBlank(closeDate)) {
			String[] buttons = {"Cancel", "Delete", "Close", "Update"};

		    result = JOptionPane.showOptionDialog(null, panel, "Ticket Details",
		        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);
		} else {
		    String[] buttons = {"Cancel", "Delete"};

		    result = JOptionPane.showOptionDialog(null, panel, "Ticket Details",
		        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);
		}

	    switch(result) {
	    case 0:
	    	System.out.println("cancel");
	    	break;
	    case 1:
	    	System.out.println("delete");
	    	//TODO insert confirmation before deleting
	    	int deleteResponse = controller.deleteTicket(ticketId);
	    	if(deleteResponse > 0) {
	    		JOptionPane.showMessageDialog(this, "Ticket deleted successfully!");
	    	} else {
	    		JOptionPane.showMessageDialog(this, "Ticket could not be deleted!");
	    	}
	    	break;
	    case 2:
	    	System.out.println("close");
	    	int closeResponse = controller.closeTicket(ticketId);
	    	if(closeResponse > 0) {
	    		JOptionPane.showMessageDialog(this, "Ticket closed successfully!");
	    	} else {
	    		JOptionPane.showMessageDialog(this, "Ticket could not be closed!");
	    	}
	    	break;
	    case 3:
	    	System.out.println("update");
	    	String techNumber = null;
	    	for(int i = 0; i < techStaff.length; i++) {
	    		if(techStaff[i][1].equals(techName)) {
	    			techNumber = techStaff[i][0];
	    		}
	    	}
	    	int updateResponse = controller.updateTicket(ticketId, techNumber, (String)priority.getSelectedItem(), fieldDescription.getText());
	    	if(updateResponse > 0) {
	    		JOptionPane.showMessageDialog(this, "Ticket updated successfully!");
	    	} else {
	    		JOptionPane.showMessageDialog(this, "Ticket could not be updated!");
	    	}
	    	break;
	    default:
	    	System.out.println("erro");
	    	break;

	    }
	}
}
