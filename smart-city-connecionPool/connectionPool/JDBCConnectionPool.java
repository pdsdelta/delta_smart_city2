package connectionPool;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


import user.Users;
 



public class JDBCConnectionPool{

	//Variables pour executer les requêtes bd
	Statement stm; 
	ResultSet rs; 
	PreparedStatement pstmt; 

	//instance pour pouvoir lire le fichier bdd.properties
	private ConnectionFileReader fileData=new ConnectionFileReader();	

	Connection connection = DriverManager.getConnection(fileData.getConnectionUrl(), fileData.getUser(),
			fileData.getPassword());
	; 
	//pour les connexions utilisées
	private CopyOnWriteArrayList<Connection> connectionsUse;
	//pour les connexions disponibles
	private CopyOnWriteArrayList<Connection> connectionsDispo;
	//le nombre de connexions max admis
	private static int MAX_CONNEXION=5;

	String connectionUrl = fileData.getConnectionUrl();
	String driver = fileData.getDriver();
	String user = fileData.getUser();
	String password = fileData.getPassword();
	Connection cnx = null;

	public JDBCConnectionPool()  throws SQLException, ClassNotFoundException{
		this.connectionsUse = new CopyOnWriteArrayList<Connection>();
		this.connectionsDispo = new CopyOnWriteArrayList<Connection>() ;

		Class.forName(driver);

	}
	//METHODE QUI RENVOIE UN OBJET COLLECTION PRIS DANS L'ATTRIBUT
	public Connection getConnection() throws SQLException {
		//Je retire une connexion disponible pour la mettre dans connection à utiliser
		//Je verifie si mon tableaud de connexion disponible n'est pas vide
		if (connectionsDispo.isEmpty()) {
			if (connectionsUse.size() < MAX_CONNEXION) {

				cnx = DriverManager.getConnection(connectionUrl, user,password);
				connectionsDispo.add(cnx);

			} else {
				throw new RuntimeException(
						"Maximum pool size reached, no available connections!");
			}
		} 

		Connection connection = connectionsDispo.remove(connectionsDispo.size() - 1);
		connectionsUse.add(connection);

		return connection;

	}
	//METHODE QUI REMPLIT L'ATTRIBUT 

	public void addConnection(Connection cnx) {
		System.out.println("Connexion ajouté avec succès");
		this.connectionsDispo.add(cnx);

	}

	//Methode pour libérer une connexion spécifique


	public void releaseConnection(Connection cnx) {
		cnx = connectionsUse.remove(0);
		connectionsDispo.add(cnx);
		System.out.println("Connexion retiré avec succès");
		System.out.println();

	}

	//Methode pour fermer toutes les connexions
	public void closeConnection() throws SQLException {
		connectionsUse.forEach(this::releaseConnection);
		for(Connection c: connectionsDispo) {
			c.close();
		}
		connectionsDispo.clear();
	}
	public int getSize() {
		// TODO Auto-generated method stub
		return connectionsDispo.size()+connectionsUse.size();
	}

	//To get a Specific user on the BD
	public Users getUtilisateur(String log) { 
		Users res = null; 
		String query = "SELECT * FROM Users WHERE login=?";
		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, log); 
			rs = pstmt.executeQuery();
			if (rs.next()) {
				res = new Users();
				res.setId(rs.getInt(1));
				res.setLogin(rs.getString(4));
				res.setPwd(rs.getString(5));
				res.setProfil(rs.getInt(6));
			}
		} 
		catch (SQLException ex) {
			Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
		}
		return res;
	}

	public void deleteUtilisateur(String ref) {

		String query = "delete Users WHERE login=?";
		try {
			pstmt=connection.prepareStatement
					(query);
			pstmt.setString(1, ref); 
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}


	//To Get a list of Users on the dataBase
	public List<Users> getAllUtilisateur() { 
		List<Users> res = new ArrayList<Users>(); 
		String query = "SELECT * FROM Users";
		try {
			stm = connection.createStatement();
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
		} 
		catch (SQLException ex) {
			Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
		}
		return res;
	}


	//To Add an user on the database
	public int addUtilisateur(Users util) { 
		int res = 0; 
		String query = "INSERT INTO Users (nom, prenom, login, pass, profil) "
				+ "VALUES (?, ?, ?, ?, ?)";
		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, util.getNom());
			pstmt.setString(2, util.getPrenom());
			pstmt.setString(3, util.getLogin());
			pstmt.setString(4, util.getPwd());
			pstmt.setInt(5, util.getProfil());
			res = pstmt.executeUpdate();            
		} 
		catch (SQLException ex) {
			Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
		}
		return res;
	}


}
//port 5167 5433









