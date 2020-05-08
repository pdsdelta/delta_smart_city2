package tram_line;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

class createMapUnSave extends JPanel {
  int longueur = option.monInt("Veuillez entrer la longueur de la ville : "); 
  int largeur = option.monInt("Veuillez entrer la largeur de la ville : ");
  double mapTaille = (((longueur/2) * (largeur/2)) * Math.PI)/1000;

  public createMapUnSave() {
    System.out.println("je suis dans la methode createMapUnSave");
    transition.saveLlt(longueur,largeur, mapTaille);
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