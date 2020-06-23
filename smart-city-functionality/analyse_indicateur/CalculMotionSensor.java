package analyse_indicateur;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;

import functionality_server.functionalityServer;

public class CalculMotionSensor {
	static Indicator indice;
	static functionalityServer server;
	public static void main(String[] args) throws ClassNotFoundException, SQLException, JSONException, UnknownHostException, IOException {
		
		///////////////////////////////
		
		Indicator objet = null;
			objet = new Indicator(server);	
			String jsonReceived = objet.informotionsensor();
			objet.startConnection("172.31.249.22", 2400);
			JSONObject obj = new JSONObject(jsonReceived);
			JSONObject objet1= obj.getJSONObject("request");
			int data= objet1.getInt("Data");
			System.out.println(data);

}
	}
