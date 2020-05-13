package tram_line;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import connectionPool.DataSource;
import functionality_client.functionalityClient;
import station.station;
import user.Users;
import city.city;

public class mapInterface extends JFrame {
  //create menu
  private JMenuBar menuBar = new JMenuBar();
  private JMenu test1 = new JMenu("Generer une carte");
  private JMenu test2 = new JMenu("Afficher");
  //create item menu
  private JMenuItem item1 = new JMenuItem("Nouvelle carte");
  private JMenuItem item2 = new JMenuItem("Fermer");
  private JMenuItem item3 = new JMenuItem("Carte");
  private JMenuItem item4 = new JMenuItem("informations sur la ville");
  private JMenuItem item5 = new JMenuItem("informations sur les stations");
  //create a for constructor
  private int a = 0;
  //create budget of the city
  private int budgetCity;
  //create name of the city
  private String nomVille;
  //connection socket
  private Socket clientSocket;
  private PrintWriter out;
  private BufferedReader in;
  

  public void startConnection(String ip, int port,int a) throws UnknownHostException, IOException, JSONException {
  	  String toSend;
  	  int alpha = a;
  	  clientSocket = new Socket(ip, port);
      out = new PrintWriter(clientSocket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      if (alpha == 1) {		
    	  toSend = this.addSetCity();
    	  System.out.print("je vais enregistrer la ville");
	  	  out.println(toSend);
      }else if(alpha == 2) {
    	  toSend = this.getInformations();
    	  System.out.println("je vais recuperer des informations sur la ville");
	  	  out.println(toSend);
    	  String response = in.readLine();
	      System.out.println("***** Resultat ******\n");
	      System.out.println(this.showResultCity(response));
      }else if (alpha ==3) {
    	  toSend = this.addSetStation();
    	  System.out.println("Je vais enregistrer des informations sur les stations");
    	  out.println(toSend);
      }else if (alpha ==4) {
    	  toSend = this.getInfoStation();
    	  System.out.println("je vais r�cup�rer des informations sur les stations");
    	  out.println(toSend);
    	  String response = in.readLine();
	      System.out.println("***** Resultat ******\n");
	      System.out.println(this.showResultStation(response));
      }
  }
      
  
  

  public mapInterface(int a){
	
	
	Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    //Informations de Jframe et de la ville (budget et nom)
	int x = (int)tailleEcran.getHeight();
    int y = (int)tailleEcran.getWidth();
    this.setSize(x,y);
    
    if (a == 1) {
      nomVille = option.monString("Veuillez entrer le nom de la ville : ");
      this.setTitle("Carte de " + nomVille);
      budgetCity = option.monInt("Veuillez entrer le budget de la ville : ");
      transition.saveBT(nomVille,budgetCity);
      } else{
      this.setTitle("Carte de " + transition.nameCity);
    }
    setIconImage((new ImageIcon("delta-logo.png")).getImage());

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);

    //On initialise nos menus      
    this.test1.add(item1);
    this.test1.addSeparator();
    
    //action item2
    item2.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
    	close();
      }        
    });
    
    //action item1
    item1.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
        System.out.println("action generer et enregistrer");
        try {
        	generateMapUnSave();
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

      }        
    });
    
    //action item3
    item3.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
    	System.out.println("j'affiche la carte");
    	try {
			generateMapSave();
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }        
    });
    
    //action item4
    item4.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
        System.out.println("j'affiche les informations sur la ville");
        try {
        	generateInformationCity();
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }        
    });
    
  //action item5
    item5.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent arg0) {
          System.out.println("j'affiche des informations sur les stations");
          try {
			generateInformationStation();
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }        
      });
    
    //on ajoute les items au menu
    this.test1.add(item2);  
    this.test2.add(item3);
    this.test2.add(item4);
    this.test2.add(item5);
    this.menuBar.add(test1);
    this.menuBar.add(test2);
    this.setJMenuBar(menuBar);
    
    
    
    if (a == 1){
    System.out.println("je genere la carte en appelant createMapUnSave");
    this.add(new createMapUnSave());
    }
    if (a == 2){
    System.out.println("je genere la carte en appelant createMapSave");
    this.add(new createMapSave(transition.longueur1, transition.largeur1, transition.nombreStation1));
    }
    if (a == 3){
    this.setContentPane(showInformationCity());
    }
    if (a == 4) {
    this.setContentPane(showInformationStation());	
    }
    this.setVisible(true);

  }
  
  
  
  public void generateMapUnSave() throws UnknownHostException, IOException, JSONException{
    System.out.println("generateMapUnSave action");
    a = 1;
    this.dispose();
    mapInterface map1 = new mapInterface(1);
    map1.startConnection("172.31.249.22", 2400, 1);
    map1.startConnection("172.31.249.22", 2400, 3);
  }
  public void generateMapSave() throws UnknownHostException, IOException, JSONException{
    System.out.println("generateMapSave action");
    a = 2;
    this.dispose();
    mapInterface map2 = new mapInterface(0);
    map2.startConnection("172.31.249.22", 2400, 2);
    map2.startConnection("172.31.249.22", 2400, 4);
    map2.dispose();
    mapInterface map3 = new mapInterface(2);
  }
  public void generateInformationCity() throws UnknownHostException, IOException, JSONException{
    System.out.println("generateInformationCity action");
    a = 3;
    this.dispose();
    mapInterface map0 = new mapInterface(0);
    map0.startConnection("172.31.249.22", 2400, 2);
    map0.dispose();
    mapInterface map3 = new mapInterface(3);
  }
  public void generateInformationStation() throws UnknownHostException, IOException, JSONException{
	System.out.println("generateInformationStation action");
	a = 4;
    this.dispose();
    mapInterface map0 = new mapInterface(0);
    map0.startConnection("172.31.249.22", 2400, 2);
    map0.startConnection("172.31.249.22", 2400, 4);
    map0.dispose();
    mapInterface map4 = new mapInterface(4);
  }
  public void close() {
	this.dispose();
  	functionalityClient client = new functionalityClient();
  }
  
  
  
    
    //Sauvegarde en bdd de la ville
    city util = new city();
	public String addSetCity() {
		int idCity1 = 1; 
		int nombreMaxVoiture1 = 0;
		int seuilAtmoCity1 = 0;
		String json= "";
		
		
	
		util.setIdCity(idCity1);
		util.setNameCity(transition.nameCity);
		util.setLongueurCity(transition.longueur1);
		util.setLargeurCity(transition.largeur1);
		util.setBudgetStation(transition.budgetCity1);
		util.setNombreMaxVoiture(nombreMaxVoiture1);
		util.setSeuilAtmoCity(seuilAtmoCity1);
		util.setTailleCity(transition.mapTaille1);
		
		json  ="{request:{ operation_type: SAVEMAP, target: city , idCity: "+util.getIdCity() + ", nameCity: "+ util.getNameCity() + ", longueurCity : "+ util.getLongueurCity() +", largeurCity : "+ util.getLargeurCity() +", budgetStation : "+ util.getBudgetStation() +",nombreMaxVoiture : "+ util.getNombreMaxVoiture() +",seuilAtmoCity : "+ util.getSeuilAtmoCity() +",tailleCity : "+ util.getTailleCity() +"}} " ;
		return json;
	}

	//Sauvegarde en bdd les informations des stations
	station utilStation = new station();
	public String addSetStation() {
		int idStation = 1; 
		String json= "";
		
		utilStation.setIdStation(idStation);
		utilStation.setNumberStation(transition.nombreStation1);
		utilStation.setCoutStation(transition.budgetStation1);
		utilStation.setLongueurReseau(transition.longueurReseau1);
		utilStation.setNumberTram(transition.numberTram1);
		utilStation.setNumberLine(transition.numberLine1);
		

		
		json  ="{request:{ operation_type: SAVESTATION, target: station , idStation: "+utilStation.getIdStation() + ", numberStation: "+ utilStation.getNumberStation() + ", coutStation : "+ utilStation.getCoutStation() +", longueurReseau : "+ utilStation.getLongueurReseau() +", numberTram : "+ utilStation.getNumberTram() +",numberLine : "+ utilStation.getNumberLine() +"}} " ;
		return json;
	}
	
    //recup�re les informations de la ville 
    public String getInformations(){
    	String json;
    	int idCity = 1;
    	json  ="{request:{ operation_type: INFOMAP, target: city , idCity: "+ idCity + "}} " ;
		return json;
    }
    
  //recup�re les informations des stations 
    public String getInfoStation(){
    	String json;
    	int idStation = 1;
    	json  ="{request:{ operation_type: INFOSTATION, target: station , idStation: "+ idStation + "}} " ;
		return json;
    }
    
    //mes informations city
    public String showResultCity(String jsonResponse) throws JSONException {
    	String res = "Empty";
    	JSONObject obj =new JSONObject(jsonResponse);
    	List<city> u = new ArrayList<city>();
    	
    			JSONArray arr = obj.getJSONArray("Data");
    			
				util.setIdCity(arr.getJSONObject(0).getInt("idCity"));
				util.setNameCity(arr.getJSONObject(0).getString("nameCity"));
				util.setLongueurCity(arr.getJSONObject(0).getInt("longueurCity"));
				util.setLargeurCity(arr.getJSONObject(0).getInt("largeurCity"));
				util.setBudgetStation(arr.getJSONObject(0).getInt("budgetStation"));
				util.setNombreMaxVoiture(arr.getJSONObject(0).getInt("nombreMaxVoiture"));
				util.setSeuilAtmoCity(arr.getJSONObject(0).getInt("seuilAtmoCity"));
				util.setTailleCity(arr.getJSONObject(0).getInt("tailleCity"));
				
				u.add(util);
				res = u.toString();   
				
				transition.budgetCity1 = util.getBudgetStation();
				transition.largeur1 = util.getLargeurCity();
				transition.longueur1 = util.getLongueurCity();
				transition.nameCity = util.getNameCity();
				transition.mapTaille1 = util.getTailleCity();
		return res;
		
	}
    
    //mes informations station
    public String showResultStation(String jsonResponse) throws JSONException {
    	String res = "Empty";
    	JSONObject obj =new JSONObject(jsonResponse);
    	List<station> u = new ArrayList<station>();
    	
    			JSONArray arr = obj.getJSONArray("Data");
    			
    			
    			utilStation.setIdStation(arr.getJSONObject(0).getInt("idStation"));
    			utilStation.setNumberStation(arr.getJSONObject(0).getInt("numberStation"));
    			utilStation.setCoutStation(arr.getJSONObject(0).getInt("coutStation"));
    			utilStation.setLongueurReseau(arr.getJSONObject(0).getInt("longueurReseau"));
    			utilStation.setNumberTram(arr.getJSONObject(0).getInt("numberTram"));
    			utilStation.setNumberLine(arr.getJSONObject(0).getInt("numberLine"));
				u.add(utilStation);
				res = u.toString();  
				
				transition.nombreStation1 = utilStation.getNumberStation();
				transition.budgetStation1 = utilStation.getCoutStation();
				transition.longueurReseau1 = utilStation.getLongueurReseau();
				transition.numberTram1 = utilStation.getNumberTram();
				transition.numberLine1 = utilStation.getNumberLine();

		return res;	
	}
    
    
    //Affiche les informations de la ville
    public  JPanel showInformationCity(){
        JPanel panel1 = new JPanel();
       	panel1.setLayout(new FlowLayout());
        panel1.setBackground(Color.white);	
        JLabel label1 = new JLabel();
        label1.setText("<html><body><p><p><p><p><p><p><p><p><p><p><p><p>" 
                      + "Ma ville fait " + transition.mapTaille1 + " km²"
                      + "<p>"   
                      + "Ma ville fait " + transition.largeur1 + " de largeur" 
                      + "<p>"
                      + "Ma ville fait " + transition.longueur1 + " de longueur" 
                      + "<p>"
                      + "Ma ville se nomme " + transition.nameCity
                      + "<p>"
                      + "Ma ville a pour budget " + transition.budgetCity1 + " euros"
                      +"</body></html>" );
        panel1.add(label1);
        return panel1;
      }
    
    public  JPanel showInformationStation(){

    	JPanel panel2 = new JPanel();
       	panel2.setLayout(new FlowLayout());
        panel2.setBackground(Color.white);	
    	JLabel label2 = new JLabel();
        label2.setText("<html><body><p><p><p><p><p><p><p><p><p><p><p><p>" 
                      + "Ma ville est compos� de  " + transition.nombreStation1 + " stations "
                      + "<p>"   
                      + "Une station est au prix unitaire de " + transition.budgetStation1 + " euros " 
                      + "<p>"
                      + "Le r�seau de tram de la ville fait " + transition.longueurReseau1 + " km" 
                      + "<p>"
                      + "Il est compos� de " + transition.numberTram1 + " trams"
                      + "<p>"
                      + "ainsi que de " + transition.numberLine1 + " lignes de tram"
                      +"</body></html>" );
       panel2.add(label2);
       return panel2;
      }
    
   //m�thode main 
   public static void main(String[] args) throws UnknownHostException, IOException, JSONException{
    mapInterface map = new mapInterface(0);
    map.startConnection("172.31.249.22", 2400, 0);
  }
}