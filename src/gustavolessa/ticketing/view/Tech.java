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
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.apache.commons.lang3.StringUtils;


public class Tech extends JFrame{
	
	//Set controller
	static gustavolessa.ticketing.controller.Controller controller;
	
	//Set class variables
	private JComboBox priority;
	private JComboBox techEmployee;
	private String[][] techStaff;
	private String[] techNames;

	
	public Tech(String userID){
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
		this.setLayout(new GridLayout(6,1));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Tech Support Menu");
		//TODO change layout for v2
		this.setLocationRelativeTo(null);


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
		setVisible(true);
	}
	
	//Method that displays JOptionPane to Add Ticket
	public void addTicketWindow() {

		// Creating panel and items
		JPanel panel = new JPanel(){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(150, 200);
            }
        };
        panel.setLayout(new GridLayout(2,1));
        JPanel subPanel = new JPanel();
        subPanel.setLayout(new GridLayout(3,2));
		JLabel priorityLabel = new JLabel("Priority: ");
		JLabel techStaffLabel = new JLabel("Assign to: ");
		JLabel descriptionLabel = new JLabel("Description: ");
		JTextArea descriptionArea = new JTextArea();
		descriptionArea.setLineWrap(true);
		descriptionArea.setWrapStyleWord(true);

		//Adding items to panel
        subPanel.add(priorityLabel);
        subPanel.add(priority);
        subPanel.add(techStaffLabel);
        subPanel.add(techEmployee);
        subPanel.add(descriptionLabel);
        panel.add(subPanel);
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
	public void viewTicketsWindow(String[][] data) {
		//TODO add button to refresh
		
        //Set column names
        String[] columnNames = {"ID", "Creation Date", "Closure Date", "Priority", "Description", "Total ticket time"};
        
        //Panel for table
        JPanel panel = new JPanel(){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(700, 400);
            }
        };
        
