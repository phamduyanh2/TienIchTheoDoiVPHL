package connect;

import java.util.Vector;

public class Huyen {
	
	private String idHuyen;
	private String tenHuyen;
	private Vector<Xa>Xa;
	
	
	public Vector<Xa> getXa() {
		return Xa;
	}
	public void setXa(Vector<Xa> xa) {
		Xa = xa;
	}
	public String getIdHuyen() {
		return idHuyen;
	}
	public void setIdHuyen(String idHuyen) {
		this.idHuyen = idHuyen;
	}
	public String getTenHuyen() {
		return tenHuyen;
	}
	public void setTenHuyen(String tenHuyen) {
		this.tenHuyen = tenHuyen;
	}
	public Huyen(String idHuyen, String tenHuyen) {
		super();
		this.idHuyen = idHuyen;
		this.tenHuyen = tenHuyen;
	}
	public Huyen() {
		super();
	}
	@Override
	public String toString() {
		return  tenHuyen;
	}
	

}
