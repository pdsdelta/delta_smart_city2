package infocarbon;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import user.Users;

public class CarbonClientUtils {

	
	public static InfoCarbon readJsontoObject(String jsonResponse) throws JSONException {
		InfoCarbon res = null ;
    	//String res = "Aucune donnée";
    	if (jsonResponse=="bad request") {
    		//res = "Impossible de traiter votre demande\n";
    		//return res ;
    	}
    	JSONObject obj =new JSONObject(jsonResponse);
    	String action = obj.getString("Action");
    	JSONArray arr = obj.getJSONArray("Data");
    	String status = obj.getString("Status");
    	System.out.println("Le Client va maintenant traduire l'objet Json reçu");
    	if(status.equals("success")) {
    		switch(action) {
    	
	    		case "GET_NB_TRAM":
	    			int nbTram = arr.getJSONObject(0).getInt("NbTram");
	            	int longueurreseau = arr.getJSONObject(0).getInt("LengthLine");
	            	res = new InfoPublicCarbon(1,nbTram,longueurreseau);
	            	int nbBus = 0 ;
	            	
	    			break;
	    		case "GET_NB_CARS":
	    			int nbCars = arr.getJSONObject(0).getInt("NbCars");
	    			res = new InfoPrivateCarbon(1,nbCars);
	    			int nbMotos = 0;
					break;
	    		case "GET_GLOBAL_CARBON" :
	    			nbTram = arr.getJSONObject(0).getInt("NbTram");
	            	longueurreseau = arr.getJSONObject(0).getInt("LengthLine");
	            	nbBus = arr.getJSONObject(0).getInt("NbBus");
	            	nbCars = arr.getJSONObject(0).getInt("NbCars");
	            	nbMotos = arr.getJSONObject(0).getInt("NbMotos");
	            	res = new InfoGlobalCarbon(1,nbCars, nbTram, longueurreseau,nbBus,nbMotos);
	    			break;
	    			
	    		default:
	    			res = null;
    		}
    	}else {
    		return res;
    	}
    	System.out.println(res.toString());
		return res;
	}
}
