package unl.cse.financial;

public class Private extends Assets{
	private double aprQuarterlyDividend = 0;
	private double baseRateOfReturn = 0;
	private double betaOmegaMeasure = 0;
	private double totalValue = 0;
	//Getters and Setters
	public void setAnnualReturn(){
			double percentage = parseInt/100;
			double annualReturn = (this.baseRateOfReturn/100)*(this.totalValue*percentage) + 4*(this.aprQuarterlyDividend*percentage);
			this.annualReturn = annualReturn;
	}
	
	public void setRateReturn(){
			double percentage = parseInt/100;
			double annualReturn = (this.baseRateOfReturn/100)*(this.totalValue*percentage) + 4*(this.aprQuarterlyDividend*percentage);
			double RateReturn = (annualReturn/(percentage*this.totalValue))*100;
			this.rateReturn = RateReturn;
	}
	
	public void setRiskMeasure(){
			double v = this.totalValue;
			double exp = (-100000/v);
			double o = Math.pow(Math.E, exp);
			double fin = o + this.betaOmegaMeasure;
			this.riskReturn = fin;
	}
	public void setValue(){
			this.value = (this.parseInt/100)*this.totalValue;
	}

	public double getAprQuarterlyDividend() {
		return aprQuarterlyDividend;
	}

	public void setAprQuarterlyDividend(double aprQuarterlyDividend) {
		this.aprQuarterlyDividend = aprQuarterlyDividend;
	}

	public double getBaseRateOfReturn() {
		return baseRateOfReturn;
	}

	public void setBaseRateOfReturn(double baseRateOfReturn) {
		this.baseRateOfReturn = baseRateOfReturn;
	}

	public double getBetaOmegaMeasure() {
		return betaOmegaMeasure;
	}

	public void setBetaOmegaMeasure(double betaOmegaMeasure) {
		this.betaOmegaMeasure = betaOmegaMeasure;
	}

	public double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}
	//Generate Class
	public Private(String code, String letter, String label, double parseInt, double aprQuarterlyDividend,
			double baseRateOfReturn, double betaOmegaMeasure, double totalValue) {
		super(code, letter, label, parseInt);
		this.aprQuarterlyDividend = aprQuarterlyDividend;
		this.baseRateOfReturn = baseRateOfReturn;
		this.betaOmegaMeasure = betaOmegaMeasure;
		this.totalValue = totalValue;
		this.setAnnualReturn();
		this.setValue();
		this.setRiskMeasure();
		this.setRateReturn();
	}

	
}
