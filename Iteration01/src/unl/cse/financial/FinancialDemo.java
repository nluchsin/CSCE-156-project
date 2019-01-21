/*Nathan Luchsinger, Tarren Wenig, Rustin Haase
 * 2/10/17
 * This Program parses two .dat file into a Person and an Assets class and outputs them into two JSON files.
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

public class FinancialDemo {

    /**
     * Main method
     * @param args the command line arguments
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
    	Scanner s = null;
    	Scanner n = null;
    	GsonBuilder w = null;
    	PrintWriter m = null;
    	GsonBuilder x = null;
    	PrintWriter y = null;
    	PrintWriter z = null;
    	//Creates necessary Scanner, GsonBuilders, and PrintWriters
    	try {
    		
			s = new Scanner(new File("data/Persons.dat"));
			n = new Scanner(new File("data/Assets.dat"));
			m = new PrintWriter(new File("data/Persons.json"));
			y = new PrintWriter(new File("data/Assets.json"));
			w = new GsonBuilder().setPrettyPrinting();
			x = new GsonBuilder().setPrettyPrinting();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
    	
    	s.nextLine();
    	List<JsonObject> array = new ArrayList<JsonObject>();
    	//Parse the Persons.dat file into the Person class and outputs it as a json.
    	while(s.hasNext()) {
    		String line = s.nextLine();
    		String tokens[] = line.split(";");
    		String code = tokens[0];
    		String broker = " ";
    		if(tokens[0].equals("") != true){
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
    		Person person = new Person(code, broker, name, address, email);
    		person.setCode(code);
    		person.setBroker(broker);
    		person.setName(name);
    		person.setAddress(address);
    		person.setEmail(email);
    		//Creates Json
    		JsonObject address1 = new JsonObject();
    		JsonObject person1 = new JsonObject();
    		address1.addProperty("street", person.getStreet());
    		address1.addProperty("city", person.getCity());
    		address1.addProperty("state", person.getState());
    		address1.addProperty("country", person.getCountry());
    		address1.addProperty("zip code", person.getZipCode());
    		person1.addProperty("Code", person.getCode());
    		if(tokens[1].length() == 2 || tokens[1].length() == 0){
    			
    		}
    		else{
    			person1.addProperty("SEC Identifier", person.getBrokerSEC());
    			person1.addProperty("Type", person.getBrokerLetter());
    		}
    		person1.addProperty("First Name", person.getFirstName());
    		person1.addProperty("Last Name", person.getLastName());
    		person1.add("Address", address1);
    		Gson gson1 = w.create();
    		JsonElement json1 = gson1.toJsonTree(person.getEmail());
    		person1.add("Email", json1);
    		array.add(person1);
       	}
    	Gson gson = w.create();
    	String json = gson.toJson(array);
    	m.println(json);
    	n.nextLine();
    	List<JsonObject> array1 = new ArrayList<JsonObject>();
    	//Repeats the above with the Assets.dat file. This time the JSON file is separated via length into three catagories.
    	while(n.hasNext()) {
    		String line = n.nextLine();
    		String tokens[] = line.split(";");
    		//Creates D string
    		if(tokens.length == 4){
    			String code = tokens[0];
        		String letter = tokens[1];
        		String label = tokens[2];
        		double apr = Double.parseDouble(tokens[3]);
        		double filler = 0;
        		String symbol = " ";
        		Assets asset = new Assets(code, letter, label, apr, filler, filler, symbol, filler);
        		asset.setCode(code);
        		asset.setLetter(letter);
        		asset.setLabel(label);
        		asset.setAprQuarterlyDividend(apr);
        		asset.setBaseRateOfReturn(filler);
        		asset.setBetaOmegaMeasure(filler);
        		asset.setStockSymbol(symbol);
        		asset.setTotalValue(filler);
        		JsonObject asset1 = new JsonObject();
        		asset1.addProperty("Code", asset.getCode());
        		asset1.addProperty("Letter", asset.getLetter());
        		asset1.addProperty("Label", asset.getLabel());
        		asset1.addProperty("APR", asset.getAprQuarterlyDividend());
        		array1.add(asset1);
    		}
    		//Creates P string
    		else if(tokens.length == 7){
    			String code = tokens[0];
        		String letter = tokens[1];
        		String label = tokens[2];
        		double apr = Double.parseDouble(tokens[3]);
        		double baseRate = Double.parseDouble(tokens[4]);
        		double omega = Double.parseDouble(tokens[5]);
        		double total = Double.parseDouble(tokens[6]);
        		String symbol = " ";
        		Assets asset = new Assets(code, letter, label, apr, baseRate, omega, symbol, total);
        		asset.setCode(code);
        		asset.setLetter(letter);
        		asset.setLabel(label);
        		asset.setAprQuarterlyDividend(apr);
        		asset.setBaseRateOfReturn(baseRate);
        		asset.setBetaOmegaMeasure(omega);
        		asset.setStockSymbol(symbol);
        		asset.setTotalValue(total);
        		JsonObject asset1 = new JsonObject();
        		asset1.addProperty("Code", asset.getCode());
        		asset1.addProperty("Letter", asset.getLetter());
        		asset1.addProperty("Label", asset.getLabel());
        		asset1.addProperty("APR", asset.getAprQuarterlyDividend());
        		asset1.addProperty("Base Rate of Return", asset.getBaseRateOfReturn());
        		asset1.addProperty("Omega Measure", asset.getBetaOmegaMeasure());
        		asset1.addProperty("Total", asset.getTotalValue());
        		array1.add(asset1);
    		}
    		//Creates S string
    		else if(tokens.length == 8){
    			String code = tokens[0];
        		String letter = tokens[1];
        		String label = tokens[2];
        		double apr = Double.parseDouble(tokens[3]);
        		double baseRate = Double.parseDouble(tokens[4]);
        		double omega = Double.parseDouble(tokens[5]);
        		double total = Double.parseDouble(tokens[7]);
        		String symbol = tokens[6];
        		Assets asset = new Assets(code, letter, label, apr, baseRate, omega, symbol, total);
        		asset.setCode(code);
        		asset.setLetter(letter);
        		asset.setLabel(label);
        		asset.setAprQuarterlyDividend(apr);
        		asset.setBaseRateOfReturn(baseRate);
        		asset.setBetaOmegaMeasure(omega);
        		asset.setStockSymbol(symbol);
        		asset.setTotalValue(total);
        		JsonObject asset1 = new JsonObject();
        		asset1.addProperty("Code", asset.getCode());
        		asset1.addProperty("Letter", asset.getLetter());
        		asset1.addProperty("Label", asset.getLabel());
        		asset1.addProperty("APR", asset.getAprQuarterlyDividend());
        		asset1.addProperty("Base Rate of Return", asset.getBaseRateOfReturn());
        		asset1.addProperty("Beta Measure", asset.getBetaOmegaMeasure());
        		asset1.addProperty("Stock Symbol", asset.getStockSymbol());
        		asset1.addProperty("Total", asset.getTotalValue());
        		array1.add(asset1);
        		
    		}
    	}
    	//Output Assets and close
    	Gson gson1 = x.create();
    	String json1 = gson1.toJson(array1);
    	y.println(json1);
    	s.close();
    	n.close();
    	m.close();
    	y.close();
    }
}