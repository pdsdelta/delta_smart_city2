package analyse_indicateur.test;

import java.io.FileReader;
import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import analyse_indicateur.TestCapAir;
import analyse_indicateur.test.ActiveCapteurAir;
public class ActiveCapteurAir {
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
