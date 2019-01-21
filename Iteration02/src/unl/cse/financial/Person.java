//info in FinancialDemo.java
package unl.cse.financial;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.JsonElement;

//import java.time.LocalDate;

public class Person {
	private String code;
	private String name;
	private String Address;
	private String email;
	
	//Set and output Code variable
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	//Set parse and output Broker code
	
	//Set, parse and output name variable
	public String getName() {
		return name;
	}
	
	public String getFirstName(){
		String[] name1 = name.split(",");
		return name1[1];
	}
	
	public String getLastName(){
		String[] name1 = name.split(",");
		return name1[0];
	}
	public void setName(String name) {
		this.name = name;
	}
	
	//Set, parse and output address
	public String getAddress() {
		return Address;
	}
	
	public String getStreet(){
		String[] address1 = Address.split(",");
		return address1[0];
	}
	
	public String getCity(){
		String[] address1 = Address.split(",");
		return address1[1];
	}
	
	public String getState(){
		String[] address1 = Address.split(",");
		return address1[2];
	}
	
	public String getCountry(){
		String[] address1 = Address.split(",");
		return address1[4];
	}
	
	public String getZipCode(){
		String[] address1 = Address.split(",");
		if(address1.length == 4){
			return " ";
		}else{
		return address1[3];
		}
	}
	
	public void setAddress(String address) {
		Address = address;
	}
	
	//Set, parse and output email
	public String[] getEmail() {
		ArrayList<String> email1 = new ArrayList<String>();
		String[] parse = email.split(",");
		for(int i = 0; i<parse.length; i++){
			email1.add(parse[i]);
		}
		return parse;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	//Creates Person class
	public Person(String code, String name, String Address, String email){
    	this.code = code;
    	this.name = name;
    	this.Address = Address;
    	this.email = email;
    }
	
}
