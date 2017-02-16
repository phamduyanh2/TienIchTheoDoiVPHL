package connect;

import java.io.Serializable;

public class NguoiDung implements Serializable {

	private int id;
	private String userName;
	private String passWord;
	private String hoTen;




	public NguoiDung() {
		//super();
	}
	
	
	public NguoiDung(int id, String userName, String passWord, String hoTen) {
		super();
		this.id = id;
		this.userName = userName;
		this.passWord = passWord;
		this.hoTen = hoTen;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getHoTen() {
		return hoTen;
	}
	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}


}
