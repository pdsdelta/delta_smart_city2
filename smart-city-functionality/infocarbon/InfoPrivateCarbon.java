package infocarbon;

public class InfoPrivateCarbon extends InfoCarbon {

	
	private int nbCars ;
	private int nbMotos ;
	private double ecc, ecm;
	
	
	protected InfoPrivateCarbon(int idCity, int nbCars,int nbMotos) {
		super(idCity);
		this.nbCars = nbCars;
		this.nbMotos = nbMotos;
	}
	
	public int getNbMotos() {
		return nbMotos;
	}





	public double getEcc() {
		return ecc/1000;
	}





	public double getEcm() {
		return ecm/1000;
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
		int nm = this.getNbMotos();
		System.out.println("Nombre de voitures : "+ nc);
		System.out.println("Nombre de passagers moyen par voiture : 2 ");
		System.out.println("Nombre de motos : "+ nm);
		System.out.println("Calcul de l'empreinte carbonne associée à ces paramètres");
		double priv = (nc * (27)) * (186/2);
		this.ecc = priv;
		double empmot = (6 * nm) * (168/2);
		this.ecm = empmot;
		System.out.println("Empreinte Carbon des Voitures : " + priv + " g de CO2");
		System.out.println("Empreinte Carbon des motos : " + empmot + " g de CO2");
		double emppriv = priv + empmot;
		System.out.println("Empreinte Carbon des transports privés : " + emppriv + " g de CO2");
		return priv/1000 ;
	}
	
	public static void main(String[] args) {
		InfoPrivateCarbon f = new InfoPrivateCarbon(1,4000,200);
		double res = f.calculateCarbon();
		System.out.println("Résultat = " + res + " kg de CO2");
		
	}

}
