package connectionPool;



import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import user.Users;

public class TestConnexion {
	static List<Connection> i = new ArrayList<>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {

			CRUD_USERS crud = new CRUD_USERS();
			DataSource ene = new DataSource();


			System.out.println("**********");
			System.out.println("*  MENU  *");
			System.out.println("********** \n");
			System.out.println("[ 1 ] Creer une connexion\n" +
					"[ 2 ] Fermer toutes les connexions\n"+
					"[ 3 ] Fermer une connexion\n"+
					"[ 4 ] Ajouter un utilisateur\n"+
					"[ 5 ] Afficher les utilisateurs\n"+
					"[ 6 ] Afficher un utilisateur spécifique\n"+
					"[ 7 ] Modifier les donn�es d'un utilisateur\n"+
					"[ 8 ] Supprimer un utilisateur\n\n");
			Scanner sc = new Scanner(System.in);

			while(true) {
				String a = sc.nextLine();
				switch(a){

				case "1": 
					Connection c = ene.getConnection();
					i.add(c);
					System.out.println("Connexion disponible: "+ene.getSize());
					break;
				case "2": ene.closeConnection(); 
				System.out.println("Connexion disponible: "+ene.getSize());
				break;
				case "3": 
					ene.releaseConnection( i.get(0));
					i.remove(0);
					break;
				case "4":
					crud.addUtilisateur(crud.ActionInsertUser());
					break;
					
				case "5":
					List<Users> lt = crud.getAllUtilisateur();
					for(Users tab:lt) {
						System.out.println(tab);
					} 
					break;
					
				case "6":
					List<Users> list = crud.getUtilisateur();
					for(Users tab:list) {
						System.out.println(tab);
					}
					break;
					
				case "7":
					crud.updateUtilisateur();
					break; 
				
				case "8": 
					crud.deleteUtilisateur();
					break;
				
					
					
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
