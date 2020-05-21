package capteur_air;
import city.city;
import district.District;

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
import javax.swing.JSplitPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import CapteurAir.CapteurAir;
import client.CityClient;
import connectionPool.DataSource;
import district.District;
import tram_line.tramExceptions.noDataInBase;
import user.Users;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class myCapteur extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Object json;
//	private capteur4 a;
//	private capteur1 b;
//	private capteur2 c;
//	private capteur3 d;
//	private capteur5 e;
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
	private JMenuItem case5 = new JMenuItem("Apercevoir l'historique des relevés");	
	

	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;

	public void startConnection(String ip, int port , String json) throws UnknownHostException, IOException, JSONException {
		String toSend = json;
		clientSocket = new Socket(ip, port);
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		//while(true) {
			out.println(toSend);
			String response = in.readLine();
			System.out.println("***** Résultat ******\n");
			//System.out.println(this.showresultquartier(response));
			//System.out.println(this.showresultindice(response));
//			System.out.println(this.showresultseuil(response));
			System.out.println(this.addSetquartier());
		//}
		
	}

 // interface central
  public myCapteur() throws UnknownHostException, IOException, JSONException{
    	setTitle("Déterminer qualité d'air");
    	setSize(400, 200);
    	setLocationRelativeTo(null);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	    
    	    //On initialise nos menus      
		this.onglet1.add(case1);
		this.onglet1.add(case2);
		this.onglet2.add(case3);
		this.onglet2.add(case4);
		this.onglet3.add(case5);

    	    //L'ordre d'ajout va dï¿½terminer l'ordre d'apparition dans le menu de gauche ï¿½ droite
    	    //Le premier ajoutï¿½ sera tout ï¿½ gauche de la barre de menu et inversement pour le dernier
		this.menu.add(onglet1);
		this.menu.add(onglet2);
		this.menu.add(onglet3);
		this.setJMenuBar(menu);
		this.setVisible(true);

		case1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					try {
						selectquartier();
						//b = new capteur1();
					} catch (IOException | JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});

		case2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//try {
					indiceville();
				//c = new capteur2();
				//} catch (IOException | JSONException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				//}

			}
		});

		case3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				try {
//				d = new capteur3();
				configseuil();
//				} catch (IOException | JSONException e) {
//					// TODO Auto-generated catch block
//				e.printStackTrace();
//				}

			}
		});

		case4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//try {
					getintervalle();
					//a = new capteur4();
				//} catch (IOException | JSONException e) {
					// TODO Auto-generated catch block
				//	e.printStackTrace();
				//}
			}
		});

		case5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				try {
//				e = new capteur5();
				 Histor();
//				} catch (IOException | JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					}
			}
		});
		
		JPanel pres = new JPanel();
		getContentPane().add(pres);
		JLabel label = new JLabel("Bienvenue sur le site qui vous permettra de relevé la qualite d'air dans les quartiers et dans la ville");
		String imgUrl="atmo.png";
	    ImageIcon icone = new ImageIcon(imgUrl);

	  
	     //Création de JLable avec un alignement gauche
	     JLabel label1 = new JLabel(icone, JLabel.CENTER);
	  
	     //Création de JLable avec un alignement gauche
		pres.add(label);
		pres.add(label1);

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

//JLabel label = new JLabel("Rï¿½sultat : Pas encore calculï¿½");

//panel.add(label);

//return panel;
//}

