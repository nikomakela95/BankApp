package bankapp;

import java.io.Serializable;
import java.util.Date;
//Class for account transactions
public class AccountTransactions implements Serializable{
	//Instance variables
	private double amount;
	private Date dateTime;
	private String transactionType;
	
	//Constructor. Takes amount and type as parameters
	AccountTransactions(double amount, String type){
		this.amount = amount;
		this.transactionType = type;
		this.dateTime = new Date();
	}
	//Print the transaction details
	public String toString(){
		return "Date: " + dateTime + "Account\n" + 
				"Transaction type: " + transactionType + "\n" + 
				"Amount: " + amount + "\n" + 
				"\n";
	}
}


