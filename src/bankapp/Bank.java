package bankapp;

import java.io.Serializable;
import java.math.BigDecimal;

import java.math.RoundingMode;
import java.util.ArrayList;

public class Bank implements Serializable {
	
	//Array list for all customers
	ArrayList<Customer> customers = new ArrayList<Customer>();
	
	//Round the given amount
	public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
	// Add new customer
	public void addCustomer(Customer customer) {
		if(accountExists(customer.getAccount().getAccountNumber())){
			customer.getAccount().setAccountNumber(validAccountNumber());
		}
		customers.add(customer);
		
	}
	//get valid account number
	private int validAccountNumber(){
		int accountNumber = 0;
		do{
			accountNumber = Account.getNextAccountNumber();
		}while(accountExists(accountNumber));
		return accountNumber;
	}
	
	//Check if account exists by looping through customers list
	public boolean accountExists(int accountNumber){
		for(Customer c: customers){
			if(c.getAccount().getAccountNumber() == accountNumber){
				return true;
			}
		}
		return false;
	}
	//Get customer from list
	Customer getCustomer(int account){
		return customers.get(account);
	}
	//Get customers from array list
	ArrayList<Customer> getCustomers(){
		return customers;
	}
	//Get customer by account number with for loop
	Customer getCustomerByAccountNumber(int accountNumber){
		Customer customer = null;
		
		for (Customer cust : customers){
			if (cust.getAccount().getAccountNumber() == accountNumber){
				customer = cust;
				break;
			}
		}
		return customer;
		
	}
	//Delete customer
	public void deleteCustomer(Customer customer) {
		customers.remove(customer);
	}
	
	

	

	
	
	

}
