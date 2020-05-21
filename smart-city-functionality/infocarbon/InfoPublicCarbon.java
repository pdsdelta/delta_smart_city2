package infocarbon;

public class InfoPublicCarbon extends InfoCarbon {

	private int nbTram;
	private int longueurreseau ;
	private int nbBus;
	private double ect, ecb;
	
	public InfoPublicCarbon(int idCity,int nbTram,int longueurreseau,int nbBus) {
		super(idCity);
		this.nbTram = nbTram;
		this.longueurreseau = longueurreseau;
		this.nbBus = nbBus ;
		
	}
	
	

	@Override
	public String toString() {
		return "InfoPublicCarbon [nbTram=" + nbTram + ", longueurreseau=" + longueurreseau + "]";
	}

	public int getNbTram() {
		return nbTram;
	}

	public int getLongueurreseau() {
		return longueurreseau;
	}

	
	public int getNbBus() {
		return nbBus;
	}

	public double getEct() {
		return ect/1000;
	}

	public double getEcb() {
		return ecb/1000;
	}

	
	@Override
	public double calculateCarbon() {
		int nt = this.getNbTram();
		int lt = this.getLongueurreseau();
		int nb = this.getNbBus();
		int np = 0;
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
		double pub = ((20 * 18) * nt) * ((3.2/10000) * npmoyen/nt);
		this.ect = pub;
		double pubb =(nb*25)*(110);
		this.ecb= pubb;
		System.out.println("Nombre de Tramways : "+ nt);
		System.out.println("Longeure de la ligne : "+ lt);
		System.out.println("Nombre de passagers en une journée : "+ np);
		System.out.println("Nombre de BUS : "+ nb);
		System.out.println("Calcul de l'empreinte carbonne associée à ces paramètres");
		System.out.println("Empreinte Carbon des Tramways : " + pub + " g de CO2");
		System.out.println("Empreinte Carbon des Bus : " + pubb + " g de CO2");
		double emppub = pub + pubb ;
		System.out.println("Empreinte Carbon des transports publics : " + emppub + " g de CO2");
		return emppub/1000;
	}
	
	public static void main(String[] args) {
		InfoPublicCarbon f = new InfoPublicCarbon(1,35,18,45);
		double res = f.calculateCarbon();
		System.out.println("Résultat = " + res + " kg de CO2");
		
	}
	
	

}
