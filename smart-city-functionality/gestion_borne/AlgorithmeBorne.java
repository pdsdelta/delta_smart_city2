package gestion_borne;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONException;
import org.json.JSONObject;

import borne.Terminal;
import functionality_server.functionalityServer;
import gestion_borne.crud.DAO;
import gestion_borne.crud.TerminalDAO;

public class AlgorithmeBorne {
	public AlgorithmeBorne() throws ClassNotFoundException, SQLException {

	}
	functionalityServer server;
	TerminalDAO data=new TerminalDAO(server)  ;
	String toSend;
	public static HashSet<Objet> infoVoitures = new HashSet<Objet>();
	public static AtomicInteger PlacesOccupees= new AtomicInteger();


	public void setCapacity(){ 

	} 
	public synchronized String TraitementEntrer(String json) throws JSONException, ClassNotFoundException, SQLException {
		String Receive = json;
		JSONObject request= new JSONObject(Receive);
		JSONObject objet= request.getJSONObject("request");
		Objet voiture= this.generateObjet(json);
		JSONObject requete= new JSONObject();
		JSONObject requeteDetails= new JSONObject();
		requeteDetails.put("operation_type", "entrer");
		requeteDetails.put("longitude", voiture.getLongitude());
		requeteDetails.put("latitude", voiture.getLatitude());
		requeteDetails.put("numeroBorne", voiture.getNumBorne());

		if(this.entrer(voiture)) {
			requeteDetails.put("status", "SUCCES");

		}else {
			requeteDetails.put("status", "REJECT");
		}
		requete.put("request", requeteDetails);
		this.toSend=requete.toString();
		voiture.run();
		System.out.println(this.toSend);
		return this.toSend;
	}
	public synchronized String TraitementSortie(String json) throws JSONException, ClassNotFoundException, SQLException {

		Objet voiture2= this.generateObjet(json);
		JSONObject requete2= new JSONObject();
		JSONObject requete2Details= new JSONObject();
		requete2Details.put("operation_type", "entrer");
		requete2Details.put("longitude", voiture2.getLongitude());
		requete2Details.put("latitude", voiture2.getLatitude());
		requete2Details.put("numeroBorne", voiture2.getNumBorne());
		requete2Details.put("status", "SUCCES");
		if(this.sortir()) {
			this.toSend=requete2.toString();

			return this.toSend;
		}else return null;
	}

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

	int places() throws ClassNotFoundException, SQLException{ 
		Terminal terminal =data.find(1);
		int size= this.PlacesOccupees.get();
		return (terminal.getCity().getNombreMaxVoiture() - size); 
	}

	public Terminal FindTerminal(Objet t) throws ClassNotFoundException, SQLException {

		int numeroBorne= t.getNumBorne();
		Terminal t1= this.data.find(numeroBorne);
		return t1;
	}

	public  synchronized boolean entrer(Objet objet) throws ClassNotFoundException, SQLException{
		Terminal borne=this.FindTerminal(objet);
		if  ((this.places() <=0 || borne.getCity().getAlert()==1 ) ){
			synchronized(this) {
				borne.setStatus(1);
				System.out.println(this.PlacesOccupees);
				System.out.println("La borne s'est leve, son status: "+borne.getStatus());
				System.out.println("Alarme: "+borne.getCity().getAlert());
				System.out.format("[Borne %s]: Objet refusée, il reste  %d places  \n", borne.getNumero(),this.places());
				return(false) ;
			}
		}
		else  {
			synchronized(this) {

				this.PlacesOccupees.incrementAndGet() ;
				borne.setStatus(0);
				System.out.println("La borne s'est baisse, status: "+borne.getStatus());
				System.out.println("Alarme: "+borne.getCity().getAlert());
				this.infoVoitures.add(objet);
				System.out.format("[Borne %s]: Objet acceptée, il reste %d places \n", borne.getNumero(), this.places());
				System.out.format("Voiture Garees  :\n"+this.infoVoitures.size());
				System.out.println(this.infoVoitures.size());
				return (true) ; 
			}
		}
	}
	public synchronized boolean sortir() throws JSONException, ClassNotFoundException, SQLException {

		this.PlacesOccupees.decrementAndGet();
		this.infoVoitures.remove(1) ;
			System.out.format("Borne : Une voiture est sortie, reste  %d places\n" ,this.places());
			//System.out.format("Voiture Garees :\n"+this.infoVoitures.size());

			return true;
		
	}

}
