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
		int nt = this.getNbTram();
		int lt = this.getLongueurreseau();
		int nc = this.getNbCars();
		int np = 0;
		if(lt < 0) {
			np = 0;
		}else if(lt < 5) {
			np = 50000;
		}else if( lt> 5 && lt <10) {
				np = 70000 ;
		}else if( lt> 5 && lt <10) {
			np = 70000 ;
		}else if( lt> 5 && lt <10) {
			np = 70000 ;
		}else if( lt> 10 && lt <14) {
			np = 100000 ;
		}else if( lt> 14 && lt <17) {
			np = 170000 ;
		}else if( lt> 17 && lt <20) {
			np = 200000 ;
		}else {
			np = 222000 ;
		}
		System.out.println("Nombre de Tramways : "+ nt);
		System.out.println("Longeure de la ligne : "+ lt);
		System.out.println("Nombre de passagers global des tramways en une journée : "+ np);
		System.out.println("Nombre de voitures : "+ nc);
		System.out.println("Nombre de passagers moyen par voiture : 2 ");
		System.out.println("Calcul de l'empreinte carbonne associée à ces paramètres");
		
		double pub = ((20 * 18) * nt) * (3.8/(np/nt));
		System.out.println("Empreinte Carbon Tramways : " + pub + " g de CO2");
		double priv = (nc * (27)) * (186/2);
		System.out.println("Empreinte Carbon Voitures : " + priv + " g de CO2");
		double glob = pub + priv ;
		System.out.println("Empreinte Carbon globale  : " + glob + " g de CO2");
		return glob;
		
	
	}
}
