package gestion_borne.crud;

import java.sql.Connection;
import java.sql.SQLException;

import city.city;
import connectionPool.DataSource;
import server.CityServer;

public abstract class DAO<T> {
	protected CityServer server;
	protected Connection connect;
	protected DataSource connection;
	public DAO(CityServer server) throws ClassNotFoundException, SQLException {
		this.server= server;
		this.connection= new DataSource();
	    this.connect = connection.getConnection();
	}
	public abstract boolean create (T obj, String nameCity) throws SQLException;
	public abstract boolean delete(T obj);
	public abstract boolean update (T obj);
	public abstract T find(int id);

}
