package capteur_air;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Date;
import java.util.TimerTask;
import java.util.Timer;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

 public class capteur4 extends JFrame {
	 
	 
	private JFormattedTextField jtf = new JFormattedTextField(NumberFormat.getIntegerInstance());
	public capteur4(){
	this.setTitle("deuxi�me fenetre");
    this.setSize(300,300);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setLocationRelativeTo(null);
    this.setVisible(true);
	JPanel panel1 = new JPanel();
	panel1.setLayout(new FlowLayout());
	panel1.setBackground(Color.white);	
	JLabel label1 = new JLabel("Veuillez d�terminer l'intervalle des relev�s");
	jtf.getText();
	//int jtf2 = jtf1 * 1000;
	//TestTimer(jtf2);
	JLabel label2 = new JLabel("en secondes");
	JButton btnNewButton = new JButton("Valider");
//	btnNewButton.addActionListener(new ActionListener() {
//		public void actionPerformed(ActionEvent e) {
//			
//		}
//	});
	this.add(panel1);
	panel1.add(label1);
	panel1.add(jtf);
	panel1.add(label2);
	panel1.add(btnNewButton);
	
 }
 }