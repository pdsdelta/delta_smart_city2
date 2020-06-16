package capteur_air;

import java.io.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TestDistrict {
   public static void main(String args[]) {
      JSONParser parser = new JSONParser();
      
      try {
        // Object obj = parser.parse(new FileReader("C:/Users/julie/Documents/TestDistrict.json"));
         JSONObject jsonObject = (JSONObject) parser.parse("{\"idquartier\" : \"1\", \"nom\" : \"Quartier_0\", \"seuilquartier\" : \"4\", \"Etatalerte\" : \"1\"}");
         
         String id = (String) jsonObject.get("idquartier");
         String nom = (String) jsonObject.get("nom");
         String seuil = (String) jsonObject.get("seuilquartier");
         String alerte = (String) jsonObject.get("Etatalerte");

         System.out.println("ID : "+ id);
         System.out.println("NOM : "+ nom);
         System.out.println("SEUIL : "+ seuil);
         System.out.println("ALERTE : "+ alerte);
         
      //} catch (FileNotFoundException e) {
      //   e.printStackTrace();
     // } catch (IOException e) {
      //   e.printStackTrace();
      } catch (ParseException e) {       
    	  e.printStackTrace();
      }
   }
}
