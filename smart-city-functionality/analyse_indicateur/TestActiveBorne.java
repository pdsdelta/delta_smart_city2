package analyse_indicateur;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import borne.Terminal;
import functionality_server.functionalityServer;
import gestion_borne.crud.TerminalDAO;


public class TestActiveBorne {

	

	
		static TerminalDAO data;
		static functionalityServer server;
		public TestActiveBorne() {}
		public void constructObject() throws ClassNotFoundException, SQLException {
			JSONArray request = readScenario();
		
	        //Iterate over employee array
	        request.forEach( voit -> {
				try {
					parseVoitureObject( (JSONObject) voit );
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} );
			
			
			
			
		}
		private static void parseVoitureObject(JSONObject requete) throws ClassNotFoundException, SQLException 
	    {
			
	        //Get employee object within list
	        JSONObject borne = (JSONObject) requete.get("request");
	         
	        //Get longitude 
	        long longi =  (Long) borne.get("longitude");    
	       
	         
	        //Get latitude
	        long lati =  (Long) borne.get("latitude"); 
	        //Get status
	        long stat=  (Long) borne.get("status");
	        
	        int longitude= (int)longi;
	        int latitude =(int)lati;
	        int status=(int)stat;
	        
	        Terminal objet = new Terminal(longitude,latitude);
	        objet.setStatus(status);
	        data= new TerminalDAO(server);
			data.create(objet);
	       
	         
	        
	    }
		public JSONArray readScenario() {
			//JSON parser object to parse read file
			JSONParser jsonParser = new JSONParser();
			JSONArray terminal = null;
			try (FileReader reader = new FileReader("C:\\Users\\etudiant\\Desktop\\ActiveBorne.json"))
			{
				//Read JSON file
				Object obj = jsonParser.parse(reader);
				terminal = (JSONArray) obj;
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return terminal;

		}
		public static void main (String args[]) {
			TestActiveBorne  test = new TestActiveBorne();
		     try {
				test.constructObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}

	}
