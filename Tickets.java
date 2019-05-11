
/* Darsh Patel - Final Project - Spring 2019 */
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Tickets extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	// class level member objects
	Dao dao = new Dao(); // for CRUD operations
	Boolean chkIfAdmin = null;
	Login log = new Login();

	// Main menu object items
	private JMenu mnuFile = new JMenu("File");
	private JMenu mnuAdmin = new JMenu("Admin");
	private JMenu mnuTickets = new JMenu("Tickets");

	// Sub menu item objects for all Main menu item objects
	JMenuItem mnuItemExit;
	JMenuItem mnuItemUpdate;
	JMenuItem mnuItemDelete;
	JMenuItem mnuItemOpenTicket;
	JMenuItem mnuItemViewTicket;
	JButton enterTicket;
	JButton viewTicket;
	JButton updateTicket;
	JButton deleteTicket;
	JButton closeTicket;
	JButton signOut;
	JLabel welcomeMsg;

	public Tickets(Boolean isAdmin) {

		chkIfAdmin = isAdmin;
		createMenu();
		prepareGUI();

	}

	private void createMenu() {

		/* Initialize sub menu items **************************************/

		// initialize sub menu item for File main menu
		mnuItemExit = new JMenuItem("Exit");
		// add to File main menu item
		mnuFile.add(mnuItemExit);

		// initialize first sub menu items for Admin main menu
		mnuItemUpdate = new JMenuItem("Update Ticket");
		// add to Admin main menu item
		mnuAdmin.add(mnuItemUpdate);

		// initialize second sub menu items for Admin main menu
		mnuItemDelete = new JMenuItem("Delete Ticket");
		// add to Admin main menu item
		mnuAdmin.add(mnuItemDelete);

		// initialize first sub menu item for Tickets main menu
		mnuItemOpenTicket = new JMenuItem("Open Ticket");
		// add to Ticket Main menu item
		mnuTickets.add(mnuItemOpenTicket);

		// initialize second sub menu item for Tickets main menu
		mnuItemViewTicket = new JMenuItem("View Ticket");
		// add to Ticket Main menu item
		mnuTickets.add(mnuItemViewTicket);

		// initialize any more desired sub menu items below

		/* Add action listeners for each desired menu item *************/
		mnuItemExit.addActionListener(this);
		mnuItemUpdate.addActionListener(this);
		mnuItemDelete.addActionListener(this);
		mnuItemOpenTicket.addActionListener(this);
		mnuItemViewTicket.addActionListener(this);
		// ticket.addActionListener(this);
		// add any more listeners for any additional sub menu items if desired

	}

	private void prepareGUI() {

		// set look and feel for GUI
		try {
			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// create jmenu bar
		JMenuBar bar = new JMenuBar();
		bar.add(mnuFile); // add main menu items in order, to JMenuBar
		bar.add(mnuAdmin);
		bar.add(mnuTickets);
		// add menu bar components to frame
		setJMenuBar(bar);

		// build controls
		enterTicket = new JButton("Enter Ticket");
		viewTicket = new JButton("View Ticket(s)");
		updateTicket = new JButton("Update Ticket");
		deleteTicket = new JButton("Delete Ticket");
		closeTicket = new JButton("Close Ticket");
		signOut = new JButton("Sign out");
		welcomeMsg = new JLabel("WELCOME TO IIT HELP DESK", JLabel.CENTER);

		// set background and foreground colors
		enterTicket.setBackground(Color.GRAY);
		enterTicket.setForeground(Color.WHITE);
		viewTicket.setBackground(Color.GRAY);
		viewTicket.setForeground(Color.WHITE);
		updateTicket.setBackground(Color.GRAY);
		updateTicket.setForeground(Color.WHITE);
		deleteTicket.setBackground(Color.GRAY);
		deleteTicket.setForeground(Color.WHITE);
		closeTicket.setBackground(Color.GRAY);
		closeTicket.setForeground(Color.WHITE);
		signOut.setBackground(Color.GRAY);
		signOut.setForeground(Color.WHITE);
		welcomeMsg.setForeground(Color.GRAY);

		// create Font style
		Font f = new Font("Century", Font.BOLD, 25);

		// set font styles
		welcomeMsg.setFont(f);
		enterTicket.setFont(f);
		viewTicket.setFont(f);
		updateTicket.setFont(f);
		deleteTicket.setFont(f);
		closeTicket.setFont(f);
		signOut.setFont(f);

		// add action listeners
		enterTicket.addActionListener(this);
		viewTicket.addActionListener(this);
		updateTicket.addActionListener(this);
		deleteTicket.addActionListener(this);
		closeTicket.addActionListener(this);
		signOut.addActionListener(this);

		addWindowListener(new WindowAdapter() {
			// define a window close operation
			public void windowClosing(WindowEvent wE) {
				System.exit(0);
			}
		});
		// set frame options
		setLayout(new GridLayout(7, 2));
		setSize(600, 600);
		getContentPane().setBackground(Color.WHITE);
		setLocationRelativeTo(null);
		setVisible(true);

		// hide admin options from regular users
		if (chkIfAdmin == false) {
			setLayout(new GridLayout(4, 2));
			add(welcomeMsg);
			add(enterTicket);
			add(viewTicket);
			add(signOut);
			mnuAdmin.setVisible(false);
		}
		// show admin options for admins
		else if (chkIfAdmin == true) {
			add(welcomeMsg);
			add(enterTicket);
			add(viewTicket);
			add(updateTicket);
			add(deleteTicket);
			add(closeTicket);
			add(signOut);

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// implement actions for sub menu items
		if (e.getSource() == mnuItemExit) {
			System.exit(0);
		}

		else if (e.getSource() == enterTicket || e.getSource() == mnuItemOpenTicket) {

			// options for ticket descriptions
			String[] ticketOptions = new String[10];
			ticketOptions[0] = "Internet connection";
			ticketOptions[1] = "Server failed";
			ticketOptions[2] = "Blue Screen of Death";
			ticketOptions[3] = "Accidentally deleted files";
			ticketOptions[4] = "System shutdown automatically";
			ticketOptions[5] = "Computer doesn't read USB";
			ticketOptions[6] = "Unable to login into computer";
			ticketOptions[7] = "Computer too slow";
			ticketOptions[8] = "Can't print";
			ticketOptions[9] = "Computer doesn't start";

			// get ticket information

			String ticketName = JOptionPane.showInputDialog(null, "Enter your name");

			// error trapping for no input
			while (ticketName.length() == 0) {
				ticketName = JOptionPane.showInputDialog(null, "Please re-enter your name");
			}

			Object ticketDesc = JOptionPane.showInputDialog(null, "Enter a ticket description", "Ticket Description",
					JOptionPane.QUESTION_MESSAGE, null, ticketOptions, "");

			// insert ticket information to database

			int id = dao.insertRecords(ticketName, ticketDesc);

			// display results if successful or not to console / dialog box
			if (id != 0) {
				System.out.println("Ticket ID : " + id + " created successfully!!!");
				JOptionPane.showMessageDialog(null, "Ticket id: " + id + " created");
			} else
				System.out.println("Ticket cannot be created!!!");
		}

		else if (e.getSource() == viewTicket || e.getSource() == mnuItemViewTicket) {

			// retrieve all tickets details for viewing in JTable
			try {

				// Use JTable built in functionality to build a table model and
				// display the table model off your result set!!!
				ticketsJTable.buildTableModel(dao.readRecords());
				setVisible(true); // refreshes or repaints frame on screen

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		else if (e.getSource() == updateTicket || e.getSource() == mnuItemUpdate) {
			// get update info from admin
			String ticketId = JOptionPane.showInputDialog(null, "Enter the ticket ID for update");

			// error trapping for no input
			while (ticketId.length() == 0) {
				ticketId = JOptionPane.showInputDialog(null, "Please re-enter ticket ID");
			}

			String ticketUpdateDesc = JOptionPane.showInputDialog(null, "Enter updated ticket description");

			// error trapping for no input
			while (ticketUpdateDesc.length() == 0) {
				ticketUpdateDesc = JOptionPane.showInputDialog(null, "Please re-enter ticket description");
			}

			// update ticket information to database

			int id = dao.updateRecords(ticketId, ticketUpdateDesc);

			if (id != 0) {
				System.out.println("Ticket ID : " + ticketId + " update: " + ticketUpdateDesc);
				JOptionPane.showMessageDialog(null, "Ticket ID: " + ticketId + " update: " + ticketUpdateDesc);
			} else {
				System.out.println("Ticket cannot be updated!!!");
			}
		}

		else if (e.getSource() == deleteTicket || e.getSource() == mnuItemDelete) {
			// get delete info from admin

			String ticketDelete = JOptionPane.showInputDialog(null, "Enter ID number you want to delete");

			// error trapping for no input
			while (ticketDelete.length() == 0) {
				ticketDelete = JOptionPane.showInputDialog(null, "Please re-enter ticket ID");
			}

			// delete ticket

			int id = dao.deleteRecords(ticketDelete);

			if (id != 0) {
				System.out.println("Ticket ID : " + ticketDelete + " deleted successfully!!!");
				JOptionPane.showMessageDialog(null, "Ticket id: " + ticketDelete + " deleted");
			} else
				System.out.println("Ticket cannot be updated!!!");

		}

		else if (e.getSource() == closeTicket) {
			// get update info from admin
			String ticketId = JOptionPane.showInputDialog(null, "Enter the ticket ID for closing");

			// error trapping for no input
			while (ticketId.length() == 0) {
				ticketId = JOptionPane.showInputDialog(null, "Please re-enter ticket ID");
			}

			String ticketClosedDate = JOptionPane.showInputDialog(null, "Enter today's date (format: YYYY-MM-DD)");

			// error trapping for no input
			while (ticketClosedDate.length() == 0) {
				ticketClosedDate = JOptionPane.showInputDialog(null, "Please re-enter date");
			}

			// update ticket status to database

			int id = dao.updateClosedTicket(ticketId, ticketClosedDate);

			if (id != 0) {
				System.out.println("Ticket ID : " + ticketId + " Closed on " + ticketClosedDate);
				JOptionPane.showMessageDialog(null, "Ticket ID : " + ticketId + " Closed on " + ticketClosedDate);
			} else {
				System.out.println("Ticket cannot be Closed!!!");
			}
		} else if (e.getSource() == signOut) {
			System.exit(0);
		}

	}

}