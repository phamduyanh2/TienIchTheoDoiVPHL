package connect;

public class KhongThongKe {

	
	private int sTT;
private String khongThongKe;


public int getsTT() {
	return sTT;
}

public void setsTT(int sTT) {
	this.sTT = sTT;
}

public String getKhongThongKe() {
	return khongThongKe;
}


public void setKhongThongKe(String khongThongKe) {
	this.khongThongKe = khongThongKe;
}

public KhongThongKe(int sTT, String khongThongKe) {
	super();
	this.sTT = sTT;
	this.khongThongKe = khongThongKe;
}

public KhongThongKe() {
	super();
}

@Override
public String toString() {
	return  khongThongKe;
}


	
}
