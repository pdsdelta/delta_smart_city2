package gestion_borne.vue;

import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.json.JSONException;
import org.json.JSONObject;

import borne.Terminal;
import functionality_server.functionalityServer;
import gestion_borne.AlgorithmeBorne;
import gestion_borne.Objet;
import gestion_borne.crud.TerminalDAO;




public class TraficVue extends Observable {

	public TraficVue() throws ClassNotFoundException, SQLException {
	}


	functionalityServer server;
	TerminalDAO data=new TerminalDAO(server)  ;
	String toSend;
	int capacity =0;
	private int status;
	private int alarme;
	private int status1;
	private int alarme1;
	private int voiture;
	//Variable contenant les voitures dans la ville
	static ArrayList<Objet> infoVoitures = new ArrayList <Objet>();
	//Variable comptant le nombre de voiture dans la ville
	static  int PlacesOccupees;

	//Methode traitant les requêtes d'entrée dans la ville provenant du serveur
	public synchronized String TraitementEntrer(String json) throws JSONException, ClassNotFoundException, SQLException {
		String Receive = json;
		JSONObject request= new JSONObject(Receive);
		JSONObject objet= request.getJSONObject("request");
		Objet voiture= this.generateObjet(json);
		JSONObject requete= new JSONObject();
		JSONObject requeteDetails= new JSONObject();
		requeteDetails.put("operation_type", "entrer");

		requeteDetails.put("numeroBorne", voiture.getNumBorne());

		if(this.entrer(voiture)) {
			requeteDetails.put("status", "SUCCES");
			System.out.println("Une voiture vient d'entrer");
			voiture.run();
		}else {
			System.out.println("Une voiture a ete refuse d'acces");
			requeteDetails.put("status", "REJECT");
		}
		requete.put("request", requeteDetails);
		this.toSend=requete.toString();
		return this.toSend;
	}

	//Methode traitant les requêtes de sortie de la ville provenant du serveur
	public synchronized String TraitementSortie(String json) throws JSONException, ClassNotFoundException, SQLException {

		Objet voiture2= this.generateObjet(json);
		JSONObject requete2= new JSONObject();
		JSONObject requete2Details= new JSONObject();
		requete2Details.put("operation_type", "sortir");
		requete2Details.put("numeroBorne", voiture2.getNumBorne());
		requete2Details.put("status", "SUCCES");
		requete2.put("request", requete2Details);
		if(this.sortir()) {
			this.toSend=requete2.toString();
			System.out.println("Une voiture vient de sortir");
		}
		return this.toSend;
	}

	//Methode permettant de generer un objet provenant de la simulation du capteur envoyé par le serveur
	public Objet generateObjet(String json) throws JSONException {
		String receive= json;
		JSONObject request= new JSONObject(receive);
		JSONObject objet= request.getJSONObject("request");
		Objet objet1 = new Objet();
		objet1.setLatitude(objet.getInt("latitude"));
		objet1.setLongitude(objet.getInt("longitude"));
		objet1.setNumBorne( objet.getInt("numeroBorne"));
		return objet1;

	}

	//Methode qui renvoie le nombre de places de voiture encore disponible dans la ville
	int places() throws ClassNotFoundException, SQLException{ 
		Terminal terminal =data.find(1);
		int size= this.getCapacity();
		int nombre=terminal.getCity().getNombreMaxVoiture() - this.PlacesOccupees;
		return nombre; 
	}
	//Methode permettant de retrouver la borne par laquelle un objet veut circuler
	public Terminal FindTerminal(Objet t) throws ClassNotFoundException, SQLException {

		int numeroBorne= t.getNumBorne();
		Terminal t1= this.data.find(numeroBorne);
		return t1;
	}

	//Methode permettant de determiner si oui ou non un objet peut entrer dans la ville
	public  synchronized boolean entrer(Objet objet) throws ClassNotFoundException, SQLException{
		Terminal borne=this.FindTerminal(objet);
		boolean result=false;
		/*
		 * On verifie s'il le nombre de place disponibles dans la ville n'est pas inférieur ou egale à zero
		 * et aussi si une alerte n'a pas ete lancé
		 */
		if  ((this.places()>0 && borne.getCity().getAlert()==0) ){
			//On incremente le nombre de places Occupee
			this.PlacesOccupees++;
			//int size=this.PlacesOccupees.incrementAndGet();
			//this.setCapacity(size );
			//La borne se baisse: status=0;
			borne.setStatus(0);
			this.status1=0;
			this.setStatus(borne.getStatus());
			this.alarme1=borne.getCity().getAlert();
			System.out.println(borne.getNumero());
			this.data.update(borne);
			System.out.println("La borne s'est baisse, status: "+borne.getStatus());
			System.out.println("Alarme: "+borne.getCity().getAlert());
			this.setAlarme(borne.getCity().getAlert());
			//On ajoute l'objet au tableau d'objet de la ville
			this.infoVoitures.add(objet);
			System.out.format("[Borne %s]: Objet acceptée, il reste %d places \n", borne.getNumero(), this.places());
			System.out.println("Voiture Garees  :"+this.PlacesOccupees);

			result=true;

		}
		//Si aucune alerte n'a ete lancé ou le nombre maximal n'a pas encore ete atteint
		else if((this.places()>0 && borne.getCity().getAlert()==1)) {


			//Si l'une des conditions est constaté, on refuse l'acces de la voiture, la borne se lève
			borne.setStatus(1);
			this.status1=1;
			this.alarme1=borne.getCity().getAlert();
			this.setStatus(borne.getStatus());
			System.out.println(borne.getNumero());
			this.data.update(borne);
			this.setAlarme(borne.getCity().getAlert());
			System.out.println("La borne s'est leve, son status: "+borne.getStatus());
			System.out.println("Alarme: "+borne.getCity().getAlert());
			System.out.format("[Borne %s]: Objet refusée, il reste  %d places  \n", borne.getNumero(),this.places());



			result=false;
		}
		return result;
	}

