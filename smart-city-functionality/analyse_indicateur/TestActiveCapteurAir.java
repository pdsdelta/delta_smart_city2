package analyse_indicateur;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import connectionPool.DataSource;
import district.District;
import functionality_server.functionalityServer;


import motionSensor.MotionSensor;


import CapteurAir.CapteurAir;
import capteur_air.Testj;

public class TestActiveCapteurAir {
	
	
	   public static void main(String args[]) throws ParseException, JSONException, UnsupportedEncodingException {
			
		  
		   
		   JSONParser parser = new JSONParser();
		   
		   try {
			   FileReader reader = new FileReader("C:\\Users\\etudiant\\Desktop\\ActiveCapteurAir.json");
			   Object obj = parser.parse(reader);
			   JSONObject jsonObj = (JSONObject) obj;
			   
			   String id = (String) jsonObj.get("id");
			   String name = (String) jsonObj.get("name");
			   String seuilquartieratmo = (String) jsonObj.get("seuilquartieratmo");
			   String etatalerte = (String) jsonObj.get("etatalerte");
			   System.out.println("Id : "+ id);
			   System.out.println("name : "+ name);
			   System.out.println("seuilquartieratmo : "+ seuilquartieratmo);
			   System.out.println("etatalerte : "+ etatalerte);
			   
			
			   TestCapAir a = new TestCapAir();
			   a.addquartierj(id, name, seuilquartieratmo, etatalerte);
		   }catch (Exception e) {
			   e.printStackTrace();
		   }
	   }

}
