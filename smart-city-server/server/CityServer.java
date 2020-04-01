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
	private String finalQuery ;
 
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
        System.out.println("Le client a envoyer ce JSON : " + this.jsonClient + "\n");
        this.generateSQL();
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
    
    public List<Users> executeSQL() throws IOException {
    	String query = this.generateSQL();
    	List<Users> users =this.getAllUtilisateur(query);
    	return users;
    }
    
    public String generateSQL() throws IOException {
    	
    	String json = this.jsonClient;
    	String query = "requette fausse";
		try {
			JSONObject obj = new JSONObject(json);
		    JSONObject request = obj.getJSONObject("request");
			String operationType = request.getString("operation_type");
			
			if(operationType.equals("CREATE")) {
				System.out.println("En construction");
			}else if(operationType.equals("SELECT_ALL")) { //OK
				query = "SELECT * FROM Users";
				//System.out.println(this.executeSQL());
				System.out.println(this.getAllUtilisateur(query));
			}else if(operationType.equals("SELECT_ONE")) {
				query= "SELECT * FROM Users WHERE login = ?";
				System.out.println(this.getUtilisateur(query));
				//getUtilisateur(query);
			}else if(operationType.equals("UPDATE")) {
				 
			}else if(operationType.equals("DELETE_ONE")) { //OK 
				String monLogin = request.getString("login");
				query= "DELETE FROM Users WHERE login = ? ";
				deleteUtilisateur(query);
			}
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		//this.finalQuery = query ;
		
		return query;
    }
    
    public List<Users> getAllUtilisateur(String query) {
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
		
		return res;
	}
    
    public List<Users> getUtilisateur(String query) throws JSONException {
    	List<Users> res2 = new ArrayList<Users>();
    	String json = this.jsonClient;	
		JSONObject obj = new JSONObject(json);
		JSONObject request = obj.getJSONObject("request");
		String monLogin = request.getString("login");
		try {
			pstmt = connect.prepareStatement(query);
			pstmt.setString(1, monLogin);
			this.finalQuery = pstmt.toString() ;
			System.out.println("La requette SQL associée est : " + this.finalQuery + "\n" );
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
		return res2;
    	
    }
    
    public void deleteUtilisateur(String query) throws JSONException {
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
