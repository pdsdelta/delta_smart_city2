package gestion_borne.crud;

import java.lang.System.Logger.Level;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import borne.Terminal;
import city.city;
import connectionPool.DataSource;
import functionality_server.functionalityServer;
import motionSensor.MotionSensor;
import server.CityServer;

public class MotionSensorDAO extends DAO<MotionSensor> {
	Connection connection;
	PreparedStatement pstmt;
	Statement stm;
	ResultSet rs; 

	public MotionSensorDAO(functionalityServer server) throws ClassNotFoundException, SQLException {
		super(server);
	}

	@Override
	public boolean create(MotionSensor obj) throws SQLException  {
		int res=0;
		
		String query = "INSERT INTO MotionSensor (longitude, latitude,isActive,city) " + "VALUES (?, ?, ?, ?)";
		try {
			pstmt = this.connect.prepareStatement(query);
			pstmt.setInt(1, obj.getLongitude());
			pstmt.setInt(2,  obj.getLatitude());
			pstmt.setBoolean(3, obj.isActive());
			pstmt.setInt(4, 1);
			res=pstmt.executeUpdate();

		}catch(SQLException e) {
			e.printStackTrace();
		}
	
		if(res==1) {
			return true;
		}else {
			return false;
		}
		
	}

	@Override
	public boolean delete(MotionSensor obj) {
		try {

			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_UPDATABLE).executeUpdate(
							"DELETE FROM MotionSensor WHERE id = " + obj.getId()
							);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;

	}



	@Override
	public boolean update(MotionSensor obj) {

		int res=0;
		String query = "update MotionSensor set longitude =?,latitude =? AND isActive=?  WHERE id= "+obj.getId(); 
		try { 
			this.pstmt = connect.prepareStatement(query); 
			this.pstmt.setInt(1,  obj.getLongitude());
			this.pstmt.setInt(2,  obj.getLatitude());
			this.pstmt.setBoolean(3, obj.isActive());
			res = pstmt.executeUpdate(); 

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return true;
	}

	@Override
	public MotionSensor find(int numero) {
		MotionSensor captor = new MotionSensor();      


		try {

			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM MotionSensor WHERE numero = " + numero);
			ResultSet i = null;
			if(result.first()) {
//				i = this.connect.createStatement(
//						ResultSet.TYPE_SCROLL_INSENSITIVE,
//						ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM city WHERE idcity = " + result.getInt("city"));
//				if(i.first()) {
//					int idCity= i.getInt("idcity");
//					city city= null;
					captor = new MotionSensor(
							result.getInt("longitude"),
							result.getInt("latitude"),
							result.getBoolean("isActive"),
							result.getInt("numero"));
				}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return captor;
	}

	@Override
	public List<MotionSensor> getAll() throws SQLException { 
		
		List<MotionSensor> res = new ArrayList<MotionSensor>(); 
		ResultSet result = this.connect.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM MotionSensor");
			while (result.next()) {
				MotionSensor captor = new MotionSensor(); 
				//captor.setId(result.getInt("id"));
				captor.setLongitude(result.getInt("longitude"));
				captor.setLatitude(result.getInt("latitude"));
				captor.setActive(result.getBoolean("isActive"));
				captor.setNumero(result.getInt("numero"));
				res.add(captor);
			}
		
		
		return res;
	}
}



