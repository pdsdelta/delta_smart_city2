package Historique;
import java.sql.Date;

public class Historic {
	int idHistoric;
	Date dateHistoric;
	int indiceReleve;

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
	
	public void gethistodistrict(int idHistoric, Date dateHistoric, int indiceReleve){
		String quartier = ;
		System.out.println("L'historique "+idHistoric+" datant du "+dateHistoric+ "a relevé l'indice ATMO "+indiceReleve+ " dans le quartier "+quartier+" présente dans notre ville");
		}

	public void gethistocity(int idHistoric, Date dateHistoric, int indiceReleve){
		System.out.println("L'historique "+idHistoric+" datant du "+dateHistoric+ "a relevé l'indice ATMO "+indiceReleve+ " dans notre ville ");
	}
}