	//Methode permettant de faire sortir un objet (voiture) de la ville
	public synchronized boolean sortir() throws JSONException, ClassNotFoundException, SQLException {
		Objet o= this.infoVoitures.get(1);
		//Decrementation du nombre de voiture dans la ville
		this.PlacesOccupees--;
		Terminal borne=this.FindTerminal(o);
		this.status1=1;
		borne.setStatus(0);
		this.setStatus(borne.getStatus());
		this.data.update(borne);
		//int size=this.PlacesOccupees.decrementAndGet();
		//this.setCapacity(size);
		//On retire la voiture du tableau
		this.infoVoitures.remove(o);
		System.out.format("Borne : Une voiture est sortie, reste  %d places\n" ,this.places());
		System.out.println("Voiture Garees  :"+this.PlacesOccupees);
		//System.out.format("Voiture Garees :\n"+this.infoVoitures.size());
		return true;

	}
	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public  ArrayList<Objet> getInfoVoitures() {
		return infoVoitures;
	}

	public  void setInfoVoitures(ArrayList<Objet> infoVoitures) {
		this.infoVoitures = infoVoitures;
	}

	public  int getPlacesOccupees() {
		return PlacesOccupees;
	}

	public  void setPlacesOccupees(int placesOccupees) {
		PlacesOccupees = placesOccupees;
	}


	public static void main(String[] args) throws InterruptedException, ClassNotFoundException, SQLException {
		TraficVue monModele = new TraficVue();
		Trafic maFenetre = new Trafic();

		MonObserveur monObserveur = new MonObserveur(maFenetre);
		monModele.addObserver(monObserveur);

		while (true) {
			monModele.incrementerMaVariable();
			//maFenetre.fenetre.removeAll();
			//maFenetre.fenetre.revalidate();
			//maFenetre.fenetre.repaint();
		}
	}


	public void incrementerMaVariable() {
		//status++;
		//alarme++;
		//voiture++;
			status=this.status1;
			alarme=this.alarme1;
			voiture=this.PlacesOccupees;
			setChanged();
			notifyObservers();
		
	}
	public int getStatus() {
		return this.status1;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getAlarme() {
		return this.alarme1;
	}

	public void setAlarme(int alarme) {
		this.alarme = alarme;
	}


	public int getVoiture() {
		return this.PlacesOccupees;
	}


	public static class Trafic  {
		JFrame fenetre = new JFrame();


		private javax.swing.JLabel vtrPresent;
		private javax.swing.JLabel alarme;
		private javax.swing.JLabel staBorne;
		functionalityServer server;
		//TerminalDAO data=new TerminalDAO(server)  ;
		String toSend;

		private JPanel contentPane;
		/**
		 * Create the frame.
		 * @throws SQLException 
		 * @throws ClassNotFoundException 
		 */
		public Trafic() throws ClassNotFoundException, SQLException {
			fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			fenetre.setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			fenetre.setContentPane(contentPane);
			contentPane.setLayout(null);

			vtrPresent = new JLabel("Voiture Present :");
			vtrPresent.setFont(new Font("Times New Roman", Font.BOLD, 14));
			vtrPresent.setBounds(27, 58, 141, 33);
			contentPane.add(vtrPresent);
			fenetre.setVisible(true);

			alarme = new JLabel("Alarme : ");
			alarme.setFont(new Font("Times New Roman", Font.BOLD, 14));
			alarme.setBounds(27, 133, 141, 13);
			contentPane.add(alarme);

			staBorne = new JLabel("Status Borne : ");
			staBorne.setFont(new Font("Times New Roman", Font.BOLD, 14));
			staBorne.setBounds(27, 193, 141, 13);
			contentPane.add(staBorne);
		}
		public void afficherAlarme(int variable) {
			alarme.setText("Alarme : "+String.valueOf(variable));
		}
		public void afficherStatus(int variable) {
			staBorne.setText("Status Borne : "+String.valueOf(variable));
		}
		public void afficherVoiture(int variable) {

			vtrPresent.setText("Voiture Present :"+String.valueOf(variable));
		}
	}

	public static class MonObserveur implements Observer {
		private Trafic fenetre;

		public MonObserveur(Trafic fenetre) {
			this.fenetre = fenetre;
		}

		@Override
		public void update(Observable o, Object arg) {
			fenetre.afficherStatus(((TraficVue) o).getStatus());
			fenetre.afficherAlarme(((TraficVue) o).getAlarme());
			fenetre.afficherVoiture(((TraficVue) o).getVoiture());
		}
	}


}


