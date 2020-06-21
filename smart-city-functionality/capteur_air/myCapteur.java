package capteur_air;
import city.city;
import district.District;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class myCapteur extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Object json;
	private JComboBox liste1;
	private JTextField jtf = new JTextField();
	private JMenuBar menu = new JMenuBar();
	private JMenu onglet1 = new JMenu("Déterminer la qualité d'air");
	private JMenu onglet2 = new JMenu ("Configurer Capteur");
	//private JMenu onglet3 = new JMenu ("Historique");

	private JMenuItem case1 = new JMenuItem("Selectionner Quartier");
	private JMenuItem case2 = new JMenuItem("Indice de la ville");
	private JMenuItem case3 = new JMenuItem("Ajuster les seuils suivant les quartiers");
	private JMenuItem case4 = new JMenuItem("Déterminer l'intervalle de relevé");
	//private JMenuItem case5 = new JMenuItem("Apercevoir l'historique des relevés");
	
	//String datetime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-M-yyyy hh:mm:ss"));
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private Object jtext;

	public String startConnection(String ip, int port , String json) throws UnknownHostException, IOException, JSONException {
		String toSend = json;
		clientSocket = new Socket(ip, port);
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		out.println(toSend);
		String response = in.readLine();
		System.out.println("***** Résultat ******\n");
		return response;
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
		//this.onglet3.add(case5);

    	    //L'ordre d'ajout va dï¿½terminer l'ordre d'apparition dans le menu de gauche ï¿½ droite
    	    //Le premier ajoutï¿½ sera tout ï¿½ gauche de la barre de menu et inversement pour le dernier
		this.menu.add(onglet1);
		this.menu.add(onglet2);
		//this.menu.add(onglet3);
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
					try {
						indiceville();
					} catch (IOException | JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});

		case3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					configseuil();
				} catch (IOException | JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		case4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//try {
					getintervalle();
				//} catch (IOException | JSONException e) {
					// TODO Auto-generated catch block
				//	e.printStackTrace();
				//}
			}
		});

