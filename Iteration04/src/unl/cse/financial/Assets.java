package unl.cse.financial;

public class Assets{
	private String code = null;
	private String letter = null;
	private String label = null; 
	protected double parseInt;
	private double test = -100;
	protected double annualReturn;
	protected double rateReturn;
	protected double riskReturn;
	protected double value;
	//Getters and setters
	public void addParseDouble(Double parseInt){
		this.parseInt = parseInt;
		test = 100;
	}
	public double getTest(){
		return test;
	}
	public double getParseInt(){
		return this.parseInt;
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public double getAnnualReturn() {
		return annualReturn;
	}
	public double getRateReturn() {
		return rateReturn;
	}
	public double getRiskReturn() {
		return riskReturn;
	}
	public double getValue() {
		return value;
	}
	public void setParseInt(double parseInt) {
		this.parseInt = parseInt;
	}
	//Generate Class
	public Assets(String code, String letter, String label, double parseInt) {
		super();
		this.code = code;
		this.letter = letter;
		this.label = label;
		this.parseInt = parseInt;
	}
	//String Return
	@Override
	public String toString() {
		return String.format("%-10s %-36s    %14.2f%s %12.2f %4s%12.2f $%13.2f\n", this.getCode(), this.getLabel(), this.getRateReturn(), "%", this.getRiskReturn(),"$", this.getAnnualReturn(), this.getValue());	 
	}
	
	
}
