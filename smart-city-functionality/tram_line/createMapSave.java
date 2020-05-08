package tram_line;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

class createMapSave extends JPanel {
      int longueur;
      int largeur;
    
      public createMapSave(int longueur1, int largeur1) {  
        longueur = longueur1; 
        largeur = largeur1;
        
        System.out.println("je suis dans la methode createMapSave");
        addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          repaint();
        }
        });
      }
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground( Color.WHITE );
        g.setColor( Color.black );  
        g.drawOval( 0, 0, largeur, longueur);
      }

    }