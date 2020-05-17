package motionSensor;

import city.city;
/*
 * Cette classe est la classe dedié à la table MotionSensor (détecteur de véhicule) de notre base de données
 * Author: DONFACK ANAELLE
 */

public class MotionSensor {
	int id;
	int longitude;
	int latitude;

	boolean isActive =true;
	int numero;
	//city  city;

	public MotionSensor(int longitude,int latitude, boolean isActive,int numero) {
		this.longitude=longitude;
		this.latitude=latitude;
		this.isActive=isActive;
		this.numero=numero;
		//this.city=city;
	}
	public MotionSensor() {
		super();
		this.isActive=true;

	}
	public MotionSensor(int longitude,int latitude) {
		this.longitude=longitude;
		this.latitude=latitude;
		this.isActive=isActive;
		
		
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getNumero() {
		return numero;
	}

	/*
	 * public city getCity(int id) { if(id==this.city.getIdCity()) { return
	 * this.city; } return city; } public city infosCity() { return city; } public
	 * void setCity(city city) { this.city = city; }
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}
	@Override
	public String toString() {
		return "MotionSensor [id=" + id + ", longitude=" + longitude + ", latitude=" + latitude + ", isActive="
				+ isActive + ", numero=" + numero + "]";
	}

	
	 
	 


}
