package infocarbon;

public class infoEstimatedCarbon extends InfoCarbon {
	
	private int nbCars ;
	private int nbTram;
	private double longueurreseau ;
	
	
	public infoEstimatedCarbon(int idCity,int nbCars,int nbPass,int nbTram,double longueurreseau) {
		super(idCity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double calculateCarbon() {
		double  epc, epuc; 
		return 4;
	}

}
