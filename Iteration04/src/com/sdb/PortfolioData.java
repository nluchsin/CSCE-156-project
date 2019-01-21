/*Nathan Luchsinger
 * 4/14/2017
 * This file contains code for interacting with a sql database.
 */

package com.sdb; //DO NOT CHANGE THIS

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.security.Permissions;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
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
/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */
public class PortfolioData {

	/**
	 * Method that removes every person record from the database
	 * @throws SQLException 
	 */
	public static void removeAllPersons() {
		DatabaseInfo data = new DatabaseInfo();
		try{
			PreparedStatement ps = null;
			Connection conn = data.getCon();
		ps = conn.prepareStatement("SET SQL_SAFE_UPDATES=0");
		ResultSet rs = ps.executeQuery();
		ps = conn.prepareStatement("DELETE FROM PersonEmail");
		int i = ps.executeUpdate();
		ps = conn.prepareStatement("DELETE FROM PortfolioAssets");
		i = ps.executeUpdate();
		ps = conn.prepareStatement("DELETE FROM Portfolio");
		i = ps.executeUpdate();
		ps = conn.prepareStatement("DELETE FROM Persons");
		i = ps.executeUpdate();
		ps = conn.prepareStatement("SET SQL_SAFE_UPDATES=1");
		rs = ps.executeQuery();
		}
		catch (SQLException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Removes the person record from the database corresponding to the
	 * provided <code>personCode</code>
	 * @param personCode
	 */
	public static void removePerson(String personCode) {
		DatabaseInfo data = new DatabaseInfo();
		try{
			PreparedStatement ps = null;
			Connection conn = data.getCon();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT PriKey, Code FROM Persons");
			int personKey = 0;
			ArrayList<Integer> array1 = new ArrayList<Integer>();
			while(rs.next()){
				String code = rs.getString("Code");
				if(code.equals(personCode)){
					personKey = rs.getInt("PriKey");
					Statement stmt1 = conn.createStatement();
					ResultSet rs1 = stmt1.executeQuery("SELECT PriKey, OwnerCode, ManagerCode, BeneficiaryCode FROM Portfolio");
					while(rs1.next()){
						int owner = rs1.getInt("OwnerCode");
						int manager = rs1.getInt("ManagerCode");
						int beneficiary = rs1.getInt("BeneficiaryCode");
						if(owner == personKey || manager == personKey || beneficiary == personKey){
							array1.add(rs1.getInt("PriKey"));
						}
					}
				}
			}
			String PortfolioAssets = "DELETE FROM PortfolioAssets WHERE PortfolioAssets.PortfolioCode = ?";
			String PersonEmail = "DELETE FROM PersonEmail WHERE PersonEmail.PersonCode = ?";
			String Portfolio = "DELETE FROM Portfolio WHERE Portfolio.PriKey = ?";
			String Person = "DELETE FROM Persons WHERE Persons.PriKey = ?";
			for(int i : array1){
				ps = conn.prepareStatement(PortfolioAssets);
				ps.setInt(1, i);
				ps.executeUpdate();
			}
			ps = conn.prepareStatement(PersonEmail);
			ps.setInt(1, personKey);
			ps.executeUpdate();
			for(int i : array1){
				ps = conn.prepareStatement(Portfolio);
				ps.setInt(1, i);
				ps.executeUpdate();
			}
			ps = conn.prepareStatement(Person);
			ps.setInt(1, personKey);
			ps.executeUpdate();
			conn.close();
		}
		catch (SQLException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Method to add a person record to the database with the provided data. The
	 * <code>brokerType</code> will either be "E" or "J" (Expert or Junior) or 
	 * <code>null</code> if the person is not a broker.
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 * @param brokerType
	 */
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city, String state, String zip, String country, String brokerType, String secBrokerId) {
	DatabaseInfo data = new DatabaseInfo();
	try{
		
		
		PreparedStatement ps = null;
		Connection conn = data.getCon();
	ps = conn.prepareStatement("select PriKey, StateName from State");
	ResultSet rs = ps.executeQuery();  //Gets all values from the table for later comparisons
	boolean shouldadd = true;
	int stateKey = 0;
	while(rs.next()){
		String temporary = rs.getString("StateName");
		boolean tester = Objects.equals(state, temporary);  //compares the given value for state with every value in the list, if its not there then sets a boolean value to false.
		if (tester == true) {
			shouldadd = false;
			stateKey = rs.getInt("PriKey");
		}
	}
	if (shouldadd == true){  //if boolean is not false, then the state needs to be added to the State table.
		ps = conn.prepareStatement("insert into State(StateName) values (?)", Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, state);
		ps.executeUpdate();
		ResultSet rs1 = ps.getGeneratedKeys();
		while(rs1.next()){
			stateKey = rs1.getInt(1);
		}
	}
	ps = conn.prepareStatement("select PriKey, CountryName from Country");
	ResultSet rs2 = ps.executeQuery();  //same exact idea as from state above, just for Country.
	boolean shouldadd2 = true;
	int countryName = 0;
	
	while(rs2.next()){
	String temporary = rs2.getString("CountryName");
	boolean tester = Objects.equals(country, temporary);
	if (tester == true) {
		shouldadd2 = false;
		countryName = rs2.getInt("PriKey");
		}
	}
	if (shouldadd2 == true){
	ps = conn.prepareStatement("insert into Country(CountryName) values (?)", Statement.RETURN_GENERATED_KEYS);
	ps.setString(1, country);
	ps.executeUpdate();
	ResultSet rs3 = ps.getGeneratedKeys();
	while(rs3.next()){
		countryName = rs3.getInt(1);
	}
	}
	
	
	int AddressKey = 0;  //this simply inserts the values stored into the Address table but captures its PriKey for later.
		ps = conn.prepareStatement("insert into Address(Street, City, State, Country, Zip) values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);									
		ps.setString(1, street);
		ps.setString(2, city);
		ps.setInt(3, stateKey);
		ps.setInt(4, countryName);
		ps.setString(5, zip);
		ps.executeUpdate();
		ResultSet rs4 = ps.getGeneratedKeys();
		while(rs4.next()){
			AddressKey = rs4.getInt(1);
		}
		
		//Inserts into Persons using the above PriKey and is the final step of our process.
		ps = conn.prepareStatement("insert into Persons(Code, BrokerLetter, BrokerSEC, FirstName, LastName, Address) values (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);									
		ps.setString(1, personCode);
		if(brokerType != null){
			ps.setString(2, brokerType);
			ps.setString(3, secBrokerId);
		}
		else{
			ps.setNull(2, java.sql.Types.VARCHAR);
			ps.setNull(3, java.sql.Types.VARCHAR);
		}
		ps.setString(4, firstName);
		ps.setString(5, lastName);
		ps.setInt(6, AddressKey);
		ps.executeUpdate();
		ResultSet rs5 = ps.getGeneratedKeys();
		while(rs5.next()){
			AddressKey = rs5.getInt(1);
		}

		conn.close();
	}
	catch (SQLException e) {
		System.out.println("InstantiationException: ");
		e.printStackTrace();
		throw new RuntimeException(e);
	}
	}
	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		DatabaseInfo data = new DatabaseInfo();
		try{
		Connection conn = data.getCon();
		Statement stmt = conn.createStatement();
		PreparedStatement ps = null;
		ResultSet rs = stmt.executeQuery("SELECT PriKey, Code FROM Persons");
		int personKey = 0;
		while(rs.next()){
			String code = rs.getString("Code");
			if(code.equals(personCode)){
				personKey = rs.getInt("PriKey");
			}
		}
		String add = "INSERT INTO PersonEmail(PersonCode, EmailAddress) values (?, ?)";
		ps = conn.prepareStatement(add);
		ps.setInt(1, personKey);
		ps.setString(2, email);
		ps.executeUpdate();
		conn.close();
		}
		catch (SQLException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Removes all asset records from the database
	 *
	 */
	public static void removeAllAssets() {
		DatabaseInfo data = new DatabaseInfo();
		try{
			PreparedStatement ps = null;
			Connection conn = data.getCon();
		ps = conn.prepareStatement("SET SQL_SAFE_UPDATES=0");
		ResultSet rs = ps.executeQuery();
		ps = conn.prepareStatement("DELETE FROM PortfolioAssets");
		ps.executeUpdate();
		ps = conn.prepareStatement("DELETE FROM Assets");
		ps.executeUpdate();
		ps = conn.prepareStatement("DELETE FROM Savings");
		ps.executeUpdate();
		ps = conn.prepareStatement("DELETE FROM Stocks");
		ps.executeUpdate();
		ps = conn.prepareStatement("DELETE FROM Private");
		ps.executeUpdate();
		ps = conn.prepareStatement("SET SQL_SAFE_UPDATES=1");
		rs = ps.executeQuery();
		conn.close();
		}
		catch (SQLException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Removes the asset record from the database corresponding to the
	 * provided <code>assetCode</code>
	 * @param assetCode
	 */
	public static void removeAsset(String assetCode) {
		DatabaseInfo data = new DatabaseInfo();
		try{
			PreparedStatement ps = null;
			Connection conn = data.getCon();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT PriKey, Code1, SavingsId, StocksId, PrivateId FROM Assets");
			int assetKey = 0;
			int assetKey1 = 0;
			String letter = null;
			ArrayList<Integer> array1 = new ArrayList<Integer>();
			while(rs.next()){
				String code = rs.getString("Code1");
				if(code.equals(assetCode)){
					assetKey = rs.getInt("PriKey");
					if(rs.getInt("SavingsId") != 0){
						assetKey1 = rs.getInt("SavingsId");
						letter = "D";
					}
					else if(rs.getInt("StocksId") != 0){
						assetKey1 = rs.getInt("StocksId");
						letter = "S";
					}
					else{
						assetKey1 = rs.getInt("PrivateId");
						letter = "P";
					}
					}
				}
			String PortfolioAssets = "DELETE FROM PortfolioAssets WHERE PortfolioAssets.AssetCode = ?";
			String Assets = "DELETE FROM Assets WHERE PriKey = ?";
			String Portfolio = "DELETE FROM Portfolio WHERE Portfolio.PriKey = ?";
			String Savings = "DELETE FROM Savings WHERE PriKey = ?";
			String Stocks = "DELETE FROM Stocks WHERE PriKey = ?";
			String Private = "DELETE FROM Private WHERE PriKey = ?";
			ps = conn.prepareStatement(PortfolioAssets);
			ps.setInt(1, assetKey);
			ps.executeUpdate();
			ps = conn.prepareStatement(Assets);
			ps.setInt(1, assetKey);
			ps.executeUpdate();
			if(letter.equals("D")){
				ps = conn.prepareStatement(Savings);
				ps.setInt(1, assetKey1);
				ps.executeUpdate();
			}
			else if(letter.equals("S")){
			ps = conn.prepareStatement(Stocks);
			ps.setInt(1, assetKey1);
			ps.executeUpdate();
			conn.close();
		}
			else{
				ps = conn.prepareStatement(Private);
				ps.setInt(1, assetKey1);
				ps.executeUpdate();
			}
		}
		catch (SQLException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Adds a deposit account asset record to the database with the
	 * provided data. 
	 * @param assetCode
	 * @param label
	 * @param apr
	 */
	public static void addDepositAccount(String assetCode, String label, double apr) {
		DatabaseInfo data = new DatabaseInfo();
		try{
			PreparedStatement ps = null;
			Connection conn = data.getCon();
			String addDeposit = "INSERT INTO Savings(APR) values(?)";
			ps = conn.prepareStatement(addDeposit, Statement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, apr);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			int key = 0;
			while(rs.next()){
				key = rs.getInt(1);
			}
			String addDeposit1 = "INSERT INTO Assets(Code1, Letter, Label, SavingsId) values(?, ?, ?, ?)";
			ps = conn.prepareStatement(addDeposit1);
			ps.setString(1, assetCode);
			ps.setString(2, "D");
			ps.setString(3, label);
			ps.setInt(4, key);
			ps.executeUpdate();
			conn.close();
		}
		catch (SQLException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Adds a private investment asset record to the database with the
	 * provided data.  The <code>baseRateOfReturn</code> is assumed to be on the
	 * scale [0, 1].
	 * @param assetCode
	 * @param label
	 * @param quarterlyDividend
	 * @param baseRateOfReturn
	 * @param baseOmega
	 * @param totalValue
	 */
	public static void addPrivateInvestment(String assetCode, String label, Double quarterlyDividend, 
			Double baseRateOfReturn, Double baseOmega, Double totalValue) {
		DatabaseInfo data = new DatabaseInfo();
		try{
			PreparedStatement ps = null;
			Connection conn = data.getCon();
			String addDeposit = "INSERT INTO Private(QuarterlyDividend, BaseRateOfReturn, OmegaMeasure, SharePrice) values(?, ?, ?, ?)";
			ps = conn.prepareStatement(addDeposit, Statement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, quarterlyDividend);
			ps.setDouble(2, baseRateOfReturn);
			ps.setDouble(3, baseOmega);
			ps.setDouble(4, totalValue);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			int key = 0;
			while(rs.next()){
				key = rs.getInt(1);
			}
			String addDeposit1 = "INSERT INTO Assets(Code1, Letter, Label, PrivateId) values(?, ?, ?, ?)";
			ps = conn.prepareStatement(addDeposit1);
			ps.setString(1, assetCode);
			ps.setString(2, "P");
			ps.setString(3, label);
			ps.setInt(4, key);
			ps.executeUpdate();
			conn.close();
		}
		catch (SQLException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Adds a stock asset record to the database with the
	 * provided data.  The <code>baseRateOfReturn</code> is assumed to be on the 
	 * scale [0, 1].
	 * @param assetCode
	 * @param label
	 * @param quarterlyDividend
	 * @param baseRateOfReturn
	 * @param beta
	 * @param stockSymbol
	 * @param sharePrice
	 */
	public static void addStock(String assetCode, String label, Double quarterlyDividend, 
			Double baseRateOfReturn, Double beta, String stockSymbol, Double sharePrice) {
		DatabaseInfo data = new DatabaseInfo();
		try{
			PreparedStatement ps = null;
			Connection conn = data.getCon();
			String addDeposit = "INSERT INTO Stocks(QuarterlyDividend, BaseRateOfReturn, BetaMeasure, StockSymbol, SharePrice) values(?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(addDeposit, Statement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, quarterlyDividend);
			ps.setDouble(2, baseRateOfReturn);
			ps.setDouble(3, beta);
			ps.setString(4, stockSymbol);
			ps.setDouble(5, sharePrice);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			int key = 0;
			while(rs.next()){
				key = rs.getInt(1);
			}
			String addDeposit1 = "INSERT INTO Assets(Code1, Letter, Label, StocksId) values(?, ?, ?, ?)";
			ps = conn.prepareStatement(addDeposit1);
			ps.setString(1, assetCode);
			ps.setString(2, "S");
			ps.setString(3, label);
			ps.setInt(4, key);
			ps.executeUpdate();
			conn.close();
		}
		catch (SQLException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * Removes all portfolio records from the database
	 */
	public static void removeAllPortfolios() {
		DatabaseInfo data = new DatabaseInfo();
		try{
			PreparedStatement ps = null;
			Connection conn = data.getCon();
		ps = conn.prepareStatement("SET SQL_SAFE_UPDATES=0");
		ResultSet rs = ps.executeQuery();
		ps = conn.prepareStatement("DELETE FROM PortfolioAssets");
		ps.executeUpdate();
		ps = conn.prepareStatement("DELETE FROM Portfolio");
		ps.executeUpdate();
		ps = conn.prepareStatement("SET SQL_SAFE_UPDATES=1");
		rs = ps.executeQuery();
		conn.close();
		}
		catch (SQLException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Removes the portfolio record from the database corresponding to the
	 * provided <code>portfolioCode</code>
	 * @param portfolioCode
	 */
public static void removePortfolio(String portfolioCode) {
		
		
		//Simply calls all the queries via jdbc in the required sequence based on the key.
		
		DatabaseInfo data = new DatabaseInfo();
		try{
			PreparedStatement ps = null;
			Connection conn = data.getCon();
			
			ps = conn.prepareStatement("SET SQL_SAFE_UPDATES=0");
			ResultSet rs = ps.executeQuery();
			ps = conn.prepareStatement("SELECT * FROM Portfolio");
			rs = ps.executeQuery();
			int PortfolioKey = 0;
			while(rs.next()){
				if(rs.getString("PortfolioCode").equals(portfolioCode)){
					PortfolioKey = rs.getInt("PriKey");
				}
			}
		ps = conn.prepareStatement("delete from PortfolioAssets where PortfolioCode = ?");
		ps.setInt(1, PortfolioKey);
		int i = ps.executeUpdate();
		
		ps = conn.prepareStatement("DELETE FROM Portfolio where PriKey = ?");
		ps.setInt(1, PortfolioKey);
		i = ps.executeUpdate();
		

		ps = conn.prepareStatement("SET SQL_SAFE_UPDATES=1");
		i = ps.executeUpdate();
		conn.close();
		}
		catch (SQLException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Adds a portfolio records to the database with the given data.  If the portfolio has no
	 * beneficiary, the <code>beneficiaryCode</code> will be <code>null</code>
	 * @param portfolioCode
	 * @param ownerCode
	 * @param managerCode
	 * @param beneficiaryCode
	 */
	public static void addPortfolio(String portfolioCode, String ownerCode, String managerCode, String beneficiaryCode) {
		DatabaseInfo data = new DatabaseInfo();
		try{
			PreparedStatement ps = null;
			Connection conn = data.getCon();
			Statement stmt = conn.createStatement();
			int test = 0;
			ResultSet rs = stmt.executeQuery("SELECT * FROM Persons");
			int owner = 0;
			int manager = 0;
			int beneficiary = 0;

			while(rs.next()){
				if(rs.getString("Code").equals(ownerCode)){
					owner = rs.getInt("PriKey");
				}
				if(rs.getString("Code").equals(managerCode)){
					manager = rs.getInt("PriKey");
				}
				if(rs.getString("Code").equals(beneficiaryCode)){
					beneficiary = rs.getInt("PriKey");
				}
			}
			rs = stmt.executeQuery("SELECT * FROM Portfolio");
			while(rs.next()){
				if(rs.getString("PortfolioCode").equals(portfolioCode)){
					test = 1;
				}
			}
			if(test == 0){
				ps = conn.prepareStatement("INSERT INTO Portfolio(PortfolioCode, OwnerCode, ManagerCode, BeneficiaryCode) values (?, ?, ?, ?)");
				ps.setString(1, portfolioCode);
				ps.setInt(2, owner);
				ps.setInt(3, manager);
				if(beneficiary == 0){
					ps.setNull(4, java.sql.Types.INTEGER);
				}else{
					ps.setInt(4, beneficiary);
				}
				ps.executeUpdate();
			}
			conn.close();
		}
			catch (SQLException e) {
				System.out.println("InstantiationException: ");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
	}
	
	
	
	/**
	 * Associates the asset record corresponding to <code>assetCode</code> with the 
	 * portfolio corresponding to the provided <code>portfolioCode</code>.  The third 
	 * parameter, <code>value</code> is interpreted as a <i>balance</i>, <i>number of shares</i>
	 * or <i>stake percentage</i> (on the scale [0, 1]) depending on the type of asset the <code>assetCode</code> is
	 * associated with.  
	 * @param portfolioCode
	 * @param assetCode
	 * @param value
	 */
	public static void addAsset(String portfolioCode, String assetCode, double value) {
		DatabaseInfo data = new DatabaseInfo();
		try{
			PreparedStatement ps = null;
			Connection conn = data.getCon();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Portfolio");
			int portfolio = 0;
			int asset = 0;
			while(rs.next()){
				if(rs.getString("PortfolioCode").equals(portfolioCode)){
					portfolio = rs.getInt("PriKey");
					break;
				}
			}
			ResultSet rs1 = stmt.executeQuery("SELECT * FROM Assets");
			while(rs1.next()){
				if(rs1.getString("Code1").equals(assetCode)){
					asset = rs1.getInt("PriKey");
					break;
				}
			}
			ps = conn.prepareStatement("INSERT INTO PortfolioAssets(portfolioCode, assetCode, ParseInt) values (?, ?, ?)");
			ps.setInt(1, portfolio);
			ps.setInt(2, asset);
			ps.setDouble(3, value);
			ps.executeUpdate();
			conn.close();
			}
			catch (SQLException e) {
				System.out.println("InstantiationException: ");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
	}
	
	
}

