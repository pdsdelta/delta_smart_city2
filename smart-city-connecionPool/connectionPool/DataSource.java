package connectionPool;



import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import user.Users;
public class DataSource {
	private static JDBCConnectionPool connectionPool ;

	public DataSource() throws SQLException, ClassNotFoundException{
		this.connectionPool = new JDBCConnectionPool();
	}


	public static Connection getConnection() throws SQLException {
		return connectionPool.getConnection();
	}

	public static void addConnection(Connection cnx) {
		connectionPool.addConnection(cnx);
	}


	public static void closeConnection() throws SQLException {
		connectionPool.closeConnection();

	}
	public static void releaseConnection(Connection cnx) {
		connectionPool.releaseConnection(cnx);

	}
	public static int getSize() {
		return connectionPool.getSize();
	}

	public Users getUtilisateur(String log) {
		return connectionPool.getUtilisateur(log);
	}

	public List<Users> getAllUtilisateur(){
		return connectionPool.getAllUtilisateur();
	}
	public void deleteUtilisateur(String ref) {
		connectionPool.deleteUtilisateur(ref);
		getAllUtilisateur();
		}
	public int addUtilisateur(Users util) { 
		getAllUtilisateur();
		return connectionPool.addUtilisateur(util);
	}

}

