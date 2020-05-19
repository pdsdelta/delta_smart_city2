package gestion_borne.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import city.city;
import functionality_server.functionalityServer;
import motionSensor.MotionSensor;

public class MotionSensorDAO extends DAO<MotionSensor> {
	Connection connection;
	PreparedStatement pstmt;
	Statement stm;
	ResultSet rs; 

	public MotionSensorDAO(functionalityServer server) throws ClassNotFoundException, SQLException {
		super(server);
	}

	@Override
	public String create(MotionSensor obj) throws SQLException  {
		int res=0;
        String json="";
        
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
		json  ="{request:{ operation_type: CREATE_SENSOR, target: MotionSensor , longitude: "+ obj.getLongitude() + "latitude "+obj.getLatitude() +"}} " ;
        System.out.println(json);
		if(res==1) {
			return json;
		}else {
			return null;
		}

	}

	@Override
	public boolean delete(MotionSensor obj) {
		try {

			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_UPDATABLE).executeUpdate(
							"DELETE FROM MotionSensor WHERE numero = " + obj.getNumero()
							);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;

	}



	@Override
	public boolean update(MotionSensor obj) {

		int res=0;
		String query = "update MotionSensor set longitude =?,latitude =? AND isActive=?  WHERE numero= "+obj.getNumero(); 
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

					captor = new MotionSensor(
							result.getInt("longitude"),
							result.getInt("latitude"),
							result.getBoolean("isActive"),
							result.getInt("numero"),
							city);
				}
			}}catch (SQLException e) {
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



