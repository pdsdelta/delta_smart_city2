package tram_line;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

class createMapSave extends JPanel {
      int longueur;
      int largeur;
      int numberStation;
    
      public createMapSave(int longueur1, int largeur1, int nombreStation) {  
        longueur = longueur1; 
        largeur = largeur1;
        numberStation = nombreStation;
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
        g.setColor( Color.LIGHT_GRAY);  
        g.fillOval( 0, 0, largeur, longueur);
        
        
        
        
        for(int i= 1; i <= numberStation; i++) {
        	if(numberStation == 2) {
        		g.setColor( Color.black );
        		if((i % 2) == 0) {
            		g.drawOval(largeur/2,(longueur/4), 10, 10);
            	} else {
            		g.drawOval((largeur/2),((longueur/4) * 3), 10, 10);
            	}
        		g.drawLine(largeur/2,(longueur/4), (largeur/2),((longueur/4) * 3));
        	} else if((i == 1)) {
        		g.setColor( Color.black );
        		g.drawOval(largeur/2,((longueur/(numberStation+1)) * i), 10, 10);
        		g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),largeur/4,((longueur/(numberStation+1)) * (i+1)));
        	} else if(i == numberStation) {
        		g.setColor( Color.black );
        		g.drawOval(largeur/2,((longueur/(numberStation+1)) * i), 10, 10);
        	} else if((i % 2) == 0) {
        		g.setColor( Color.black );
        		g.drawOval(largeur/4,((longueur/(numberStation+1)) * i), 10, 10);
        		if(i == (numberStation -1)) {
        			g.drawLine(largeur/4,((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (i+1)));
        		}else {
        			g.drawLine(largeur/4,((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+1)));
        		}
        	} else {
        		g.setColor( Color.black );
        		g.drawOval(((largeur/4) * 3),((longueur/(numberStation+1)) * i), 10, 10);
        		if(i == (numberStation -1)) {
        			g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (i+1)));
        		}else {
        			g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/4,((longueur/(numberStation+1)) * (i+1)));
        		}
        	}

        	
        	if (((numberStation >= 20) && (numberStation < 30) &&(i+9 <= numberStation) && ((i % 9) == 0))  || ((numberStation >= 20) && (numberStation < 30) && i == 1)) {
        		if(i == 1) {
        			g.setColor( Color.BLUE );
        			g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			g.setColor( Color.RED );
        			g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+8)));
        		}else if(numberStation == (i+9)) {
        			if((numberStation - 9) % 2 != 0) {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			} else {
        				g.setColor( Color.RED );
        				g.drawLine((largeur/4),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			}
        		} else if((i % 6) == 0) {
        			g.setColor( Color.RED );
            		g.drawLine(largeur/4,((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+9)));
        		} else{
        			g.setColor( Color.RED );
            		g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/4,((longueur/(numberStation+1)) * (i+9)));
        		}
        	}
        	
        	
        	if (((numberStation >= 30) && (i+5 <= numberStation) && ((i % 5) == 0))  || ((numberStation >= 30) && i == 1)) {
        		if(i == 1) {
        			g.setColor( Color.BLUE );
        			g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			g.setColor( Color.RED );
        			g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+4)));
        		}else if(numberStation == (i+5)) {
        			if((numberStation - 5) % 2 != 0) {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			} else {
        				g.setColor( Color.RED );
        				g.drawLine((largeur/4),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			}
        		} else if (i % 10 == 0){
        			g.setColor( Color.RED );
            		g.drawLine((largeur/4),((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+5)));
        		} else if(i % 5 == 0) {
        			g.setColor( Color.RED );
            		g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/4,((longueur/(numberStation+1)) * (i+5)));
        		}
        	}
        }
        
      }


    }