package borne;

public class Car {
	
	private int idCity;
	private String dateof;
	private int nbcars;
	
	public Car() {
		
	}
	public Car(int idCity, int nbcars, String dateof) {
		this.idCity= idCity;
		this.nbcars=nbcars;
		this.dateof=dateof;
	}
	public int getIdCity() {
		return idCity;
	}
	public void setIdCity(int idCity) {
		this.idCity = idCity;
	}
	public String getDateof() {
		return dateof;
	}
	public void setDateof(String dateof) {
		this.dateof = dateof;
	}
	public int getNbcars() {
		return nbcars;
	}
	public void setNbcars(int nbcars) {
		this.nbcars = nbcars;
	}
	@Override
	public String toString() {
		return "Car [idCity=" + idCity + ", dateof=" + dateof + ", nbcars=" + nbcars + "]";
	}
	

}
