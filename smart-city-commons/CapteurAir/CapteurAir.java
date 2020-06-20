package CapteurAir;

public class CapteurAir {
	int idcapteur;
	String datereleve;
	int indiceatmo;
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datereleve == null) ? 0 : datereleve.hashCode());
		result = prime * result + idcapteur;
		result = prime * result + indiceatmo;
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
		CapteurAir other = (CapteurAir) obj;
		if (datereleve == null) {
			if (other.datereleve != null)
				return false;
		} else if (!datereleve.equals(other.datereleve))
			return false;
		if (idcapteur != other.idcapteur)
			return false;
		if (indiceatmo != other.indiceatmo)
			return false;
		return true;
	}

	public int getId() {
		return idcapteur;
	}
	
	public void setId(int id) {
		this.idcapteur = id;
	}
	
	public String getDate() {
		return datereleve;
	}
	public void setDate(String date) {
		this.datereleve = date;
	}

	public int getIndice() {
		return indiceatmo;
	}
	public void setIndice(int indice) {
		this.indiceatmo = indice;
	}
}
