package capteur_air;

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

public class TestDistrict {

   //public void insertHistoricalSensorPolluant (JSONObject JsonRecu, Connection c) throws ParseException, UnsupportedEncodingException, SQLException {
   public static void main(String args[]) throws ParseException, UnsupportedEncodingException, JSONException {
      //JSONParser parser = new JSONParser();
        // Object obj = parser.parse(new FileReader("C:/Users/julie/Documents/TestDistrict.json"));
         //JSONObject jsonObject = (JSONObject) parser.parse("{\"id\" : \"1\", \"name\" : \"Quartier_0\", \"seuilquartieratmo\" : \"4\", \"etatalerte\" : \"1\"}");
         
    	  StringBuffer sb = new StringBuffer();

  		// lecture du JSON afin de mettre chaque ligne en chaîne de caractère
  		InputStream inputStream = FileReader.class.getClassLoader().getSystemResourceAsStream("TestDistrict.json"); 
  		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8")); 
  		try {
  			String temp; 
  			while ((temp = bufferedReader.readLine()) != null) 
  				sb.append(temp); 
  		} catch (IOException e) {
  			e.printStackTrace();
  		} finally {
  			try {
  				bufferedReader.close();
  			} catch (IOException e) {
  				e.printStackTrace(); 
  			}
  		}
  		String myjsonstring = sb.toString(); 
  		JSONParser parser = new JSONParser(); 
		JSONArray json = (JSONArray) parser.parse(myjsonstring); 
		
		; 
		for (int i = 0; i < ((Map) json).size(); i++) {
		 JSONObject jsonObject = (JSONObject) json.get(i);
         String id = String.valueOf(jsonObject.get("id"));
         String name = String.valueOf(jsonObject.get("name"));
         String seuilquartieratmo = String.valueOf(jsonObject.get("seuilquartieratmo"));
         String etatalerte = String.valueOf(jsonObject.get("etatalerte"));
         //String name = (String) jsonObject.get("name");
         //String seuilquartieratmo = (String) jsonObject.get("seuilquartieratmo");
         //String etatalerte = (String) jsonObject.get("etatalerte");

         System.out.println("ID : "+ id);
         System.out.println("NOM : "+ name);
         System.out.println("SEUIL : "+ seuilquartieratmo);
         System.out.println("ALERTE : "+ etatalerte);
         
      //} catch (FileNotFoundException e) {
      //   e.printStackTrace();
     // } catch (IOException e) {
      //   e.printStackTrace();
		}
   }
}
