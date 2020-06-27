package analyse_indicateur.test;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
	  
	
	
	InputStream fis = new FileInputStream("smart-city-functionality/analyse_indicateur/test/TestStation.json");
	InputStreamReader ipsr = new InputStreamReader(fis);
	BufferedReader br = new BufferedReader(ipsr);
	String outjsonString = "";
	String s = "";

	while ((outjsonString = br.readLine()) != null) {
		s = s + outjsonString;
	}
       JSONObject objet1 = new JSONObject(s);

	 
		   String id = (String) objet1.get("idstation");
		   String numberstation = id;
		   System.out.println(id);
	  
	
		
		String jsonReceived = objet.infostation();
		objet.startConnection("172.31.249.22", 2400);
		JSONObject obj = new JSONObject(jsonReceived);
		JSONObject objet11= obj.getJSONObject("request");
		int	data = objet11.getInt("Data");
		
		System.out.println(data);
	

}
}
