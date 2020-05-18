package gestion_borne.vue;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import functionality_server.functionalityServer;
import gestion_borne.crud.DAO;
import gestion_borne.crud.MotionSensorDAO;
import motionSensor.MotionSensor;

import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

public class MotionSensorVue {
	private functionalityServer server;

	//connection socket
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private JFrame frame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MotionSensorVue window = new MotionSensorVue();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void startConnection(String ip, int port) throws UnknownHostException, IOException {
		clientSocket = new Socket(ip, port);
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}

	/**
	 * Create the application.
	 */
	public MotionSensorVue() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JButton btnAddSensor = new JButton("Ajouter Un Detecteur");
		btnAddSensor.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AddSensor(evt);
			}
		});
		btnAddSensor.setBounds(54, 44, 136, 28);
		panel.add(btnAddSensor);

		JButton btnDeleteSensor = new JButton("Supprimer un Detecteur");
		btnDeleteSensor.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed( java.awt.event.ActionEvent evt) {
				try {
					DeleteSensor(evt);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnDeleteSensor.setBounds(224, 44, 158, 28);
		panel.add(btnDeleteSensor);

		JButton btnListSensor = new JButton("Liste des Detecteurs");
		btnListSensor.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					ListeSensor(evt);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnListSensor.setBounds(54, 115, 136, 28);
		panel.add(btnListSensor);

		JButton btnModifySensor = new JButton("Modifier un Detecteur");
		btnModifySensor.setBounds(224, 115, 158, 28);
		panel.add(btnModifySensor);
	}
	private void ListeSensor(java.awt.event.ActionEvent evt) throws ClassNotFoundException, SQLException {                                           
		new MotionSensorListVue(frame, true).setVisible(true);
	}
	private void AddSensor(java.awt.event.ActionEvent evt) {                                           
		new MotionSensorAdd().setVisible(true);
	}
	private void DeleteSensor(java.awt.event.ActionEvent evt) throws ClassNotFoundException, SQLException {                                           
		JOptionPane jop= new JOptionPane();
		String num = jop.showInputDialog(null,"Entrez le numero du capteur à supprimer",JOptionPane.QUESTION_MESSAGE);
		int y = Integer.parseInt(num); 
	   MotionSensorDAO data = new MotionSensorDAO(server); 
		MotionSensor captor= data.find(y);
		data.delete(captor);
	}
}

