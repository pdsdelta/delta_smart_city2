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
import functionality_server.functionalityServer;
import infocarbon.InfoGlobalCarbon;
import motionSensor.MotionSensor;
import station.station;
import transportation.PublicTransport;
import Terminal.Terminal;


public class CRUD {


	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private String jsonClient ;

	private ObjectMapper jacksonObjectMapper;
	protected Connection connect;
	private Statement stm;
	private ResultSet rs;
	private PreparedStatement pstmt;
	protected DataSource connection;
	private String finalResponse ;
	private functionalityServer server; 
	private InfoGlobalCarbon InfoGlobalCarbon;

	public CRUD(functionalityServer server) throws ClassNotFoundException, SQLException {
		this.server=server;	
		this.connection= new DataSource();
		this.connect=connection.getConnection();
	}

	// 1 numbr de capteur air
	/**
	 * @return
	 * @throws JSONException
	 * @throws JsonProcessingException
	 * @throws SQLException 
	 */

	public int informationcapteurAir() throws JSONException, JsonProcessingException, SQLException{
		//String resultat= "{Table: district, Action : infoCapteurair   ,  Data: ";
		
		List<District> res = new ArrayList<District>();
		
		//String json = this.jsonClient;	
		//JSONObject obj = new JSONObject(json);
		//JSONObject request = obj.getJSONObject("request");
		Statement stm3= this.connect.createStatement();
		int count = 0;
				
		ResultSet rs3 = stm3.executeQuery("SELECT COUNT(*) AS total FROM district");
		while(rs3.next()){
			
		}
//		int id = request.getInt("id");
//		String query= "SELECT count(id) from district";
//
//		try {
//			pstmt = connect.prepareStatement(query);
//			pstmt.setInt(1, id);
//			rs = pstmt.executeQuery();
//			while(rs.next()) {
//				District utilStation = new District();
//				utilStation.setId(rs.getInt(1));
//				res.add(utilStation);
//			}
//		} catch (SQLException ex) {
//
//			System.out.println("");
//		}
		System.out.println("je vais recuperer le numbr totale de capetur d'air en bdd");
		//ObjectMapper mapper = new ObjectMapper();
		//resultat =  resultat + mapper.writeValueAsString(res) + "}";
		//this.finalResponse = resultat;
		return count;
	} 

	// 2 numbr totale de motion sensor
	//select count(id) from motionsensor; ==>12

	/**
	 * @return
	 * @throws JSONException
	 * @throws JsonProcessingException
	 * @throws SQLException 
	 */
	public int informotionsensor() throws JSONException, JsonProcessingException, SQLException {

		List<MotionSensor> res = new ArrayList<MotionSensor>();
		//String json = this.jsonClient;	
		//JSONObject obj = new JSONObject(json);
		//JSONObject request = obj.getJSONObject("request");


		Statement stmt3 = this.connect.createStatement();
		int count = 0;

		ResultSet rs3 = stmt3.executeQuery("SELECT COUNT(*) AS total FROM MotionSensor");
		while(rs3.next()){

			count = rs3.getInt("total");
		}
		//int id = request.getInt("id");
		//String query= "SELECT count(id) from motionsensor";
		//		try {
		//			pstmt = connect.prepareStatement(query);
		//			pstmt.setInt(1, id);
		//			rs = pstmt.executeQuery();
		//			while(rs.next()) {
		//			MotionSensor utilStation = new MotionSensor();
		//			utilStation.setId(rs.getInt(1));
		//			res.add(utilStation);
		//			}
		//		} catch (SQLException ex) {
		//			System.out.println("Erreur infos motionsensor!");
		//		}
		System.out.println("je vais recuperer le nbr totale de  motionsensor en bdd");
		//ObjectMapper mapper = new ObjectMapper();
		//resultat =  resultat + mapper.writeValueAsString(res) + "}";
	//	String resultat= "{Table: motionsensor, Action : infoMotionsensor,  Data: "+count+"}";
	//	this.finalResponse = resultat;
		return count;
	}

	//3 numbr totale des stations
	//select numberstation from station ==> 10
	
	/**
	 * @return
	 * @throws JSONException
	 * @throws JsonProcessingException
	 * @throws SQLException 
	 */
	public int infostation() throws JSONException, JsonProcessingException, SQLException {
		//String resultat= "{Table: station, Action : Infostation ,  Data: ";
		
		List<station> res = new ArrayList<station>();
		//String json = this.jsonClient;	
		//JSONObject obj = new JSONObject(json);
		//JSONObject request = obj.getJSONObject("request");
		
		Statement stmt3 = this.connect.createStatement();
		int count = 0;

		ResultSet rs3 = stmt3.executeQuery("SELECT numberstation FROM station");
		while(rs3.next()){

			count = rs3.getInt("numberstation");
		}
		
		
		
		//	int numberstation = request.getInt("id");
//		String query= "SELECT numberstation from station";
//		try {
//			pstmt = connect.prepareStatement(query);
//			//	pstmt.setInt(1, numberstation);
//			rs = pstmt.executeQuery();
//			while(rs.next()) {
//				station utilStation = new station();
//				utilStation.setNumberStation(rs.getInt(1));
//				res.add(utilStation); 
//			}
//		} catch (SQLException ex) {
//			System.out.println("Erreur infos numberstation!");
//		}
		System.out.println("je vais recuperer le nbr totale de  station en bdd");
		//ObjectMapper mapper = new ObjectMapper();
		//resultat =  resultat + mapper.writeValueAsString(res) + "}";
		//this.finalResponse = resultat;
		return count;
	}

