package functionality_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import gestion_borne.AlgorithmeBorne;
import gestion_borne.mock.MotionSensorFonctionMock;
import gestion_borne.vue.TraficVue;
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

	public String generateSQL() throws IOException, ClassNotFoundException, SQLException {

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
				query = s.getPrivateCarbonne(date);
				this.response = query;
			}else if (operationType.equals("GET_PUBLIC_CARBON")) {//INFO CARBON PUBLIC TRANSPORT requet
				String date = request.getString("date");
				CarbonServerUtils s = new CarbonServerUtils(this.connect, this.stm, this.rs, this.pstmt);
				query = s.getPublicCarbonne(date);
				this.response = query;
			}else if (operationType.equals("GET_GLOBAL_CARBON")) {//INFO CARBON request
				String date = request.getString("date");
				CarbonServerUtils s = new CarbonServerUtils(this.connect, this.stm, this.rs, this.pstmt);
				query = s.getGlobalCarbonne(date);
				this.response = query;
			}else if (operationType.equals("PUT_CARBON")) {//INFO CARBON request
				String date = request.getString("date");
				CarbonServerUtils s = new CarbonServerUtils(this.connect, this.stm, this.rs, this.pstmt);
				double carbon = request.getDouble("carbon");
				query = s.putCarbonne(carbon, date);
				this.response = query;
			}
			// Requete concernant les analyses des indicateurs

			// 1 CAPTEURAIR
			if(operationType.equals( "infoCapteurair"))
			{
				this.response="{request:{operation_type: infoCapteurair,�table: district, status: SUCCESS}}";}
			// 2 MOTION SENSOR
			if(operationType.equals( "infoMotionsensor"))
			{
				this.response="{request:{operation_type:infoMotionsensor,�table: motionsensor, status: SUCCESS}}";}

			//3  NUMBRE DE STATION
			if(operationType.equals( "Infostationr"))
			{
				this.response="{request:{operation_type:Infostation,�table: station, status: SUCCESS}}";}

			//4 NUMBRE DE cars
			if(operationType.equals( "nbcars"))
			{
				this.response="{request:{operation_type:nbcars,�table: InfoGlobalCarbon, status: SUCCESS}}";}

			//5 numbre tram
			if(operationType.equals( "informationTram"))
			{
				this.response="{request:{operation_type:informationTram,�table: station, status: SUCCESS}}";}

			//6numbre de borne
			if(operationType.equals( "informationBorne"))
			{
				this.response="{request:{operation_type:informationBorne,�table:terminal, status: SUCCESS}}";}

			// 7 EMPREINTE DE CARBONE

			if(operationType.equals( "emprientecarbone"))
			{
				this.response="{request:{operation_type:emprientecarbone,�table:carboninfo, status: SUCCESS}}";}
			// 8TAUX DATMO
			if(operationType.equals( "emprientecarbone"))
			{
				this.response="{request:{operation_type:tauxATMO,�table:myCapteur, status: SUCCESS}}";}
			// 9TAUX DE DEPASSEMENT DE SEUIL
			if(operationType.equals( "emprientecarbone"))
			{
				this.response="{request:{operation_type:tauxDepAtmo,�table:myCapteur, status: SUCCESS}}";}
			//10 nbr d'alerte

			if(operationType.equals( "etatalerte"))
			{
				this.response="{request:{operation_type:etatalerte,�table:myCapteur, status: SUCCESS}}";}

			// 11capteurairActif

			if(operationType.equals( "namecapteur"))
			{
				this.response="{request:{operation_type:namecapteur,�table:capteurair, status: SUCCESS}}";}

			//Action concernant la simulation de circulation de voiture
			if(operationType.equals("entrer")) {
				TraficVue vue = new TraficVue();
				this.response=vue.TraitementEntrer(this.mapJson);
				//AlgorithmeBorne algo= new AlgorithmeBorne();
				//this.response=algo.TraitementEntrer(this.mapJson);
			}else if(operationType.equals("sortir")) {
				TraficVue vue = new TraficVue();
				this.response=vue.TraitementSortie(this.mapJson);
				//this.response=TraficVue.AlgorithmeBorne.TraitementSortie(this.mapJson);
				//AlgorithmeBorne algo= new AlgorithmeBorne();
				//this.response=algo.TraitementSortie(this.mapJson);
			}
			// Requ�te concernant les d�tecteurs de v�hicules
			if (operationType.equals("CREATE_SENSOR")) {
				this.response  ="{request:{ operation_type: CREATE_SENSOR, target: MotionSensor, status: Success}} " ;
			} else if (operationType.equals("DELETE_SENSOR")) {
				this.response  ="{request:{ operation_type: DELETE_SENSOR, target: MotionSensor, status: Success}} " ;
			} else if (operationType.equals("ALL_SENSOR")) {
				this.response  ="{request:{ operation_type: ALL_SENSOR, target: MotionSensor, status: Success}} " ;
			} else if (operationType.equals("SELECT_SENSOR")) {
				this.response  ="{request:{ operation_type: SELECT_SENSOR, target: MotionSensor, status: Success}} " ;
			}  else if (operationType.equals("UPDATE_SENSOR")) {
				this.response  ="{request:{ operation_type: UPDATE_SENSOR, target: MotionSensor, status: Success}} " ;

			}
			// Requ�te concernant les bornes retractables
			if (operationType.equals("CREATE_TERMINAL")) {
				this.response  ="{request:{ operation_type: CREATE_TERMINAL, target: Terminal, status: Success}} " ;
			} else if (operationType.equals("DELETE_TERMINAL")) {
				this.response  ="{request:{ operation_type: DELETE_TERMINAL, target: Terminal, status: Success}} " ;
			} else if (operationType.equals("ALL_TERMINAL")) {
				this.response  ="{request:{ operation_type: ALL_TERMINAL, target: Terminal, status: Success}} " ;
			} else if (operationType.equals("SELECT_TERMINAL")) {
				this.response  ="{request:{ operation_type: SELECT_TERMINAL, target: city, status: Success}} " ;
			} else if (operationType.equals("SET_CITY")) {
				this.response  ="{request:{ operation_type: SET_CITY, target: Terminal, status: Success}} " ;
			} else if (operationType.equals("UPDATE_TERMINAL")) {
				this.response  ="{request:{ operation_type: UPDATE_TERMINAL, target: Terminal, status: Success}} " ;

			}
			// Rajouter ici vos operation_type avec vos m�thodes :)

			if(operationType.equals("INFOCITY")) {
				sizecity();
				System.out.println("La m�thode INFOCITY a �t� effectu�e");

			}else if(operationType.equals("UPDATEINDICE")) {
				updateindice();
				System.out.println("La m�thode UPDATEINDICE a �t� effectu�e");	

			}else if(operationType.equals("CAPTEURAIR")) {
				insertcapteur();
				System.out.println("La m�thode CAPTEURAIR a �t� effectu�e");

			}else if(operationType.equals("ADDQUARTIER")) {
				insertquartier();
				System.out.println("La m�thode ADDQUARTIER a �t� effectu�e");

			}else if(operationType.equals("UPDATESEUIL")) {
				updateseuil();
				System.out.println("La m�thode UPDATESEUIL a �t� effectu�e");

			}else if(operationType.equals("UPDATECAPTEUR")) {
				updatecapteur();
				System.out.println("La m�thode UPDATECAPTEUR a �t� effectu�e");

			}else if(operationType.equals("UPDATECITY")) {
				updatecity();
				System.out.println("La m�thode UPDATECITY a �t� effectu�e");

			}else if(operationType.equals("INFOCAPTEUR")) {
				capteurair();
				System.out.println("La m�thode INFOCAPTEUR a �t� effectu�e");
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
		System.out.println("R�cup�ration des valeurs en bdd");
		ObjectMapper mapper = new ObjectMapper();
		resultat = resultat + mapper.writeValueAsString(res) + "}";
		this.response = resultat;
		return resultat;
	}

	public String indice() throws JSONException, JsonProcessingException {
		String resultat = "{Table: capteurair, Action : INFOINDATMO ";
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
		resultat = resultat + "DATA : [{"+mapper.writeValueAsString(res) + "}]}";
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
			System.out.println("Erreur infos district!");
		}
		System.out.println("je vais recuperer des infos district en bdd");
		ObjectMapper mapper = new ObjectMapper();
		resultat = resultat + mapper.writeValueAsString(res) + "}";
		this.response = resultat;
		return resultat;
	}

	public String addindice() throws JSONException {
		String resultat = "{Table:capteurair , Action: ADDindice ";
		int res = 0;
		String json = this.mapJson;
		JSONObject obj = new JSONObject(json);
		JSONObject request = obj.getJSONObject("request");
		int idCapteur = request.getInt("idcapteur");
		int nameCapteur = request.getInt("namecapteur");
		int dateReleve = request.getInt("datereleve");
		int IndiceATMO = request.getInt("indiceatmo");

		String query = "delete from capteurair WHERE idcapteur=?";

		try {
			pstmt = connect.prepareStatement(query);
			pstmt.setInt(1, idCapteur);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("Erreur! addindice");
		}

		query = "INSERT INTO capteurair (indiceatmo) "
				+ "VALUES ()";
		try {
			pstmt = connect.prepareStatement(query);
			pstmt.setInt(1, IndiceATMO);
			res = pstmt.executeUpdate();
			System.out.println("operation ok\n");
		} catch (SQLException ex) {
			Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("je vais enregistrer en bdd");

		resultat = resultat + "Data : [{  idcapteur: " + idCapteur + ", namecapteur: " + nameCapteur
				+ ", datereleve : " + dateReleve + ", indiceatmo : " + IndiceATMO + "} ]}";
		this.response = resultat;
		return resultat;
	}

	public String insertquartier() throws JSONException {
		String resultat = "{Table:district , Action: ADDQUARTIER ";
		String json = this.mapJson;
		JSONObject obj = new JSONObject(json);
		JSONObject request = obj.getJSONObject("request");
		int id = request.getInt("id");
		String name = request.getString("name");
		int seuilquartieratmo = request.getInt("seuilquartieratmo");

		try {
			pstmt = connect.prepareStatement("INSERT INTO district (id, name, seuilquartieratmo) VALUES (?, ?, ?)");
			pstmt.setInt(1, id);
			pstmt.setString(2, name);
			pstmt.setInt(3, seuilquartieratmo);
			pstmt.executeUpdate();
			System.out.println("operation ok\n");
		} catch (SQLException ex) {
			Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
			ex.printStackTrace();
		}
		System.out.println("on est dans la m�thode insertquartier");

		resultat = resultat + "Data : [{  id: " + id + ", name: " + name
				+ ", seuilquartieratmo : " + seuilquartieratmo + "} ]}";
		this.response = resultat;
		return resultat;
	}

	public String insertcapteur() throws JSONException {
		String resultat = "{Table:capteurair , Action: ADDCAPTEUR ";
		//String resultat = "FIN";
		//int res = 0;
		String json = this.mapJson;
		JSONObject obj = new JSONObject(json);
		JSONObject request = obj.getJSONObject("request");
		int idcapteur = request.getInt("idcapteur");
		String datereleve = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-M-yyyy hh:mm:ss"));
		//request.getString("datereleve");
		int indiceatmo = request.getInt("indiceatmo");
		String namecapteur = request.getString("namecapteur");
		int intervalle = request.getInt("intervalle");

		try {
			pstmt = connect.prepareStatement("INSERT INTO capteurair (idcapteur, datereleve, indiceatmo, namecapteur, intervalle) VALUES (?, ?, ?, ?, ?)");
			pstmt.setInt(1, idcapteur);
			pstmt.setString(2, datereleve);
			pstmt.setInt(3, indiceatmo);
			pstmt.setString(4, namecapteur);
			pstmt.setInt(5, intervalle);
			pstmt.executeUpdate();
			System.out.println("operation ok\n");
		} catch (SQLException ex) {
			Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
			ex.printStackTrace();
		}
		System.out.println("on est dans la m�thode insertcapteur");

		resultat = resultat + "Data : [{  idcapteur: " + idcapteur + ", datereleve: " + datereleve
				+ ", indiceatmo : " + indiceatmo + ", namecapteur : " + namecapteur + ", intervalle : " + intervalle +"} ]}";
		this.response = resultat;
		return resultat;
	}


	public String updateseuil() throws JSONException {
		String resultat = "{Table:district , Action: UPDATESEUIL ";
		String json = this.mapJson;
		JSONObject obj = new JSONObject(json);
		JSONObject request = obj.getJSONObject("request");
		String name = request.getString("name");
		int seuilquartieratmo = request.getInt("seuilquartieratmo");
		int indiceatmo = request.getInt("indiceatmo");
		int etatalerte = request.getInt("etatalerte");


		try {
			pstmt = connect.prepareStatement("UPDATE district SET seuilquartieratmo = (?), indiceatmo = (?), etatalerte = (?) WHERE name = (?)");
			pstmt.setInt(1, seuilquartieratmo);
			pstmt.setInt(2, indiceatmo);
			pstmt.setInt(3, etatalerte);
			pstmt.setString(4, name);
			pstmt.executeUpdate();
			System.out.println("operation ok\n");
		} catch (SQLException ex) {
			Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
			ex.printStackTrace();
		}
		System.out.println("on est dans la m�thode updateseuil");

		resultat = resultat + "Data : [{  seuilquartieratmo: " + seuilquartieratmo + ", indiceatmo: " + indiceatmo+ ", etatalerte: " + etatalerte+ "} ]}";
		this.response = resultat;
		return resultat;
	}

	public String updateindice() throws JSONException {
		String resultat = "{Table:district , Action: UPDATEINDICE ";
		//String resultat = "FIN";
		//int res = 0;
		String json = this.mapJson;
		JSONObject obj = new JSONObject(json);
		JSONObject request = obj.getJSONObject("request");
		String name = request.getString("name");
		int indiceatmo = request.getInt("indiceatmo");
		int etatalerte = request.getInt("etatalerte");

		try {
			pstmt = connect.prepareStatement("UPDATE district SET indiceatmo = (?), etatalerte = (?) WHERE name = (?)");
			pstmt.setInt(1, indiceatmo);
			pstmt.setInt(2, etatalerte);
			pstmt.setString(3, name);
			pstmt.executeUpdate();
			System.out.println("operation ok\n");
		} catch (SQLException ex) {
			Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
			ex.printStackTrace();
		}
		System.out.println("on est dans la m�thode updateindice");

		resultat = resultat + "Data : [{  indiceatmo: " + indiceatmo +", etatalerte: " + etatalerte+ "} ]}";
		this.response = resultat;
		return resultat;
	}


	public String updatecapteur() throws JSONException {
		String resultat = "{Table:capteurair , Action: UPDATECAPTEUR ";
		String json = this.mapJson;
		JSONObject obj = new JSONObject(json);
		JSONObject request = obj.getJSONObject("request");
		int intervalle = request.getInt("intervalle");

		try {
			pstmt = connect.prepareStatement("UPDATE capteurair SET intervalle = (?)");
			pstmt.setInt(1, intervalle);
			pstmt.executeUpdate();
			System.out.println("operation ok\n");
		} catch (SQLException ex) {
			Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
			ex.printStackTrace();
		}
		System.out.println("on est dans la m�thode updatecapteurair");

		resultat = resultat + "Data : [{  intervalle: " + intervalle +"} ]}";
		this.response = resultat;
		return resultat;
	}

	public String updatecity() throws JSONException {
		String resultat = "{Table:city , Action: UPDATECITY ";
		String json = this.mapJson;
		JSONObject obj = new JSONObject(json);
		JSONObject request = obj.getJSONObject("request");
		int idcity = request.getInt("idcity");
		int seuilatmocity = request.getInt("seuilatmocity");

		try {
			pstmt = connect.prepareStatement("UPDATE city SET seuilatmocity = (?) WHERE idcity = (?)");
			pstmt.setInt(1,seuilatmocity);
			pstmt.setInt(2,idcity);
			pstmt.executeUpdate();
			System.out.println("operation ok\n");
		} catch (SQLException ex) {
			Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
			ex.printStackTrace();
		}
		System.out.println("on est dans la m�thode updatecity");

		resultat = resultat + "Data : [{  seuilatmocity: " + seuilatmocity +"} ]}";
		this.response = resultat;
		return resultat;
	}

	public String capteurair() throws JSONException, JsonProcessingException {
		String resultat = "{Table: capteurair, Action : INFOCAPTEUR ,  Data: ";
		List<CapteurAir> res = new ArrayList<CapteurAir>();
		String json = this.mapJson;
		JSONObject obj = new JSONObject(json);
		JSONObject request = obj.getJSONObject("request");
		int idcapteur = request.getInt("idcapteur");
		try {
			pstmt = connect.prepareStatement("SELECT * FROM capteurair WHERE idcapteur = (?)");
			pstmt.setInt(1, idcapteur);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CapteurAir util = new CapteurAir();
				util.setId(rs.getInt(1));
				util.setDate(rs.getString(2));
				util.setIndice(rs.getInt(3));
				util.setName(rs.getString(4));
				util.setIntervalle(rs.getInt(5));
				res.add(util);
			}
		} catch (SQLException ex) {
			System.out.println("Erreur infos capteur");
		}
		System.out.println("R�cup�ration des valeurs en bdd");
		ObjectMapper mapper = new ObjectMapper();
		resultat = resultat + mapper.writeValueAsString(res) + "}";
		this.response = resultat;
		return resultat;
	}
}
