package analyse_indicateur;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.image.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;

import functionality_server.functionalityServer;
import gestion_borne.vue.TerminalListVue;

import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JCalendar;

public class fenetre1 extends JFrame {
	public fenetre1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 795, 413);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		setContentPane(contentPane);
		this.setVisible(true);

		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setBounds(127, 166, 139, 20);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
												
	//1number de capteur d'air
	   JButton numberCapteurair = new JButton("number capteurAir");
	   numberCapteurair.setBounds(87, 59, 218, 35);
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
					JSONObject obj = new JSONObject(jsonReceived);
					 int data= obj.getInt("Data");
					JOptionPane.showMessageDialog(null, "le nombre de capteur d'air est : " + data);
				} catch (HeadlessException | JsonProcessingException | JSONException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		
	//2number motion sensor
				JButton NumberMotionsensor = new JButton("number motionSensor");
				NumberMotionsensor.setBounds(336, 132, 218, 35);
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
							JSONObject obj = new JSONObject(jsonReceived);
							
							int	data = obj.getInt("Data");
							JOptionPane.showMessageDialog(null, "le nombre de motionSensor est :" + data);
							
							
							
							ListeMotionSensor(evt);
							
							
							
							
						} catch (HeadlessException | JsonProcessingException | JSONException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
		
				
        // 3number de station		
				JButton NumberStation = new JButton("number station");
				NumberStation.setBounds(336, 59, 218, 35);
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
							JSONObject obj = new JSONObject(jsonReceived);
							
							int	data = obj.getInt("Data");
							JOptionPane.showMessageDialog(null, "le nombre de station est :" + data);
						} catch (HeadlessException | JsonProcessingException | JSONException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				
	//4 NUMBER CAR	 calendrier
				
				JButton numberCar = new JButton("number Car");
				numberCar.setBounds(87, 132, 218, 35);
				contentPane.add(numberCar);
				numberCar.setBackground(SystemColor.inactiveCaption);
				numberCar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
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
							JSONObject obj = new JSONObject(jsonReceived );
							 
							int data = obj.getInt("Data");
							JOptionPane.showMessageDialog(null, "le nombre de car present dans la ville est :" + data);
						} catch (HeadlessException | JsonProcessingException | JSONException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}
					
				});						
				
	//5 NUMBER TRAM	
				JButton numbrTram = new JButton("number Tram");
				numbrTram.setBounds(87, 216, 218, 35);
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
								JSONObject obj = new JSONObject(jsonReceived );
								 
								int data = obj.getInt("Data");
								JOptionPane.showMessageDialog(null, "le nombre de tram est :" +data);
							} catch (HeadlessException | JsonProcessingException | JSONException | SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					}
				});					
				
	// 6 NUMBER BORNE	
				JButton NumberBorne = new JButton("number borne");
				NumberBorne.setBounds(336, 216, 218, 35);
				contentPane.add(NumberBorne);
				NumberBorne.setBackground(SystemColor.inactiveCaption);
				
			
				contentPane.add(dateChooser);
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
							JSONObject obj = new JSONObject(jsonReceived );
							 
							int data = obj.getInt("Data");
							JOptionPane.showMessageDialog(null, "le nombre de borne  est :" + data);
						} catch (HeadlessException | JsonProcessingException | JSONException | SQLException e1) {
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
