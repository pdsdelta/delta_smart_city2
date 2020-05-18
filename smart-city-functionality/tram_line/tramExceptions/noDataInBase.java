package tram_line.tramExceptions;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

import tram_line.mapInterface;

public class noDataInBase extends Error{
    
	public noDataInBase() {
		mapInterface map = new mapInterface(0);
		JOptionPane messageTypeError;
		messageTypeError = new JOptionPane();
		messageTypeError.showMessageDialog(null, "Il n'y a aucune ville en base ! \n merci d'enregistrer une ville", "Attention", JOptionPane.WARNING_MESSAGE);
		System.out.println("exception de type format!");
	}
}