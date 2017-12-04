package bankapp;
//Class for checking account
public class Checking extends Account{
	private static String accountType = "Checking";
	
	//Constructor. Takes initialDeposit as parameter
	Checking(double initialDeposit){
		this.setBalance(initialDeposit);
		this.checkInterest(0);
	}
	
	//Return the account type
	@Override
	public String getAccountType(){
		return accountType;
	}
	

}
