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
import javax.swing.table.DefaultTableModel;
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
import tram_line.transition;
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
	private JMenu onglet1 = new JMenu("Consulter la qualité d'air");
	private JMenu onglet2 = new JMenu ("Configurer Capteur");
	private JMenu onglet3 = new JMenu ("Historique");

	private JMenuItem case1 = new JMenuItem("Selectionner Quartier");
	private JMenuItem case2 = new JMenuItem("Indice de la ville");
	private JMenuItem case3 = new JMenuItem("Ajuster les seuils suivant les quartiers");
	private JMenuItem case4 = new JMenuItem("Déterminer l'intervalle de relevé");
	private JMenuItem case5 = new JMenuItem("Apercevoir l'historique des relevés");
	
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
    	setTitle("Consulter la qualité d'air");
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
					try {
						getintervalle();
					} catch (IOException | JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});

		case5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 try {
					 indicecapteur();
				} catch (IOException | JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
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
	JPanel panel1 = new JPanel();
	panel1.setLayout(new FlowLayout());
	panel1.setBackground(Color.white);
	JLabel label1 = new JLabel("Choisissez le quartier souhaité: ");
	JComboBox<String> liste1 = new JComboBox<String> (); //Création d'une liste déroulante.
	int b = getnumquart();
	if(b == 3) {
		liste1.addItem("Quartier_1");liste1.addItem("Quartier_2");liste1.addItem("Quartier_2");
	}else if(b == 6) {
		liste1.addItem("Quartier_1");liste1.addItem("Quartier_2");liste1.addItem("Quartier_3");liste1.addItem("Quartier_4");
		liste1.addItem("Quartier_5");liste1.addItem("Quartier_6");
	}else if(b == 10) {
		liste1.addItem("Quartier_1"); liste1.addItem("Quartier_2");liste1.addItem("Quartier_3");liste1.addItem("Quartier_4");liste1.addItem("Quartier_5");liste1.addItem("Quartier_6");
		liste1.addItem("Quartier_7");liste1.addItem("Quartier_8");liste1.addItem("Quartier_9");liste1.addItem("Quartier_10");
	}else if(b == 15) {
		liste1.addItem("Quartier_1");liste1.addItem("Quartier_2");liste1.addItem("Quartier_3");liste1.addItem("Quartier_4");liste1.addItem("Quartier_5");liste1.addItem("Quartier_6");liste1.addItem("Quartier_7");liste1.addItem("Quartier_8");
		liste1.addItem("Quartier_9");liste1.addItem("Quartier_10");liste1.addItem("Quartier_11");liste1.addItem("Quartier_12");liste1.addItem("Quartier_13");liste1.addItem("Quartier_14");liste1.addItem("Quartier_15");
	}else if(b > 20) {
		liste1.addItem("Quartier_1");liste1.addItem("Quartier_2");liste1.addItem("Quartier_3");liste1.addItem("Quartier_4");liste1.addItem("Quartier_5");liste1.addItem("Quartier_6");liste1.addItem("Quartier_7");liste1.addItem("Quartier_8");
		liste1.addItem("Quartier_9");liste1.addItem("Quartier_10");liste1.addItem("Quartier_11");liste1.addItem("Quartier_12");liste1.addItem("Quartier_13");liste1.addItem("Quartier_14");liste1.addItem("Quartier_15");liste1.addItem("Quartier_16");liste1.addItem("Quartier_17");
		liste1.addItem("Quartier_18");liste1.addItem("Quartier_19");liste1.addItem("Quartier_20");
	}

	JLabel label2 = new JLabel(
			"Par défaut les seuils des quartiers sont de 4 mais ils peuvent être changeable dans configuration du capteur");
	addSetquartier(b);
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
		label2.setText("<html><body><p><p><p>" + "Pour le quartier_1, nous avons un indice de " +getCalculIndice()+ "<p>" + "Pour le quartier_2, nous avons un indice de " +getCalculIndice()+ "<p>"
				+ "Pour le quartier_3, nous avons un indice de " +getCalculIndice()+ "<p>"+"<p>" + "Nous avons donc un indice dans la ville de 6"+ "</body></html>");
	} else if (c == 6) {
		label2.setText("<html><body><p><p><p><p><p>" + "Pour le quartier_1, nous avons un indice de " +getCalculIndice()+ "<p>" + "Pour le quartier_2, nous avons un indice de " +getCalculIndice()+ "<p>"
				+ "Pour le quartier_3, nous avons un indice de " +getCalculIndice()+ "<p>" +"Pour le quartier_4, nous avons un indice de " +getCalculIndice()+ "<p>"+"Pour le quartier_5, nous avons un indice de " +getCalculIndice()+ "<p>"+"Pour le quartier_6, nous avons un indice de " +getCalculIndice()+ "<p>"+"<p>" + "Nous avons donc un indice dans la ville de 6"+"</body></html>");
	} else if (c == 10) {
		label2.setText("<html><body><p><p><p><p><p>" + "Pour le quartier_1, nous avons un indice de " +getCalculIndice()+ "<p>"
				+ "Pour le quartier_2, nous avons un indice de " +getCalculIndice()+ "<p>" +"Pour le quartier_3, nous avons un indice de " +getCalculIndice()+ "<p>"+"Pour le quartier_4, nous avons un indice de " +getCalculIndice()+ "<p>"+"Pour le quartier_5, nous avons un indice de " +getCalculIndice()+ "<p>"+ "Pour le quartier_6, nous avons un indice de " +getCalculIndice()+"<p>"+"Pour le quartier_7, nous avons un indice de " +getCalculIndice()+"<p>" +"Pour le quartier_8, nous avons un indice de " +getCalculIndice()+ "<p>" +"Pour le quartier_9, nous avons un indice de " +getCalculIndice()+ "<p>"+ "Pour le quartier_10, nous avons un indice de " +getCalculIndice()+ "<p><p>"+ "Nous avons donc un indice dans la ville de 6"+"</body></html>");

	} else if (c == 15) {
		label2.setText("<html><body><p><p><p><p><p>"  + "Pour le quartier_1, nous avons un indice de " +getCalculIndice()+ "<p>"
				+ "Pour le quartier_2, nous avons un indice de " +getCalculIndice()+ "<p>" +"Pour le quartier_3, nous avons un indice de " +getCalculIndice()+ "<p>"+"Pour le quartier_4, nous avons un indice de " +getCalculIndice()+ "<p>"+"Pour le quartier_5, nous avons un indice de " +getCalculIndice()+ "<p>"+ "Pour le quartier_6, nous avons un indice de " +getCalculIndice()+"<p>"+"Pour le quartier_7, nous avons un indice de " +getCalculIndice()+"<p>" +"Pour le quartier_8, nous avons un indice de " +getCalculIndice()+ "<p>" +"Pour le quartier_9, nous avons un indice de " +getCalculIndice()+ "<p>" + "Pour le quartier_10, nous avons un indice de " +getCalculIndice()+ "<p>"+ "Pour le quartier_11, nous avons un indice de " +getCalculIndice()+ "<p>"+ "Pour le quartier_12, nous avons un indice de " +getCalculIndice()+ "<p>"+ "Pour le quartier_13, nous avons un indice de " +getCalculIndice()+ "<p>" + "Pour le quartier_14, nous avons un indice de " +getCalculIndice()+ "<p>"+ "Pour le quartier_15, nous avons un indice de " +getCalculIndice()+ "<p><p>"+ "Nous avons donc un indice dans la ville de 6"+"</body></html>");
	} else if (c > 20) {
		label2.setText("<html><body><p><p><p><p><p>" + "Pour le quartier_1, nous avons un indice de " +getCalculIndice()+ "<p>"
				+ "Pour le quartier_2, nous avons un indice de " +getCalculIndice()+ "<p>" +"Pour le quartier_3, nous avons un indice de " +getCalculIndice()+ "<p>"+"Pour le quartier_4, nous avons un indice de " +getCalculIndice()+ "<p>"+"Pour le quartier_5, nous avons un indice de " +getCalculIndice()+ "<p>"+ "Pour le quartier_6, nous avons un indice de " +getCalculIndice()+"<p>"+"Pour le quartier_7, nous avons un indice de " +getCalculIndice()+"<p>" +"Pour le quartier_8, nous avons un indice de " +getCalculIndice()+ "<p>" +"Pour le quartier_9, nous avons un indice de " +getCalculIndice()+ "<p>" + "Pour le quartier_10, nous avons un indice de " +getCalculIndice()+ "<p>"+ "Pour le quartier_11, nous avons un indice de " +getCalculIndice()+ "<p>"+ "Pour le quartier_12, nous avons un indice de " +getCalculIndice()+ "<p>"+ "Pour le quartier_13, nous avons un indice de " +getCalculIndice()+ "<p>" + "Pour le quartier_14, nous avons un indice de " +getCalculIndice()+ "<p>"+ "Pour le quartier_15, nous avons un indice de " +getCalculIndice()+ "<p>" +"Pour le quartier_16, nous avons un indice de " +getCalculIndice()+ "<p>" +"Pour le quartier_17, nous avons un indice de " +getCalculIndice()+ "<p>" +"Pour le quartier_18, nous avons un indice de " +getCalculIndice()+ "<p>" +"Pour le quartier_19, nous avons un indice de " +getCalculIndice()+ "<p>"+ "Pour le quartier_20, nous avons un indice de " +getCalculIndice()+ "<p><p>" +"Nous avons donc un indice dans la ville de 6"+"</body></html>");
	}
        a.add(panel);
        panel.add(label2);
        upSetcity();
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
		liste1.addItem("Quartier_1");liste1.addItem("Quartier_2");liste1.addItem("Quartier_3");
	}else if(b == 6) {
		liste1.addItem("Quartier_1");liste1.addItem("Quartier_2");liste1.addItem("Quartier_3");liste1.addItem("Quartier_4");liste1.addItem("Quartier_5");
		liste1.addItem("Quartier_6");
	}else if(b == 10) {
		liste1.addItem("Quartier_1"); liste1.addItem("Quartier_2");liste1.addItem("Quartier_3");liste1.addItem("Quartier_4");liste1.addItem("Quartier_5");liste1.addItem("Quartier_6");
		liste1.addItem("Quartier_7");liste1.addItem("Quartier_8");liste1.addItem("Quartier_9");liste1.addItem("Quartier_10");
	}else if(b == 15) {
		liste1.addItem("Quartier_1");liste1.addItem("Quartier_2");liste1.addItem("Quartier_3");liste1.addItem("Quartier_4");liste1.addItem("Quartier_5");liste1.addItem("Quartier_6");liste1.addItem("Quartier_7");liste1.addItem("Quartier_8");
		liste1.addItem("Quartier_9");liste1.addItem("Quartier_10");liste1.addItem("Quartier_11");liste1.addItem("Quartier_12");liste1.addItem("Quartier_13");liste1.addItem("Quartier_14");liste1.addItem("Quartier_15");
	}else if(b > 20) {
		liste1.addItem("Quartier_1");liste1.addItem("Quartier_2");liste1.addItem("Quartier_3");liste1.addItem("Quartier_4");liste1.addItem("Quartier_5");liste1.addItem("Quartier_6");liste1.addItem("Quartier_7");liste1.addItem("Quartier_8");
		liste1.addItem("Quartier_9");liste1.addItem("Quartier_10");liste1.addItem("Quartier_11");liste1.addItem("Quartier_12");liste1.addItem("Quartier_13");liste1.addItem("Quartier_14");liste1.addItem("Quartier_15");liste1.addItem("Quartier_16");liste1.addItem("Quartier_17");
		liste1.addItem("Quartier_18");liste1.addItem("Quartier_19");liste1.addItem("Quartier_20");
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
				comparaison(z, selected);
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
  
  public JFrame comparaison(int s, String a) throws UnknownHostException, IOException, JSONException {
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
		int c = getCalculIndice(); 
		if (c > s) {
			bouton1 = new JButton("Alerte !!! L'indice relevé est supérieur au seuil");
  	    getContentPane().add(bouton1, "North");
  	    getContentPane().setLayout(null);
  	    bouton1.setBackground(Color.red);
  	    int d = 1;
  	    upSetquartier(s, a, c, d);
		}else {
			bouton1 = new JButton("Tout va bien, aucun problème détecté");
  	    getContentPane().add(bouton1, "North");
  	    getContentPane().setLayout(null);
  	    bouton1.setBackground(Color.green);
  	    int d = 0;
  	    upSetquartier(s,a, c, d);
		}
		JLabel label1 = new JLabel();
		label1.setText("<html><body><p><p><p><p><p><p><p><p><p><p><p><p>" 
               + "Nous avons un indice ATMO de " + c + " pour le quartier " + a
               +"</body></html>" );
		b.add(panel2);
		panel2.add(bouton1);
		panel2.add(label1);
		return b;
	}
  
  
  
  public  void indicecapteur() throws UnknownHostException, IOException, JSONException {
		JFrame a = new JFrame();
		a.setLocationRelativeTo(null);
		a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    a.setTitle("Historique");
	    a.setSize(300, 120);
	    a.setVisible(true);
	    JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		panel1.setBackground(Color.white);
		int i =0;
			String columns[] = {"Id", "date", "indice", "name", "intervalle"};
		      String data[][] = new String[10][5];
		    int j = 1;
		      while(i<10) {
		    	getHistorique(j);
		        int b = util1.getId();
		        String m = String.valueOf(b);
		        String e = util1.getDate();
				int c = util1.getIndice();
				String p = String.valueOf(c);
				String f = util1.getName();
				int d = util1.getIntervalle();
				String v = String.valueOf(d);
		        data[i][0] = m;
		        data[i][1] = e;
		        data[i][2] = p;
		        data[i][3] = f;
		        data[i][4] = v;
		        i++;
		        j++;
		      }
		      
		DefaultTableModel model = new DefaultTableModel(data, columns);
		JTable table = new JTable(model);
	    table.setShowGrid(true);
	    table.setShowVerticalLines(true);
	    a.getContentPane().add(new JScrollPane(table));
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
    
    
	public JFrame getintervalle() throws UnknownHostException, IOException, JSONException {
		JFrame a = new JFrame();
		a.setTitle("sélectionner capteur");
		a.setSize(400, 400);
		a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		a.setLocationRelativeTo(null);
		a.setVisible(true);
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		panel1.setBackground(Color.white);
		JLabel label1 = new JLabel("Veuillez déterminer l'intervalle des relevés");
		JComboBox<String> liste1 = new JComboBox<String> (); //Création d'une liste déroulante.
		int b = getnumquart();
		if(b == 3) {
			liste1.addItem("Capteur_1");liste1.addItem("Capteur_2");liste1.addItem("Capteur_3");
		}else if(b == 6) {
			liste1.addItem("Capteur_1");liste1.addItem("Capteur_2");liste1.addItem("Capteur_3");liste1.addItem("Capteur_4");liste1.addItem("Capteur_5");
			liste1.addItem("Capteur_6");
		}else if(b == 10) {
			liste1.addItem("Capteur_1"); liste1.addItem("Capteur_2");liste1.addItem("Capteur_3");liste1.addItem("Capteur_4");liste1.addItem("Capteur_5");liste1.addItem("Capteur_6");
			liste1.addItem("Capteur_7");liste1.addItem("Capteur_8");liste1.addItem("Capteur_9");liste1.addItem("Capteur_10");
		}else if(b == 15) {
			liste1.addItem("Capteur_1");liste1.addItem("Capteur_2");liste1.addItem("Capteur_3");liste1.addItem("Capteur_4");liste1.addItem("Capteur_5");liste1.addItem("Capteur_6");liste1.addItem("Capteur_7");liste1.addItem("Capteur_8");
			liste1.addItem("Capteur_9");liste1.addItem("Capteur_10");liste1.addItem("Capteur_11");liste1.addItem("Capteur_12");liste1.addItem("Capteur_13");liste1.addItem("Capteur_14");liste1.addItem("Capteur_15");
		}else if(b > 20) {
			liste1.addItem("Capteur_1");liste1.addItem("Capteur_2");liste1.addItem("Capteur_3");liste1.addItem("Capteur_4");liste1.addItem("Capteur_5");liste1.addItem("Capteur_6");liste1.addItem("Capteur_7");liste1.addItem("Capteur_8");
			liste1.addItem("Capteur_9");liste1.addItem("Capteur_10");liste1.addItem("Capteur_11");liste1.addItem("Capteur_12");liste1.addItem("Capteur_13");liste1.addItem("Capteur_14");liste1.addItem("Capteur_15");liste1.addItem("Capteur_16");liste1.addItem("Capteur_17");
			liste1.addItem("Capteur_18");liste1.addItem("Capteur_19");liste1.addItem("Capteur_20");
		}
		jtf.getText();
		JLabel label2 = new JLabel("en secondes");
		JButton btnNewButton = new JButton("Valider");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				a.dispose();
				String select = (String) liste1.getSelectedItem();
				int jtf1 = Integer.parseInt(jtf.getText());
				MyTask b = null;
				try {
					b = new MyTask(jtf1, select);
				} catch (IOException | JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				 // lancement de ce thread par appel à sa méthode start()
				b.start();
				 // cette méthode rend immédiatement la main...
				System.out.println("Thread lancé") ;
			}
		});
		a.add(panel1);
		panel1.add(label1);
		panel1.add(liste1);
		panel1.add(jtf);
		panel1.add(label2);
		panel1.add(btnNewButton);
		return a;
	}
    
	public String nomquartier(String a){
		return a;
	}
	
	
	//public String historique() throws UnknownHostException, IOException, JSONException {
		//int b = 6;
	   //String s = null;
	  // if(b == 3) {
		//   s = "Pour le quartier_0, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_1, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_2, nous avons un indice de" +getCalculIndice();
	   //}else if(b ==6) {
		//   s = "Pour le quartier_0, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_1, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_2, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_3, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_4, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_5, nous avons un indice de" +getCalculIndice();
	   //}else if (b==10) {
		//   s = "Pour le quartier_0, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_1, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_2, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_3, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_4, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_5, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_6, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_7, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_8, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_9, nous avons un indice de" +getCalculIndice();
	  // }else if (b==15) {
		//   s = "Pour le quartier_0, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_1, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_2, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_3, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_4, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_5, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_6, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_7, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_8, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_9, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_10, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_11, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_12, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_13, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_14, nous avons un indice de" +getCalculIndice();	   
	  // }else if (b ==20) {
		//   s = "Pour le quartier_0, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_1, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_2, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_3, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_4, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_5, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_6, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_7, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_8, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_9, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_10, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_11, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_12, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_13, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_14, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_15, nous avons un indice de" +getCalculIndice()+"\n Pour le quartier_16, nous avons un indice de" +getCalculIndice()+"\n Pour le quartier_17, nous avons un indice de" +getCalculIndice()+"\n Pour le quartier_18, nous avons un indice de" +getCalculIndice()+ "\n Pour le quartier_19, nous avons un indice de" +getCalculIndice();	
	   //}
	  // return s;
   //}

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
    	    int d = 1;
    	    upQuartierindice(e, a, d);
		}else {
			bouton1 = new JButton("Tout va bien, aucun problème détecté");
    	    getContentPane().add(bouton1, "North");
    	    getContentPane().setLayout(null);
    	    bouton1.setBackground(Color.green);
    	    int d = 0;
    	    upQuartierindice(e, a, d);
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
	
	
	class MyTask  extends Thread {
		// surcharge de la méthode run() de la classe Thread
		private int a;
		private String b;
		public MyTask(int a, String b) throws UnknownHostException, IOException, JSONException {
			this.a = a * 1000;
			this.b = b;
		}
	    public  void run() {
	    	int n = 1;
	    	while(n < 11) {
	         System.out.println("\n ATTENTE !") ;
	          try {
	            Thread.sleep(a);
					try {
						addSetcapteur(a, n ,b);
						n++;
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
		
		String json= "";
	
		util.setId(id);
		util.setName(name);
		util.setSeuilQuartierATMO(seuilquartieratmo);
		
		json  ="{request:{ operation_type: ADDQUARTIER, target: district , id: "+util.getId() + 
				", name: "+ util.getName() + ", seuilquartieratmo : "+ util.getSeuilQuartierATMO() + "}} " ;
		this.startConnection("172.31.249.22", 2400,json);
		System.out.println("Nous allons enregistrer un par un les quartiers");
		
		}
		return (String) json;
	}

	
	public String upQuartierindice(String c, int a, int b) throws UnknownHostException, IOException, JSONException {
		String name = c;
		int indiceatmo = a;
		int etatalerte = b;
		
		String json= "";
		
		util.setName(name);
		util.setIndice(indiceatmo);
		util.setEtatalterte(etatalerte);
		
		
		json  ="{request:{ operation_type: UPDATEINDICE, target: district , name:"+ util.getName() + ", indiceatmo:"+ util.getIndice() + ", etatalerte : "+ util.getEtatalterte() +"}} " ;
		this.startConnection("172.31.249.22", 2400,json);
		System.out.println("Nous allons modifier l'indice des quartiers");
		return (String) json;
	}
	
	
	public String upSetquartier(int a, String b, int c, int d) throws UnknownHostException, IOException, JSONException {
			int seuilquartieratmo = a;
			String name = b;
			int indiceatmo = c;
			int etatalerte = d;
		
		String json= "";
	
		util.setName(name);
		util.setSeuilQuartierATMO(seuilquartieratmo);
		util.setIndice(indiceatmo);
		util.setEtatalterte(etatalerte);
		
		json  ="{request:{ operation_type: UPDATESEUIL, target: district , name:"+ util.getName() + ", seuilquartieratmo : "+ util.getSeuilQuartierATMO() + ", indiceatmo : "+ util.getIndice() + ", etatalerte : "+ util.getEtatalterte() +"}} " ;
		this.startConnection("172.31.249.22", 2400,json);
		System.out.println("Nous allons modifier le seuil des quartiers");
		return (String) json;
	}

	city util2 = new city();
	public String upSetcity() throws UnknownHostException, IOException, JSONException {
			int idcity = 1;
			int seuilatmocity = 6;
		
		String json= "";
	
		util2.setIdCity(idcity);
		util2.setSeuilAtmoCity(seuilatmocity);
		
		
		json  ="{request:{ operation_type: UPDATECITY, target: city , idcity:"+ util2.getIdCity() + ", seuilatmocity : "+ util2.getSeuilAtmoCity() +"}} " ;
		this.startConnection("172.31.249.22", 2400,json);
		System.out.println("Nous allons modifier le seuil des quartiers");
		return (String) json;
	}
	
	 CapteurAir util1 = new CapteurAir();
	public String addSetcapteur(int a, int c, String b) throws UnknownHostException, IOException, JSONException { 
			int idcapteur = c;
			int indiceatmo = getCalculIndice();
			String namecapteur = b;
			System.out.println(b);
			a = a / 1000;
			int intervalle = a;
		
		String json= "";
	
		util1.setId(idcapteur);
		util1.setIndice(indiceatmo);
		util1.setName(namecapteur);
		util1.setIntervalle(intervalle);
		
		json  ="{request:{ operation_type: CAPTEURAIR, target: capteurair , idcapteur: "+util1.getId() + 
				", indiceatmo : " + util1.getIndice() + ", namecapteur : " + util1.getName() + ", intervalle : " + util1.getIntervalle() +"}} " ; 
		this.startConnection("172.31.249.22", 2400,json);
		System.out.println("Nous allons enregistrer un par un les capteurs");
		return (String) json;
	}
	
	    
	    public void getHistorique(int a) throws UnknownHostException, IOException, JSONException{
	    	String json;
	    		int idcapteur = a;
		    	json  ="{request:{ operation_type: INFOCAPTEUR, target: capteurair , idcapteur: "+ idcapteur + "}}" ;
		    	
		    	String jsonResponse = this.startConnection("172.31.249.22", 2400,json);
		    	this.showresultcapteur(jsonResponse);
	    	}
	   
		public String showresultcapteur(String jsonResponse) throws JSONException, noDataInBase {
			String res = "Empty";
			JSONObject obj1 =new JSONObject(jsonResponse);
			List<CapteurAir> u1 = new ArrayList<CapteurAir>();
			System.out.println("DEBUT");
			JSONArray arr1 = obj1.getJSONArray("Data");

			if(arr1.length() == 0) {
				System.out.println("Il n'y a pas d'informations en base sur le capteur");
				dispose();
				throw new noDataInBase();
			}
			util1.setIntervalle(arr1.getJSONObject(0).getInt("intervalle"));
			util1.setDate(arr1.getJSONObject(0).getString("date"));
			util1.setIndice(arr1.getJSONObject(0).getInt("indice"));
			util1.setId(arr1.getJSONObject(0).getInt("id"));
			util1.setName(arr1.getJSONObject(0).getString("name"));
			
			u1.add(util1);
			res = u1.toString();

			System.out.println("idcapteur: " +util1.getId());
			System.out.println("date: " +util1.getDate());
			System.out.println("indice: " +util1.getIndice());
			System.out.println("name: " +util1.getName());
			System.out.println("intervalle: " +util1.getIntervalle());
			
			return res;
		}

		public void historique(int a, String b, int c, String d, int e) {
			
		}
		
	public static void main(String[] args) throws JSONException, UnknownHostException, IOException { 
		myCapteur b = new myCapteur();
		}
}