package tram_line.tramExceptions;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

import tram_line.mapInterface;

public class formatCityExceptions extends Error{
    
	public formatCityExceptions() {
		mapInterface map = new mapInterface(0);
		JOptionPane messageTypeError;
		messageTypeError = new JOptionPane();
		messageTypeError.showMessageDialog(null, "Attention, la longueur doit être supérieur à la largeur", "Attention", JOptionPane.WARNING_MESSAGE);
		System.out.println("exception de type format de ville!");
	}
}