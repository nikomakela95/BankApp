package bankapp;

//class for Savings account
//Inherits from Account class
public class Savings extends Account{
	private static String accountType = "Savings";
	
	//Constructor and takes initialDeposit as parameter
	Savings(double initialDeposit){
		this.setBalance(initialDeposit);
		this.checkInterest(0);
	}
	
	//Returns account type
	@Override
	public String getAccountType(){
		return accountType;
	}
}
