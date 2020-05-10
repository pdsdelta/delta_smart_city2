package tram_line;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

class stationPlacement{
   static int nombre = 0; 
   public static void placement(int longueur, int largeur) {

   }
   public static int numberStation(int budgetCity, int budgetStation) {
	  nombre = Math.floorDiv(budgetCity, budgetStation);
	  System.out.println("le nombre de station est : " + nombre);
	  return nombre;
   }
    
}