package borne;

import city.city;

public class Terminal {

	int id;
	long longitude;
	long latitude;
	boolean isActive=true;
	int status;
	int numero;
	city city;

	public Terminal() {
		super();
	}

	public Terminal(long longitude,long latitude, boolean isActive,int status,int numero, city city) {
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
	public long getLongitude() {
		return longitude;
	}
	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}
	public long getLatitude() {
		return latitude;
	}
	public void setLatitude(long latitude) {
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
	
	
	/*@Override
	public String toString() {
		return "Terminal [id=" + id + ", longitude=" + longitude + ", latitude=" + latitude + ", idVille=" + idVille
				+ ", isActive=" + isActive + ", status=" + status + "]";
	}*/




}
