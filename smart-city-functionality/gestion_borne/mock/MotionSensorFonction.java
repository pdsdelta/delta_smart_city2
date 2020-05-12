package gestion_borne.mock;

import java.io.IOException;

import org.json.simple.JSONArray;
/*
 * Cette classe est l'interface de fonctionnement d'un MotionSensor (detecteur de véhicule)
 * @author : DONFACK ANAELLE
 */
public interface MotionSensorFonction {
	
	public JSONArray readScenario();
	
	public void modifyScenario();
	
	public void sendResult(JSONArray i) throws IOException;
	
}

