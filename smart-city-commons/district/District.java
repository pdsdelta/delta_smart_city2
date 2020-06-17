package district;

public class District {
	
	int id;
	String name;
	int seuilquartieratmo;
	int etatalerte;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + etatalerte;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + seuilquartieratmo;
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
		District other = (District) obj;
		if (etatalerte != other.etatalerte)
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (seuilquartieratmo != other.seuilquartieratmo)
			return false;
		return true;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSeuilQuartierATMO() {
		return seuilquartieratmo;
	}
	public void setSeuilQuartierATMO(int seuilQuartierATMO) {
		this.seuilquartieratmo = seuilQuartierATMO;
	}
	public int getEtatalterte() {
		return etatalerte;
	}
	public void setEtatalterte(int etat) {
		this.etatalerte = etat;
	}

}
