package capteur_air;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.JSONException;

public class capteur3 extends JFrame{

	public capteur3() throws UnknownHostException, IOException, JSONException {
		this.setTitle("sélectionner quartier");
		this.setSize(400, 400);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		panel1.setBackground(Color.white);
		JLabel label1 = new JLabel("Déterminer les nouveaux seuils des quartiers:\n ");
		JButton Bouton = new JButton("Valider");
		Bouton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.exit(0);
				dispose();
			}
		});
		this.add(panel1);
		panel1.add(label1);
		panel1.add(Bouton);
	}
}
