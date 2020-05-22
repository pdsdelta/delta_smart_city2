package infocarbon;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonProcessingException;

import user.Users;

public class TestScénario {

	
	//Function wich return the date of yesterday
	public static Date yesterdayTo() {
		    final Calendar cal = Calendar.getInstance();
		    cal.add(Calendar.DATE, -1);
		    return cal.getTime();
	}
		
	//Scénario test of calculating carbon footprint for yesterday
	public static void main(String[] args) throws JsonProcessingException {
		Users u = new Users();
		CarbonOrder co = new CarbonOrder(1,u);
		DecimalFormat df = new DecimalFormat ( ) ; 
		df.setMaximumFractionDigits ( 2 ) ; 
		try {
			String res = co.generateJson();
			System.out.println("Calcul de l'empreinte carbonne globale pour hier");
			System.out.println("Le json qui va etre envoyé au serveur");
			System.out.println(res);
			CarbonInfo client = CarbonInfo.getInstance();
			client.startConnection("172.31.249.22", 2400);
			String resp = CarbonInfo.getInstance().sendMessage(res);
			InfoGlobalCarbon ic = (InfoGlobalCarbon) CarbonInfo.getInstance().responseToInfoCarbon(resp);
			if(ic==null) {
				System.out.println("Aucune donnée pour la date d'hier");
			}else {
				double resul = ic.calculateCarbon();
				System.out.println("L'empreinte carbonne des tramways est éstimée à :" + df.format(ic.getEct())+" Kg de CO2");
				System.out.println("L'empreinte carbonne des bus est éstimée à :" + df.format(ic.getEcb())+" Kg de CO2");
				System.out.println("L'empreinte carbonne des transports publics est éstimée à :" + df.format(ic.getEcpub())+" Kg de CO2");
				System.out.println("************************************************************************");
				System.out.println("L'empreinte carbonne des voitures est éstimée à :" + df.format(ic.getEcc())+" Kg de CO2");
				System.out.println("L'empreinte carbonne des motos est éstimée à :" + df.format(ic.getEcm())+" Kg de CO2");
				System.out.println("L'empreinte carbonne des transports privées est éstimée à :" + df.format(ic.getEcpriv()) + " Kg de CO2");
				System.out.println("************************************************************************");
				System.out.println("L'empreinte carbonne globale est de : "+ df.format(resul) + " Kg de CO2");
				CarbonOrder co3 = new CarbonOrder(5,u);
				co3.setCarbon(resul);
				DateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd");
				String daSql = dateFormatSQL.format(yesterdayTo());
				co3.setDate(daSql);
				String resPut = co3.generateJson();
				//PUTTING THE CARBON VALUE IN DATABASE
				String response = CarbonInfo.getInstance().sendMessage(resPut);
				
			}
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}
	
	
}
