package transportation;

public class PrivateTransport extends Transport{

	public PrivateTransport(int idTransport, int typeTransport) {
		super(idTransport, typeTransport);
		
	}
	
	public String getDescription() {
		String res = super.getDescription();
		int type = this.getIdTransport();
		switch (type) {
			case 5 :
				res += " Voiture "+ this.idTransport;
				break;
			case 6 : 
				res +=" Moto "+ this.idTransport;
				break;
				
				
		}
		return res;
		

}
}
