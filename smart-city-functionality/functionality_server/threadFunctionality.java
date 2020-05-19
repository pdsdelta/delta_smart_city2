package functionality_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import CapteurAir.CapteurAir;
import city.city;
import connectionPool.DataSource;
import district.District;
import infocarbon.CarbonServerUtils;
import station.station;

class threadFunctionality extends Thread {
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private String mapJson;

	private Connection connect;
	private Statement stm;
	private ResultSet rs;
	private PreparedStatement pstmt;
	private DataSource connection;
	private String response;

	public threadFunctionality(Socket socket) {
		this.clientSocket = socket;
	}

	public void run() {
		try {
			this.connection = new DataSource();
			this.connect = connection.getConnection();
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			String inputLine;
			while (true) {
				this.mapJson = in.readLine();
				System.out.println("Le client a envoye ce JSON : " + this.mapJson + "\n");
				this.generateSQL();
				System.out.println("Le serveur va envoyer au client ce JSON : " + response + "\n");
				out.println(response);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String generateSQL() throws IOException {

		String json = this.mapJson;
		String query = "bad request";
		try {
			JSONObject obj = new JSONObject(json);
			JSONObject request = obj.getJSONObject("request");
			String operationType = request.getString("operation_type");
			System.out.println("L'opération est : " + operationType);
			if (operationType.equals("SAVEMAP")) {
				addGetCity();
			} else if (operationType.equals("INFOMAP")) {
				informationMap();
			} else if (operationType.equals("SAVESTATION")) {
				addGetStation();
			} else if (operationType.equals("INFOSTATION")) {
				informationStation();
			} else if (operationType.equals("GET_PRIVATE_CARBON")) {//INFO CARBON PRIVATE TRANSPORT request
				String date = request.getString("date");
				CarbonServerUtils s = new CarbonServerUtils(this.connect, this.stm, this.rs, this.pstmt);
				query = s.getNbCars(date);
				this.response = query;
			}else if (operationType.equals("GET_PUBLIC_CARBON")) {//INFO CARBON PUBLIC TRANSPORT requet
				CarbonServerUtils s = new CarbonServerUtils(this.connect, this.stm, this.rs, this.pstmt);
				query = s.getNbTram();
				this.response = query;
			}else if (operationType.equals("GET_GLOBAL_CARBON")) {//INFO CARBON request
				String date = request.getString("date");
				CarbonServerUtils s = new CarbonServerUtils(this.connect, this.stm, this.rs, this.pstmt);
				query = s.getGlobalCarbonne(date);
				this.response = query;
			}
			
			//Action concernant la simulation de circulation de voiture
			if(operationType.equals("entrer")) {
				System.out.println("Une voiture veut entrer");
				this.response="Une voiture veut entrer";
			}else if(operationType.equals("sortir")) {
				System.out.println("Une voiture veut sortir");
				this.response= "Une voiture veut sortir";

			}
			// Requ�te concernant les d�tecteurs de v�hicules
			if (operationType.equals("CREATE_SENSOR")) {

			} else if (operationType.equals("DELETE_SENSOR")) {

			} else if (operationType.equals("ALL_SENSOR")) {

			} else if (operationType.equals("SELECT_SENSOR")) {

			} else if (operationType.equals("GET_ACTIVE")) {

			} else if (operationType.equals("UPDATE_SENSOR")) {

			}
			// Requ�te concernant les bornes retractables
			if (operationType.equals("CREATE_TERMINAL")) {

			} else if (operationType.equals("DELETE_TERMINAL")) {

			} else if (operationType.equals("ALL_TERMINAL")) {

			} else if (operationType.equals("SELECT_TERMINAL")) {

			} else if (operationType.equals("GET_STATUS")) {

			} else if (operationType.equals("UPDATE_TERMINAL")) {

			}
			// Rajouter ici vos operation_type avec vos m�thodes :)
			
			if(operationType.equals("INFOCITY")) {
				sizecity();
			}

		} catch (JSONException e) {

			e.printStackTrace();
		}
		return query;
	}

	public String addGetCity() throws JSONException {
		String resultat = "{Table: city, Action: SAVEMAP ";
		int res = 0;
		String json = this.mapJson;
		JSONObject obj = new JSONObject(json);
		JSONObject request = obj.getJSONObject("request");
		int idCity = request.getInt("idCity");
		String nameCity = request.getString("nameCity");
		int longueurCity = request.getInt("longueurCity");
		int largeurCity = request.getInt("largeurCity");
		int budgetStation = request.getInt("budgetStation");
		int nombreMaxVoiture = request.getInt("nombreMaxVoiture");
		int seuilAtmoCity = request.getInt("seuilAtmoCity");
		int tailleCity = request.getInt("tailleCity");

		String query = "delete from city WHERE idCity=?";

		try {
			pstmt = connect.prepareStatement(query);
			pstmt.setInt(1, idCity);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("Erreur! addGetCity");
		}

		query = "INSERT INTO city (idCity, nameCity, longueurCity, largeurCity, budgetStation,nombreMaxVoiture,seuilAtmoCity,tailleCity) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			pstmt = connect.prepareStatement(query);
			pstmt.setInt(1, idCity);
			pstmt.setString(2, nameCity);
			pstmt.setInt(3, longueurCity);
			pstmt.setInt(4, largeurCity);
			pstmt.setInt(5, budgetStation);
			pstmt.setInt(6, nombreMaxVoiture);
			pstmt.setInt(7, seuilAtmoCity);
			pstmt.setDouble(8, tailleCity);
			res = pstmt.executeUpdate();
			System.out.println("operation ok\n");
		} catch (SQLException ex) {
			Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("je vais enregistrer en bdd");

		resultat = resultat + "Data : [{  idCity: " + idCity + ", nameCity: " + nameCity + ", longueurCity : "
				+ longueurCity + ", largeurCity : " + largeurCity + ", budgetStation : " + budgetStation
				+ ",nombreMaxVoiture : " + nombreMaxVoiture + ",seuilAtmoCity : " + seuilAtmoCity + ",tailleCity : "
				+ tailleCity + "} ]}";
		this.response = resultat;
		return resultat;
	}

	public String addGetStation() throws JSONException {
		String resultat = "{Table: station, Action: SAVESTATION ";
		int res = 0;
		String json = this.mapJson;
		JSONObject obj = new JSONObject(json);
		JSONObject request = obj.getJSONObject("request");
		int idStation = request.getInt("idStation");
		int numberStation = request.getInt("numberStation");
		int coutStation = request.getInt("coutStation");
		int longueurReseau = request.getInt("longueurReseau");
		int numberTram = request.getInt("numberTram");
		int numberLine = request.getInt("numberLine");

		String query = "delete from station WHERE idStation=?";

		try {
			pstmt = connect.prepareStatement(query);
			pstmt.setInt(1, idStation);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("Erreur! addGetStation");
		}

		query = "INSERT INTO station (idStation, numberStation, coutStation, longueurReseau, numberTram,numberLine) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		try {
			pstmt = connect.prepareStatement(query);
			pstmt.setInt(1, idStation);
			pstmt.setInt(2, numberStation);
			pstmt.setInt(3, coutStation);
			pstmt.setInt(4, longueurReseau);
			pstmt.setInt(5, numberTram);
			pstmt.setInt(6, numberLine);
			res = pstmt.executeUpdate();
			System.out.println("operation ok\n");
		} catch (SQLException ex) {
			Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("je vais enregistrer en bdd");

		resultat = resultat + "Data : [{  idStation: " + idStation + ", numberStation: " + numberStation
				+ ", coutStation : " + coutStation + ", longueurReseau : " + longueurReseau + ", numberTram : "
				+ numberTram + ",numberLine : " + numberLine + "} ]}";
		this.response = resultat;
		return resultat;
	}

	public String informationMap() throws JSONException, JsonProcessingException {
		String resultat = "{Table: city, Action : INFOMAP ,  Data: ";
		List<city> res = new ArrayList<city>();
		String json = this.mapJson;
		JSONObject obj = new JSONObject(json);
		JSONObject request = obj.getJSONObject("request");
		int idCity = request.getInt("idCity");
		String query = "SELECT * FROM city WHERE idCity = ?";
		try {
			pstmt = connect.prepareStatement(query);
			pstmt.setInt(1, idCity);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				city util = new city();
				util.setIdCity(rs.getInt(1));
				util.setNameCity(rs.getString(2));
				util.setLongueurCity(rs.getInt(3));
				util.setLargeurCity(rs.getInt(4));
				util.setBudgetStation(rs.getInt(5));
				util.setNombreMaxVoiture(rs.getInt(6));
				util.setSeuilAtmoCity(rs.getInt(7));
				util.setTailleCity(rs.getInt(8));
				res.add(util);
			}

		} catch (SQLException ex) {
			System.out.println("Erreur infos!");
		}
		System.out.println("je vais recuperer des infos en bdd");
		ObjectMapper mapper = new ObjectMapper();
		resultat = resultat + mapper.writeValueAsString(res) + "}";
		this.response = resultat;
		return resultat;
	}

	public String informationStation() throws JSONException, JsonProcessingException {
		String resultat = "{Table: station, Action : INFOSTATION ,  Data: ";
		List<station> res = new ArrayList<station>();
		String json = this.mapJson;
		JSONObject obj = new JSONObject(json);
		JSONObject request = obj.getJSONObject("request");
		int idStation = request.getInt("idStation");
		String query = "SELECT * FROM station WHERE idStation = ?";
		try {
			pstmt = connect.prepareStatement(query);
			pstmt.setInt(1, idStation);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				station utilStation = new station();
				utilStation.setIdStation(rs.getInt(1));
				utilStation.setNumberStation(rs.getInt(2));
				utilStation.setCoutStation(rs.getInt(3));
				utilStation.setLongueurReseau(rs.getInt(4));
				utilStation.setNumberTram(rs.getInt(5));
				utilStation.setNumberLine(rs.getInt(6));
				res.add(utilStation);
			}
		} catch (SQLException ex) {
			System.out.println("Erreur infos station!");
		}
		System.out.println("je vais recuperer des infos station en bdd");
		ObjectMapper mapper = new ObjectMapper();
		resultat = resultat + mapper.writeValueAsString(res) + "}";
		this.response = resultat;
		return resultat;
	}
	
	public String sizecity() throws JSONException, JsonProcessingException {
		String resultat = "{Table: city, Action : INFOCITY ,  Data: ";
		List<city> res = new ArrayList<city>();
		String json = this.mapJson;
		JSONObject obj = new JSONObject(json);
		JSONObject request = obj.getJSONObject("request");
		String query = "SELECT taillecity from city where idcity = 1;";
		try {
			pstmt = connect.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				city utilCity = new city();
				utilCity.setTailleCity(rs.getDouble(1));

				res.add(utilCity);
			}
		} catch (SQLException ex) {
			System.out.println("Erreur infos city!");
		}
		System.out.println("je vais recuperer des infos city en bdd");
		ObjectMapper mapper = new ObjectMapper();
		resultat = resultat + mapper.writeValueAsString(res) + "}";
		this.response = resultat;
		return resultat;
	}
	
