package tram_line.tramExceptions;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

import tram_line.mapInterface;

public class errorNumberStation extends Error{
    
	public errorNumberStation() {
		mapInterface map = new mapInterface(0);
		JOptionPane messageTypeError;
		messageTypeError = new JOptionPane();
		messageTypeError.showMessageDialog(null, "Attention, vous ne pouvez pas générer un réseau avec une seule station ! ", "Attention", JOptionPane.WARNING_MESSAGE);
		System.out.println("exception de type format!");
	}
}