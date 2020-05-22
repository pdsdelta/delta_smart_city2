package tram_line.test;

import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.UnknownHostException;

import javax.swing.*;

import org.json.JSONException;
import org.postgresql.util.LruCache.CreateAction;
import org.yaml.snakeyaml.Yaml;

import tram_line.tramExceptions.typeMapExceptions;
import tram_line.transition;
import tram_line.mapInterface;
import tram_line.*;

import java.awt.*;
import java.util.*;
import java.util.*;

public class testFile{
	
	public testFile() throws UnknownHostException, IOException, JSONException {
		  mapInterface test = new mapInterface(10);
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
		  
		  
	}

	public static void main(String[]args) throws UnknownHostException, IOException, JSONException {
		testFile test = new testFile();
	}
}