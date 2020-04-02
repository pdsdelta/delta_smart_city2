package usefull;

import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import user.Users;




public class Order {

	private int numOrder;
	private Users user;
	
	public Order(int numOrder, Users user) {
		
		this.numOrder = numOrder;
		this.user = user;
	}
	
	public String generateJson() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String res = "";
		int profil ;
    	Scanner read = new Scanner(System.in);
    	Scanner readInt = new Scanner(System.in);
		switch(this.numOrder){
			case 1 :
				System.out.print("veuillez entrez le nom : ");
				String nom = read.nextLine();
				System.out.print("veuillez entrez le prenom : ");
				String prenom = read.nextLine();
				System.out.print("veuillez entrez le login : ");
				String login = read.nextLine();
				System.out.print("veuillez entrez le mot de passe : ");
				String pass = read.nextLine();
				System.out.print("veuillez entrez le profil : ");
				profil = readInt.nextInt();
				user.setProfil(profil);
				res  ="{request:{ operation_type: CREATE, target: Users , nom: "+nom + ", prenom: "+ prenom + ", login : "+ login +", pass : "+ pass +", profil : "+ profil +"}} " ;
				break;
			case 2 :
				res ="{request:{ operation_type: SELECT_ALL, target: Users }}" ;
				break;
			case 3 :
				System.out.print("Quel est le login de l'utilisateur que vous souhaitez consulter ?\n");
				Scanner userRead = new Scanner(System.in);
				String newUserRead = userRead.nextLine(); 
				res ="{request:{ operation_type: SELECT_ONE, target: Users , login: ";
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
				res  ="{request:{ operation_type: UPDATE, target: Users , to_modify: "+choixFinal + ", modification: "+ "\"" +newthingUpdate + "\"" + ", login : "+ userUpdate +"}} " ;

				break;
			case 5:
				System.out.print("Quel est le login de l'utilisateur que vous souhaitez supprimer ?\n");
				Scanner userDelete = new Scanner(System.in);
				String newUserDelete = userDelete.nextLine(); 
				res ="{request:{ operation_type: DELETE_ONE, target: Users , login: ";
				res = res + newUserDelete + "}}" ;
				break;
			}
		return res;	
	}
	
	public static void main(final String[] argv) throws JSONException {
	
	 }
}