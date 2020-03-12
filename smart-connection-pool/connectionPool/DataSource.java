package connectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
	private static JDBCConnectionPool connectionPool ;
	
	public DataSource() throws SQLException, ClassNotFoundException{
		this.connectionPool = new JDBCConnectionPool();
	}
	
	public static Connection getConnection() {
		return connectionPool.getConnection();
	}
	
	public static void addConnection(Connection cnx) {
		connectionPool.addConnection(cnx);
	}
	
	public static void closeConnection() throws SQLException {
		connectionPool.closeConnection();
		
	}
}
