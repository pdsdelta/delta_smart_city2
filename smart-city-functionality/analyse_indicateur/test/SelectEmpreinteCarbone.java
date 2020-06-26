package analyse_indicateur.test;

import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import analyse_indicateur.Indicator;
import functionality_server.functionalityServer;

public class SelectEmpreinteCarbone {
	static Indicator indice;
	static functionalityServer server;
	public static void main(String[] args) throws ClassNotFoundException, SQLException, JSONException, UnknownHostException, IOException, ParseException {
		Indicator objet = null;
		
		
			
			objet = new Indicator(server);
		  
		
			
			
			  
			   
			  
				   FileReader reader = new FileReader("C:\\Users\\etudiant\\Desktop\\EmpreinteCarbone.json");
				   
				   JSONParser jsonParser = new JSONParser();
			        Object obj = jsonParser.parse(reader);
			        JSONObject objet1 = (JSONObject) obj;

				   
				   
				   String id = (String) objet1.get("date");
				   String date = id;
				   System.out.println(id);
			  
			
							
			
			
			
			String jsonReceived = objet.emprientecarbone(date);
			objet.startConnection("172.31.249.22", 2400);
			System.out.println("hello "+jsonReceived);
			JSONObject obj1 = new JSONObject(jsonReceived );
			JSONObject objet11= obj1.getJSONObject("request");
			int data = objet11.getInt("Data");
			System.out.println(data);
			
			  
			   }
	
}
