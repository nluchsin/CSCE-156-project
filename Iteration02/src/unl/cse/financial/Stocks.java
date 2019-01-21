package unl.cse.financial;

public class Stocks extends Assets{
	private double aprQuarterlyDividend = 0;
	private double baseRateOfReturn = 0;
	private double betaOmegaMeasure;
	private String stockSymbol = null;
	private double totalValue = 0;
	public void setAnnualReturn(){
			double annualReturn = (this.baseRateOfReturn/100)*(this.totalValue*this.parseInt) + 4*(this.aprQuarterlyDividend*this.parseInt); 
			this.annualReturn = annualReturn;
	}
	
	public void setRateReturn(){
			double annualReturn = (this.baseRateOfReturn/100)*(this.totalValue*this.parseInt) + 4*(this.aprQuarterlyDividend*this.parseInt); 
			double RateReturn = annualReturn/(this.totalValue*this.parseInt);
			this.rateReturn = RateReturn*100;
	}
	
	public void setRiskMeasure(){
			this.riskReturn = this.getBetaOmegaMeasure();
	}
	public void setValue(){
			this.value = this.totalValue*this.parseInt;
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

	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}

	public Stocks(String code, String letter, String label, double parseInt, double aprQuarterlyDividend,
			double baseRateOfReturn, double betaOmegaMeasure, String stockSymbol, double totalValue) {
		super(code, letter, label, parseInt);
		this.aprQuarterlyDividend = aprQuarterlyDividend;
		this.baseRateOfReturn = baseRateOfReturn;
		this.betaOmegaMeasure = betaOmegaMeasure;
		this.stockSymbol = stockSymbol;
		this.totalValue = totalValue;
		this.setAnnualReturn();
		this.setRateReturn();
		this.setRiskMeasure();
		this.setValue();
	}
	
	
}
