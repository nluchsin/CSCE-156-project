/*Nathan Luchsinger, Tarren Wenig, Rustin Haase
 * 2/24/17
 * This Program parses three dat files into classes, calculates important values, and outputs to the command line.
 */
package unl.cse.financial;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.security.Permissions;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class FinancialDemo{

    /**
     * Main method
     * @param args the command line arguments
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
    	Scanner s = null;
    	Scanner n = null;
    	Scanner p = null;
    	GsonBuilder w = null;
    	PrintWriter m = null;
    	GsonBuilder x = null;
    	PrintWriter y = null;
    	PrintWriter z = null;
    	//Creates necessary Scanners and PrintWriters
    	try {
    		
			s = new Scanner(new File("data/Persons.dat"));
			n = new Scanner(new File("data/Assets.dat"));
			p = new Scanner(new File("data/Portfolios.dat"));
			m = new PrintWriter(new File("data/output.txt"));
			w = new GsonBuilder().setPrettyPrinting();
			x = new GsonBuilder().setPrettyPrinting();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
    	
    	s.nextLine();
    	List<Customer> array = new ArrayList<Customer>();
    	List<Manager>arrayM = new ArrayList<Manager>();
    	//Parse the Persons.dat file into the Customer and Manager classes.
    	while(s.hasNext()) {
    		String line = s.nextLine();
    		String tokens[] = line.split(";");
    		String code = tokens[0];
    		String broker = " ";
    		if(tokens[1].equals("") != true){
    			broker = tokens[1];
    		}
    		String name = tokens[2];
    		String address = tokens[3];
    		int t = line.charAt(line.length()-1);
    		String email = null;
    		if(t == 59){
    			email = " ";
    		}else{
    		email = tokens[4];
    		}
    		//Creates Person variable
    		if(broker.equals(" ")){
    		    Customer person = new Customer(code, name, address, email);
    			person.setCode(code);
        		person.setName(name);
        		person.setAddress(address);
        		person.setEmail(email);
        		array.add(person);
    		}
    		else{
    			Manager person = new Manager(code, broker, name, address, email);
        		person.setBroker(broker);
        		person.setCode(code);
        		person.setName(name);
        		person.setAddress(address);
        		person.setEmail(email);
        		arrayM.add(person);
    		}
    		

       	}
    	
    	n.nextLine();
    	List<String[]> array1 = new ArrayList<String[]>();
    	//Parses the assets file into a string array
    	while(n.hasNext()) {
    		String line = n.nextLine();
    		String tokens[] = line.split(";");
    		//Creates D string
    		array1.add(tokens);
    	}
    	p.nextLine();
    	List<Portfolio<Assets>> array2 = new ArrayList<Portfolio<Assets>>();
    	//Generates portfolio and asset classes and subclasses
    	while(p.hasNext()) {
    		String line = p.nextLine();
    		String tokens[] = line.split(";");
    		String portfolioCode = tokens[0];
    		String ownerCode = tokens[1];
    		String managerCode = tokens[2];
    		String beneficiaryCode = "none";
    		int t = line.charAt(line.length() - 1);
    		if(tokens.length > 3){
    			beneficiaryCode = tokens[3];
    		} 
    		else{
    			beneficiaryCode = "none";
    		}

    		if(beneficiaryCode.length() == 0){
    			beneficiaryCode = "none";
    		}
    		//Creates Portfolio
    		Portfolio<Assets> portfolio = new Portfolio<Assets>(portfolioCode, ownerCode, managerCode, beneficiaryCode);
    		portfolio.setPortfolioCode(portfolioCode);
    		portfolio.setOwnerCode(ownerCode);
    		portfolio.setManagerCode(managerCode);
    		portfolio.setBeneficiaryCode(beneficiaryCode);
    		//tests the parselist String against the asset String[] and creates various Asset subclasses when necessary.
    		if(t != 59){
    			String[] assetList = tokens[4].split(",");
    			for(int i = 0; i < assetList.length; i++){
    				String[] parseList = assetList[i].split(":");
    				for(String[] tokens1 : array1){
    					if(tokens1[0].equals(parseList[0])){
    						if(tokens1.length == 4){
    			    			String code = tokens1[0];
    			        		String letter = tokens1[1];
    			        		String label = tokens1[2];
    			        		double apr = Double.parseDouble(tokens1[3]);
    			        		Savings asset = new Savings(code, letter, label, Double.parseDouble(parseList[1]), apr);
    			        		asset.setCode(code);
    			        		asset.setLetter(letter);
    			        		asset.setLabel(label);
    			        		asset.setApr(apr);
    			        		portfolio.addAsset(asset);
    			    		}
    			    		//Creates P string
    			    		else if(tokens1.length == 7){
    			    			String code = tokens1[0];
    			        		String letter = tokens1[1];
    			        		String label = tokens1[2];
    			        		double quarterlyDividend = Double.parseDouble(tokens1[3]);
    			        		double baseRate = Double.parseDouble(tokens1[4]);
    			        		double omega = Double.parseDouble(tokens1[5]);
    			        		double total = Double.parseDouble(tokens1[6]);
    			        		Private asset = new Private(code, letter, label, Double.parseDouble(parseList[1]), quarterlyDividend, baseRate, omega, total);
    			        		asset.setCode(code);
    			        		asset.setLetter(letter);
    			        		asset.setLabel(label);
    			        		asset.setAprQuarterlyDividend(quarterlyDividend);
    			        		asset.setBaseRateOfReturn(baseRate);
    			        		asset.setBetaOmegaMeasure(omega);
    			        		asset.setTotalValue(total);
    			        		portfolio.addAsset(asset);
    			    		}
    			    		//Creates S string
    			    		else if(tokens1.length == 8){
    			    			String code = tokens1[0];
    			        		String letter = tokens1[1];
    			        		String label = tokens1[2];
    			        		double quarterlyDividend = Double.parseDouble(tokens1[3]);
    			        		double baseRate = Double.parseDouble(tokens1[4]);
    			        		double omega = Double.parseDouble(tokens1[5]);
    			        		double total = Double.parseDouble(tokens1[7]);
    			        		String symbol = tokens1[6];
    			        		Stocks asset = new Stocks(code, letter, label, Double.parseDouble(parseList[1]), quarterlyDividend, baseRate, omega, symbol, total);
    			        		asset.setCode(code);
    			        		asset.setLetter(letter);
    			        		asset.setLabel(label);
    			        		asset.setAprQuarterlyDividend(quarterlyDividend);
    			        		asset.setBaseRateOfReturn(baseRate);
    			        		asset.setBetaOmegaMeasure(omega);
    			        		asset.setStockSymbol(symbol);
    			        		asset.setTotalValue(total);
    			        		portfolio.addAsset(asset);
    			    		}
    					}
    				}
    			}
    		}
    		//Adds the Customer and Manager Classes to the array
    		for(Customer a : array){
    			String test = a.getCode();
				if(test.equals(ownerCode)){
					portfolio.addOwner(a);
				}
				if(test.equals(beneficiaryCode)){
					portfolio.addBeneficary(a);
				}
			}
    		
    		for(Manager a : arrayM){
    			String test = a.getCode();
				if(test.equals(ownerCode)){
					portfolio.addOwner(a);
				}
				if(test.equals(managerCode)){
					portfolio.addManager(a);
				}
				if(test.equals(beneficiaryCode)){
					portfolio.addBeneficary(a);
				}
			}
    		
    		array2.add(portfolio);
       	}
    	
    	double totalFees = 0;
    	double totalCommissions = 0;
    	double totalReturn = 0;
    	double totalValue = 0;
    	//Final Calculations for the output
    	for(Portfolio<Assets> portfolio : array2){
    		totalFees = totalFees + portfolio.getFee();
    		totalCommissions = totalCommissions + portfolio.getCommission();
    		totalReturn = totalReturn + portfolio.getAnnualReturn();
    		totalValue = totalValue + portfolio.getTotalValue();
    	}
    	//Format and output
    	System.out.printf("Portfolio Summary Report\n");
    	System.out.printf("========================================================================================================================================================================\n");
    	System.out.printf("%-20s %-20s %-30s %20s %20s %20s %20s %20s\n", "Portfolio", "Owner", "Manager", "Fees", "Commission", "Weighted Risk", "Return", "Total");
    	for(Portfolio<Assets> portfolio : array2){
        	System.out.printf("%-20s %-20s %-30s $%19.2f $%19.2f %20.4f $%19.2f $%20.2f\n", portfolio.getPortfolioCode(), portfolio.getOwnerName(), portfolio.getManagerName(), portfolio.getFee(), portfolio.getCommission(), portfolio.getRisk(), portfolio.getAnnualReturn(), portfolio.getTotalValue());
    	}
    	System.out.printf("%168s\n", "-----------------------------------------------------------------------------------------------");
    	System.out.printf("%74s%19.2f $%19.2f %20s $%19.2f $%20.2f\n\n\n", "Totals $", totalFees, totalCommissions, " ", totalReturn, totalValue);
    	//
    	System.out.printf("Portfolio Details\n");
    	System.out.printf("================================================================================================================\n");
    	for(Portfolio<Assets> portfolio : array2){
    		System.out.println("Portfolio " + portfolio.getPortfolioCode());
    		System.out.println("------------------------------------------");
    		System.out.printf("Owner:       %s\n", portfolio.getOwnerName());
    		System.out.printf("Manager:     %s\n", portfolio.getManagerName());
    		System.out.printf("Beneficiary: %s\n", portfolio.getBeneficiaryName());
    		System.out.printf("Assets\n");
    		System.out.printf("%-10s %-31s %23s %12s    %8s %14s\n", "Code", "Asset", "Return Rate", "Risk", "Annual Return", "Value");
    		System.out.printf("%s", portfolio.toString());
    		System.out.printf("                                                                   --------------------------------------------\n");
    		System.out.printf("%66s %12.4f %4s%12.2f $%13.2f\n", "Totals", portfolio.getRisk(),"$", portfolio.getAnnualReturn(), portfolio.getTotalValue());
    		System.out.println(" ");
    	}
    	s.close();
    	n.close();
    	p.close();
    	m.close();
    }
}