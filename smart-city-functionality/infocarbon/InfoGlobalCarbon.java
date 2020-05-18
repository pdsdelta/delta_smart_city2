package infocarbon;

public class InfoGlobalCarbon extends InfoCarbon{

	private int nbCars ;
	private int nbTram;
	private int longueurreseau ;
	
	public void setNbCars(int nbCars) {
		this.nbCars = nbCars;
	}

	public InfoGlobalCarbon() {
	}
	
	protected InfoGlobalCarbon(int idCity,int nbCars,int nbTram,int longueurreseau) {
		super(idCity);
		this.nbCars = nbCars;
		this.nbTram = nbTram;
		this.longueurreseau = longueurreseau;
	}
	
	public int getNbCars() {
		return nbCars;
	}
	
	public int getNbTram() {
		return nbTram;
	}

	public int getLongueurreseau() {
		return longueurreseau;
	}

	@Override
	public double calculateCarbon() {
		// TODO Auto-generated method stub
		return 3;
	}
	
	
}
