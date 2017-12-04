package bankapp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//Class to show Account details
public class DetailedInfo extends JDialog {	
	private final JPanel contentPanel = new JPanel();

	//Constructor with parameters: parent, modal, bank, customer
	public DetailedInfo(java.awt.Frame parent, boolean modal, Bank bank, Customer customer) {
		//call parent constructor with arguments parent and modal
		super(parent, modal);
		//UI setup
		setTitle("Account Details");
		setLocationRelativeTo(null);
		setBounds(100, 100, 348, 300);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 434, 10);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(new GridLayout(1, 0, 0, 0));
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 228, 172, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				//OK button actions
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		//Labels and fields to show start from here
		
		JLabel lblFirstName = new JLabel("First name:");
		lblFirstName.setBounds(10, 11, 72, 14);
		getContentPane().add(lblFirstName);
		
		JLabel firstNameField = new JLabel("");
		firstNameField.setBounds(165, 11, 157, 14);
		getContentPane().add(firstNameField);
		firstNameField.setText(customer.getFirstName());
		
		{
			JLabel lblLastName = new JLabel("Last name:");
			lblLastName.setBounds(10, 36, 72, 14);
			getContentPane().add(lblLastName);
		}
		{
			//Last name field
			JLabel lastNameField = new JLabel("");
			lastNameField.setBounds(165, 36, 138, 14);
			getContentPane().add(lastNameField);
			lastNameField.setText(customer.getLastName()); //Get last name of customer
		}
		{
			JLabel lblAccountNumber = new JLabel("Account number:");
			lblAccountNumber.setBounds(10, 86, 109, 14);
			getContentPane().add(lblAccountNumber);
		}
		{
			//Social security number field
			JLabel ssnField = new JLabel("");
			ssnField.setBounds(165, 61, 138, 14);
			getContentPane().add(ssnField);
			ssnField.setText(customer.getSsn()); //Get social security number of chosen customer
		}
		{
			JLabel lblAccountType = new JLabel("Account type:");
			lblAccountType.setBounds(10, 111, 109, 14);
			getContentPane().add(lblAccountType);
		}
		{
			//Account number field
			JLabel accountNumberField = new JLabel("");
			accountNumberField.setBounds(165, 86, 120, 14);
			getContentPane().add(accountNumberField);
			accountNumberField.setText(String.valueOf(customer.getAccount().getAccountNumber())); //Get Account number of chosen customer
		}
		{
			JLabel lblBalance = new JLabel("Balance:");
			lblBalance.setBounds(10, 136, 109, 14);
			getContentPane().add(lblBalance);
		}
		{
			//Account type field
			JLabel accountTypeField = new JLabel("");
			accountTypeField.setBounds(165, 111, 120, 14);
			getContentPane().add(accountTypeField);
			accountTypeField.setText(customer.getAccount().getAccountType()); //Get selected customer's account type
		}
		{
			JLabel lblInterestRate = new JLabel("InterestRate");
			lblInterestRate.setBounds(10, 161, 109, 14);
			getContentPane().add(lblInterestRate);
		}
		{
			//Balance field
			JLabel balanceField = new JLabel("");
			balanceField.setBounds(165, 136, 120, 14);
			getContentPane().add(balanceField);
			balanceField.setText(String.valueOf(customer.getAccount().getBalance()) + "€"); //Get Balance of chosen customer
		}
		{
			JLabel lblSsn = new JLabel("Social security number:");
			lblSsn.setBounds(10, 61, 145, 14);
			getContentPane().add(lblSsn);
		}
		{
			//Interest field
			JLabel interestField = new JLabel("");
			interestField.setBounds(165, 161, 120, 14);
			getContentPane().add(interestField);
			interestField.setText(String.valueOf(customer.getAccount().getInterest()) + "%"); //Get Customer's interest rate
		}
		
		
		
		
	}
	
}
