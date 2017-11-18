package bankapp;

import java.math.BigDecimal;

import java.math.RoundingMode;
import java.util.ArrayList;

public class Bank {
	
	
	ArrayList<Customer> customers = new ArrayList<Customer>();
	
	public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

	public void addCustomer(Customer customer) {
		customers.add(customer);
		
	}
	
	Customer getCustomer(int account){
		return customers.get(account);
	}
	
	ArrayList<Customer> getCustomers(){
		return customers;
	}

	

	
	
	

}
