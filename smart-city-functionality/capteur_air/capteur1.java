package capteur_air;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.JSONException;

public class capteur1 extends JFrame{

	
	public capteur1() throws UnknownHostException, IOException, JSONException{
		
		this.setTitle("sélectionner quartier");
		this.setSize(400, 400);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		panel1.setBackground(Color.white);
		JLabel label1 = new JLabel("Choisissez le quartier souhaité: ");
		//myCapteur a = new myCapteur();
		//Object[] element1 = a.getconfigcapteur();
		//JComboBox liste1 = new JComboBox(element1);
		//panel1.add(liste1);
		JLabel label2 = new JLabel(
				"Par défaut les seuils des quartiers sont de 4 mais ils peuvent être changeable dans configuration du capteur");
		JButton Bouton = new JButton("Valider");
		Bouton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		this.add(panel1);
		panel1.add(label1);
		//panel1.add(liste1);
		panel1.add(label2);
		panel1.add(Bouton);
	}
}
