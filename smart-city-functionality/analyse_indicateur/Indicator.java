package analyse_indicateur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
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

import borne.Terminal;
import connectionPool.DataSource;
import district.District;
import functionality_server.functionalityServer;
import infocarbon.InfoCarbon;
import infocarbon.InfoGlobalCarbon;
import motionSensor.MotionSensor;
import station.station;
import transportation.PublicTransport;
import CapteurAir.CapteurAir;



public class Indicator {


	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private String json;
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

	public Indicator(functionalityServer server) throws ClassNotFoundException, SQLException {
		this.server=server;	
		this.connection= new DataSource();
		this.connect=connection.getConnection();
	}

	public void startConnection(String ip, int port) throws UnknownHostException, IOException {
		clientSocket = new Socket(ip, port);
		out=new PrintWriter(this.clientSocket.getOutputStream(), true);
		in=new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
		out.println(json);

	}                                                   // 1 numbr de capteur air
	/**
	 * @return
	 * @throws JSONException
	 * @throws JsonProcessingException
	 * @throws SQLException 
	 */

	public String informationcapteurAir() throws JSONException, JsonProcessingException, SQLException{


		List<District> res = new ArrayList<District>();


		//JSONObject obj = new JSONObject(json);
		//JSONObject request = obj.getJSONObject("request");
		Statement stm3= this.connect.createStatement();
		int count = 0;

		ResultSet rs3 = stm3.executeQuery("SELECT COUNT(id) AS total FROM district");
		while(rs3.next()){
			count = rs3.getInt("total");

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
		String resultat= "{request:{ operation_type:infoCapteurair , Table: district, Action : infoCapteurair   ,  Data: "+count+"}}";
		//this.finalResponse = resultat;
		resultat = resultat ;
		this.json=resultat;
		System.out.println(resultat);
		return resultat ;
	} 

	// 2 numbr totale de motion sensor
	//select count(id) from motionsensor; ==>12

	/**
	 * @return
	 * @throws JSONException
	 * @throws JsonProcessingException
	 * @throws SQLException 
	 */
	public String informotionsensor() throws JSONException, JsonProcessingException, SQLException {

		List<MotionSensor> res = new ArrayList<MotionSensor>();
		//String json = this.jsonClient;	
		//JSONObject obj = new JSONObject(json);
		//JSONObject request = obj.getJSONObject("request");


		Statement stmt3 = this.connect.createStatement();
		int count = 0;

		ResultSet rs3 = stmt3.executeQuery("SELECT COUNT(*) AS total FROM motionsensor");
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
		String resultat= "{request:{ operation_type:infoMotionsensor, Table: motionsensor, Action : infoMotionsensor,  Data: "+count+"}}";
		//	this.finalResponse = resultat;
		resultat = resultat;
		this.json=resultat;
		System.out.println(resultat);
		return resultat ;

	}

	//3 numbr totale des stations
	//select numberstation from station ==> 10

	/**
	 * @return
	 * @throws JSONException
	 * @throws JsonProcessingException
	 * @throws SQLException 
	 */
	public String infostation() throws JSONException, JsonProcessingException, SQLException {


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
		String resultat= "{  request:{ operation_type:Infostation, Table: station, Action : Infostation ,  Data: "+count+"}}";
		//this.finalResponse = resultat;
		resultat = resultat ;
		this.json=resultat;
		System.out.println(resultat);
		return resultat ;
	}

	// 4 numbr totale de voiture present dans la ville avec la date
	//select (nbcars,dateof) from publictransportstat;==>(2000,2020-05-12)

	/**
	 * @return
	 * @throws JSONException
	 * @throws JsonProcessingException
	 * @throws SQLException 
	 */
	public String nbcars(String date) throws JSONException, JsonProcessingException, SQLException {
		List<InfoGlobalCarbon> res = new ArrayList<InfoGlobalCarbon>();
		//		String json = this.jsonClient;	
		//		JSONObject obj = new JSONObject(json);
		//		JSONObject request = obj.getJSONObject("request");
		Statement stmt3 = this.connect.createStatement();
		int count = 0;
		ResultSet rs3 = stmt3.executeQuery("SELECT nbcars FROM carstats where dateof = '"+date+ "\'");
		while(rs3.next()){
			count = rs3.getInt("nbcars");
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
		String resultat= "{request:{ operation_type:nbcars , Table: InfoGlobalCarbon, Action : nbcars,  Data: " +count+"}}";
		//this.finalResponse = resultat;
		resultat = resultat;
		this.json=resultat;
		System.out.println(resultat);
		
		return resultat;
	}
	// 5 numbr TRAM
	//select numbertram from station; ==>

	/**
	 * @return
	 * @throws JSONException
	 * @throws JsonProcessingException
	 * @throws SQLException 
	 */
	public String informationTram() throws JSONException, JsonProcessingException, SQLException {

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
		String resultat= "{request:{ operation_type: informationTram ,Table: station, Action : informationTram,  Data: "+count+"}}";
		//	this.finalResponse = resultat;
		resultat = resultat ;
		System.out.println(resultat);
		this.json=resultat;
		
		return resultat;
	}

	// 6 number borne
	/**
	 * @return
	 * @throws JSONException
	 * @throws JsonProcessingException
	 * @throws SQLException 
	 */
	public String informationBorne() throws JSONException, JsonProcessingException, SQLException {

		List<Terminal> res = new ArrayList<Terminal>();
		//String json = this.jsonClient;	
		//JSONObject obj = new JSONObject(json);
		//JSONObject request = obj.getJSONObject("request");

		Statement stmt3 = this.connect.createStatement();
		int count = 0;

		ResultSet rs3 = stmt3.executeQuery("select count(*) as total from terminal");//select id from terminal where status=1;
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
		System.out.println("je vais recuperer le nbr borneen bdd");
		//ObjectMapper mapper = new ObjectMapper();
		//resultat =  resultat + mapper.writeValueAsString(res) + "}";
		String resultat= "{   request:{ operation_type: informationBorne , Table: terminal, Action : informationBorne,  Data: "+count+"}}";
		//	this.finalResponse = resultat;
		resultat = resultat  ;
		System.out.println(resultat);
		this.json=resultat;
		return resultat;

	}

	// 7 lempreinte de carbone
	/**
	 * @return
	 * @throws JSONException
	 * @throws JsonProcessingException
	 * @throws SQLException 
	 */
	public String emprientecarbone(String date) throws JSONException, JsonProcessingException, SQLException {

		List<InfoCarbon> res = new ArrayList<InfoCarbon>();
		//String json = this.jsonClient;	
		//JSONObject obj = new JSONObject(json);
		//JSONObject request = obj.getJSONObject("request");

		Statement stmt3 = this.connect.createStatement();
		int count = 0;

		ResultSet rs3 = stmt3.executeQuery("select empreintecarbone from carboninfo where date='"+date+"\'");
		while(rs3.next()){
			count = rs3.getInt("empreintecarbone");
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
		System.out.println("je vais recuperer lempreinte de carbon bdd");
		//ObjectMapper mapper = new ObjectMapper();
		//resultat =  resultat + mapper.writeValueAsString(res) + "}";
		String resultat= "{request:{ operation_type: emprientecarbone ,Table: carboninfo, Action :emprientecarbone ,  Data: "+count+"}}";
		//	this.finalResponse = resultat;
		resultat = resultat  ;
		System.out.println(resultat);
		this.json=resultat;
		return resultat;

	}



	/**
	 * @return
	 * @throws JSONException
	 * @throws JsonProcessingException
	 * @throws SQLException 
	 */
	public List<MotionSensor> getAllMotion() throws SQLException { 
		List<MotionSensor> res = new ArrayList<MotionSensor>(); 
		ResultSet result = this.connect.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM motionsensor where isActive = true");
		while (result.next()) {
			MotionSensor captor = new MotionSensor(); 

			captor.setLongitude(result.getInt("longitude"));
			captor.setLatitude(result.getInt("latitude"));
			captor.setActive(result.getBoolean("isActive"));
			captor.setNumero(result.getInt("numero"));
			res.add(captor);

		} 

		return res;
	}



	/**
	 * @return
	 * @throws JSONException
	 * @throws JsonProcessingException
	 * @throws SQLException 
	 */
	public List<Terminal> getAll() throws SQLException { 
		List<Terminal> res = new ArrayList<Terminal>(); 
		ResultSet result = this.connect.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Terminal where status = 1");
		while (result.next()) {
			Terminal borne = new Terminal(); 

			borne.setLongitude(result.getInt("longitude"));
			borne.setLatitude(result.getInt("latitude"));
			borne.setActive(result.getBoolean("isActive"));
			borne.setStatus(result.getInt("status"));
			borne.setNumero(result.getInt("numero"));
			res.add(borne);

		} 

		return res;
	}
	

	//8 taux de pollution dan la ville

	/**
	 * @return
	 * @throws JSONException
	 * @throws JsonProcessingException
	 * @throws SQLException 
	 */
	public String tauxATMO() throws JSONException, JsonProcessingException, SQLException {


		//List<station> res = new ArrayList<station>();
		//String json = this.jsonClient;	
		//JSONObject obj = new JSONObject(json);
		//JSONObject request = obj.getJSONObject("request");

		Statement stmt3 = this.connect.createStatement();
		int count = 0;

		ResultSet rs3 = stmt3.executeQuery("select cast((indicereleve/seuilquartieratmo) as FLOAT) as tauxAtmo " + 
				"from historique H, district D, capteurair CA" + 
				" where CA.idcapteur = H.idhistoric" + 
				" and H.idhistoric = D.id;" + "");
		while(rs3.next()){

			count = rs3.getInt("tauxAtmo");
		}
		//	int numberstation = request.getInt("id");
		//			String query= "SELECT numberstation from station";
		//			try {
		//				pstmt = connect.prepareStatement(query);
		//				//	pstmt.setInt(1, numberstation);
		//				rs = pstmt.executeQuery();
		//				while(rs.next()) {
		//					station utilStation = new station();
		//					utilStation.setNumberStation(rs.getInt(1));
		//					res.add(utilStation); 
		//				}
		//			} catch (SQLException ex) {
		//				System.out.println("Erreur infos numberstation!");
		//			}
		System.out.println("je vais recuperer le nbr totale de  station en bdd");
		//ObjectMapper mapper = new ObjectMapper();
		//resultat =  resultat + mapper.writeValueAsString(res) + "}";
		String resultat= "{request:{ operation_type: tauxATMO,Table: myCapteur, Action : tauxATMO ,  Data: "+count+"}}";
		//this.finalResponse = resultat;
		resultat = resultat  ;
		System.out.println(resultat);
		this.json=resultat;
		return resultat ;
	}

	// 9taux de depassement de deuild'ATMO

	/**
	 * @return
	 * @throws JSONException
	 * @throws JsonProcessingException
	 * @throws SQLException 
	 */
	public String tauxDepAtmo() throws JSONException, JsonProcessingException, SQLException {


		//List<station> res = new ArrayList<station>();
		//String json = this.jsonClient;	
		//JSONObject obj = new JSONObject(json);
		//JSONObject request = obj.getJSONObject("request");

		Statement stmt3 = this.connect.createStatement();
		int count = 0;

		ResultSet rs3 = stmt3.executeQuery("select cast( (((indicereleve/seuilquartieratmo)-1)*100)as FLOAT) as tauxDepAtmo" + 
				" from historique H, district D, capteurair CA " + 
				"where CA.idcapteur = H.idhistoric " + 
				"and H.idhistoric = D.id;" + "");
		while(rs3.next()){

			count = rs3.getInt("tauxDepAtmo");
		}
		//	int numberstation = request.getInt("id");
		//					String query= "SELECT numberstation from station";
		//					try {
		//						pstmt = connect.prepareStatement(query);
		//						//	pstmt.setInt(1, numberstation);
		//						rs = pstmt.executeQuery();
		//						while(rs.next()) {
		//							station utilStation = new station();
		//							utilStation.setNumberStation(rs.getInt(1));
		//							res.add(utilStation); 
		//						}
		//					} catch (SQLException ex) {
		//						System.out.println("Erreur infos numberstation!");
		//					}
		System.out.println("je vais recuperer le nbr totale de  station en bdd");
		//ObjectMapper mapper = new ObjectMapper();
		//resultat =  resultat + mapper.writeValueAsString(res) + "}";
		String resultat= "{request:{ operation_type:tauxDepAtmo ,Table: myCapteur, Action :tauxDepAtmo  ,  Data: "+count+"}}";
		//this.finalResponse = resultat;
		resultat = resultat  ;
		System.out.println(resultat);
		this.json=resultat;
		return resultat ;
	}

	// 10 numbre d'alerte 

		/**
		 * @return
		 * @throws JSONException
		 * @throws JsonProcessingException
		 * @throws SQLException 
		 */
		public String etatalerte () throws JSONException, JsonProcessingException, SQLException {


			//List<station> res = new ArrayList<station>();
			//String json = this.jsonClient;	
			//JSONObject obj = new JSONObject(json);
			//JSONObject request = obj.getJSONObject("request");

			Statement stmt3 = this.connect.createStatement();
			int count = 0;

			ResultSet rs3 = stmt3.executeQuery("select count (etatalerte) from district where etatalerte=1  ");
			while(rs3.next()){

				count = rs3.getInt("etatalerte");
			}
			//	int numberstation = request.getInt("id");
			//					String query= "SELECT numberstation from station";
			//					try {
			//						pstmt = connect.prepareStatement(query);
			//						//	pstmt.setInt(1, numberstation);
			//						rs = pstmt.executeQuery();
			//						while(rs.next()) {
			//							station utilStation = new station();
			//							utilStation.setNumberStation(rs.getInt(1));
			//							res.add(utilStation); 
			//						}
			//					} catch (SQLException ex) {
			//						System.out.println("Erreur infos numberstation!");
			//					}
			System.out.println("je vais recuperer le nbr totale d'alerte en bdd");
			//ObjectMapper mapper = new ObjectMapper();
			//resultat =  resultat + mapper.writeValueAsString(res) + "}";
			String resultat= "{request:{ operation_type:etatalerte ,Table: myCapteur, Action :etatalerte ,  Data: "+count+"}}";
			//this.finalResponse = resultat;
			resultat = resultat  ;
			System.out.println(resultat);
			this.json=resultat;
			return resultat ;
		}

		
		// CAPTEURAIR ACTIF 
		/**
		 * @return
		 * @throws JSONException
		 * @throws JsonProcessingException
		 * @throws SQLException 
		 */

		public String capteurairActif(String date) throws JSONException, JsonProcessingException, SQLException{


			List<CapteurAir> res = new ArrayList<CapteurAir>();


			//JSONObject obj = new JSONObject(json);
			//JSONObject request = obj.getJSONObject("request");
			Statement stm3= this.connect.createStatement();
			int count = 0;
 
			ResultSet rs3 = stm3.executeQuery("SELECT namecapteur FROM capteurair where datereleve = '"+date+ "\'");
			while(rs3.next()){
				count = rs3.getInt("namecapteur");

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
			String resultat= "{request:{ operation_type:capteurairActif , Table: capteurair, Action : capteurairActif   ,  Data: "+count+"}}";
			//this.finalResponse = resultat;
			resultat = resultat ;
			this.json=resultat;
			System.out.println(resultat);
			return resultat ;
		} 

}
