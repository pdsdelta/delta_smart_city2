package motionSensor;

import city.city;
/*
 * Cette classe est la classe dedié à la table MotionSensor (détecteur de véhicule) de notre base de données
 * Author: DONFACK ANAELLE
 */

public class MotionSensor {
	int id;
	long longitude;
	long latitude;

	boolean isActive =true;
	int numero;
	city  city;

	public MotionSensor(long longitude,long latitude, boolean isActive,int numero, city city) {
		this.longitude=longitude;
		this.latitude=latitude;
		this.isActive=isActive;
		this.numero=numero;
		this.city=city;
	}
	public MotionSensor() {
		super();
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getNumero() {
		return numero;
	}
	public city getCity(int id) {
		return city;
	}
	public city infosCity() {
		return city;
	}
	public void setCity(city city) {
		this.city = city;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}

	/*
	 * @Override public String toString() { return "MotionSensor [id=" + id +
	 * ", longitude=" + longitude + ", latitude=" + latitude + ", status=" + status
	 * + ", isActive=" + isActive + ", idVille=" + idVille + "]"; }
	 */


}
