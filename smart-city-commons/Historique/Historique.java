package Historique;
import java.sql.Date;

public class Historique {
	int idHistoric;
	Date dateHistoric;
	int indiceReleve;
	String nameQuartier;

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