	// 4 numbr totale de voiture present dans la ville avec la date
	//select (nbcars,dateof) from publictransportstat;==>(2000,2020-05-12)

	/**
	 * @return
	 * @throws JSONException
	 * @throws JsonProcessingException
	 * @throws SQLException 
	 */
	public int nbcars(String date ) throws JSONException, JsonProcessingException, SQLException {
		
		 //String resultat= "{Table: publictransportstat, Action : infoNbcarswithdate ,  Data: ";
		List<InfoGlobalCarbon> res = new ArrayList<InfoGlobalCarbon>();
//		String json = this.jsonClient;	
//		JSONObject obj = new JSONObject(json);
//		JSONObject request = obj.getJSONObject("request");
		Statement stmt3 = this.connect.createStatement();
		int count = 0;

		ResultSet rs3 = stmt3.executeQuery("SELECT nbcars FROM publictransportstat where dateof = "+date);
		while(rs3.next()){

			count = rs3.getInt("Total");
		}
		
		
//		int nbcars= request.getInt("nbcars");
//		int dateof= request.getInt("dateof");
//		String query= "select nbcars,dateof from publictransportstat";
//		try {
//			pstmt = connect.prepareStatement(query);
//			pstmt.setInt(1, nbcars);
//			rs = pstmt.executeQuery();
//			while(rs.next()) {
//				InfoGlobalCarbon utilStation = new InfoGlobalCarbon();
//				utilStation.setNbCars(rs.getInt(1));
//				res.add(utilStation);
//			}
//		} catch (SQLException ex) {
//			System.out.println("Erreur infos number cars!");
//		}
		System.out.println("je vais recuperer le nbr totale de voiture en bdd");
		//ObjectMapper mapper = new ObjectMapper();
		//resultat =  resultat + mapper.writeValueAsString(res) + "}";
		//this.finalResponse = resultat;
		return count;
	}
	// 5 numbr TRAM
		//select numbertram from station; ==>

		/**
		 * @return
		 * @throws JSONException
		 * @throws JsonProcessingException
		 * @throws SQLException 
		 */
		public int informationTram() throws JSONException, JsonProcessingException, SQLException {

			List<station> res = new ArrayList<station>();
			//String json = this.jsonClient;	
			//JSONObject obj = new JSONObject(json);
			//JSONObject request = obj.getJSONObject("request");


			Statement stmt3 = this.connect.createStatement();
			int count = 0;

			ResultSet rs3 = stmt3.executeQuery("select numbertram  from station ");
			while(rs3.next()){

				count = rs3.getInt("numbertram");
			}
			//int id = request.getInt("id");
			//String query= "SELECT count(id) from motionsensor";
			//		try {
			//			pstmt = connect.prepareStatement(query);
			//			pstmt.setInt(1, id);
			//			rs = pstmt.executeQuery();
			//			while(rs.next()) {
			//			MotionSensor utilStation = new MotionSensor();
			//			utilStation.setId(rs.getInt(1));
			//			res.add(utilStation);
			//			}
			//		} catch (SQLException ex) {
			//			System.out.println("Erreur infos motionsensor!");
			//		}
			System.out.println("je vais recuperer le nbr tram en bdd");
			//ObjectMapper mapper = new ObjectMapper();
			//resultat =  resultat + mapper.writeValueAsString(res) + "}";
		//	String resultat= "{Table: motionsensor, Action : infoMotionsensor,  Data: "+count+"}";
		//	this.finalResponse = resultat;
			return count;
		}
  
		   // 6 number borne
		/**
		 * @return
		 * @throws JSONException
		 * @throws JsonProcessingException
		 * @throws SQLException 
		 */
		public int informationBorne() throws JSONException, JsonProcessingException, SQLException {

			List<Terminal> res = new ArrayList<Terminal>();
			//String json = this.jsonClient;	
			//JSONObject obj = new JSONObject(json);
			//JSONObject request = obj.getJSONObject("request");


			Statement stmt3 = this.connect.createStatement();
			int count = 0;

			ResultSet rs3 = stmt3.executeQuery("select numbertram from terminal");
			while(rs3.next()){

				count = rs3.getInt("total");
			}
			//int id = request.getInt("id");
			//String query= "SELECT count(id) from motionsensor";
			//		try {
			//			pstmt = connect.prepareStatement(query);
			//			pstmt.setInt(1, id);
			//			rs = pstmt.executeQuery();
			//			while(rs.next()) {
			//			MotionSensor utilStation = new MotionSensor();
			//			utilStation.setId(rs.getInt(1));
			//			res.add(utilStation);
			//			}
			//		} catch (SQLException ex) {
			//			System.out.println("Erreur infos motionsensor!");
			//		}
			System.out.println("je vais recuperer le nbr tram en bdd");
			//ObjectMapper mapper = new ObjectMapper();
			//resultat =  resultat + mapper.writeValueAsString(res) + "}";
		//	String resultat= "{Table: motionsensor, Action : infoMotionsensor,  Data: "+count+"}";
		//	this.finalResponse = resultat;
			return count;
		}
}