	public String indiceATMO() throws JSONException, JsonProcessingException {
		String resultat = "{Table: capteurair, Action : INFOINDATMO ,  Data: ";
		List<CapteurAir> res = new ArrayList<CapteurAir>();
		String json = this.mapJson;
		JSONObject obj = new JSONObject(json);
		JSONObject request = obj.getJSONObject("request");
		String query = "SELECT indiceatmo from capteurair;";
		try {
			pstmt = connect.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CapteurAir utilCapteurair = new CapteurAir();
				utilCapteurair.setIndice(rs.getInt(1));

				res.add(utilCapteurair);
			}
		} catch (SQLException ex) {
			System.out.println("Erreur infos capteurair!");
		}
		System.out.println("je vais recuperer des infos capteurair en bdd");
		ObjectMapper mapper = new ObjectMapper();
		resultat = resultat + mapper.writeValueAsString(res) + "}";
		this.response = resultat;
		return resultat;
	}
	
	public String seuilquartier() throws JSONException, JsonProcessingException {
		String resultat = "{Table: district, Action : INFOSEUIL ,  Data: ";
		List<District> res = new ArrayList<District>();
		String json = this.mapJson;
		JSONObject obj = new JSONObject(json);
		JSONObject request = obj.getJSONObject("request");
		String query = "SELECT seuilquartier from district;";
		try {
			pstmt = connect.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				District utilDistrict = new District();
				utilDistrict.setSeuilQuartierATMO(rs.getInt(1));

				res.add(utilDistrict);
			}
		} catch (SQLException ex) {
			System.out.println("Erreur infos capteurair!");
		}
		System.out.println("je vais recuperer des infos capteurair en bdd");
		ObjectMapper mapper = new ObjectMapper();
		resultat = resultat + mapper.writeValueAsString(res) + "}";
		this.response = resultat;
		return resultat;
	}

}
