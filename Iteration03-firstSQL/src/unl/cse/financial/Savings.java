package unl.cse.financial;

public class Savings extends Assets{
	private double apr;
	//Sets general values
	public void setAnnualReturn(double RateReturn){
			double annualReturn = RateReturn*this.parseInt;
			this.annualReturn = annualReturn;
	}
	
	public void setRateReturn(){
			double RateReturn = Math.pow(Math.E, this.apr/100) - 1;
			this.rateReturn = RateReturn*100;
			setAnnualReturn(RateReturn);
	}
	
	public void setRiskMeasure(){
			this.riskReturn = 0;
	}
	public void setValue(){
			this.value = this.parseInt;
	}
	
	
	public double getApr() {
		return apr;
	}

	public void setApr(double apr) {
		this.apr = apr;
	}
	//Generates Class
	public Savings(String code, String letter, String label, double parseInt, double apr) {
		super(code, letter, label, parseInt);
		this.apr = apr;
		this.setRateReturn();
		this.setValue();
		this.setRiskMeasure();
	}
	
}
