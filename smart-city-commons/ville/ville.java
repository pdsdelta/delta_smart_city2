package ville;

public class ville {
	
	int idVille; 
	String nomVille;
	int longueurVille;
	int largeurVille;
	int budgetStation;
	int nombreMaxVoiture;
	int seuilAtmoVille;
	int tailleVille;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + budgetStation;
		result = prime * result + idVille;
		result = prime * result + largeurVille;
		result = prime * result + longueurVille;
		result = prime * result + ((nomVille == null) ? 0 : nomVille.hashCode());
		result = prime * result + nombreMaxVoiture;
		result = prime * result + seuilAtmoVille;
		result = prime * result + tailleVille;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ville other = (ville) obj;
		if (budgetStation != other.budgetStation)
			return false;
		if (idVille != other.idVille)
			return false;
		if (largeurVille != other.largeurVille)
			return false;
		if (longueurVille != other.longueurVille)
			return false;
		if (nomVille == null) {
			if (other.nomVille != null)
				return false;
		} else if (!nomVille.equals(other.nomVille))
			return false;
		if (nombreMaxVoiture != other.nombreMaxVoiture)
			return false;
		if (seuilAtmoVille != other.seuilAtmoVille)
			return false;
		if (tailleVille != other.tailleVille)
			return false;
		return true;
	}
	public int getIdVille() {
		return idVille;
	}
	public void setIdVille(int idVille) {
		this.idVille = idVille;
	}
	public String getNomVille() {
		return nomVille;
	}
	public void setNomVille(String nomVille) {
		this.nomVille = nomVille;
	}
	public int getLongueurVille() {
		return longueurVille;
	}
	public void setLongueurVille(int longueurVille) {
		this.longueurVille = longueurVille;
	}
	public int getLargeurVille() {
		return largeurVille;
	}
	public void setLargeurVille(int largeurVille) {
		this.largeurVille = largeurVille;
	}
	public int getBudgetStation() {
		return budgetStation;
	}
	public void setBudgetStation(int budgetStation) {
		this.budgetStation = budgetStation;
	}
	public int getNombreMaxVoiture() {
		return nombreMaxVoiture;
	}
	public void setNombreMaxVoiture(int nombreMaxVoiture) {
		this.nombreMaxVoiture = nombreMaxVoiture;
	}
	public int getSeuilAtmoVille() {
		return seuilAtmoVille;
	}
	public void setSeuilAtmoVille(int seuilAtmoVille) {
		this.seuilAtmoVille = seuilAtmoVille;
	}
	public int getTailleVille() {
		return tailleVille;
	}
	public void setTailleVille(int tailleVille) {
		this.tailleVille = tailleVille;
	}
	
	

}
