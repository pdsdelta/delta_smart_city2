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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

import borne.Car;
import borne.Terminal;
import city.city;
import connectionPool.DataSource;
import functionality_server.functionalityServer;

/*
 * Cette classe permet de mener les operations de CRUD sur la table Terminal de notre
 * base de données à travers le serveur
 * AUTHOR: DONFACK ANAELLE
 */
public class CarDAO extends DAO<Car>{

	PreparedStatement pstmt;

	//Variables de Communications avec le serveur
	private String json;
	private PrintWriter out;
	private BufferedReader in;
	private Socket clientSocket; 
	public CarDAO(functionalityServer server) throws ClassNotFoundException, SQLException {
		super(server);
	}
	public void startConnection(String ip, int port) throws UnknownHostException, IOException {
		clientSocket = new Socket(ip, port);
		out=new PrintWriter(this.clientSocket.getOutputStream(), true);
		in=new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
		out.println(json);
		System.out.println(json);
	}

	//Methode de création d'une borne
	@Override
	public String create(Car obj) throws SQLException  {
		return json;


	}

	//Methode de suppression d'une borne
	@Override
	public boolean delete(Car  obj) {
		return false;

	}


	//Methode de Modification d'une borne
	@Override
	public boolean update(Car obj) {
		return false;


	}
	//Methode de recherche d'une borne à partir de son numero
	@Override
	public Car find(int numero) {
		return null;

	}

	//METHODE QUI PERMET DE LISTER TOUTES LES BORNES DE LA VILLE
	public List<Car> getAll() throws SQLException { 
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");  


		List<Car> res = new ArrayList<Car>(); 
		ResultSet result = this.connect.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM carstats");
		while (result.next()) {
			Car borne = new Car(); 

			borne.setIdCity(result.getInt("idcity"));
			borne.setNbcars(result.getInt("nbcars"));
			borne.setDateof(formatter.format(result.getDate("dateof")));
			res.add(borne);

		} 
		this.json  ="{request:{ operation_type: ALL_TERMINAL, target: Terminal}} " ;
		return res;
	}

	





}
