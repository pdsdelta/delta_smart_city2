package gestion_borne.mock;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import functionality_server.functionalityServer;
import gestion_borne.crud.MotionSensorDAO;
import motionSensor.MotionSensor;
/*
 * Cette classe est l'implémentation de la classe MotionSensorFonction
 * Elle a pour but d'implementer toutes ses méthodes
 */

public class MotionSensorFonctionMock implements MotionSensorFonction, Runnable {
	private MotionSensor captor;
	private Thread MoveSensorThread;
	private Socket SensorClient;

	private PrintWriter out;

	//le numero du capteur
	private int numSensor;

	//variable pour recuperer le temps que la voiture fait dans la ville
	Long temps;
	//variable de recuperation de la direction de la voiture : "entree" ou "sortie"
	String direction;

	public MotionSensorFonctionMock(MotionSensor captor){
		this.captor= captor;
		this.numSensor = captor.getNumero();
		MoveSensorThread = new Thread(this);
		MoveSensorThread.start();
	}
	public void startConnection(String ip, int port) throws IOException, InterruptedException{
		try{
			//cree la socket du client avec l'ip du serveur avec lequel il doit communiquer
			SensorClient= new Socket(ip,port);
			//InputStream pour rÃ©cupÃ©rer la reponse du serveur dans un flux
			//Pour permettre au client d'Ã©crire au serveur
			this.out = new PrintWriter(SensorClient.getOutputStream(),true);
			String toSend="{request:{ operation_type: monter, temps: 11 }} ";
			out.println(toSend);
			}catch(UnknownHostException e){

			} 
		} 
		public void stopConnection() throws IOException{
			out.close();
			SensorClient.close();
		}

		@Override
		public void  sendResult(JSONArray scenario) throws IOException {
			scenario =readScenario();
			Iterator<JSONObject> iterator =scenario.iterator();
			while(iterator.hasNext()) {
				JSONObject voiture = new JSONObject();
				voiture= iterator.next();
				//Get object voiture
				JSONObject voitureObject = (JSONObject) voiture.get("voiture");
				try {
					String t = voitureObject.toString();
					//System.out.println(t);
					MotionSensorFonctionMock.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// TODO Auto-generated method stub

		}
		private static void sleep(int i) throws InterruptedException {
			// TODO Auto-generated method stub
			Thread.sleep(5000);
		}
		@Override
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

		@Override
		public void modifyScenario() {
			// TODO Auto-generated method stub

		}

		@Override
		public void run() {
			System.out.println("Le capteur numero "+numSensor+" se met en marche");
			try {
				while(true) {
					this.startConnection("172.31.249.22", 2400);
				}

			}catch(Exception e) {}
			// TODO Auto-generated method stub
		}
		public static void main(String args[]) throws ClassNotFoundException, SQLException, IOException, InterruptedException {
			functionalityServer server= new functionalityServer();
			MotionSensorDAO data= new MotionSensorDAO(server);
			MotionSensor captor=null;
			List<MotionSensor> listCaptor= data.getAll();
			for(int i=0;i<3;i++) {
				captor= listCaptor.get(i);
				MotionSensorFonctionMock simulateur = new MotionSensorFonctionMock(captor);
				//simulateur.stopConnection();
			}
		}

	}
