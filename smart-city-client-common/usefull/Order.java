package usefull;

import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
		switch(this.numOrder){
			case 4 :
				res ="{\"request\":{ \"operation_type\": \"CREATE\", \"data\": " ;
				res = res + mapper.writeValueAsString(this.user);
				res = res + "}}" ;
				break;
			case 5 :
				res ="{\"request\":{ \"operation_type\": \"SELECT_ALL\", \"target\": Users }}" ;
				break;
			case 6 :
				System.out.print("Quel est le login de l'utilisateur que vous souhaitez consulter ?\n");
				Scanner userRead = new Scanner(System.in);
				String newUserRead = userRead.nextLine(); 
				res ="{\"request\":{ \"operation_type\": \"SELECT_ONE\", \"target\": Users , \"login\": ";
				res = res + newUserRead + "}}" ;
				break;
			case 7 :
				System.out.print("Quel est le login de l'utilisateur que vous souhaitez modifier ?\n");
				Scanner user = new Scanner(System.in);
				String userUpdate = user.nextLine(); 
				System.out.println("Que voulez vous modifier ?\n"); 
				System.out.println("[ 1 ] Le nom\n" +
						"[ 2 ] Le prénom\n"+
						"[ 3 ] Le mot de passe\n");
				Scanner choix = new Scanner(System.in);
				int choixUpdate = choix.nextInt(); 
				String choixFinal = "";
				if(choixUpdate ==1) {
					choixFinal ="nom";
				}else if (choixUpdate ==2) {
					choixFinal = "prenom";
				}else if (choixUpdate ==3) {
					choixFinal ="pass";
				}
				System.out.println("Quel est le nouveau " + choixFinal +"\n");
				Scanner newThing = new Scanner(System.in);
				String newthingUpdate = newThing.nextLine(); 
				res  ="{\"request\":{ \"operation_type\": \"UPDATE\", \"target\": Users , \"to_modify\": "+choixFinal + ", \"modification\": "+ newthingUpdate + " }} " ;

				break;
			case 8:
				System.out.print("Quel est le login de l'utilisateur que vous souhaitez supprimer ?\n");
				Scanner userDelete = new Scanner(System.in);
				String newUserDelete = userDelete.nextLine(); 
				res ="{\"request\":{ \"operation_type\": \"DELETE_ONE\", \"target\": Users , \"login\": ";
				res = res + newUserDelete + "}}" ;
				break;
			
			}
		
		return res;
		
		
	}


	public static void main(String[] args) throws JsonProcessingException {
		Users u = new Users();
		u.setId(1);
		u.setNom("REDJ");
		u.setPrenom("Yac");
		u.setLogin("yredj");
		u.setPwd("toitoi");
		u.setProfil(1);
		Order o = new Order(8,u);
		System.out.println(o.generateJson());
		
		
	}

}