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

			DataSource ene = new DataSource();

			System.out.println("**********");
			System.out.println("*  MENU  *");
			System.out.println("********** \n");
			System.out.println("[ 1 ] Creer une connexion\n " +
					"[ 2 ] Fermer toutes les connexions\n "
					+ "[ 3 ] Fermer une connexion\n " + 
					"[ 4 ] Ajouter un utilisateur\n " +
					"[ 5 ] Afficher les utilisateurs");
			Scanner sc = new Scanner(System.in);

			while (true) {
				String a = sc.nextLine();
				Scanner read = new Scanner(System.in);
				Scanner hey = new Scanner(System.in);
				String nom = null;
				String prenom = null;
				String login = null;
				Users util = new Users();
				switch (a) {

				case "1":
					Connection c = ene.getConnection();
					i.add(c);
					System.out.println("Connexion disponible: " + ene.getSize());
					break;
				case "2":
					ene.closeConnection();
					System.out.println("Connexion disponible: " + ene.getSize());
					break;
				case "3":
					ene.releaseConnection(i.get(0));
					i.remove(0);
					break;
				case "4":

					System.out.println("veuillez entrez le nom: ");
					nom = read.nextLine();
					System.out.println("veuillez entrez le prenom: ");
					prenom = read.nextLine();
					System.out.println("veuillez entrez le login: ");
					login = read.nextLine();
					System.out.println("veuillez entrez le mot de passe: ");
					String pass = read.nextLine();
					System.out.println("veuillez entrez le profil: ");
					int profil = hey.nextInt();
					util.setNom(nom);
					util.setPrenom(prenom);
					util.setLogin(login);
					util.setPwd(pass);
					util.setProfil(profil);
					ene.addUtilisateur(util);
					break;

				case "5":
					List<Users> lt = ene.getAllUtilisateur();
					for (Users tab : lt) {
						System.out.println(tab);
					}
					break;

				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
