package connect;

public class GhiChu {

private int sTT;
private String ghiChu;

public int getsTT() {
	return sTT;
}

public void setsTT(int sTT) {
	this.sTT = sTT;
}

public String getGhiChu() {
	return ghiChu;
}

public void setGhiChu(String ghiChu) {
	this.ghiChu = ghiChu;
}

public GhiChu(int sTT, String ghiChu) {
	super();
	this.sTT = sTT;
	this.ghiChu = ghiChu;
}

public GhiChu() {
	super();
}

@Override
public String toString() {
	return ghiChu;
}

	
}
