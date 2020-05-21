package tram_line.tramExceptions;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

import tram_line.mapInterface;

public class errorBudgetExceptions extends Error{
    
	public errorBudgetExceptions() {
		mapInterface map = new mapInterface(5);
		JOptionPane messageTypeError;
		messageTypeError = new JOptionPane();
		messageTypeError.showMessageDialog(null, "Attention, le prix d'une station est supérieur au budget de la ville", "Attention", JOptionPane.WARNING_MESSAGE);
		System.out.println("exception de type budget non validé !");
	}
}