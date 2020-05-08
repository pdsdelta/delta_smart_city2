package Historique;
import java.sql.Date;

public class Historique {
	int idHistoric;
	Date dateHistoric;
	int indiceReleve;
	String nameQuartier;

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateHistoric == null) ? 0 : dateHistoric.hashCode());
		result = prime * result + idHistoric;
		result = prime * result + indiceReleve;
		result = prime * result + ((nameQuartier == null) ? 0 : nameQuartier.hashCode());
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
		Historique other = (Historique) obj;
		if (dateHistoric == null) {
			if (other.dateHistoric != null)
				return false;
		} else if (!dateHistoric.equals(other.dateHistoric))
			return false;
		if (idHistoric != other.idHistoric)
			return false;
		if (indiceReleve != other.indiceReleve)
			return false;
		if (nameQuartier == null) {
			if (other.nameQuartier != null)
				return false;
		} else if (!nameQuartier.equals(other.nameQuartier))
			return false;
		return true;
	}

	public int getId() {
		return idHistoric;
	}
	
	public void setId(int id) {
		this.idHistoric = id;
	}

	public Date getDate() {
		return dateHistoric;
	}
	public void setDate(Date date) {
		this.dateHistoric = date;
	}
	
	public int getIndice() {
		return indiceReleve;
	}
	public void setIndice(int indiceATMO) {
		this.indiceReleve = indiceATMO;
	}
	
	public String getNamequartier() {
		return nameQuartier;
	}
	public void setNamequartier(String name) {
		this.nameQuartier = name;
	}
	
	public void gethistodistrict(int idHistoric, Date dateHistoric, int indiceReleve, String nameQuartier){
		System.out.println("L'historique "+idHistoric+" datant du "+dateHistoric+ "a relevé l'indice ATMO "+indiceReleve+ " dans le quartier "+nameQuartier+" présente dans notre ville");
		}

	public void gethistocity(int idHistoric, Date dateHistoric, int indiceReleve){
		System.out.println("L'historique "+idHistoric+" datant du "+dateHistoric+ "a relevé l'indice ATMO "+indiceReleve+ " dans notre ville ");
	}
}
