package bankapp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.awt.event.ActionEvent;
//Class for adding new account
public class AddAccountMenu extends JDialog {
	//Instances
	private final JPanel contentPanel = new JPanel();
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField ssnField;
	private JTextField depositField;

	private Bank bank;
	private Customer customer;
	
	

	//Constructor 
	public AddAccountMenu(Bank bank, boolean modal, java.awt.Frame parent) {
		//call parent constructor
		super(parent, modal);
		//JDialog setup
		setLocationRelativeTo(parent);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		this.bank = bank;
        customer= null;
		
		
		//Defining labels and fields
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(10, 11, 71, 30);
		contentPanel.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(10, 52, 71, 24);
		contentPanel.add(lblLastName);
		
		JLabel lblNewLabel = new JLabel("Social security number");
		lblNewLabel.setBounds(10, 87, 142, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblInitialDeposit = new JLabel("Initial Deposit");
		lblInitialDeposit.setBounds(10, 112, 71, 30);
		contentPanel.add(lblInitialDeposit);
		
		JLabel lblType = new JLabel("Account Type");
		lblType.setBounds(10, 153, 71, 30);
		contentPanel.add(lblType);
		
		//Field for first name
		firstNameField = new JTextField();
		firstNameField.setBounds(176, 16, 86, 20);
		contentPanel.add(firstNameField);
		firstNameField.setColumns(10);
		
		//Field for last name
		lastNameField = new JTextField();
		lastNameField.setBounds(176, 54, 86, 20);
		contentPanel.add(lastNameField);
		lastNameField.setColumns(10);
		
		//Field for social security number
		ssnField = new JTextField();
		ssnField.setBounds(176, 84, 86, 20);
		contentPanel.add(ssnField);
		ssnField.setColumns(10);
		
		//Field for initial deposit
		depositField = new JTextField();
		depositField.setBounds(176, 117, 86, 20);
		contentPanel.add(depositField);
		depositField.setColumns(10);
		
		
		//Field for account type
		JComboBox typeField = new JComboBox();
		typeField.setModel(new DefaultComboBoxModel(new String[] {"Checking", "Savings"}));
		typeField.setBounds(176, 158, 86, 20);
		contentPanel.add(typeField);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				//OK button setup
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						StringBuilder warnings = new StringBuilder();
						String firstName = "", lastName = "", ssn = "", depositString = "";
						double amount = 0;
						//Verify First name
						if(firstNameField.getText().isEmpty()){
							warnings.append("Enter First name\n");
						}else{
							firstName = firstNameField.getText();
						}
						//Verify last name
						if(lastNameField.getText().isEmpty()){
							warnings.append("Enter last name\n");
						}else{
							lastName = lastNameField.getText();
						}
						//Verify Social security number
						if (!ssnField.getText().matches("([0-3][0-9])([0-1][0-9])([0-9]{2})(\\-|[A]|\\+)([0-9]{3})([0-9]|[A-Z])")) {
				            warnings.append("Social security number must be in form DDMMYY-ZZZQ\nExample: 010189-123A\n");
				        } else {
				            ssn = ssnField.getText();
				        }
						//Verify Deposit
						if(depositField.getText().isEmpty()){
							warnings.append("Enter deposit value");
						}else {
				            try {
				            	//Change amount from depositField to double and use Bank.round method
				                amount = Bank.round(Double.parseDouble(depositField.getText()), 2);
				            } catch (NumberFormatException ex) {
				                warnings.append("Initial deposit must be a number.\n");
				            }
						}
						if(amount <= 0){
							warnings.append("Deposition amount must be positive");
						}
						
						//Show message dialog if there are warnings
						if(warnings.length()> 0 ){
							JOptionPane.showMessageDialog(null, warnings.toString(), "Input warnings", JOptionPane.WARNING_MESSAGE);
						}else{
							Account account = null;
							//Verify account type
							if(typeField.getSelectedItem().toString() == "Checking"){
								account = new Checking(amount);
							}
							else if(typeField.getSelectedItem().toString()== "Savings"){
								account = new Savings(amount);
							}
							if (account != null){
								
							//Create new customer with given details and add event to transactions list
							customer = new Customer(firstName, lastName, ssn, account);
							bank.addCustomer(customer);
							AccountTransactions action = new AccountTransactions(amount, "Initial Deposit");
					    	customer.getAccount().transactions.add(action);
							dispose(); //Close JDialog
							}
						
						}
					}
										
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				//Cancel button setup
				JButton cancelButton = new JButton("Cancel");
				
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose(); //Close the window
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	//Get customer
	Customer getCustomer() {
		return customer;
	}
}
