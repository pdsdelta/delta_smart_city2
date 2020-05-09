package capteur_air;

import java.awt.event.*;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

class myCapteur extends JFrame {
	private JMenuBar menu = new JMenuBar();
	private JMenu onglet1 = new JMenu("Choisir endroit");
	private JMenu onglet2 = new JMenu ("Configurer Capteur");
	private JMenu onglet3 = new JMenu ("Historique");
	
	private JMenuItem case1 = new JMenuItem("Selectionner Quartier");
	private JMenuItem case2 = new JMenuItem("Fermer");
	private JMenuItem case3 = new JMenuItem("Loulou");
	private JMenuItem case4 = new JMenuItem("TR");
	
    //private Socket clientSocket;
    //private PrintWriter out;
    //private BufferedReader in;
    
   // public void startConnection(String ip, int port) throws UnknownHostException, IOException, JSONException {
    	//String toSend;
    	//clientSocket = new Socket(ip, port);
       // out = new PrintWriter(clientSocket.getOutputStream(), true);
        //in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        //while(true) {
        //	toSend =this.afficheMenu();
	    //    out.println(toSend);
	    //    String response = in.readLine();
	    //    System.out.println("***** RÃ©sultat ******\n");
	    //    System.out.println(this.diplayResult(response));
       // }
   // }
    
    public myCapteur(int a) {
    	 this.setSize(400, 200);
    	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	    this.setLocationRelativeTo(null);

    	    //On initialise nos menus      
    	    this.onglet1.add(case1);

    	    //On ajoute les éléments dans notre sous-menu
    	   // this.test1_2.add(jcmi1);
    	    //this.test1_2.add(jcmi2);
    	    //Ajout d'un séparateur
    	    //this.test1_2.addSeparator();
    	    //On met nos radios dans un ButtonGroup
    	    //ButtonGroup bg = new ButtonGroup();
    	    //bg.add(jrmi1);
    	    //bg.add(jrmi1);
    	    //On présélectionne la première radio
    	    //jrmi1.setSelected(true);

    	    //this.test1_2.add(jrmi1);
    	    //this.test1_2.add(jrmi2);

    	    //Ajout du sous-menu dans notre menu
    	    //this.test1.add(this.test1_2);
    	    //Ajout d'un séparateur
    	    //this.onglet1.addSeparator();
    	    //item2.addActionListener(new ActionListener(){
    	      //public void actionPerformed(ActionEvent arg0) {
    	        //System.exit(0);
    	      //}        
    	    //});
    	    this.onglet1.add(case2);  
    	    this.onglet2.add(case3);
    	    this.onglet2.add(case4);

    	    //L'ordre d'ajout va déterminer l'ordre d'apparition dans le menu de gauche à droite
    	    //Le premier ajouté sera tout à gauche de la barre de menu et inversement pour le dernier
    	    this.menu.add(onglet1);
    	    this.menu.add(onglet2);
            this.menu.add(onglet3);
    	    this.setJMenuBar(menu);
    	    this.setVisible(true);
    }
    
