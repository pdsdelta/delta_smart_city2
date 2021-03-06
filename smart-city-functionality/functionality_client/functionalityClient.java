
package functionality_client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.json.JSONException;

import analyse_indicateur.fenetre1;
import gestion_borne.vue.GestionMotionSensorVue;
import gestion_borne.vue.GestionTerminalVue;
import gestion_borne.vue.MotionSensorVue;
import gestion_borne.vue.TerminalVue;
import infocarbon.CarbonInfo;
import tram_line.mapInterface;
import capteur_air.myCapteur;



public class functionalityClient extends JFrame{
	private JPanel pan = new JPanel();
	private JButton boutonCity = new JButton("Fonctionnalité Réseau de transport");
	private JButton boutonCarbon = new JButton("Fonctionnalité Empreinte carbonne");
	private JButton boutonAircapteur = new JButton("Fonctionnalitee Qualit� d'air");
	private JButton boutonAnalyseIndicateur  = new JButton("Fonctionnalitee Analyse_indicateur");
	private JButton boutonTerminal = new JButton("Configuration Borne Retractable");
	private JButton boutonMotionSensor = new JButton("Configuration Detecteur de vehicule");

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
		pan.add(boutonAnalyseIndicateur);
		pan.add(boutonTerminal);
		pan.add(boutonMotionSensor);

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
		//Fontionalitee Julien
		boutonAircapteur.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("j'affiche le menu de qualit� d'air ");
				try {
					myCapteur a = new myCapteur();
				} catch (IOException | JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}        
		});



		//Fonctionalitee hiba UPDATE

		boutonAnalyseIndicateur.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Affichafe des statistiques");
				fenetre1 page = new fenetre1();
				page.affichePage1();

			}
		});
		//Fonctionnalit� Anaelle : Gestion des bornes Retractables
		boutonTerminal.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Lancement de l'ecran de configuration des bornes retractables");
				try {
					chargeBorneFonctionality();
				} catch (IOException | JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}        
		});
		//Fonctionnalit� Analle: Gestion des detecteurs de vehicules
		boutonMotionSensor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					chargeMotionSensorFonctionality() ;
				} catch (IOException | JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} 
		});

	}
	//Fontionalit� Martin 
	public void chargeTramFonctionality() throws UnknownHostException, IOException, JSONException{
		this.dispose();
		mapInterface map = new mapInterface(5);
		map.startConnection("172.31.249.22", 2400, 0);
	}

	public void chargeBorneFonctionality() throws UnknownHostException, IOException, JSONException{
		
		GestionTerminalVue affichage = null;
		try {
			affichage = new GestionTerminalVue();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] args = null;
		affichage.main(args);
	}

	public void chargeMotionSensorFonctionality() throws UnknownHostException, IOException, JSONException{
		
		GestionMotionSensorVue affichage = null;
		try {
			affichage = new GestionMotionSensorVue();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] args = null;
		affichage.main(args);
	}

	public static void main(String[]args) {
		functionalityClient fonction = new functionalityClient();
	}
}


