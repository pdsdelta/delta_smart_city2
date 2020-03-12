package connectionPool;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;


public class JDBCConnectionPool{
	private ConnectionFileReader fileData; 
	private ArrayList<Connection> connections;

	
	public JDBCConnectionPool()  throws SQLException, ClassNotFoundException{
		
		this.connections = new ArrayList<Connection>() ;
		this.fileData = new ConnectionFileReader();
		String connectionUrl = fileData.getConnectionUrl();
		String driver = fileData.getDriver();
		String user = fileData.getUser();
		String password = fileData.getPassword();
		Connection cnx = null;
		for(int i =0;i<connections.size()-1 ; i++) {
			Class.forName(driver);
			cnx = DriverManager.getConnection(connectionUrl, user,password );
			connections.add(cnx);
			
		}
		
		
	}
	
	public Connection getConnection() {
		Connection cnx = connections.get(0);
		connections.remove(0);
		return cnx;
		
	}
	
	public void addConnection(Connection cnx) {
		this.connections.add(cnx);
		
	}
	
	public void closeConnection() throws SQLException {
		for(int i =0;i<connections.size()-1 ; i++) {
			connections.get(i).close();
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
}
