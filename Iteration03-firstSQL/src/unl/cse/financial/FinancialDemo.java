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
    	try{
		Statement stmt = data.getCon().createStatement();
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
    		Statement stmt1 = data.getCon().createStatement();
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
    		if(broker[0] == null){
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
    	List<Portfolio<Assets>> array2 = new ArrayList<Portfolio<Assets>>();
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
    		String managerCode = null;
    		while(r1.next()){
    		if(r1.getInt("PriKey") == code[0] && r1.getInt("PriKey") != 0){
    			ownerCode = r1.getString("Code");
    		}
    		else if(r1.getInt("PriKey") == code[1] && r1.getInt("PriKey") != 0){
    			managerCode = r1.getString("Code");
    		}
    		else if(r1.getInt("PriKey") == code[2] && r1.getInt("PriKey") != 0){
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
			        		Savings asset = new Savings(code2, letter, label, r2.getInt("ParseInt"), apr);
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
			        		Private asset = new Private(code2, letter, label, r2.getInt("ParseInt"), quarterlyDividend, baseRate, omega, total);
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
			        		Stocks asset = new Stocks(code2, letter, label, r2.getInt("ParseInt"), quarterlyDividend, baseRate, omega, symbol, total);
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
    	}
    	catch (SQLException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
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
    	System.out.printf("==================================================================================================================================================================================\n");
    	System.out.printf("%-20s %-20s %-30s %20s %20s %20s %20s %20s\n", "Portfolio", "Owner", "Manager", "Fees", "Commission", "Weighted Risk", "Return", "Total");
    	for(Portfolio<Assets> portfolio : array2){
        	System.out.printf("%-20s %-20s %-30s $%19.2f $%19.2f %20.4f $%19.2f $%20.2f\n", portfolio.getPortfolioCode(), portfolio.getOwnerName(), portfolio.getManagerName(), portfolio.getFee(), portfolio.getCommission(), portfolio.getRisk(), portfolio.getAnnualReturn(), portfolio.getTotalValue());
    	}
    	System.out.printf("%178s\n", "---------------------------------------------------------------------------------------------------------");
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

    }
}