        //Table non editable
        JTable table = new JTable(data, columnNames){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(600, 350);
            }
        };
        table.setDefaultEditor(Object.class, null);
        table.setAutoCreateRowSorter(true);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(10);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(50);
        table.getColumnModel().getColumn(4).setPreferredWidth(80);
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
        JScrollPane scroll = new JScrollPane(table){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(700, 400);
            }
        };
        
        //Add Scroll Pane to the Panel
        panel.add(scroll);

        //Displays JOptionPane
        int result = JOptionPane.showConfirmDialog(null, panel, "Add Ticket",
        		JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
        		int row = table.getSelectedRow();
    			int idToView = Integer.parseInt(data[row][0]);
    			controller.viewTicket(idToView);
        } else {
        	System.out.println("Cancelled");
        }
    }

	//Method to display the details of a ticket
	public void viewTicketDetails(ResultSet rs) {
		
		//Set local variables
		String ticketId = null;
	//	String techId = null;
		String techName = null;
		String creationDate = null;
		String closeDate = null;
		String ticketPriority = null; 
		String description = null;
		String timeRetrieved = null;
		
		//Get strings from ResultSet retrieved from database
		try {
			while(rs.next()){
				ticketId = rs.getString("id");
			//	techId = rs.getString("tech_id");
				techName = rs.getString("name");
				creationDate = rs.getString("creation_date");
				closeDate = rs.getString("close_date");
				ticketPriority = rs.getString("priority"); 
				description = rs.getString("description");
				timeRetrieved = rs.getString("time_taken");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Create items to display info
		JLabel labelId = new JLabel("ID: ");
		JLabel fieldId = new JLabel("\t\t"+ticketId);
	
		JLabel labelTechStaff = new JLabel("Assigned to: ");

		JLabel labelCreated = new JLabel("Date created: ");
		JLabel fieldCreated = new JLabel("\t\t"+creationDate);

		
		JLabel labelClosed = new JLabel("Date closed: ");
        if(StringUtils.isBlank(closeDate)) {
      	  	closeDate = "Ticket open";
        }
		JLabel fieldClosed = new JLabel("\t\t"+closeDate);
		
		JLabel labelTimeTaken = new JLabel("Total ticket time: ");
		
		String timeTaken = "";
		
        if(StringUtils.isBlank(timeRetrieved)) {
      	  	timeTaken = "Ticket open";
        } else {
			long seconds = Long.parseLong(timeRetrieved);   	  	
			long day = TimeUnit.SECONDS.toDays(seconds);        
			long hours = TimeUnit.SECONDS.toHours(seconds) - (day *24);
			long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds)* 60);
			long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) *60);
			if(day > 0) {
          	  	timeTaken = day+"d "+hours+"h "+minute+"min";
            } else {
          	  	if(hours > 0) {
          	  		timeTaken = hours+"h "+minute+"min";
          	  	} else {
          	  		if(minute > 0) {
          	  			timeTaken = minute+"min";
          	  		} else {
          	  			timeTaken = second+" seconds";
          	  		}
          	  	}
            }
        }
		
		JLabel fieldTimeTaken = new JLabel("\t\t"+timeTaken);
		
		JLabel labelPriority = new JLabel("Priority: ");
	
		
		JTextArea fieldDescription = new JTextArea(5,20);
		fieldDescription.setLineWrap(true);
		fieldDescription.setWrapStyleWord(true);
		fieldDescription.setText(description);
		
		//Create panel and sub panels
		JPanel panel = new JPanel(){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(600, 350);
            }
        };
		panel.setLayout(new GridLayout(1,2,5,5));
		
		JPanel subPanel = new JPanel();
		subPanel.setLayout(new GridLayout(6,2));
		
		
		JPanel descriptionPanel = new JPanel();
		descriptionPanel.setLayout(new GridLayout(1,1));
		TitledBorder descriptionBorder = BorderFactory.createTitledBorder("Description");
		descriptionBorder.setTitleJustification(TitledBorder.CENTER);
		descriptionPanel.setBorder(descriptionBorder);

		//Add Items to subpanels
		subPanel.add(labelId);
		subPanel.add(fieldId);

		subPanel.add(labelCreated);
		subPanel.add(fieldCreated);
		
		subPanel.add(labelClosed);
		subPanel.add(fieldClosed);
		
		subPanel.add(labelTimeTaken);
		subPanel.add(fieldTimeTaken);
		
		subPanel.add(labelTechStaff);
		techEmployee.setSelectedItem(techName);
		subPanel.add(techEmployee);

		subPanel.add(labelPriority);
		priority.setSelectedItem(ticketPriority);
		subPanel.add(priority);
		
		descriptionPanel.add(fieldDescription);
		
		//Add items to main panel
		panel.add(subPanel);
		panel.add(descriptionPanel);

		//Display JOptionPane
		int result;
		if(closeDate.equals("Ticket open")) {
			String[] buttons = {"Cancel", "Delete ticket", "Close ticket", "Update ticket"};

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
	    	int n = JOptionPane.showConfirmDialog(this, "Would you like to delete this ticket?", "Confirmation", JOptionPane.YES_NO_OPTION);
		if (n==0) {
		  int deleteResponse = controller.deleteTicket(ticketId);
		  if(deleteResponse > 0) {
		 	JOptionPane.showMessageDialog(this, "Ticket deleted successfully!");
		  } else {
			JOptionPane.showMessageDialog(this, "Ticket could not be deleted!");
	    	}
		}
		break;
	      
	    case 2:
	    	System.out.println("close");
	    	
	    	int x = JOptionPane.showConfirmDialog(this, "Would you like to close this ticket?", "Confirmation", JOptionPane.YES_NO_OPTION);
			if (x==0) {
		    	int closeResponse = controller.closeTicket(ticketId);
		    	if(closeResponse > 0) {
		    		JOptionPane.showMessageDialog(this, "Ticket closed successfully!");
		    	} else {
		    		JOptionPane.showMessageDialog(this, "Ticket could not be closed!");
		    	}
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
