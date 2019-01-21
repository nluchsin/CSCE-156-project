//package com.sdb; //DO NOT CHANGE THIS
package unl.cse.financial;

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
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city, String state, String zip, String country, String brokerType, String secBrokerId) {}
	
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
	public static void removeAllAssets() {}

	/**
	 * Removes the asset record from the database corresponding to the
	 * provided <code>assetCode</code>
	 * @param assetCode
	 */
	public static void removeAsset(String assetCode) {}
	
	/**
	 * Adds a deposit account asset record to the database with the
	 * provided data. 
	 * @param assetCode
	 * @param label
	 * @param apr
	 */
	public static void addDepositAccount(String assetCode, String label, double apr) {}
	
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
			Double baseRateOfReturn, Double baseOmega, Double totalValue) {}
	
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
			Double baseRateOfReturn, Double beta, String stockSymbol, Double sharePrice) {}

	/**
	 * Removes all portfolio records from the database
	 */
	public static void removeAllPortfolios() {}
	
	/**
	 * Removes the portfolio record from the database corresponding to the
	 * provided <code>portfolioCode</code>
	 * @param portfolioCode
	 */
	public static void removePortfolio(String portfolioCode) {}
	
	/**
	 * Adds a portfolio records to the database with the given data.  If the portfolio has no
	 * beneficiary, the <code>beneficiaryCode</code> will be <code>null</code>
	 * @param portfolioCode
	 * @param ownerCode
	 * @param managerCode
	 * @param beneficiaryCode
	 */
	public static void addPortfolio(String portfolioCode, String ownerCode, String managerCode, String beneficiaryCode) {}
	
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
	public static void addAsset(String portfolioCode, String assetCode, double value) {}
	
	
}

