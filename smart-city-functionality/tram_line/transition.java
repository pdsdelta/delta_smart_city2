package tram_line;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

class transition{
  static int longueur1;
  static int largeur1;
  static double mapTaille1;
  static String nameCity;
  static int budgetCity1;
  static int budgetStation1;
  static int nombreStation1;
  static int numberLine1;
  
  public static void saveLlt(int longueur, int largeur, double mapTaille, int budgetStation, int nombreStation){
	  longueur1 = longueur;
	  largeur1 = largeur;
	  mapTaille1 = mapTaille;
	  budgetStation1 = budgetStation;
	  nombreStation1 = nombreStation;
	  System.out.println(longueur1 + largeur1 + mapTaille1 + budgetStation1 + nombreStation1 + " save long" );
  }
  public static void saveBT(String nomVille, int budgetCity){
	  nameCity = nomVille;
	  budgetCity1 = budgetCity;
	  System.out.println(nameCity + budgetCity1 + " save name");
  }
  public static void saveLine(int numberLine) {
	  numberLine1 = numberLine;
	  System.out.println(numberLine1  + " save line");
  }
}