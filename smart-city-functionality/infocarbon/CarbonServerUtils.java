package infocarbon;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import connectionPool.DataSource;


public class CarbonServerUtils {
	
	 private Connection connect;
	 private Statement stm;
	 private ResultSet rs;
	 private PreparedStatement pstmt;
	 private DataSource connection;
		
	 
	
	 public CarbonServerUtils() {

		}
	 
	public CarbonServerUtils(Connection connect,Statement stm,ResultSet rs,PreparedStatement pstmt ) {
		this.connect = connect;
		this.stm = stm;
		this.rs=rs;
		this.pstmt = pstmt;
	}
	
	//Setters and Getters
	public Connection getConnect() {
		return connect;
	}

	public void setConnect(Connection connect) {
		this.connect = connect;
	}

	public Statement getStm() {
		return stm;
	}

	public void setStm(Statement stm) {
		this.stm = stm;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	public PreparedStatement getPstmt() {
		return pstmt;
	}

	public void setPstmt(PreparedStatement pstmt) {
		this.pstmt = pstmt;
	}

	public DataSource getConnection() {
		return connection;
	}

	public void setConnection(DataSource connection) {
		this.connection = connection;
	}

	//Functions
	public String getGlobalCarbonne(String date) throws JSONException, JsonProcessingException {
		String resultat= "{Table: publictransportstat, Action : GET_GLOBAL_CARBON , Status: ";
    	String privateTr = getNbCars(date);
    	String publicTr = getNbTram();
    	JSONObject objpriv =new JSONObject(privateTr);
    	JSONObject objpub =new JSONObject(publicTr);
    	String statusPriv = objpriv.getString("Status");
    	String statusPub = objpub.getString("Status") ;
    	if(statusPriv.equals("success") && statusPub.equals("success") ) {
    		JSONArray arrpr = objpriv.getJSONArray("Data");
    		int nbCars = arrpr.getJSONObject(0).getInt("NbCars");
    		JSONArray arrpu = objpub.getJSONArray("Data");
        	int nbTram = arrpu.getJSONObject(0).getInt("NbTram");
        	int longueurreseau = arrpu.getJSONObject(1).getInt("NbTram");
        	resultat= resultat + statusPriv;
    	}
    	
		return privateTr;
    	
    }
	

	
	public String getNbCars(String date) throws JSONException, JsonProcessingException {
    	String resultat= "{Table: publictransportstat, Action : GET_NB_CARS , Status: ";
    	String query = "select nbcars from publictransportstat where idcity = 1 and dateof = '" + date + "' ;"  ;
    	String status ="failed";
    	try {
			stm = connect.createStatement();
			rs = stm.executeQuery(query);
			if(rs.next()) {
				int nbCars = rs.getInt("nbcars");
				status = "success";
				resultat =  resultat +", Data: [ NbCars :" + nbCars + "]}";
				
			}
    	}catch(SQLException ex) {
    		ex.printStackTrace();
    		resultat =  resultat + status + "}" ;
    	}
    	return resultat;
    	
    	
    }
	
	public String getNbTram() throws JSONException, JsonProcessingException {
    	String resultat= "{Table: publictransportstat, Action : GET_NB_TRAM ,  Status: ";
    	String query = "select numbertram ,longueurreseau from station where idstation = 1 ;"  ;
    	String status ="failed";
    	try {
			stm = connect.createStatement();
			rs = stm.executeQuery(query);
			if(rs.first()) {
				String numbertram = rs.getString("numbertram");
				int longueurreseau = rs.getInt("longueurreseau");
				status = "success";
				resultat =  resultat + status + ", Data: [ NbTram :" + numbertram + ",LengthLine :" + longueurreseau+"]}";
			}
			rs.close();
    	}catch(SQLException ex) {
    		Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
    		resultat =  resultat + status +"}" ;
  
    	}
    	
   
    	
		return resultat;
    	
    }
	
	
	public static void main(String[] args) throws UnknownHostException, IOException, JSONException {
    	
		CarbonServerUtils s = new CarbonServerUtils();
		System.out.println(s.getNbCars("2020-05-12"));
		
        
        
     
        
        
    }
}
