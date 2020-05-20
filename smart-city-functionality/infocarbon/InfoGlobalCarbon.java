package infocarbon;

public class InfoGlobalCarbon extends InfoCarbon{

	private int nbCars ;
	private int nbMotos ;
	private int nbBus ;
	private int nbTram;
	private int longueurreseau ;
	
	public void setNbCars(int nbCars) {
		this.nbCars = nbCars;
	}

	public InfoGlobalCarbon() {
	}
	
	protected InfoGlobalCarbon(int idCity,int nbCars,int nbTram,int longueurreseau,int nbMotos, int nbBus) {
		super(idCity);
		this.nbCars = nbCars;
		this.nbTram = nbTram;
		this.longueurreseau = longueurreseau;
		this.nbMotos = nbMotos;
		this.nbBus = nbBus ; 
	}
	
	public int getNbMotos() {
		return nbMotos;
	}

	public int getNbBus() {
		return nbBus;
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
		int nb = this.getNbBus();
		int nm = this.getNbMotos();
		int np = 0;
		int nh =0 ;
		if(lt < 0) {
			np = 0;
		}else if(lt < 5) {
			np = 5000;
		}else if( lt> 5 && lt <10) {
				np = 7000 ;
		}else if( lt> 5 && lt <10) {
			np = 8000 ;
		}else if( lt> 10 && lt <14) {
			np = 10000 ;
		}else if( lt> 14 && lt <17) {
			np = 17000 ;
		}else if( lt> 17 && lt <20) {
			np = 20000 ;
		}else {
			np = 22200 ;
		}
		int npmoyen = np / nt;
		System.out.println("Nombre de Tramways : "+ nt);
		System.out.println("Longeure de la ligne : "+ lt);
		System.out.println("Nombre de BUS : "+ nb);
		System.out.println("Nombre de voitures : "+ nc);
		System.out.println("Nombre de passagers moyen par voiture : 2 ");
		System.out.println("Nombre de motos : "+ nm);
		System.out.println("Calcul de l'empreinte carbonne associée à ces paramètres");
		if(lt!=0) {
			 nh = 18;
		}
		
		double pub = ((20 * nh) * nt) * ((3.2/10000) * npmoyen/nt);
		double empbus = (nb*25)*(110);
		System.out.println("Empreinte Carbon des Tramways : " + pub + " g de CO2");
		System.out.println("Empreinte Carbon des Bus : " + empbus + " g de CO2");
		double emppub = pub + empbus ;
		double priv = (nc * (27)) * (186/2);
		double empmot = (6 * nm) * (168/2);
		System.out.println("Empreinte Carbon des Voitures : " + priv + " g de CO2");
		System.out.println("Empreinte Carbon des motos : " + empmot + " g de CO2");
		double emppriv = priv + empmot ;
		double glob = emppub + emppriv ;
		System.out.println("Empreinte Carbon globale  : " + glob + " g de CO2");
		return glob/1000;
		
	
	}
}