//}
//    	    
  //}
  
  //sélectionner quartier
  public JFrame selectquartier() throws UnknownHostException, IOException, JSONException{
	JFrame a = new JFrame();
	a.setTitle("sélectionner quartier");
	a.setSize(400, 400);
	a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	a.setLocationRelativeTo(null);
	a.setVisible(true);
	JPanel panel1 = new JPanel();
	panel1.setLayout(new FlowLayout());
	panel1.setBackground(Color.white);
	JLabel label1 = new JLabel("Choisissez le quartier souhaité: ");
	//String[] elements = getconfigcapteur();
//	Object[] element1 = getconfigcapteur();
	//JComboBox liste1 = new JComboBox(elements);
	JComboBox liste1 = new JComboBox (); //Création d'une liste déroulante.
	liste1.addItem("Choix 1");
	liste1.addItem("Choix 2");
	liste1.addItem("Choix 3"); //Ajout de choix à cette liste déroulante.
	JLabel label2 = new JLabel(
			"Par défaut les seuils des quartiers sont de 4 mais ils peuvent être changeable dans configuration du capteur");
	JButton Bouton = new JButton("Valider");
	Bouton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				indicequartier();
				dispose();
			} catch (IOException | JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	});
	a.add(panel1);
	panel1.add(label1);
	panel1.add(liste1);
	panel1.add(label2);
	panel1.add(Bouton);
	return a;
  }
	public JComboBox getListe1(){
		return liste1;
	}
	
    //interface indice ville
    public JFrame indiceville(){
    	JFrame a = new JFrame();
    	a.setTitle("deuxième fenetre");
		a.setSize(400, 400);
		a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		a.setLocationRelativeTo(null);
		a.setVisible(true);
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(Color.white);

        JLabel label1 = new JLabel();
        label1.setText("<html><body><p><p><p><p><p><p><p><p><p><p><p><p>" 
                      + "Pour le quartier " //+ quartier 
                      + "nous avons un indice de" 
                      + "<p>"   
                      + "Pour les particules fines, nous avons un indice ATMO de "    
                      + "<p>"
                      + "Pour l'ozote, nous avons un indice ATMO de " 
                      + "<p>"
                      + "Pour le dioxyde d'azote, nous avons un indice ATMO de "  
                      + "<p>"
                      + "Pour le dioxyde de soufre, nous avons un indice ATMO de " 
                      + "<p>"
                      + "Donc un indice ATMO de "  
                      +"</body></html>" );
        a.add(panel);
        panel.add(label1);
        return a;
      }
    
  public JFrame configseuil() { 
	JFrame a = new JFrame();  
    a.setTitle("sélectionner quartier");
	a.setSize(400, 400);
	a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	a.setLocationRelativeTo(null);
	a.setVisible(true);
	JPanel panel1 = new JPanel();
	panel1.setLayout(new FlowLayout());
	panel1.setBackground(Color.white);
	JLabel label1 = new JLabel("Déterminer les nouveaux seuils des quartiers:\n ");
	JButton Bouton = new JButton("Valider");
	Bouton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			//System.exit(0);
			dispose();
		}
	});
	a.add(panel1);
	panel1.add(label1);
	panel1.add(Bouton);
	return a;
  }
  
	public JFrame Histor() {
		JFrame a = new JFrame();
		a.setTitle("Historique");
		a.setSize(400, 400);
		a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		a.setLocationRelativeTo(null);
		a.setVisible(true);
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		panel1.setBackground(Color.white);
		JLabel label1 = new JLabel();
		label1.setText("<html><body><p><p><p><p><p><p><p><p><p><p><p><p>"
				+ "Un relevé de 7 sur l'ï¿½chelle d'indice ATMO" 
				+ "<p>" 
				+ "dans le quartier  " // + quartier
				+ "<p>" 
				+ "le " 
				+ "<p>" 
				+ "<p>" 
				+ "</body></html>");
		a.add(panel1);
		panel1.add(label1);
		return a;
	}
  
   //alerte
    public int getalerte() throws UnknownHostException, IOException, JSONException {
    	//A faire
    	//getseuil();
    	//A modifier
    	//getcalculIndice();
    	int alerte1 = 0;
    	getindice();
    	int a =indiceATMO.getIndice();
    	System.out.println(a);
    	getdistrictseuil();
    	int b =seuildistrict.getSeuilQuartierATMO();
    	System.out.println(b);
	
    	if(b < a) {
    		alerte1 = 1;
    	}else {
    		alerte1 = 0;
    	}
    	return alerte1;
    }

    public void getindice() throws UnknownHostException, IOException, JSONException{
    	String json;
    	json  ="{request:{ operation_type: INFOINDATMO, target: capteurair }}";
    	this.startConnection("172.31.249.22", 2400, json);
    }

	CapteurAir indiceATMO = new CapteurAir();
	public String showresultindice (String jsonResponse) throws JSONException, noDataInBase {
		String res = "Empty";
		JSONObject obj =new JSONObject(jsonResponse);
		List<CapteurAir> u = new ArrayList<CapteurAir>();

		JSONArray arr = obj.getJSONArray("Data");

		if(arr.length() == 0) {
			System.out.println("Il n'y a pas d'informations en base dans le capteur d'air");
			dispose();
			throw new noDataInBase();
		}

		indiceATMO.setIndice(arr.getJSONObject(0).getInt("IndiceATMO"));

		System.out.println(indiceATMO.getIndice());

		u.add(indiceATMO);
		res = u.toString();   

		return res;

	}		
	
	public void getdistrictseuil() throws UnknownHostException, IOException, JSONException{
    	String json;
    	json  ="{request:{ operation_type: INFOSEUIL, target: district }}";
    	this.startConnection("172.31.249.22", 2400, json);
    }

	District seuildistrict = new District();
	public String showresultseuil (String jsonResponse) throws JSONException, noDataInBase {
		String res = "Empty";
		JSONObject obj =new JSONObject(jsonResponse);
		List <District> u = new ArrayList<District>();

		JSONArray arr = obj.getJSONArray("Data");

		if(arr.length() == 0) {
			System.out.println("Il n'y a pas d'informations en base dans le capteur d'air");
			dispose();
			throw new noDataInBase();
		}

		seuildistrict.setSeuilQuartierATMO(arr.getJSONObject(0).getInt("seuilQuartierATMO"));

		System.out.println(seuildistrict.getSeuilQuartierATMO());

		u.add(seuildistrict);
		res = u.toString();   

		return res;
	}		
    
    
    	//intervalle relevé
	public void TestTimer(int a) {
		int jtf2 = a * 1000;
		TestTimer(jtf2);
		Timer timer;
		timer = new Timer();
		timer.schedule(new myTask(), 0, a);
	}
    
	public JFrame getintervalle() {
		JFrame a = new JFrame();
		a.setTitle("sélectionner quartier");
		a.setSize(400, 400);
		a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		a.setLocationRelativeTo(null);
		a.setVisible(true);
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		panel1.setBackground(Color.white);
		JLabel label1 = new JLabel("Veuillez déterminer l'intervalle des relevés");
		jtf.getText();
		JLabel label2 = new JLabel("en secondes");
		JButton btnNewButton = new JButton("Valider");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
//				int jtf1 = (Integer) jtf.getValue();
//				TestTimer(jtf1);
			}
		});
		a.add(panel1);
		panel1.add(label1);
		panel1.add(jtf);
		panel1.add(label2);
		panel1.add(btnNewButton);
		return a;
	}
    
   public void getindiceville(int jtf2){
		int jtf3 = jtf2 * 1000;
		TestTimer(jtf3);
	}

	//interface indice du quartier 
	public JFrame indicequartier() throws UnknownHostException, IOException, JSONException {
		JFrame b = new JFrame();
		b.setTitle("sélectionner quartier");
		b.setSize(400, 400);
		b.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		b.setLocationRelativeTo(null);
		b.setVisible(true);
		int a = getCalculIndice();
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		panel2.setBackground(Color.white);
		JButton bouton1;
		int c = 5;
		int d = 4;
		//int c = getalerte();
		if (c < d) {
			bouton1 = new JButton("Alerte !!! L'indice relevé est supérieur au seuil");
    	    getContentPane().add(bouton1, "North");
    	    getContentPane().setLayout(null);
    	    bouton1.setBackground(Color.red);
		}else {
			bouton1 = new JButton("Tout va bien, aucun problème détecté");
    	    getContentPane().add(bouton1, "North");
    	    getContentPane().setLayout(null);
    	    bouton1.setBackground(Color.green);
		}
		JLabel label1 = new JLabel();
		label1.setText("<html><body><p><p><p><p><p><p><p><p><p><p><p><p>" 
                 + "Nous avons un indice ATMO de " +a+ " pour le quartier sélectionné"
                 +"</body></html>" );
		b.add(panel2);
		panel2.add(bouton1);
		panel2.add(label1);
		return b;
	}

	
	

