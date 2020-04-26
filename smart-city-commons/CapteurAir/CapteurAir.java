package CapteurAir;
import java.sql.Date;

public class CapteurAir {
	int idCapteur;
	String nameCapteur;
	Date dateReleve;
	int IndiceATMO;
	int tempsReleve;
	
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
