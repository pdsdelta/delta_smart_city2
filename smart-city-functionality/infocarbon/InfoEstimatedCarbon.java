package infocarbon;

import java.text.DecimalFormat;

public class InfoEstimatedCarbon extends InfoCarbon {
	

	private int nbCars ;
	private int npc;
	private double km ;
	private int nbTram;
	private double longueurreseau ;
	private int hs;
	private double ecpriv, ecpub;
	
	
	public double getEcpriv() {
		return ecpriv;
	}



	public void setEcpriv(double ecpriv) {
		this.ecpriv = ecpriv;
	}



	public double getEcpub() {
		return ecpub;
	}



	public void setEcpub(double ecpub) {
		this.ecpub = ecpub;
	}



	public InfoEstimatedCarbon(int idCity,int nbCars,int npc,double km ,int nbTram,double longueurreseau, int hs) {
		super(idCity);
		this.nbCars = nbCars;
		this.npc = npc;
		this.km = km;
		this.nbTram = nbTram;
		this.longueurreseau = longueurreseau; 
		this.hs = hs;
	}

	

	public double getKm() {
		return km;
	}

	public void setKm(double km) {
		this.km = km;
	}

	public int getNbCars() {
		return nbCars;
	}

	public int getNpc() {
		return npc;
	}

	public int getNbTram() {
		return nbTram;
	}

	public double getLongueurreseau() {
		return longueurreseau;
	}

	public int getHs() {
		return hs;
	}

	@Override
	public String toString() {
		return "infoEstimatedCarbon [nbCars=" + nbCars + ", npc=" + npc + ", km=" + km + ", nbTram=" + nbTram
				+ ", longueurreseau=" + longueurreseau + ", hs=" + hs + "]";
	}
	
	@Override
	public double calculateCarbon() {
		int nt = this.getNbTram();
		double lt = this.getLongueurreseau();
		int hours = this.getHs();
		int nc = this.getNbCars();
		int npass = this.getNpc();
		double kilo = this.getKm();
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
		System.out.println("Longeure de la ligne : "+ lt + " Km");
		System.out.println("Nombre de passagers global des tramways en une journée : "+ np);
		System.out.println("Nombre d'heures de service : "+ hours +" heures");
		System.out.println("Nombre de voitures : "+ nc);
		System.out.println("Nombre de passagers moyen par voiture : "+ npass);
		System.out.println("Distance moyenne parcouru par les voitures : "+ kilo + " Km");
		System.out.println("Calcul de l'empreinte carbonne associée à ces paramètres");
		
		double pub = ((20 * hours) * nt) * ((3.2/(np/nt)));
		System.out.println("Empreinte Carbon Tramways : " + pub + " g de CO2");
		this.setEcpub(pub);
		double priv = (nc * kilo ) * (186/npass);
		System.out.println("Empreinte Carbon Voitures : " + priv + " g de CO2");
		this.setEcpriv(priv);
		double glob = pub + priv ;
		System.out.println("Empreinte Carbon globale  : " + glob + " g de CO2");
		
		return glob/1000;
	}
	
	public static void main(String[] args) {
		InfoEstimatedCarbon f = new InfoEstimatedCarbon(1,500,3,20,120,18,20);
		double res = f.calculateCarbon();
		DecimalFormat df = new DecimalFormat ( ) ; 
		df.setMaximumFractionDigits ( 2 ) ; 
		System.out.println("Résultat = " + df.format(res) + " Kg de CO2");
		
	}
}
