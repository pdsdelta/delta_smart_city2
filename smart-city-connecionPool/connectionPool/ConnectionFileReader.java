package connectionPool;

import java.io.File;
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
			
			//Pour le jar
			//input = new FileInputStream("delta_smart_city2"+ File.separator +"Ressources" + File.separator + "bdd.properties");
			
			//Pour �clipse
			//input = new FileInputStream("Ressources" + File.separator + "bdd.properties");
			
			// load a properties file
			//pr.load(input);

			// get the property value and print it out
			
			//this.driver = pr.getProperty("bdd.driver");
			this.driver = pr.getProperty("org.postgresql.Driver");
			//this.connectionUrl =pr.getProperty("bdd.url");
			this.connectionUrl = "jdbc:postgresql://172.31.249.20:5432/delta-city-bd";
			//this.user = pr.getProperty("bdd.username");
			this.user = pr.getProperty("delta-city");
			//this.password = pr.getProperty("bdd.password");
			this.password = pr.getProperty("toto");
			

		}/*catch (final IOException ex) {
			ex.printStackTrace();
		}*/finally {
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