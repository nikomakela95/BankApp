package bankapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable{
	//Instance variables
    private final String firstName;
    private final String lastName;
    private final String ssn;
    private final Account account;
    
	
    
    //Constructor. Takes first name, last name, social security number and account as parameters. 
    Customer(String firstName, String lastName, String ssn, Account account) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
        this.account = account;
    }    
    
    //Get account
    Account getAccount(){
        return account;
    }

    //Return First name
    public String getFirstName() {
        return firstName;
    }

    //Return Last name
    public String getLastName() {
        return lastName;
    }

    //Return social security number
    public String getSsn() {
        return ssn;
    }
    
    
}
