package bankapp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//Class which shows customers latest transactions
public class Transactions extends JDialog {
	
	private final JPanel contentPanel = new JPanel();

	//Constructor. Takes parameters parent, modal, bank, customer
	public Transactions(java.awt.Frame parent, boolean modal, Bank bank, Customer customer) {
		//UI setup
		setTitle("Transactions");
		setBounds(100, 100, 553, 406);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			//JScrollPane to able scrolling when observing transactions
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 11, 517, 312);
			contentPanel.add(scrollPane);
			
			//Show customer's transactions
			JTextArea transactionsField = new JTextArea();
			scrollPane.setViewportView(transactionsField);
			transactionsField.setEnabled(false);
			String transactions = customer.getAccount().getTransactions();
			transactionsField.setText(transactions);
		}
		
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				//OK button actions
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();//Close the window
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}
