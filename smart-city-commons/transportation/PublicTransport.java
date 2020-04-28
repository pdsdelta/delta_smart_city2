package transportation;

public class PublicTransport extends Transport{

	public PublicTransport(int idTransport, int typeTransport) {
		super(idTransport, typeTransport);
		
	}
	
	public String getDescription() {
		String res = super.getDescription();
		int type = this.getIdTransport();
		switch (type) {
			case 1 :
				res += " Tram "+ this.idTransport;
				break;
			case 2 : 
				res +=" Bus "+ this.idTransport;
				break;
				
				
		}
		return res;
		
	}
	

	

}
