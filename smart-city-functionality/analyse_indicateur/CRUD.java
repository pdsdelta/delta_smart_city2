package analyse_indicateur;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
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
import district.District;
import motionSensor.MotionSensor;
import station.station;

public class CRUD {

	private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String jsonClient ;
    
    private Connection connect;
	private Statement stm;
	private ResultSet rs;
	private PreparedStatement pstmt;
	private DataSource connection;
	private String finalResponse ;
	
	 // 1 numbr de capteur air
	 
	  public String informationcapteurAir() throws JSONException, JsonProcessingException {
	    	String resultat= "{Table: district, Action : infoCapteurair   ,  Data: ";
	    	List<District> res = new ArrayList<District>();
	    	String json = this.jsonClient;	
			JSONObject obj = new JSONObject(json);
			JSONObject request = obj.getJSONObject("request");
			int id = request.getInt("id");
			String query= "SELECT count(id) from district";
			try {
				pstmt = connect.prepareStatement(query);
				pstmt.setInt(1, id);
				rs = pstmt.executeQuery();
				while(rs.next()) {
				District utilStation = new District();
				utilStation.setId(rs.getInt(1));
				res.add(utilStation);
				}
			} catch (SQLException ex) {
	
	 			System.out.println("");
			}
			System.out.println("je vais recuperer le numbr totale de capetur d'air en bdd");
			ObjectMapper mapper = new ObjectMapper();
			resultat =  resultat + mapper.writeValueAsString(res) + "}";
			this.finalResponse = resultat;
			return resultat;
	    } 
	    
	// 2 numbr totale de motion sensor
	 //select count(id) from motionsensor; ==>12
	 
	public String informotionsensor() throws JSONException, JsonProcessingException {
    	String resultat= "{Table: motionsensor, Action : infoMotionsensor,  Data: ";
    	List<MotionSensor> res = new ArrayList<MotionSensor>();
    	String json = this.jsonClient;	
		JSONObject obj = new JSONObject(json);
		JSONObject request = obj.getJSONObject("request");
		int id = request.getInt("id");
		String query= "SELECT count(id) from motionsensor";
		try {
			pstmt = connect.prepareStatement(query);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
			MotionSensor utilStation = new MotionSensor();
			utilStation.setId(rs.getInt(1));
			res.add(utilStation);
			}
		} catch (SQLException ex) {
			System.out.println("Erreur infos motionsensor!");
		}
		System.out.println("je vais recuperer le nbr totale de  motionsensor en bdd");
		ObjectMapper mapper = new ObjectMapper();
		resultat =  resultat + mapper.writeValueAsString(res) + "}";
		this.finalResponse = resultat;
		return resultat;
    }
        
	//3 numbr totale des stations
	 //select numberstation from station ==> 20
	 
	public String infostation() throws JSONException, JsonProcessingException {
    	String resultat= "{Table: station, Action : Infostation ,  Data: ";
    	List<Station> res = new ArrayList<Station>();
    	String json = this.jsonClient;	
		JSONObject obj = new JSONObject(json);
		JSONObject request = obj.getJSONObject("request");
		int id = request.getInt("id");
		String query= "SELECT numberstation from station";
		try {
			pstmt = connect.prepareStatement(query);
			pstmt.setInt(1, numberstation);
			rs = pstmt.executeQuery();
			while(rs.next()) {
			Station utilStation = new station();
			utilStation.setnumberstation(rs.getInt(1));
			res.add(utilStation); 
			}
		} catch (SQLException ex) {
			System.out.println("Erreur infos numberstation!");
		}
		System.out.println("je vais recuperer le nbr totale de  station en bdd");
		ObjectMapper mapper = new ObjectMapper();
		resultat =  resultat + mapper.writeValueAsString(res) + "}";
		this.finalResponse = resultat;
		return resultat;
    }
        
	// 4 numbr totale de voiture present dans la ville
	 //  nbcars from publictransportstat; ==>2000
	 
	public String Nbcars() throws JSONException, JsonProcessingException {
    	String resultat= "{Table: publictransportstat, Action : infoNbcars ,  Data: ";
    	List<Nbcars> res = new ArrayList<Nbcars>();
    	String json = this.jsonClient;	
		JSONObject obj = new JSONObject(json);
		JSONObject request = obj.getJSONObject("request");
		int nbcars= request.getInt("nbcars");
		String query= "select nbcars from publictransportstat";
		try {
			pstmt = connect.prepareStatement(query);
			pstmt.setInt(1, nbcars);
			rs = pstmt.executeQuery();
			while(rs.next()) {
		      Publictransportstat utilStation = new station();
			utilStation.setnbcars(rs.getInt(1));
			res.add(utilStation);
			}
		} catch (SQLException ex) {
			System.out.println("Erreur infos number cars!");
		}
		System.out.println("je vais recuperer le nbr totale de voiture en bdd");
		ObjectMapper mapper = new ObjectMapper();
		resultat =  resultat + mapper.writeValueAsString(res) + "}";
		this.finalResponse = resultat;
		return resultat;
    }
	
	
}
