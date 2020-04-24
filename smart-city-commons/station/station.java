package station;

public class station {
	
	int idStation; 
	String nameStation;
	int positionStationX; 
	int positionStationY;
	int coutStation;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + coutStation;
		result = prime * result + idStation;
		result = prime * result + ((nameStation == null) ? 0 : nameStation.hashCode());
		result = prime * result + positionStationX;
		result = prime * result + positionStationY;
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
		if (nameStation == null) {
			if (other.nameStation != null)
				return false;
		} else if (!nameStation.equals(other.nameStation))
			return false;
		if (positionStationX != other.positionStationX)
			return false;
		if (positionStationY != other.positionStationY)
			return false;
		return true;
	}
	
	public int getIdStation() {
		return idStation;
	}
	public void setIdStation(int idStation) {
		this.idStation = idStation;
	}
	public String getNameStation() {
		return nameStation;
	}
	public void setNameStation(String nameStation) {
		this.nameStation = nameStation;
	}
	public int getPositionStationX() {
		return positionStationX;
	}
	public void setPositionStationX(int positionStationX) {
		this.positionStationX = positionStationX;
	}
	public int getPositionStationY() {
		return positionStationY;
	}
	public void setPositionStationY(int positionStationY) {
		this.positionStationY = positionStationY;
	}
	public int getCoutStation() {
		return coutStation;
	}
	public void setCoutStation(int coutStation) {
		this.coutStation = coutStation;
	}
}