//		case5.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				 try {
//					Histor();
//				} catch (IOException | JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		});
		
		JPanel pres = new JPanel();
		getContentPane().add(pres);
		JLabel label = new JLabel();
		label.setText("<html><body><p><p><p>"
			+ "Bienvenue sur le site qui vous permettra de relevé la qualite d'air dans les quartiers et dans la ville" 
			+ "<p>"+ "<p>" 
			+ "Dans l'onglet 'Déterminer la qualité d'air' , vous pourrez apercevoir la qualité d'air dans un quartier précis ainsi que l'indice global de la ville" // + quartier
			+ "<p>" + "<p>" 
			+ "Dans l'onglet 'Configuration des capteurs', vous pourrez modifier le seuil des quartiers ainsi que l'intervalle des relevés de qualité d'air dans la ville"
			+ "</body></html>");

	  
	     //Création de JLable avec un alignement gauche
	  
	     //Création de JLable avec un alignement gauche
		pres.add(label);

	}
  
  //sélectionner quartier
  public JFrame selectquartier() throws UnknownHostException, IOException, JSONException{
	JFrame a = new JFrame();
	a.setTitle("sélectionner quartier");
	a.setSize(400, 400);
	a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	a.setLocationRelativeTo(null);
	a.setVisible(true);
	//String[] e = getconfigcapteur();
	//Arrays.toString(e);
	JPanel panel1 = new JPanel();
	panel1.setLayout(new FlowLayout());
	panel1.setBackground(Color.white);
	JLabel label1 = new JLabel("Choisissez le quartier souhaité: ");
	JComboBox<String> liste1 = new JComboBox<String> (); //Création d'une liste déroulante.
	int b = getnumquart();
	if(b == 3) {
		liste1.addItem("Quartier_0");liste1.addItem("Quartier_1");liste1.addItem("Quartier_2");
	}else if(b == 6) {
		liste1.addItem("Quartier_0");liste1.addItem("Quartier_1");liste1.addItem("Quartier_2");liste1.addItem("Quartier_3");liste1.addItem("Quartier_4");
		liste1.addItem("Quartier_5");
	}else if(b == 10) {
		liste1.addItem("Quartier_0"); liste1.addItem("Quartier_1"); liste1.addItem("Quartier_2");liste1.addItem("Quartier_3");liste1.addItem("Quartier_4");liste1.addItem("Quartier_5");liste1.addItem("Quartier_6");
		liste1.addItem("Quartier_7");liste1.addItem("Quartier_8");liste1.addItem("Quartier_9");
	}else if(b == 15) {
		liste1.addItem("Quartier_0");liste1.addItem("Quartier_1");liste1.addItem("Quartier_2");liste1.addItem("Quartier_3");liste1.addItem("Quartier_4");liste1.addItem("Quartier_5");liste1.addItem("Quartier_6");liste1.addItem("Quartier_7");liste1.addItem("Quartier_8");
		liste1.addItem("Quartier_9");liste1.addItem("Quartier_10");liste1.addItem("Quartier_11");liste1.addItem("Quartier_12");liste1.addItem("Quartier_13");liste1.addItem("Quartier_14");
	}else if(b > 20) {
		liste1.addItem("Quartier_0");liste1.addItem("Quartier_1");liste1.addItem("Quartier_2");liste1.addItem("Quartier_3");liste1.addItem("Quartier_4");liste1.addItem("Quartier_5");liste1.addItem("Quartier_6");liste1.addItem("Quartier_7");liste1.addItem("Quartier_8");
		liste1.addItem("Quartier_9");liste1.addItem("Quartier_10");liste1.addItem("Quartier_11");liste1.addItem("Quartier_12");liste1.addItem("Quartier_13");liste1.addItem("Quartier_14");liste1.addItem("Quartier_15");liste1.addItem("Quartier_16");liste1.addItem("Quartier_17");
		liste1.addItem("Quartier_18");liste1.addItem("Quartier_19");
	}

	JLabel label2 = new JLabel(
			"Par défaut les seuils des quartiers sont de 4 mais ils peuvent être changeable dans configuration du capteur");
	JButton Bouton = new JButton("Valider");
	Bouton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				String selected = (String) liste1.getSelectedItem();
				System.out.println(selected);
				a.dispose();
				indicequartier(selected);
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
	public JFrame indiceville() throws UnknownHostException, IOException, JSONException {
		JFrame a = new JFrame();
		a.setTitle("Indice de la ville");
		a.setSize(400, 400);
		a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		a.setLocationRelativeTo(null);
		a.setVisible(true);
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.setBackground(Color.white);
		JLabel label2 = new JLabel();
		int c = getnumquart();
		if (c == 3) {
		label2.setText("<html><body><p><p><p>" + "Pour le quartier_0, nous avons un indice de " +getCalculIndice()+ "<p>" + "Pour le quartier_1, nous avons un indice de " +getCalculIndice()+ "<p>"
				+ "Pour le quartier_2, nous avons un indice de " +getCalculIndice()+ "<p>"+"<p>" + "Nous avons donc un indice dans la ville de 6"+ "</body></html>");
	} else if (c == 6) {
		label2.setText("<html><body><p><p><p><p><p>" + "Pour le quartier_0, nous avons un indice de " +getCalculIndice()+ "<p>" + "Pour le quartier_1, nous avons un indice de " +getCalculIndice()+ "<p>"
				+ "Pour le quartier_2, nous avons un indice de " +getCalculIndice()+ "<p>" +"Pour le quartier_3, nous avons un indice de " +getCalculIndice()+ "<p>"+"Pour le quartier_4, nous avons un indice de " +getCalculIndice()+ "<p>"+"Pour le quartier_5, nous avons un indice de " +getCalculIndice()+ "<p>"+"<p>" + "Nous avons donc un indice dans la ville de 6"+"</body></html>");
	} else if (c == 10) {
		label2.setText("<html><body><p><p><p><p><p>" + "Pour le quartier_0, nous avons un indice de " +getCalculIndice()+ "<p>" + "Pour le quartier_1, nous avons un indice de " +getCalculIndice()+ "<p>"
				+ "Pour le quartier_2, nous avons un indice de " +getCalculIndice()+ "<p>" +"Pour le quartier_3, nous avons un indice de " +getCalculIndice()+ "<p>"+"Pour le quartier_4, nous avons un indice de " +getCalculIndice()+ "<p>"+"Pour le quartier_5, nous avons un indice de " +getCalculIndice()+ "<p>"+ "Pour le quartier_6, nous avons un indice de " +getCalculIndice()+"<p>"+"Pour le quartier_7, nous avons un indice de " +getCalculIndice()+"<p>" +"Pour le quartier_8, nous avons un indice de " +getCalculIndice()+ "<p>" +"Pour le quartier_9, nous avons un indice de " +getCalculIndice()+ "<p>" + "Nous avons donc un indice dans la ville de 6"+"</body></html>");

	} else if (c == 15) {
		label2.setText("<html><body><p><p><p><p><p>" + "Pour le quartier_0, nous avons un indice de " +getCalculIndice()+ "<p>" + "Pour le quartier_1, nous avons un indice de " +getCalculIndice()+ "<p>"
				+ "Pour le quartier_2, nous avons un indice de " +getCalculIndice()+ "<p>" +"Pour le quartier_3, nous avons un indice de " +getCalculIndice()+ "<p>"+"Pour le quartier_4, nous avons un indice de " +getCalculIndice()+ "<p>"+"Pour le quartier_5, nous avons un indice de " +getCalculIndice()+ "<p>"+ "Pour le quartier_6, nous avons un indice de " +getCalculIndice()+"<p>"+"Pour le quartier_7, nous avons un indice de " +getCalculIndice()+"<p>" +"Pour le quartier_8, nous avons un indice de " +getCalculIndice()+ "<p>" +"Pour le quartier_9, nous avons un indice de " +getCalculIndice()+ "<p>" + "Pour le quartier_10, nous avons un indice de " +getCalculIndice()+ "<p>"+ "Pour le quartier_11, nous avons un indice de " +getCalculIndice()+ "<p>"+ "Pour le quartier_12, nous avons un indice de " +getCalculIndice()+ "<p>"+ "Pour le quartier_13, nous avons un indice de " +getCalculIndice()+ "<p>" + "Pour le quartier_14, nous avons un indice de " +getCalculIndice()+ "<p>"+ "Nous avons donc un indice dans la ville de 6"+"</body></html>");
	} else if (c > 20) {
		label2.setText("<html><body><p><p><p><p><p>" + "Pour le quartier_0, nous avons un indice de " +getCalculIndice()+ "<p>" + "Pour le quartier_1, nous avons un indice de " +getCalculIndice()+ "<p>"
				+ "Pour le quartier_2, nous avons un indice de " +getCalculIndice()+ "<p>" +"Pour le quartier_3, nous avons un indice de " +getCalculIndice()+ "<p>"+"Pour le quartier_4, nous avons un indice de " +getCalculIndice()+ "<p>"+"Pour le quartier_5, nous avons un indice de " +getCalculIndice()+ "<p>"+ "Pour le quartier_6, nous avons un indice de " +getCalculIndice()+"<p>"+"Pour le quartier_7, nous avons un indice de " +getCalculIndice()+"<p>" +"Pour le quartier_8, nous avons un indice de " +getCalculIndice()+ "<p>" +"Pour le quartier_9, nous avons un indice de " +getCalculIndice()+ "<p>" + "Pour le quartier_10, nous avons un indice de " +getCalculIndice()+ "<p>"+ "Pour le quartier_11, nous avons un indice de " +getCalculIndice()+ "<p>"+ "Pour le quartier_12, nous avons un indice de " +getCalculIndice()+ "<p>"+ "Pour le quartier_13, nous avons un indice de " +getCalculIndice()+ "<p>" + "Pour le quartier_14, nous avons un indice de " +getCalculIndice()+ "<p>"+ "Pour le quartier_15, nous avons un indice de " +getCalculIndice()+ "<p>" +"Pour le quartier_16, nous avons un indice de " +getCalculIndice()+ "<p>" +"Pour le quartier_17, nous avons un indice de " +getCalculIndice()+ "<p>" +"Pour le quartier_18, nous avons un indice de " +getCalculIndice()+ "<p>" +"Pour le quartier_19, nous avons un indice de " +getCalculIndice()+ "<p>" +"Nous avons donc un indice dans la ville de 6"+"</body></html>");
	}
        a.add(panel);
        panel.add(label2);
        return a;
      }
    
  public JFrame configseuil() throws UnknownHostException, IOException, JSONException { 
	JFrame a = new JFrame();  
    a.setTitle("sélectionner quartier");
	a.setSize(450, 300);
	a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	a.setLocationRelativeTo(null);
	a.setVisible(true);
	JPanel panel1 = new JPanel();
	panel1.setLayout(new FlowLayout());
	panel1.setBackground(Color.white);
	JLabel label1 = new JLabel("Déterminer les nouveaux seuils des quartiers:\n ");
	JTextField textField = new JTextField();
	textField.setColumns(2);
	JComboBox<String> liste1 = new JComboBox<String> (); //Création d'une liste déroulante.
	int b = getnumquart();
	if(b == 3) {
		liste1.addItem("Quartier_0");liste1.addItem("Quartier_1");liste1.addItem("Quartier_2");
	}else if(b == 6) {
		liste1.addItem("Quartier_0");liste1.addItem("Quartier_1");liste1.addItem("Quartier_2");liste1.addItem("Quartier_3");liste1.addItem("Quartier_4");
		liste1.addItem("Quartier_5");
	}else if(b == 10) {
		liste1.addItem("Quartier_0"); liste1.addItem("Quartier_1"); liste1.addItem("Quartier_2");liste1.addItem("Quartier_3");liste1.addItem("Quartier_4");liste1.addItem("Quartier_5");liste1.addItem("Quartier_6");
		liste1.addItem("Quartier_7");liste1.addItem("Quartier_8");liste1.addItem("Quartier_9");
	}else if(b == 15) {
		liste1.addItem("Quartier_0");liste1.addItem("Quartier_1");liste1.addItem("Quartier_2");liste1.addItem("Quartier_3");liste1.addItem("Quartier_4");liste1.addItem("Quartier_5");liste1.addItem("Quartier_6");liste1.addItem("Quartier_7");liste1.addItem("Quartier_8");
		liste1.addItem("Quartier_9");liste1.addItem("Quartier_10");liste1.addItem("Quartier_11");liste1.addItem("Quartier_12");liste1.addItem("Quartier_13");liste1.addItem("Quartier_14");
	}else if(b > 20) {
		liste1.addItem("Quartier_0");liste1.addItem("Quartier_1");liste1.addItem("Quartier_2");liste1.addItem("Quartier_3");liste1.addItem("Quartier_4");liste1.addItem("Quartier_5");liste1.addItem("Quartier_6");liste1.addItem("Quartier_7");liste1.addItem("Quartier_8");
		liste1.addItem("Quartier_9");liste1.addItem("Quartier_10");liste1.addItem("Quartier_11");liste1.addItem("Quartier_12");liste1.addItem("Quartier_13");liste1.addItem("Quartier_14");liste1.addItem("Quartier_15");liste1.addItem("Quartier_16");liste1.addItem("Quartier_17");
		liste1.addItem("Quartier_18");liste1.addItem("Quartier_19");
	}

	JButton Bouton = new JButton("Valider");
	Bouton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			//System.exit(0);
			String selected = (String) liste1.getSelectedItem();
			System.out.println(selected);
			a.dispose();
			int z = Integer.parseInt(textField.getText());
			try {
				comparaison(z);
			} catch (IOException | JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	});
	a.add(panel1);
	panel1.add(label1);
	panel1.add(liste1);
	panel1.add(textField);
	panel1.add(Bouton);
	return a;
  }
  
  public JFrame comparaison(int s) throws UnknownHostException, IOException, JSONException {
		JFrame b = new JFrame();
		b.setTitle("sélectionner quartier");
		b.setSize(400, 400);
		b.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		b.setLocationRelativeTo(null);
		b.setVisible(true);
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		panel2.setBackground(Color.white);
		JButton bouton1;
		//getindice();
		//getdistrictseuil();
		
		int c = getCalculIndice(); //indiceATMO.getIndice();
		//System.out.println("indiceATMO.getIndice()");
		 //seuildistrict.getSeuilQuartierATMO();
		//int c = getalerte();
		if (c > s) {
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
               + "Nous avons un indice ATMO de " + c + " pour le quartier sélectionné"
               +"</body></html>" );
		b.add(panel2);
		panel2.add(bouton1);
		panel2.add(label1);
		return b;
	}
  
 
