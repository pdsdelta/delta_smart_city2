package borne;

public class Terminal {

	int id;
	float longitude;
	float latitude;
	//Ville idVille;
	boolean isActive=true;
	int status;
	
	
	public Terminal() {
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
	/*public int getIdVille() {
		return idVille;
	}*/
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	/*@Override
	public String toString() {
		return "Terminal [id=" + id + ", longitude=" + longitude + ", latitude=" + latitude + ", idVille=" + idVille
				+ ", isActive=" + isActive + ", status=" + status + "]";
	}*/
	
	
	
	
}