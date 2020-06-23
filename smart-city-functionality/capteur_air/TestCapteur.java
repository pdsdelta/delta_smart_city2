package capteur_air;

import java.io.File;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TestCapteur {
	 public static void main(String args[]) throws ParseException, JSONException, UnsupportedEncodingException {
			//StringBuffer sb = new StringBuffer();
		   ClassLoader classLoader = new TestDistrict().getClass().getClassLoader();
		   String fileName = "capteur_air/TestCapteur.json";
		   File file = new File(classLoader.getResource(fileName).getFile());
		   
		   JSONParser parser = new JSONParser();
		   
		   try {
			   FileReader reader = new FileReader(file.getAbsolutePath());
			   Object obj = parser.parse(reader);
			   JSONObject jsonObj = (JSONObject) obj;
			   //JSONObject studentDetails = (JSONObject)jsonObj.get("studentDetails");
			   //JSONObject studentDetails = (JSONObject)jsonObj;
			   //System.out.println("studentDetails :" +studentDetails.toJSONString());
			   String idcapteur = (String) jsonObj.get("idcapteur");
			   String datereleve = (String) jsonObj.get("datereleve");
			   String indiceatmo = (String) jsonObj.get("indiceatmo");
			   String namecapteur = (String) jsonObj.get("namecapteur");
			   String intervalle = (String) jsonObj.get("intervalle");
			   System.out.println("idcapteur : "+ idcapteur);
			   System.out.println("datereleve : "+ datereleve);
			   System.out.println("indiceatmo : "+ indiceatmo);
			   System.out.println("namecapteur : "+ namecapteur);
			   System.out.println("intervalle : "+ intervalle);
			   
			   //addquartierj(id, name, seuilquartieratmo, etatalerte);
			   Testj a = new Testj();
			   a.addcapteurj(idcapteur, datereleve, indiceatmo, namecapteur, intervalle);
		   }catch (Exception e) {
			   e.printStackTrace();
		   }
	   }
	   
	}
