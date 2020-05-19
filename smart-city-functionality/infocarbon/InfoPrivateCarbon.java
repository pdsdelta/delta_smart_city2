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
		int nc = this.getNbCars();
		System.out.println("Nombre de voitures : "+ nc);
		System.out.println("Nombre de passagers moyen par voiture : 2 ");
		System.out.println("Calcul de l'empreinte carbonne associée à ces paramètres");
		double res = (nc * 27) * (186/2);
		return res/1000 ;
	}
	
	public static void main(String[] args) {
		InfoPrivateCarbon f = new InfoPrivateCarbon(1,4000);
		double res = f.calculateCarbon();
		System.out.println("Résultat = " + res + " kg de CO2");
		
	}

}
