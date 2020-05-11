package capteur_air;

import java.awt.Color;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import connectionPool.DataSource;
import user.Users;

class myCapteur extends JFrame {
	private JMenuBar menu = new JMenuBar();
	private JMenu onglet1 = new JMenu("Déterminer la qualité d'air");
	private JMenu onglet2 = new JMenu ("Configurer Capteur");
	private JMenu onglet3 = new JMenu ("Historique");
	
	private JMenuItem case1 = new JMenuItem("Selectionner Quartier");
	private JMenuItem case2 = new JMenuItem("Indice de la ville");
	private JMenuItem case3 = new JMenuItem("Ajuster les seuils suivant les quartiers");
	private JMenuItem case4 = new JMenuItem("Déterminer l'intervalle de relevé");
	
    //private Socket clientSocket;
    //private PrintWriter out;
    //private BufferedReader in;
    
   // public void startConnection(String ip, int port) throws UnknownHostException, IOException, JSONException {
    	//String toSend;
    	//clientSocket = new Socket(ip, port);
       // out = new PrintWriter(clientSocket.getOutputStream(), true);
        //in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        //while(true) {
        //	toSend =this.afficheMenu();
	    //    out.println(toSend);
	    //    String response = in.readLine();
	    //    System.out.println("***** RÃ©sultat ******\n");
	    //    System.out.println(this.diplayResult(response));
       // }
   // }
    
    public myCapteur(int a) {
    	 this.setSize(400, 200);
    	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	    this.setLocationRelativeTo(null);

    	    //On initialise nos menus      
    	    this.onglet1.add(case1);

    	    //On ajoute les éléments dans notre sous-menu
    	   // this.test1_2.add(jcmi1);
    	    //this.test1_2.add(jcmi2);
    	    //Ajout d'un séparateur
    	    //this.test1_2.addSeparator();
    	    //On met nos radios dans un ButtonGroup
    	    //ButtonGroup bg = new ButtonGroup();
    	    //bg.add(jrmi1);
    	    //bg.add(jrmi1);
    	    //On présélectionne la première radio
    	    //jrmi1.setSelected(true);

    	    //this.test1_2.add(jrmi1);
    	    //this.test1_2.add(jrmi2);

    	    //Ajout du sous-menu dans notre menu
    	    //this.test1.add(this.test1_2);
    	    //Ajout d'un séparateur
    	    //this.onglet1.addSeparator();
    	    //item2.addActionListener(new ActionListener(){
    	      //public void actionPerformed(ActionEvent arg0) {
    	        //System.exit(0);
    	      //}        
    	    //});
    	    this.onglet1.add(case2);  
    	    this.onglet2.add(case3);
    	    this.onglet2.add(case4);

    	    //L'ordre d'ajout va déterminer l'ordre d'apparition dans le menu de gauche à droite
    	    //Le premier ajouté sera tout à gauche de la barre de menu et inversement pour le dernier
    	    this.menu.add(onglet1);
    	    this.menu.add(onglet2);
            this.menu.add(onglet3);
    	    this.setJMenuBar(menu);
    	    this.setVisible(true);
    	    
    	    if(getalerte(alerte = true)) {
        	    JButton bouton = new JButton("Alerte !!! L'indice relevé est supérerieur au seuil");
        	    getContentPane().add(bouton);
        	    bouton.setBackground(Color.red);
    	    }else {
    	    JButton bouton = new JButton("Un bouton sans taille");
    	    getContentPane().add(bouton);
    	    bouton.setBackground(Color.green);
    	    }
    	    
    	    
    }
    public void getalerte() {
    	boolean alerte = false;
    	getseuil();
    	getcalculIndice();
    		
    	String query = "SELECT indiceatmo from capteurair;
    	List<capteurair> res = new ArrayList<capteurair>();
		try {
			stm = connect.prepareStatement(query);
			rs = stm.executeQuery();
			while (rs.next()) {
				util.setidcapteur(rs.getInt(1));
				util.setnamecapteur(rs.getString(2));
				util.setdatereleve(rs.getdate(3));
				util.setindiceatmo(rs.getInt(4));
				res.add(util);
				//System.out.println("Operation realisee avec succes\n");
			}
		} catch (SQLException ex) {
			System.out.println("pas d'indice atmo ");
		}
		return query;
		int indice = Integer.parseInt(query);
    
	String seuil = "SELECT seuilquartier from district;
	List<district> res = new ArrayList<district>();
	try {
		stm = connect.prepareStatement(seuil);
		rs = stm.executeQuery();
		while (rs.next()) {
			util.setidcapteur(rs.getInt(1));
			util.setnamecapteur(rs.getString(2));
			util.setdatereleve(rs.getdate(3));
			util.setindiceatmo(rs.getInt(4));
			res.add(util);
			//System.out.println("Operation realisee avec succes\n");
		}
	} catch (SQLException ex) {
		System.out.println("pas d'indice atmo ");
	}
	return seuil;
	int Atmoseuil = Integer.parseInt(seuil);
	
	if(Atmoseuil < indice) {
		alerte = true;
	}else {
		alerte = false;
	}
}
    
    
    public void getseuil() {
    	int res1 = 0;
    	//String status = "Unknown";
		//int monNom = request.getInt("id");
		//String monLogin = request.getString("name");
		int monPass = request.getInt("seuilquartieratmo");
		// boolean alerte = request.getBoolean("etatalerte");
		
		String query = "INSERT INTO district (seuilquartieratmo) " + "VALUES ()";
		
		try {
			pstmt = connect.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setString(2, name);
			pstmt.setInt(3, seuilquartieratmo);
			pstmt.setBoolean(4, etatalerte); 
			res = pstmt.executeUpdate();
			if(res1 == 1) {
				status = "Succed";
			}else {
				status ="Failed";
			}
		} catch (SQLException ex) {
			System.out.println(status);
		}
		//resultat = resultat + "Data : [{ nom: "+monNom + ", prenom: "+ monPrenom + ", login : "+ monLogin +", pass : "+ monPass +", profil : "+ monProfil +"} ]}";
		//this.finalResponse = resultat ;
		//return query;
    }
    
    
    
