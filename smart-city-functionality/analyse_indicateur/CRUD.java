package analyse_indicateur;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import station.station;

public class CRUD {

	/*
	  public String informationStation() throws JSONException, JsonProcessingException {
	    	String resultat= "{Table: station, Action : INFOSTATION ,  Data: ";
	    	List<station> res = new ArrayList<station>();
	    	String json = this.mapJson;	
			JSONObject obj = new JSONObject(json);
			JSONObject request = obj.getJSONObject("request");
			int idStation = request.getInt("idStation");
			String query= "SELECT idCapteur FROM capteur";
			try {
				pstmt = connect.prepareStatement(query);
				pstmt.setInt(1, idStation);
				rs = pstmt.executeQuery();
				while(rs.next()) {
				capteur utilStation = new station();
				utilStation.setIdCapteur(rs.getInt(1));
				res.add(utilStation);
				}
			} catch (SQLException ex) {
				System.out.println("Erreur infos station!");
			}
			System.out.println("je vais recuperer des infos station en bdd");
			ObjectMapper mapper = new ObjectMapper();
			resultat =  resultat + mapper.writeValueAsString(res) + "}";
			this.response = resultat;
			return resultat;
	    }
	*/
	
	
}
