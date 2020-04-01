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

	DataSource connection;
 
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
        System.out.println("Le client à envoyer ce JSON : " + this.jsonClient + "\n");
        System.out.println("La requette SQL associée est : " + this.generateSQL() + "\n" );
        System.out.println("***** Résultat ******** \n\n ");
        
        String json = this.jsonClient;
    	try {
        JSONObject obj = new JSONObject(json);
	    JSONObject request = obj.getJSONObject("request");
		String operationType = request.getString("operation_type");
		
		if(operationType.equals("CREATE")) {
			System.out.println("create");
		}else if(operationType.equals("SELECT_ALL")) {
			System.out.println(this.executeSQL());
		}else if(operationType.equals("SELECT_ONE")) {
			String monLogin = request.getString("login");
		}else if(operationType.equals("UPDATE")) {
			String monLogin = request.getString("login");
		}else if(operationType.equals("DELETE_ONE")) {
			String query= "DELETE FROM Users WHERE login = ? ";
			deleteUtilisateur(query);
		}
        //System.out.println(this.executeSQL());
    } catch (JSONException e) {
		
		e.printStackTrace();
	}
        
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
			//String monLogin = request.getString("login");
			
			if(operationType.equals("CREATE")) {
			
			}else if(operationType.equals("SELECT_ALL")) {
				query = "SELECT * FROM Users";
			}else if(operationType.equals("SELECT_ONE")) {
				
			}else if(operationType.equals("UPDATE")) {
				 
			}else if(operationType.equals("DELETE_ONE")) {
				String monLogin = request.getString("login");
				query= "delete from Users WHERE login=" + monLogin; 

			}
			
			
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
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
    
    public void deleteUtilisateur(String query) throws JSONException {
    		String json = this.jsonClient;	
    		JSONObject obj = new JSONObject(json);
    		JSONObject request = obj.getJSONObject("request");
    		String monLogin = request.getString("login");
    		
    		System.out.println("Utilisateur supprimer avec succ�s");
    	try {
    		//stm = connect.createStatement();
			//res = stm.executeQuery(query);
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
