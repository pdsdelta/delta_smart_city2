package capteur_air;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONException;

import district.District;

public class Testj {

	
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private Object jtext;

	public String startConnection(String ip, int port , String json) throws UnknownHostException, IOException, JSONException {
		String toSend = json;
		clientSocket = new Socket(ip, port);
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		out.println(toSend);
		String response = in.readLine();
		System.out.println("***** Résultat ******\n");
		return response;
	}
	
	District util = new District();
	public String addquartierj(String a, String b, String c, String d) throws UnknownHostException, IOException, JSONException {
		int id =Integer.parseInt(a); 
		String name = b;
		int seuilquartieratmo = Integer.parseInt(c);
		int etatalerte = Integer.parseInt(d);
	
	String json= "";

	util.setId(id);
	util.setName(name);
	util.setSeuilQuartierATMO(seuilquartieratmo);
	util.setEtatalterte(etatalerte);
	
	json  ="{request:{ operation_type: ADDQUARTIER, target: district , id: "+util.getId() + 
			", name: "+ util.getName() + ", seuilquartieratmo : "+ util.getSeuilQuartierATMO() + 
			", etatalerte : " + util.getEtatalterte() +"}} " ;
	this.startConnection("172.31.249.22", 2400,json);
	System.out.println("Nous allons enregistrer un par un les quartiers");
	return (String) json;
	}
}
