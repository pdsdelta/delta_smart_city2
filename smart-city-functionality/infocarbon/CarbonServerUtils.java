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
		System.out.println("Récupérations des informations concernant les transports privées");
    	String privateTr = getNbCars(date);
    	String privateMo = getNbMotos(date);
    	System.out.println("----------------------------------------------------------------");
    	System.out.println("Récupérations des informations concernant les transports public");
    	String publicTr = getNbTram();
    	String publicBus = getNbBus(date);
    	JSONObject objpriv =new JSONObject(privateTr);
    	JSONObject objprivmo =new JSONObject(privateMo);
    	JSONObject objpub =new JSONObject(publicTr);
    	JSONObject objpubbus =new JSONObject(publicBus);
    	String statusPriv = objpriv.getString("Status");
    	String statusPrivMo = objprivmo.getString("Status");
    	String statusPub = objpub.getString("Status") ;
    	String statusPubPub = objpubbus.getString("Status") ;
    	if(statusPriv.equals("success") && statusPub.equals("success") && statusPrivMo.equals("success") && statusPubPub.equals("success")) {
    		System.out.println("----------------------------------------------------------------");
    		System.out.println("On a bien des informations sur les deux types de transports");
    		resultat= resultat + statusPriv;
    		JSONArray arrpr = objpriv.getJSONArray("Data");
    		int nbCars = arrpr.getJSONObject(0).getInt("NbCars");
    		JSONArray arrprmo = objpriv.getJSONArray("Data");
    		int nbMotos = arrprmo.getJSONObject(0).getInt("NbMotos");
    		JSONArray arrpu = objpub.getJSONArray("Data");
        	int nbTram = arrpu.getJSONObject(0).getInt("NbTram");
        	int longueurreseau = arrpu.getJSONObject(0).getInt("LengthLine");
        	JSONArray arrpubu = objpubbus.getJSONArray("Data");
        	int nbBus = arrpubu.getJSONObject(0).getInt("NbTram");
        	resultat= resultat + ", Data: [{ NbCars :" + nbCars +", NbMotos :" + nbMotos +", NbTram :" + nbTram + ",LengthLine :" + longueurreseau + ",NbBus :" + nbBus + "}]} " ;
        	
    	}else {
    		resultat = resultat + "failed , Data: [{ empty : true }]}";
    	}
    	
		return resultat;
    	
    }
	

	
	public String getNbCars(String date) throws JSONException, JsonProcessingException {
    	String resultat= "{Table: carstats, Action : GET_NB_CARS , Status: ";
    	String query = "select nbcars from carstats where idcity = 1 and dateof = '" + date + "' ;"  ;
    	String status ="failed";
    	System.out.println("Requette : "+query);
    	try {
			stm = connect.createStatement();
			rs = stm.executeQuery(query);
			if(rs.next()) {
				int nbCars = rs.getInt("nbcars");
				status = "success";
				resultat =  resultat+ status +", Data: [{ NbCars :" + nbCars + "}]}";
				
			}else {
				status ="empty";
				resultat = resultat + status + ", Data: [{ empty : true }]}" ;
			}
    	}catch(SQLException ex) {
    		ex.printStackTrace();
    		resultat =  resultat + status + ", Data: [{ empty : true }]}" ;
    	}
    	System.out.println("Résultat: " +resultat);
    	return resultat;
    	
    	
    }
	
	public String getNbMotos(String date) throws JSONException, JsonProcessingException {
    	String resultat= "{Table: motostats, Action : GET_NB_MOTOS , Status: ";
    	String query = "select nbmotos from motostats where idcity = 1 and dateof = '" + date + "' ;"  ;
    	String status ="failed";
    	System.out.println("Requette : "+query);
    	try {
			stm = connect.createStatement();
			rs = stm.executeQuery(query);
			if(rs.next()) {
				int nbMotos = rs.getInt("nbmotos");
				status = "success";
				resultat =  resultat+ status +", Data: [{ NbMotos :" + nbMotos + "}]}";
				
			}else {
				status ="empty";
				resultat = resultat + status + ", Data: [{ empty : true }]}" ;
			}
    	}catch(SQLException ex) {
    		ex.printStackTrace();
    		resultat =  resultat + status + ", Data: [{ empty : true }]}" ;
    	}
    	System.out.println("Résultat: " +resultat);
    	return resultat;
    	
    	
    }
	
	public String getNbBus(String date) throws JSONException, JsonProcessingException {
    	String resultat= "{Table: busstats, Action : GET_NB_MOTOS , Status: ";
    	String query = "select nbmotos from bustats where idcity = 1 and dateof = '" + date + "' ;"  ;
    	String status ="failed";
    	System.out.println("Requette : "+query);
    	try {
			stm = connect.createStatement();
			rs = stm.executeQuery(query);
			if(rs.next()) {
				int nbBus = rs.getInt("nbbus");
				status = "success";
				resultat =  resultat+ status +", Data: [{ NbBus :" + nbBus + "}]}";
				
			}else {
				status ="empty";
				resultat = resultat + status + ", Data: [{ empty : true }]}" ;
			}
    	}catch(SQLException ex) {
    		ex.printStackTrace();
    		resultat =  resultat + status + ", Data: [{ empty : true }]}" ;
    	}
    	System.out.println("Résultat: " +resultat);
    	return resultat;
    	
    	
    }
	
	public String getNbTram() throws JSONException, JsonProcessingException {
    	String resultat= "{Table: station, Action : GET_NB_TRAM ,  Status: ";
    	String query = "select numbertram ,longueurreseau from station where idstation = 1 ;"  ;
    	String status ="failed";
    	System.out.println("Requette : "+query);
    	try {
			stm = connect.createStatement();
			rs = stm.executeQuery(query);
			if(rs.next()) {
				String numbertram = rs.getString("numbertram");
				int longueurreseau = rs.getInt("longueurreseau");
				status = "success";
				resultat =  resultat + status + ", Data: [{ NbTram :" + numbertram + ",LengthLine :" + longueurreseau+"}]}";
			}else {
				status ="empty";
				resultat = resultat + status + ", Data: [{ empty : true }]}" ;
			}
			rs.close();
    	}catch(SQLException ex) {
    		Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
    		resultat =  resultat + status +", Data: [{ empty : true }]}" ;
  
    	}
    	
   
    	System.out.println("Résultat: " +resultat);
		return resultat;
    	
    }
	
	
	public static void main(String[] args) throws UnknownHostException, IOException, JSONException {
    	
		CarbonServerUtils s = new CarbonServerUtils();
		System.out.println(s.getNbCars("2020-05-12"));
		
        
        
     
        
        
    }
}
