package tram_line;

import java.awt.event.*;
import javax.swing.*;

import tram_line.tramExceptions.unexpectedHeight;

import java.awt.*;
import java.util.*;

public class createMapSave extends JPanel {
      int longueur;
      int largeur;
      int numberStation;
      int x,y;
      double z = 1;
    
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
        System.out.println("L'algorithme c'est terminé avec succès!");
      }
      @Override
      protected void paintComponent(Graphics g) { 
        super.paintComponent(g);
        this.setBackground( Color.WHITE );
        g.setColor( Color.LIGHT_GRAY);  
        
        //taille de la station en fonction du nombre de station
        if(numberStation < 60) {
        	x = 10;
        	y = 10;
        }else if((numberStation >= 60) && (numberStation < 150)){
        	x = 7;
        	y = 7;
        }else if((numberStation >= 150) && (numberStation < 200)){
        	x = 6;
        	y = 6;
        }else if((numberStation >= 200) && (numberStation <= 300)){
        	x = 5;
        	y = 5;
        }
        
        //Zoom min
        if((longueur > 3600) || (largeur> 3600)) {
        	throw new unexpectedHeight();
        }else if ((longueur > 1800) || (largeur> 1800)) {
        	if(largeur <= 1) {
        		longueur = longueur /4;
        		largeur = largeur + 50;
            	x = 5; 
            	y = 5;
            	z = 0.25;
        	}else {
        		longueur = longueur /4;
        		largeur = largeur /4;
        		if(largeur == 50) {
        			largeur = largeur + 150;
        		}
        		x = 5; 
        		y = 5;
        		z = 0.25;
        	}
        }else if ((longueur > 900) || (largeur> 900)) {
        	if(largeur <= 1) {
        		longueur = longueur /2;
            	x = 5; 
            	y = 5;
            	z = 0.5;
        	}else {
        		longueur = longueur /2;
        		largeur = largeur /2;
        		x = 5; 
        		y = 5;
        		z = 0.5;
        	}
        }
    	g.drawString("Ville à taille x" + z, 800, 800);
        g.fillOval( 0, 0, largeur, longueur);
        
        if(numberStation > 200) {
        	longueur = longueur+40;	
        }else if(numberStation > 140) {
        	longueur = longueur+20;	
        }
        
       //DEBUT ALGORITHME
       for(int i= 1; i <= numberStation; i++) {
        if(numberStation >= 16 ) {
        	if(numberStation == 2) {
        		g.setColor( Color.black );
        		if((i % 2) == 0) {
            		g.drawOval(largeur/2,(longueur/4), x, y);
            	} else {
            		g.drawOval((largeur/2),((longueur/4) * 3), x, y);
            	}
        		g.drawLine(largeur/2,(longueur/4), (largeur/2),((longueur/4) * 3));
        	} else if((i == 1)) {
        		g.setColor( Color.black );
        		g.drawOval(largeur/2,((longueur/(numberStation+1)) * i), x, y);

        		g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),((largeur/8) * 3),((longueur/(numberStation+1)) * (i+1)));
        	} else if(i == numberStation) {
        		g.setColor( Color.black );
        		g.drawOval(largeur/2,((longueur/(numberStation+1)) * i), x, y);	
        	} else if(i <= (numberStation / 8)) {
        		if(i == (numberStation / 8)) {
        			if((i % 2) == 0) {
                		g.setColor( Color.black );
                		g.drawOval((largeur/8) * 3,((longueur/(numberStation+1)) * i), x, y);
                		g.drawLine((largeur/8) * 3,((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+1)));
                		}else {
                		g.setColor( Color.black );
                    	g.drawOval((largeur/8) * 5,((longueur/(numberStation+1)) * i), x, y);
                    	g.drawLine((largeur/8) * 5,((longueur/(numberStation+1)) * i),largeur/4,((longueur/(numberStation+1)) * (i+1)));    			
                		}
        		} else if((i % 2) == 0) {
            		g.setColor( Color.black );
            		g.drawOval((largeur/8) * 3,((longueur/(numberStation+1)) * i), x, y);
            		g.drawLine((largeur/8) * 3,((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+1)));
            		}else {
            		g.setColor( Color.black );
                	g.drawOval((largeur/8) * 5,((longueur/(numberStation+1)) * i), x, y);
                	g.drawLine((largeur/8) * 5,((longueur/(numberStation+1)) * i),((largeur/8) * 3),((longueur/(numberStation+1)) * (i+1)));    			
            		}
        		} else if((((numberStation / 8) * 7) - 1) == i) {
        			if((i % 2) == 0) {
                		g.setColor( Color.black );
                		g.drawOval((largeur / 4),((longueur/(numberStation+1)) * i), x, y);
                		g.drawLine((largeur / 4),((longueur/(numberStation+1)) * i),((largeur / 8) * 5),((longueur/(numberStation+1)) * (i+1)));
                		}else {
                		g.setColor( Color.black );
                    	g.drawOval((largeur/4) * 3,((longueur/(numberStation+1)) * i), x, y);
                    	g.drawLine((largeur/4) * 3,((longueur/(numberStation+1)) * i),((largeur / 8) * 3),((longueur/(numberStation+1)) * (i+1)));    			
                		}
        	}else if(i >= ((numberStation / 8)*7)) {
        		if(i == (numberStation - 1)) {
        			if((i % 2) == 0) {
                		g.setColor( Color.black );
                		g.drawOval((largeur/8) * 3,((longueur/(numberStation+1)) * i), x, y);
                		g.drawLine((largeur/8) * 3,((longueur/(numberStation+1)) * i),(largeur / 2),((longueur/(numberStation+1)) * (i+1)));
                		}else {
                		g.setColor( Color.black );
                    	g.drawOval((largeur/8) * 5,((longueur/(numberStation+1)) * i), x, y);
                    	g.drawLine((largeur/8) * 5,((longueur/(numberStation+1)) * i),(largeur / 2),((longueur/(numberStation+1)) * (i+1)));    			
                		}
        		} else if((i % 2) == 0) {
            		g.setColor( Color.black );
            		g.drawOval((largeur/8) * 3,((longueur/(numberStation+1)) * i), x, y);
            		g.drawLine((largeur/8) * 3,((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+1)));
            		}else {
            		g.setColor( Color.black );
                	g.drawOval((largeur/8) * 5,((longueur/(numberStation+1)) * i), x, y);
                	g.drawLine((largeur/8) * 5,((longueur/(numberStation+1)) * i),((largeur/8) * 3),((longueur/(numberStation+1)) * (i+1)));    			
            		}	
        	} else if((i % 2) == 0) {
        		g.setColor( Color.black );
        		g.drawOval(largeur/4,((longueur/(numberStation+1)) * i), x, y);
        		if(i == (numberStation -1)) {
        			g.drawLine(largeur/4,((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (i+1)));
        		}else {
        			g.drawLine(largeur/4,((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+1)));
        		}
        	} else {
        		g.setColor( Color.black );
        		g.drawOval(((largeur/4) * 3),((longueur/(numberStation+1)) * i), x, y);
        		if(i == (numberStation -1)) {
        			g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (i+1)));
        		}else {
        			g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/4,((longueur/(numberStation+1)) * (i+1)));
        		}
        	}
      	
        	//VALEUR 16 / 30 ALGO
        	
        	if ((numberStation >= 16) && (numberStation < 30)) {
        	
        	if (((numberStation >= 16) && (numberStation < 30) &&(i+9 <= numberStation) && ((i % 9) == 0))  || ((numberStation >= 16) && (numberStation < 30) && i == 1)) {
        		
        	
        		if((i <= (numberStation/8))) {
        			if(i == 1) {
            			g.setColor( Color.BLUE );
            			g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
            			g.setColor( Color.RED );
            			if(i+8 <= (numberStation/8)) {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/8) * 5,((longueur/(numberStation+1)) * (i+8)));
            			}else {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/4) * 3,((longueur/(numberStation+1)) * (i+8)));
            			}
        			}else if(numberStation == (numberStation/8)) {
        				if(i % 2 == 0) {
            				g.setColor( Color.RED );
            				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+8)));
            			} else {
            				g.setColor( Color.RED );
            				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),(largeur/4),((longueur/(numberStation+1)) * (i+8)));
            			}
        			}
        		
        		}else if((numberStation == (i+9)) && (i>=(numberStation/8)*7)) {
        			if(i % 2 != 0) {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			} else {
        				g.setColor( Color.RED );
        				g.drawLine(( (largeur/8) * 3),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			}	
        		}else if((numberStation == (i+9))){
        			if(i % 2 != 0) {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			} else {
        				g.setColor( Color.RED );
        				g.drawLine((largeur/4),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			}	
        		}else if((i+9 >= (numberStation/8)*7)){
        			if(i % 2 != 0) {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 3),((longueur/(numberStation+1)) * (i+9)));
        			} else {
        				g.setColor( Color.RED );
        				g.drawLine((largeur/4),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+9)));
        			}	
        		} else if((i % 2) == 0) {
        			g.setColor( Color.RED );
            		g.drawLine(largeur/4,((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+9)));
        		} else{
        			g.setColor( Color.RED );
            		g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/4,((longueur/(numberStation+1)) * (i+9)));
        		}
        	}
        	//FIN if
        	}
        	
        	
        	//VALEUR 30 / 60 ALGO
        if ((numberStation >= 30) && (numberStation < 70)) {
        	if (((numberStation >= 30) &&  (numberStation < 70) && (i+5 <= numberStation) && ((i % 5) == 0))  || ((numberStation >= 30) &&  (numberStation < 70) && i == 1)) {
        		
        		if(((i+4) <= (numberStation/8)) && (i <= (numberStation/8))) {
        			if(i == 1) {
            			g.setColor( Color.BLUE );
            			g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
            			g.setColor( Color.ORANGE );
            			if(i+4 <= (numberStation/8)) {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/8) * 5,((longueur/(numberStation+1)) * (i+4)));
            			}else {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/4) * 3,((longueur/(numberStation+1)) * (i+4)));
            			}
        			}else if(i % 2 == 0) {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+4)));
        			} else {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),(largeur/8) * 3,((longueur/(numberStation+1)) * (i+4)));
        			}
        		}else if((i <= (numberStation/8))) {
        			if(i == 1) {
            			g.setColor( Color.BLUE );
            			g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
            			g.setColor( Color.ORANGE );
            			if(i+4 <= (numberStation/8)) {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/8) * 5,((longueur/(numberStation+1)) * (i+4)));
            			}else {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/4) * 3,((longueur/(numberStation+1)) * (i+4)));
            			}
        			}else if(i % 2 == 0) {
            				g.setColor( Color.ORANGE );
            				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+4)));
            			} else {
            				g.setColor( Color.ORANGE );
            				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),(largeur/4),((longueur/(numberStation+1)) * (i+4)));
            			}
        		}else if((numberStation == (i+5)) && (i>=(numberStation/8)*7)) {
        			if(i % 2 != 0) {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			} else {
        				g.setColor( Color.ORANGE );
        				g.drawLine(( (largeur/8) * 3),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			}	
        		}else if(((i+5)>=(numberStation/8)*7) && (i>=(numberStation/8)*7)){
        			if(i % 2 != 0) {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),((largeur/8) * 3),((longueur/(numberStation+1)) * (i+5)));
        			} else {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+5)));
        			}	
        		}else if((numberStation == (i+5))){
        			if(i % 2 != 0) {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			} else {
        				g.setColor( Color.ORANGE );
        				g.drawLine((largeur/4),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			}	
        		}else if((i+5 >= (numberStation/8)*7)){
        			if(i % 2 != 0) {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 3),((longueur/(numberStation+1)) * (i+5)));
        			} else {
        				g.setColor( Color.ORANGE );
        				g.drawLine((largeur/4),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+5)));
        			}	
        		} else if((i % 2) == 0) {
        			g.setColor( Color.ORANGE );
            		g.drawLine(largeur/4,((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+5)));
        		} else{
        			g.setColor( Color.ORANGE );
            		g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/4,((longueur/(numberStation+1)) * (i+5)));
        		}
        	}	
        	//FIN IF
        }
        	//ALGO 70 125
        if ((numberStation >= 70) && (numberStation < 125)) {
        	//LIGNE SUP 9
        	if (((numberStation >= 70) && (numberStation < 125) &&(i+9 <= numberStation) && ((i % 9) == 0))  || ((numberStation >= 70) && (numberStation < 125) && i == 1)) {
        		if(((i+8) <= (numberStation/8)) && (i <= (numberStation/8))) {
        			if(i == 1) {
            			g.setColor( Color.BLUE );
            			g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
            			g.setColor( Color.RED );
            			if(i+4 <= (numberStation/8)) {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/8) * 5,((longueur/(numberStation+1)) * (i+8)));
            			}else {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/4) * 3,((longueur/(numberStation+1)) * (i+8)));
            			}
        			}else if(i % 2 == 0) {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+8)));
        			} else {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),(largeur/8) * 3,((longueur/(numberStation+1)) * (i+8)));
        			}
        		}else if((i <= (numberStation/8))) {
        			if(i == 1) {
            			g.setColor( Color.BLUE );
            			g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
            			g.setColor( Color.RED );
            			if(i+4 <= (numberStation/8)) {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/8) * 5,((longueur/(numberStation+1)) * (i+4)));
            			}else {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/4) * 3,((longueur/(numberStation+1)) * (i+4)));
            			}
        			}else if(i % 2 == 0) {
            				g.setColor( Color.RED );
            				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+9)));
            			} else {
            				g.setColor( Color.RED );
            				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),(largeur/4),((longueur/(numberStation+1)) * (i+9)));
            			}
        		}else if((numberStation == (i+9)) && (i>=(numberStation/8)*7)) {
        			if(i % 2 != 0) {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			} else {
        				g.setColor( Color.RED );
        				g.drawLine(( (largeur/8) * 3),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			}	
        		}else if(((i+9)>=(numberStation/8)*7) && (i>=(numberStation/8)*7)){
        			if(i % 2 != 0) {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),((largeur/8) * 3),((longueur/(numberStation+1)) * (i+9)));
        			} else {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+9)));
        			}	
        		}else if((numberStation == (i+9))){
        			if(i % 2 != 0) {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			} else {
        				g.setColor( Color.RED );
        				g.drawLine((largeur/4),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			}	
        		}else if((i+9 >= (numberStation/8)*7)){
        			if(i % 2 != 0) {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 3),((longueur/(numberStation+1)) * (i+9)));
        			} else {
        				g.setColor( Color.RED );
        				g.drawLine((largeur/4),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+9)));
        			}	
        		} else if((i % 2) == 0) {
        			g.setColor( Color.RED );
            		g.drawLine(largeur/4,((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+9)));
        		} else{
        			g.setColor( Color.RED );
            		g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/4,((longueur/(numberStation+1)) * (i+9)));
        		}
        	}
        	
        	//LIGNE SUP 5    	
        	if (((numberStation >= 70) &&  (numberStation < 125) && (i+5 <= numberStation) && ((i % 5) == 0))  || ((numberStation >= 70) &&  (numberStation < 125) && i == 1)) {
        		if(((i+5) <= (numberStation/8)) && (i <= (numberStation/8))) {
        			if(i == 1) {
            			g.setColor( Color.BLUE );
            			g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
            			g.setColor( Color.ORANGE );
            			if(i+4 <= (numberStation/8)) {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/8) * 5,((longueur/(numberStation+1)) * (i+4)));
            			}else {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/4) * 3,((longueur/(numberStation+1)) * (i+4)));
            			}
        			}else if(i % 2 == 0) {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+5)));
        			} else {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),(largeur/8) * 3,((longueur/(numberStation+1)) * (i+5)));
        			}
        		}else if((i <= (numberStation/8))) {
        			if(i == 1) {
            			g.setColor( Color.BLUE );
            			g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
            			g.setColor( Color.ORANGE );
            			if(i+4 <= (numberStation/8)) {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/8) * 5,((longueur/(numberStation+1)) * (i+4)));
            			}else {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/4) * 3,((longueur/(numberStation+1)) * (i+4)));
            			}
        			}else if(i % 2 == 0) {
            				g.setColor( Color.ORANGE );
            				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+5)));
            			} else {
            				g.setColor( Color.ORANGE );
            				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),(largeur/4),((longueur/(numberStation+1)) * (i+5)));
            			}
        		}else if((numberStation == (i+5)) && (i>=(numberStation/8)*7)) {
        			if(i % 2 != 0) {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			} else {
        				g.setColor( Color.ORANGE );
        				g.drawLine(( (largeur/8) * 3),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			}	
        		}else if(((i+5)>=(numberStation/8)*7) && (i>=(numberStation/8)*7)){
        			if(i % 2 != 0) {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),((largeur/8) * 3),((longueur/(numberStation+1)) * (i+5)));
        			} else {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+5)));
        			}	
        		}else if((numberStation == (i+5))){
        			if(i % 2 != 0) {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			} else {
        				g.setColor( Color.ORANGE );
        				g.drawLine((largeur/4),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			}	
        		}else if((i+5 >= (numberStation/8)*7)){
        			if(i % 2 != 0) {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 3),((longueur/(numberStation+1)) * (i+5)));
        			} else {
        				g.setColor( Color.ORANGE );
        				g.drawLine((largeur/4),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+5)));
        			}	
        		} else if((i % 2) == 0) {
        			g.setColor( Color.ORANGE );
            		g.drawLine(largeur/4,((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+5)));
        		} else{
        			g.setColor( Color.ORANGE );
            		g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/4,((longueur/(numberStation+1)) * (i+5)));
        		}
        	}	
        	//FIN IF
        }
        	
        //ALGO 120 220
        if ((numberStation >= 125) && (numberStation < 220)) {
        	//LIGNE SUP 9
        	if (((numberStation >= 125) && (numberStation < 225) &&(i+9 <= numberStation) && ((i % 9) == 0))  || ((numberStation >= 125) && (numberStation < 225) && i == 1)) {
        		if(((i+8) <= (numberStation/8)) && (i <= (numberStation/8))) {
        			if(i == 1) {
            			g.setColor( Color.BLUE );
            			g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
            			g.setColor( Color.RED );
            			if(i+4 <= (numberStation/8)) {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/8) * 5,((longueur/(numberStation+1)) * (i+8)));
            			}else {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/4) * 3,((longueur/(numberStation+1)) * (i+8)));
            			}
        			}else if(i % 2 == 0) {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+8)));
        			} else {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),(largeur/8) * 3,((longueur/(numberStation+1)) * (i+8)));
        			}
        		}else if((i <= (numberStation/8))) {
        			if(i == 1) {
            			g.setColor( Color.BLUE );
            			g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
            			g.setColor( Color.RED );
            			if(i+4 <= (numberStation/8)) {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/8) * 5,((longueur/(numberStation+1)) * (i+4)));
            			}else {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/4) * 3,((longueur/(numberStation+1)) * (i+4)));
            			}
        			}else if(i % 2 == 0) {
            				g.setColor( Color.RED );
            				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+9)));
            			} else {
            				g.setColor( Color.RED );
            				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),(largeur/4),((longueur/(numberStation+1)) * (i+9)));
            			}
        		}else if((numberStation == (i+9)) && (i>=(numberStation/8)*7)) {
        			if(i % 2 != 0) {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			} else {
        				g.setColor( Color.RED );
        				g.drawLine(( (largeur/8) * 3),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			}	
        		}else if(((i+9)>=(numberStation/8)*7) && (i>=(numberStation/8)*7)){
        			if(i % 2 != 0) {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),((largeur/8) * 3),((longueur/(numberStation+1)) * (i+9)));
        			} else {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+9)));
        			}	
        		}else if((numberStation == (i+9))){
        			if(i % 2 != 0) {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			} else {
        				g.setColor( Color.RED );
        				g.drawLine((largeur/4),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			}	
        		}else if((i+9 >= (numberStation/8)*7)){
        			if(i % 2 != 0) {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 3),((longueur/(numberStation+1)) * (i+9)));
        			} else {
        				g.setColor( Color.RED );
        				g.drawLine((largeur/4),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+9)));
        			}	
        		} else if((i % 2) == 0) {
        			g.setColor( Color.RED );
            		g.drawLine(largeur/4,((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+9)));
        		} else{
        			g.setColor( Color.RED );
            		g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/4,((longueur/(numberStation+1)) * (i+9)));
        		}
        	}
        	
        	//LIGNE SUP 5    	
        	if (((numberStation >= 125) &&  (numberStation < 225) && (i+5 <= numberStation) && ((i % 5) == 0))  || ((numberStation >= 125) &&  (numberStation < 225) && i == 1)) {
        		if(((i+5) <= (numberStation/8)) && (i <= (numberStation/8))) {
        			if(i == 1) {
            			g.setColor( Color.BLUE );
            			g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
            			g.setColor( Color.ORANGE );
            			if(i+4 <= (numberStation/8)) {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/8) * 5,((longueur/(numberStation+1)) * (i+4)));
            			}else {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/4) * 3,((longueur/(numberStation+1)) * (i+4)));
            			}
        			}else if(i % 2 == 0) {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+5)));
        			} else {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),(largeur/8) * 3,((longueur/(numberStation+1)) * (i+5)));
        			}
        		}else if((i <= (numberStation/8))) {
        			if(i == 1) {
            			g.setColor( Color.BLUE );
            			g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
            			g.setColor( Color.ORANGE );
            			if(i+4 <= (numberStation/8)) {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/8) * 5,((longueur/(numberStation+1)) * (i+4)));
            			}else {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/4) * 3,((longueur/(numberStation+1)) * (i+4)));
            			}
        			}else if(i % 2 == 0) {
            				g.setColor( Color.ORANGE );
            				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+5)));
            			} else {
            				g.setColor( Color.ORANGE );
            				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),(largeur/4),((longueur/(numberStation+1)) * (i+5)));
            			}
        		}else if((numberStation == (i+5)) && (i>=(numberStation/8)*7)) {
        			if(i % 2 != 0) {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			} else {
        				g.setColor( Color.ORANGE );
        				g.drawLine(( (largeur/8) * 3),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			}	
        		}else if(((i+5)>=(numberStation/8)*7) && (i>=(numberStation/8)*7)){
        			if(i % 2 != 0) {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),((largeur/8) * 3),((longueur/(numberStation+1)) * (i+5)));
        			} else {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+5)));
        			}	
        		}else if((numberStation == (i+5))){
        			if(i % 2 != 0) {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			} else {
        				g.setColor( Color.ORANGE );
        				g.drawLine((largeur/4),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			}	
        		}else if((i+5 >= (numberStation/8)*7)){
        			if(i % 2 != 0) {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 3),((longueur/(numberStation+1)) * (i+5)));
        			} else {
        				g.setColor( Color.ORANGE );
        				g.drawLine((largeur/4),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+5)));
        			}	
        		} else if((i % 2) == 0) {
        			g.setColor( Color.ORANGE );
            		g.drawLine(largeur/4,((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+5)));
        		} else{
        			g.setColor( Color.ORANGE );
            		g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/4,((longueur/(numberStation+1)) * (i+5)));
        		}
        	}	
        	
        	
        	//LIGNE SUP 30
        	if (((numberStation >= 125) &&  (numberStation < 225) && (i+5 <= numberStation) && ((i % 25) == 0))  || ((numberStation >= 125) &&  (numberStation < 225) && i == 1)) {
        		if(((i+25) <= (numberStation/8)) && (i <= (numberStation/8))) {
        			if(i == 1) {
            			g.setColor( Color.BLUE );
            			g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
            			g.setColor( Color.MAGENTA );
            			if(i+24 <= (numberStation/8)) {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/8) * 5,((longueur/(numberStation+1)) * (i+24)));
            			}else {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/4) * 3,((longueur/(numberStation+1)) * (i+24)));
            			}
        			}else if(i % 2 == 0) {
        				g.setColor( Color.MAGENTA );
        				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+25)));
        			} else {
        				g.setColor( Color.MAGENTA );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),(largeur/8) * 3,((longueur/(numberStation+1)) * (i+25)));
        			}
        		}else if((i <= (numberStation/8))) {
        			if(i == 1) {
            			g.setColor( Color.BLUE );
            			g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
            			g.setColor( Color.MAGENTA );
            			if(i+24 <= (numberStation/8)) {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/8) * 5,((longueur/(numberStation+1)) * (i+24)));
            			}else {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/4) * 3,((longueur/(numberStation+1)) * (i+24)));
            			}
        			}else if(i % 2 == 0) {
            				g.setColor( Color.MAGENTA );
            				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+25)));
            			} else {
            				g.setColor( Color.MAGENTA );
            				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),(largeur/4),((longueur/(numberStation+1)) * (i+25)));
            			}
        		}else if((numberStation == (i+25)) && (i>=(numberStation/8)*7)) {
        			if(i % 2 != 0) {
        				g.setColor( Color.MAGENTA );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			} else {
        				g.setColor( Color.MAGENTA );
        				g.drawLine(( (largeur/8) * 3),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			}	
        		}else if((i+25) > numberStation) {
        			//DO NOTHING
        		}else if(((i+25)>=(numberStation/8)*7) && (i>=(numberStation/8)*7)){
        			if(i % 2 != 0) {
        				g.setColor( Color.MAGENTA );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),((largeur/8) * 3),((longueur/(numberStation+1)) * (i+25)));
        			} else {
        				g.setColor( Color.MAGENTA );
        				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+25)));
        			}	
        		}else if((numberStation == (i+25))){
        			if(i % 2 != 0) {
        				g.setColor( Color.MAGENTA );
        				g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			} else {
        				g.setColor( Color.MAGENTA );
        				g.drawLine((largeur/4),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			}	
        		} else if((i+25 >= (numberStation/8)*7) && ((i+25) <= numberStation)){
        			if(i % 2 != 0) {
        				g.setColor( Color.MAGENTA );
        				g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 3),((longueur/(numberStation+1)) * (i+25)));
        			} else {
        				g.setColor( Color.MAGENTA );
        				g.drawLine((largeur/4),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+25)));
        			}	
        		}  else if((i % 2) == 0) {
        			g.setColor( Color.MAGENTA );
            		g.drawLine(largeur/4,((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+25)));
        		} else{
        			g.setColor( Color.MAGENTA );
            		g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/4,((longueur/(numberStation+1)) * (i+25)));
        		}
        	}
        	//FIN IF
        }
        
        
        //ALGO 225 300
        
        if ((numberStation >= 225) && (numberStation <= 300)) {
        	//LIGNE SUP 9
        	if (((numberStation >= 225) && (numberStation <= 300) &&(i+9 <= numberStation) && ((i % 9) == 0))  || ((numberStation >= 225) && (numberStation <= 300) && i == 1)) {
        		if(((i+8) <= (numberStation/8)) && (i <= (numberStation/8))) {
        			if(i == 1) {
            			g.setColor( Color.BLUE );
            			g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
            			g.setColor( Color.RED );
            			if(i+4 <= (numberStation/8)) {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/8) * 5,((longueur/(numberStation+1)) * (i+8)));
            			}else {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/4) * 3,((longueur/(numberStation+1)) * (i+8)));
            			}
        			}else if(i % 2 == 0) {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+8)));
        			} else {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),(largeur/8) * 3,((longueur/(numberStation+1)) * (i+8)));
        			}
        		}else if((i <= (numberStation/8))) {
        			if(i == 1) {
            			g.setColor( Color.BLUE );
            			g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
            			g.setColor( Color.RED );
            			if(i+4 <= (numberStation/8)) {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/8) * 5,((longueur/(numberStation+1)) * (i+4)));
            			}else {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/4) * 3,((longueur/(numberStation+1)) * (i+4)));
            			}
        			}else if(i % 2 == 0) {
            				g.setColor( Color.RED );
            				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+9)));
            			} else {
            				g.setColor( Color.RED );
            				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),(largeur/4),((longueur/(numberStation+1)) * (i+9)));
            			}
        		}else if((numberStation == (i+9)) && (i>=(numberStation/8)*7)) {
        			if(i % 2 != 0) {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			} else {
        				g.setColor( Color.RED );
        				g.drawLine(( (largeur/8) * 3),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			}	
        		}else if(((i+9)>=(numberStation/8)*7) && (i>=(numberStation/8)*7)){
        			if(i % 2 != 0) {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),((largeur/8) * 3),((longueur/(numberStation+1)) * (i+9)));
        			} else {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+9)));
        			}	
        		}else if((numberStation == (i+9))){
        			if(i % 2 != 0) {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			} else {
        				g.setColor( Color.RED );
        				g.drawLine((largeur/4),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			}	
        		}else if((i+9 >= (numberStation/8)*7)){
        			if(i % 2 != 0) {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 3),((longueur/(numberStation+1)) * (i+9)));
        			} else {
        				g.setColor( Color.RED );
        				g.drawLine((largeur/4),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+9)));
        			}	
        		} else if((i % 2) == 0) {
        			g.setColor( Color.RED );
            		g.drawLine(largeur/4,((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+9)));
        		} else{
        			g.setColor( Color.RED );
            		g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/4,((longueur/(numberStation+1)) * (i+9)));
        		}
        	}
        	
        	//LIGNE SUP 5    	
        	if (((numberStation >= 225) &&  (numberStation <= 300) && (i+5 <= numberStation) && ((i % 5) == 0))  || ((numberStation >= 225) &&  (numberStation <= 300) && i == 1)) {
        		if(((i+5) <= (numberStation/8)) && (i <= (numberStation/8))) {
        			if(i == 1) {
            			g.setColor( Color.BLUE );
            			g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
            			g.setColor( Color.ORANGE );
            			if(i+4 <= (numberStation/8)) {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/8) * 5,((longueur/(numberStation+1)) * (i+4)));
            			}else {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/4) * 3,((longueur/(numberStation+1)) * (i+4)));
            			}
        			}else if(i % 2 == 0) {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+5)));
        			} else {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),(largeur/8) * 3,((longueur/(numberStation+1)) * (i+5)));
        			}
        		}else if((i <= (numberStation/8))) {
        			if(i == 1) {
            			g.setColor( Color.BLUE );
            			g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
            			g.setColor( Color.ORANGE );
            			if(i+4 <= (numberStation/8)) {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/8) * 5,((longueur/(numberStation+1)) * (i+4)));
            			}else {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/4) * 3,((longueur/(numberStation+1)) * (i+4)));
            			}
        			}else if(i % 2 == 0) {
            				g.setColor( Color.ORANGE );
            				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+5)));
            			} else {
            				g.setColor( Color.ORANGE );
            				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),(largeur/4),((longueur/(numberStation+1)) * (i+5)));
            			}
        		}else if((numberStation == (i+5)) && (i>=(numberStation/8)*7)) {
        			if(i % 2 != 0) {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			} else {
        				g.setColor( Color.ORANGE );
        				g.drawLine(( (largeur/8) * 3),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			}	
        		}else if(((i+5)>=(numberStation/8)*7) && (i>=(numberStation/8)*7)){
        			if(i % 2 != 0) {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),((largeur/8) * 3),((longueur/(numberStation+1)) * (i+5)));
        			} else {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+5)));
        			}	
        		}else if((numberStation == (i+5))){
        			if(i % 2 != 0) {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			} else {
        				g.setColor( Color.ORANGE );
        				g.drawLine((largeur/4),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			}	
        		}else if((i+5 >= (numberStation/8)*7)){
        			if(i % 2 != 0) {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 3),((longueur/(numberStation+1)) * (i+5)));
        			} else {
        				g.setColor( Color.ORANGE );
        				g.drawLine((largeur/4),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+5)));
        			}	
        		} else if((i % 2) == 0) {
        			g.setColor( Color.ORANGE );
            		g.drawLine(largeur/4,((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+5)));
        		} else{
        			g.setColor( Color.ORANGE );
            		g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/4,((longueur/(numberStation+1)) * (i+5)));
        		}
        	}	
        	
        	
        	//LIGNE SUP 30
        	if (((numberStation >= 225) &&  (numberStation <= 300) && (i+5 <= numberStation) && ((i % 25) == 0))  || ((numberStation >= 225) &&  (numberStation <= 300) && i == 1)) {
        		if(((i+25) <= (numberStation/8)) && (i <= (numberStation/8))) {
        			if(i == 1) {
            			g.setColor( Color.BLUE );
            			g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
            			g.setColor( Color.MAGENTA );
            			if(i+24 <= (numberStation/8)) {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/8) * 5,((longueur/(numberStation+1)) * (i+24)));
            			}else {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/4) * 3,((longueur/(numberStation+1)) * (i+24)));
            			}
        			}else if(i % 2 == 0) {
        				g.setColor( Color.MAGENTA );
        				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+25)));
        			} else {
        				g.setColor( Color.MAGENTA );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),(largeur/8) * 3,((longueur/(numberStation+1)) * (i+25)));
        			}
        		}else if((i <= (numberStation/8))) {
        			if(i == 1) {
            			g.setColor( Color.BLUE );
            			g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
            			g.setColor( Color.MAGENTA );
            			if(i+24 <= (numberStation/8)) {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/8) * 5,((longueur/(numberStation+1)) * (i+24)));
            			}else {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/4) * 3,((longueur/(numberStation+1)) * (i+24)));
            			}
        			}else if(i % 2 == 0) {
            				g.setColor( Color.MAGENTA );
            				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+25)));
            			} else {
            				g.setColor( Color.MAGENTA );
            				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),(largeur/4),((longueur/(numberStation+1)) * (i+25)));
            			}
        		}else if((numberStation == (i+25)) && (i>=(numberStation/8)*7)) {
        			if(i % 2 != 0) {
        				g.setColor( Color.MAGENTA );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			} else {
        				g.setColor( Color.MAGENTA );
        				g.drawLine(( (largeur/8) * 3),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			}	
        		}else if((i+25) > numberStation) {
        			//DO NOTHING
        		}else if(((i+25)>=(numberStation/8)*7) && (i>=(numberStation/8)*7)){
        			if(i % 2 != 0) {
        				g.setColor( Color.MAGENTA );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),((largeur/8) * 3),((longueur/(numberStation+1)) * (i+25)));
        			} else {
        				g.setColor( Color.MAGENTA );
        				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+25)));
        			}	
        		}else if((numberStation == (i+25))){
        			if(i % 2 != 0) {
        				g.setColor( Color.MAGENTA );
        				g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			} else {
        				g.setColor( Color.MAGENTA );
        				g.drawLine((largeur/4),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			}	
        		} else if((i+25 >= (numberStation/8)*7) && ((i+25) <= numberStation)){
        			if(i % 2 != 0) {
        				g.setColor( Color.MAGENTA );
        				g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 3),((longueur/(numberStation+1)) * (i+25)));
        			} else {
        				g.setColor( Color.MAGENTA );
        				g.drawLine((largeur/4),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+25)));
        			}	
        		}  else if((i % 2) == 0) {
        			g.setColor( Color.MAGENTA );
            		g.drawLine(largeur/4,((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+25)));
        		} else{
        			g.setColor( Color.MAGENTA );
            		g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/4,((longueur/(numberStation+1)) * (i+25)));
        		}
        	}
        	
        	//LIGNE SUP 50
        	if (((numberStation >= 225) &&  (numberStation <= 300) && (i+5 <= numberStation) && ((i % 50) == 0))  || ((numberStation >= 225) &&  (numberStation <= 300) && i == 1)) {
        		if(((i+50) <= (numberStation/8)) && (i <= (numberStation/8))) {
        			if(i == 1) {
            			g.setColor( Color.BLUE );
            			g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
            			g.setColor( Color.GREEN );
            			if(i+49 <= (numberStation/8)) {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/8) * 5,((longueur/(numberStation+1)) * (i+49)));
            			}else {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/4) * 3,((longueur/(numberStation+1)) * (i+49)));
            			}
        			}else if(i % 100 == 0) {
        				g.setColor( Color.GREEN );
        				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+50)));
        			} else {
        				g.setColor( Color.GREEN );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),(largeur/8) * 3,((longueur/(numberStation+1)) * (i+50)));
        			}
        		}else if((i <= (numberStation/8))) {
        			if(i == 1) {
            			g.setColor( Color.BLUE );
            			g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
            			g.setColor( Color.GREEN );
            			if(i+49 <= (numberStation/8)) {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/8) * 5,((longueur/(numberStation+1)) * (i+49)));
            			}else {
            				g.drawLine(largeur/2,((longueur/(numberStation+1)) * i),(largeur/4) * 3,((longueur/(numberStation+1)) * (i+49)));
            			}
        			}else if(i % 100 == 0) {
            				g.setColor( Color.GREEN );
            				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+50)));
            			} else {
            				g.setColor( Color.GREEN );
            				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),(largeur/4),((longueur/(numberStation+1)) * (i+50)));
            			}
        		}else if((numberStation == (i+50)) && (i>=(numberStation/8)*7)) {
        			if(i % 100 != 0) {
        				g.setColor( Color.GREEN );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			} else {
        				g.setColor( Color.GREEN );
        				g.drawLine(( (largeur/8) * 3),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			}	
        		}else if((i+50) > numberStation) {
        			//DO NOTHING
        		}else if(((i+50)>=(numberStation/8)*7) && (i>=(numberStation/8)*7)){
        			if(i % 100 != 0) {
        				g.setColor( Color.GREEN );
        				g.drawLine(((largeur/8) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+50)));
        			} else {
        				g.setColor( Color.GREEN );
        				g.drawLine(((largeur/8) * 5),((longueur/(numberStation+1)) * i),((largeur/8) * 3),((longueur/(numberStation+1)) * (i+50)));
        			}	
        		}else if((numberStation == (i+50))){
        			if(i % 100 != 0) {
        				g.setColor( Color.GREEN );
        				g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			} else {
        				g.setColor( Color.GREEN );
        				g.drawLine((largeur/4),((longueur/(numberStation+1)) * i),largeur/2,((longueur/(numberStation+1)) * (numberStation)));
        			}	
        		} else if((i+50 >= (numberStation/8)*7) && ((i+50) <= numberStation)){
        			if(i % 100 != 0) {
        				g.setColor( Color.GREEN );
        				g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),((largeur/8) * 3),((longueur/(numberStation+1)) * (i+50)));
        			} else {
        				g.setColor( Color.GREEN );
        				g.drawLine((largeur/4),((longueur/(numberStation+1)) * i),((largeur/8) * 5),((longueur/(numberStation+1)) * (i+50)));
        			}	
        		}  else if((i % 100) != 0) {
        			g.setColor( Color.GREEN );
            		g.drawLine(((largeur/4) * 3),((longueur/(numberStation+1)) * i),largeur/4 ,((longueur/(numberStation+1)) * (i+50)));
        		} else{
        			g.setColor( Color.GREEN );
        			g.drawLine(largeur/4,((longueur/(numberStation+1)) * i),((largeur/4) * 3),((longueur/(numberStation+1)) * (i+50)));
        		}
        	}
        	//FIN IF
        }	
        	//SEPERATION 16 OK
        	
        }else {
        		  
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
        	
            	if  (((numberStation > 5) && (numberStation < 20) &&(i+5 <= numberStation) && ((i % 5) == 0))  || ((numberStation > 5) && (numberStation < 20) && i == 1))  {
            		if(i == 1) {
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
        }