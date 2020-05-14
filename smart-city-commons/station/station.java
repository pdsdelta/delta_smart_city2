package station;

public class station {
	
	int idStation; 
	int numberStation;
	int coutStation;
	int longueurReseau;
	int numberTram;
	int numberLine;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + coutStation;
		result = prime * result + idStation;
		result = prime * result + longueurReseau;
		result = prime * result + numberLine;
		result = prime * result + numberStation;
		result = prime * result + numberTram;
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
		station other = (station) obj;
		if (coutStation != other.coutStation)
			return false;
		if (idStation != other.idStation)
			return false;
		if (longueurReseau != other.longueurReseau)
			return false;
		if (numberLine != other.numberLine)
			return false;
		if (numberStation != other.numberStation)
			return false;
		if (numberTram != other.numberTram)
			return false;
		return true;
	}
	public int getIdStation() {
		return idStation;
	}
	public void setIdStation(int idStation) {
		this.idStation = idStation;
	}
	public int getNumberStation() {
		return numberStation;
	}
	public void setNumberStation(int numberStation) {
		this.numberStation = numberStation;
	}
	public int getCoutStation() {
		return coutStation;
	}
	public void setCoutStation(int coutStation) {
		this.coutStation = coutStation;
	}
	public int getLongueurReseau() {
		return longueurReseau;
	}
	public void setLongueurReseau(int longueurReseau) {
		this.longueurReseau = longueurReseau;
	}
	public int getNumberTram() {
		return numberTram;
	}
	public void setNumberTram(int numberTram) {
		this.numberTram = numberTram;
	}
	public int getNumberLine() {
		return numberLine;
	}
	public void setNumberLine(int numberLine) {
		this.numberLine = numberLine;
	}
}
