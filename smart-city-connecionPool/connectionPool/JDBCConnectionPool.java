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
	Connection connection;
	
	//Connection connection = DriverManager.getConnection(fileData.getConnectionUrl(), fileData.getUser(),
	//		fileData.getPassword());;
			
	//pour les connexions utilisées
	private CopyOnWriteArrayList<Connection> connectionsUse;
	//pour les connexions disponibles
	private CopyOnWriteArrayList<Connection> connectionsDispo;
	//le nombre de connexions max admis
	private static int MAX_CONNEXION=50;

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
}
//port 5167 5433









