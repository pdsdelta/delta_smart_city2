package gestion_borne.mock;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

import borne.Terminal;
import functionality_server.functionalityServer;
import gestion_borne.crud.TerminalDAO;


public class MotionSensorFonctionMock implements Runnable { 
	String nom;
	int longitude;
	int latitude;
	Terminal park;
	String json;

	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	public MotionSensorFonctionMock(String name, Terminal park){
		this.nom=name; 
		this.park=park; 
		this.longitude=8;
		this.latitude=7;
	}

	public MotionSensorFonctionMock(int longitude,int latitude){
		this.longitude=longitude;
		this.latitude=latitude;
	}
	public void startConnection(String ip, int port) throws UnknownHostException, IOException {
		clientSocket = new Socket(ip, port);
		out=new PrintWriter(this.clientSocket.getOutputStream(), true);
		in=new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
		out.println(json);
		System.out.println(json);
	}
	public String toString(){ return this.nom;} 

	public String rentrer() throws InterruptedException{	
		while (!(this.park.accept(this)))
		{
			//le temps que la voiture demeure hors de la ville
			Thread.sleep((long)  (1000* Math.random()));
			//Après ce temps de sommeil il redemande à rentrer
			System.out.format("[%s]  : Je redemande à rentrer  \n", this.nom);
		}
		JSONArray scenario=readScenario();
		Iterator<JSONObject> iterator =scenario.iterator();
		while(iterator.hasNext()) {
			JSONObject voiture = new JSONObject();
			voiture= iterator.next();
			//Get object voiture
			JSONObject voitureObject = (JSONObject) voiture.get("request");
			json=voiture.toString();
		}
		
		return json;
	}
	public JSONArray readScenario() {
		//JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();
		JSONArray voitureList = null;
		try (FileReader reader = new FileReader("E:\\CirculationVoiture.json"))
		{
			//Read JSON file
			Object obj = jsonParser.parse(reader);
			voitureList = (JSONArray) obj;
			System.out.println(voitureList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return voitureList;

	}
	public void run(){ 
		System.out.format("[%s]: Je débute !  \n", this.nom);
		try {

			while(true){
				Thread.sleep((long)  (50000* Math.random()));
				System.out.format("[%s]: Je demande à rentrer  \n", this.nom);
				this.rentrer();
				System.out.format("[%s]: Je viens d'entrer \n", this.nom);
				//le temps de sommeil dans la ville
				Thread.sleep((long)  (50000* Math.random()));
				System.out.format("[%s]: Je demande à sortir  \n", this.nom);
				json=this.park.leave(this);  
                this.startConnection( "127.0.0.1",2400);
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

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		functionalityServer server = new functionalityServer();
		TerminalDAO data= new TerminalDAO(server);
		int nbVoitures=15; 

		Terminal t= data.find(1);
		Thread MesVoitures[] = new Thread[nbVoitures];
		Thread MesVoiture[] = new Thread[nbVoitures];
		for (int i =0; i< nbVoitures; i++){
			MesVoitures[i]= new Thread(new MotionSensorFonctionMock(String.format("Voit %d ", i) , t));
			MesVoiture[i]= new Thread(new MotionSensorFonctionMock(String.format("Voit %d ", i) , t));
			MesVoitures[i].start();
			MesVoiture[i].start();
		}
		

	}


}