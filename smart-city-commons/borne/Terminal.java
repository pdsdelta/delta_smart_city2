package borne;

import java.util.HashSet;

import org.json.simple.JSONObject;

import city.city;
import gestion_borne.mock.MotionSensorFonctionMock;
public class Terminal {

	int id;
	int longitude;
	int latitude;
	boolean isActive=true;
	int status;
	int numero;
	city city;
	
	public Terminal() {
		super();
		this.isActive=true;
		this.status=0;
	}
	public Terminal(int longitude,int latitude) {
		this.longitude=longitude;
		this.latitude=latitude;
		this.isActive=isActive;
		this.status=0;
	}

	public Terminal(int longitude,int latitude, boolean isActive,int status,int numero,city city) {
		this.longitude=longitude;
		this.latitude=latitude;
		this.isActive=isActive;
		this.status=status;
		this.numero=numero;
		this.city=city;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLongitude() {
		return longitude;
	}
	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}
	public int getLatitude() {
		return latitude;
	}
	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}


	public city getCity() {
		return city;
	}
	public void setCity(city city) {
		this.city = city;
	}
	@Override
	public String toString() {
		return "Terminal [id=" + id + ", longitude=" + longitude + ", latitude=" + latitude + ", isActive=" + isActive
				+ ", status=" + status + ", numero=" + numero + "]";
	}
}




