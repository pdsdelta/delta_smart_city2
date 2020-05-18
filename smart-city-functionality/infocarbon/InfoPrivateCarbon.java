package infocarbon;

public class InfoPrivateCarbon extends InfoCarbon {

	
	private int nbCars ;
	
	protected InfoPrivateCarbon(int idCity, int nbCars) {
		super(idCity);
		this.nbCars = nbCars;
	}


	
	
	
	@Override
	public String toString() {
		return "privateCarbon [nbCars=" + nbCars + "]";
	}



	public int getNbCars() {
		return nbCars;
	}





	@Override
	public double calculateCarbon() {
		// TODO Auto-generated method stub
		return 2;
	}

}
