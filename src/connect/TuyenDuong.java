package connect;

public class TuyenDuong {

	private int sTT;
	private String tenDuong;
	private DoiXuLy doiXuLy;
	
	
	public int getsTT() {
		return sTT;
	}
	public void setsTT(int sTT) {
		this.sTT = sTT;
	}
	public String getTenDuong() {
		return tenDuong;
	}
	public void setTenDuong(String tenDuong) {
		this.tenDuong = tenDuong;
	}
	public DoiXuLy getDoiXuLy() {
		return doiXuLy;
	}
	public void setDoiXuLy(DoiXuLy doiXuLy) {
		this.doiXuLy = doiXuLy;
	}
	
	
	public TuyenDuong() {
		super();
	}
	
	
	public TuyenDuong(int sTT, String tenDuong) {
		super();
		this.sTT = sTT;
		this.tenDuong = tenDuong;
				
	}
	
	@Override
	public String toString() {
		return this.tenDuong;
	}
	
	
	
	
}
