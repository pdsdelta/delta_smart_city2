package tram_line.tramExceptions;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

import tram_line.mapInterface;

public class typeMapExceptions extends Error{
    
	public typeMapExceptions() {
		mapInterface map = new mapInterface(0);
		JOptionPane messageTypeError;
		messageTypeError = new JOptionPane();
		messageTypeError.showMessageDialog(null, "Attention, vous devez rentrer un nombre", "Attention", JOptionPane.WARNING_MESSAGE);
		System.out.println("exception de type format!");
	}
}