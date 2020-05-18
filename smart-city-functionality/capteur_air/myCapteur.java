package capteur_air;
import city.city;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.util.TimerTask;
import java.util.Timer;
import javax.swing.JComboBox;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import client.CityClient;
import connectionPool.DataSource;
import tram_line.tramExceptions.noDataInBase;
import user.Users;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class myCapteur extends JFrame implements ActionListener {
	private JComboBox liste1;
	private JFormattedTextField jtf = new JFormattedTextField(NumberFormat.getIntegerInstance());
	private JMenuBar menu = new JMenuBar();
	private JMenu onglet1 = new JMenu("Déterminer la qualité d'air");
	private JMenu onglet2 = new JMenu ("Configurer Capteur");
	private JMenu onglet3 = new JMenu ("Historique");

	private JMenuItem case1 = new JMenuItem("Selectionner Quartier");
	private JMenuItem case2 = new JMenuItem("Indice de la ville");
	private JMenuItem case3 = new JMenuItem("Ajuster les seuils suivant les quartiers");
	private JMenuItem case4 = new JMenuItem("Déterminer l'intervalle de relevé");

	private Socket clientSocket; 
	private PrintWriter out;
	private BufferedReader in;

	public void startConnection(String ip, int port, String json) throws UnknownHostException, IOException, JSONException {
		String toSend = json;
		clientSocket = new Socket(ip, port);
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		while(true) {
			out.println(toSend);
			String response = in.readLine();
			System.out.println("***** RÃ©sultat ******\n");
			System.out.println(this.showresultquartier(response));
		}
	}

 // interface central
  public JPanel myCapteur(){
    	boolean alerte = true;
    	setTitle("Déterminer qualité d'air");
    	setSize(400, 200);
    	setLocationRelativeTo(null);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	    
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
    	    
    	    case1.addActionListener(new ActionListener(){
    	      public void actionPerformed(ActionEvent arg0) {
    	          try {
    	        	  getselectquartier();
    	  		} catch (IOException | JSONException e) {
    	  			// TODO Auto-generated catch block
    	  			e.printStackTrace();
    	  		}
    	      }        
    	    });
    	    
    	    case2.addActionListener(new ActionListener(){
    	        public void actionPerformed(ActionEvent arg0) {
    	          try {
    	          	//generateMapUnSave();
    	  		} catch (IOException | JSONException e) {
    	  			// TODO Auto-generated catch block
    	  			e.printStackTrace();
    	  		}

    	        }        
    	      });
    	    
    	    case3.addActionListener(new ActionListener(){
    	        public void actionPerformed(ActionEvent arg0) {
    	          try {
    	          	//generateMapUnSave();
    	  		} catch (IOException | JSONException e) {
    	  			// TODO Auto-generated catch block
    	  			e.printStackTrace();
    	  		}

    	        }        
    	      });
    	    
    	    case4.addActionListener(new ActionListener(){
    	        public void actionPerformed(ActionEvent arg0) {
    	          try {
    	        	  getintervalle();
    	  		} catch (IOException | JSONException e) {
    	  			// TODO Auto-generated catch block
    	  			e.printStackTrace();
    	  		} 

    	        }        
    	      });
    	    
    	    onglet3.addActionListener(new ActionListener(){
    	        public void actionPerformed(ActionEvent arg0) {
    	          try {
    	          	gethistorique();
    	  		} catch (IOException | JSONException e) {
    	  			// TODO Auto-generated catch block
    	  			e.printStackTrace();
    	  		}

  	        }        
    	      });
    
    

    	    
    	    if(getalerte(alerte = true)) {
        	    JButton bouton1 = new JButton("Alerte !!! L'indice relevé est supérerieur au seuil");
        	    getContentPane().add(bouton1, "North");
        	    getContentPane().setLayout(null);
        	    bouton1.setBackground(Color.red);
        	    bouton1.addActionListener(this);
    	    }else {
    	    	JButton bouton2 = new JButton("Un bouton sans taille");
    	    	getContentPane().add(bouton2, "North");
    	    	getContentPane().setLayout(null);
    	    	bouton2.setBackground(Color.green);
    	    	bouton2.addActionListener(this);
    	    }
    	    	
    	    	JPanel pres = new JPanel();
    	    	getContentPane().add(pres);
    	    	JLabel label = new JLabel("Bienvenue sur le site qui vous permettra de relevé la qualite d'air dans les quartiers et dans la ville");
    	    	pres.add(label);
    	    	
  	}
//private JComboBox liste;


//private JPanel buildContentPane(){
//JPanel panel = new JPane();
//panel.setLayout(new FlowLayout());
//panel.setBackground(Color.White);

//field1 = new JTextField();

//panel.add(field1);

//liste = new JComboBox(new OperateursModel());
//panel.add(liste);

//field2 = new JTextField();

//panel.add(field2);

//JButton bouton = new JButton(new CalculAction("Calculer"));

//panel.add(bouton);

//JLabel label = new JLabel("Résultat : Pas encore calculé");

//panel.add(label);

//return panel;
//}

//}
//    	    
  //}

    
    
    public  JPanel indiceville(){
    	
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(Color.white);

        JLabel label1 = new JLabel();
        label1.setText("<html><body><p><p><p><p><p><p><p><p><p><p><p><p>" 
                      + "Pour le quartier " + quartier + "nous avons un indice de" ++ 
                      + "<p>"   
                      + "Pour les particules fines, nous avons un indice ATMO de " +   
                      + "<p>"
                      + "Pour l'ozote, nous avons un indice ATMO de " +
                      + "<p>"
                      + "Pour le dioxyde d'azote, nous avons un indice ATMO de " + 
                      + "<p>"
                      + "Pour le dioxyde de soufre, nous avons un indice ATMO de " + 
                      + "<p>"
                      + "Donc un indice ATMO de " + 
                      +"</body></html>" );
        panel.add(label1);
        return panel;
      }
    
    public boolean getalerte(boolean alerte1) {
    	getseuil();
    	getcalculIndice();
    		
    	String query = "SELECT indiceatmo from capteurair";
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
    
	String seuil = "SELECT seuilquartier from district";
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
		alerte1 = true;
	}else {
		alerte1 = false;
	}
	return alerte1;
    }

    		
    	//intervalle relevé
    public void TestTimer(int a) {
    		Timer timer;
    		timer = new Timer();
    	    timer.schedule(new getcalculIndice(), 0 , a);
    }
    
    public  JPanel getintervalle(){
    		JPanel panel1 = new JPanel();
    		panel1.setLayout(new FlowLayout());
    		panel1.setBackground(Color.white);	
    		JLabel label1 = new JLabel("Veuillez déterminer l'intervalle des relevés");
    		jtf.getText();
    		int jtf1 = (Integer)jtf.getValue();
    		int jtf2 = jtf1 * 1000;
    		TestTimer(jtf2);
    		JLabel label2 = new JLabel("en secondes");
    		JButton btnNewButton = new JButton("Valider");
    		btnNewButton.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				
    			}
    		});
    		panel1.add(label1);
    		panel1.add(jtf);
    		panel1.add(label2);
    		panel1.add(btnNewButton);
    		return panel1;
      }
    
    public  JPanel getindiceville(){
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		panel1.setBackground(Color.white);	
		JLabel label1 = new JLabel("Veuillez déterminer l'intervalle des relevés");
		jtf.getText();
		int jtf1 = (Integer)jtf.getValue();
		int jtf2 = jtf1 * 1000;
		TestTimer(jtf2);
		JLabel label2 = new JLabel("en secondes");
		JButton btnNewButton = new JButton("Valider");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		panel1.add(label1);
		panel1.add(jtf);
		panel1.add(label2);
		panel1.add(btnNewButton);
		return panel1;
  }

