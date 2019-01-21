package unl.cse.financial;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Portfolio<T extends Assets>{
	private String portfolioCode;
	private String ownerCode;
	private String managerCode;
	private String beneficiaryCode;
	private final List<T> assetList;
	private Person owner;
	private Manager manager;
	private Person beneficiary = null;
	
	//Getters and setters
	public String getOwnerName(){
		return owner.getLastName() + ", " + owner.getFirstName();
	}
	public Person getOwner(){
		return owner;
	}
	public Manager getManager(){
		return manager;
	}
	public String getManagerName(){
			return manager.getName1();
	}
	
	public String getBeneficiaryName(){
		if(beneficiaryCode.equals("none")){
			return "none";
		}
		else{
		return beneficiary.getLastName() + ", " + beneficiary.getFirstName();
		}
	}
	public void addAsset(T t) {
		this.assetList.add(t);
	}
	public List<T> getAssetList(){
		return assetList;
	}
	public void addOwner(Person t){
		owner = t;
	}
	
	public void addManager(Manager t){
		manager = t;
	}
	public void addBeneficary(Person t){
		beneficiary = t;
	}
	
	public String getPortfolioCode() {
		return portfolioCode;
	}

	public void setPortfolioCode(String portfolioCode) {
		this.portfolioCode = portfolioCode;
	}

	public String getOwnerCode() {
		return ownerCode;
	}

	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}

	public String getManagerCode() {
		return managerCode;
	}

	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}

	public String getBeneficiaryCode() {
		return beneficiaryCode;
	}

	public void setBeneficiaryCode(String beneficiaryCode) {
		this.beneficiaryCode = beneficiaryCode;
	}
	//Return important info
	public double getTotalValue(){
		double total = 0;
		for(T asset : assetList){
			total = total + asset.getValue();
		}
		return total;
	}
	
	public double getAnnualReturn(){
		double total = 0;
		for(T asset : assetList){
			total = total + asset.getAnnualReturn();
		}
		return total;
	}
	public double getRisk(){
		double sum = 0;
		for(T asset : assetList){
			sum = sum + asset.getRiskReturn()*(asset.getValue()/this.getTotalValue());
		}
		return sum;
	}
	public double getFee(){
		if(this.manager == null){
			double count = 0;
			for(Assets asset : assetList){
				count++;
			}
			return count*10;
		}
		else{
		if(this.manager.getBrokerLetter().equals("J")){
			double count = 0;
			for(Assets asset : assetList){
				count++;
			}
			return count*50;
		}
		else{
			double count = 0;
			for(Assets asset : assetList){
				count++;
			}
			return count*10;
		}
		}
	}
		public double getCommission(){
			if(this.manager == null){
				double rate = .05;
				double commission = rate*this.getAnnualReturn();
				return commission;
			}
			else{
				if(this.manager.getBrokerLetter().equals("J")){
				double rate = .02;
				double commission = rate*this.getAnnualReturn();
				return commission;
				}
				else{
				double rate = .05;
				double commission = rate*this.getAnnualReturn();
				return commission;
			}
			}
	}
		//Generates Class
	public Portfolio(String portfolioCode, String ownerCode, String managerCode, String beneficiaryCode) {
		super();
		this.portfolioCode = portfolioCode;
		this.ownerCode = ownerCode;
		this.managerCode = managerCode;
		this.beneficiaryCode = beneficiaryCode;
		this.assetList = new ArrayList<T>();
	}
	//to String
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
			for(Assets o : this.assetList) {
				sb.append(o.toString());
			}
		return sb.toString();
	}
	
	//Comparator for Names
	public int compareToName(Portfolio<Assets> l) {
		int i = 0;
		//compares Lastname first.
    	while(i+1 < l.getOwner().getLastName().length() && i+1 < this.getOwner().getLastName().length()){
    		String a1 = l.getOwner().getLastName().substring(i, i+1);
    		String b1 = this.getOwner().getLastName().substring(i, i+1);
    		if(a1.compareToIgnoreCase(b1) != 0){
    				return b1.compareToIgnoreCase(a1);
    		}
    		i++;
    	}
    	i = 0;
    	//compares firstName after LastName
    	while(i+1 < l.getOwner().getFirstName().length() && i+1 < this.getOwner().getFirstName().length()){
    		String a1 = l.getOwner().getFirstName().substring(i, i+1);
    		String b1 = this.getOwner().getFirstName().substring(i, i+1);
    		if(a1.compareToIgnoreCase(b1) != 0){
    				return b1.compareToIgnoreCase(a1);
    		}
    		i++;
    	} 
    	//Since only "equal" strings will appear, the longest takes precedence.
    	if(l.getOwner().getFirstName().length() < this.getOwner().getFirstName().length()){
    		return 1;
    	}else if (l.getOwner().getFirstName().length() > this.getOwner().getFirstName().length()){
    		return -1;
    	}
    	return 0;
    }
	
	
	public int compareToManagerLetter(Portfolio l) {
    	//Compares the manager letter first and then calls functions for the names.
		if (this.getManager().getBrokerLetter().equalsIgnoreCase(l.getManager().getBrokerLetter())) {
    		return -this.compareToManagerName(l);
    	} 
    	else{
    		return -this.getManager().getBrokerLetter().compareToIgnoreCase(l.getManager().getBrokerLetter());
    	}
}
	
	public int compareToManagerName(Portfolio l) {
		//function for comparing manager names.
		int i = 0;
    	while(i+1 < l.getManager().getLastName().length() && i+1 < this.getManager().getLastName().length()){
    		String a1 = l.getManager().getLastName().substring(i, i+1);
    		String b1 = this.getManager().getLastName().substring(i, i+1);
    		if(a1.equalsIgnoreCase(b1) == false){
    			return b1.compareToIgnoreCase(a1);    			
    		}
    		i++;
    	}
    	i = 0;
    	//grabs strings for comparison from each of the portfolios given.
    	while(i+1 < l.getManager().getFirstName().length() && i+1 < this.getManager().getFirstName().length()){
    		String a1 = l.getManager().getFirstName().substring(i, i+1);
    		String b1 = this.getManager().getFirstName().substring(i, i+1);
    		if(a1.equalsIgnoreCase(b1) == false){
    			return b1.compareToIgnoreCase(a1);
    			/*if(b1.compareToIgnoreCase(a1)<0){
    				return -1;
    			}
    			else{
    				return 1;
    			}*/
    		}
    		i++;
    	}
    	return 0;
    }
	
	
}
