//See FinancialDemo.java
package unl.cse.financial;

public class Assets {
	private String code;
	private String letter;
	private String label; 
	private double aprQuarterlyDividend;
	private double baseRateOfReturn;
	private double betaOmegaMeasure;
	private String stockSymbol;
	private double totalValue;
	
	//Creates Asset Class
	public Assets(String code, String letter, String label, double aprQuarterlyDividend,double baseRateOfReturn, 
			double betaOmegaMeasure, String stockSymbol, double totalValue) {
		
		super();
		this.code = code;
		this.letter = letter;
		this.label = label;
		this.aprQuarterlyDividend = aprQuarterlyDividend;
		this.baseRateOfReturn = baseRateOfReturn;
		this.betaOmegaMeasure = betaOmegaMeasure;
		this.stockSymbol = stockSymbol;
		this.totalValue = totalValue;
	}
	
	//Set and output code variable
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	//Set and output letter variable
	public String getLetter() {
		return letter;
	}
	
	public void setLetter(String letter) {
		this.letter = letter;
	}
	//Set and output label variable
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	//Set and output all number variables and Stock Symbol
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
	
	
		
}
