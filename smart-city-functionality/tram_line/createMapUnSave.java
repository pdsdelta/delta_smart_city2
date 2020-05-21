package tram_line;

import java.awt.event.*;
import java.rmi.UnexpectedException;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import tram_line.tramExceptions.formatCityExceptions;
import tram_line.tramExceptions.errorBudgetExceptions;
import tram_line.tramExceptions.errorNumberStation;
import tram_line.tramExceptions.unexpectedHeight;

class createMapUnSave extends JPanel {
  int longueur = ((option.monInt("Veuillez entrer la longueur de la ville en km : ")) * 100);
  int largeur = ((option.monInt("Veuillez entrer la largeur de la ville en km : ")) * 100);
  double mapTaille = ((((longueur/100)/2) * ((largeur/100)/2)) * Math.PI);
  int budgetCity = transition.budgetCity1;
  int budgetStation = option.monInt("Veuillez entrer le coût unitaire d'une station : ");
  int nombreStation = stationPlacement.numberStation(budgetCity,budgetStation);
  int numberLine = 0;
  int numberTram = 0;
  int longueurLine = 0;
  int x,y;
  double z = 1;

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
    //calcul longueur de la ligne
    if(nombreStation < 16) {
    	longueurLine = ((((largeur / 4)*2)*nombreStation)/100);
    }else {
    	longueurLine = (((((largeur / 4)*2)*(nombreStation / 2)) + (((largeur / 8)*2)*(nombreStation / 2)))/100);
    }
    
    //enregistrement des variables méthode locale
    transition.saveLlt(longueur,largeur, mapTaille, budgetStation, nombreStation, longueurLine);
        
    //nombre de trams sur le réseau
    if((nombreStation == 2) || (nombreStation == 3)) {
    	numberTram = 2;
    }else {
    	numberTram = (nombreStation / 2); 
    }
    //nombre de lignes
    if(nombreStation < 6) {
    	numberLine = 1;
    }else if((nombreStation >= 6) && (nombreStation < 16)) {
		numberLine = 2;
    }else if((nombreStation >= 16) && (nombreStation < 70)) {
    	numberLine = 3;
    }else if((nombreStation >= 70) && (nombreStation < 125)) {
    	numberLine = 4;
    }else if ((nombreStation >= 125) && (nombreStation < 225)) {
    	numberLine = 5;
    }else if((nombreStation >= 225) && (nombreStation <= 300)) {
    	numberLine = 6;
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
    
    //taille de la station en fonction du nombre de station
    if(nombreStation < 60) {
    	x = 10;
    	y = 10;
    }else if((nombreStation >= 60) && (nombreStation < 150)){
    	x = 7;
    	y = 7;
    }else if((nombreStation >= 150) && (nombreStation < 200)){
    	x = 6;
    	y = 6;
    }else if((nombreStation >= 200) && (nombreStation <= 300)){
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
    
    if(nombreStation > 200) {
    	longueur = longueur+40;	
    }else if(nombreStation > 140) {
    	longueur = longueur+20;	
    }
    
   //DEBUT ALGORITHME
   for(int i= 1; i <= nombreStation; i++) {
    if(nombreStation >= 16 ) {
    	if(nombreStation == 2) {
    		g.setColor( Color.black );
    		if((i % 2) == 0) {
        		g.drawOval(largeur/2,(longueur/4), x, y);
        	} else {
        		g.drawOval((largeur/2),((longueur/4) * 3), x, y);
        	}
    		g.drawLine(largeur/2,(longueur/4), (largeur/2),((longueur/4) * 3));
    	} else if((i == 1)) {
    		g.setColor( Color.black );
    		g.drawOval(largeur/2,((longueur/(nombreStation+1)) * i), x, y);

    		g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),((largeur/8) * 3),((longueur/(nombreStation+1)) * (i+1)));
    	} else if(i == nombreStation) {
    		g.setColor( Color.black );
    		g.drawOval(largeur/2,((longueur/(nombreStation+1)) * i), x, y);	
    	} else if(i <= (nombreStation / 8)) {
    		if(i == (nombreStation / 8)) {
    			if((i % 2) == 0) {
            		g.setColor( Color.black );
            		g.drawOval((largeur/8) * 3,((longueur/(nombreStation+1)) * i), x, y);
            		g.drawLine((largeur/8) * 3,((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+1)));
            		}else {
            		g.setColor( Color.black );
                	g.drawOval((largeur/8) * 5,((longueur/(nombreStation+1)) * i), x, y);
                	g.drawLine((largeur/8) * 5,((longueur/(nombreStation+1)) * i),largeur/4,((longueur/(nombreStation+1)) * (i+1)));    			
            		}
    		} else if((i % 2) == 0) {
        		g.setColor( Color.black );
        		g.drawOval((largeur/8) * 3,((longueur/(nombreStation+1)) * i), x, y);
        		g.drawLine((largeur/8) * 3,((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+1)));
        		}else {
        		g.setColor( Color.black );
            	g.drawOval((largeur/8) * 5,((longueur/(nombreStation+1)) * i), x, y);
            	g.drawLine((largeur/8) * 5,((longueur/(nombreStation+1)) * i),((largeur/8) * 3),((longueur/(nombreStation+1)) * (i+1)));    			
        		}
    		} else if((((nombreStation / 8) * 7) - 1) == i) {
    			if((i % 2) == 0) {
            		g.setColor( Color.black );
            		g.drawOval((largeur / 4),((longueur/(nombreStation+1)) * i), x, y);
            		g.drawLine((largeur / 4),((longueur/(nombreStation+1)) * i),((largeur / 8) * 5),((longueur/(nombreStation+1)) * (i+1)));
            		}else {
            		g.setColor( Color.black );
                	g.drawOval((largeur/4) * 3,((longueur/(nombreStation+1)) * i), x, y);
                	g.drawLine((largeur/4) * 3,((longueur/(nombreStation+1)) * i),((largeur / 8) * 3),((longueur/(nombreStation+1)) * (i+1)));    			
            		}
    	}else if(i >= ((nombreStation / 8)*7)) {
    		if(i == (nombreStation - 1)) {
    			if((i % 2) == 0) {
            		g.setColor( Color.black );
            		g.drawOval((largeur/8) * 3,((longueur/(nombreStation+1)) * i), x, y);
            		g.drawLine((largeur/8) * 3,((longueur/(nombreStation+1)) * i),(largeur / 2),((longueur/(nombreStation+1)) * (i+1)));
            		}else {
            		g.setColor( Color.black );
                	g.drawOval((largeur/8) * 5,((longueur/(nombreStation+1)) * i), x, y);
                	g.drawLine((largeur/8) * 5,((longueur/(nombreStation+1)) * i),(largeur / 2),((longueur/(nombreStation+1)) * (i+1)));    			
            		}
    		} else if((i % 2) == 0) {
        		g.setColor( Color.black );
        		g.drawOval((largeur/8) * 3,((longueur/(nombreStation+1)) * i), x, y);
        		g.drawLine((largeur/8) * 3,((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+1)));
        		}else {
        		g.setColor( Color.black );
            	g.drawOval((largeur/8) * 5,((longueur/(nombreStation+1)) * i), x, y);
            	g.drawLine((largeur/8) * 5,((longueur/(nombreStation+1)) * i),((largeur/8) * 3),((longueur/(nombreStation+1)) * (i+1)));    			
        		}	
    	} else if((i % 2) == 0) {
    		g.setColor( Color.black );
    		g.drawOval(largeur/4,((longueur/(nombreStation+1)) * i), x, y);
    		if(i == (nombreStation -1)) {
    			g.drawLine(largeur/4,((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (i+1)));
    		}else {
    			g.drawLine(largeur/4,((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+1)));
    		}
    	} else {
    		g.setColor( Color.black );
    		g.drawOval(((largeur/4) * 3),((longueur/(nombreStation+1)) * i), x, y);
    		if(i == (nombreStation -1)) {
    			g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (i+1)));
    		}else {
    			g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/4,((longueur/(nombreStation+1)) * (i+1)));
    		}
    	}
  	
