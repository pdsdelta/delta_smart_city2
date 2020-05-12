package gestion_borne.mock;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import motionSensor.MotionSensor;
/*
 * Cette classe est l'implémentation de la classe MotionSensorFonction
 * Elle a pour but d'implementer toutes ses méthodes
 */

public class MotionSensorFonctionMock implements MotionSensorFonction, Runnable {
	private MotionSensor captor;
	private Thread MoveSensorThread;
	private Socket SensorClient;

	private OutputStreamWriter out;
	//le numero du capteur
	private int numSensor;

	//variable pour recuperer le temps que la voiture fait dans la ville
	Long temps;
	//variable de recuperation de la direction de la voiture : "entree" ou "sortie"
	String direction;

	public MotionSensorFonctionMock(Socket server, MotionSensor captor){
		this.captor= captor;
		this.numSensor = captor.getNumero();
		this.SensorClient= server;

		try {
			out= new OutputStreamWriter(server.getOutputStream(), StandardCharsets.UTF_8);
		}catch(IOException e ) {

		}
		MoveSensorThread = new Thread(this);
		MoveSensorThread.start();
	}

	@Override
	public void sendResult(JSONArray scenario) throws IOException {
		scenario =readScenario();
		Iterator<JSONObject> iterator =scenario.iterator();
		while(iterator.hasNext()) {
			JSONObject voiture = new JSONObject();
			voiture= iterator.next();
			//Get object voiture
			JSONObject voitureObject = (JSONObject) voiture.get("voiture");

			try {
				String t = voitureObject.toString();
				System.out.println(t);
				out.write(voitureObject.toString());
				MoveSensorThread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// TODO Auto-generated method stub

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
				JSONArray scenario= new JSONArray();
				sendResult(scenario);
			}

		}catch(Exception e) {}
		// TODO Auto-generated method stub
	}

}
