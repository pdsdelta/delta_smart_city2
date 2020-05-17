package gestion_borne.crud;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import city.city;
import connectionPool.DataSource;
import functionality_server.functionalityServer;
import server.CityServer;

public abstract class DAO<T> {
	protected functionalityServer server;
	protected Connection connect;
	protected DataSource connection;
	public DAO(functionalityServer server2) throws ClassNotFoundException, SQLException {
		this.server= server2;
		this.connection= new DataSource();
	    this.connect = connection.getConnection();
	}
	public abstract boolean create (T obj) throws SQLException;
	public abstract boolean delete(T obj);
	public abstract boolean update (T obj);
	public abstract T find(int id);
	public abstract List<T> getAll() throws SQLException;

}
