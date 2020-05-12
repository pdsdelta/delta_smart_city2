package gestion_borne.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import borne.Terminal;
import city.city;
import connectionPool.DataSource;
import server.CityServer;

public class TerminalDAO extends DAO<Terminal>{
	PreparedStatement pstmt;
	public TerminalDAO(CityServer server) throws ClassNotFoundException, SQLException {
		super(server);
	}

	@Override
	public boolean create(Terminal obj,String nameCity) throws SQLException  {
		int res=0;
		ResultSet result=null;
		result = this.connect.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM city WHERE namecity = " + nameCity);
		if(result.first()) {
		
		String query = "INSERT INTO Terminal (longitude, latitude, isActive, status, numero,city) " + "VALUES (?, ?, ?, ?, ?,?)";
		try {
			pstmt = this.connect.prepareStatement(query);
			pstmt.setLong(1, (long) obj.getLongitude());
			pstmt.setLong(2, (long) obj.getLatitude());
			pstmt.setBoolean(3, obj.isActive());
			pstmt.setLong(4, obj.getStatus());
			pstmt.setInt(6, result.getInt("idcity"));
			res = pstmt.executeUpdate();
			//obj= this.find(obj.getId());

		}catch(SQLException e) {
			e.printStackTrace();
		}
		}
		return true;
	}

	@Override
	public boolean delete(Terminal obj) {
		try {

			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_UPDATABLE).executeUpdate(
							"DELETE FROM Terminal WHERE id = " + obj.getId()
							);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;

	}



	@Override
	public boolean update(Terminal obj) {

		int res=0;
		String query = "update Terminal set longitude =?,latitude =?,isActive=? AND status=?  WHERE id= "+obj.getId(); 
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
		return true;
	}

	@Override
	public Terminal find(int numero) {
		Terminal terminal = new Terminal();      


		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Terminal t, city c"+" INNER JOIN city c ON  c.idcity= t.city WHERE numero = " + numero);
			ResultSet i;
			if(result.first()) {

				/*i = this.connect.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM city WHERE idcity = " + result.getInt("city"));
				if(i.first()) {*/
					//int idCity= i.getInt("idcity");
					
					/*terminal = new Terminal(
							result.getLong("longitude"),
							result.getLong("latitude"),
							result.getBoolean("isActive"),
							result.getInt("status"),
							result.getInt("numero"),
							city=; */
				}
			//}       
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return terminal;
	}
}