//sélectionner quartier
    public  JPanel getselectquartier() throws UnknownHostException, IOException, JSONException{
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		panel1.setBackground(Color.white);	
		JLabel label1 = new JLabel("Choisissez le quartier souhaité : ");
		Object[] element1 = getconfigcapteur();
		liste1 = new JComboBox(element1);
		panel1.add(liste1);
		JButton Bouton = new JButton("Valider");
		Bouton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		panel1.add(Bouton);
		panel1.add(label1);
		return panel1;
    }
 
	public JComboBox getListe1(){
		return liste1;
	}


//historique
    public JPanel gethistorique(){
        JPanel panel1 = new JPanel();
       	panel1.setLayout(new FlowLayout());
        panel1.setBackground(Color.white);	
        JLabel label1 = new JLabel();
        for(int i = 0; i < ; i++) {
        	label1.setText("Un relevé de)
        }
        label1.setText("<html><body><p><p><p><p><p><p><p><p><p><p><p><p>" 
                      + "Un relevé de " +  + "sur l'échelle d'indice ATMO"
                      + "<p>"   
                      + "dans le quartier  " + quartier
                      + "<p>"
                      + "le " + date 
                      + "<p>"
                      + "à " + date
                      + "<p>"
                      +"</body></html>" );
        panel1.add(label1);
        return panel1;
    }


//    public void getseuil() {
//private JComboBox liste1;
//private JComboBox liste2;


//private JPanel buildContentPane(){
//JPanel panel = new JPanel();
//panel.setLayout(new FlowLayout());

//Object[] elements = new Object[];
//getconfigurecapteur(elements);

//liste1 = new JComboBox(elements);

//panel.add(liste1);

//JButton bouton = new JButton(new CopierAction(this, "OK"));

//panel.add(bouton);

//return panel;
//}

//   	int res1 = 0;
//String status = "Unknown";
//int monNom = request.getInt("id");
//String monLogin = request.getString("name");
//		int monPass = request.getInt("seuilquartieratmo");
//		// boolean alerte = request.getBoolean("etatalerte");
//		
//		String query = "INSERT INTO district (seuilquartieratmo) " + "VALUES ()";
//		
//		try {
//			pstmt = connect.prepareStatement(query);
//			pstmt.setInt(1, id);
//			pstmt.setString(2, name);
//			pstmt.setInt(3, seuilquartieratmo);
//			pstmt.setBoolean(4, etatalerte); 
//			res = pstmt.executeUpdate();
//			if(res1 == 1) {
//				status = "Succed";
//			}else {
//				status ="Failed";
//			}
//		} catch (SQLException ex) {
//			System.out.println(status);
//		}
//		//resultat = resultat + "Data : [{ nom: "+monNom + ", prenom: "+ monPrenom + ", login : "+ monLogin +", pass : "+ monPass +", profil : "+ monProfil +"} ]}";
//		//this.finalResponse = resultat ;
//		//return query;




    class getcalculIndice extends TimerTask {
    	public void run() {
      	    new Date();
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
 
 
      for(int i = 0; i < array.length; i++){
        if(array[i] > minVal)
          minVal = array[i];
      }
 
      
      System.out.print("\nIndiceATMO = "+minVal);
     }
   }
       
      
//	    	int res = 0;
//	    	String status = "Unknown";
//			int monNom = request.getInt("idcapteur");
//			String monPrenom = request.getString("namecapteur");
//			Date monLogin = request.getDate("datereleve");
//			int monPass = request.getInt("indiceatmo");
//			
//			String query = "INSERT INTO capteurair (indiceatmo) " + "VALUES (minVal)";
//			
//			try {
//				pstmt = connect.prepareStatement(query);
//				pstmt.setString(1, idcapteur);
//				pstmt.setString(2, namecapteur);
//				pstmt.setString(3, datereleve);
//				pstmt.setString(4, indiceatmo); 
//				res = pstmt.executeUpdate();
//				if(res == 1) {
//					status = "Succed";
//				}else {
//					status ="Failed";
//				}
//			} catch (SQLException ex) {
//				System.out.println(status);
//			}
//			//resultat = resultat + "Data : [{ nom: "+monNom + ", prenom: "+ monPrenom + ", login : "+ monLogin +", pass : "+ monPass +", profil : "+ monProfil +"} ]}";
//			//this.finalResponse = resultat ;
//			//return query;
		
    

//donne quartier
    public int getnumquart(int Quartier) throws UnknownHostException, IOException, JSONException {
    	city taille = new city();
    	getQuartier();
    	double a =taille.getTailleCity();
    	int resultat = (int)a;

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
		return resultat;
    }

    public void getQuartier() throws UnknownHostException, IOException, JSONException{
    	String json;
    	int idcity = 1;
    	json  ="{request:{ operation_type: INFOCITY, target: station , idcity: "+ idcity + "}} " ;
    	this.startConnection("172.31.249.22", 2400, json);
    }

	city tailleQuartier = new city();
	public String showresultquartier(String jsonResponse) throws JSONException, noDataInBase {
		String res = "Empty";
		JSONObject obj =new JSONObject(jsonResponse);
		List<city> u = new ArrayList<city>();

		JSONArray arr = obj.getJSONArray("Data");

		if(arr.length() == 0) {
			System.out.println("Il n'y a pas d'informations en base sur la ville");
			this.dispose();
			throw new noDataInBase();
		}

		tailleQuartier.setTailleCity(arr.getJSONObject(0).getInt("tailleCity"));

		System.out.println(tailleQuartier.getTailleCity());

		u.add(tailleQuartier);
		res = u.toString();   

		return res;

	}

	public String[] getconfigcapteur() throws UnknownHostException, IOException, JSONException{
		int a = 0;
		getnumquart(a);
		String[] myArray = new String[20];
		for(int i = 1; i < a + 1; i++) {
			myArray[i] = "Quartier" + i;
		}
		return myArray;
	}


	public static void main(String[] args) throws JSONException, UnknownHostException, IOException { 
		myCapteur air = new myCapteur();
		//air.startConnection("172.31.249.22", 2400);
	}
}