//historique
	//A corriger
//	public JPanel gethistorique(){
//        JPanel panel1 = new JPanel();
//       	panel1.setLayout(new FlowLayout());
//        panel1.setBackground(Color.white);	
//        JLabel label1 = new JLabel();
//		
//		 for(int i = 0; i < ; i++) { label1.setText("Un relevï¿½ de"); }
//		  label1.setText("<html><body><p><p><p><p><p><p><p><p><p><p><p><p>" +
//		 "Un relevï¿½ de " + + "sur l'ï¿½chelle d'indice ATMO" + "<p>" +
//		  "dans le quartier  " + quartier + "<p>" + "le " + date + "<p>" + "ï¿½ " + date
//		  + "<p>" +"</body></html>" ); panel1.add(label1);
//		
//        return panel1;
//    }


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


//   
//    CapteurAir util = new CapteurAir();
//   	public String addSetCapteur() {
//   		int idCity1 = 1; 
//   		int nombreMaxVoiture1 = 0;
//   		int seuilAtmoCity1 = 0;
//   		String json= "";
//   		
//   		
//   	
//   		util.setIdCity(idCity1);
//   		util.setNameCity(transition.nameCity);
//   		util.setLongueurCity(transition.longueur1);
//   		util.setLargeurCity(transition.largeur1);
//   		util.setBudgetStation(transition.budgetCity1);
//   		util.setNombreMaxVoiture(nombreMaxVoiture1);
//   		util.setSeuilAtmoCity(seuilAtmoCity1);
//   		util.setTailleCity(transition.mapTaille1);
//   		
//   		json  ="{request:{ operation_type: SAVEMAP, target: city , idCity: "+util.getId() + ", nameCity: "+ util.getNameCity() + ", longueurCity : "+ util.getLongueurCity() +", largeurCity : "+ util.getLargeurCity() +", budgetStation : "+ util.getBudgetStation() +",nombreMaxVoiture : "+ util.getNombreMaxVoiture() +",seuilAtmoCity : "+ util.getSeuilAtmoCity() +",tailleCity : "+ util.getTailleCity() +"}} " ;
//   		return json;
//   	}

    public class myTask extends TimerTask{
    		public void run() {
    			 new Date();
    			 getCalculIndice();
    		}
    }
    		
    public int getCalculIndice() {
    	int indiceATMO = 0;
    	int indiceParticule = 0;
    	int indiceSoufre = 0;
      	int indiceOzone = 0;
       	int indiceAzote = 0;
      	int ParticulesFines = 0 + (int)(Math.random() * ((59 - 0) + 1));
    	int DioxydeSoufre = 0 + (int)(Math.random() * ((249 - 0) + 1));
    	int Ozone = 0 + (int)(Math.random() * ((149 - 0) + 1));
    	int DioxydeAzote = 0 + (int)(Math.random() * ((164 - 0) + 1));
    			   	
    			   	
    	if (Ozone > 0 && 29 > Ozone) {
      		indiceOzone = 1;
  	   	}else if (Ozone > 30 && 54 > Ozone){
  	   		indiceOzone = 2;
    	}else if (Ozone > 55 && 79 > Ozone){
			indiceOzone = 3;
		} else if (Ozone > 80 && 104 > Ozone) {
			indiceOzone = 4;
		} else if (Ozone > 105 && 129 > Ozone) {
			indiceOzone = 5;
		} else if (Ozone > 130 && 149 > Ozone) {
			indiceOzone = 6;
//		} else if (Ozone > 150 && 179 > Ozone) {
//			indiceOzone = 7;
//		} else if (Ozone > 180 && 209 > Ozone) {
//			indiceOzone = 8;
//		} else if (Ozone > 210 && 239 > Ozone) {
//			indiceOzone = 9;
//		} else if (240 < Ozone) {
//			indiceOzone = 10;
		}
		System.out.println(indiceOzone);

		if (DioxydeSoufre > 0 && 39 > DioxydeSoufre) {
			indiceSoufre = 1;
		} else if (DioxydeSoufre > 40 && 79 > DioxydeSoufre) {
			indiceSoufre = 2;
		} else if (DioxydeSoufre > 80 && 119 > DioxydeSoufre) {
			indiceSoufre = 3;
		} else if (DioxydeSoufre > 120 && 159 > DioxydeSoufre) {
			indiceSoufre = 4;
		} else if (DioxydeSoufre > 160 && 199 > DioxydeSoufre) {
			indiceSoufre = 5;
		} else if (DioxydeSoufre > 200 && 249 > DioxydeSoufre) {
			indiceSoufre = 6;
//		} else if (DioxydeSoufre > 250 && 299 > DioxydeSoufre) {
//			indiceSoufre = 7;
//		} else if (DioxydeSoufre > 300 && 399 > DioxydeSoufre) {
//			indiceSoufre = 8;
//		} else if (DioxydeSoufre > 400 && 499 > DioxydeSoufre) {
//			indiceSoufre = 9;
//		} else if (500 < DioxydeSoufre) {
//			indiceSoufre = 10;
		}

		System.out.println(indiceSoufre);
    			   	
    			   	
		if (ParticulesFines > 0 && 9 > ParticulesFines) {
			indiceParticule = 1;
		} else if (ParticulesFines > 10 && 19 > ParticulesFines) {
			indiceParticule = 2;
		} else if (ParticulesFines > 20 && 29 > ParticulesFines) {
			indiceParticule = 3;
		} else if (ParticulesFines > 30 && 39 > ParticulesFines) {
			indiceParticule = 4;
		} else if (ParticulesFines > 40 && 49 > ParticulesFines) {
			indiceParticule = 5;
		} else if (ParticulesFines > 50 && 59 > ParticulesFines) {
			indiceParticule = 6;
//		} else if (ParticulesFines > 60 && 79 > ParticulesFines) {
//			indiceParticule = 7;
//		} else if (ParticulesFines > 80 && 99 > ParticulesFines) {
//			indiceParticule = 8;
//		} else if (ParticulesFines > 100 && 124 > ParticulesFines) {
//    			   		indiceParticule = 9;
//    	}else if (125 < ParticulesFines){
//			indiceParticule = 10;
		}
		System.out.println(indiceParticule);

		if (DioxydeAzote > 0 && 29 > DioxydeAzote) {
			indiceAzote = 1;
		} else if (DioxydeAzote > 30 && 54 > DioxydeAzote) {
			indiceAzote = 2;
		} else if (DioxydeAzote > 55 && 84 > DioxydeAzote) {
			indiceAzote = 3;
		} else if (DioxydeAzote > 85 && 109 > DioxydeAzote) {
			indiceAzote = 4;
		} else if (DioxydeAzote > 110 && 134 > DioxydeAzote) {
			indiceAzote = 5;
		} else if (DioxydeAzote > 135 && 164 > DioxydeAzote) {
			indiceAzote = 6;
//		} else if (DioxydeAzote > 165 && 199 > DioxydeAzote) {
//			indiceAzote = 7;
//		} else if (DioxydeAzote > 200 && 274 > DioxydeAzote) {
//			indiceAzote = 8;
//		} else if (DioxydeAzote > 275 && 399 > DioxydeAzote) {
//			indiceAzote = 9;
//		} else if (400 < DioxydeAzote) {
//			indiceAzote = 10;
		}

		System.out.println(indiceAzote);

		int minVal = Integer.MIN_VALUE;

		int array[] = { indiceOzone, indiceParticule, indiceAzote, indiceSoufre };

		for (int i = 0; i < array.length; i++) {
			if (array[i] > minVal)
				minVal = array[i];
		}

		System.out.print("\nIndiceATMO = " + minVal);
		return minVal;
	}
      
		
    

