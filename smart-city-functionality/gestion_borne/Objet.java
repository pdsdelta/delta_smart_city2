package gestion_borne;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONException;
import org.json.simple.JSONObject;
/*
 * Cette classe est la classe permettant de construire les objets envoyés par un detecteur de vehicules
 * à travers le serveur
 * Author : DONFACK ANAELLE
 */

public class Objet implements Runnable{
	//Socket pour pouvoir communiquer avec le serveur
	private Socket clientSocket;
	private PrintWriter out;
	String json;

	private int longitude;
	private int latitude;
	//Variable du numero de borne par lequel l'objet circule
	private int numBorne;
	//Direction de l'objet
	private String direction;

	public Objet() {}
	public void startConnection(String ip, int port) throws UnknownHostException, IOException, JSONException {
		clientSocket = new Socket(ip, port);
		out=new PrintWriter(this.clientSocket.getOutputStream(), true);
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
	
	//Methode permettant à la simulation de capteur d'envoyer une requête de sortie de vehicule au serveur
	public synchronized String leave() {
		String jsone=" ";
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
		
		try {
			//le temps de sommeil dans la ville
			Thread.sleep((long)  (500000* Math.random()));
			this.leave();
			this.startConnection("127.0.0.1", 2400);
		} catch (InterruptedException | IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
