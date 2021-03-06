package infocarbon;

import java.text.DecimalFormat;

public class InfoEstimatedCarbon extends InfoCarbon {
	

	private int nbCars ;
	private int npc;
	private double km ;
	private int nbMotos;
	private int nbTrotinette;
	private int nbBus;
	private double kmb;
	private int nbTram;
	private double longueurreseau ;
	private int hs;
	private double ecpriv, ecpub;
	private int npp;
	private double ect, ecb, ectt, ecc, ecm ;
	
	
	public double getEcb() {
		return ecb/1000;
	}



	public void setEcb(double ecb) {
		this.ecb = ecb;
	}



	public double getEctt() {
		return ectt/1000;
	}



	public double getEcc() {
		return ecc/1000;
	}



	public double getEcm() {
		return ecm/1000;
	}



	public int getNbMotos() {
		return nbMotos;
	}



	public int getNbTrotinette() {
		return nbTrotinette;
	}

	public double getEct() {
		return ect/1000;
	}


	
	public double getEcpriv() {
		return ecpriv/1000;
	}



	public void setEcpriv(double ecpriv) {
		this.ecpriv = ecpriv;
	}



	public double getEcpub() {
		return ecpub/1000;
	}



	public void setEcpub(double ecpub) {
		this.ecpub = ecpub;
	}



	



	public InfoEstimatedCarbon(int idCity,int nbCars,int npc,double km ,int nbMotos,int nbTrotinette,int nbTram,double longueurreseau, int hs,int nbBus,double kmb) {
		super(idCity);
		this.nbCars = nbCars;
		this.npc = npc;
		this.km = km;
		this.nbMotos = nbMotos;
		this.nbTrotinette = nbTrotinette;
		this.nbTram = nbTram;
		this.longueurreseau = longueurreseau; 
		this.hs = hs;
		this.nbBus = nbBus;
		this.kmb = kmb;
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
		int nbb = this.getNbBus();
		double kmbb = this.getKmb();
		int nc = this.getNbCars();
		int npass = this.getNpc();
		double kilo = this.getKm();
		int nmoo = this.getNbMotos();
		int nttr = this.getNbTrotinette();
		int np = 0;
		if(lt < 0) {
			np = 0;
		}else if(lt < 5) {
			np = 3000;
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
		this.npp = np;
		System.out.println("Nombre de Tramways : "+ nt);
		System.out.println("Longeure de la ligne : "+ lt + " Km");
		System.out.println("Nombre de passagers global des tramways en une journée : "+ np);
		System.out.println("Nombre d'heures de service : "+ hours +" heures");
		System.out.println("Nombre de Bus : "+ nbb);
		System.out.println("Distance moyenne parcouru par les bus : "+ kmbb + " Km");
		System.out.println("Nombre de voitures : "+ nc);
		System.out.println("Nombre de passagers moyen par voiture : "+ npass);
		System.out.println("Distance moyenne parcouru par les voitures : "+ kilo + " Km");
		System.out.println("Nombre de motos : "+ nmoo);
		System.out.println("Calcul de l'empreinte carbonne associée à ces paramètres");
		
		int npmoyen = np / nt;
		double pub = (20 * hours) * nt * ((3.2/10000) * npmoyen/nt);
		double empbus = (nbb*kmbb)*(110);
		double emptrot = (3 * nttr) * (9.7) ;
		System.out.println("Empreinte Carbon des tramways : " + pub + " g de CO2");
		this.ect = pub;
		System.out.println("Empreinte Carbon Bus : " + empbus + " g de CO2");
		this.ecb = empbus;
		System.out.println("Empreinte Carbon trotinettes electriques : "+ emptrot +" g de CO2");
		this.ectt = emptrot;
		double emppub = pub + empbus + emptrot ;
		this.setEcpub(emppub);
		System.out.println("Empreinte Carbon des transports publics : " + emppub + " g de CO2");
		double priv = (nc * kilo ) * (193 * npass/3);
		double empmot = (6 * nmoo) * (168/2);
		System.out.println("Empreinte Carbon des voitures  : " + priv + " g de CO2");
		this.ecc = priv;
		System.out.println("Empreinte Carbon des motos : " + empmot + " g de CO2");
		this.ecm = empmot;
		double empglob = priv + empmot ; 
		this.setEcpriv(empglob);
		System.out.println("Empreinte Carbon des transports privés : " + empglob + " g de CO2");
		double glob = emppub + empglob ;
		System.out.println("Empreinte Carbon globale  : " + glob + " g de CO2");
		
		return glob/1000;
	}
	
	public int getNbBus() {
		return nbBus;
	}



	public double getKmb() {
		return kmb;
	}



	public int getNpp() {
		return npp;
	}



	public static void main(String[] args) {
		InfoEstimatedCarbon f = new InfoEstimatedCarbon(1,500,3,20,200,50,20, 12, 20, 50, 30);
		double res = f.calculateCarbon();
		DecimalFormat df = new DecimalFormat ( ) ; 
		df.setMaximumFractionDigits ( 2 ) ; 
		System.out.println("Résultat = " + df.format(res) + " Kg de CO2");
		
	}
}
