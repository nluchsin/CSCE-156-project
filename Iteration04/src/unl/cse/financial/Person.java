//info in FinancialDemo.java
package unl.cse.financial;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.JsonElement;

//import java.time.LocalDate;

public class Person {
	private String code;
	private String[] name;
	private String[] Address;
	private ArrayList<String> email;
	
	//Set and output Code variable
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	//Set parse and output Broker code
	
	//Set, parse and output name variable
	public String[] getName() {
		return name;
	}
	
	public String getFirstName(){;
		return name[0];
	}
	
	public String getLastName(){
		return name[1];
	}
	public String getName1(){
		return this.getLastName() + ", " + this.getFirstName();
	}
	public void setName(String[] name) {
		this.name = name;
	}
	
	//Set, parse and output address
	public String getAddress() {
		return Address[0] + ", " + Address[1] + ", " + Address[2] + ", " + Address[3] + ", " + Address[4] + " " + Address[5];
	}
	
	public String getStreet(){
		return Address[0];
	}
	
	public String getCity(){
		return Address[1];
	}
	
	public String getState(){
		return Address[2];
	}
	
	public String getCountry(){
		return Address[3];
	}
	
	public String getZipCode(){
		return Address[4];
	}
	
	public void setAddress(String[] address) {
		Address = address;
	}
	
	//Set, parse and output email
	public String[] getEmail() {
		String[] parse = new String[email.size()];
		for(int i = 0; i<email.size(); i++){
			parse[i] = email.get(i);
		}
		return parse;
	}

	public void setEmail(ArrayList<String> email) {
		this.email = email;
	}
	
	//Creates Person class
	public Person(String code, String name[], String Address[], ArrayList<String> email){
    	this.code = code;
    	this.name = name;
    	this.Address = Address;
    	this.email = email;
    }

	
}
