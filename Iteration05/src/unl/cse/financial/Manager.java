package unl.cse.financial;

import java.util.ArrayList;

public class Manager extends Person{
	private String[] broker;
	//Getters and Setters
	public String getBrokerLetter() {
		return broker[0];
	}
	
	public String getBrokerSEC() {
		return broker[1];
	}
	public void setBroker(String[] broker) {
		this.broker = broker;
	}
	//Generate Class
	public Manager(String code, String[] name, String[] Address, String[] broker, ArrayList<String> email) {
		super(code, name, Address, email);
		this.broker = broker;
	}
	
}
