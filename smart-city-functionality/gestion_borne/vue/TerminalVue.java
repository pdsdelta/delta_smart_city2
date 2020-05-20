package gestion_borne.vue;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import borne.Terminal;
import functionality_server.functionalityServer;
import gestion_borne.crud.MotionSensorDAO;
import gestion_borne.crud.TerminalDAO;
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
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;

public class TerminalVue {

	functionalityServer server;
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
					TerminalVue window = new TerminalVue();
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
	public TerminalVue() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 684, 320);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JMenuBar menuBar= new JMenuBar();
		JMenu menu= new JMenu("Configuration");
		JMenuItem menuItem = new JMenuItem("Voiture Admis");
		menuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					SetMaxVoiture(evt);
				} catch (ClassNotFoundException | SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		menuBar.add(menuItem);
		frame.setJMenuBar(menuBar);
		JButton btnAddTerminal = new JButton("Ajouter Une Borne");
		btnAddTerminal.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AddTerminal(evt);
			}
		});
		btnAddTerminal.setBounds(54, 44, 237, 28);
		panel.add(btnAddTerminal);

		JButton btnDeleteTerminal = new JButton("Supprimer une Borne");
		btnDeleteTerminal.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed( java.awt.event.ActionEvent evt) {
				try {
					DeleteTerminal(evt);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
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
		btnDeleteTerminal.setBounds(314, 44, 290, 28);
		panel.add(btnDeleteTerminal);

		JButton btnListTerminal = new JButton("Liste des Bornes");
		btnListTerminal.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					ListeTerminal(evt);
				} catch (ClassNotFoundException | SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnListTerminal.setBounds(54, 115, 237, 28);
		panel.add(btnListTerminal);

		JButton btnModifyTerminal = new JButton("Modifier une Borne");
		btnModifyTerminal.setBounds(314, 115, 290, 28);
		panel.add(btnModifyTerminal);
	}
	private void ListeTerminal(java.awt.event.ActionEvent evt) throws ClassNotFoundException, SQLException, UnknownHostException, IOException {                                           
		new TerminalListVue(frame, true).setVisible(true);
	}
	private void AddTerminal(java.awt.event.ActionEvent evt) {                                           
		new TerminalAddVue().setVisible(true);
	} 
	private void SetMaxVoiture(java.awt.event.ActionEvent evt) throws ClassNotFoundException, SQLException, UnknownHostException, IOException {
		JOptionPane jop= new JOptionPane();
		String num = jop.showInputDialog(null,"Entrez le numero de voiture admis",JOptionPane.QUESTION_MESSAGE);
		int y = Integer.parseInt(num);
		TerminalDAO data  = new TerminalDAO(server);
		data.SetNbrMaxVoiture(y);
		data.startConnection("172.31.249.22", 2400);
	}
	private void DeleteTerminal(java.awt.event.ActionEvent evt) throws ClassNotFoundException, SQLException, UnknownHostException, IOException {                                           
		JOptionPane jop= new JOptionPane();
		String num = jop.showInputDialog(null,"Entrez le numero de la borne à supprimer",JOptionPane.QUESTION_MESSAGE);
		int y = Integer.parseInt(num); 
		TerminalDAO data = new TerminalDAO(server); 
		Terminal borne= data.find(y);
		data.delete(borne);
		data.startConnection("172.31.249.22", 2400);
	}
	
}
