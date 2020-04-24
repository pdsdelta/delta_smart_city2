package borne;

public class MotionSensor {
	int id;
	float longitude;
	float latitude;
	int status =1;
	boolean isActive =true;
	//Ville idVille;
	
	public MotionSensor() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
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

	/*
	 * public int getIdVille() { return idVille; }
	 * 
	 * @Override public String toString() { return "MotionSensor [id=" + id +
	 * ", longitude=" + longitude + ", latitude=" + latitude + ", status=" + status
	 * + ", isActive=" + isActive + ", idVille=" + idVille + "]"; }
	 */
	

}
