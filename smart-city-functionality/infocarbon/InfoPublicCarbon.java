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
		// TODO FORMULE
		return 1;
	}
	
	
	
	
	

}
