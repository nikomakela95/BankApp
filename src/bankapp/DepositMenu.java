package bankapp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//Class to make deposit
public class DepositMenu extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField depositAmmountField;
	private final Customer customer;
	private Bank bank;

	
	

	
	//Constructor with parameters parent, modal, bank and customer
	public DepositMenu(java.awt.Frame parent, boolean modal, Bank bank, Customer customer) {
		super(parent, modal);
		setTitle("Deposit");
		this.customer = customer;
		JDialog dialog = this;
		
		//UI setup
		setBounds(100, 100, 224, 162);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblDepositAmount = new JLabel("Deposit Amount");
			contentPanel.add(lblDepositAmount);
		}
		{
			//Field for deposit amount
			depositAmmountField = new JTextField();
			contentPanel.add(depositAmmountField);
			depositAmmountField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				//OK button functions
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						StringBuilder warnings = new StringBuilder();
						
						//Check if deposit amount field is empty
						if(depositAmmountField.getText().isEmpty()){
							warnings.append("Enter deposit amount.");
						}
						if(warnings.length() > 0){
							JOptionPane.showMessageDialog(null, warnings.toString(), "Warning!", JOptionPane.WARNING_MESSAGE);
						}
						else{
							double amount = 0;
							try {
				                amount = Bank.round(Double.parseDouble(depositAmmountField.getText()), 2);
				                if(amount <= 0){
				                	JOptionPane.showMessageDialog(null, "You cannot deposit negative amount of money.");

				                }else{
				                //Confirm deposit from user	
				                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to deposit " + amount + "€ to account " + customer.getAccount().getAccountNumber());
				                if(confirm == JOptionPane.OK_OPTION ){
				                //Do deposit when action is confirmed	
				                customer.getAccount().deposit(amount);
				                JOptionPane.showMessageDialog(null, "The deposit was successful!\nYou got " + customer.getAccount().getInterest() + "% interest (" + (amount * customer.getAccount().getInterest() / 100) + "€)");
				                dialog.dispose(); // Close JDialog
				                }
				                }
				            } catch (NumberFormatException ex) {
				                warnings.append("The withdraw must be a number.");
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
						dispose(); //Close dialog
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
