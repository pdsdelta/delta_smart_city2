package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
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
import com.fasterxml.jackson.databind.util.JSONPObject;

import connectionPool.DataSource;
import user.Users;

public class CityServer {
	
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private String jsonClient ;
	//Attributs for connexion and Database read
	private Connection connect;
	private Statement stm;
	private ResultSet rs;
	private PreparedStatement pstmt;
	private DataSource connection;
	private String finalResponse ;
 
    public void start(int port) throws IOException, ClassNotFoundException, SQLException {
    	//Connexion
    	this.connection= new DataSource();
		this.connect = connection.getConnection();
		//Réseaux
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.jsonClient = in.readLine();
        System.out.println("Le client a envoyÉ ce JSON : " + this.jsonClient + "\n");
        this.generateSQL();
        out.println(finalResponse);
        

    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
    
    public String generateSQL() throws IOException {
    	
    	String json = this.jsonClient;
    	String query = "requette fausse";
		try {
			JSONObject obj = new JSONObject(json);
		    JSONObject request = obj.getJSONObject("request");
			String operationType = request.getString("operation_type");
			
			if(operationType.equals("CREATE")) {
				System.out.println(addUtilisateur());
			}else if(operationType.equals("SELECT_ALL")) { //OK
				query = "SELECT * FROM Users";
				System.out.println(this.getAllUtilisateur(query));
			}else if(operationType.equals("SELECT_ONE")) { //OK
				query= "SELECT * FROM Users WHERE login = ?";
				System.out.println(this.getUtilisateur(query));
			}else if(operationType.equals("UPDATE")) { //OK
				query = "update Users set ? =?  WHERE login=? ";
				System.out.println(this.updateUtilisateur(query));
			}else if(operationType.equals("DELETE_ONE")) { //OK 
				String monLogin = request.getString("login");
				query= "DELETE FROM Users WHERE login = ? ";
				deleteUtilisateur(query);
			}
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		
		return query;
    }
    
    
    //CREATE
    public String addUtilisateur() throws JSONException {
    	String resultat= "{Table: users, Action: Create ,Status : " ; 
    	int res = 0;
    	String status = "Unknown";
    	String json = this.jsonClient;	
		JSONObject obj = new JSONObject(json);
		JSONObject request = obj.getJSONObject("request");
		String monNom = request.getString("nom");
		String monPrenom = request.getString("prenom");
		String monLogin = request.getString("login");
		String monPass = request.getString("pass");
		int monProfil = request.getInt("profil");
		
		String query = "INSERT INTO Users (nom, prenom, login, pass, profil) " + "VALUES (?, ?, ?, ?, ?)";
		System.out.println("La requette SQL associée est : " + query + "\n" );
		try {
			pstmt = connect.prepareStatement(query);
			pstmt.setString(1, monNom);
			pstmt.setString(2, monPrenom);
			pstmt.setString(3, monLogin);
			pstmt.setString(4, monPass);
			pstmt.setInt(5, monProfil);
			res = pstmt.executeUpdate();
			if(res == 1) {
				status = "Succed";
			}else {
				status ="failed";
			}
			resultat = resultat + status +" ,";
		} catch (SQLException ex) {
			Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
		}
		resultat = resultat + "Data : [{ nom: "+monNom + ", prenom: "+ monPrenom + ", login : "+ monLogin +", pass : "+ monPass +", profil : "+ monProfil +"} ]}";
		this.finalResponse = resultat ;
		return resultat;
	}
    
    
    //READ ALL
    public String getAllUtilisateur(String query) throws JsonProcessingException {
    	String resultat= "{Table: users, Action: SELECT_ALL: , Data: ";
    	System.out.println("La requette SQL associée est : " + query + "\n" );
    	List<Users> res = new ArrayList<Users>();
		try {
			stm = connect.createStatement();
			rs = stm.executeQuery(query);
			while (rs.next()) {
				Users util = new Users();
				util.setId(rs.getInt(1));
				util.setNom(rs.getString(2));
				util.setPrenom(rs.getString(3));
				util.setLogin(rs.getString(4));
				util.setPwd(rs.getString(5));
				util.setProfil(rs.getInt(6));
				res.add(util);
				//System.out.println("Opération réalisée avec succés\n");
			}
		} catch (SQLException ex) {
			Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
		}
		ObjectMapper mapper = new ObjectMapper();
		resultat =  resultat + mapper.writeValueAsString(res) + "}";
		this.finalResponse =resultat;
		return resultat;
	}
    
    
    //READ ONE
    public String getUtilisateur(String query) throws JSONException, JsonProcessingException {
    	String resultat= "{Table: users, Data: ";
    	System.out.println("La requette SQL associée est : " + query + "\n" );
    	List<Users> res2 = new ArrayList<Users>();
    	String json = this.jsonClient;	
		JSONObject obj = new JSONObject(json);
		JSONObject request = obj.getJSONObject("request");
		String monLogin = request.getString("login");
		try {
			pstmt = connect.prepareStatement(query);
			pstmt.setString(1, monLogin);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
			Users util = new Users();
			util.setId(rs.getInt(1));
			util.setNom(rs.getString(2));
			util.setPrenom(rs.getString(3));
			util.setLogin(rs.getString(4));
			util.setPwd(rs.getString(5));
			util.setProfil(rs.getInt(6));
			res2.add(util);
			}
		} catch (SQLException ex) {
			System.out.println("Erreur, veuillez r�essayer");
		}
		ObjectMapper mapper = new ObjectMapper();
		resultat =  resultat + mapper.writeValueAsString(res2) + "}";
		this.finalResponse = resultat;
		return resultat;
    	
    }
    
    //UPDATE
    public String updateUtilisateur(String query) throws JSONException { 
    	
    	String field ="unknown";
    	String status = "unknown";
		String json = this.jsonClient;	
		JSONObject obj = new JSONObject(json);
		JSONObject request = obj.getJSONObject("request");
		String toUpdate = request.getString("to_modify");
		String theUpdate = request.getString("modification");
		String monLogin = request.getString("login");
		String resultat= "{Table: users, Action: Update_user , login =  " + monLogin  ;
		
		int res = 0;
		if(toUpdate.equals("nom")) {
			 field = "nom";
			 query = "update Users set nom =?  WHERE login=? "; 
			 System.out.println("La requette SQL associée est : " + query + "\n" );
			try { 
				pstmt = connect.prepareStatement(query); 
				pstmt.setString(1, theUpdate);
				pstmt.setString(2, monLogin);
				res = pstmt.executeUpdate(); 
				resultat= resultat + ",field = " +field ;
			} catch (SQLException ex) {
				Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
			}
		}else  if(toUpdate.equals("prenom")) {
			 field = "prenom";
			 query = "update Users set prenom =?  WHERE login=? ";
			 System.out.println("La requette SQL associée est : " + query + "\n" );
				try { 
					pstmt = connect.prepareStatement(query); 
					pstmt.setString(1, theUpdate);
					pstmt.setString(2, monLogin);
					res = pstmt.executeUpdate(); 
					resultat= resultat + ",field = " +field ;
				} catch (SQLException ex) {
					Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
				}
		}else if(toUpdate.equals("pass")) {
			 field = "pass";
			 query = "update Users set pass =?  WHERE login=? "; 
			 System.out.println("La requette SQL associée est : " + query + "\n" );
				try { 
					pstmt = connect.prepareStatement(query); 
					pstmt.setString(1, theUpdate);
					pstmt.setString(2, monLogin);
					res = pstmt.executeUpdate(); 
					resultat= resultat + ",field = " +field ;
				} catch (SQLException ex) {
					Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
				}
		}
		if(res != 0) {
			status = "Succed";
		}
		resultat = resultat + ", Status : " + status + " }";
		this.finalResponse =resultat ;
		return resultat;
    }
    
    //DELETE
    public void deleteUtilisateur(String query) throws JSONException {
    		System.out.println("La requette SQL associ�e est : " + query + "\n" );
    		String json = this.jsonClient;	
    		JSONObject obj = new JSONObject(json);
    		JSONObject request = obj.getJSONObject("request");
    		String monLogin = request.getString("login");
    		System.out.println("Utilisateur supprimer avec succ�s");
    	try {
			pstmt=connect.prepareStatement (query);
			pstmt.setString(1, monLogin);
			pstmt.executeUpdate();
			pstmt.close();
    	} catch (SQLException ex) {
			Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    

    public static void main(String[] args) throws ClassNotFoundException, SQLException{
    	
        CityServer server=new CityServer();
        try {
			server.start(7000);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
}
