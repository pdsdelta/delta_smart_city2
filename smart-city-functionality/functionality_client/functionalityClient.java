package functionality_client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.json.JSONException;

import infocarbon.CarbonInfo;
import tram_line.mapInterface;
//import capteur_air.myCapteur;



public class functionalityClient extends JFrame{
	private JPanel pan = new JPanel();
	private JButton boutonCity = new JButton("Fonctionnalité Réseau de transport");
	private JButton boutonCarbon = new JButton("Fonctionnalité Empreinte carbonne");
	private JButton boutonAircapteur = new JButton("Fonctionnalitee Qualit� d'air");
	
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
	   pan.add(boutonAircapteur);
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
			        System.out.println("Lancement de l'écran carbonne info");
			        CarbonInfo client = CarbonInfo.getInstance();
			        try {
						client.startConnection("172.31.249.22", 2400);
					} catch (IOException | JSONException e) {
						e.printStackTrace();
						System.out.println("Echec de connexion");
					}
			        
			      }        
			    });
	   
	}
	
	 //Fontionalitee Julien
//	   boutonAircapteur.addActionListener(new ActionListener(){
//		      public void actionPerformed(ActionEvent arg0) {
//			        System.out.println("Ouverture de la page de qualit� d'air ");
//			        myCapteur client = new myCapteur();
//			        client.afficheMenuAndGetJson();
//			      }        
//			    });
//	}
//	

	//Fonctionalitee hiba UPDATE
	/*boutonAnalyseIndicateur.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent arg0) {
		Page1 page = new Page1();
		page.affichePage1();
		
	}
	});*/
	

	
	
	//Fontionalit� Martin 
	public void chargeTramFonctionality() throws UnknownHostException, IOException, JSONException{
		this.dispose();
		mapInterface map = new mapInterface(0);
	    map.startConnection("172.31.249.22", 2400, 0); //Adresse + port serveur commun nom jar : delta_smart_city2_functionality
	}
	
	public static void main(String[]args) {
		functionalityClient fonction = new functionalityClient();
	}
}

