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
        String jsonClient = in.readLine();
        
        System.out.println(jsonClient);
            if ("Bonjour CityServer".equals(jsonClient)) {
                out.println("Bonjour Chère client");
            }
            else {
                out.println("Je ne vous connais pas");
            }
        /*
        String toSend ="Réponse";
        out.write(toSend);
        out.flush();
        */
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
    
    public void executeSQL() throws IOException {
    	String query = this.generateSQL(in.readLine());
    	List<Users> users =this.getAllUtilisateur(query);
    }
    public String generateSQL(String json) throws IOException {
    	
    	String jsonClient = in.readLine();
    	String query = "";
		try {
			JSONObject obj = new JSONObject(jsonClient);
		    JSONObject request = obj.getJSONObject("request");
			String operationType = request.getString("operation_type");
			
			if(operationType.equals("CREATE")) {
			
			}else if(operationType.equals("SELECT_ALL")) {
				query = "SELECT * FROM Users";
			}else if(operationType.equals("SELECT_ONE")) {
				String login = request.getString("login");
			}else if(operationType.equals("UPDATE")) {
				
			}else if(operationType.equals("DELETE_ONE")) {
				
			}
			
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
	    
    	
    	return " ";
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
				System.out.println("Opération réalisée avec succés\n");
			}
		} catch (SQLException ex) {
			Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
		}
		for(Users tab:res) {
			System.out.println(tab);
		} 
		return res;
	}
    
    
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        CityServer server=new CityServer();
        try {
			server.start(7000);
			server.executeSQL();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        
    }
}

