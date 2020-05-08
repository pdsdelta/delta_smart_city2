package tram_line;

import java.awt.event.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;

import javax.swing.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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


import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import city.city;
import connectionPool.DataSource;


class threadMap extends Thread {
		private Socket clientSocket;
	    private PrintWriter out;
	    private BufferedReader in;
	    private String mapJson ;
	    
	    private Connection connect;
		private Statement stm;
		private ResultSet rs;
		private PreparedStatement pstmt;
		private DataSource connection;
		private String response ;
	    
	    public threadMap(Socket socket) {
	        this.clientSocket = socket;
	    }
	    
	    
	    public void run() {
	    	 try {
	    		this.connection= new DataSource();
	    	    this.connect = connection.getConnection();
	    	    out = new PrintWriter(clientSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	         
				String inputLine;
		        while(true) {
			        	this.mapJson = in.readLine();
				        System.out.println("Le client a envoye ce JSON : " + this.mapJson + "\n");
				        this.generateSQL();
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

				if(operationType.equals("SAVEMAP")) { 
					addGetCity();
				}else if(operationType.equals("INFOMAP")) { 
					informationMap();
				}
				
			} catch (JSONException e) {
				
				e.printStackTrace();
			}
			return query;
	    }
	
	
	    public String addGetCity() throws JSONException {		
	    	String resultat= "{Table: city, Action: SAVEMAP " ; 
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
				pstmt=connect.prepareStatement (query);
				pstmt.setInt(1, idCity);
				pstmt.executeUpdate(); 
				pstmt.close();
			} catch (SQLException e) { 
				System.out.println("Erreur! "); 
			}
		
			query = "INSERT INTO city (idCity, nameCity, longueurCity, largeurCity, budgetStation,nombreMaxVoiture,seuilAtmoCity,tailleCity) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
    
			resultat = resultat + "Data : [{  idCity: "+idCity + ", nameCity: "+ nameCity + ", longueurCity : "+ longueurCity +", largeurCity : "+ largeurCity +", budgetStation : "+ budgetStation +",nombreMaxVoiture : "+ nombreMaxVoiture +",seuilAtmoCity : "+ seuilAtmoCity +",tailleCity : "+ tailleCity +"} ]}";
			this.response = resultat ;
			return resultat;
	    }	
	    
	    public String informationMap() throws JSONException, JsonProcessingException {
	    	String resultat= "{Table: city, Action : INFOMAP ,  Data: ";
	    	List<city> res = new ArrayList<city>();
	    	String json = this.mapJson;	
			JSONObject obj = new JSONObject(json);
			JSONObject request = obj.getJSONObject("request");
			int idCity = request.getInt("idCity");
			String query= "SELECT * FROM city WHERE idCity = ?";
			try {
				pstmt = connect.prepareStatement(query);
				pstmt.setInt(1, idCity);
				rs = pstmt.executeQuery();
				while(rs.next()) {
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
			resultat =  resultat + mapper.writeValueAsString(res) + "}";
			this.response = resultat;
			return resultat;
	    }
}