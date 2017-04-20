package connect;

public class LoaiViPham {
	
	private int sTT;
	private String loaiViPham;
	
	
	
	
	public LoaiViPham(int sTT, String loaiViPham) {
		super();
		this.sTT = sTT;
		this.loaiViPham = loaiViPham;
	}
	
		
	
	
	public LoaiViPham() {
		super();
	}


	public int getsTT() {
		return sTT;
	}
	public void setsTT(int sTT) {
		this.sTT = sTT;
	}
	public String getLoaiViPham() {
		return loaiViPham;
	}
	public void setLoaiViPham(String loaiViPham) {
		this.loaiViPham = loaiViPham;
	}


// CHU Y KHONG CO HAM NAY HIEN THI RA COMBO BOX KHONG DUNG

	@Override
	public String toString() {
		return this.loaiViPham;
	}
	

}
