package unl.cse.financial;

import java.util.ArrayList;
import java.util.List;

public class Portfolio<T extends Assets>{
	private String portfolioCode;
	private String ownerCode;
	private String managerCode;
	private String beneficiaryCode;
	private final List<T> assetList;
	private Person owner;
	private Manager manager;
	private Customer managerPerson;
	private Person beneficiary = null;
	
	//Getters and setters
	public String getOwnerName(){
		return owner.getLastName() + ", " + owner.getFirstName();
	}
	public Manager getManager(){
		return manager;
	}
	public String getManagerName(){
		if(manager != null){
			return manager.getName1();
		}
		else{
			return owner.getName1();
		}
		/*if(owner.getCode().equals(manager.getCode())){
			return owner.getName();
		}
		else{
			return ((Person) manager).getLastName() + ", " + ((Person) manager).getFirstName();
		}*/
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
	public void addManagerPerson(Customer t){
		managerPerson = t;
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

	
	
}
