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

import functionality_server.functionalityServer;
import gestion_borne.crud.TerminalDAO;


public class MotionSensorFonctionMock implements Runnable { 
	

	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	String json;
	public MotionSensorFonctionMock(){
		
	}
	public void startConnection(String ip, int port) throws UnknownHostException, IOException {
		clientSocket = new Socket(ip, port);
		out=new PrintWriter(this.clientSocket.getOutputStream(), true);
		in=new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
		out.println(json);
		System.out.println(json);
	}

	public String rentrer() throws InterruptedException{	
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
	public JSONArray readScenario() {
		//JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();
		JSONArray voitureList = null;
		try (FileReader reader = new FileReader("E:\\CirculationVoiture.json"))
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
	public void run(){ 
		try {
			while(true){
				this.rentrer();
				this.startConnection( "172.31.249.22",2400);
				Thread.sleep((long)  (50000* Math.random()));
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