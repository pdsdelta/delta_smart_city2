package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import usefull.Order;
import user.Users;


public class CityClient {
	    private Socket clientSocket;
	    private PrintWriter out;
	    private BufferedReader in;
	    
	    
	    public void startConnection(String ip, int port) throws UnknownHostException, IOException, JSONException {
	    	String toSend;
	    	clientSocket = new Socket(ip, port);
	        out = new PrintWriter(clientSocket.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	        
	        while(true) {
	        	toSend =this.afficheMenu();
		        out.println(toSend);
		        String response = in.readLine();
		        System.out.println("***** Résultat ******\n");
		        System.out.println(this.diplayResult(response));
	        }
	    }
	 
	   
	    public String sendMessage(String Json) throws IOException {
	        System.out.println(Json);
	        String resp = in.readLine();
	        System.out.println(resp);
	        return resp;
	    }
	 
	    public void stopConnection() throws IOException {
	        in.close();
	        out.close();
	        clientSocket.close();
	    }
	    
	    public String afficheMenu() {
	    	
	
	    	Users u = new Users();
			u.setId(1);
			
			
	    	System.out.println("**********");
			System.out.println("*  MENU  *");
			System.out.println("********** \n");
			System.out.println("[ 1 ] Ajouter un utilisateur\n"+
					"[ 2 ] Afficher les utilisateurs\n"+
					"[ 3 ] Afficher un utilisateur spécifique\n"+
					"[ 4 ] Modifier les donn�es d'un utilisateur\n"+
					"[ 5 ] Supprimer un utilisateur\n\n");
			
			Scanner sc = new Scanner(System.in);
			PrintWriter pw = new PrintWriter(out);
			String json = "";
			try {
				while(true) {
					int a = sc.nextInt();
					switch(a){
	
						case 1: 
							Order ord = new Order(1,u);
							json = ord.generateJson();
							break;
						case 2: 
							ord = new Order(2,u);
							json = ord.generateJson();
						break;
						case 3: 
							ord = new Order(3,u);
							json = ord.generateJson();
							break;
						case 4:
							ord = new Order(4,u);
							json = ord.generateJson();
							break;
							
						case 5:
							ord = new Order(5,u);
							json = ord.generateJson();
							break;
						}
					return json ;
					
				}
		} catch (JsonProcessingException e) {

		}
			return json; 
	    
		
	 }
	    public String diplayResult(String jsonResponse) throws JSONException {
	    	JSONObject obj =new JSONObject(jsonResponse);
	    	String action = obj.getString("Action");
	    	String res = "Empty";
	    	List<Users> users = new ArrayList<Users>();
	    	switch(action) {
	    		case "CREATE" :
	    			if (obj.getString("Status").equals("Succed")){
	    				System.out.println("Utilisateur crée avec succès");
	    			}
	    			break;
	    		case "SELECT_ALL":
	    			JSONArray arr = obj.getJSONArray("Data");
	    			for (int i = 0; i < arr.length(); i++)
	    			{
	    			    Users util = new Users();
	    				util.setId(arr.getJSONObject(i).getInt("id"));
	    				util.setNom(arr.getJSONObject(i).getString("nom"));
	    				util.setPrenom(arr.getJSONObject(i).getString("prenom"));
	    				util.setLogin(arr.getJSONObject(i).getString("login"));
	    				util.setPwd(arr.getJSONObject(i).getString("pwd"));
	    				util.setProfil(arr.getJSONObject(i).getInt("profil"));
	    				users.add(util);
	    			    
	    			    
	    			}
	    			res = users.toString();
	    			break;
	    		case "SELECT_ONE":
	    			arr = obj.getJSONArray("Data");
	    			Users util = new Users();
    				util.setId(arr.getJSONObject(0).getInt("id"));
    				util.setNom(arr.getJSONObject(0).getString("nom"));
    				util.setPrenom(arr.getJSONObject(0).getString("prenom"));
    				util.setLogin(arr.getJSONObject(0).getString("login"));
    				util.setPwd(arr.getJSONObject(0).getString("pwd"));
    				util.setProfil(arr.getJSONObject(0).getInt("profil"));
    				users.add(util);
    				res = users.toString();
    				break;
	    		case "UPDATE" :
	    			String field = obj.getString("Field");
	    			String login = obj.getString("Login");
	    			if (obj.getString("Status").equals("Succed")){
	    				
	    				res = "Le " + field + " de l'utilisateur "+ login +" a été mis à jour avec avec succès";
	    			}else {
	    				res = "Le " + field + " de l'utilisateur "+ login +" n'a pas été mis à jour avec avec succès";
	    			}
	    			break;
	    		case "DELETE_ONE" :
	    			login = obj.getString("Login");
	    			if (obj.getString("Status").equals("Succed")){
	    				res = "L'utilisateur "+ login + " a été supprimé avec avec succès";
	    			}else {
	    				res = "L'utilisateur "+ login + " n'a pas été supprimé";
	    			}
	    			break;
	    			
	    		default:
	    			//
	    	}
	    	
			return res;
			
		}

	    public static void main(String[] args) throws UnknownHostException, IOException, JSONException {
	    	
	        CityClient client = new CityClient();
	        client.startConnection("172.31.249.22", 7000);
	       // client.afficheMenu();

	        
	     
	        
	        
	    }
	    

}
