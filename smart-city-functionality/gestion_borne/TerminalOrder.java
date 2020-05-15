package gestion_borne;



import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import user.Users;
public class TerminalOrder {

	private int numOrder;
	private Users user;
	
	public TerminalOrder(int numOrder, Users user) {
		
		this.numOrder = numOrder;
		this.user = user;
	}
	
	public String generateJson() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String res = "";
		int profil ;  	
		switch(this.numOrder){
			case 1 :
				Scanner read = new Scanner(System.in);
		    	Scanner readInt = new Scanner(System.in);
				System.out.print("veuillez entrez le nom : ");
				Long longitude = read.nextLong();
				System.out.print("veuillez entrez le prenom : ");
				Long latitude = read.nextLong();
				System.out.print("veuillez entrez le login : ");
				String login = read.nextLine();
				System.out.print("veuillez entrez le mot de passe : ");
				String pass = read.nextLine();
				System.out.print("veuillez entrez le profil : ");
				profil = readInt.nextInt();
				int idCity=1;
				user.setProfil(profil);
				res  ="{request:{ operation_type: CREATE_TERMINAL, target: Terminal , longitude: "+longitude + ", latitude: "+ latitude + ", isActive : "+ login +", status : "+ pass +", numero : "+ profil +", city : "+idCity+"}} " ;
				break;
			case 2 :
				res ="{request:{ operation_type: ALL_TERMINAL, target: Terminal }}" ;
				break;
			case 3 :
				System.out.print("Quel est le login de l'utilisateur que vous souhaitez consulter ?\n");
				Scanner userRead = new Scanner(System.in);
				String newUserRead = userRead.nextLine(); 
				res ="{request:{ operation_type: SELECT_TERMINAL, target: Terminal , login: ";
				res = res + newUserRead + "}}" ;
				break;
			case 4 :
				System.out.print("Quel est le login de l'utilisateur que vous souhaitez modifier ?\n");
				Scanner user = new Scanner(System.in);
				String userUpdate = user.nextLine(); 
				System.out.println("Que voulez vous modifier ?\n"); 
				System.out.println("[ 1 ] Le nom\n" +
						"[ 2 ] Le prénom\n"+
						"[ 3 ] Le mot de passe\n");
				boolean b = true ;
				String choixFinal = "";
				while(b) {
					Scanner choix = new Scanner(System.in);
					int choixUpdate = choix.nextInt(); 
					
					if(choixUpdate ==1) {
						choixFinal ="nom";
						b=false;
					}else if (choixUpdate ==2) {
						choixFinal = "prenom";
						b=false;
					}else if (choixUpdate ==3) {
						choixFinal ="pass";
						b=false;
					}else {
						System.out.println("Désolé, on ne peut traiter votre demande, veuillez choisir entre 1 et 3");
						
					}
				}
				System.out.println("Quel est le nouveau " + choixFinal +" ? \n");
				Scanner newThing = new Scanner(System.in);
				String newthingUpdate = newThing.nextLine(); 
				res  ="{request:{ operation_type: UPDATE_TERMINAL, target: Terminal , to_modify: "+choixFinal + ", modification: "+ "\"" +newthingUpdate + "\"" + ", login : "+ userUpdate +"}} " ;

				break;
			case 5:
				System.out.print("Quel est le login de l'utilisateur que vous souhaitez supprimer ?\n");
				Scanner userDelete = new Scanner(System.in);
				String newUserDelete = userDelete.nextLine(); 
				res ="{request:{ operation_type: DELETE_TERMINAL, target: Terminal , login: ";
				res = res + newUserDelete + "}}" ;
				break;
			default:
				res ="{request:{ operation_type: UNKNOWN } }";
			}
		return res;	
	}
	
	public static void main(final String[] argv) throws JSONException {
	
	 }
}