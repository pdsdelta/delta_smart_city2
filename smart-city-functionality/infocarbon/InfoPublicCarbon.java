package infocarbon;

public class InfoPublicCarbon extends InfoCarbon {

	private int nbTram;
	private int longueurreseau ;
	
	public InfoPublicCarbon(int idCity,int nbTram,int longueurreseau) {
		super(idCity);
		this.nbTram = nbTram;
		this.longueurreseau = longueurreseau;
		
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

	@Override
	public double calculateCarbon() {
		int nt = this.getNbTram();
		int lt = this.getLongueurreseau();
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
		double pub = ((20 * 18) * nt) * (3.8/(np/nt));
		System.out.println("Nombre de Tramways : "+ nt);
		System.out.println("Longeure de la ligne : "+ lt);
		System.out.println("Nombre de passagers en une journée : "+ np);
		System.out.println("Calcul de l'empreinte carbonne associée à ces paramètres");
		System.out.println("Empreinte Carbon Tramways : " + pub + " g de CO2");
		return pub/1000;
	}
	
	public static void main(String[] args) {
		InfoPublicCarbon f = new InfoPublicCarbon(1,35,18);
		double res = f.calculateCarbon();
		System.out.println("Résultat = " + res + " kg de CO2");
		
	}
	
	

}
