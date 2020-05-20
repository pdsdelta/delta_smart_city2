package gestion_borne.crud;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

import borne.Terminal;
import city.city;
import connectionPool.DataSource;
import functionality_server.functionalityServer;

public class TerminalDAO extends DAO<Terminal>{
	Connection connection;
	PreparedStatement pstmt;
	Statement stm;
	ResultSet rs;
	private String json;
	private PrintWriter out;
	private BufferedReader in;
	private Socket clientSocket; 
	public TerminalDAO(functionalityServer server) throws ClassNotFoundException, SQLException {
		super(server);
	}
	public void startConnection(String ip, int port) throws UnknownHostException, IOException {
		clientSocket = new Socket(ip, port);
		out=new PrintWriter(this.clientSocket.getOutputStream(), true);
		in=new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
		out.println(json);
		System.out.println(json);
	}

	@Override
	public String create(Terminal obj) throws SQLException  {
		int res=0;

        String json ="";
		String query = "INSERT INTO Terminal (longitude, latitude, isActive, status,city) " + "VALUES (?, ?, ?, ?,?)";
		try {
			pstmt = this.connect.prepareStatement(query);
			pstmt.setLong(1, (long) obj.getLongitude());
			pstmt.setLong(2, (long) obj.getLatitude());
			pstmt.setBoolean(3, obj.isActive());
			pstmt.setLong(4, obj.getStatus());
			pstmt.setInt(5, 1);
			res=pstmt.executeUpdate();

		}catch(SQLException e) {
			e.printStackTrace();
		}

		json  ="{request:{ operation_type: CREATE_TERMINAL, target: Terminal , longitude: "+ obj.getLongitude() + ", latitude "+obj.getLatitude() +"}} " ;
        System.out.println(json);
		if(res==1) {
			return json;
		}else {
			return null;
		}


	}

	@Override
	public boolean delete(Terminal obj) {
		try {

			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_UPDATABLE).executeUpdate(
							"DELETE FROM Terminal WHERE numero = " + obj.getNumero()
							);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.json  ="{request:{ operation_type: DELETE_TERMINAL, target: Terminal,"+"Numero : "+obj.getNumero()+"}} " ;
		return true;

	}



	@Override
	public boolean update(Terminal obj) {

		int res=0;
		String query = "update Terminal set longitude =?,latitude =?,isActive=? AND status=?  WHERE numero= "+obj.getNumero(); 
		try { 
			this.pstmt = connect.prepareStatement(query); 
			this.pstmt.setLong(1,obj.getLongitude());
			this.pstmt.setLong(2,obj.getLatitude());
			this.pstmt.setBoolean(3, obj.isActive());
			this.pstmt.setInt(4, obj.getStatus());
			res = pstmt.executeUpdate(); 

		} catch (SQLException ex) {
			Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
		}
		if (res==1) {
			this.json  ="{request:{ operation_type: UPDATE_TERMINAL, target: Terminal , longitude: "+ obj.getLongitude() + "latitude :"+obj.getLatitude() +"isActive: "+obj.isActive()+"}} " ;
			return true;
		}else {
			return false;

		}
	}

	@Override
	public Terminal find(int numero) {
		Terminal terminal = new Terminal();      


		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Terminal WHERE numero = " + numero);
			ResultSet i;
			if(result.first()) {
				city city ;
				i = this.connect.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM city WHERE idcity = " + result.getInt("city"));
				if(i.first()) {
					city =new city();
					city.setTailleCity(i.getDouble("taillecity"));
					city.setNameCity(i.getString(2));
					city.setLargeurCity(i.getInt("largeurcity"));
					city.setLongueurCity(i.getInt("longueurcity"));
					city.setBudgetStation(i.getInt("budgetstation"));
					city.setNombreMaxVoiture(i.getInt("nombremaxvoiture"));
					terminal = new Terminal(
							result.getInt("longitude"),
							result.getInt("latitude"),
							result.getBoolean("isActive"),
							result.getInt("status"),
							result.getInt("numero"),
							city);
				}
				}       
			} catch (SQLException e) {
				e.printStackTrace();
			}
		this.json  ="{request:{ operation_type: SELECT_TERMINAL, target: Terminal , longitude: "+terminal.getLongitude() + "latitude, :"+terminal.getLatitude() +" ,Numero:"+terminal.getNumero()+"isActive: "+terminal.isActive()+"}} " ;
			return terminal;
		}
		public List<Terminal> getAll() throws SQLException { 
			List<Terminal> res = new ArrayList<Terminal>(); 
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Terminal");
			while (result.next()) {
				Terminal borne = new Terminal(); 

				borne.setLongitude(result.getInt("longitude"));
				borne.setLatitude(result.getInt("latitude"));
				borne.setActive(result.getBoolean("isActive"));
				borne.setStatus(result.getInt("status"));
				borne.setNumero(result.getInt("numero"));
				res.add(borne);

			} 
			this.json  ="{request:{ operation_type: ALL_TERMINAL, target: Terminal}} " ;
			return res;
		}
		public String SetNbrMaxVoiture(int nombre){
			int res=0;
			String jsone="";
			String query = "update city set nombremaxvoiture=? WHERE idcity= "+1; 
			try { 
				this.pstmt = this.connect.prepareStatement(query); 
				this.pstmt.setInt(1, nombre);;
				res = pstmt.executeUpdate(); 

			} catch (SQLException ex) {
				Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
			}
			if(res==1) {
				JSONObject requestDetails = new JSONObject();
				requestDetails.put("operation_type", "UPDATE_NBR");
				requestDetails.put("Target","city");
				requestDetails.put("nbrmaxvoiture", nombre);
				JSONObject voitureObject= null;
				JSONObject request = new JSONObject(); 
				request.put("request", requestDetails);
				jsone = request.toString();
			}else {
				jsone=null;
			}
			this.json  ="{request:{ operation_type: SET_CITY, target: city ,nombremaxvoiture :"+nombre+"}} " ;
			return jsone;
		}
	}
