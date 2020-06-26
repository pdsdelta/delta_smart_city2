package analyse_indicateur;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.toedter.calendar.JDateChooser;

import functionality_server.functionalityServer;

public class fenetre1 extends JFrame {
	public fenetre1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 795, 413);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		setContentPane(contentPane);
		this.setVisible(true);
		
		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setDateFormatString("yyyy-MM-dd");
		dateChooser_1.setBounds(127, 318, 139, 20);
		contentPane.add(dateChooser_1);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setBounds(127, 166, 139, 20);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
												
	//1number capteur d'air
	   JButton numberCapteurair = new JButton("  nombre de capteurAir totale :");
	   numberCapteurair.setBounds(67, 59, 238, 35);
		contentPane.add(numberCapteurair);
		numberCapteurair.setBackground(SystemColor.inactiveCaption);
		numberCapteurair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Indicator objet = null;
				try {
					objet = new Indicator(server);
					
					
				} catch (ClassNotFoundException | SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					String jsonReceived = objet.informationcapteurAir();
					objet.startConnection("172.31.249.22", 2400);
					JSONObject obj = new JSONObject(jsonReceived); 
					JSONObject objet1= obj.getJSONObject("request");
					 int data= objet1.getInt("Data");
					JOptionPane.showMessageDialog(null, "le nombre de capteur d'air est : " + data);
				} catch (HeadlessException | JSONException | SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		
	//2number motion sensor
				JButton NumberMotionsensor = new JButton(" nombre totale de capteurs de mouvement :");
				NumberMotionsensor.setBounds(336, 132, 245, 35);
				contentPane.add(NumberMotionsensor);
				NumberMotionsensor.setBackground(SystemColor.inactiveCaption);
				
				NumberMotionsensor.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						Indicator objet = null;
						try {
							objet = new Indicator(server);
						} catch (ClassNotFoundException | SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						try {
							String jsonReceived = objet.informotionsensor();
							objet.startConnection("172.31.249.22", 2400);
							JSONObject obj = new JSONObject(jsonReceived);
							JSONObject objet1= obj.getJSONObject("request");
							 int data= objet1.getInt("Data");
							
							JOptionPane.showMessageDialog(null, "le nombre de motionSensor est :" + data);
							
							
							
							ListeMotionSensor(evt);
							
							
							
							
						} catch (HeadlessException | JsonProcessingException | JSONException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
		
				
        // 3number  station		
				JButton NumberStation = new JButton("  nombre totale de station :");
				NumberStation.setBounds(336, 59, 245, 35);
				contentPane.add(NumberStation);
				NumberStation.setBackground(SystemColor.inactiveCaption);
				NumberStation.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						Indicator objet = null;
						try {
							objet = new Indicator(server);
						} catch (ClassNotFoundException | SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						try {
							
							String jsonReceived = objet.infostation();
							objet.startConnection("172.31.249.22", 2400);
							JSONObject obj = new JSONObject(jsonReceived);
							JSONObject objet1= obj.getJSONObject("request");
							int	data = objet1.getInt("Data");
							
							JOptionPane.showMessageDialog(null, "le nombre de station est :" + data);
						} catch (HeadlessException | JSONException | SQLException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				
	//4 NUMBER CAR	 calendrier
				
				JButton numberCar = new JButton("nombre de voiture pr\u00E9sente dans la ville :");
				numberCar.setBounds(67, 132, 238, 35);
				contentPane.add(numberCar);
				numberCar.setBackground(SystemColor.inactiveCaption);
				numberCar.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						
						Indicator objet = null;
						try {
							objet = new Indicator(server);
						} catch (ClassNotFoundException | SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						try {
							
							/*calendrier.getDay()
							calendar.getYear
							calendar.getMonth()
							//date='calendar.getYear-calendar.getMonth()-calendrier.getDay()';
							date'2020-12-7'*/
							DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							String date = dateFormat.format(dateChooser.getDate());
									//"-"+String.valueOf(dateChooser.getDate().getMonth()+1)+"-"+String.valueOf(dateChooser.getDate().getDay());
							System.out.println("hello "+date);
							
							String jsonReceived = objet.nbcars(date);
							objet.startConnection("172.31.249.22", 2400);
							JSONObject obj = new JSONObject(jsonReceived );
							JSONObject objet1= obj.getJSONObject("request");
							int data = objet1.getInt("Data");
							if(data == 0) {
							JOptionPane.showMessageDialog(null, "aucune nouvelle voiture n'est détectée ");
							}else{JOptionPane.showMessageDialog(null, "le nombre de car present dans la ville est :" + data);
							  }
						} catch (HeadlessException | JSONException | SQLException | IOException e1) {
							
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}
					
				});						
				
	//5 NUMBER TRAM	
				JButton numbrTram = new JButton(" nombre de tram :");
				numbrTram.setBounds(67, 216, 238, 35);
				contentPane.add(numbrTram);
				numbrTram.setBackground(SystemColor.inactiveCaption);
				numbrTram.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
							Indicator objet = null;
							try {
								objet = new Indicator(server);
							} catch (ClassNotFoundException | SQLException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							try { 
								String jsonReceived = objet.informationTram();
								objet.startConnection("172.31.249.22", 2400);
								JSONObject obj = new JSONObject(jsonReceived );
								JSONObject objet1= obj.getJSONObject("request");
								int data = objet1.getInt("Data");
								
								JOptionPane.showMessageDialog(null, "le nombre de tram est :" +data);
							} catch (HeadlessException | JSONException | SQLException | IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					}
				});					
				
	// 6 NUMBER BORNE	
				JButton NumberBorne = new JButton("nombre de borne active");
				NumberBorne.setBounds(336, 216, 245, 35);
				contentPane.add(NumberBorne);
				NumberBorne.setBackground(SystemColor.inactiveCaption);
		
				// 7 EMPREINTE CARBONE	
				
				JButton empreinteCarbone = new JButton("empreinte de carbone");
				empreinteCarbone.setBounds(67, 284, 238, 35);
				contentPane.add(empreinteCarbone);
				empreinteCarbone.setBackground(SystemColor.inactiveCaption);
				empreinteCarbone.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						Indicator objet = null;
						
						try {
							
							objet = new Indicator(server);
						} catch (ClassNotFoundException | SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						try {
							/*calendrier.getDay()
							calendar.getYear
							calendar.getMonth()
							//date='calendar.getYear-calendar.getMonth()-calendrier.getDay()';
							date'2020-12-7'*/
							DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							String date = dateFormat.format(dateChooser_1.getDate());
									//"-"+String.valueOf(dateChooser.getDate().getMonth()+1)+"-"+String.valueOf(dateChooser.getDate().getDay());
							System.out.println("hello "+date);
							
							
							
							String jsonReceived = objet.emprientecarbone(date);
							objet.startConnection("172.31.249.22", 2400);
							System.out.println("hello "+jsonReceived);
							JSONObject obj = new JSONObject(jsonReceived );
							JSONObject objet1= obj.getJSONObject("request");
							int data = objet1.getInt("Data");
							if(data==0) {
								JOptionPane.showMessageDialog(null, "aucune nouvelle empreinte carbone n'est donnée ");
							}else {JOptionPane.showMessageDialog(null, "l'empreinte de carbone est :" + data+"kg");
							}
						
						} catch (HeadlessException | JSONException | SQLException | IOException e1) {
						//	JOptionPane.showMessageDialog(null, );
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					
						
						
					}
				});
				
				
				
				
			
				contentPane.add(dateChooser);
				
				JButton statistics = new JButton(" Les statistiques :");
				statistics.setBackground(SystemColor.inactiveCaption);
				statistics.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						
						Statistics statistics = new Statistics();
						
					}
				});
				statistics.setBounds(336, 284, 245, 35);
				contentPane.add(statistics);
				
				JLabel lblNewLabel = new JLabel("    Bienvenue sur le site qui vous permettra de consulter les donner de la ville");
				lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));
				lblNewLabel.setBounds(127, 11, 454, 30);
				contentPane.add(lblNewLabel);
				
				JLabel lblNewLabel_1 = new JLabel("  Les informations :");
				lblNewLabel_1.setBounds(0, 41, 113, 14);
				contentPane.add(lblNewLabel_1);
				
				
				
				
				NumberBorne.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						Indicator objet = null;
						try {
							objet = new Indicator(server);
						} catch (ClassNotFoundException | SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						try {
							String jsonReceived = objet.informationBorne();
							objet.startConnection("172.31.249.22", 2400);
							JSONObject obj = new JSONObject(jsonReceived );
							JSONObject objet1= obj.getJSONObject("request");
							 
							int data = objet1.getInt("Data");
							JOptionPane.showMessageDialog(null, "le nombre de borne  est :" + data);
						} catch (HeadlessException| JSONException | SQLException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					
						try {
							ListeTerminal(evt);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
					}
				});
										
						
	}

	private void ListeTerminal(java.awt.event.ActionEvent evt) throws ClassNotFoundException, SQLException {                                           
		new BorneActif(this, true).setVisible(true);
	}
	
	private void ListeMotionSensor(java.awt.event.ActionEvent evt) throws ClassNotFoundException, SQLException {                                           
		new Nbr_motionSensor(this, true).setVisible(true);
	}
	
	
	
	
	private JPanel contentPane;
	private functionalityServer server;

	/**
	 * Launch the application.
	 */

	public void affichePage1() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					caracteristiquePage1();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { Page1 frame = new Page1();
	 * frame.setVisible(true); } catch (Exception e) { e.printStackTrace(); } } });
	 * }
	 */
	/**
	 * Create the frame.
	 */
	public void caracteristiquePage1() {

	
	}
}