//  public Comparaison(int a) {
//	  int b = getCalculIndice();
//	  int c = 0;
//	  if (a > b){
//		c = 0;
//	  } else {
//		c = 1;
//	  }
//	   c = getalerte();
//  }
  
//	public JFrame Histor() throws UnknownHostException, IOException, JSONException {
//		JFrame a = new JFrame();
//		a.setTitle("Historique");
//		a.setSize(400, 400);
//		a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		a.setLocationRelativeTo(null);
//		a.setVisible(true);
//		JPanel panel1 = new JPanel();
//		panel1.setLayout(new FlowLayout());
//		panel1.setBackground(Color.white);
//		String s = null;
//		for(int i = 0; i < 50; i++ ) {
//			String f = historique();
//			s = f + "\n";
//		}
//		//String f = historique();
//		JLabel label1 = new JLabel(s);
////		JLabel label2 = new JLabel();
////		Timer timer = new Timer();
////        timer.scheduleAtFixedRate(new TimerTask() {
////            public void run() {
////               label2.setText(df.format(new Date()));
////            }
////        }, 0, 1000);
////		label1.setText("<html><body><p><p><p><p><p><p>"
////				+ "Un relevé de 7 sur l'échelle d'indice ATMO" 
////				+ "<p>" 
////				+ "dans le quartier  " // + quartier
////				+ "<p>" 
////				+ "le " 
////				+ "<p>" 
////				+ "<p>" 
////				+ "</body></html>");
//		a.add(panel1);
////		panel1.add(label2);
//		panel1.add(label1);
//		
//		return a;
//	}
	
