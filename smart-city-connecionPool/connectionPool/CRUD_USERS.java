package connectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;

import user.Users;


/*
 * Cette classe CRUD USERS est juste la classe de methode de CRUD d'un utilisateur
 *  l'interface console qui demande au client ce qu'il veut faire sur la BD
 */
public class CRUD_USERS {

	Connection connect;
	// Variables pour executer les requï¿½tes bd
	Statement stm;
	ResultSet rs;
	PreparedStatement pstmt;

	DataSource connection;


	public CRUD_USERS() throws ClassNotFoundException, SQLException {
		this.connection= new DataSource();
		this.connect = connection.getConnection();
	}
	
	Scanner read = new Scanner(System.in);
	Scanner readInt = new Scanner(System.in);
	String nom = null;
	String prenom = null;
	String login = null;
	int profil ;
	
	//CREATE

	Users util = new Users();
	public Users ActionInsertUser() {
		System.out.print("veuillez entrez le nom : ");
		nom = read.nextLine();
		System.out.print("veuillez entrez le prenom : ");
		prenom = read.nextLine();
		System.out.print("veuillez entrez le login : ");
		login = read.nextLine();
		System.out.print("veuillez entrez le mot de passe : ");
		String pass = read.nextLine();
		System.out.print("veuillez entrez le profil : ");
		util.setNom(nom);
		util.setPrenom(prenom);
		util.setLogin(login);
		util.setPwd(pass);
		profil = readInt.nextInt();
		util.setProfil(profil);


		return util;
	}
	
	public int addUtilisateur(Users util) {
		int res = 0;
		String query = "INSERT INTO Users (nom, prenom, login, pass, profil) " + "VALUES (?, ?, ?, ?, ?)";
		try {
			pstmt = connect.prepareStatement(query);
			pstmt.setString(1, util.getNom());
			pstmt.setString(2, util.getPrenom());
			pstmt.setString(3, util.getLogin());
			pstmt.setString(4, util.getPwd());
			pstmt.setInt(5, util.getProfil());
			res = pstmt.executeUpdate();
			System.out.println("opération réalisée avec succès\n");
		} catch (SQLException ex) {
			Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
		}
		return res;
	}
	
	
	//READ
	
	// To Get a list of Users on the dataBase
		public List<Users> getAllUtilisateur() {
			List<Users> res = new ArrayList<Users>();
			String query = "SELECT * FROM Users";
			try {
				stm = connect.createStatement();
				rs = stm.executeQuery(query);
				while (rs.next()) {
					Users util = new Users();
					util.setId(rs.getInt(1));
					util.setNom(rs.getString(2));

					util.setPrenom(rs.getString(3));
					util.setLogin(rs.getString(4));
					util.setPwd(rs.getString(5));
					util.setProfil(rs.getInt(6));
					res.add(util);
					System.out.println("opération réalisée avec succès\n");
				}
			} catch (SQLException ex) {
				Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
			}
			return res;
		}

		
		//SPECIAL READ
		// To get a Specific user on the BD
		public List<Users> getUtilisateur() {
			List<Users> res2 = new ArrayList<Users>();
			System.out.print("Quel est le login de l'utilisateur que vous souhaitez consulter ?\n");
			Scanner userRead = new Scanner(System.in);
			String newUserRead = userRead.nextLine(); 
			String query = "SELECT * FROM Users WHERE login=?";
			try {
				pstmt = connect.prepareStatement(query);
				pstmt.setString(1, newUserRead);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
				Users util = new Users();
				util.setId(rs.getInt(1));
				util.setNom(rs.getString(2));
				util.setPrenom(rs.getString(3));
				util.setLogin(rs.getString(4));
				util.setPwd(rs.getString(5));
				util.setProfil(rs.getInt(6));
				res2.add(util);
				System.out.println("opération réalisée avec succès\n");
				}
			} catch (SQLException ex) {
				System.out.println("Erreur, veuillez réessayer");
			}
			return res2;
		}
		
	//UPDATE 
	
	//Methode pour mettre Ã  jour un utilisateur
		public int updateUtilisateur() { 
			System.out.print("Quel est le login de l'utilisateur que vous souhaitez modifier ?\n");
			Scanner user = new Scanner(System.in);
			String userUpdate = user.nextLine(); 
			System.out.println("Que voulez vous modifier ?\n"); 
			System.out.println("[ 1 ] Le nom\n" +
					"[ 2 ] Le prÃ©nom\n"+
					"[ 3 ] Le mot de passe\n");
			Scanner choix = new Scanner(System.in);
			int choixUpdate = choix.nextInt(); 
			int res = 0; 
			String query = "";
			switch(choixUpdate){

			case 1: 
				System.out.println("Quel est le nouveau nom ? \n");
				Scanner newName = new Scanner(System.in);
				String newNameUpdate = newName.nextLine(); 
				query = "update Users set nom =?  WHERE login=? "; 
				try { 
					pstmt = connect.prepareStatement(query); 
					pstmt.setString(1, newNameUpdate);
					pstmt.setString(2, userUpdate);
					res = pstmt.executeUpdate(); 
				} catch (SQLException ex) {
					Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
				}
				
				break;
				
				
			case 2: 
				System.out.println("Quel est le nouveau prénom ?\n");
				Scanner newFname = new Scanner(System.in);
				String newFnameUpdate = newFname.nextLine(); 
				query = "update Users set prenom =?  WHERE login=? "; 
				try { 
					pstmt = connect.prepareStatement(query); 
					pstmt.setString(1, newFnameUpdate);
					pstmt.setString(2, userUpdate);
					res = pstmt.executeUpdate(); 
				} catch (SQLException ex) {
					Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
				}
				
			
				break;
			case 3: 
				System.out.println("Quel est le nouveau mot de passe ?\t\n");
				Scanner newPwd = new Scanner(System.in);
				String newPwdUpdate = newPwd.nextLine(); 
				query = "update Users set pass =?  WHERE login=? "; 
				try { 
					pstmt = connect.prepareStatement(query); 
					pstmt.setString(1, newPwdUpdate);
					pstmt.setString(2, userUpdate);
					res = pstmt.executeUpdate(); 
				} catch (SQLException ex) {
					Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
				}
				
			}
			
			if (res == 0) {
				System.out.println("La modification a Ã©chouÃ©\n");
			}else if (res == 1) {
				System.out.println("opération réalisée avec succès\n");
			}
			return res; 	
		}
		
		//DELETE
		//Methode pour supprimer un utilisateur
		public void deleteUtilisateur() {
			System.out.print("Quel est le login de l'utilisateur que vous souhaitez supprimer ?\n");
			Scanner userDelete = new Scanner(System.in);
			String newUserDelete = userDelete.nextLine(); 
			String query = "";
			
			query = "delete from Users WHERE login=?"; 
			
			try {
				pstmt=connect.prepareStatement (query);
				pstmt.setString(1, newUserDelete);
				pstmt.executeUpdate(); 
				pstmt.close();
				System.out.println("opération réalisée avec succès\n");
			} catch (SQLException e) { 
				System.out.println("Erreur, veuillez réessayer ! "); 
			}
		}

}