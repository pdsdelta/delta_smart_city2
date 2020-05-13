package functionality_client;

import java.awt.event.*;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.swing.JFrame;

import org.json.JSONException;

import infocarbon.CarbonInfo;
import infocarbon.CarbonMenu;
import tram_line.mapInterface;



public class functionalityClient extends JFrame{
	private JPanel pan = new JPanel();
	private JButton boutonCity = new JButton("Fonctionnalit� R�seau de transport");
	private JButton boutonCarbon = new JButton("Fonctionnalité Empreinte carbonne");
	
	public functionalityClient() {
	   this.setTitle("Delta Smart City");
	   Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	   int x = (int)tailleEcran.getHeight();
	   int y = (int)tailleEcran.getWidth();
	   this.setSize(x,y);
	   this.setLocationRelativeTo(null);
	   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   
	   //ajout bouton fonctionnalit�
	   pan.add(boutonCity);
	   pan.add(boutonCarbon);
	   this.getContentPane().add(pan);
	   
	   this.setVisible(true);
	
	   // les actions
	   
	   
	 //Fontionalit� Martin
	   boutonCity.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent arg0) {
		        System.out.println("j'affiche les informations ");
		        try {
					chargeTramFonctionality();
				} catch (IOException | JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		      }        
		    });
	 //Fontionalité Yacine
	   //Listener on the button boutonCarbon which open CarbonMenu
	   boutonCarbon.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent arg0) {
			        System.out.println("Lancement de l'écran carbonne");
			        CarbonInfo client = new CarbonInfo();
			        client.afficheMenuAndGetJson();
			      }        
			    });
	}
	
	//Fontionalit� Martin 
	public void chargeTramFonctionality() throws UnknownHostException, IOException, JSONException{
		mapInterface map = new mapInterface(0);
	    map.startConnection("172.31.249.22", 2400, 0); // serveur perso provisoire
	}
	
	public static void main(String[]args) {
		functionalityClient fonction = new functionalityClient();
	}
}

