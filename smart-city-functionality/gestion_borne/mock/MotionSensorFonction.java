package gestion_borne.mock;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
/*
 * Cette classe est l'interface de fonctionnement d'un MotionSensor (detecteur de v�hicule)
 * @author : DONFACK ANAELLE
 */
public interface MotionSensorFonction {
	//Methode qui permet de lire le fichier scenario 
	public JSONArray readScenario();
	
	
}