//	public String Historique(int indice){
//		
//	}
  
   //alerte
//    public int getalerte() throws UnknownHostException, IOException, JSONException {
//    	//A faire
//    	//getseuil();
//    	//A modifier
//    	//getcalculIndice();
//    	int alerte1 = 0;
//    	getindice();
//    	int a =indiceATMO.getIndice();
//    	int d = 5;
//    	int c = 4;
//    	System.out.println(a);
//    	getdistrictseuil();
//    	int b =seuildistrict.getSeuilQuartierATMO();
//    	System.out.println(b);
//	
//    	if(c < d) {
//    		alerte1 = 1;
//    	}else {
//    		alerte1 = 0;
//    	}
//    	return alerte1;
//    }

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
//	public void sleep(int a) {
//		int jtf2 = a * 1000;
//		Timer timer;
//		timer = new Timer();
//		timer.schedule(new myTask(), 0, jtf2);
//	}
    
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
				a.dispose();
				int jtf1 = Integer.parseInt(jtf.getText());
				MyTask b = new MyTask(jtf1); 
				 // lancement de ce thread par appel à sa méthode start()
				b.start();
				 // cette méthode rend immédiatement la main...
				System.out.println("Thread lancé") ;
			}
		});
		a.add(panel1);
		panel1.add(label1);
		panel1.add(jtf);
		panel1.add(label2);
		panel1.add(btnNewButton);
		return a;
	}
    
