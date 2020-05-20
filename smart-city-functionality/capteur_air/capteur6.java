package capteur_air;

import java.awt.Color;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import CapteurAir.CapteurAir;
import district.District;
import tram_line.tramExceptions.noDataInBase;

public class capteur6 extends JFrame{
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	public void startConnection(String ip, int port, String json) throws UnknownHostException, IOException, JSONException {
		String toSend = json;
		clientSocket = new Socket(ip, port);
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		//while(true) {
			out.println(toSend);
			String response = in.readLine();
			System.out.println("***** RÃ©sultat ******\n");
//			System.out.println(this.showresultseuil(response));
//			System.out.println(this.showresultindice(response));
		//}
		
	}

	public capteur6() throws UnknownHostException, IOException, JSONException{
		this.setTitle("sélectionner quartier");
		this.setSize(400, 400);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		panel1.setBackground(Color.white);
		JButton bouton1;
//		if (getalerte(1) == 1) {
//			bouton1 = new JButton("Alerte !!! L'indice relevé est supérieur au seuil");
//    	    getContentPane().add(bouton1, "North");
//    	    getContentPane().setLayout(null);
//    	    bouton1.setBackground(Color.red);
//		} else {
//			bouton1 = new JButton("Tout va bien, aucun problème détecté");
//    	    getContentPane().add(bouton1, "North");
//    	    getContentPane().setLayout(null);
//    	    bouton1.setBackground(Color.green);
//		}
		JLabel label1 = new JLabel();
		label1.setText("<html><body><p><p><p><p><p><p><p><p><p><p><p><p>" 
                 + "Nous avons un indice ATMO de 7 pour le quartier sélectionné"
                 +"</body></html>" );
		this.add(panel1);
//		panel1.add(bouton1);
		panel1.add(label1);
	}
}
//	public int getalerte(int alerte1) throws UnknownHostException, IOException, JSONException {
//    	//A faire
//    	//A modifier
//		getdistrictseuil();
//		getindice();
//    	int a = seuildistrict.getSeuilQuartierATMO();
//    	System.out.println(a);
//    	int b = indiceATMO.getIndice();
//    	System.out.println(b);
//	
//    	if(a < b) {
//    		alerte1 = 1;
//    	}else {
//    		alerte1 = 0;
//    	}
//    	System.out.println(alerte1);
//    	return alerte1;
//    }
//	
//	public void getdistrictseuil() throws UnknownHostException, IOException, JSONException{
//    	String json;
//    	int id = 1;
//    	json  ="{request:{ operation_type: INFOSEUIL, target: district , idcity: "+ id + "}}";
//    	this.startConnection("172.31.249.22", 2400, json);
//    }
//
//	District seuildistrict = new District();
//	public String showresultseuil (String jsonResponse) throws JSONException, noDataInBase {
//		String res = "Empty";
//		JSONObject obj =new JSONObject(jsonResponse);
//		List <District> u = new ArrayList<District>();
//
//		JSONArray arr = obj.getJSONArray("Data");
//
//		if(arr.length() == 0) {
//			System.out.println("Il n'y a pas d'informations en base dans le capteur d'air");
//			dispose();
//			throw new noDataInBase();
//		}
//
//		seuildistrict.setSeuilQuartierATMO(arr.getJSONObject(0).getInt("seuildistrict"));
//
//		System.out.println(seuildistrict.getSeuilQuartierATMO());
//
//		u.add(seuildistrict);
//		res = u.toString();   
//
//		return res;
//	}
//	
//    public void getindice() throws UnknownHostException, IOException, JSONException{
//    	String json;
//    	int idcapteur = 1;
//    	json  ="{request:{ operation_type: INFOINDATMO, target: capteurair, idcapteur: "+ idcapteur +  "}}";
//    	this.startConnection("172.31.249.22", 2400, json);
//    }
//
//	CapteurAir indiceATMO = new CapteurAir();
//	public String showresultindice (String jsonResponse) throws JSONException, noDataInBase {
//		String res = "Empty";
//		JSONObject obj =new JSONObject(jsonResponse);
//		List<CapteurAir> u = new ArrayList<CapteurAir>();
//
//		JSONArray arr = obj.getJSONArray("Data");
//
//		if(arr.length() == 0) {
//			System.out.println("Il n'y a pas d'informations en base dans le capteur d'air");
//			dispose();
//			throw new noDataInBase();
//		}
//
//		indiceATMO.setIndice(arr.getJSONObject(0).getInt("IndiceATMO"));
//
//		System.out.println(indiceATMO.getIndice());
//
//		u.add(indiceATMO);
//		res = u.toString();   
//
//		return res;
//
//	}	
//}
