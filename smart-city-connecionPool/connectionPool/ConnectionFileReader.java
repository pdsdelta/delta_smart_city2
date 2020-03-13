package connectionPool;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionFileReader {
	private String driver;
	private String connectionUrl;
	private String user;
	private String password;
	final Properties pr;
	public InputStream input = null ;
	
	

	public ConnectionFileReader() {
		
		pr = new Properties();
		InputStream input = null;
		
		try {
			
			
			input = new FileInputStream("E:/Cours/eclipse/delta-city/Ressources/bdd.properties");

			// load a properties file
			pr.load(input);

			// get the property value and print it out
			this.driver = pr.getProperty("bdd.driver");
			this.connectionUrl =pr.getProperty("bdd.url");
			this.user = pr.getProperty("bdd.username");
			this.password = pr.getProperty("bdd.password");
			

		} catch (final IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}

		 
		 
	}
	}
		
	public String getDriver() {
		return this.driver;
	}

	public String getConnectionUrl() {
		return connectionUrl;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	
	
}