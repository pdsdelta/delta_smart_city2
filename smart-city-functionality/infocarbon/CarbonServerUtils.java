package infocarbon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import connectionPool.DataSource;
import user.Users;

public class CarbonServerUtils {
	
	 private Connection connect;
	 private Statement stm;
	 private ResultSet rs;
	 private PreparedStatement pstmt;
	 private DataSource connection;
		
	 
	public String jsonToSql(String jsonClient) {
		//json Client to Object json
		// Selon l'opération Exexuter la fonction adapté
		return "" ;
	}
	
	public float getGlobalCarbonne(String date) throws JSONException, JsonProcessingException {
    	
		return getPublicCarbonne(date) + getPrivateCarbonne(date); 
    	
    }
	
	public float getPublicCarbonne(String date) throws JSONException, JsonProcessingException {
		//appelle la fonction getnbTRAM
		//APPELLE la fonction gettramLineLength
		
		return 12;
    	
    }
	
	public float getPrivateCarbonne(String date) throws JSONException, JsonProcessingException {
		//appelle la fonction getnbCars
		return 13;
    	
    }
	
	public String getNbCars(String date) throws JSONException, JsonProcessingException {
    	String resultat= "{Table: publictransportstat, Action : GET_NB_CARS ,  Data: ";
    	
		return resultat;
    	
    }
	
	public String getNbTram(String date) throws JSONException, JsonProcessingException {
    	String resultat= "{Table: publictransportstat, Action : GET_NB_TRAM ,  Data: ";
    	
		return resultat;
    	
    }
	
	public String getTramLineLength(String query) throws JSONException, JsonProcessingException {
    	String resultat= "{Table: publictransportstat, Action : SELECT_ONE ,  Data: ";
    	
		return resultat;
    	
    }
}
