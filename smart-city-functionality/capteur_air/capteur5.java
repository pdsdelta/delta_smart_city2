package capteur_air;

import java.awt.Color;
import java.awt.FlowLayout;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.JSONException;

public class capteur5 extends JFrame {

	public capteur5() throws UnknownHostException, IOException, JSONException{
		this.setTitle("Historique");
		this.setSize(400, 400);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		JPanel panel1 = new JPanel();
       	panel1.setLayout(new FlowLayout());
        panel1.setBackground(Color.white);	
        JLabel label1 = new JLabel();
        label1.setText("<html><body><p><p><p><p><p><p><p><p><p><p><p><p>" 
        	  +"Un relevé de 7 sur l'ï¿½chelle d'indice ATMO" 
        	  + "<p>" 
        	  +"dans le quartier  " //+ quartier 
       		  + "<p>" 
       		  + "le "  
       		  + "<p>" 
       		  + "<p>" 
       		  +"</body></html>" );
        this.add(panel1);
        panel1.add(label1);
	}
}
