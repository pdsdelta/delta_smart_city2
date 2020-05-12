package gestion_borne.crud;

import java.lang.System.Logger.Level;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import city.city;
import connectionPool.DataSource;
import motionSensor.MotionSensor;
import server.CityServer;

public class MotionSensorDAO extends DAO<MotionSensor> {
	PreparedStatement pstmt;


	public MotionSensorDAO(CityServer server) throws ClassNotFoundException, SQLException {
		super(server);
	}

	@Override
	public boolean create(MotionSensor obj,String nameCity) throws SQLException  {
		int res=0;
		ResultSet result=null;
		result = this.connect.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM city WHERE namecity = " + nameCity);
		String query = "INSERT INTO MotionSensor (longitude, latitude, isActive, status, numero,city) " + "VALUES (?, ?, ?, ?, ?)";
		try {
			pstmt = this.connect.prepareStatement(query);
			pstmt.setLong(1,  obj.getLongitude());
			pstmt.setLong(2,  obj.getLatitude());
			pstmt.setBoolean(3, obj.isActive());
			pstmt.setInt(5, result.getInt("idcity"));
			//obj= this.find(obj.getId());

		}catch(SQLException e) {
			e.printStackTrace();
		}
		return true;
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
			this.pstmt.setLong(1,  obj.getLongitude());
			this.pstmt.setLong(2,  obj.getLatitude());
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
				i = this.connect.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM city WHERE idcity = " + result.getInt("city"));
				if(i.first()) {
					int idCity= i.getInt("idcity");
					city city= null;
					captor = new MotionSensor(
							result.getLong("longitude"),
							result.getLong("latitude"),
							result.getBoolean("isActive"),
							result.getInt("numero"),
							city= captor.getCity(idCity));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return captor;
	}
}



