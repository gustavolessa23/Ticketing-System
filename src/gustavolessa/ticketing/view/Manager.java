package gustavolessa.ticketing.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class Manager extends JFrame{

	String userID;
	gustavolessa.ticketing.controller.Controller controller = new gustavolessa.ticketing.controller.Controller(this);
	double openCost;
	double closedCost;
	double totalCost;
	int openTickets;
	int closedTickets;
	int totalTickets;
	int costPerTicket = 50;
	BarChart chart;
	
	DecimalFormat decim = new DecimalFormat("#.00");

	public Manager(String userID){
		super("Manager Dashboard");
		this.userID = userID;
		setSize(700,400);
		this.setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		updateTicketStats();

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
				              
		//Create center panel and set layout manager
		JPanel centerPanel = new JPanel(){
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(650, 350);
			}
		};
		centerPanel.setLayout(new GridLayout(1,2));

		//Create left subpanel, set layout manager and border.
		JPanel overviewPanel = new JPanel() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(300, 350);
			}
		};
		overviewPanel.setLayout(new GridLayout(2,1));

		//Create top left subpanel, its border and its layout manager
		JPanel ticketsPanel = new JPanel() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(250, 150);
			}
		};
		ticketsPanel.setLayout(new GridLayout(3,2));
		TitledBorder ticketsBorder = BorderFactory.createTitledBorder("Tickets");
		ticketsBorder.setTitleJustification(TitledBorder.CENTER);
		ticketsPanel.setBorder(ticketsBorder);
		
		//Create tickets panel specifics
		JLabel openTicketsLabel = new JLabel("Open:");
		openTicketsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel closedTicketsLabel = new JLabel("Closed:");
		closedTicketsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel totalTicketsLabel = new JLabel("Total:");
		totalTicketsLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel openTicketsField = new JLabel(String.valueOf(openTickets));
		openTicketsField.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel closedTicketsField = new JLabel(String.valueOf(closedTickets));
		closedTicketsField.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel totalTicketsField = new JLabel(String.valueOf(totalTickets));
		totalTicketsField.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Create cost panel specifics
		JLabel openCostLabel = new JLabel("Open tickets:");
		openCostLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel closedCostLabel = new JLabel("Closed tickets:");
		closedCostLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel totalCostLabel = new JLabel("Total cost:");
		totalCostLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel openCostField = new JLabel("€ "+decim.format(openCost));
		openCostField.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel closedCostField = new JLabel("€ "+decim.format(closedCost));
		closedCostField.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel totalCostField = new JLabel("€ "+decim.format(totalCost));
		totalCostField.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Create bottom left subpanel, its border and its layout manager
		JPanel costPanel = new JPanel() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(250, 150);
			}
		};
		costPanel.setLayout(new GridLayout(3,2));
		TitledBorder costBorder = BorderFactory.createTitledBorder("Cost");
		costBorder.setTitleJustification(TitledBorder.CENTER);
		costPanel.setBorder(costBorder);
		
		//Create right panel (graph) and its border
		JPanel graphPanel = new JPanel() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(250, 300);
			}
		};		
		TitledBorder graphBorder = BorderFactory.createTitledBorder("Graph");
		graphBorder.setTitleJustification(TitledBorder.CENTER);
		graphPanel.setBorder(graphBorder);
	
		
		//Create bottom panel (buttons) and set its ActionListeners
		JPanel bottomPanel = new JPanel();
		JButton refresh = new JButton("Refresh");
		refresh.addActionListener(controller);
		refresh.setActionCommand("managerRefresh");
		JButton logout = new JButton("Logout");
		logout.addActionListener(controller);
		logout.setActionCommand("managerLogout");
		
		//Generate bar graph

		chart = new BarChart(graphValues(), graphLabels(), graphColors()){
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(300, 280);
			}
		};

		//Add Items do Panels
		ticketsPanel.add(openTicketsLabel);
		ticketsPanel.add(openTicketsField);
		ticketsPanel.add(closedTicketsLabel);
		ticketsPanel.add(closedTicketsField);
		ticketsPanel.add(totalTicketsLabel);
		ticketsPanel.add(totalTicketsField);
		costPanel.add(openCostLabel);
		costPanel.add(openCostField);
		costPanel.add(closedCostLabel);
		costPanel.add(closedCostField);
		costPanel.add(totalCostLabel);
		costPanel.add(totalCostField);
		
		overviewPanel.add(ticketsPanel);
		overviewPanel.add(costPanel);
		graphPanel.add(chart);
		centerPanel.add(overviewPanel);
		centerPanel.add(graphPanel);
		bottomPanel.add(refresh);
		bottomPanel.add(logout);

		this.add(centerPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);

		validate();
		repaint();
		setVisible(true);
	}
	
	public void updateDisplayedData() {
		updateTicketStats();
		this.dispose();
		new Manager(userID);
		revalidate();
		repaint();
	}
	
	public void updateTicketStats() {
		int[] stats = controller.retrieveTicketStats();
		openTickets = stats[0];
		closedTickets = stats[1];
		totalTickets = stats[2]	;
		openCost = Double.parseDouble(decim.format((openTickets*costPerTicket)));
		closedCost = Double.parseDouble(decim.format((closedTickets*costPerTicket)));
		totalCost = Double.parseDouble(decim.format((totalTickets*costPerTicket)));
	}

	public double[] graphValues(){
		double[] values = new double[5];
		values[0] = openCost;
		values[1] = 0;
		values[2] = closedCost;
		values[3] = 0;
		values[4] = totalCost;
		return values;
	}

	public String[] graphLabels() {
		String openLabel = "Open: "+openTickets;
		String closedLabel = "Closed: "+closedTickets;
		String totalLabel = "Total: "+totalTickets;

		String[] labels = new String[5];
		labels[0] = openLabel;
		labels[1] = "";
		labels[2] = closedLabel;
		labels[3] = "";
		labels[4] = totalLabel;

		return labels;
	}

	public Color[] graphColors() {
		Color[] colors = new Color[]{
				Color.red,
				Color.orange,
				Color.green,
				Color.green,
				Color.blue
		};
		return colors;
	}


}
