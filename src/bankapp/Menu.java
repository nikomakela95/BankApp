package bankapp;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
//The main menu class
public class Menu extends JFrame {

	private JPanel contentPane;
	private static JTable accountTable;
	private static Bank bank;
	private Customer customer;

	
	 // Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Create the frame
	public Menu() {
		//UI setup
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 544, 394);
		setLocationRelativeTo(null);
		bank = new Bank();
		JFrame frame = this;
		
		//new menu bar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		//File menu
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		
		//Save menu-button
		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.addActionListener(new ActionListener() {
			//Actions to save the current program. Saves to file. 
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(new BankFilter());
				chooser.setAcceptAllFileFilterUsed(false);
				int result = chooser.showSaveDialog(frame);
				if(result == JFileChooser.APPROVE_OPTION){
					File file = chooser.getSelectedFile();
					String fileName = file.toString();
					//adds .bof to file name if needed
					if(!fileName.toLowerCase().endsWith(".bof")){
						fileName += ".bof";
					}
					try {
						FileOutputStream fOut = new FileOutputStream(fileName);
						ObjectOutputStream objOut = new ObjectOutputStream(fOut);
						objOut.writeObject(bank);
						objOut.close();
						fOut.close();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
				}
				
			}
		});
		fileMenu.add(saveMenuItem);
		
