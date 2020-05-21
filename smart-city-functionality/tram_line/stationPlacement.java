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
	  if (nombre > 300) {
			JOptionPane messageTypeError;
			messageTypeError = new JOptionPane();
			messageTypeError.showMessageDialog(null, "Attention, dans le but d'optmiser au maximum les performances de l'application, il est impossible de générer un réseau de plus de 300 stations \n Le nombre de station dans le réseau a donc été ramené à 300 ! ", "Attention", JOptionPane.WARNING_MESSAGE);
			System.out.println("exception de type station max 300!");
			nombre = 300;
	  }
	  System.out.println("le nombre de station est : " + nombre);
	  return nombre;
   }
    
}