package analyse_indicateur;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;

import functionality_server.functionalityServer;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;
import javax.swing.JLabel;
import java.awt.SystemColor;

public class Statistics extends JFrame {
	private functionalityServer server;
	
	public Statistics() {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.setVisible(true);
		contentPane.setLayout(null);
		

		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setBounds(89, 186, 139, 20);
		contentPane.add(dateChooser);
		
	// Thershold depassement of pollution
		JButton tauxdépassemnetseuil = new JButton("taux de dépassement de seuil d'ATMO");
		tauxdépassemnetseuil.setBounds(44, 90, 253, 23);
		contentPane.add(tauxdépassemnetseuil);
		tauxdépassemnetseuil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Indicator objet = null;
				try {
					objet = new Indicator(server);
					
				} catch (ClassNotFoundException | SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					String jsonReceived = objet.tauxDepAtmo();
					objet.startConnection("172.31.249.22", 2400);
					JSONObject obj = new JSONObject(jsonReceived);
					JSONObject objet1= obj.getJSONObject("request");
					 
					 int data= objet1.getInt("Data");
					JOptionPane.showMessageDialog(null, "le taux de dépassemnt de seuil d'ATMO est : " + data);
				} catch (HeadlessException| JSONException | SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
// Thershold ATMO:
		JButton tauxdepollution = new JButton("taux d'ATMO  dans la ville");
		tauxdepollution.setBounds(44, 54, 253, 23);
		contentPane.add(tauxdepollution);
		
		JButton numberaletre = new JButton("nombre d'alerte");
		numberaletre.setBounds(44, 124, 253, 23);
		numberaletre.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				Indicator objet = null;
				try {
					objet = new Indicator(server);
				} catch (ClassNotFoundException | SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					String jsonReceived = objet.tauxATMO();
					objet.startConnection("172.31.249.22", 2400);
					JSONObject obj = new JSONObject(jsonReceived);
					JSONObject objet1= obj.getJSONObject("request");
					 
					 int data= objet1.getInt("Data");
					JOptionPane.showMessageDialog(null, "le nombre d'alerte  est : " + data);
				} catch (HeadlessException  | JSONException | SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		//numbre alerte
		contentPane.add(numberaletre);
		
		JButton capteurairActif = new JButton(" capteur air active");
		capteurairActif.setBounds(44, 162, 251, 23);
		contentPane.add(capteurairActif);
		
		JLabel lblNewLabel = new JLabel("  Les statistiques");
		lblNewLabel.setBackground(SystemColor.windowText);
		lblNewLabel.setBounds(112, 29, 226, 14);
		contentPane.add(lblNewLabel);
		
		capteurairActif.addActionListener(new ActionListener() {
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
					
					String jsonReceived = objet.capteurairActif (date);
					objet.startConnection("172.31.249.22", 2400);
					JSONObject obj = new JSONObject(jsonReceived );
					JSONObject objet1= obj.getJSONObject("request");
					int data = objet1.getInt("Data");
			if(data==0) {
			    JOptionPane.showMessageDialog(null, "aucun capteurair n'est activée");
			}else{JOptionPane.showMessageDialog(null, "le capteur d'air activer est :" + data);
			}
			}
				 catch (HeadlessException | JSONException | SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		
		
		tauxdepollution.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Indicator objet = null;
				try {
					objet = new Indicator(server);
				} catch (ClassNotFoundException | SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					String jsonReceived = objet.tauxATMO();
					objet.startConnection("172.31.249.22", 2400);
					JSONObject obj = new JSONObject(jsonReceived);
					JSONObject objet1= obj.getJSONObject("request");
					 
					 int data= objet1.getInt("Data");
					JOptionPane.showMessageDialog(null, "le taux d'ATMO dans la ville est : " + data);
				} catch (HeadlessException  | JSONException | SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

private JPanel contentPane;

/**
 * Launch the application.
 */
public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				Statistics frame = new Statistics();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
}
}