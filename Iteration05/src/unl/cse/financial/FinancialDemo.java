/*Nathan Luchsinger, Tarren Wenig, Rustin Haase
 * 3/31/17
 * This Program parses sql tables into classes, calculates important values, and outputs to the command line.
 */
package unl.cse.financial;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.security.Permissions;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import com.sdb.PortfolioData;

import unl.cse.financial.DatabaseInfo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    	List<Customer> array = new ArrayList<Customer>();
    	List<Manager>arrayM = new ArrayList<Manager>();
    	//Parse the Persons sql info into the Customer and Manager classes.
    	DatabaseInfo data = new DatabaseInfo();
    	Connection conn = data.getCon();
    	try{
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT Persons.PriKey, Persons.Code, Persons.BrokerLetter, Persons.BrokerSEC, Persons.FirstName, Persons.LastName, Address.Street, Address.City, State.StateName, Country.CountryName, Address.Zip FROM Persons LEFT JOIN Address ON Persons.Address = Address.PriKey LEFT JOIN State ON Address.State = State.PriKey LEFT JOIN Country ON Address.Country = Country.PriKey");
    	while(rs.next()) {
    		String code = rs.getString("Code");
    		String[] broker = new String[2];
    		broker[0] = rs.getString("BrokerLetter");
    		broker[1] = rs.getString("BrokerSEC");
    		String[] name = new String[2];
    		name[0] = rs.getString("firstName");
      	    name[1] = rs.getString("lastName");
    		String address[] = new String[5];
    		address[0] = rs.getString("Street");
    		address[1] = rs.getString("City");
    		address[2] = rs.getString("StateName");
    		address[3] = rs.getString("CountryName");
    		address[4] = rs.getString("Zip");
    		Statement stmt1 = conn.createStatement();
    		ResultSet rs1 = stmt1.executeQuery("SELECT PersonCode, EmailAddress FROM PersonEmail");
    		ArrayList<String> email = new ArrayList<String>();
    		while(rs1.next()){
    			int int1 = rs.getInt("PriKey");
    			int int2 = rs1.getInt("PersonCode");
    		if(int1 == int2){
    			email.add(rs1.getString("EmailAddress"));
    		}
    		}
    		//Creates Person variable
    		    Customer person = new Customer(code, name, address, email);
    			person.setCode(code);
        		person.setName(name);
        		person.setAddress(address);
        		person.setEmail(email);
        		array.add(person);
    		if(broker[0] != null){
    			Manager person1 = new Manager(code, broker, name, address, email);
        		person1.setBroker(broker);
        		person1.setCode(code);
        		person1.setName(name);
        		person1.setAddress(address);
        		person1.setEmail(email);
        		arrayM.add(person1);
    		}
       	}
    	}
    	catch (SQLException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    	List<String[]> array1 = new ArrayList<String[]>();
    	try{
    		Connection con = data.getCon();
    	Statement stmt = con.createStatement();
    	ResultSet rs2 = stmt.executeQuery("SELECT * FROM Assets");
    	//Parses the assets tables into a string array
    	while(rs2.next()){   
    		if(rs2.getInt("SavingsId") != 0){
    			Statement stmt1 = con.createStatement();
    			ResultSet rs3 = stmt1.executeQuery("SELECT * FROM Savings");
    			while(rs3.next()){
    				int int1 = rs3.getInt("PriKey");
    				int int2 = rs2.getInt("SavingsId");
    				if(int1 == int2){
    					String[] tokens = new String[5];
    					tokens[0] = rs2.getString("PriKey");
    					tokens[1] = rs2.getString("Code1");
    					tokens[2] = rs2.getString("Letter");
    					tokens[3] = rs2.getString("Label");
    					tokens[4] = rs3.getString("APR");
    					array1.add(tokens);
    				}
    			}
    		}
    		else if(rs2.getInt("PrivateId") != 0){
    			Statement stmt1 = con.createStatement();
    			ResultSet rs3 = stmt1.executeQuery("SELECT * FROM Private");
    			while(rs3.next()){
    				if(rs3.getInt("PriKey") == rs2.getInt("PrivateId")){
    					String[] tokens = new String[8];
    					tokens[0] = rs2.getString("PriKey");
    					tokens[1] = rs2.getString("Code1");
    					tokens[2] = rs2.getString("Letter");
    					tokens[3] = rs2.getString("Label");
    					tokens[4] = rs3.getString("QuarterlyDividend");
    					tokens[5] = rs3.getString("BaseRateOfReturn");
    					tokens[6] = rs3.getString("OmegaMeasure");
    					tokens[7] = rs3.getString("SharePrice");
    					array1.add(tokens);
    				}
    			}
    		}
    		else{
    			Statement stmt1 = con.createStatement();
    			ResultSet rs3 = stmt1.executeQuery("SELECT * FROM Stocks");
    			while(rs3.next()){
    				if(rs3.getInt("PriKey") == rs2.getInt("StocksId")){
    					String[] tokens = new String[9];
    					tokens[0] = rs2.getString("PriKey");
    					tokens[1] = rs2.getString("Code1");
    					tokens[2] = rs2.getString("Letter");
    					tokens[3] = rs2.getString("Label");
    					tokens[4] = rs3.getString("QuarterlyDividend");
    					tokens[5] = rs3.getString("BaseRateOfReturn");
    					tokens[6] = rs3.getString("BetaMeasure");
    					tokens[7] = rs3.getString("StockSymbol");
    					tokens[8] = rs3.getString("SharePrice");
    					array1.add(tokens);
    				}
    			}
    		}
    	}
    	}
    	catch (SQLException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    	PortfolioList array2 = new PortfolioList();
    	PortfolioList array3 = new PortfolioList();
    	PortfolioList array4 = new PortfolioList();
    	try{
    		Connection con = data.getCon();
        	Statement stmt = con.createStatement();
        	ResultSet r = stmt.executeQuery("SELECT * FROM Portfolio");
    	//Generates portfolio and asset classes and subclasses
    	while(r.next()) {
    		int[] code = new int[3];
    		code[0] = r.getInt("OwnerCode");
    		code[1] = r.getInt("ManagerCode");
    		code[2] = r.getInt("BeneficiaryCode");
    		String portfolioCode = r.getString("PortfolioCode");
    		Statement stmt1 = con.createStatement();
    		ResultSet r1 = stmt1.executeQuery("SELECT * FROM Persons");    		
    		String beneficiaryCode = null;
    		String ownerCode = null;
    		String managerCode = new String();
    		while(r1.next()){
    		if(r1.getInt("PriKey") == code[0] && r1.getInt("PriKey") != 0){
    			ownerCode = r1.getString("Code");
    		}
    		if(r1.getInt("PriKey") == code[1] && r1.getInt("PriKey") != 0){
    			managerCode = r1.getString("Code");
    		}
    		if(r1.getInt("PriKey") == code[2] && r1.getInt("PriKey") != 0){
    			beneficiaryCode = r1.getString("Code");
    		}
    		}
    		if(beneficiaryCode == null){
    			beneficiaryCode = "none";
    		}
    		//Creates Portfolio
    		Portfolio<Assets> portfolio = new Portfolio<Assets>(portfolioCode, ownerCode, managerCode, beneficiaryCode);
    		portfolio.setPortfolioCode(portfolioCode);
    		portfolio.setOwnerCode(ownerCode);
    		portfolio.setManagerCode(managerCode);
    		portfolio.setBeneficiaryCode(beneficiaryCode);
    		//tests the parselist String against the asset String[] and creates various Asset subclasses when necessary.
    		Statement stmt2 = con.createStatement();
    		ResultSet r2 = stmt2.executeQuery("SELECT Portfolio.PortfolioCode, Assets.Code1, ParseInt FROM PortfolioAssets LEFT JOIN Portfolio ON PortfolioAssets.PortfolioCode = Portfolio.PriKey LEFT JOIN Assets ON PortfolioAssets.AssetCode = Assets.PriKey");
    		while(r2.next()){
    			if(r2.getString("PortfolioCode").equals(portfolioCode)){
    			for(int i = 0; i < array1.size(); i++){
    				String[] parseList = array1.get(i);
    				if(parseList[1].equals(r2.getString("Code1"))){
    					if(parseList.length == 5){
			    			String code2 = parseList[1];
			        		String letter = parseList[2];
			        		String label = parseList[3];
			        		double apr = Double.parseDouble(parseList[4]);
			        		Savings asset = new Savings(code2, letter, label, r2.getDouble("ParseInt"), apr);
			        		asset.setCode(code2);
			        		asset.setLetter(letter);
			        		asset.setLabel(label);
			        		asset.setApr(apr);
			        		portfolio.addAsset(asset);
			    		}
			    		//Creates P string
			    		else if(parseList.length == 8){
			    			String code2 = parseList[1];
			        		String letter = parseList[2];
			        		String label = parseList[3];
			        		double quarterlyDividend = Double.parseDouble(parseList[4]);
			        		double baseRate = Double.parseDouble(parseList[5]);
			        		double omega = Double.parseDouble(parseList[6]);
			        		double total = Double.parseDouble(parseList[7]);
			        		Private asset = new Private(code2, letter, label, r2.getDouble("ParseInt"), quarterlyDividend, baseRate, omega, total);
			        		asset.setCode(code2);
			        		asset.setLetter(letter);
			        		asset.setLabel(label);
			        		asset.setAprQuarterlyDividend(quarterlyDividend);
			        		asset.setBaseRateOfReturn(baseRate);
			        		asset.setBetaOmegaMeasure(omega);
			        		asset.setTotalValue(total);
			        		portfolio.addAsset(asset);
			    		}
			    		//Creates S string
			    		else if(parseList.length == 9){
			    			String code2 = parseList[1];
			        		String letter = parseList[2];
			        		String label = parseList[3];
			        		double quarterlyDividend = Double.parseDouble(parseList[4]);
			        		double baseRate = Double.parseDouble(parseList[5]);
			        		double omega = Double.parseDouble(parseList[6]);
			        		double total = Double.parseDouble(parseList[8]);
			        		String symbol = parseList[7];
			        		Stocks asset = new Stocks(code2, letter, label, r2.getDouble("ParseInt"), quarterlyDividend, baseRate, omega, symbol, total);
			        		asset.setCode(code2);
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
    		for(Person a : array){
    			String test = a.getCode();
				if(test.equals(ownerCode)){
					portfolio.addOwner(a);
				}
				if(test.equals(beneficiaryCode)){
					portfolio.addBeneficary(a);
				}
			}
    		
    		for(Manager a : arrayM){
    			String test = portfolio.getManagerCode();
    			//System.out.println(managerCode == null);
				if(a.getCode().equals(ownerCode)){
			//		System.out.println("owner");
					portfolio.addOwner(a);
				}
				if(a.getCode().equals(test)){
				//	System.out.println("manager");
					portfolio.addManager(a);
				}
				if(test.equals(beneficiaryCode)){
					portfolio.addBeneficary(a);
				}
			}
    		array2.insertPortfolioListNodeName(portfolio);
    		array3.insertPortfolioListNodeValue(portfolio);
    		array4.insertPortfolioListManager(portfolio);
       	}
    	}
    	catch (SQLException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    	double totalFees = array2.getTotalFees();
    	double totalCommissions = array2.getCommission();
    	double totalReturn = array2.getReturn();
    	double totalValue = array2.getTotal();
    	//Final Calculations for the output
    	//Format and output
    	//Following procedures are identical to this except for the ADT sorting which include Name, Value, and Manager letter + name.
    	System.out.printf("Portfolio Summary Report = By Name\n");
    	System.out.printf("==================================================================================================================================================================================\n");
    	System.out.printf("%-20s %-20s %-30s %20s %20s %20s %20s %20s\n", "Portfolio", "Owner", "Manager", "Fees", "Commission", "Weighted Risk", "Return", "Total");
    	for(int i = 1; i <= array2.getSize(); i++){  //Repeatedly prints for every item in the array.
    		Portfolio portfolio = array2.getPortfolio(i);
        	System.out.printf("%-20s %-20s %-30s $%19.2f $%19.2f %20.4f $%19.2f $%20.2f\n",  portfolio.getPortfolioCode(), portfolio.getOwnerName(), portfolio.getManagerName(), portfolio.getFee(), portfolio.getCommission(), portfolio.getRisk(), portfolio.getAnnualReturn(), portfolio.getTotalValue());
    	}
    	System.out.printf("%178s\n", "---------------------------------------------------------------------------------------------------------");
    	System.out.printf("%74s%19.2f $%19.2f %20s $%19.2f $%20.2f\n\n\n", "Totals $", totalFees, totalCommissions, " ", totalReturn, totalValue);
    
    totalFees = array3.getTotalFees();
	totalCommissions = array3.getCommission();
	totalReturn = array3.getReturn();
	totalValue = array3.getTotal();
	//Final Calculations for the output
	//Format and output
	System.out.printf("Portfolio Summary Report = By Value\n");
	System.out.printf("==================================================================================================================================================================================\n");
	System.out.printf("%-20s %-20s %-30s %20s %20s %20s %20s %20s\n", "Portfolio", "Owner", "Manager", "Fees", "Commission", "Weighted Risk", "Return", "Total");
	for(int i = 1; i <= array3.getSize(); i++){
		Portfolio portfolio = array3.getPortfolio(i);
    	System.out.printf("%-20s %-20s %-30s $%19.2f $%19.2f %20.4f $%19.2f $%20.2f\n", portfolio.getPortfolioCode(), portfolio.getOwnerName(), portfolio.getManagerName(), portfolio.getFee(), portfolio.getCommission(), portfolio.getRisk(), portfolio.getAnnualReturn(), portfolio.getTotalValue());
	}
	System.out.printf("%178s\n", "---------------------------------------------------------------------------------------------------------");
	System.out.printf("%74s%19.2f $%19.2f %20s $%19.2f $%20.2f\n\n\n", "Totals $", totalFees, totalCommissions, " ", totalReturn, totalValue);
	
	 totalFees = array4.getTotalFees();
		totalCommissions = array4.getCommission();
		totalReturn = array4.getReturn();
		totalValue = array4.getTotal();
		//Final Calculations for the output
		//Format and output
		System.out.printf("Portfolio Summary Report = By Manager\n");
		System.out.printf("==================================================================================================================================================================================\n");
		System.out.printf("%-20s %-20s %-30s %20s %20s %20s %20s %20s\n", "Portfolio", "Owner", "Manager", "Fees", "Commission", "Weighted Risk", "Return", "Total");
		for(int i = 1; i <= array4.getSize(); i++){
			Portfolio portfolio = array4.getPortfolio(i);
	    	System.out.printf("%s %-20s %-20s %-30s $%19.2f $%19.2f %20.4f $%19.2f $%20.2f\n", portfolio.getManager().getBrokerLetter(), portfolio.getPortfolioCode(), portfolio.getOwnerName(), portfolio.getManagerName(), portfolio.getFee(), portfolio.getCommission(), portfolio.getRisk(), portfolio.getAnnualReturn(), portfolio.getTotalValue());
		}
		System.out.printf("%178s\n", "---------------------------------------------------------------------------------------------------------");
		System.out.printf("%74s%19.2f $%19.2f %20s $%19.2f $%20.2f\n\n\n", "Totals $", totalFees, totalCommissions, " ", totalReturn, totalValue);
}
}