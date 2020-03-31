package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class CityClient {
	    private Socket clientSocket;
	    private PrintWriter out;
	    private BufferedReader in;
	 
	    public void startConnection(String ip, int port) throws UnknownHostException, IOException {
	        clientSocket = new Socket(ip, port);
	        out = new PrintWriter(clientSocket.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	        PrintWriter pw = new PrintWriter(out);
			pw.println("Bonjour CityServer");
			pw.flush();
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
	    

	
	    public static void main(String[] args) throws UnknownHostException, IOException {
	        CityClient client = new CityClient();
	        client.startConnection("172.31.249.22", 7000);
	        String response = client.sendMessage("Bonjour CityServer");
	     
	        
	        
	    }
	    

}
