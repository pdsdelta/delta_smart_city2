package infocarbon;

public abstract class InfoCarbon {
	
	
	protected int idCity;
	
	
	protected InfoCarbon() {
	}
	
	protected InfoCarbon(int idCity) {
		this.idCity = idCity;
	}
	
	
	//ABSTRACT METHOD WICH CALCULATE THE CARBON FOOTPRINT
	public abstract double calculateCarbon() ;
	
	
	
	
	
}
