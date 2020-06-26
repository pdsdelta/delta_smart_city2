package analyse_indicateur.test;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileInputStream;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import analyse_indicateur.Indicator;

import analyse_indicateur.test.ActiveCapteurAir;

import functionality_server.functionalityServer;

public class ActiveCapteurAir {
	static Indicator indice;
	static functionalityServer server;
	public static void main(String[] args) throws ClassNotFoundException, SQLException, JSONException, UnknownHostException, IOException {
			
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
