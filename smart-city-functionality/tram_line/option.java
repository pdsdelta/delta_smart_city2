package tram_line;

import java.awt.event.*;
import javax.swing.*;

import tram_line.tramExceptions.typeMapExceptions;

import java.awt.*;
import java.util.*;

class option{
    public static String monString(String dialog){
        return(JOptionPane.showInputDialog(dialog));
    }
    
    public static int  monInt(String dialog) throws typeMapExceptions{
        String x = JOptionPane.showInputDialog(dialog);
        try{
        int y = Integer.parseInt(x); 
        return(y); }
        catch (NumberFormatException e){
            System.out.println("Erreur ! il faut rentrer un nombre");
            throw new typeMapExceptions();
        }
    }
}