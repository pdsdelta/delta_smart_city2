package tram_line;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

class createMapUnSave extends JPanel {
  int longueur = option.monInt("Veuillez entrer la longueur de la ville : "); 
  int largeur = option.monInt("Veuillez entrer la largeur de la ville : ");
  double mapTaille = (((longueur/2) * (largeur/2)) * Math.PI)/1000;
  int budgetCity = transition.budgetCity1;
  int budgetStation = option.monInt("Veuillez entrer le coût unitaire d'une station : ");
  int nombreStation = stationPlacement.numberStation(budgetCity,budgetStation);

  public createMapUnSave() {
    System.out.println("je suis dans la methode createMapUnSave");  
    transition.saveLlt(longueur,largeur, mapTaille, budgetStation, nombreStation);
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
    
    
    
    
    for(int i= 1; i <= nombreStation; i++) {
    	//g.drawOval(largeur/2,((longueur/(nombreStation+1)) * i), 10, 10);
    	
    	if(nombreStation == 2) {
    		if((i % 2) == 0) {
        		g.drawOval(largeur/2,(longueur/4), 10, 10);
        	} else {
        		g.drawOval((largeur/2),((longueur/4) * 3), 10, 10);
        	}
    		g.drawLine(largeur/2,(longueur/4), (largeur/2),((longueur/4) * 3));
    	} else if((i == 1)) {
    		g.drawOval(largeur/2,((longueur/(nombreStation+1)) * i), 10, 10);
    		g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),largeur/4,((longueur/(nombreStation+1)) * (i+1)));
    	} else if(i == nombreStation) {
    		g.drawOval(largeur/2,((longueur/(nombreStation+1)) * i), 10, 10);
    	} else if((i % 2) == 0) {
    		g.drawOval(largeur/4,((longueur/(nombreStation+1)) * i), 10, 10);
    		if(i == (nombreStation -1)) {
    			g.drawLine(largeur/4,((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (i+1)));
    		}else {
    			g.drawLine(largeur/4,((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+1)));
    		}
    	} else {
    		g.drawOval(((largeur/4) * 3),((longueur/(nombreStation+1)) * i), 10, 10);
    		if(i == (nombreStation -1)) {
    			g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (i+1)));
    		}else {
    			g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/4,((longueur/(nombreStation+1)) * (i+1)));
    		}
    	}

    	
        	//	g.drawLine(,);

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    }
    
  }

}



