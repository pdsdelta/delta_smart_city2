package city;

public class city {
	
	int idCity; 
	String nameCity;
	int longueurCity;
	int largeurCity;
	int budgetStation;
	int nombreMaxVoiture;
	int seuilAtmoCity;
	double tailleCity;
	int numberTram;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + budgetStation;
		result = prime * result + idCity;
		result = prime * result + largeurCity;
		result = prime * result + longueurCity;
		result = prime * result + ((nameCity == null) ? 0 : nameCity.hashCode());
		result = prime * result + nombreMaxVoiture;
		result = prime * result + numberTram;
		result = prime * result + seuilAtmoCity;
		long temp;
		temp = Double.doubleToLongBits(tailleCity);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (nameCity == null) {
			if (other.nameCity != null)
				return false;
		} else if (!nameCity.equals(other.nameCity))
			return false;
		if (nombreMaxVoiture != other.nombreMaxVoiture)
			return false;
		if (numberTram != other.numberTram)
			return false;
		if (seuilAtmoCity != other.seuilAtmoCity)
			return false;
		if (Double.doubleToLongBits(tailleCity) != Double.doubleToLongBits(other.tailleCity))
			return false;
		return true;
	}
	public int getIdCity() {
		return idCity;
	}
	public void setIdCity(int idCity) {
		this.idCity = idCity;
	}
	public String getNameCity() {
		return nameCity;
	}
	public void setNameCity(String nameCity) {
		this.nameCity = nameCity;
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
	public double getTailleCity() {
		return tailleCity;
	}
	public void setTailleCity(double tailleCity) {
		this.tailleCity = tailleCity;
	}
	public int getNumberTram() {
		return numberTram;
	}
	public void setNumberTram(int numberTram) {
		this.numberTram = numberTram;
	}
}
