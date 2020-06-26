package analyse_indicateur.test;

import java.awt.HeadlessException;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import analyse_indicateur.test.TestStation;
import analyse_indicateur.Indicator;


import functionality_server.functionalityServer;



public class TestStation {
	static Indicator indice;
	static functionalityServer server;
	public static void main(String[] args) throws ClassNotFoundException, SQLException, JSONException, UnknownHostException, IOException, ParseException {
	Indicator objet = null;
	
	objet = new Indicator(server);
	  
	
	
	
	  JSONParser parser = new JSONParser();
	   
	  
		   FileReader reader = new FileReader("C:\\Users\\etudiant\\Desktop\\NomberStation.json");
		   
		   JSONObject jsonObj = (JSONObject) parser.parse(reader);

		   String id = (String) jsonObj.get("numberstation");
		   String numberstation = id;
		   System.out.println(id);
	  
	
		
		String jsonReceived = objet.infostation();
		objet.startConnection("172.31.249.22", 2400);
		JSONObject obj = new JSONObject(jsonReceived);
		JSONObject objet1= obj.getJSONObject("request");
		int	data = objet1.getInt("Data");
		
		
	

}
}
