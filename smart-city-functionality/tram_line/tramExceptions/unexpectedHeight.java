package tram_line.tramExceptions;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

import tram_line.mapInterface;

public class unexpectedHeight extends Error{
    
	public unexpectedHeight() {
		mapInterface map = new mapInterface(5);
		JOptionPane messageTypeError;
		messageTypeError = new JOptionPane();
		messageTypeError.showMessageDialog(null, "Attention, vous devez entrer une longueur et une largeur en km inférieur à 36 km", "Attention", JOptionPane.WARNING_MESSAGE);
		System.out.println("exception de type mauvaise hauteur!");
	}
}