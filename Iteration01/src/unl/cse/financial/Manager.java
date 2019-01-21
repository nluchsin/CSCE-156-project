package unl.cse.financial;

public class Manager extends Person{
	private String broker;
	public String getBrokerLetter() {
		String[] broker1 = broker.split(",");
		return broker1[0];
	}
	
	public String getBrokerSEC() {
		String[] broker1 = broker.split(",");
		return broker1[1];
	}
	public void setBroker(String broker) {
		this.broker = broker;
	}
	public Manager(String code, String name, String Address, String email, String broker) {
		super(code, name, Address, email);
		this.broker = broker;
	}
	
}
