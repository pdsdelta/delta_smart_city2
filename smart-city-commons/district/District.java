package district;

public class District {
	
	int id;
	String name;
	int seuilQuartierATMO;
	int Etatalterte;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Etatalterte;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + seuilQuartierATMO;
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
		if (Etatalterte != other.Etatalterte)
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (seuilQuartierATMO != other.seuilQuartierATMO)
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
		return seuilQuartierATMO;
	}
	public void setSeuilQuartierATMO(int seuilQuartierATMO) {
		this.seuilQuartierATMO = seuilQuartierATMO;
	}
	public int getEtatalterte() {
		return Etatalterte;
	}
	public void setEtatalterte(int etatalterte) {
		Etatalterte = etatalterte;
	}

}