		//Open menu item
		JMenuItem openMenuItem = new JMenuItem("Open");
		openMenuItem.addActionListener(new ActionListener() {
			//Actions to open saved file
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(new BankFilter());
				chooser.setAcceptAllFileFilterUsed(false);
				int result = chooser.showOpenDialog(frame);
				//Check if the selected file is valid
				if(result == JFileChooser.APPROVE_OPTION){
					if(!chooser.getSelectedFile().toString().toLowerCase().endsWith(".bof")){
						JOptionPane.showMessageDialog(frame, "Invalid file!");
					}else{
						//Open file and reload the accountTable
						try {
							FileInputStream fileIn = new FileInputStream(chooser.getSelectedFile());
							ObjectInputStream objIn = new ObjectInputStream(fileIn);
							Object bankData = objIn.readObject();
							if(bankData instanceof Bank){
								bank = (Bank) bankData;
								reloadTable();
							}
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			
		});
		
		fileMenu.add(openMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
	
		//Actions for addAccountButton
		//Opens new JDialog
		JButton addAccountButton = new JButton("Add Account");
		addAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				AddAccountMenu menu = new AddAccountMenu(bank, true, frame);
				menu.setVisible(true);
				//Adds customer to table if it is created successfully
				if(menu.getCustomer() != null){
					addCustomerToTable(menu.getCustomer());
				}
			}
			
		});
		
		
		addAccountButton.setMinimumSize(new Dimension(105, 23));
		addAccountButton.setMaximumSize(new Dimension(105, 23));
		addAccountButton.setBounds(10, 11, 120, 23);
		contentPane.add(addAccountButton);
		
		//Actions for deleteAccountButton
		//Deletes selected customer from table and customer list
		JButton deleteAccountButton = new JButton("Delete Account");
		deleteAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = accountTable.getSelectedRow();
				if(selectedRow >= 0){
				Customer customer = getSelectedCustomer(selectedRow);
				//Confirm from user to do action
				int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete account " + customer.getAccount().getAccountNumber() + "?");
                //Delete account if user confirms
                if(confirm == JOptionPane.OK_OPTION){
				bank.deleteCustomer(customer); //Deletes customer from customers list
				deleteCustomerFromTable(selectedRow); //Deletes customer's row from accountTable
			}
		}
	}

		});
		deleteAccountButton.setBounds(140, 11, 120, 23);
		contentPane.add(deleteAccountButton);
		
		//DepositButton actions
		//Opens new JDialog 
		JButton depositButton = new JButton("Deposit");
		depositButton.setMinimumSize(new Dimension(105, 23));
		depositButton.setMaximumSize(new Dimension(105, 23));
		depositButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int selectedRow = accountTable.getSelectedRow();
				Customer customer = getSelectedCustomer(selectedRow);
				//Open new window if customer is selected
				if(customer != null){
					DepositMenu depositMenu = new DepositMenu(frame, true, bank, customer);
					depositMenu.setVisible(true);
					reloadData(selectedRow, customer); // Reloads data after deposit
					
				}
			}
		
		});
		
		depositButton.setBounds(270, 11, 120, 23);
		contentPane.add(depositButton);
		
		//withdrawButton actions
		//Opens new JDialog
		JButton withdrawButton = new JButton("Withdrawal");
		withdrawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = accountTable.getSelectedRow();
				Customer customer = getSelectedCustomer(selectedRow);
				//Open new window if customer is selected
				if(customer != null){
					WithdrawMenu withdrawMenu = new WithdrawMenu(frame, true, bank, customer);
					withdrawMenu.setVisible(true);
					reloadData(selectedRow, customer); // Reloads row data after withdraw
					
				}
			}
		
		});
		withdrawButton.setBounds(400, 11, 120, 23);
		contentPane.add(withdrawButton);
		
		//ScrollPane to able scrolling for accountTable
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 45, 508, 179);
		contentPane.add(scrollPane);
		
		//Table for customers
		accountTable = new JTable();
		accountTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		accountTable.setAutoCreateRowSorter(false);
		accountTable.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(accountTable);
		accountTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"First Name", "Last Name", "Account Number", "Balance"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, Integer.class, Double.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		//detailedInfoButton actions
		//Opens new JDialog where is detailed info about selected customer
		JButton detailedInfoButton = new JButton("Detailed Account Information");
		detailedInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = accountTable.getSelectedRow();
				Customer customer = getSelectedCustomer(selectedRow);
				//Open new window if customer is selected
				if(customer != null){
				DetailedInfo detailMenu = new DetailedInfo(frame, true, bank, customer);
				detailMenu.setVisible(true);
				
			}
			}
		
		});
		
		
		detailedInfoButton.setBounds(140, 235, 250, 23);
		contentPane.add(detailedInfoButton);
		
		//transferButton actions
		//Open new JDialog to send money to other account
		JButton transferButton = new JButton("Transfer money to other account");
		transferButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = accountTable.getSelectedRow();
				Customer customer = getSelectedCustomer(selectedRow);
				//Open new window if customer is selected and there are at least 2 customers on the table
				if(customer != null && bank.getCustomers().size() > 1){
				TransferMoney detailMenu = new TransferMoney(frame, true, bank, customer, accountTable);
				detailMenu.setVisible(true);
				refreshTable(); //Refreshes the table after money transfer
				
			}
			}
			
			
		});
		transferButton.setBounds(10, 276, 250, 23);
		contentPane.add(transferButton);
		
		//transActionsButton actions
		//Opens new JDialog to show latest transactions 
		JButton transactionsButton = new JButton("Latest Transactions");
		transactionsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = accountTable.getSelectedRow();
				Customer customer = getSelectedCustomer(selectedRow);
				//Open new window if customer is selected
				if(customer != null){
				Transactions transactionsMenu = new Transactions(frame, true, bank, customer);
				transactionsMenu.setVisible(true);
			}
			}
		});
		transactionsButton.setBounds(270, 276, 248, 23);
		contentPane.add(transactionsButton);
		accountTable.getColumnModel().getColumn(0).setResizable(false);
		accountTable.getColumnModel().getColumn(1).setResizable(false);
		accountTable.getColumnModel().getColumn(2).setResizable(false);
		accountTable.getColumnModel().getColumn(3).setResizable(false);
		
		
		
		
	}
	//Reload data of selected row
	private static void reloadData(int selectedRow, Customer customer) {
		DefaultTableModel model = (DefaultTableModel) accountTable.getModel();
        model.setValueAt(customer.getFirstName(), selectedRow, 0);
        model.setValueAt(customer.getLastName(), selectedRow, 1);
        model.setValueAt(customer.getAccount().getAccountNumber(), selectedRow, 2);
        model.setValueAt(customer.getAccount().getBalance(), selectedRow, 3);				
	}
	
	//Get selected customer from the Account table
	private Customer getSelectedCustomer(int selectedRow) {
		Customer customer = null;
        if (selectedRow >= 0) {
            int accountNumber = (int) accountTable.getValueAt(selectedRow, 2);
            customer = bank.getCustomerByAccountNumber(accountNumber);
        }
        return customer;
	}
	
	//Add new customer to table
	private static void addCustomerToTable(Customer customer){
		DefaultTableModel model = (DefaultTableModel) accountTable.getModel();
		model.addRow(new Object[]{});
		reloadData(model.getRowCount() -1, customer);
	}
	
	//Reload the table. Used when opening saved file
	private static void reloadTable() {
		for(Customer c : bank.getCustomers()){
			addCustomerToTable(c);
		}
	}
	//Refresh the whole table
	private static void refreshTable() {
		  int i=0;
		  for(Customer c : bank.getCustomers()){
		   reloadData(i, c);
		   i=i+1;   
		  }
		 }
	//Delete selected customer from table
	private static void deleteCustomerFromTable(int row) {
		DefaultTableModel model = (DefaultTableModel) accountTable.getModel();
		model.removeRow(row);
		
	}
	
}
