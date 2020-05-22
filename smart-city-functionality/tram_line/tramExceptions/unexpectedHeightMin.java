package tram_line.tramExceptions;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

import tram_line.mapInterface;

public class unexpectedHeightMin extends Error{
    
	public unexpectedHeightMin() {
		mapInterface map = new mapInterface(5);
		JOptionPane messageTypeError;
		messageTypeError = new JOptionPane();
		messageTypeError.showMessageDialog(null, "Attention, vous devez entrer une longueur et une largeur supérieur à 0 km", "Attention", JOptionPane.WARNING_MESSAGE);
		System.out.println("exception de type mauvaise hauteur!");
	}
}