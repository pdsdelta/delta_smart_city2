package gestion_borne.test;

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

public class TestAlert {
	static TerminalDAO data;
	static functionalityServer server;
	public TestAlert() {}
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
        long longi =  (Long) borne.get("numero");    
       
        
        int numero= (int)longi;
   
       Terminal t = new Terminal();
       
        data= new TerminalDAO(server);
        t= data.find(numero);
        t.getCity().setAlert(1);
        System.out.println( t.getCity().getAlert());
        data.SetAlertCity();
        
    }
	public JSONArray readScenario() {
		//JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();
		JSONArray terminal = null;
		try (FileReader reader = new FileReader("E:\\FindTerminal.json"))
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
		TestAlert  test = new TestAlert();
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
