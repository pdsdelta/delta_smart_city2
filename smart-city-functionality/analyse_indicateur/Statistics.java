package analyse_indicateur;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.sql.SQLException;

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

public class Statistics extends JFrame {
	private functionalityServer server;
	
	public Statistics() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.setVisible(true);
		
	// Taux de dépassement de seuil d'ATMO	
		JButton tauxdépassemnetseuil = new JButton("taux de dépassement de seuil d'ATMO");
		tauxdépassemnetseuil.setBounds(44, 69, 253, 23);
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
					 
					 int data= obj.getInt("Data");
					JOptionPane.showMessageDialog(null, "le taux de dépassemnt de seuil d'ATMO est : " + data);
				} catch (HeadlessException| JSONException | SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		contentPane.setLayout(null);
		
		
// Taux de pollution:
		JButton tauxdepollution = new JButton("taux d'ATMO  dans la ville");
		tauxdepollution.setBounds(44, 11, 253, 23);
		contentPane.add(tauxdepollution);
		
		JButton numberaletre = new JButton("numberalerte");
		numberaletre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		numberaletre.setBounds(44, 124, 253, 23);
		contentPane.add(numberaletre);
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