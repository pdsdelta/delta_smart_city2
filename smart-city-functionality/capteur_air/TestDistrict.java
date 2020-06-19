package capteur_air;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TestDistrict {

   //public void insertHistoricalSensorPolluant (JSONObject JsonRecu, Connection c) throws ParseException, UnsupportedEncodingException, SQLException {
   public static void main(String args[]) {
      JSONParser parser = new JSONParser();
      
      try {
        // Object obj = parser.parse(new FileReader("C:/Users/julie/Documents/TestDistrict.json"));
         JSONObject jsonObject = (JSONObject) parser.parse("{\"id\" : \"1\", \"name\" : \"Quartier_0\", \"seuilquartieratmo\" : \"4\", \"etatalerte\" : \"1\"}");
         
         String id = (String) jsonObject.get("id");
         String name = (String) jsonObject.get("name");
         String seuilquartieratmo = (String) jsonObject.get("seuilquartieratmo");
         String etatalerte = (String) jsonObject.get("etatalerte");

         System.out.println("ID : "+ id);
         System.out.println("NOM : "+ name);
         System.out.println("SEUIL : "+ seuilquartieratmo);
         System.out.println("ALERTE : "+ etatalerte);
         
      //} catch (FileNotFoundException e) {
      //   e.printStackTrace();
     // } catch (IOException e) {
      //   e.printStackTrace();

      PreparedStatement stmt3 = c.prepareStatement("insert into district (id ,name ,seuilquartieratmo ,etatalerte) values (?,?,?,?);");
		// the request takes name and first name already retrieved 
		stmt3.setString(1, id);
		stmt3.setString(2, name);
		stmt3.setString(3, seuilquartieratmo);
		stmt3.setString(4, etatalerte);
		// query execution 
		
		System.out.println("recupération des données"); 
		
		JSONObject obj=new JSONObject(); 

		// if (insertion bien passé) => executer les lignes suivantes sinon dire erreur
		if(stmt3.executeUpdate()>=1) {
			obj.put("reponse",String.valueOf("insertion reussi"));
			obj.put("id",id);
			obj.put("name",name);
			obj.put("seuilquartieratmo",seuilquartieratmo);
			obj.put("etatalerte",etatalerte);
			
			System.out.println("Insertion des lignes en base faite"); 
		}
		else {
			obj.put("reponse",String.valueOf("erreur lors de l'insertion"));
		}
		System.out.println(obj);
		
		
      	} catch (ParseException e) {       
      		e.printStackTrace();
   		}
   }
}
