package analyse_indicateur;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.*;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import tram_line.tramExceptions.typeMapExceptions;
import tram_line.*;
import tram_line.test.tram_line.test.testFile;

import java.awt.*;
import java.util.*;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import transportation.PublicTransport;
import functionality_server.functionalityServer;
import gestion_borne.crud.MotionSensorDAO;
import motionSensor.MotionSensor;
import station.station;



public class TestNombreStation {
	static station data;
	static functionalityServer server;
	public TestNombreStation() {}
	public void constructObject() throws ClassNotFoundException, UnknownHostException, IOException, JSONException {
		  System.out.println("Scenario 1: We are going to set values that a user could have entered with the interface to observe if the program works well. \n We will also test the station placement program to see if it ends without errors.");
		  JSONArray request = readScenario();
		  
	        //Iterate over employee array
	        request.forEach( voit -> {
				try {
					parseVoitureObject( (JSONObject) voit );
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} );
			
	}	  
	private static void parseVoitureObject(JSONObject requete) throws ClassNotFoundException, SQLException 
    {
		//Get employee object within list
        JSONObject borne = (JSONObject) requete.get("request");
         
        //Get nombreStation 
        long nombreStation=  (Long) borne.get("nombreStation");    
       
         
        //Get numberTram
        long numberTram =  (Long) borne.get("numberTram"); 
       
        
        int nombreStation = (int)nombreStation ;
        int numberTram =(int)numberTram;
       
        station objet = new station(nombreStation,numberTram);
        data= new station(server);
		data.create(objet);
       
		
    }
	public JSONArray readScenario() {
		//JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();
		JSONArray station = null;
		try (FileReader reader = new FileReader("C:\\Users\\etudiant\\Desktop\\ActiveMotionSensor.json"))
		{
			//Read JSON file
			Object obj = jsonParser.parse(reader);
			station = (JSONArray) obj;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return station;

	}
	public static void main1 (String args[]) throws UnknownHostException, IOException, JSONException {
		TestNombreStation  test = new TestNombreStation();
	     try {
			test.constructObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}


		/*  mapInterface test = new mapInterface(10);
		  transition.longueur1 = 800;
		  transition.largeur1 = 600;
		  transition.mapTaille1 = 84;
		  transition.nameCity = "Paris";
		  transition. budgetCity1 = 1000000;
		  transition.budgetStation1 = 10000;
		  transition.nombreStation1 = 100;
		  transition.numberLine1 = 4;
		  transition.numberTram1 = 50;
		  transition.longueurReseau1 = 250;
		  
		  createMapSave test_algo= new createMapSave(transition.longueur1, transition.largeur1, transition.nombreStation1);
		  test.startConnection("172.31.249.22", 2400, 1);
		  test.startConnection("172.31.249.22", 2400, 3);
		  test.startConnection("172.31.249.22", 2400, 2);
		  test.startConnection("172.31.249.22", 2400, 4);
		  
		  System.out.println("If this message is displayed, the program has worked well. \n So the program correctly saved the data in the database and recovered it well. \n In addition, the algorithm is finished without errors.");
	}

	public static void main(String[]args) throws UnknownHostException, IOException, JSONException {
		testFile test = new testFile();
	}
	*/

	

}
