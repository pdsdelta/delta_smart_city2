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
   public static void main(String args[]) throws ParseException, JSONException, UnsupportedEncodingException {
		//StringBuffer sb = new StringBuffer();
	   ClassLoader classLoader = new TestDistrict().getClass().getClassLoader();
	   String fileName = "capteur_air/TestDistrict.json";
	   File file = new File(classLoader.getResource(fileName).getFile());
	   
	   JSONParser parser = new JSONParser();
	   
	   try {
		   FileReader reader = new FileReader(file.getAbsolutePath());
		   Object obj = parser.parse(reader);
		   JSONObject jsonObj = (JSONObject) obj;
		   //JSONObject studentDetails = (JSONObject)jsonObj.get("studentDetails");
		   //JSONObject studentDetails = (JSONObject)jsonObj;
		   //System.out.println("studentDetails :" +studentDetails.toJSONString());
		   String id = (String) jsonObj.get("id");
		   String name = (String) jsonObj.get("name");
		   String seuilquartieratmo = (String) jsonObj.get("seuilquartieratmo");
		   String etatalerte = (String) jsonObj.get("etatalerte");
		   System.out.println("Id : "+ id);
		   System.out.println("name : "+ name);
		   System.out.println("seuilquartieratmo : "+ seuilquartieratmo);
		   System.out.println("etatalerte : "+ etatalerte);
		   
	   }catch (Exception e) {
		   e.printStackTrace();
	   }
   }
   
}
