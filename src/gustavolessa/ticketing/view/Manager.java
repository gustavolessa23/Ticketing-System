package gustavolessa.ticketing.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class Manager extends JFrame{

	private String userID;
	private gustavolessa.ticketing.controller.Controller controller = new gustavolessa.ticketing.controller.Controller(this);
	private double openCost;
	private double closedCost;
	private double totalCost;
	private int openTickets;
	private int closedTickets;
	private int totalTickets;
	private int costPerTicket = 50;
	private BarChart chart;
	
	private DecimalFormat decim = new DecimalFormat("#.00");
	
	public String getUserID() {
		return userID;
	}

	public Manager(String userID){
		super("Manager Dashboard");
		this.userID = userID;
		setSize(700,400);
		this.setLayout(new BorderLayout());
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		updateTicketStats();
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
				              
		//Create center panel and set layout manager
		JPanel centerPanel = new JPanel(){
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(650, 350);
			}
		};
		centerPanel.setLayout(new GridLayout(1,2));

		//Create left sub-panel, set layout manager and border.
		JPanel overviewPanel = new JPanel() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(300, 350);
			}
		};
		overviewPanel.setLayout(new GridLayout(2,1));

		//Create top left sub-panel, its border and its layout manager
		JPanel ticketsPanel = new JPanel() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(250, 150);
			}
		};
		ticketsPanel.setLayout(new GridLayout(3,2));
		TitledBorder ticketsBorder = BorderFactory.createTitledBorder("Overview (tickets/cost)");
		ticketsBorder.setTitleJustification(TitledBorder.CENTER);
		ticketsPanel.setBorder(ticketsBorder);
		
		//Create tickets panel labels
		JLabel openTicketsLabel = new JLabel("Open:");
		openTicketsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel closedTicketsLabel = new JLabel("Closed:");
		closedTicketsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel totalTicketsLabel = new JLabel("Total:");
		totalTicketsLabel.setHorizontalAlignment(SwingConstants.CENTER);

		//Create tickets panel fields (as JLabels)
		JLabel openTicketsField = new JLabel(String.valueOf(openTickets)+" tickets / € "+decim.format(openCost));
		openTicketsField.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel closedTicketsField = new JLabel(String.valueOf(closedTickets)+" tickets / € "+decim.format(closedCost));
		closedTicketsField.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel totalTicketsField = new JLabel(String.valueOf(totalTickets)+" tickets / € "+decim.format(totalCost));
		totalTicketsField.setHorizontalAlignment(SwingConstants.CENTER);
		
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
		bottomPanel.setLayout(new FlowLayout());
		JButton refresh = new JButton("Refresh");
		refresh.addActionListener(controller);
		refresh.setActionCommand("refreshManager");
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
		
		//Create panel for tech table (tickets assigned to each tech support staff member)
		JPanel techTicketsPanel = new JPanel() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(250, 150);
			}
		};
		TitledBorder techTicketsBorder = BorderFactory.createTitledBorder("Number of tickets for each Tech staff");
		techTicketsBorder.setTitleJustification(TitledBorder.CENTER);
		techTicketsPanel.setBorder(techTicketsBorder);
		techTicketsPanel.setLayout(new GridLayout(1,1));
		
		//Create table to show tickets assigned to each tech support staff member
		JTable techTicketsTable = displayTechTicketsTable();
		techTicketsTable.setDefaultEditor(Object.class, null);
		techTicketsTable.setAutoCreateRowSorter(true);
		((DefaultTableCellRenderer)techTicketsTable.getTableHeader().getDefaultRenderer())
	    .setHorizontalAlignment(JLabel.CENTER);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		techTicketsTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		techTicketsTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		techTicketsTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		techTicketsTable.getColumnModel().getColumn(0).setPreferredWidth(10);
		techTicketsTable.getColumnModel().getColumn(1).setPreferredWidth(50);
		techTicketsTable.getColumnModel().getColumn(2).setPreferredWidth(30);
		JScrollPane techTicketsScroll = new JScrollPane(techTicketsTable);
		
		//Add Items to Panels
		ticketsPanel.add(openTicketsLabel);
		ticketsPanel.add(openTicketsField);
		ticketsPanel.add(closedTicketsLabel);
		ticketsPanel.add(closedTicketsField);
		ticketsPanel.add(totalTicketsLabel);
		ticketsPanel.add(totalTicketsField);
		graphPanel.add(chart);
		bottomPanel.add(refresh);
		bottomPanel.add(logout);
		techTicketsPanel.add(techTicketsScroll);
		
		//Add panels to panels
		overviewPanel.add(ticketsPanel);
		centerPanel.add(overviewPanel);
		centerPanel.add(graphPanel);
		overviewPanel.add(techTicketsPanel);
		
		//Add panels to JFrame
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);

		validate();
		repaint();
		setVisible(true);
	}
	
	/**
	 * Retrieves tickets per tech staff information from database (using controller), creates a JTable and returns it.
	 * @return JTable
	 */
	public JTable displayTechTicketsTable() {
		String[][] data = controller.getTicketsPerTech();
		String [] columns = {"Tech ID", "Username","Tickets"};
		JTable table = new JTable (data, columns);
		return table;
	}
	
	/**
	 * Updates tickets statistics, retrieving them from database (using controller), and storing in variables to be used by tables and graphs.
	 */
	public void updateTicketStats() {
		int[] stats = controller.retrieveTicketStats();
		openTickets = stats[0];
		closedTickets = stats[1];
		totalTickets = stats[2]	;
		openCost = Double.parseDouble(decim.format((openTickets*costPerTicket)));
		closedCost = Double.parseDouble(decim.format((closedTickets*costPerTicket)));
		totalCost = Double.parseDouble(decim.format((totalTickets*costPerTicket)));
	}
	
	/**
	 * Formats the array taken by the Graph, including the information previously retrieved from database and two empty bars, in order to display the graph nicely.
	 * @return double[] values.
	 */
	public double[] graphValues(){
		double[] values = new double[5];
		values[0] = openCost;
		values[1] = 0;
		values[2] = closedCost;
		values[3] = 0;
		values[4] = totalCost;
		return values;
	}
	
	/**
	 * Formats the graph labels using information retrived from database.
	 * @return String[] graphLabels.
	 */
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

	/**
	 * Formats the colors array of Strings.
	 * @return Color[] graphColors.
	 */
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
