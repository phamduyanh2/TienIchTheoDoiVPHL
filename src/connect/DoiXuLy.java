package connect;

public class DoiXuLy {
	
	private String idDoi;
	private String tenDoi;
	
	
	public String getIdDoi() {
		return idDoi;
	}
	public void setIdDoi(String idDoi) {
		this.idDoi = idDoi;
	}
	public String getTenDoi() {
		return tenDoi;
	}
	public void setTenDoi(String tenDoi) {
		this.tenDoi = tenDoi;
	}
	
	
	public DoiXuLy(String idDoi, String tenDoi) {
		super();
		this.idDoi = idDoi;
		this.tenDoi = tenDoi;
	}
	
	
	public DoiXuLy() {
		super();
	}
	
	@Override
	public String toString() {
		return this.tenDoi;
	}

	
	
}