//   public void getindiceville(int jtf2){
//		int jtf3 = jtf2 * 1000;
//		
//		//TestTimer(jtf3);
//	}
	public String historique() throws UnknownHostException, IOException, JSONException {
	   int b = getnumquart();
	   String s = null;
	   if(b == 3) {
		   s = "Pour le quartier_0, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_1, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_2, nous avons un indice de" +getCalculIndice();
	   }else if(b ==6) {
		   s = "Pour le quartier_0, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_1, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_2, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_3, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_4, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_5, nous avons un indice de" +getCalculIndice();
	   }else if (b==10) {
		   s = "Pour le quartier_0, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_1, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_2, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_3, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_4, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_5, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_6, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_7, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_8, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_9, nous avons un indice de" +getCalculIndice();
	   }else if (b==15) {
		   s = "Pour le quartier_0, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_1, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_2, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_3, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_4, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_5, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_6, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_7, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_8, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_9, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_10, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_11, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_12, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_13, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_14, nous avons un indice de" +getCalculIndice();	   
	   }else if (b ==20) {
		   s = "Pour le quartier_0, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_1, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_2, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_3, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_4, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_5, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_6, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_7, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_8, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_9, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_10, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_11, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_12, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_13, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_14, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_15, nous avons un indice de" +getCalculIndice()+"\n Pour le quartier_16, nous avons un indice de" +getCalculIndice()+"\n Pour le quartier_17, nous avons un indice de" +getCalculIndice()+"\n Pour le quartier_18, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_19, nous avons un indice de" +getCalculIndice();	
	   }
	   return s;
   }

	//interface indice du quartier 
	public JFrame indicequartier(String e) throws UnknownHostException, IOException, JSONException {
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
		int c = 4; 
		if (a > c) {
			bouton1 = new JButton("Alerte !!! L'indice relevé est supérieur au seuil");
    	    getContentPane().add(bouton1, "North");
    	    getContentPane().setLayout(null);
    	    bouton1.setBackground(Color.red);
    	    c++;
		}else {
			bouton1 = new JButton("Tout va bien, aucun problème détecté");
    	    getContentPane().add(bouton1, "North");
    	    getContentPane().setLayout(null);
    	    bouton1.setBackground(Color.green);
		}
		JLabel label1 = new JLabel();
		label1.setText("<html><body><p><p><p><p><p><p><p><p><p><p><p><p>" 
                 + "Nous avons un indice ATMO de " + a + " pour le quartier " + e 
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

//    public class myTask extends TimerTask{
//    		public void run() {
//    			 new Date();
//    			 getCalculIndice();
//    		}
//    }
	
	class MyTask  extends Thread {
		// surcharge de la méthode run() de la classe Thread
		private int a;
		public MyTask(int a) {
			this.a = a * 1000;
		}
	    public  void run() {
	    	int n = 0;
	    	while(n < 100) {
	         System.out.println("\n ATTENTE !") ;
	          try {
	            Thread.sleep(a);
					try {
						historique();
					} catch (IOException | JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	         }  catch (InterruptedException e) {
	             // gestion de l'erreur
	         }
	      }
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
    	System.out.println("Indice Ozone : " + indiceOzone);

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
		System.out.println("Indice soufre : " + indiceSoufre);
    			   	
    			   	
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
		System.out.println("Particules Fines: " + indiceParticule);

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
		System.out.println("Indice Dioxyde d'azote: " + indiceAzote);

		int minVal = Integer.MIN_VALUE;

		int array[] = { indiceOzone, indiceParticule, indiceAzote, indiceSoufre };

		for (int i = 0; i < array.length; i++) {
			if (array[i] > minVal)
				minVal = array[i];
		}
		//Historique(minVal);
		System.out.print("\nIndiceATMO = " + minVal+"\n\n");
		return minVal;
	}
      
		
    

//donne quartier
    public int getnumquart() throws UnknownHostException, IOException, JSONException {
    	getQuartier();
    	int Quartier = 0;
    	double b = tailleQuartier.getTailleCity();
    	int c = (int)b;

    	if(c > 0 && 11 >= c) {
    		Quartier = 3;
    	}else if(c >= 12 && 25 >= c) {
    		Quartier = 6;
    	}else if(c >= 26 && 50 >= c) {
    		Quartier = 10;
    	}else if(c >= 51 && 100 >= c) {
    		Quartier = 15;
    		System.out.println("loulou");
    	}else if(c > 100) {
    		Quartier = 20;
    	}
    	addSetquartier(Quartier);
    	System.out.println("Il y a " +Quartier+ " quartiers dans la ville");
    	return Quartier;
    }

    public void getQuartier() throws UnknownHostException, IOException, JSONException{
    	String json;
    	int idcity = 1;
    	json  ="{request:{ operation_type: INFOCITY, target: city , idcity: "+ idcity + "}}" ;
    	
    	String jsonResponse = this.startConnection("172.31.249.22", 2400,json);
    	this.showresultquartier(jsonResponse);
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

		System.out.println("Taille de la ville: " +tailleQuartier.getTailleCity());

		u.add(tailleQuartier);
		res = u.toString();   

		return res;

	}
	
	
	District util = new District();
	public String addSetquartier(int a) throws UnknownHostException, IOException, JSONException {
		//int id = 1;
		for(int i = 1; i <= a; i++) {
			int id = i;
			String name = "Quartier_" + i;
			int seuilquartieratmo = 4;
			int etatalerte = 1;
		
		String json= "";
	
		util.setId(id);
		util.setName(name);
		util.setSeuilQuartierATMO(seuilquartieratmo);
		util.setEtatalterte(etatalerte);
		
		json  ="{request:{ operation_type: ADDQUARTIER, target: district , id: "+util.getId() + 
				", name: "+ util.getName() + ", seuilquartieratmo : "+ util.getSeuilQuartierATMO() + 
				", etatalerte : " + util.getEtatalterte() +"}} " ;
		this.startConnection("172.31.249.22", 2400,json);
		System.out.println("Nous allons enregistrer un par un les quartiers");
		
		}
		return (String) json;
	}

	 CapteurAir util1 = new CapteurAir();
	public String addSetcapteur() throws UnknownHostException, IOException, JSONException {
		//int id = 1;
		//for(int i = 1; i <= ; i++) {
			int idcapteur = 3;
			String datereleve = "l";
			int indiceatmo = 4;
		
		String json= "";
	
		util1.setId(idcapteur);
		util1.setDate(datereleve);
		util1.setIndice(indiceatmo);
		
		json  ="{request:{ operation_type: CAPTEURAIR, target: capteurair , idcapteur: "+util1.getId() + 
				", datereleve: "+ util1.getDate() + ", indiceatmo : " + util1.getIndice() +"}} " ; 
		this.startConnection("172.31.249.22", 2400,json);
		System.out.println("Nous allons enregistrer un par un les capteurs");
		
		//}
		return json;
	}

	

	public static void main(String[] args) throws JSONException, UnknownHostException, IOException { 
		myCapteur b = new myCapteur();
		//a.getconfigcapteur();
		//a.getconfigcapteur();
		 b.addSetcapteur();
		//a.getalerte();
		//a.startConnection("172.31.249.22", 2400);
		//capteur6 b = new capteur6();
		//b.getalerte(2);
		}
}

