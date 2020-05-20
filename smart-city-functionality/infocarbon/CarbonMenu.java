package infocarbon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.fasterxml.jackson.core.JsonProcessingException;

import user.Users;


public class CarbonMenu extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel p = new JPanel();
	private JPanel cg = new JPanel();
	private JPanel es = new JPanel();
	private JPanel voitures = new JPanel(); 
	private JPanel motos = new JPanel();
	private JPanel trotinette = new JPanel();
	private JPanel passagers = new JPanel(); 
	private JPanel trams = new JPanel(); 
	private JPanel longTram = new JPanel(); 
	private JPanel kmMoyen = new JPanel();
	private JPanel hourService = new JPanel();
	private JPanel bus = new JPanel();
	private JPanel kmMoyenBus = new JPanel();
	private JPanel Metro = new JPanel();
	private JPanel Transilien = new JPanel();
	String[] tab_string = {"1", "2", "3", "4", "5", "6"};
	JButton[] tab_button = new JButton[tab_string.length];
	private JButton b1,b2,b3,b4,b5,b6; 
	private JRadioButton rb1, rb2 ;
	private CarbonOrder co;
	//Attribute of the Json to send to the server
	private String jsonClient; 
	private JLabel jtiec = new JLabel();
	private JLabel jtiecpriv = new JLabel();
	private JLabel jtiecpub = new JLabel();
	private JLabel jtiecnpass = new JLabel();
	private JLabel jtiecnpass2 = new JLabel();
	private JLabel jtiectram = new JLabel();
	private JLabel jtiecbus = new JLabel();
	private JLabel jtiectrot = new JLabel();
	private JLabel jtieccar = new JLabel();
	private JLabel jtiecmoto = new JLabel();
	private static CarbonMenu instance = null ;

	
	private CarbonMenu(){
		this.setBounds(0,0,750,750);// X,Y,Largeur, Longueur
		this.setTitle("Carbon info DELTA-City");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new FlowLayout());
		//getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		//getContentPane().setLayout(new GridLayout(4,3));
		JPanel pannel = new JPanel();
		//pannel.setBounds(x, y, width, height);
		pannel.setBackground(Color.yellow);
		pannel.setBorder(new TitledBorder("Choisissez une option"));
		getContentPane().add(pannel);
		ButtonGroup groupe = new ButtonGroup();
		rb1 = new JRadioButton("Calculer l'empreinte carbonne de la ville");
		groupe.add(rb1);
		rb1.addActionListener(this);
		pannel.add(rb1);
		rb2 = new JRadioButton("Estimer l'empreinte carbonne de la ville");
		groupe.add(rb2);
		rb2.addActionListener(this);
		pannel.add(rb2);
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.setBackground(Color.WHITE);
		p.setBorder(new TitledBorder("Liste des choix Possibles") );
		es.setLayout (new BoxLayout (es, BoxLayout.Y_AXIS)); 
		es.setBackground(Color.WHITE);
		es.setBorder(new TitledBorder("Estimer l'empreinte carbonne pour une journée") );
		cg.setLayout(new BoxLayout (cg, BoxLayout.Y_AXIS));
		//this.getContentPane().add(BorderLayout.SOUTH,p);
		//this.getContentPane().add(p);
		b1 = new JButton("Empreinte carbonne globale pour la date d'hier");
		b2 = new JButton("Empreite carbonne globale de la ville pour une date souhaitée");
		b3 = new JButton("Empreinte carbonne pour les transports publics pour une date souhaitée");
		b4 = new JButton("Empreinte carbonne pour les transports privées pour une date souhaitée");
		b5 = new JButton("Estimer l'empreinte carbonne");
		b6 = new JButton("Calculer");
		
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  }
	
	public static CarbonMenu getInstance(){
		
		if (instance == null) {
			instance = new CarbonMenu();
		}  
		return instance ;
	}
	public void setJsonClient(String json) {
		this.jsonClient = json;
	}
	
	public String getJsonClient() {
		return this.jsonClient;
	}
	
	public void actionPerformed(ActionEvent event) {
		Object o = event.getSource();
		if (o == rb1) {
			
			
			es.removeAll();
			cg.removeAll();
			p.removeAll();
			getContentPane().remove(jtiectram);
			getContentPane().remove(jtiecbus);
			getContentPane().remove(jtiectrot);
			getContentPane().remove(jtiecpub);
			getContentPane().remove(jtiecnpass);
			getContentPane().remove(jtieccar);
			getContentPane().remove(jtiecmoto);
			getContentPane().remove(jtiecpriv);
			getContentPane().remove(jtiecnpass2);
			getContentPane().remove(jtiec);
			getContentPane().remove(es);
			getContentPane().remove(p);
			p.add(b1);
			p.add(b2);
			p.add(b3);
			p.add(b4);
			getContentPane().add(p);
			getContentPane().revalidate();
			getContentPane().repaint();
			b1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cg.removeAll();
					getContentPane().add(cg);
					Users u = new Users();
					co = new CarbonOrder(1,u);
					String res;
					try {
						res = co.generateJson();
						System.out.println("Calcul de l'empreinte carbonne globale pour hier");
						System.out.println("Le json qui va etre envoyé au serveur");
						System.out.println(res);
						jsonClient=res;
						String resp = CarbonInfo.getInstance().sendMessage(res);
						InfoCarbon ic = CarbonInfo.getInstance().responseToInfoCarbon(resp);
						if(ic==null) {
							JLabel emp = new JLabel("Aucune donnée pour la date d'hier");
							cg.add(emp);
						}else {
							double resul = ic.calculateCarbon();
							JLabel emp = new JLabel("L'empreinte carbonne globale de la ville est de : "+ resul + " Kg de CO2");
							cg.add(emp);
						}
						getContentPane().add(cg);
						getContentPane().revalidate();
						getContentPane().repaint();
						setVisible(true);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					setVisible(true);
					
				}

				
			});
			
			b2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					cg.removeAll();
					JLabel jt = new JLabel("Veuillez choisir la date souhaitée");
					cg.add(jt);
					JDatePickerImpl datePicker = generateDatePicker();
					cg.add(datePicker);
					JButton but = new JButton("submit"); 
					cg.add(but);
					getContentPane().add(cg);
					but.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							cg.removeAll();
							Date dd = (Date) datePicker.getModel().getValue();
							DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
							DateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd");
							String d ="";
							if(dd == null) {
								d ="";
								System.out.println(d);
							}else {
								d = dateFormat.format(dd);
								System.out.println("Date entrée :" + d);
							}
							JLabel jtt = new JLabel("Chargement");
							cg.add(jtt);
							boolean b = validateJavaDate(d);
							if(b) {
								boolean bb = compareDate(d);
								if(bb) {
									cg.remove(jtt);
									jtt = new JLabel("Calcul de l'empreinte carbonne globale pour la date du "+ d);
									cg.add(jtt);
									getContentPane().add(cg);
									Users u = new Users();
									CarbonOrder co2 = new CarbonOrder(2,u);
									String dSql = dateFormatSQL.format(dd);
									co2.setDate(dSql);
									String res;
									try {
										res = co2.generateJson();
										System.out.println("Le json qui va etre envoyé au serveur : ");
										System.out.println(res);
										jsonClient = res;
										String resp = CarbonInfo.getInstance().sendMessage(res);
										InfoCarbon ic = CarbonInfo.getInstance().responseToInfoCarbon(resp);
										if(ic==null) {
											JLabel emp = new JLabel("Aucune donnée pour la date :  "+ d);
											cg.add(emp);
										}else {
											double resul = ic.calculateCarbon();
											JLabel emp = new JLabel("L'empreinte carbonne globale est de : "+ resul + " Kg de CO2");
											cg.add(emp);
										}
										getContentPane().add(cg);
										getContentPane().revalidate();
										getContentPane().repaint();
										setVisible(true);
										System.out.println("*******************");
									} catch (IOException e1) {
										e1.printStackTrace();
									}
									setVisible(true);
								}else {
									cg.removeAll();
									jtt = new JLabel("Veuillez entrez une antérieure à celle d'aujourd'hui");
									cg.add(jtt);
									getContentPane().add(cg);
									getContentPane().revalidate();
									getContentPane().repaint();
									setVisible(true);
									
								}
							}else {
								cg.remove(jtt);
								jtt = new JLabel("Veuillez entrez une date valide sous le format JJ/MM/AAAA");
								cg.add(jtt);
								getContentPane().add(cg);
								setVisible(true);
							}
							//System.out.println(d);
						}
					});
					
					
					

					
					setVisible(true);
					
				}
				
			});
			b3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					
					cg.removeAll();
					JLabel jt = new JLabel("Veuillez choisir la date souhaitée");
					cg.add(jt);
					JDatePickerImpl datePicker = generateDatePicker();
					cg.add(datePicker);
					JButton but = new JButton("submit"); 
					cg.add(but);
					getContentPane().add(cg);
					but.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							cg.removeAll();
							getContentPane().add(cg);
							
							Date dd = (Date) datePicker.getModel().getValue();
							DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
							DateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd");
							String d ="";
							if(dd == null) {
								d ="";
								System.out.println(d);
							}else {
								d = dateFormat.format(dd);
								System.out.println("Date entrée :" + d);
							}
							
							boolean b = validateJavaDate(d);
							if(b) {
								boolean bb = compareDate(d);
								if(bb) {
									JLabel jtt = new JLabel("Calcul de l'empreinte carbonne des transports publics pour la date du "+ d);
									cg.add(jtt);
									getContentPane().add(cg);
									Users u = new Users();
									CarbonOrder co2 = new CarbonOrder(3,u);
									String dSql = dateFormatSQL.format(dd);
									co2.setDate(dSql);
									String res;
									try {
										res = co2.generateJson();
										System.out.println("Le json qui va etre envoyé au serveur : ");
										System.out.println(res);
										jsonClient = res;
										//CarbonInfo.getInstance().afficheMenuAndGetJson();
										String resp = CarbonInfo.getInstance().sendMessage(res);
										InfoCarbon ic = CarbonInfo.getInstance().responseToInfoCarbon(resp);
										if(ic==null) {
											JLabel emp = new JLabel("Aucune donnée pour la date :  "+ d);
											cg.add(emp);
										}else {
											double resul = ic.calculateCarbon();
											JLabel emp = new JLabel("L'empreinte carbonne est de : "+ resul + " Kg de CO2");
											cg.add(emp);
			
										}
										getContentPane().add(cg);
										getContentPane().revalidate();
										getContentPane().repaint();
										setVisible(true);
										System.out.println("*******************");
									} catch (IOException e1) {
										e1.printStackTrace();
									}
									
									setVisible(true);
								}else {
									cg.removeAll();
									JLabel jtt = new JLabel("Veuillez entrez une date valide et antérieure à celle d'aujourd'hui");
									cg.add(jtt);
									getContentPane().add(cg);
									getContentPane().revalidate();
									getContentPane().repaint();
									setVisible(true);
									
								}
							}else {
								cg.removeAll();
								JLabel jtt = new JLabel("Veuillez entrez une date valide sous le format JJ/MM/AAAA");
								cg.add(jtt);
								getContentPane().add(cg);
								setVisible(true);
							}
							
						
						}
					});
					
					
					

					
					setVisible(true);
					
				
				}
			});
			b4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					cg.removeAll();
					JLabel jt = new JLabel("Veuillez choisir la date souhaitée");
					cg.add(jt);
					JDatePickerImpl datePicker = generateDatePicker();
					cg.add(datePicker);
					JButton but = new JButton("submit"); 
					cg.add(but);
					getContentPane().add(cg);
					but.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							cg.removeAll();
							Date dd = (Date) datePicker.getModel().getValue();
							DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
							DateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd");
							String d ="";
							if(dd == null) {
								d ="";
								System.out.println(d);
							}else {
								d = dateFormat.format(dd);
								System.out.println("Date entrée :" + d);
							}
							boolean b = validateJavaDate(d);
							if(b) {
								boolean bb = compareDate(d);
								if(bb) {
									JLabel jtt = new JLabel("Calcul de l'empreinte carbonne des transports privés pour la date du "+ d);
									cg.add(jtt);
									getContentPane().add(cg);
									Users u = new Users();
									CarbonOrder co2 = new CarbonOrder(4,u);
									String dSql = dateFormatSQL.format(dd);
									co2.setDate(dSql);
									String res;
									try {
										res = co2.generateJson();
										System.out.println("Le json qui va etre envoyé au serveur : ");
										System.out.println(res);
										jsonClient = res;
										String resp = CarbonInfo.getInstance().sendMessage(res);
										InfoCarbon ic = CarbonInfo.getInstance().responseToInfoCarbon(resp);
										if(ic==null) {
											JLabel emp = new JLabel("Aucune donnée trouvée pour la date : "+ d);
											cg.add(emp);
										}else {
											double resul = ic.calculateCarbon();
											
											JLabel emp = new JLabel("L'empreinte carbonne pour les transports privée est de : "+ resul +" Kg de CO2");
											cg.add(emp);
										}
										getContentPane().add(cg);
										getContentPane().revalidate();
										getContentPane().repaint();
										setVisible(true);
										System.out.println("*******************");
										//System.out.println(jsonClient);
									} catch (IOException e1) {
										e1.printStackTrace();
									}
									setVisible(true);
								}else {
									JLabel jtt = new JLabel("Veuillez entrez une date valide et antérieure à celle d'aujourd'hui");
									getContentPane().add(cg);
									getContentPane().revalidate();
									getContentPane().repaint();
									cg.add(jtt);
									setVisible(true);
									
								}
							}else {
								JLabel jtt = new JLabel("Veuillez entrez une date valide sous le format JJ/MM/AAAA");
								cg.add(jtt);
								getContentPane().add(cg);
								setVisible(true);
							}
							
						}
					});
					
					setVisible(true);
					
				}
				
			});
			
			
			
			
			
			setVisible(true);
		}
		if (o == rb2) {
			cg.removeAll();
			p.removeAll();
			es.removeAll();
			getContentPane().remove(jtiecpriv);
			getContentPane().remove(jtiecpub);
			getContentPane().remove(jtiec);
			getContentPane().remove(p);
			getContentPane().remove(cg);
			getContentPane().remove(es);
			p.add(b5);
			getContentPane().add(p);
			getContentPane().revalidate();
			getContentPane().repaint();
			b5.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Voiture input
					voitures.removeAll();
					JLabel label = new JLabel();		
					label.setText("Entrez le nombre de voitures :");
					label.setBounds(10, 10, 100, 100);
					JTextField nCars = new JTextField();
					nCars.setColumns(10);
					voitures.add(label);
					voitures.add(nCars);
					es.add(voitures);
					//Passagers voitures input
					passagers.removeAll();
					JLabel passlabel = new JLabel();		
					passlabel.setText("Entrez le nombre de passagers moyen par voitures :");
					passlabel.setBounds(10, 10, 100, 100);
					JTextField npass = new JTextField();
					npass.setColumns(10);
					passagers.add(passlabel);
					passagers.add(npass);
					es.add(passagers);
					//Distance moyenne
					kmMoyen.removeAll();
					JLabel kmLabel = new JLabel();
					kmLabel.setText("Entrez la distance moyenne parcouru en km par une voiture pour une journée");
					kmLabel.setBounds(10, 10, 100, 100);
					JTextField nkm = new JTextField();
					nkm.setColumns(10);
					kmMoyen.add(kmLabel);
					kmMoyen.add(nkm);
					es.add(kmMoyen);
					//Motos
					motos.removeAll();
					JLabel motosLabel = new JLabel();
					motosLabel.setText("Entrez le nombre de motos : ");
					motosLabel.setBounds(10, 10, 100, 100);
					JTextField nmotos = new JTextField();
					nmotos.setColumns(10);
					motos.add(motosLabel);
					motos.add(nmotos);
					es.add(motos);
					//Trotinettes
					trotinette.removeAll();
					JLabel trotinetteLabel = new JLabel();
					trotinetteLabel.setText("Entrez le nombre de trotinettes eléctriques : ");
					trotinetteLabel.setBounds(10, 10, 100, 100);
					JTextField ntrotinette = new JTextField();
					ntrotinette.setColumns(10);
					trotinette.add(trotinetteLabel);
					trotinette.add(ntrotinette);
					es.add(trotinette);
					//Tramway
					trams.removeAll();
					JLabel tramslabel = new JLabel();		
					tramslabel.setText("Entrez le nombre de Tramway déployés :");
					tramslabel.setBounds(10, 10, 100, 100);
					JTextField ntrams = new JTextField();
					ntrams.setColumns(10);
					trams.add(tramslabel);
					trams.add(ntrams);
					es.add(trams);
					//Longueur Tram
					longTram.removeAll();
					JLabel longTramlabel = new JLabel();		
					longTramlabel.setText("Entrez la longeur de la ligne de Tramway :");
					longTramlabel.setBounds(10, 10, 100, 100);
					JTextField nlongtram = new JTextField();
					nlongtram.setColumns(10);
					longTram.add(longTramlabel);
					longTram.add(nlongtram);
					es.add(longTram);
					//Heures Tramway
					hourService.removeAll();
					JLabel hourServicelabel = new JLabel();		
					hourServicelabel.setText("Entrez le nombre d'heures de services du Tramway , ne pas dépasser 22h :");
					hourServicelabel.setBounds(10, 10, 100, 100);
					JTextField nhourService = new JTextField();
					nhourService.setColumns(10);
					hourService.add(hourServicelabel);
					hourService.add(nhourService);
					es.add(hourService);
					//Bus
					bus.removeAll();
					JLabel buslabel = new JLabel();		
					buslabel.setText("Entrez le nombre de bus :");
					buslabel.setBounds(10, 10, 100, 100);
					JTextField nbus = new JTextField();
					nbus.setColumns(10);
					bus.add(buslabel);
					bus.add(nbus);
					es.add(bus);
					//Bus Km
					kmMoyenBus.removeAll();
					JLabel kmMoyenLabel = new JLabel();
					kmMoyenLabel.setText("Entrez la distance moyenne parcouru en km par les bus pour une journée");
					kmMoyenLabel.setBounds(10, 10, 100, 100);
					JTextField nkmb = new JTextField();
					nkmb.setColumns(10);
					kmMoyenBus.add(kmMoyenLabel);
					kmMoyenBus.add(nkmb);
					es.add(kmMoyenBus);
					//Button Calculer
					es.add(b6);
					//Adding 
					getContentPane().add(es);
					getContentPane().revalidate();
					getContentPane().repaint();
					setVisible(true); 
					//Adding 
					b6.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							getContentPane().remove(jtiectram);
							getContentPane().remove(jtiecbus);
							getContentPane().remove(jtiectrot);
							getContentPane().remove(jtiecpub);
							getContentPane().remove(jtiecnpass);
							getContentPane().remove(jtieccar);
							getContentPane().remove(jtiecmoto);
							getContentPane().remove(jtiecpriv);
							getContentPane().remove(jtiecnpass2);
							getContentPane().remove(jtiec);
							getContentPane().revalidate();
							getContentPane().repaint();
							es.remove(jtiecpriv);
							es.remove(jtiecpub);
							es.remove(jtiec);
							es.revalidate();
							es.repaint();
							int nv,np,nm,ntt,nt,hs,nb;
							double lt, km, kmb;
							InfoEstimatedCarbon iec;
							try {
								
								nv = Integer.parseInt(nCars.getText());
								np = Integer.parseInt(npass.getText());
								nm = Integer.parseInt(nmotos.getText());
								ntt = Integer.parseInt(ntrotinette.getText());
								km = Double.parseDouble(nkm.getText());
								nt = Integer.parseInt(ntrams.getText());
								lt= Double.parseDouble(nlongtram.getText());
								hs = Integer.parseInt(nhourService.getText());
								nb = Integer.parseInt(nbus.getText());
								kmb = Double.parseDouble(nkmb.getText());
								
								if (np > 5) {
									JOptionPane.showMessageDialog(null, "Le nombre de passager moyen ne doit pas dépasser 5 personnes");
		
									getContentPane().remove(jtiectram);
									getContentPane().remove(jtiecbus);
									getContentPane().remove(jtiectrot);
									getContentPane().remove(jtiecpub);
									getContentPane().remove(jtiecnpass);
									getContentPane().remove(jtieccar);
									getContentPane().remove(jtiecmoto);
									getContentPane().remove(jtiecpriv);
									getContentPane().remove(jtiecnpass2);
									getContentPane().remove(jtiec);
									getContentPane().revalidate();
									getContentPane().repaint();
					
				
								}else if (nv <= 0 || np < 0 || lt < 0 || hs < 0 || km < 0 || nm < 0 || ntt < 0 || nb <0) {
									JOptionPane.showMessageDialog(null, "Les nombres doivent être positifs");
									
									getContentPane().remove(jtiectram);
									getContentPane().remove(jtiecbus);
									getContentPane().remove(jtiectrot);
									getContentPane().remove(jtiecpub);
									getContentPane().remove(jtiecnpass);
									getContentPane().remove(jtieccar);
									getContentPane().remove(jtiecmoto);
									getContentPane().remove(jtiecpriv);
									getContentPane().remove(jtiecnpass2);
									getContentPane().remove(jtiec);
									getContentPane().revalidate();
									getContentPane().repaint();
					
								}else if (hs > 22) {
									JOptionPane.showMessageDialog(null, "Le nombre d'heures de service ne doit pas dépasser 22");
									getContentPane().remove(jtiectram);
									getContentPane().remove(jtiecbus);
									getContentPane().remove(jtiectrot);
									getContentPane().remove(jtiecpub);
									getContentPane().remove(jtiecnpass);
									getContentPane().remove(jtieccar);
									getContentPane().remove(jtiecmoto);
									getContentPane().remove(jtiecpriv);
									getContentPane().remove(jtiecnpass2);
									getContentPane().remove(jtiec);
									getContentPane().revalidate();
									getContentPane().repaint();
								}else {
									iec = new InfoEstimatedCarbon(1,nv,np,km,nm,ntt,nt,lt,hs,nb,kmb);
									DecimalFormat df = new DecimalFormat ( ) ; 
									df.setMaximumFractionDigits ( 2 ) ; 
									double glob = iec.calculateCarbon();
									jtiectram.setText("L'empreinte carbonne des tramways est éstimée à :" + df.format(iec.getEct())+" Kg de CO2");
									jtiecbus.setText("L'empreinte carbonne des bus est éstimée à :" + df.format(iec.getEcb())+" Kg de CO2");
									jtiectrot.setText("L'empreinte carbonne des trotinettes éléctriques est éstimée à :" + df.format(iec.getEctt())+" Kg de CO2");
									jtiecpub.setText("L'empreinte carbonne des transports publics est éstimée à :" + df.format(iec.getEcpub())+" Kg de CO2");
									jtiecnpass.setText("************************************************************************");
									jtieccar.setText("L'empreinte carbonne des voitures est éstimée à :" + df.format(iec.getEcc())+" Kg de CO2");
									jtiecmoto.setText("L'empreinte carbonne des motos est éstimée à :" + df.format(iec.getEcm())+" Kg de CO2");
									jtiecpriv.setText("L'empreinte carbonne des transports privées est éstimée à :" + df.format(iec.getEcpriv()) + " Kg de CO2");
									jtiecnpass2.setText("************************************************************************");
									jtiec.setText("L'empreinte carbonne globale est éstimée à : "+ df.format(glob) +" Kg de CO2");
									
									
									getContentPane().add(jtiectram);
									getContentPane().add(jtiecbus);
									getContentPane().add(jtiectrot);
									getContentPane().add(jtiecpub);
									getContentPane().add(jtiecnpass);
									getContentPane().add(jtieccar);
									getContentPane().add(jtiecmoto);
									getContentPane().add(jtiecpriv);
									getContentPane().add(jtiecnpass2);
									getContentPane().add(jtiec);
									getContentPane().revalidate();
									getContentPane().repaint();
								}
								
							} catch (Exception e1) {
								JOptionPane.showMessageDialog(null, "Veuillez entrer des nombre");
								iec =null ;
								getContentPane().remove(jtiectram);
								getContentPane().remove(jtiecbus);
								getContentPane().remove(jtiectrot);
								getContentPane().remove(jtiecpub);
								getContentPane().remove(jtiecnpass);
								getContentPane().remove(jtieccar);
								getContentPane().remove(jtiecmoto);
								getContentPane().remove(jtiecpriv);
								getContentPane().remove(jtiecnpass2);
								getContentPane().remove(jtiec);
								getContentPane().revalidate();
								getContentPane().repaint();
							}
							
							
						}
						
						
					});
					
				}
				});
				
			setVisible(true);
		}
		
	}
	
	//Function of Date Picker
	public static JDatePickerImpl generateDatePicker() {
		UtilDateModel model = new UtilDateModel();
		Properties prop = new Properties();
		prop.put("text.today", "Today");
		prop.put("text.month", "Month");
		prop.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, prop);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		return datePicker;
	}
	//Function which validate the date format
		public static boolean validateJavaDate(String date)
		   {
			/* Check if date is 'null' */
			if (date.trim().equals(""))
			{
				System.out.println("L'entrée est vide\n");
			    return false;
			}
			/* Date is not 'null' */
			else
			{
			    /*
			     * Set preferred date format,
			     * dd/MM/yyyy */
			    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			    formatter.setLenient(false);
			    try
			    {
			        Date javaDate = formatter.parse(date); 
			        
			    }
			    /* Date format is invalid */
			    catch (ParseException e)
			    {
			    	System.out.println("L'entrée est invalide\n");
			        return false;
			    }
			    /* Return true if date format is valid */
			    System.out.println("L'entrée est valide\n");
			    return true;
			}
		   }
		
	//Function which allow to compare two dates 
	public static boolean compareDate(String date) {
		  SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		  Date dateToday = new Date();  
	      Date d1;
	      
	      boolean res = false ;
	      try {
	    	  d1 = formatter.parse(date);
		      if(d1.compareTo(dateToday) < 0) {
		    	 res = true;
		       }

	      } catch (ParseException e) {
			e.printStackTrace();
	      }
		return res;
	      
	       
	   }
	
	public static void main(String[] args) {
		CarbonMenu f = new CarbonMenu();
		f.setVisible(true);
		
	}

}
