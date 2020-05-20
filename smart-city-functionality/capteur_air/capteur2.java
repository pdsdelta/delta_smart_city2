package capteur_air;

import java.awt.Color;
import java.awt.FlowLayout;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.JSONException;

public class capteur2 extends JFrame {
	
	public capteur2() throws UnknownHostException, IOException, JSONException{
		this.setTitle("deuxième fenetre");
		this.setSize(400, 400);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.setBackground(Color.white);
		JLabel label1 = new JLabel();
		label1.setText("<html><body><p><p><p><p><p><p><p><p><p><p><p><p>" 
				+ "Pour le quartier " // + quartier
				+ " nous avons un indice de" 
				+ "<p>" 
				+ "Pour les particules fines, nous avons un indice ATMO de " 
				+ "<p>"
				+ "Pour l'ozote, nous avons un indice ATMO de " 
				+ "<p>"
				+ "Pour le dioxyde d'azote, nous avons un indice ATMO de "
				+ "<p>"
				+ "Pour le dioxyde de soufre, nous avons un indice ATMO de " 
				+ "<p>" 
				+ "Donc un indice ATMO de "
				+ "</body></html>");
		this.add(panel);
		panel.add(label1);
  }
}
