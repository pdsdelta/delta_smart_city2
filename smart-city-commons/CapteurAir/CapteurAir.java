package CapteurAir;
import java.sql.Date;

public class CapteurAir {
	int idCapteur;
	String nameCapteur;
	Date dateReleve;
	int IndiceATMO;
	int tempsReleve;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + IndiceATMO;
		result = prime * result + ((dateReleve == null) ? 0 : dateReleve.hashCode());
		result = prime * result + idCapteur;
		result = prime * result + ((nameCapteur == null) ? 0 : nameCapteur.hashCode());
		result = prime * result + tempsReleve;
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
		if (IndiceATMO != other.IndiceATMO)
			return false;
		if (dateReleve == null) {
			if (other.dateReleve != null)
				return false;
		} else if (!dateReleve.equals(other.dateReleve))
			return false;
		if (idCapteur != other.idCapteur)
			return false;
		if (nameCapteur == null) {
			if (other.nameCapteur != null)
				return false;
		} else if (!nameCapteur.equals(other.nameCapteur))
			return false;
		if (tempsReleve != other.tempsReleve)
			return false;
		return true;
	}

	public int getId() {
		return idCapteur;
	}
	
	public void setId(int id) {
		this.idCapteur = id;
	}
	public String getName() {
		return nameCapteur;
	}
	public void setName(String name) {
		this.nameCapteur = name;
	}
	
	public Date getDate() {
		return dateReleve;
	}
	public void setDate(Date date) {
		this.dateReleve = date;
	}

	public int getIndice() {
		return IndiceATMO;
	}
	public void setIndice(int indice) {
		this.IndiceATMO = indice;
	}
	
	public int getTempsReleve() {
		return tempsReleve;
	}
	public void setTempsReleve(int time) {
		this.tempsReleve = time;
	}
	
	
	
	
}
