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
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;

import usefull.Order;
import user.Users;


public class CityClient {
	    private Socket clientSocket;
	    private PrintWriter out;
	    private BufferedReader in;
	    
	    
	    public void startConnection(String ip, int port) throws UnknownHostException, IOException {
	    	String toSend;
	    	clientSocket = new Socket(ip, port);
	        out = new PrintWriter(clientSocket.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	        toSend =this.afficheMenu();
	        out.println(toSend);
	        String response = in.readLine();
	        System.out.println(response);
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
	    public static void main(String[] args) throws UnknownHostException, IOException {
	    	
	        CityClient client = new CityClient();
	        client.startConnection("172.31.249.22", 7000);
	       // client.afficheMenu();

	        
	     
	        
	        
	    }
	    

}
