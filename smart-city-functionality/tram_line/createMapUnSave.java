package tram_line;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

import tram_line.tramExceptions.formatCityExceptions;
import tram_line.tramExceptions.errorBudgetExceptions;
import tram_line.tramExceptions.errorNumberStation;

class createMapUnSave extends JPanel {
  int longueur = ((option.monInt("Veuillez entrer la longueur de la ville en km : ")) * 100);
  int largeur = ((option.monInt("Veuillez entrer la largeur de la ville en km : ")) * 100);
  double mapTaille = ((((longueur/100)/2) * ((largeur/100)/2)) * Math.PI);
  int budgetCity = transition.budgetCity1;
  int budgetStation = option.monInt("Veuillez entrer le co�t unitaire d'une station : ");
  int nombreStation = stationPlacement.numberStation(budgetCity,budgetStation);
  int numberLine = 0;
  int numberTram = 0;

  public createMapUnSave() throws formatCityExceptions, errorBudgetExceptions, errorNumberStation{
    System.out.println("je suis dans la methode createMapUnSave"); 
    if(largeur > longueur) {
    	throw new formatCityExceptions();
    }
    if(budgetStation > budgetCity) {
    	throw new errorBudgetExceptions();
    }
    if(nombreStation == 1) {
    	throw new errorNumberStation();
    }
    transition.saveLlt(longueur,largeur, mapTaille, budgetStation, nombreStation);
    if((nombreStation == 2) || (nombreStation == 3)) {
    	numberTram = 2;
    }else {
    	numberTram = (nombreStation / 2); 
    }
    if ((nombreStation >= 20) && (nombreStation < 70)) {
		numberLine = 3;
    }else {
    	numberLine = 1;
    }
    transition.saveLine(numberLine,numberTram);
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
    g.setColor( Color.LIGHT_GRAY);  
    g.fillOval( 0, 0, largeur, longueur);
    
    
    
    
    for(int i= 1; i <= nombreStation; i++) {
    	if(nombreStation == 2) {
    		g.setColor( Color.black );
    		if((i % 2) == 0) {
        		g.drawOval(largeur/2,(longueur/4), 10, 10);
        	} else {
        		g.drawOval((largeur/2),((longueur/4) * 3), 10, 10);
        	}
    		g.drawLine(largeur/2,(longueur/4), (largeur/2),((longueur/4) * 3));
    	} else if((i == 1)) {
    		g.setColor( Color.black );
    		g.drawOval(largeur/2,((longueur/(nombreStation+1)) * i), 10, 10);
    		g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),largeur/4,((longueur/(nombreStation+1)) * (i+1)));
    	} else if(i == nombreStation) {
    		g.setColor( Color.black );
    		g.drawOval(largeur/2,((longueur/(nombreStation+1)) * i), 10, 10);
    	} else if((i % 2) == 0) {
    		g.setColor( Color.black );
    		g.drawOval(largeur/4,((longueur/(nombreStation+1)) * i), 10, 10);
    		if(i == (nombreStation -1)) {
    			g.drawLine(largeur/4,((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (i+1)));
    		}else {
    			g.drawLine(largeur/4,((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+1)));
    		}
    	} else {
    		g.setColor( Color.black );
    		g.drawOval(((largeur/4) * 3),((longueur/(nombreStation+1)) * i), 10, 10);
    		if(i == (nombreStation -1)) {
    			g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (i+1)));
    		}else {
    			g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/4,((longueur/(nombreStation+1)) * (i+1)));
    		}
    	}

    	
    	if (((nombreStation >= 20) && (nombreStation < 30) &&(i+9 <= nombreStation) && ((i % 9) == 0))  || ((nombreStation >= 20) && (nombreStation < 30) && i == 1)) {
    		if(i == 1) {
    			g.setColor( Color.BLUE );
    			g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			g.setColor( Color.RED );
    			g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+8)));
    		}else if(nombreStation == (i+9)) {
    			if((nombreStation - 9) % 2 != 0) {
    				g.setColor( Color.RED );
    				g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			} else {
    				g.setColor( Color.RED );
    				g.drawLine((largeur/4),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			}
    		} else if((i % 6) == 0) {
    			g.setColor( Color.RED );
        		g.drawLine(largeur/4,((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+9)));
    		} else{
    			g.setColor( Color.RED );
        		g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/4,((longueur/(nombreStation+1)) * (i+9)));
    		}
    	}
    	
    	
    	if (((nombreStation >= 30) && (i+5 <= nombreStation) && ((i % 5) == 0))  || ((nombreStation >= 30) && i == 1)) {
    		if(i == 1) {
    			g.setColor( Color.BLUE );
    			g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			g.setColor( Color.RED );
    			g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+4)));
    		}else if(nombreStation == (i+5)) {
    			if((nombreStation - 5) % 2 != 0) {
    				g.setColor( Color.RED );
    				g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			} else {
    				g.setColor( Color.RED );
    				g.drawLine((largeur/4),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			}
    		} else if (i % 10 == 0){
    			g.setColor( Color.RED );
        		g.drawLine((largeur/4),((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+5)));
    		} else if(i % 5 == 0) {
    			g.setColor( Color.RED );
        		g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/4,((longueur/(nombreStation+1)) * (i+5)));
    		}
    	}

    	
    	
    
    }
    
  }

}



