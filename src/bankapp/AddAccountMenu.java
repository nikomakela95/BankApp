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

public class AddAccountMenu extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField ssnField;
	private JTextField depositField;

	private Bank bank;
	private Customer customer;
	
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the dialog.
	 */
	public AddAccountMenu() {
		
		setLocationRelativeTo(null);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		this.bank = bank;
        customer= null;
		
		
		
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(10, 11, 71, 30);
		contentPanel.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(10, 52, 71, 24);
		contentPanel.add(lblLastName);
		
		JLabel lblNewLabel = new JLabel("SSN");
		lblNewLabel.setBounds(10, 87, 46, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblInitialDeposit = new JLabel("Initial Deposit");
		lblInitialDeposit.setBounds(10, 112, 71, 30);
		contentPanel.add(lblInitialDeposit);
		
		JLabel lblType = new JLabel("Account Type");
		lblType.setBounds(10, 153, 71, 30);
		contentPanel.add(lblType);
		
		firstNameField = new JTextField();
		firstNameField.setBounds(123, 16, 86, 20);
		contentPanel.add(firstNameField);
		firstNameField.setColumns(10);
		
		lastNameField = new JTextField();
		lastNameField.setBounds(123, 54, 86, 20);
		contentPanel.add(lastNameField);
		lastNameField.setColumns(10);
		
		ssnField = new JTextField();
		ssnField.setBounds(123, 87, 86, 20);
		contentPanel.add(ssnField);
		ssnField.setColumns(10);
		
		depositField = new JTextField();
		depositField.setBounds(123, 117, 86, 20);
		contentPanel.add(depositField);
		depositField.setColumns(10);
		
		
		
		JComboBox typeField = new JComboBox();
		typeField.setModel(new DefaultComboBoxModel(new String[] {"Checking", "Savings"}));
		typeField.setBounds(123, 158, 86, 20);
		contentPanel.add(typeField);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					

					public void actionPerformed(ActionEvent e) {
						StringBuilder warnings = new StringBuilder();
						String firstName = "", lastName = "", ssn = "", depositString = "";
						double amount = 0;
						
						if(firstNameField.getText().isEmpty()){
							warnings.append("Enter First name\n");
						}else{
							firstName = firstNameField.getText();
						}
						
						if(lastNameField.getText().isEmpty()){
							warnings.append("Enter last name\n");
						}else{
							lastName = lastNameField.getText();
						}
						
						if (!ssnField.getText().matches("\\d{3}-?\\d{2}-?\\d{4}")) {
				            warnings.append("SSN must be 9 digits\n");
				        } else {
				            ssn = ssnField.getText().replace("-", "");
				        }
						
						if(depositField.getText().isEmpty()){
							warnings.append("Enter deposit value");
						}else {
				            try {
				                amount = round(Double.parseDouble(depositField.getText()), 2);
				            } catch (NumberFormatException ex) {
				                warnings.append("Initial deposit must be a number.");
				            }
						}
						if(warnings.length()> 0 ){
							JOptionPane.showMessageDialog(null, warnings.toString(), "Input warnings", JOptionPane.WARNING_MESSAGE);
						}else{
							Account account = null;
							if(typeField.getSelectedItem().toString() == "Checking"){
								account = new Checking(amount);
							}
							else if(typeField.getSelectedItem().toString()=="Savings"){
								account = new Savings(amount);
							}
							
							Customer customer = new Customer(firstName, lastName, ssn, account);
							bank.addCustomer(customer);
							dispose();
								
							
						}
					}
					
					
					
					private double round(double value, int places) {
				        if (places < 0) {
				            throw new IllegalArgumentException();
				        }			 
				        BigDecimal bd = new BigDecimal(value);
				        bd = bd.setScale(places, RoundingMode.FLOOR);
				        return bd.doubleValue();
				    }
					
					
					
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		
	}


	Customer getCustomer() {
		return customer;
	}
}