    public void getcalculIndice() {
    	int indiceATMO = 0;
    	int indiceParticule = 0;
    	int indiceSoufre = 0;
    	int indiceOzone = 0;
    	int indiceAzote = 0;
    	int ParticulesFines = 0 + (int)(Math.random() * ((125 - 0) + 1));
    	int DioxydeSoufre = 0 + (int)(Math.random() * ((500 - 0) + 1));
    	int Ozone = 0 + (int)(Math.random() * ((240 - 0) + 1));
    	int DioxydeAzote = 0 + (int)(Math.random() * ((400 - 0) + 1));
    	
    	
    	if (0 <Ozone < 29) {
    		indiceOzone = 1
    	}else if (30 < Ozone < 54){
    		indiceOzone = 2
    	}else if (55 < Ozone < 79){
    		indiceOzone = 3
    	}else if (80 < Ozone < 104){
    		indiceOzone = 4
    	}else if (105 < Ozone < 129){
    		indiceOzone = 5
    	}else if (130 < Ozone < 149){
    		indiceOzone = 6
    	}else if (150 < Ozone < 179){
    		indiceOzone = 7
    	}else if (180 < Ozone < 209){
    		indiceOzone = 8
    	}else if (210 < Ozone < 239){
    		indiceOzone = 9
    	}else if (240 < Ozone){
    		indiceOzone = 10	
    	}
    	return indiceOzone;
   
    	if (0 < DioxydeSoufre < 39) {
    		indiceSoufre = 1
    	}else if (40 < DioxydeSoufre < 79){
    		indiceSoufre = 2
    	}else if (80 < DioxydeSoufre < 119){
    		indiceSoufre = 3
    	}else if (120 < DioxydeSoufre < 159){
    		indiceSoufre = 4
    	}else if (160 < DioxydeSoufre < 199){
    		indiceSoufre = 5
    	}else if (200 < DioxydeSoufre < 249){
    		indiceSoufre = 6
    	}else if (250 < DioxydeSoufre < 299){
    		indiceSoufre = 7
    	}else if (300 < DioxydeSoufre < 399){
    		indiceSoufre = 8
    	}else if (400 < DioxydeSoufre < 499){
    		indiceSoufre = 9
    	}else if (500 < DioxydeSoufre){
    		indiceSoufre = 10	
    	}	
    	return indiceSoufre;
    	
    	if (0 < ParticulesFines < 9) {
    		indiceParticule = 1
    	}else if (10 < ParticulesFines < 19){
    		indiceParticule = 2
    	}else if (20 < ParticulesFines < 29){
    		indiceParticule = 3
    	}else if (30 < ParticulesFines < 39){
    		indiceParticule = 4
    	}else if (40 < ParticulesFines < 49){
    		indiceParticule = 5
    	}else if (50 < ParticulesFines < 64){
    		indiceParticule = 6
    	}else if (65 < ParticulesFines < 79){
    		indiceParticule = 7
    	}else if (80 < ParticulesFines < 99){
    		indiceParticule = 8
    	}else if (100 < ParticulesFines < 124){
    		indiceParticule = 9
    	}else if (125 < ParticulesFines){
    		indiceParticule = 10	
    	}
    	return indiceParticule;
    	
    	if (0 < DioxydeAzote < 29) {
    		indiceAzote = 1
    	}else if (30 < DioxydeAzote < 54){
    		indiceAzote = 2
    	}else if (55 < DioxydeAzote < 84){
    		indiceAzote = 3
    	}else if (85 < DioxydeAzote < 109){
    		indiceAzote = 4
    	}else if (110 < DioxydeAzote < 134){
    		indiceAzote = 5
    	}else if (135 < DioxydeAzote < 164){
    		indiceAzote = 6
    	}else if (165 < DioxydeAzote < 199){
    		indiceAzote = 7
    	}else if (200 < DioxydeAzote < 274){
    		indiceAzote = 8
    	}else if (275 < DioxydeAzote < 399){
    		indiceAzote = 9
    	}else if (400 < DioxydeAzote){
    		indiceAzote = 10	
    	}
    	return indiceAzote;
    	
    	int minVal = Integer.MIN_VALUE;
    	
    	int array[] = {indiceAzote, indiceSoufre, indiceOzone, indiceParticule};
    	 
    	
    	 for(int i = 0; i < arraylenght; i++) {
    		 if(array[i] > minVal) {
    			 minVal = array[i];
    		 }
    	 }
    	 
    	 minVal = indiceATMO;
    }
    
    
    public static void main(String[] args){ //throws UnknownHostException, IOException, JSONException {
    	myCapteur air = new myCapteur(1);
       // air.startConnection("172.31.249.22", 1500);
       // client.afficheMenu();
    }
}