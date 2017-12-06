package bankapp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

//Class which able user to send money to other account
public class TransferMoney extends JDialog {

	//Instances
	private final JPanel contentPanel = new JPanel();
	private JTextField transferAmountField;
	private static JTable accountTable;
	

	
	//Constructor takes parent, modal, bank, customer and accountTable as parameters
	public TransferMoney(java.awt.Frame parent, boolean modal, Bank bank, Customer customer, JTable accountTable) {
		//Calls parent constructor with arguments parent and modal
		super(parent, modal);
		setTitle("Transfer money");
		
		this.accountTable = accountTable;
		setBounds(100, 100, 484, 189);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
	
		//Define labels and fields 
		JLabel lblTransferMoneyTo = new JLabel("Transfer money to account:");
		lblTransferMoneyTo.setBounds(10, 44, 156, 14);
		contentPanel.add(lblTransferMoneyTo);
		
		JLabel lblTransferAmount = new JLabel("Enter transfer amount:");
		lblTransferAmount.setBounds(10, 69, 146, 14);
		contentPanel.add(lblTransferAmount);
		
		//field for transfer amount
		transferAmountField = new JTextField();
		transferAmountField.setBounds(282, 72, 142, 20);
		contentPanel.add(transferAmountField);
		transferAmountField.setColumns(10);
		
		//Field for account where to send money
		JComboBox toAccountField = new JComboBox();
		toAccountField.setBounds(282, 41, 142, 20);
		contentPanel.add(toAccountField);
		
		JLabel lblTransferMoneyFrom = new JLabel("Transfer money from account:");
		lblTransferMoneyFrom.setBounds(10, 19, 166, 14);
		contentPanel.add(lblTransferMoneyFrom);
		
		//Field for account where money is sent from
		JComboBox fromAccountField = new JComboBox();
		fromAccountField.setBounds(282, 16, 142, 20);
		contentPanel.add(fromAccountField);
		
		
		
		//Sets all other customers to combo box except selected customer
		//Here user can choose which account should receive the money transfer
		for(int count = 0; count < bank.customers.size(); count++){
			if(customer.getAccount().getAccountNumber()!=bank.customers.get(count).getAccount().getAccountNumber()){
			toAccountField.addItem(bank.customers.get(count).getAccount().getAccountNumber());
		}
		}
		//Sets selected customer to combo box
		//Shows the account where money is sent from
		fromAccountField.addItem(customer.getAccount().getAccountNumber());
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				//Actions for OK button
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int count = 0;
						double amount = 0;
						String amountString;
						amountString = transferAmountField.getText();
						try{
							amount = Double.parseDouble(amountString); //Change amount at text field to double
						}
						catch(NumberFormatException ex){
							if(!transferAmountField.getText().isEmpty()){
								JOptionPane.showMessageDialog(null, "Transfer amount must be a number");
						}
						}
						int accountNbr=Integer.parseInt(toAccountField.getSelectedItem().toString());
						
						Customer toAccount = null;
						toAccount= bank.getCustomerByAccountNumber(accountNbr); //Customer who receives the money
						
							//Check if customer has enough money for transfer
							if(amount > customer.getAccount().getBalance()){
								JOptionPane.showMessageDialog(null, "You don't have enough money for transfer.");
							}
								else if(transferAmountField.getText().isEmpty() || amount <= 0){
									JOptionPane.showMessageDialog(null, "You cannot transfer negative amount of money.");
								}
								else{
									//Confirm the action from user
									int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to transfer " + amount + "€ to account " + toAccount.getAccount().getAccountNumber() + "?"); 
									if (confirm == JOptionPane.OK_OPTION){
										//subtract money from account
										customer.getAccount().giveMoney(amount);
										//increase money for account
										toAccount.getAccount().getMoney(amount);
										dispose(); // Close window after transfer
							}
							}
							
						}
					
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				//cancel button actions
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose(); //Close window
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
	}
	}

