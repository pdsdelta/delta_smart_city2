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

		getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("ADD");
		btnNewButton.setBounds(366, 86, 89, 23);
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
		getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Compare");
		btnNewButton_1.setBounds(484, 86, 89, 23);
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
		textFieldNum1.setBounds(316, 39, 86, 20);
		getContentPane().add(textFieldNum1);
		textFieldNum1.setColumns(10);

		textFieldNum2 = new JTextField();
		textFieldNum2.setBounds(487, 39, 86, 20);
		getContentPane().add(textFieldNum2);
		textFieldNum2.setColumns(10);

		textFieldAns = new JTextField();
		textFieldAns.setBounds(338, 131, 219, 35);
		textFieldAns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Please Enter Valid Number");
			}
		});
		getContentPane().add(textFieldAns);
		textFieldAns.setColumns(10);

		JLabel lblNewLabel = new JLabel("  The Answer is :");
		lblNewLabel.setBounds(237, 133, 117, 30);
		lblNewLabel.setForeground(new Color(240, 255, 240));

		getContentPane().add(lblNewLabel);

		JPanel panelMenu = new JPanel();
		panelMenu.setBounds(0, 0, 218, 374);
		panelMenu.setBackground(SystemColor.inactiveCaption);
		contentPane.add(panelMenu);
		panelMenu.setLayout(null);
		
				//1number motion sensor
				JButton NumberMotionsensor = new JButton("number motionSensor");
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
							JOptionPane.showMessageDialog(null, "le nombre de motion sensor est :" + objet.informotionsensor());
						} catch (HeadlessException | JsonProcessingException | JSONException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				NumberMotionsensor.setBounds(0, 111, 218, 38);
				panelMenu.add(NumberMotionsensor);
				
				//2number de capteur d'air
						JButton numberCapteurair = new JButton("number capteurAir");
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
									JOptionPane.showMessageDialog(null, "le nombre de capteur d'air est :" + objet.informationcapteurAir());
								} catch (HeadlessException | JsonProcessingException | JSONException | SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						});
						numberCapteurair.setBounds(0, 80, 218, 32);
						panelMenu.add(numberCapteurair);
						
				// 3number de station		
								JButton numberStation = new JButton("number station");
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
											JOptionPane.showMessageDialog(null, "le nombre de station est :" + objet.infostation());
										} catch (HeadlessException | JsonProcessingException | JSONException | SQLException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
									}
								});
								numberStation.setBounds(0, 150, 218, 38);
								panelMenu.add(numberStation);
			//4 NUMBER TRAM	
										JButton numbrTram = new JButton("number Tram");
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
														JOptionPane.showMessageDialog(null, "le nombre de tram est :" + objet.informationTram());
													} catch (HeadlessException | JsonProcessingException | JSONException | SQLException e1) {
														// TODO Auto-generated catch block
														e1.printStackTrace();
													}
											}
										});
										
										numbrTram.setBounds(0, 222, 218, 38);
										panelMenu.add(numbrTram);
					//5 NUMBER CAR	
											JButton numberCar = new JButton("number Car");
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
															
															/*caldrier.getDay()
															canlendar.getYear
															calendar.getMonth()
															//date='canlendar.getYear-calendar.getMonth()-caldrier.getDay()';
															date'2020-12-7'*/
															JOptionPane.showMessageDialog(null, "le nombre de car  est :" + objet.nbcars(date));
														} catch (HeadlessException | JsonProcessingException | JSONException | SQLException e1) {
															// TODO Auto-generated catch block
															e1.printStackTrace();
														}
												}
													
												});
												numberCar.setBounds(0, 187, 218, 38);
												panelMenu.add(numberCar);
						// 6 NUMBER BORNE	
												JButton btnNumberBorne = new JButton("number borne");
												btnNumberBorne.setBackground(SystemColor.inactiveCaption);
												btnNumberBorne.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent e) {
													}
												});
												btnNumberBorne.setBounds(0, 259, 218, 38);
												panelMenu.add(btnNumberBorne);
												
														JButton btnNewButton_2 = new JButton("numbr totalCapteur");
														btnNewButton_2.setBounds(282, 223, 145, 38);
														contentPane.add(btnNewButton_2);
														btnNewButton_2.addActionListener(new ActionListener() {
															public void actionPerformed(ActionEvent e) {
																CRUD objet = null;
																try {
																	objet = new CRUD(server);
																} catch (ClassNotFoundException | SQLException e2) {
																	// TODO Auto-generated catch block
																	e2.printStackTrace();
																}
																try {
																	JOptionPane.showMessageDialog(null, "le nombre de tram est :" + objet.informationBorne());
																} catch (HeadlessException | JsonProcessingException | JSONException | SQLException e1) {
																	// TODO Auto-generated catch block
																	e1.printStackTrace();
																}
															
															}
														});
										
										
								
						
						
				
						
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