    	//VALEUR 16 / 30 ALGO
    	
    	if ((nombreStation >= 16) && (nombreStation < 30)) {
    	
    	if (((nombreStation >= 16) && (nombreStation < 30) &&(i+9 <= nombreStation) && ((i % 9) == 0))  || ((nombreStation >= 16) && (nombreStation < 30) && i == 1)) {
    		
    	
    		if((i <= (nombreStation/8))) {
    			if(i == 1) {
        			g.setColor( Color.BLUE );
        			g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
        			g.setColor( Color.RED );
        			if(i+8 <= (nombreStation/8)) {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/8) * 5,((longueur/(nombreStation+1)) * (i+8)));
        			}else {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/4) * 3,((longueur/(nombreStation+1)) * (i+8)));
        			}
    			}else if(nombreStation == (nombreStation/8)) {
    				if(i % 2 == 0) {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+8)));
        			} else {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),(largeur/4),((longueur/(nombreStation+1)) * (i+8)));
        			}
    			}
    		
    		}else if((nombreStation == (i+9)) && (i>=(nombreStation/8)*7)) {
    			if(i % 2 != 0) {
    				g.setColor( Color.RED );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			} else {
    				g.setColor( Color.RED );
    				g.drawLine(( (largeur/8) * 3),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			}	
    		}else if((nombreStation == (i+9))){
    			if(i % 2 != 0) {
    				g.setColor( Color.RED );
    				g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			} else {
    				g.setColor( Color.RED );
    				g.drawLine((largeur/4),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			}	
    		}else if((i+9 >= (nombreStation/8)*7)){
    			if(i % 2 != 0) {
    				g.setColor( Color.RED );
    				g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 3),((longueur/(nombreStation+1)) * (i+9)));
    			} else {
    				g.setColor( Color.RED );
    				g.drawLine((largeur/4),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+9)));
    			}	
    		} else if((i % 2) == 0) {
    			g.setColor( Color.RED );
        		g.drawLine(largeur/4,((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+9)));
    		} else{
    			g.setColor( Color.RED );
        		g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/4,((longueur/(nombreStation+1)) * (i+9)));
    		}
    	}
    	//FIN if
    	}
    	
    	
    	//VALEUR 30 / 70 ALGO
    if ((nombreStation >= 30) && (nombreStation < 70)) {
    	if (((nombreStation >= 30) &&  (nombreStation < 70) && (i+5 <= nombreStation) && ((i % 5) == 0))  || ((nombreStation >= 30) &&  (nombreStation < 70) && i == 1)) {
    		
    		if(((i+4) <= (nombreStation/8)) && (i <= (nombreStation/8))) {
    			if(i == 1) {
        			g.setColor( Color.BLUE );
        			g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
        			g.setColor( Color.ORANGE );
        			if(i+4 <= (nombreStation/8)) {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/8) * 5,((longueur/(nombreStation+1)) * (i+4)));
        			}else {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/4) * 3,((longueur/(nombreStation+1)) * (i+4)));
        			}
    			}else if(i % 2 == 0) {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+4)));
    			} else {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),(largeur/8) * 3,((longueur/(nombreStation+1)) * (i+4)));
    			}
    		}else if((i <= (nombreStation/8))) {
    			if(i == 1) {
        			g.setColor( Color.BLUE );
        			g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
        			g.setColor( Color.ORANGE );
        			if(i+4 <= (nombreStation/8)) {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/8) * 5,((longueur/(nombreStation+1)) * (i+4)));
        			}else {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/4) * 3,((longueur/(nombreStation+1)) * (i+4)));
        			}
    			}else if(i % 2 == 0) {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+4)));
        			} else {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),(largeur/4),((longueur/(nombreStation+1)) * (i+4)));
        			}
    		}else if((nombreStation == (i+5)) && (i>=(nombreStation/8)*7)) {
    			if(i % 2 != 0) {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			} else {
    				g.setColor( Color.ORANGE );
    				g.drawLine(( (largeur/8) * 3),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			}	
    		}else if(((i+5)>=(nombreStation/8)*7) && (i>=(nombreStation/8)*7)){
    			if(i % 2 != 0) {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),((largeur/8) * 3),((longueur/(nombreStation+1)) * (i+5)));
    			} else {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+5)));
    			}	
    		}else if((nombreStation == (i+5))){
    			if(i % 2 != 0) {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			} else {
    				g.setColor( Color.ORANGE );
    				g.drawLine((largeur/4),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			}	
    		}else if((i+5 >= (nombreStation/8)*7)){
    			if(i % 2 != 0) {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 3),((longueur/(nombreStation+1)) * (i+5)));
    			} else {
    				g.setColor( Color.ORANGE );
    				g.drawLine((largeur/4),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+5)));
    			}	
    		} else if((i % 2) == 0) {
    			g.setColor( Color.ORANGE );
        		g.drawLine(largeur/4,((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+5)));
    		} else{
    			g.setColor( Color.ORANGE );
        		g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/4,((longueur/(nombreStation+1)) * (i+5)));
    		}
    	}	
    	//FIN IF
    }
    	//ALGO 70 125
    if ((nombreStation >= 70) && (nombreStation < 125)) {
    	//LIGNE SUP 9
    	if (((nombreStation >= 70) && (nombreStation < 125) &&(i+9 <= nombreStation) && ((i % 9) == 0))  || ((nombreStation >= 70) && (nombreStation < 125) && i == 1)) {
    		if(((i+8) <= (nombreStation/8)) && (i <= (nombreStation/8))) {
    			if(i == 1) {
        			g.setColor( Color.BLUE );
        			g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
        			g.setColor( Color.RED );
        			if(i+4 <= (nombreStation/8)) {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/8) * 5,((longueur/(nombreStation+1)) * (i+8)));
        			}else {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/4) * 3,((longueur/(nombreStation+1)) * (i+8)));
        			}
    			}else if(i % 2 == 0) {
    				g.setColor( Color.RED );
    				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+8)));
    			} else {
    				g.setColor( Color.RED );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),(largeur/8) * 3,((longueur/(nombreStation+1)) * (i+8)));
    			}
    		}else if((i <= (nombreStation/8))) {
    			if(i == 1) {
        			g.setColor( Color.BLUE );
        			g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
        			g.setColor( Color.RED );
        			if(i+4 <= (nombreStation/8)) {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/8) * 5,((longueur/(nombreStation+1)) * (i+4)));
        			}else {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/4) * 3,((longueur/(nombreStation+1)) * (i+4)));
        			}
    			}else if(i % 2 == 0) {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+9)));
        			} else {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),(largeur/4),((longueur/(nombreStation+1)) * (i+9)));
        			}
    		}else if((nombreStation == (i+9)) && (i>=(nombreStation/8)*7)) {
    			if(i % 2 != 0) {
    				g.setColor( Color.RED );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			} else {
    				g.setColor( Color.RED );
    				g.drawLine(( (largeur/8) * 3),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			}	
    		}else if(((i+9)>=(nombreStation/8)*7) && (i>=(nombreStation/8)*7)){
    			if(i % 2 != 0) {
    				g.setColor( Color.RED );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),((largeur/8) * 3),((longueur/(nombreStation+1)) * (i+9)));
    			} else {
    				g.setColor( Color.RED );
    				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+9)));
    			}	
    		}else if((nombreStation == (i+9))){
    			if(i % 2 != 0) {
    				g.setColor( Color.RED );
    				g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			} else {
    				g.setColor( Color.RED );
    				g.drawLine((largeur/4),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			}	
    		}else if((i+9 >= (nombreStation/8)*7)){
    			if(i % 2 != 0) {
    				g.setColor( Color.RED );
    				g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 3),((longueur/(nombreStation+1)) * (i+9)));
    			} else {
    				g.setColor( Color.RED );
    				g.drawLine((largeur/4),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+9)));
    			}	
    		} else if((i % 2) == 0) {
    			g.setColor( Color.RED );
        		g.drawLine(largeur/4,((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+9)));
    		} else{
    			g.setColor( Color.RED );
        		g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/4,((longueur/(nombreStation+1)) * (i+9)));
    		}
    	}
    	
    	//LIGNE SUP 5    	
    	if (((nombreStation >= 70) &&  (nombreStation < 125) && (i+5 <= nombreStation) && ((i % 5) == 0))  || ((nombreStation >= 70) &&  (nombreStation < 125) && i == 1)) {
    		if(((i+5) <= (nombreStation/8)) && (i <= (nombreStation/8))) {
    			if(i == 1) {
        			g.setColor( Color.BLUE );
        			g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
        			g.setColor( Color.ORANGE );
        			if(i+4 <= (nombreStation/8)) {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/8) * 5,((longueur/(nombreStation+1)) * (i+4)));
        			}else {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/4) * 3,((longueur/(nombreStation+1)) * (i+4)));
        			}
    			}else if(i % 2 == 0) {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+5)));
    			} else {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),(largeur/8) * 3,((longueur/(nombreStation+1)) * (i+5)));
    			}
    		}else if((i <= (nombreStation/8))) {
    			if(i == 1) {
        			g.setColor( Color.BLUE );
        			g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
        			g.setColor( Color.ORANGE );
        			if(i+4 <= (nombreStation/8)) {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/8) * 5,((longueur/(nombreStation+1)) * (i+4)));
        			}else {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/4) * 3,((longueur/(nombreStation+1)) * (i+4)));
        			}
    			}else if(i % 2 == 0) {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+5)));
        			} else {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),(largeur/4),((longueur/(nombreStation+1)) * (i+5)));
        			}
    		}else if((nombreStation == (i+5)) && (i>=(nombreStation/8)*7)) {
    			if(i % 2 != 0) {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			} else {
    				g.setColor( Color.ORANGE );
    				g.drawLine(( (largeur/8) * 3),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			}	
    		}else if(((i+5)>=(nombreStation/8)*7) && (i>=(nombreStation/8)*7)){
    			if(i % 2 != 0) {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),((largeur/8) * 3),((longueur/(nombreStation+1)) * (i+5)));
    			} else {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+5)));
    			}	
    		}else if((nombreStation == (i+5))){
    			if(i % 2 != 0) {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			} else {
    				g.setColor( Color.ORANGE );
    				g.drawLine((largeur/4),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			}	
    		}else if((i+5 >= (nombreStation/8)*7)){
    			if(i % 2 != 0) {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 3),((longueur/(nombreStation+1)) * (i+5)));
    			} else {
    				g.setColor( Color.ORANGE );
    				g.drawLine((largeur/4),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+5)));
    			}	
    		} else if((i % 2) == 0) {
    			g.setColor( Color.ORANGE );
        		g.drawLine(largeur/4,((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+5)));
    		} else{
    			g.setColor( Color.ORANGE );
        		g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/4,((longueur/(nombreStation+1)) * (i+5)));
    		}
    	}	
    	//FIN IF
    }
    	
    //ALGO 120 220
    if ((nombreStation >= 125) && (nombreStation < 220)) {
    	//LIGNE SUP 9
    	if (((nombreStation >= 125) && (nombreStation < 225) &&(i+9 <= nombreStation) && ((i % 9) == 0))  || ((nombreStation >= 125) && (nombreStation < 225) && i == 1)) {
    		if(((i+8) <= (nombreStation/8)) && (i <= (nombreStation/8))) {
    			if(i == 1) {
        			g.setColor( Color.BLUE );
        			g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
        			g.setColor( Color.RED );
        			if(i+4 <= (nombreStation/8)) {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/8) * 5,((longueur/(nombreStation+1)) * (i+8)));
        			}else {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/4) * 3,((longueur/(nombreStation+1)) * (i+8)));
        			}
    			}else if(i % 2 == 0) {
    				g.setColor( Color.RED );
    				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+8)));
    			} else {
    				g.setColor( Color.RED );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),(largeur/8) * 3,((longueur/(nombreStation+1)) * (i+8)));
    			}
    		}else if((i <= (nombreStation/8))) {
    			if(i == 1) {
        			g.setColor( Color.BLUE );
        			g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
        			g.setColor( Color.RED );
        			if(i+4 <= (nombreStation/8)) {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/8) * 5,((longueur/(nombreStation+1)) * (i+4)));
        			}else {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/4) * 3,((longueur/(nombreStation+1)) * (i+4)));
        			}
    			}else if(i % 2 == 0) {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+9)));
        			} else {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),(largeur/4),((longueur/(nombreStation+1)) * (i+9)));
        			}
    		}else if((nombreStation == (i+9)) && (i>=(nombreStation/8)*7)) {
    			if(i % 2 != 0) {
    				g.setColor( Color.RED );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			} else {
    				g.setColor( Color.RED );
    				g.drawLine(( (largeur/8) * 3),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			}	
    		}else if(((i+9)>=(nombreStation/8)*7) && (i>=(nombreStation/8)*7)){
    			if(i % 2 != 0) {
    				g.setColor( Color.RED );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),((largeur/8) * 3),((longueur/(nombreStation+1)) * (i+9)));
    			} else {
    				g.setColor( Color.RED );
    				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+9)));
    			}	
    		}else if((nombreStation == (i+9))){
    			if(i % 2 != 0) {
    				g.setColor( Color.RED );
    				g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			} else {
    				g.setColor( Color.RED );
    				g.drawLine((largeur/4),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			}	
    		}else if((i+9 >= (nombreStation/8)*7)){
    			if(i % 2 != 0) {
    				g.setColor( Color.RED );
    				g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 3),((longueur/(nombreStation+1)) * (i+9)));
    			} else {
    				g.setColor( Color.RED );
    				g.drawLine((largeur/4),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+9)));
    			}	
    		} else if((i % 2) == 0) {
    			g.setColor( Color.RED );
        		g.drawLine(largeur/4,((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+9)));
    		} else{
    			g.setColor( Color.RED );
        		g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/4,((longueur/(nombreStation+1)) * (i+9)));
    		}
    	}
    	
    	//LIGNE SUP 5    	
    	if (((nombreStation >= 125) &&  (nombreStation < 225) && (i+5 <= nombreStation) && ((i % 5) == 0))  || ((nombreStation >= 125) &&  (nombreStation < 225) && i == 1)) {
    		if(((i+5) <= (nombreStation/8)) && (i <= (nombreStation/8))) {
    			if(i == 1) {
        			g.setColor( Color.BLUE );
        			g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
        			g.setColor( Color.ORANGE );
        			if(i+4 <= (nombreStation/8)) {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/8) * 5,((longueur/(nombreStation+1)) * (i+4)));
        			}else {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/4) * 3,((longueur/(nombreStation+1)) * (i+4)));
        			}
    			}else if(i % 2 == 0) {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+5)));
    			} else {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),(largeur/8) * 3,((longueur/(nombreStation+1)) * (i+5)));
    			}
    		}else if((i <= (nombreStation/8))) {
    			if(i == 1) {
        			g.setColor( Color.BLUE );
        			g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
        			g.setColor( Color.ORANGE );
        			if(i+4 <= (nombreStation/8)) {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/8) * 5,((longueur/(nombreStation+1)) * (i+4)));
        			}else {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/4) * 3,((longueur/(nombreStation+1)) * (i+4)));
        			}
    			}else if(i % 2 == 0) {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+5)));
        			} else {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),(largeur/4),((longueur/(nombreStation+1)) * (i+5)));
        			}
    		}else if((nombreStation == (i+5)) && (i>=(nombreStation/8)*7)) {
    			if(i % 2 != 0) {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			} else {
    				g.setColor( Color.ORANGE );
    				g.drawLine(( (largeur/8) * 3),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			}	
    		}else if(((i+5)>=(nombreStation/8)*7) && (i>=(nombreStation/8)*7)){
    			if(i % 2 != 0) {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),((largeur/8) * 3),((longueur/(nombreStation+1)) * (i+5)));
    			} else {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+5)));
    			}	
    		}else if((nombreStation == (i+5))){
    			if(i % 2 != 0) {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			} else {
    				g.setColor( Color.ORANGE );
    				g.drawLine((largeur/4),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			}	
    		}else if((i+5 >= (nombreStation/8)*7)){
    			if(i % 2 != 0) {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 3),((longueur/(nombreStation+1)) * (i+5)));
    			} else {
    				g.setColor( Color.ORANGE );
    				g.drawLine((largeur/4),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+5)));
    			}	
    		} else if((i % 2) == 0) {
    			g.setColor( Color.ORANGE );
        		g.drawLine(largeur/4,((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+5)));
    		} else{
    			g.setColor( Color.ORANGE );
        		g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/4,((longueur/(nombreStation+1)) * (i+5)));
    		}
    	}	
    	
    	
    	//LIGNE SUP 30
    	if (((nombreStation >= 125) &&  (nombreStation < 225) && (i+5 <= nombreStation) && ((i % 25) == 0))  || ((nombreStation >= 125) &&  (nombreStation < 225) && i == 1)) {
    		if(((i+25) <= (nombreStation/8)) && (i <= (nombreStation/8))) {
    			if(i == 1) {
        			g.setColor( Color.BLUE );
        			g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
        			g.setColor( Color.MAGENTA );
        			if(i+24 <= (nombreStation/8)) {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/8) * 5,((longueur/(nombreStation+1)) * (i+24)));
        			}else {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/4) * 3,((longueur/(nombreStation+1)) * (i+24)));
        			}
    			}else if(i % 2 == 0) {
    				g.setColor( Color.MAGENTA );
    				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+25)));
    			} else {
    				g.setColor( Color.MAGENTA );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),(largeur/8) * 3,((longueur/(nombreStation+1)) * (i+25)));
    			}
    		}else if((i <= (nombreStation/8))) {
    			if(i == 1) {
        			g.setColor( Color.BLUE );
        			g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
        			g.setColor( Color.MAGENTA );
        			if(i+24 <= (nombreStation/8)) {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/8) * 5,((longueur/(nombreStation+1)) * (i+24)));
        			}else {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/4) * 3,((longueur/(nombreStation+1)) * (i+24)));
        			}
    			}else if(i % 2 == 0) {
        				g.setColor( Color.MAGENTA );
        				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+25)));
        			} else {
        				g.setColor( Color.MAGENTA );
        				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),(largeur/4),((longueur/(nombreStation+1)) * (i+25)));
        			}
    		}else if((nombreStation == (i+25)) && (i>=(nombreStation/8)*7)) {
    			if(i % 2 != 0) {
    				g.setColor( Color.MAGENTA );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			} else {
    				g.setColor( Color.MAGENTA );
    				g.drawLine(( (largeur/8) * 3),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			}	
    		}else if((i+25) > nombreStation) {
    			//DO NOTHING
    		}else if(((i+25)>=(nombreStation/8)*7) && (i>=(nombreStation/8)*7)){
    			if(i % 2 != 0) {
    				g.setColor( Color.MAGENTA );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),((largeur/8) * 3),((longueur/(nombreStation+1)) * (i+25)));
    			} else {
    				g.setColor( Color.MAGENTA );
    				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+25)));
    			}	
    		}else if((nombreStation == (i+25))){
    			if(i % 2 != 0) {
    				g.setColor( Color.MAGENTA );
    				g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			} else {
    				g.setColor( Color.MAGENTA );
    				g.drawLine((largeur/4),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			}	
    		} else if((i+25 >= (nombreStation/8)*7) && ((i+25) <= nombreStation)){
    			if(i % 2 != 0) {
    				g.setColor( Color.MAGENTA );
    				g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 3),((longueur/(nombreStation+1)) * (i+25)));
    			} else {
    				g.setColor( Color.MAGENTA );
    				g.drawLine((largeur/4),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+25)));
    			}	
    		}  else if((i % 2) == 0) {
    			g.setColor( Color.MAGENTA );
        		g.drawLine(largeur/4,((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+25)));
    		} else{
    			g.setColor( Color.MAGENTA );
        		g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/4,((longueur/(nombreStation+1)) * (i+25)));
    		}
    	}
    	//FIN IF
    }
    
    
    //ALGO 225 300
    
    if ((nombreStation >= 225) && (nombreStation <= 300)) {
    	//LIGNE SUP 9
    	if (((nombreStation >= 225) && (nombreStation <= 300) &&(i+9 <= nombreStation) && ((i % 9) == 0))  || ((nombreStation >= 225) && (nombreStation <= 300) && i == 1)) {
    		if(((i+8) <= (nombreStation/8)) && (i <= (nombreStation/8))) {
    			if(i == 1) {
        			g.setColor( Color.BLUE );
        			g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
        			g.setColor( Color.RED );
        			if(i+4 <= (nombreStation/8)) {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/8) * 5,((longueur/(nombreStation+1)) * (i+8)));
        			}else {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/4) * 3,((longueur/(nombreStation+1)) * (i+8)));
        			}
    			}else if(i % 2 == 0) {
    				g.setColor( Color.RED );
    				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+8)));
    			} else {
    				g.setColor( Color.RED );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),(largeur/8) * 3,((longueur/(nombreStation+1)) * (i+8)));
    			}
    		}else if((i <= (nombreStation/8))) {
    			if(i == 1) {
        			g.setColor( Color.BLUE );
        			g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
        			g.setColor( Color.RED );
        			if(i+4 <= (nombreStation/8)) {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/8) * 5,((longueur/(nombreStation+1)) * (i+4)));
        			}else {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/4) * 3,((longueur/(nombreStation+1)) * (i+4)));
        			}
    			}else if(i % 2 == 0) {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+9)));
        			} else {
        				g.setColor( Color.RED );
        				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),(largeur/4),((longueur/(nombreStation+1)) * (i+9)));
        			}
    		}else if((nombreStation == (i+9)) && (i>=(nombreStation/8)*7)) {
    			if(i % 2 != 0) {
    				g.setColor( Color.RED );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			} else {
    				g.setColor( Color.RED );
    				g.drawLine(( (largeur/8) * 3),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			}	
    		}else if(((i+9)>=(nombreStation/8)*7) && (i>=(nombreStation/8)*7)){
    			if(i % 2 != 0) {
    				g.setColor( Color.RED );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),((largeur/8) * 3),((longueur/(nombreStation+1)) * (i+9)));
    			} else {
    				g.setColor( Color.RED );
    				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+9)));
    			}	
    		}else if((nombreStation == (i+9))){
    			if(i % 2 != 0) {
    				g.setColor( Color.RED );
    				g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			} else {
    				g.setColor( Color.RED );
    				g.drawLine((largeur/4),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			}	
    		}else if((i+9 >= (nombreStation/8)*7)){
    			if(i % 2 != 0) {
    				g.setColor( Color.RED );
    				g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 3),((longueur/(nombreStation+1)) * (i+9)));
    			} else {
    				g.setColor( Color.RED );
    				g.drawLine((largeur/4),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+9)));
    			}	
    		} else if((i % 2) == 0) {
    			g.setColor( Color.RED );
        		g.drawLine(largeur/4,((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+9)));
    		} else{
    			g.setColor( Color.RED );
        		g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/4,((longueur/(nombreStation+1)) * (i+9)));
    		}
    	}
    	
    	//LIGNE SUP 5    	
    	if (((nombreStation >= 225) &&  (nombreStation <= 300) && (i+5 <= nombreStation) && ((i % 5) == 0))  || ((nombreStation >= 225) &&  (nombreStation <= 300) && i == 1)) {
    		if(((i+5) <= (nombreStation/8)) && (i <= (nombreStation/8))) {
    			if(i == 1) {
        			g.setColor( Color.BLUE );
        			g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
        			g.setColor( Color.ORANGE );
        			if(i+4 <= (nombreStation/8)) {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/8) * 5,((longueur/(nombreStation+1)) * (i+4)));
        			}else {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/4) * 3,((longueur/(nombreStation+1)) * (i+4)));
        			}
    			}else if(i % 2 == 0) {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+5)));
    			} else {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),(largeur/8) * 3,((longueur/(nombreStation+1)) * (i+5)));
    			}
    		}else if((i <= (nombreStation/8))) {
    			if(i == 1) {
        			g.setColor( Color.BLUE );
        			g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
        			g.setColor( Color.ORANGE );
        			if(i+4 <= (nombreStation/8)) {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/8) * 5,((longueur/(nombreStation+1)) * (i+4)));
        			}else {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/4) * 3,((longueur/(nombreStation+1)) * (i+4)));
        			}
    			}else if(i % 2 == 0) {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+5)));
        			} else {
        				g.setColor( Color.ORANGE );
        				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),(largeur/4),((longueur/(nombreStation+1)) * (i+5)));
        			}
    		}else if((nombreStation == (i+5)) && (i>=(nombreStation/8)*7)) {
    			if(i % 2 != 0) {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			} else {
    				g.setColor( Color.ORANGE );
    				g.drawLine(( (largeur/8) * 3),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			}	
    		}else if(((i+5)>=(nombreStation/8)*7) && (i>=(nombreStation/8)*7)){
    			if(i % 2 != 0) {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),((largeur/8) * 3),((longueur/(nombreStation+1)) * (i+5)));
    			} else {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+5)));
    			}	
    		}else if((nombreStation == (i+5))){
    			if(i % 2 != 0) {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			} else {
    				g.setColor( Color.ORANGE );
    				g.drawLine((largeur/4),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			}	
    		}else if((i+5 >= (nombreStation/8)*7)){
    			if(i % 2 != 0) {
    				g.setColor( Color.ORANGE );
    				g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 3),((longueur/(nombreStation+1)) * (i+5)));
    			} else {
    				g.setColor( Color.ORANGE );
    				g.drawLine((largeur/4),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+5)));
    			}	
    		} else if((i % 2) == 0) {
    			g.setColor( Color.ORANGE );
        		g.drawLine(largeur/4,((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+5)));
    		} else{
    			g.setColor( Color.ORANGE );
        		g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/4,((longueur/(nombreStation+1)) * (i+5)));
    		}
    	}	
    	
    	
    	//LIGNE SUP 30
    	if (((nombreStation >= 225) &&  (nombreStation <= 300) && (i+5 <= nombreStation) && ((i % 25) == 0))  || ((nombreStation >= 225) &&  (nombreStation <= 300) && i == 1)) {
    		if(((i+25) <= (nombreStation/8)) && (i <= (nombreStation/8))) {
    			if(i == 1) {
        			g.setColor( Color.BLUE );
        			g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
        			g.setColor( Color.MAGENTA );
        			if(i+24 <= (nombreStation/8)) {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/8) * 5,((longueur/(nombreStation+1)) * (i+24)));
        			}else {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/4) * 3,((longueur/(nombreStation+1)) * (i+24)));
        			}
    			}else if(i % 2 == 0) {
    				g.setColor( Color.MAGENTA );
    				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+25)));
    			} else {
    				g.setColor( Color.MAGENTA );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),(largeur/8) * 3,((longueur/(nombreStation+1)) * (i+25)));
    			}
    		}else if((i <= (nombreStation/8))) {
    			if(i == 1) {
        			g.setColor( Color.BLUE );
        			g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
        			g.setColor( Color.MAGENTA );
        			if(i+24 <= (nombreStation/8)) {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/8) * 5,((longueur/(nombreStation+1)) * (i+24)));
        			}else {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/4) * 3,((longueur/(nombreStation+1)) * (i+24)));
        			}
    			}else if(i % 2 == 0) {
        				g.setColor( Color.MAGENTA );
        				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+25)));
        			} else {
        				g.setColor( Color.MAGENTA );
        				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),(largeur/4),((longueur/(nombreStation+1)) * (i+25)));
        			}
    		}else if((nombreStation == (i+25)) && (i>=(nombreStation/8)*7)) {
    			if(i % 2 != 0) {
    				g.setColor( Color.MAGENTA );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			} else {
    				g.setColor( Color.MAGENTA );
    				g.drawLine(( (largeur/8) * 3),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			}	
    		}else if((i+25) > nombreStation) {
    			//DO NOTHING
    		}else if(((i+25)>=(nombreStation/8)*7) && (i>=(nombreStation/8)*7)){
    			if(i % 2 != 0) {
    				g.setColor( Color.MAGENTA );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),((largeur/8) * 3),((longueur/(nombreStation+1)) * (i+25)));
    			} else {
    				g.setColor( Color.MAGENTA );
    				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+25)));
    			}	
    		}else if((nombreStation == (i+25))){
    			if(i % 2 != 0) {
    				g.setColor( Color.MAGENTA );
    				g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			} else {
    				g.setColor( Color.MAGENTA );
    				g.drawLine((largeur/4),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			}	
    		} else if((i+25 >= (nombreStation/8)*7) && ((i+25) <= nombreStation)){
    			if(i % 2 != 0) {
    				g.setColor( Color.MAGENTA );
    				g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 3),((longueur/(nombreStation+1)) * (i+25)));
    			} else {
    				g.setColor( Color.MAGENTA );
    				g.drawLine((largeur/4),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+25)));
    			}	
    		}  else if((i % 2) == 0) {
    			g.setColor( Color.MAGENTA );
        		g.drawLine(largeur/4,((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+25)));
    		} else{
    			g.setColor( Color.MAGENTA );
        		g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/4,((longueur/(nombreStation+1)) * (i+25)));
    		}
    	}
    	
    	//LIGNE SUP 50
    	if (((nombreStation >= 225) &&  (nombreStation <= 300) && (i+5 <= nombreStation) && ((i % 50) == 0))  || ((nombreStation >= 225) &&  (nombreStation <= 300) && i == 1)) {
    		if(((i+50) <= (nombreStation/8)) && (i <= (nombreStation/8))) {
    			if(i == 1) {
        			g.setColor( Color.BLUE );
        			g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
        			g.setColor( Color.GREEN );
        			if(i+49 <= (nombreStation/8)) {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/8) * 5,((longueur/(nombreStation+1)) * (i+49)));
        			}else {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/4) * 3,((longueur/(nombreStation+1)) * (i+49)));
        			}
    			}else if(i % 100 == 0) {
    				g.setColor( Color.GREEN );
    				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+50)));
    			} else {
    				g.setColor( Color.GREEN );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),(largeur/8) * 3,((longueur/(nombreStation+1)) * (i+50)));
    			}
    		}else if((i <= (nombreStation/8))) {
    			if(i == 1) {
        			g.setColor( Color.BLUE );
        			g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
        			g.setColor( Color.GREEN );
        			if(i+49 <= (nombreStation/8)) {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/8) * 5,((longueur/(nombreStation+1)) * (i+49)));
        			}else {
        				g.drawLine(largeur/2,((longueur/(nombreStation+1)) * i),(largeur/4) * 3,((longueur/(nombreStation+1)) * (i+49)));
        			}
    			}else if(i % 100 == 0) {
        				g.setColor( Color.GREEN );
        				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+50)));
        			} else {
        				g.setColor( Color.GREEN );
        				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),(largeur/4),((longueur/(nombreStation+1)) * (i+50)));
        			}
    		}else if((nombreStation == (i+50)) && (i>=(nombreStation/8)*7)) {
    			if(i % 100 != 0) {
    				g.setColor( Color.GREEN );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			} else {
    				g.setColor( Color.GREEN );
    				g.drawLine(( (largeur/8) * 3),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			}	
    		}else if((i+50) > nombreStation) {
    			//DO NOTHING
    		}else if(((i+50)>=(nombreStation/8)*7) && (i>=(nombreStation/8)*7)){
    			if(i % 100 != 0) {
    				g.setColor( Color.GREEN );
    				g.drawLine(((largeur/8) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+50)));
    			} else {
    				g.setColor( Color.GREEN );
    				g.drawLine(((largeur/8) * 5),((longueur/(nombreStation+1)) * i),((largeur/8) * 3),((longueur/(nombreStation+1)) * (i+50)));
    			}	
    		}else if((nombreStation == (i+50))){
    			if(i % 100 != 0) {
    				g.setColor( Color.GREEN );
    				g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			} else {
    				g.setColor( Color.GREEN );
    				g.drawLine((largeur/4),((longueur/(nombreStation+1)) * i),largeur/2,((longueur/(nombreStation+1)) * (nombreStation)));
    			}	
    		} else if((i+50 >= (nombreStation/8)*7) && ((i+50) <= nombreStation)){
    			if(i % 100 != 0) {
    				g.setColor( Color.GREEN );
    				g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),((largeur/8) * 3),((longueur/(nombreStation+1)) * (i+50)));
    			} else {
    				g.setColor( Color.GREEN );
    				g.drawLine((largeur/4),((longueur/(nombreStation+1)) * i),((largeur/8) * 5),((longueur/(nombreStation+1)) * (i+50)));
    			}	
    		}  else if((i % 100) != 0) {
    			g.setColor( Color.GREEN );
        		g.drawLine(((largeur/4) * 3),((longueur/(nombreStation+1)) * i),largeur/4 ,((longueur/(nombreStation+1)) * (i+50)));
    		} else{
    			g.setColor( Color.GREEN );
    			g.drawLine(largeur/4,((longueur/(nombreStation+1)) * i),((largeur/4) * 3),((longueur/(nombreStation+1)) * (i+50)));
    		}
    	}
    	//FIN IF
    }	
    	//SEPERATION 16 OK
    	
    }else {
    		  
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
    	
        	if  (((nombreStation > 5) && (nombreStation < 20) &&(i+5 <= nombreStation) && ((i % 5) == 0))  || ((nombreStation > 5) && (nombreStation < 20) && i == 1))  {
        		if(i == 1) {
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
    }