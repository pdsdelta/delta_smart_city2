package infocarbon;

import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;  
import user.Users;

/*
 * Class wich
 */
public class CarbonOrder {
	
	private int numOrder;
	private Users user;
	private String dateToSend;
	private double carbon ;
	
	public CarbonOrder(int numOrder, Users user) {
		
		this.numOrder = numOrder;
		this.user = user;
	}
	
	
	public double getCarbon() {
		return carbon;
	}


	public void setCarbon(double carbon) {
		this.carbon = carbon;
	}


	public void setDate(String dateToSend) {
		this.dateToSend = dateToSend;
	}
	
	//METHOD WICH ALLOWS TO GENERATE THE JSON TO SEND TO THE SERVER
	public String generateJson() throws JsonProcessingException {
		

		String res = "";
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd");
		Date dateToday = new Date(); 
		String dateRequest;
		double c = this.getCarbon();
		switch(this.numOrder){
		case 1 :
			res  ="{request:{ operation_type: GET_GLOBAL_CARBON, date: "+ dateFormatSQL.format(yesterday()) +" }} " ;
			break;
		case 2 :
			res ="{request:{ operation_type: GET_GLOBAL_CARBON, date: "+ dateToSend +" }}" ;
			break;
		case 3 :
			res ="{request:{ operation_type: GET_PUBLIC_CARBON, date: "+ dateToSend +" }}" ;
			break;
		case 4 :
			res ="{request:{ operation_type: GET_PRIVATE_CARBON, date: "+ dateToSend +" }}" ;
			break;
		case 5 :
			res ="{request:{ operation_type: PUT_CARBON, date: "+ dateToSend +" , carbon:"+ c +" }}" ;
			break;
		case 6:
			Scanner read = new Scanner(System.in);
			System.out.print("Estimation de l'empreinte carbonne \n");
			boolean bool = true;
			while(bool) {
				System.out.print("Veuillez entrer le nombre de voiture \n");
				int nbCars = read.nextInt();
				if(nbCars<0) {
					System.out.print("Veuillez entrer un nombre positif \n");
				}else {
					System.out.print("Veuillez entrer la distance moyenne en Km parcouru par les voitures, Ex : 31  \n");
					float kmAvgCars = read.nextFloat();
					if(kmAvgCars<0) {
						System.out.print("Veuillez entrer un nombre positif \n");
					}else {
						System.out.print("Veuillez entrer le nombre de passagers moyen par voiture , Ex : 2  \n");
					}
				}
				
				
			}
			
			break;
		default:
			res ="{request:{ operation_type: UNKNOWN } }";
		}
		return res;
		
		
	}
	
	//Function which allow to compare two dates 
	
	public static boolean compareDate(String date) {
		  SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		  Date dateToday = new Date();  
	      Date d1;
	      boolean res = false ;
	      try {
	    	  d1 = formatter.parse(date);
		      if(dateToday.compareTo(d1) > 0) {
		         
		         res = true;
		      } else if(dateToday.compareTo(d1) < 0) {
		    	  res =  false;
		      } else if(dateToday.compareTo(d1) == 0) {
		         res = false;
		      }
	      } catch (ParseException e) {
			e.printStackTrace();
	      }
		return res;
	      
	       
	   }
	
	//Function which validate the date format
	public static boolean validateJavaDate(String date)
	   {
		/* Check if date is 'null' */
		if (date.trim().equals(""))
		{
			System.out.println("L'entrée est vide\n");
		    return false;
		}
		/* Date is not 'null' */
		else
		{
		    /*
		     * Set preferred date format,
		     * dd/MM/yyyy */
		    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		    formatter.setLenient(false);
		    try
		    {
		        Date javaDate = formatter.parse(date); 
		        
		    }
		    /* Date format is invalid */
		    catch (ParseException e)
		    {
		        
		        return false;
		    }
		    /* Return true if date format is valid */
		    return true;
		}
	   }
	
	   public static String returnDate() {
		    String res= "";
		    Scanner read = new Scanner(System.in);
			System.out.print("Veuillez entrer la date souhaitée avec le format JJ/MM/AAAA \n");
			boolean b = false;
			boolean c = false;
			boolean bool = true;
			while(bool) {
				String dateRequest = read.nextLine();
				c = validateJavaDate(dateRequest);
				if (c) {
					System.out.println(dateRequest+" est un format de date valide\n");
					b = compareDate(dateRequest);
					if (b) {
						System.out.println("Date Valide\n");
						System.out.println("Chargement des résultat...\n");
						res = dateRequest ;
						bool = false;
					}else {
						System.out.println("Veuillez entrer une date antérieure à celle d'aujourd'hui\n");
					}
				}else {
					System.out.println("La date entrée n'est pas un format de date valide\n");
				}
				
				
			}
			return res;
	
	   }
	   
	   //Function Date of Yestreday
	   public Date yesterday() {
		    final Calendar cal = Calendar.getInstance();
		    cal.add(Calendar.DATE, -1);
		    return cal.getTime();
		}
	   
	   


	
	
	public static void main(final String[] argv) throws JSONException, JsonProcessingException {
		Users u = new Users();
		u.setId(1);
		CarbonOrder co = new CarbonOrder(1,u);
		String res = co.generateJson();
		System.out.println(res);

		
		
	 }

}
