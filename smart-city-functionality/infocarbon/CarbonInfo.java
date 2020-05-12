package infocarbon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonProcessingException;

import client.CityClient;
import usefull.Order;
import user.Users;

public class CarbonInfo {
	private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    
    public void startConnection(String ip, int port) throws UnknownHostException, IOException, JSONException {
    	String toSend;
    	clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        while(true) {
        	//toSend =this.afficheMenu();
	        //out.println(toSend);
	        //String response = in.readLine();
	        //System.out.println("***** Résultat ******\n");
	        //System.out.println(this.diplayResult(response));
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
		System.out.println("*  Carbon Info of Delta City  *");
		System.out.println("********** \n");
    	System.out.println("**********");
		System.out.println("*  MENU  *");
		System.out.println("********** \n");
		System.out.println("[ 1 ] Calculer l'empreinte carbonne globale de la ville pour la date d'hier \n"+
				"[ 2 ] Calculer l'empreite carbonne globale de la ville une date souhaitée \n"+
				"[ 3 ] Calculer l'empreinte carbonne pour les transports publics de la ville pour une date souhaitée\n"+
				"[ 4 ] Calculer l'empreinte carbonne pour les transports privées de la ville pour une date souhaitée\n"+
				"[ 5 ] Estimer l'empreinte carbonne\n" +
				"[ 6 ] Quitter\n\n");
		
		Scanner sc = new Scanner(System.in);
		//PrintWriter pw = new PrintWriter(out);
		String json = "NON";
		boolean b = true;
		try {
			while(b==true) {
				int a = sc.nextInt();
				a=1;
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
					case 6:
						System.exit(0);
						break;
					default :
						ord = new Order(7,u);
						json = ord.generateJson();
						
					}
				return json ;
				
			}
	} catch (JsonProcessingException e) {

	}
		return json; 
    
	
 }
    public static void main(String[] args) throws UnknownHostException, IOException, JSONException {
    	
        CarbonInfo client = new CarbonInfo();
        client.afficheMenu();

        
     
        
        
    }
    
    
    
    
    
 
}
