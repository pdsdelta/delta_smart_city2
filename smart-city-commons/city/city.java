package city;

public class city {
	
	int idCity; 
	String nomCity;
	int longueurCity;
	int largeurCity;
	int budgetStation;
	int nombreMaxVoiture;
	int seuilAtmoCity;
	int tailleCity;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + budgetStation;
		result = prime * result + idCity;
		result = prime * result + largeurCity;
		result = prime * result + longueurCity;
		result = prime * result + ((nomCity == null) ? 0 : nomCity.hashCode());
		result = prime * result + nombreMaxVoiture;
		result = prime * result + seuilAtmoCity;
		result = prime * result + tailleCity;
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
		city other = (city) obj;
		if (budgetStation != other.budgetStation)
			return false;
		if (idCity != other.idCity)
			return false;
		if (largeurCity != other.largeurCity)
			return false;
		if (longueurCity != other.longueurCity)
			return false;
		if (nomCity == null) {
			if (other.nomCity != null)
				return false;
		} else if (!nomCity.equals(other.nomCity))
			return false;
		if (nombreMaxVoiture != other.nombreMaxVoiture)
			return false;
		if (seuilAtmoCity != other.seuilAtmoCity)
			return false;
		if (tailleCity != other.tailleCity)
			return false;
		return true;
	}
	public int getIdCity() {
		return idCity;
	}
	public void setIdCity(int idCity) {
		this.idCity = idCity;
	}
	public String getNomCity() {
		return nomCity;
	}
	public void setNomCity(String nomCity) {
		this.nomCity = nomCity;
	}
	public int getLongueurCity() {
		return longueurCity;
	}
	public void setLongueurCity(int longueurCity) {
		this.longueurCity = longueurCity;
	}
	public int getLargeurCity() {
		return largeurCity;
	}
	public void setLargeurCity(int largeurCity) {
		this.largeurCity = largeurCity;
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
	public int getSeuilAtmoCity() {
		return seuilAtmoCity;
	}
	public void setSeuilAtmoCity(int seuilAtmoCity) {
		this.seuilAtmoCity = seuilAtmoCity;
	}
	public int getTailleCity() {
		return tailleCity;
	}
	public void setTailleCity(int tailleCity) {
		this.tailleCity = tailleCity;
	}


}
