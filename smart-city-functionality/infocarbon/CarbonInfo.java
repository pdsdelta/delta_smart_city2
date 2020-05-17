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
    private String toSend;
    private static CarbonInfo instance = null ;
    
    public void startConnection(String ip, int port) throws UnknownHostException, IOException, JSONException {
    	
    	clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        toSend =this.afficheMenuAndGetJson();
        //out.println(toSend);
        
	        //String response = in.readLine();
	        //System.out.println("***** Résultat ******\n");
	        //System.out.println(this.diplayResult(response));
        
    }
    
    private CarbonInfo() {
    	
    }
    
    public static CarbonInfo getInstance(){
    	if (instance == null) {
			instance = new CarbonInfo();
		}  
		return instance ;
    }
    
    
    public String sendMessage(String Json) throws IOException {
    	out.println(Json);
        //System.out.println(Json);
        String resp = in.readLine();
        //System.out.println(resp);
        return resp;
    }
    
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
    
    public String getToSend() {
		return toSend;
	}




	public void setToSend(String toSend) {
		this.toSend = toSend;
	}
	
    public String afficheMenuAndGetJson() {
    	String res = null;
    	CarbonMenu f = CarbonMenu.getInstance();
		f.setVisible(true);
		res = f.getJsonClient();
		if (res != null) {
			System.out.println("Le JSON final à envoyer au serveur") ;
			System.out.println(res);
			this.setToSend(res);
		} 
		
		return res;
		
	}
    
    
    public static void main(String[] args) throws UnknownHostException, IOException, JSONException {
    	
        CarbonInfo client = new CarbonInfo();
        client.afficheMenuAndGetJson();

        

        
     
        
        
    }




	
    
    
    
    
    
 
}
