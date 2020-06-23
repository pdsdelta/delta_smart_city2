package gestion_borne.mock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import functionality_server.functionalityServer;
import gestion_borne.crud.TerminalDAO;


public class MotionSensorFonctionMock implements Runnable { 

	//Variable Socket pour communiquer avec le serveur
	private Socket clientSocket;
	//variable pour envoyer des donnees au serveur
	private PrintWriter out;
	private BufferedReader in;
	String json;
	public MotionSensorFonctionMock(){

	}
	//Methode pour commencer la communication avec le serveur 
	public void startConnection(String ip, int port) throws UnknownHostException, IOException {
		clientSocket = new Socket(ip, port);
		out=new PrintWriter(this.clientSocket.getOutputStream(), true);
		in=new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
		//Envoi de donnee au serveur
		out.println(json);
		System.out.println(json);
	}

	//Methode pour recuperer le json à envoyer
	public String JSON() throws InterruptedException{	
		JSONArray scenario=readScenario();
		Iterator<JSONObject> iterator =scenario.iterator();
		while(iterator.hasNext()) {
			JSONObject voiture = new JSONObject();
			voiture= iterator.next();
			//Get object voiture
			JSONObject voitureObject = (JSONObject) voiture.get("request");
			this.json=voiture.toString();

		}
		return json;
	}
	//Methode pour lire le fichier de simulation
	public JSONArray readScenario() {
		//JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();
		JSONArray voitureList = null;
		File file = new File(
				getClass().getClassLoader().getResource("CirculationVoiture.json").getFile()
				);
		try (FileReader reader = new FileReader(file))
		{
			//Read JSON file
			Object obj = jsonParser.parse(reader);
			voitureList = (JSONArray) obj;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return voitureList;

	}

	public void changeScenario() {
		JSONArray json=readScenario();
		Iterator<JSONObject> iterator =json.iterator();
		JSONObject voitureObject= null;
		while(iterator.hasNext()) {
			JSONObject voiture = new JSONObject();
			voiture= iterator.next();
			//Get object voiture
			voitureObject = (JSONObject) voiture.get("request");
			JSONObject voitureDetails1 = new JSONObject();
			voitureDetails1.put("operation_type", "sortir");
			voitureDetails1.put("numero",2);
			voitureObject.put("request", voitureDetails1);
		}

		//Write JSON file
		File file = new File(
				getClass().getClassLoader().getResource("AddTerminal.json").getFile()
				);
		try (FileWriter fichier = new FileWriter(file)) {
			fichier.write(voitureObject.toJSONString());
			fichier.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}


	}
	public void run(){ 
		try {
			while(true){
				this.JSON();
				this.startConnection( "172.31.249.22",2400);
				Thread.sleep((long)  (5000* Math.random()));
				
			}}
		catch (InterruptedException e) {
			e.printStackTrace();	} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}



	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		int nbVoitures=2; 
		Thread MesVoitures[] = new Thread[nbVoitures];

		for (int i =1; i< nbVoitures; i++){
			MesVoitures[i]= new Thread(new MotionSensorFonctionMock());
			MesVoitures[i].start();

		}


	}


}