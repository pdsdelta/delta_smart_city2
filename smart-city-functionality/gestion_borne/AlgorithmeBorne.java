package gestion_borne;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONException;
import org.json.JSONObject;

import borne.Terminal;
import functionality_server.functionalityServer;
import gestion_borne.crud.DAO;
import gestion_borne.crud.TerminalDAO;
/**
 * Cette classe est la classe qui contient l'algorithme de fonctionnement des bornes:
 * Action : Baisser ou Sortie pour faire entrer ou sortir une voiture de la ville
 * @author Annaelle Donfack
 *
 */
public class AlgorithmeBorne {
	public AlgorithmeBorne() throws ClassNotFoundException, SQLException {

	}
	functionalityServer server;
	TerminalDAO data=new TerminalDAO(server)  ;
	String toSend;
	//Variable contenant les voitures dans la ville
	static ArrayList<Objet> infoVoitures = new ArrayList <Objet>();
	//Variable comptant le nombre de voiture dans la ville
	static  int PlacesOccupees;
	int capacity =0;

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
			System.out.println("La borne s'est baisse, status: "+borne.getStatus());
			System.out.println("Alarme: "+borne.getCity().getAlert());
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

	public static ArrayList<Objet> getInfoVoitures() {
		return infoVoitures;
	}

	public static void setInfoVoitures(ArrayList<Objet> infoVoitures) {
		AlgorithmeBorne.infoVoitures = infoVoitures;
	}

	public static int getPlacesOccupees() {
		return PlacesOccupees;
	}

	public static void setPlacesOccupees(int placesOccupees) {
		PlacesOccupees = placesOccupees;
	}


}
