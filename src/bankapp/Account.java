package bankapp;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;

//Implement Serializable to able saving
public abstract class Account implements Serializable{
	//Instance variables
    private double balance = 0;
    private double interest = 0.02;
    private int accountNumber;
    private static int numberOfAccounts = 1000000;
    

    //Array list for transactions
    ArrayList<AccountTransactions> transactions = new ArrayList<AccountTransactions>();
   
    //Constructor
    Account(){
    	accountNumber = getNextAccountNumber();
    }
    //get Account Type (Checking or Savings)
    public abstract String getAccountType();
 
    //Get customer's account balance
    public double getBalance(){
    	return balance;
    }
    //Set account balance
    public void setBalance(double balance){
    	this.balance = balance;
    }
    //Get interest rate for withdraw and deposit
    public double getInterest(){
    	return interest * 100;
    }
    //Set interest rate
    public void setInterest(double interest){
    	this.interest = interest;
    }
    //Get customers account number
    public int getAccountNumber(){
    	return accountNumber;
    }
    //Withdraw money
    public void withdraw(double amount){
    	if(amount + 2 > balance){
    		JOptionPane.showMessageDialog(null, "You don't have enough money for withdraw.");
    	}
    	else{
    		balance-= amount +2;
    		checkInterest(0);
    		AccountTransactions action = new AccountTransactions(amount, "Withdrawal");
    		this.transactions.add(action);
    }
    }
    //Transfer money to other account
    public void giveMoney(double amount){
    	
    	 if(amount > balance){
    		JOptionPane.showMessageDialog(null, "You don't have enough money for transfer.");
    	}
    	else {
    		balance -= amount;
    		AccountTransactions action = new AccountTransactions(amount, "Sent money transfer");
    		this.transactions.add(action);
    	}
    }
    //Deposit money
    public void deposit(double amount){
    	if(amount<= 0){
    		JOptionPane.showMessageDialog(null, "You cannot deposit negative amount of money.");
    	}else{
    	//Make deposit and add event to transactions	
    	checkInterest(amount);
    	amount = amount + amount * interest;
    	balance +=amount;
    	AccountTransactions action = new AccountTransactions(amount, "Deposit");
    	this.transactions.add(action);
    	}
    }
    //Get money from other account
    public void getMoney(double amount){
    	if(amount > 0){
    		balance += amount;
    		AccountTransactions action = new AccountTransactions(amount, "Received money transfer");
    		this.transactions.add(action);
    	}
    		
    	
    }
    //Check interest rate for account
    public void checkInterest(double amount){
    	if(balance + amount> 10000){
    		interest = 0.05;
    	}else{
    		interest = 0.02;
    	}
    		
    }
    //Get and return next account number
    public static int getNextAccountNumber(){
		return ++numberOfAccounts;
	}
    
    //Set account number
    void setAccountNumber(int accountNumber) {
	this.accountNumber = accountNumber;
}
    //Get transactions from array list
    public String getTransactions(){
	String ta = "";
	for(AccountTransactions c : this.transactions){
		ta = ta + " " + c.toString();   
		    
		  }
	return ta;
}

    

}
