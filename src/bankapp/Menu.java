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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class Menu extends JFrame {

	private JPanel contentPane;
	private JTable accountTable;
	private Bank bank;

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public Menu() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 544, 394);
		setLocationRelativeTo(null);
		bank = new Bank();
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton addAccountButton = new JButton("Add Account");
		addAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				AddAccountMenu menu = new AddAccountMenu();
				menu.setVisible(true);
				if(menu.getCustomer() != null){
					addCustomerToTable(menu.getCustomer());
				}
			}
			
			private void addCustomerToTable(Customer customer){
				DefaultTableModel model = (DefaultTableModel) accountTable.getModel();
				model.addRow(new Object[] {customer.getFirstName(), customer.getLastName(), customer.getAccount().getAccountNumber(), customer.getAccount().getBalance() });
				
			}
			
			private void reloadTable(){
				
			}
		});
		
		
		addAccountButton.setMinimumSize(new Dimension(105, 23));
		addAccountButton.setMaximumSize(new Dimension(105, 23));
		addAccountButton.setBounds(10, 11, 120, 23);
		contentPane.add(addAccountButton);
		
		JButton deleteAccountButton = new JButton("Delete Account");
		deleteAccountButton.setBounds(140, 11, 120, 23);
		contentPane.add(deleteAccountButton);
		
		JButton depositButton = new JButton("Deposit");
		depositButton.setMinimumSize(new Dimension(105, 23));
		depositButton.setMaximumSize(new Dimension(105, 23));
		depositButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		depositButton.setBounds(270, 11, 120, 23);
		contentPane.add(depositButton);
		
		JButton withdrawButton = new JButton("Withdrawal");
		withdrawButton.setBounds(400, 11, 120, 23);
		contentPane.add(withdrawButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 45, 508, 278);
		contentPane.add(scrollPane);
		
		accountTable = new JTable();
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
		
	
		
		
		accountTable.getColumnModel().getColumn(0).setResizable(false);
		accountTable.getColumnModel().getColumn(1).setResizable(false);
		accountTable.getColumnModel().getColumn(2).setResizable(false);
		accountTable.getColumnModel().getColumn(3).setResizable(false);
		
		
	}
	
	
}
