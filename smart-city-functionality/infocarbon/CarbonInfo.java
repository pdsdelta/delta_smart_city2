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
    
    public String afficheMenuAndGetJson() {
    	CarbonMenu f = new CarbonMenu();
		f.setVisible(true);
		String res = f.getJsonClient();
    	return res;

    
	
 }
    public static void main(String[] args) throws UnknownHostException, IOException, JSONException {
    	
        CarbonInfo client = new CarbonInfo();
        client.afficheMenuAndGetJson();

        
     
        
        
    }
    
    
    
    
    
 
}
