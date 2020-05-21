package gestion_borne;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONException;
import org.json.simple.JSONObject;

public class Objet implements Runnable{
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	String ReceiveJson;
	String json;

	private int longitude;
	private int latitude;
	private int numBorne;
	private String direction;

	public Objet() {}
	public void startConnection(String ip, int port) throws UnknownHostException, IOException, JSONException {
		clientSocket = new Socket(ip, port);
		out=new PrintWriter(this.clientSocket.getOutputStream(), true);
		in=new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
		out.println(json);
		System.out.println(json);
	}
	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public int getNumBorne() {
		return numBorne;
	}

	public void setNumBorne(int numBorne) {
		this.numBorne = numBorne;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	public synchronized String leave() {
		String jsone=" ";
		//this.setNumBorne(this.getNumBorne()+1);
		JSONObject voitureDetails = new JSONObject();
		voitureDetails.put("operation_type", "sortir");
		voitureDetails.put("longitude", this.getLongitude());
		voitureDetails.put("latitude", this.getLatitude());
		voitureDetails.put("numeroBorne", this.getNumBorne()+1);
		JSONObject voitureObject= null;
		voitureObject = new JSONObject(); 
		voitureObject.put("request", voitureDetails);
		this.json= voitureObject.toString();
		return jsone;
	}
	
	@Override
	public void  run() {
		//le temps de sommeil dans la ville
		try {
			Thread.sleep((long)  (500000* Math.random()));
			this.leave();
			this.startConnection("127.0.0.1", 2400);
		} catch (InterruptedException | IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
