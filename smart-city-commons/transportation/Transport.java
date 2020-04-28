package transportation;

public abstract class Transport {
	
	protected int idTransport ;
	protected int typeTransport ;
	
	public Transport(int idTransport, int typeTransport) {
		this.idTransport = idTransport ;
		this.typeTransport = typeTransport ;
	}

	
	protected String getDescription() {
		return "Description du transport: /n";
	}
	
	/*
	 * Setters and Getters of Transport attributes
	 */
	public int getIdTransport() {
		return idTransport;
	}

	public void setIdTransport(int idTransport) {
		this.idTransport = idTransport;
	}

	public int getTypeTransport() {
		return typeTransport;
	}

	public void setTypeTransport(int typeTransport) {
		this.typeTransport = typeTransport;
	}
	

}