    public void getcalculIndice() {
   	int indiceATMO = 0;
   	int indiceParticule = 0;
   	int indiceSoufre = 0;
   	int indiceOzone = 0;
   	int indiceAzote = 0;
   	int ParticulesFines = 0 + (int)(Math.random() * ((135 - 0) + 1));
   	int DioxydeSoufre = 0 + (int)(Math.random() * ((510 - 0) + 1));
   	int Ozone = 0 + (int)(Math.random() * ((250 - 0) + 1));
   	int DioxydeAzote = 0 + (int)(Math.random() * ((410 - 0) + 1));
   	
   	
   	if (Ozone > 0 && 29 > Ozone) {
   		indiceOzone = 1;
   	}else if (Ozone > 30 && 54 > Ozone){
   		indiceOzone = 2;
   	}else if (Ozone > 55 && 79 > Ozone){
   		indiceOzone = 3;
   	}else if (Ozone > 80 && 104 > Ozone){
   		indiceOzone = 4;
   	}else if (Ozone > 105 && 129 > Ozone){
   		indiceOzone = 5;
   	}else if (Ozone > 130 && 149 > Ozone){
   		indiceOzone = 6;
   	}else if (Ozone > 150 && 179 > Ozone){
   		indiceOzone = 7;
   	}else if (Ozone > 180 && 209 > Ozone){
   		indiceOzone = 8;
   	}else if (Ozone > 210 && 239 > Ozone){
   		indiceOzone = 9;
   	}else if (240 < Ozone){
   		indiceOzone = 10;	
   	}
        System.out.println(indiceOzone);
   	
  
   	if (DioxydeSoufre > 0 && 39 > DioxydeSoufre) {
   		indiceSoufre = 1;
   	}else if (DioxydeSoufre > 40 && 79 > DioxydeSoufre){
   		indiceSoufre = 2;
   	}else if (DioxydeSoufre > 80 && 119 > DioxydeSoufre){
   		indiceSoufre = 3;
   	}else if (DioxydeSoufre > 120 && 159 > DioxydeSoufre){
   		indiceSoufre = 4;
   	}else if (DioxydeSoufre > 160 && 199 > DioxydeSoufre){
   		indiceSoufre = 5;
   	}else if (DioxydeSoufre > 200 && 249 > DioxydeSoufre){
   		indiceSoufre = 6;
   	}else if (DioxydeSoufre > 250 && 299 > DioxydeSoufre){
   		indiceSoufre = 7;
   	}else if (DioxydeSoufre > 300 && 399 > DioxydeSoufre){
   		indiceSoufre = 8;
   	}else if (DioxydeSoufre > 400 && 499 > DioxydeSoufre){
   		indiceSoufre = 9;
   	}else if (500 < DioxydeSoufre){
   		indiceSoufre = 10;	
   	}	

        System.out.println(indiceSoufre);
   	
   	
   	if (ParticulesFines > 0 && 9 > ParticulesFines) {
   		indiceParticule = 1;
   	}else if (ParticulesFines > 10 && 19 > ParticulesFines){
   		indiceParticule = 2;
   	}else if (ParticulesFines > 20 && 29 > ParticulesFines){
   		indiceParticule = 3;
   	}else if (ParticulesFines > 30 && 39 > ParticulesFines){
   		indiceParticule = 4;
   	}else if (ParticulesFines > 40 && 49 > ParticulesFines){
   		indiceParticule = 5;
   	}else if (ParticulesFines > 50 && 59 > ParticulesFines){
   		indiceParticule = 6;
   	}else if (ParticulesFines > 60 && 79 > ParticulesFines){
   		indiceParticule = 7;
   	}else if (ParticulesFines > 80 && 99 > ParticulesFines){
   		indiceParticule = 8;
   	}else if (ParticulesFines > 100 && 124 > ParticulesFines){
   		indiceParticule = 9;
   	}else if (125 < ParticulesFines){
   		indiceParticule = 10;	
   	}
        System.out.println(indiceParticule);
   	
   	
   	if (DioxydeAzote > 0 && 29 > DioxydeAzote) {
   		indiceAzote = 1;
   	}else if (DioxydeAzote > 30 && 54 > DioxydeAzote){
   		indiceAzote = 2;
   	}else if (DioxydeAzote > 55 && 84 > DioxydeAzote){
   		indiceAzote = 3;
   	}else if (DioxydeAzote > 85 && 109 > DioxydeAzote){
   		indiceAzote = 4;
   	}else if (DioxydeAzote > 110 && 134 > DioxydeAzote){
   		indiceAzote = 5;
   	}else if (DioxydeAzote > 135 && 164 > DioxydeAzote){
   		indiceAzote = 6;
   	}else if (DioxydeAzote > 165 && 199 > DioxydeAzote){
   		indiceAzote = 7;
   	}else if (DioxydeAzote > 200 && 274 > DioxydeAzote){
   		indiceAzote = 8;
   	}else if (DioxydeAzote > 275 && 399 > DioxydeAzote){
   		indiceAzote = 9;
   	}else if (400 < DioxydeAzote){
   		indiceAzote = 10;	
   	}

   	 System.out.println(indiceAzote);

      int minVal = Integer.MIN_VALUE;
      
      int array[] = {indiceOzone, indiceParticule, indiceAzote, indiceSoufre};
 
      for (int nombre:array)
        System.out.print(nombre+" ");
 
      for(int i = 0; i < array.length; i++){
        if(array[i] > minVal)
          minVal = array[i];
      }
 
      
      System.out.print("\nIndiceATMO = "+minVal);
       
      
	    	int res = 0;
	    	//String status = "Unknown";
			//int monNom = request.getInt("idcapteur");
			//String monPrenom = request.getString("namecapteur");
			//Date monLogin = request.getDate("datereleve");
			int monPass = request.getInt("indiceatmo");
			
			String query = "INSERT INTO capteurair (indiceatmo) " + "VALUES (minVal)";
			
			try {
				pstmt = connect.prepareStatement(query);
				pstmt.setString(1, idcapteur);
				pstmt.setString(2, namecapteur);
				pstmt.setString(3, datereleve);
				pstmt.setString(4, indiceatmo); 
				res = pstmt.executeUpdate();
				if(res == 1) {
					status = "Succed";
				}else {
					status ="Failed";
				}
			} catch (SQLException ex) {
				System.out.println(status);
			}
			//resultat = resultat + "Data : [{ nom: "+monNom + ", prenom: "+ monPrenom + ", login : "+ monLogin +", pass : "+ monPass +", profil : "+ monProfil +"} ]}";
			//this.finalResponse = resultat ;
			//return query;
		}
  
    
    public String getQuartier(String query) throws JsonProcessingException {
    	int Quartier = 0;
	  	 String query = "SELECT taillecity from city where idcity = 1";
    	List<City> res = new ArrayList<City>();
		try {
			stm = connect.prepareStatement(query);
			rs = stm.executeQuery();
			while (rs.next()) {
				util.setIdCity(rs.getInt(1));
				util.setNameCity(rs.getString(2));
				util.setLongueurCity(rs.getInt(3));
				util.setLargeurCity(rs.getInt(4));
				util.setBudgetStation(rs.getInt(5));
				util.setNombreMaxVoiture(rs.getInt(6));
				util.setSeuilAtmoCity(rs.getInt(7));
				util.setTailleCity(rs.getInt(8));
				res.add(util);
				//System.out.println("Operation realisee avec succes\n");
			}
		} catch (SQLException ex) {
			System.out.println("La ville n'existe pas, il n'y a donc pas de quartier");
		}
		return query;
		int resultat = Integer.parseInt(query);
		
		if(resultat < 10) {
			Quartier = 3;
		}else if(resultat > 11 && 25 > resultat) {
			Quartier = 6;
		}else if(resultat > 26 && 50 > resultat) {
			Quartier = 10;
		}else if(resultat > 51 && 100 > resultat) {
			Quartier = 15;
		}else if(resultat > 100) {
			Quartier = 20;
		}
		return Quartier;
		
	}
    
    public static void main(String[] args){ //throws UnknownHostException, IOException, JSONException {
    	myCapteur air = new myCapteur(1);
       // air.startConnection("172.31.249.22", 1500);
       // client.afficheMenu();
    }
}