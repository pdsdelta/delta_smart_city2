package gestion_borne.vue;

import java.awt.Font;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import functionality_server.functionalityServer;
import gestion_borne.AlgorithmeBorne;




public class TraficVue {
	public static void main(String[] args) throws InterruptedException, ClassNotFoundException, SQLException {
		MonModele monModele = new MonModele();
		Trafic maFenetre = new Trafic();

		MonObserveur monObserveur = new MonObserveur(maFenetre);
		monModele.addObserver(monObserveur);

		while (true) {
			monModele.incrementerMaVariable();
		}
	}

	public static class MonModele extends Observable  {
		public MonModele() throws ClassNotFoundException, SQLException {
		}
		private int status = 1;
		private int alarme =1;
		private int voiture =1;
		AlgorithmeBorne algo= new AlgorithmeBorne();

		public void incrementerMaVariable() {
			
			while (true) {
				
			this.status=algo.getStatus();
			this.alarme=algo.getAlarme();
			this.voiture=algo.getPlacesOccupees();
			setChanged();
			notifyObservers();
			}
		}

		public int getStatus() {
			return this.status;
		}

		public int getAlarme() {
			return this.alarme;
		}
		public int getVoiture() {
			return this.voiture;
		}
	}

	public static class Trafic  {
		JFrame fenetre = new JFrame();


		private javax.swing.JLabel vtrPresent;
		private javax.swing.JLabel alarme;
		private javax.swing.JLabel staBorne;
		functionalityServer server;
		//TerminalDAO data=new TerminalDAO(server)  ;
		String toSend;

		private JPanel contentPane;
		/**
		 * Create the frame.
		 * @throws SQLException 
		 * @throws ClassNotFoundException 
		 */
		public Trafic() throws ClassNotFoundException, SQLException {
			fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			fenetre.setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			fenetre.setContentPane(contentPane);
			contentPane.setLayout(null);

			vtrPresent = new JLabel("Voiture Present :");
			vtrPresent.setFont(new Font("Times New Roman", Font.BOLD, 14));
			vtrPresent.setBounds(27, 58, 141, 33);
			contentPane.add(vtrPresent);
			fenetre.setVisible(true);

			alarme = new JLabel("Alarme : ");
			alarme.setFont(new Font("Times New Roman", Font.BOLD, 14));
			alarme.setBounds(27, 133, 141, 13);
			contentPane.add(alarme);

			staBorne = new JLabel("Status Borne : ");
			staBorne.setFont(new Font("Times New Roman", Font.BOLD, 14));
			staBorne.setBounds(27, 193, 141, 13);
			contentPane.add(staBorne);
		}
		public void afficherAlarme(int variable) {
			alarme.setText("Alarme : "+String.valueOf(variable));
		}
		public void afficherStatus(int variable) {
			staBorne.setText("Status Borne : "+String.valueOf(variable));
		}
		public void afficherVoiture(int variable) {
			
			vtrPresent.setText("Voiture Present :"+String.valueOf(variable));
		}
	}

	public static class MonObserveur implements Observer {
		private Trafic fenetre;

		public MonObserveur(Trafic fenetre) {
			this.fenetre = fenetre;
		}

		@Override
		public void update(Observable o, Object arg) {
			fenetre.afficherStatus(((MonModele) o).getStatus());
			fenetre.afficherAlarme(((MonModele) o).getAlarme());
			fenetre.afficherVoiture(((MonModele) o).getVoiture());
		}
	}


}


