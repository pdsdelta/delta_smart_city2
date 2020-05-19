package analyse_indicateur;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.image.*;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;

import functionality_server.functionalityServer;

import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.SystemColor;

public class Page1 extends JFrame {
	public Page1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 795, 413);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		setContentPane(contentPane);
		this.setVisible(true);

		JButton btnNewButton = new JButton("ADD");
		btnNewButton.setBounds(577, 59, 89, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int num1, num2, ans;
				try {
					num1 = Integer.parseInt(textFieldNum1.getText());
					num2 = Integer.parseInt(textFieldNum2.getText());
					ans = num1 + num2;
					textFieldAns.setText(Integer.toString(ans));
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "please enter valid number");
				}
			}
		});
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Compare");
		btnNewButton_1.setBounds(680, 59, 89, 23);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int num1, num2, ans;
				try {
					num1 = Integer.parseInt(textFieldNum1.getText());
					num2 = Integer.parseInt(textFieldNum2.getText());
					ans = num1 - num2;
					if (ans < 0) {
						textFieldAns.setText(num1 + " est plus petit que " + num2);
					} else if (ans > 0) {
						textFieldAns.setText(num1 + " est plus grand que " + num2);
					} else {
						textFieldAns.setText("Il n y a pas d'amelioration ");

					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "please enter valid number");
				}

			}
		});
		getContentPane().add(btnNewButton_1);

		textFieldNum1 = new JTextField();
		textFieldNum1.setBounds(562, 11, 86, 20);
		getContentPane().add(textFieldNum1);
		textFieldNum1.setColumns(10);

		textFieldNum2 = new JTextField();
		textFieldNum2.setBounds(683, 11, 86, 20);
		getContentPane().add(textFieldNum2);
		textFieldNum2.setColumns(10);

		textFieldAns = new JTextField();
		textFieldAns.setBounds(593, 109, 219, 35);
		textFieldAns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Please Enter Valid Number");
			}
		});
		getContentPane().add(textFieldAns);
		textFieldAns.setColumns(10);

		JLabel lblNewLabel = new JLabel("  The Answer is :");
		lblNewLabel.setBounds(609, 78, 117, 30);
		lblNewLabel.setForeground(new Color(240, 255, 240));

		getContentPane().add(lblNewLabel);
												
	//1number de capteur d'air
	   JButton numberCapteurair = new JButton("number capteurAir");
		numberCapteurair.setBounds(87, 59, 218, 50);
		contentPane.add(numberCapteurair);
		numberCapteurair.setBackground(SystemColor.inactiveCaption);
		numberCapteurair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CRUD objet = null;
				try {
					objet = new CRUD(server);
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
				NumberMotionsensor.setBounds(336, 132, 218, 50);
				contentPane.add(NumberMotionsensor);
				NumberMotionsensor.setBackground(SystemColor.inactiveCaption);
				
				NumberMotionsensor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						CRUD objet = null;
						try {
							objet = new CRUD(server);
						} catch (ClassNotFoundException | SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						try {
							String jsonReceived = objet.informotionsensor();
							JSONObject obj = new JSONObject(jsonReceived);
							
							int	data = obj.getInt("Data");
							JOptionPane.showMessageDialog(null, "le nombre de motionSensor est :" + data);
						} catch (HeadlessException | JsonProcessingException | JSONException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
		
				
        // 3number de station		
				JButton numberStation = new JButton("number station");
				numberStation.setBounds(336, 59, 218, 49);
				contentPane.add(numberStation);
				numberStation.setBackground(SystemColor.inactiveCaption);
				numberStation.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						CRUD objet = null;
						try {
							objet = new CRUD(server);
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
				numberCar.setBounds(87, 132, 218, 50);
				contentPane.add(numberCar);
				numberCar.setBackground(SystemColor.inactiveCaption);
				numberCar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						CRUD objet = null;
						try {
							objet = new CRUD(server);
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
							JOptionPane.showMessageDialog(null, "le nombre de car present dans la ville est :" + objet.nbcars(date));
						} catch (HeadlessException | JsonProcessingException | JSONException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}
					
				});						
				
	//5 NUMBER TRAM	
				JButton numbrTram = new JButton("number Tram");
				numbrTram.setBounds(87, 216, 218, 50);
				contentPane.add(numbrTram);
				numbrTram.setBackground(SystemColor.inactiveCaption);
				numbrTram.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
							CRUD objet = null;
							try {
								objet = new CRUD(server);
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
				NumberBorne.setBounds(336, 216, 218, 50);
				contentPane.add(NumberBorne);
				NumberBorne.setBackground(SystemColor.inactiveCaption);
				NumberBorne.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						CRUD objet = null;
						try {
							objet = new CRUD(server);
						} catch (ClassNotFoundException | SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						try {
							JOptionPane.showMessageDialog(null, "le nombre de borne  est :" + objet.informationBorne());
						} catch (HeadlessException | JsonProcessingException | JSONException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					
					}
				});
				
				
				JButton essaye = new JButton("essaye");
				essaye.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
					}
				});
				essaye.setBounds(103, 306, 89, 23);
				contentPane.add(essaye);
				
				
															
																			
													
												
					
						
						
			
		
		





										
						
	}

	private JPanel contentPane;
	private JTextField textFieldNum1;
	private JTextField textFieldNum2;
	private JTextField textFieldAns;
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
