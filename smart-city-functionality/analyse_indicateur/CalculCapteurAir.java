package analyse_indicateur;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import functionality_server.functionalityServer;

public class CalculCapteurAir {
	static Indicator indice;
	static functionalityServer server;
	public static void main(String[] args) throws ClassNotFoundException, SQLException, JSONException, UnknownHostException, IOException {
		
		///////////////////////////////
		
		Indicator objet = null;
			objet = new Indicator(server);	
			String jsonReceived = objet.informationcapteurAir();
			try{objet.startConnection("172.31.249.22", 2400);}catch(Exception e) {}
			JSONObject obj = new JSONObject(jsonReceived);
			JSONObject objet1= obj.getJSONObject("request");
			int data= objet1.getInt("Data");
			System.out.println(data);
	}

}