//donne quartier
    public int getnumquart() throws UnknownHostException, IOException, JSONException {
    	int Quartier = 0;
    	getQuartier();
    	double b =tailleQuartier.getTailleCity();
    	int a = (int)b;

    	if(a > 0 && 11 > a) {
    		Quartier = 3;
    	}else if(a > 11 && 25 > a) {
    		Quartier = 6;
    	}else if(a > 26 && 50 > a) {
    		Quartier = 10;
    	}else if(a > 51 && 100 > a) {
    		Quartier = 15;
    	}else if(a > 100) {
    		Quartier = 20;
    	}
    	System.out.println("Quartier = " +Quartier);
    return Quartier;
    }

    public void getQuartier() throws UnknownHostException, IOException, JSONException{
    	String json;
    	int idcity = 1;
    	json  ="{request:{ operation_type: INFOCITY, target: station , idcity: "+ idcity + "}} " ;
    	this.startConnection("172.31.249.22", 2400,json);
    }

	city tailleQuartier = new city();
	public String showresultquartier(String jsonResponse) throws JSONException, noDataInBase {
		String res = "Empty";
		JSONObject obj =new JSONObject(jsonResponse);
		List<city> u = new ArrayList<city>();

		JSONArray arr = obj.getJSONArray("Data");

		if(arr.length() == 0) {
			System.out.println("Il n'y a pas d'informations en base sur la ville");
			dispose();
			throw new noDataInBase();
		}

		tailleQuartier.setTailleCity(arr.getJSONObject(0).getInt("tailleCity"));

		System.out.println(tailleQuartier.getTailleCity());

		u.add(tailleQuartier);
		res = u.toString();   

		return res;

	}
	
	
	public String[] getconfigcapteur()throws UnknownHostException, IOException, JSONException{
		int a = getnumquart();
		String[] myArray = new String[a];
		for(int i = 0; i < a ; i++) {
			myArray[i] = "Quartier_" + i;
			System.out.println(" '"+myArray[i]+"',");
			
		}
		
		return myArray;
	}
	
	District util = new District();
	public String addSetquartier() throws UnknownHostException, IOException, JSONException {
		int idCity1 = 1;
		String Quartier1 = "Quartier_"+idCity1;
		idCity1++;
		int a = 4;
		String json= "";
	
		util.setId(1);
		util.setName("quart");
		util.setSeuilQuartierATMO(4);
		
		json  ="{request:{ operation_type: ADDQUARTIER, target: district , id: "+util.getId() + ", name: "+ util.getName() + ", seuilquartieratmo : "+ util.getSeuilQuartierATMO() +"}} " ;
		this.startConnection("172.31.249.22", 2400,json);
		return json;
	}



	public static void main(String[] args) throws JSONException, UnknownHostException, IOException { 
		myCapteur a = new myCapteur();
		a.addSetquartier();
		//a.getalerte();
		//a.startConnection("172.31.249.22", 2400);
		//capteur6 b = new capteur6();
		//b.getalerte(2);
		}
}





