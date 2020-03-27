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
	// Variables pour executer les requ�tes bd
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


	Users util = new Users();
	public Users ActionInsertUser() {
		System.out.println("veuillez entrez le nom: ");
		nom = read.nextLine();
		System.out.println("veuillez entrez le prenom: ");
		prenom = read.nextLine();
		System.out.println("veuillez entrez le login: ");
		login = read.nextLine();
		System.out.println("veuillez entrez le mot de passe: ");
		String pass = read.nextLine();
		System.out.println("veuillez entrez le profil: ");
		util.setNom(nom);
		util.setPrenom(prenom);
		util.setLogin(login);
		util.setPwd(pass);
		profil = readInt.nextInt();
		util.setProfil(profil);


		return util;
	}
	public void ActionUpdateUser() {

	}
	public void ActionDeleteUser() {

	}
	public void ActionGetUser() {

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
		} catch (SQLException ex) {
			Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
		}
		return res;
	}

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
			}
		} catch (SQLException ex) {
			Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
		}
		return res;
	}

	// To get a Specific user on the BD
	public Users getUtilisateur(String log) {
		Users res = null;
		String query = "SELECT * FROM Users WHERE login=?";
		try {
			pstmt = connect.prepareStatement(query);
			pstmt.setString(1, log);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				res = new Users();
				res.setId(rs.getInt(1));
				res.setLogin(rs.getString(4));
				res.setPwd(rs.getString(5));
				res.setProfil(rs.getInt(6));
			}
		} catch (SQLException ex) {
			Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
		}
		return res;
	}


	//Methode pour supprimer un utilisateur
	public void deleteUtilisateur(String ref) {

		String query = "delete Users WHERE login=?"; 
		try {
			pstmt=connect.prepareStatement (query);
			pstmt.setString(1, ref);
			pstmt.executeUpdate(); 
			pstmt.close(); 
		} catch (SQLException e) { // TODO Auto-generated 
		}
	}

	//Methode pour mettre � jour un utilisateur
	public int updateUtilisateur(Users util) { 
		int res = 0; 
		String query = "update Users set nom =? ,prenom=? where id=? "; 
		try { 
			pstmt = connect.prepareStatement(query); 
			pstmt.setInt(1, util.getId());
			pstmt.setString(2, util.getNom());
			pstmt.setString(3, util.getPrenom());
			res = pstmt.executeUpdate(); 
		} catch (SQLException ex) {
			Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
		}
		return res; }



}