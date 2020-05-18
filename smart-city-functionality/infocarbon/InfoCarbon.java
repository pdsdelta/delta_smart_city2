package infocarbon;

public abstract class InfoCarbon {
	
	
	protected int idCity;
	
	
	protected InfoCarbon() {
	}
	
	protected InfoCarbon(int idCity) {
		this.idCity = idCity;
	}
	
	public abstract double calculateCarbon() ;
	
	
	
	
	
}
