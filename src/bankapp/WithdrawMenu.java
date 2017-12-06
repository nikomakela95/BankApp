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

//Class where user can withdraw money from account
public class WithdrawMenu extends JDialog {
	//instances
	private final JPanel contentPanel = new JPanel();
	private Customer customer;
	private JTextField withdrawAmountField;
	private Bank bank;
	
	
	//Create the dialog
	//Constructor with parameters parent, modal, bank, customer
	public WithdrawMenu(java.awt.Frame parent, boolean modal, Bank bank, Customer customer) {
		//calls parent constructor with arguments parent and modal
		super(parent, modal);
		setTitle("Withdraw");
		this.customer = customer;
		JDialog dialog = this;
		
		//Set JPanel layout
		setBounds(100, 100, 302, 175);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblWithdrawAmount = new JLabel("Withdraw amount: ");
			contentPanel.add(lblWithdrawAmount);
		}
		{
			//Field for withdraw amount
			withdrawAmountField = new JTextField();
			contentPanel.add(withdrawAmountField);
			withdrawAmountField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				//Actions for OK button
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						StringBuilder warnings = new StringBuilder();
						double balance = customer.getAccount().getBalance();
						
						//check if amount field is empty
						if(withdrawAmountField.getText().isEmpty()){
							warnings.append("Enter withdraw amount.");
						}
						if(warnings.length() > 0){
							JOptionPane.showMessageDialog(null, warnings.toString(), "Warning!", JOptionPane.WARNING_MESSAGE);
						}
						else{
							double amount = 0;
							try {
				                amount = Bank.round(Double.parseDouble(withdrawAmountField.getText()), 2);
				                if(amount <= 0 || amount +2 > balance ){
				                	JOptionPane.showMessageDialog(null, "You don't have enough money for withdraw or you are trying to withdraw negative amount of money.\n(Incurred fee is 2€)");

				                }else{
				                //Confirm withdraw from user	
				                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to withdraw " + amount + "€ from account " + customer.getAccount().getAccountNumber() + "\n(Incurred fee is 2 Euros)");
				                if(confirm == JOptionPane.OK_OPTION && (balance ) > amount && amount > 0){
				                	
				                //Do withdraw when action is confirmed	
				                customer.getAccount().withdraw(amount);
				                JOptionPane.showMessageDialog(null, "The withdraw was successful!\nYou withdrew " + amount+ "€\nIncurred fees: 2€");
				                dialog.dispose(); //Close window when withdraw action is done
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
				//Cancel button actions
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
