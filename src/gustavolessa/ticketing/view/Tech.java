package gustavolessa.ticketing.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.WindowConstants;
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
	private String[][] data = null;
	private String userId;
	private JTable table;
	private String ticketId = null;
	private String techId = null;
	private String techName = null;
	private String creationDate = null;
	private String closeDate = null;
	private String ticketPriority = null; 
	private String description = null;
	private String timeRetrieved = null;
	
	public String getUserId() {
		return userId;
	}

	public Tech(String userId){
		this.userId = userId;
		//Set frame settings
		
		//setSize(700,400);
		this.setLayout(new BorderLayout());
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("Tech Support Dashboard");
		this.addWindowListener(controller);

		
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
		populateTech();
		
		
		//Populate JComboBoxes: priority and techEmployee
		priority = new JComboBox(controller.getPriorityNames());
		techEmployee = new JComboBox(techNames);		
		
		//Create center panel for table containing tickets
		JPanel tablePanel = new JPanel() {
		     @Override
	            public Dimension getPreferredSize() {
	                return new Dimension(600, 350);
	            }
		};
		
		//Create table for tickets	
		try {
			data = controller.getTicketsInfo();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    //Set column names
        String[] columnNames = {"ID", "Creation Date", "Closure Date", "Priority", "Description", "Total ticket time"};
        
        table = new JTable(data, columnNames){
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
        			//int idToView = Integer.parseInt(data[row][0]);
        			try {
        				controller.viewTicket(data[row][0]);
        			} catch (ArrayIndexOutOfBoundsException ex) {
        				
        			}
        			
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
        tablePanel.add(scroll);
		
		//Create panel and button to Logout
		JPanel buttonsPanel = new JPanel();
		JButton logout = new JButton("Logout");
		logout.addActionListener(controller);
		logout.setActionCommand("techLogout");
		JButton refresh = new JButton("Refresh");
		refresh.addActionListener(controller);
		refresh.setActionCommand("refreshTech");
		JButton addTicketButton = new JButton("New ticket");
		addTicketButton.addActionListener(controller);
		addTicketButton.setActionCommand("addTicket");
		JButton editButton = new JButton("Edit");
		editButton.addActionListener(controller);
		editButton.setActionCommand("editTicket");
		JButton closeTicket = new JButton("Close ticket");
		closeTicket.addActionListener(controller);
		closeTicket.setActionCommand("closeTicket");
		JButton deleteTicket = new JButton("Delete ticket");
		deleteTicket.addActionListener(controller);
		deleteTicket.setActionCommand("deleteTicket");
		buttonsPanel.add(addTicketButton);
		buttonsPanel.add(editButton);
		buttonsPanel.add(closeTicket);
		buttonsPanel.add(deleteTicket);
		buttonsPanel.add(refresh);
		buttonsPanel.add(logout);

		//Add items to frame
		this.add(tablePanel, BorderLayout.CENTER);
		this.add(buttonsPanel, BorderLayout.SOUTH);
		
		validate();
		repaint();
		setSize(700,400);
		this.setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Method that displays JOptionPane to Add Ticket
	 */
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
	        		controller.refreshTech();
	        	} else {
	        		JOptionPane.showMessageDialog(this, "Ticket could not be added!");
	        	}
        }
    }

	/**
	 * Method that displays a dialog showing ticket info and buttons to perform actions to said ticket
	 * @param ResultSet
	 */
	public void viewTicketDetails(ResultSet rs) {

		//Get strings from ResultSet retrieved from database
		try {
			while(rs.next()){
				ticketId = rs.getString("id");
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
		String timeTaken = formatIntervalFromUnix(timeRetrieved);
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

		//Add Items to sub-panels
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
		    	break;
	    case 1:
		    	controller.deleteTicket(ticketId);
		    	break;
	    case 2:
		    	controller.closeTicket(ticketId);
		    	break; 	
	    case 3:
		    controller.updateTicket(ticketId, getTechId(), (String)priority.getSelectedItem(), fieldDescription.getText());
		    	break; 	
	    default:
		    	break;
	    }
	}
	
	public String checkRowId() {
		int row = table.getSelectedRow();
		String id = null;
		if(row>=0) {
			id = (data[row][0]);
		}
		return id;
	}
	
	public void populateTech() {
		techStaff = null;
		techNames = null;
		techStaff = controller.getStaff("Tech");
		techNames = new String[techStaff.length];
		for (int x = 0; x < techStaff.length; x++) {
			techNames[x] = techStaff[x][1];
		}
	}
	
	public String getTechId() {
		populateTech();
	    	for(int i = 0; i < techStaff.length; i++) {
	    		if(techStaff[i][1].equals(techEmployee.getSelectedItem())) {
	    			techId = techStaff[i][0];
	    		}
	    	}
	    	return techId;
	}
	
	/**
	 * Method to convert from Unix (Epoch) time to written interval, in hours, minutes and seconds.
	 * 
	 * @param String timeRetrieved
	 * @return String timeTaken
	 */
	public String formatIntervalFromUnix(String timeRetrieved) {	
		String timeTaken= "";
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
		return timeTaken;
	}
}
