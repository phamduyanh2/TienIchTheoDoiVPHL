package connect;

public class Xa {
	
	private int idXa;
	private String tenXa;
	private String idHuyen;
	
	
	public int getIdXa() {
		return idXa;
	}
	public void setIdXa(int idXa) {
		this.idXa = idXa;
	}
	public String getTenXa() {
		return tenXa;
	}
	public void setTenXa(String tenXa) {
		this.tenXa = tenXa;
	}
	public String getIdHuyen() {
		return idHuyen;
	}
	public void setIdHuyen(String idHuyen) {
		this.idHuyen = idHuyen;
	}
	public Xa(int idXa, String tenXa, String idHuyen) {
		super();
		this.idXa = idXa;
		this.tenXa = tenXa;
		this.idHuyen = idHuyen;
	}
	
	public Xa() {
		super();
	}
	
	@Override
	public String toString() {
		return tenXa;
	}

	
	
	
}
