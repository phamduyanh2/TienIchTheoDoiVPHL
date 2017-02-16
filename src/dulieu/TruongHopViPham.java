package dulieu;

import java.io.Serializable;
import java.sql.Date;

public class TruongHopViPham implements Serializable{

	private int sTT;
	private Date ngayPhatSinh;
	
	private String thanhPhoHuyen;
	private String xa;
	
	private String nguoiXuLy;	// them moi so voi ben sql
	private String doiXuLy;
	
	private String tuyenDuong;
	private String lyTrinh;
	
	private String nguoiViPham;
	private String noiDungViPham;
	private String loaiViPham;
	
	private Date ngayLapBBKC;
	private Date ngayChuyenGiaoBBKC;
	
	private Date ngayLapBBVPHC;
	private String soBBVPHC;
	private Date ngayChuyenGiaoBBVPHC;
	private String soBBChuyenGiaoBBVPHC;
	
	private Date ngayQuyetDinh;
	private String soQuyetDinh;	
	private float soTien;
	
	private Date ngayLapBBLV;
	private String soBBLV;
	
	private Date ngayLapBBCK;
	private String soBBCK;
	
	private String ketQuaXuLy;
	private String ghiChu;
	
	private String khongThongKe;
	
	private Date ngayThaoDo;
	private Date ngayCuongChe;
	private Date ngayTuyenTruyen;
	
	private int soLanTaiPham;
	private String ghiChuKhac;
	
	
	public TruongHopViPham()
	{
		super();
	}
	
	
	// Tao chon
	
	
		
	
	
	// getter and setter
	
	public TruongHopViPham(int sTT, Date ngayPhatSinh, String thanhPhoHuyen, String xa, String nguoiXuLy,
			String doiXuLy, String tuyenDuong, String lyTrinh, String nguoiViPham, String noiDungViPham,
			String loaiViPham, Date ngayLapBBKC, Date ngayChuyenGiaoBBKC, Date ngayLapBBVPHC, String soBBVPHC,
			Date ngayChuyenGiaoBBVPHC, String soBBChuyenGiaoBBVPHC, Date ngayQuyetDinh, String soQuyetDinh,
			float soTien, Date ngayLapBBLV, String soBBLV, Date ngayLapBBCK, String soBBCK, String ketQuaXuLy,
			String ghiChu, String khongThongKe, Date ngayThaoDo, Date ngayCuongChe, Date ngayTuyenTruyen,
			int soLanTaiPham, String ghiChuKhac) {
		super();
		this.sTT = sTT;
		this.ngayPhatSinh = ngayPhatSinh;
		this.thanhPhoHuyen = thanhPhoHuyen;
		this.xa = xa;
		this.nguoiXuLy = nguoiXuLy;
		this.doiXuLy = doiXuLy;
		this.tuyenDuong = tuyenDuong;
		this.lyTrinh = lyTrinh;
		this.nguoiViPham = nguoiViPham;
		this.noiDungViPham = noiDungViPham;
		this.loaiViPham = loaiViPham;
		this.ngayLapBBKC = ngayLapBBKC;
		this.ngayChuyenGiaoBBKC = ngayChuyenGiaoBBKC;
		this.ngayLapBBVPHC = ngayLapBBVPHC;
		this.soBBVPHC = soBBVPHC;
		this.ngayChuyenGiaoBBVPHC = ngayChuyenGiaoBBVPHC;
		this.soBBChuyenGiaoBBVPHC = soBBChuyenGiaoBBVPHC;
		this.ngayQuyetDinh = ngayQuyetDinh;
		this.soQuyetDinh = soQuyetDinh;
		this.soTien = soTien;
		this.ngayLapBBLV = ngayLapBBLV;
		this.soBBLV = soBBLV;
		this.ngayLapBBCK = ngayLapBBCK;
		this.soBBCK = soBBCK;
		this.ketQuaXuLy = ketQuaXuLy;
		this.ghiChu = ghiChu;
		this.khongThongKe = khongThongKe;
		this.ngayThaoDo = ngayThaoDo;
		this.ngayCuongChe = ngayCuongChe;
		this.ngayTuyenTruyen = ngayTuyenTruyen;
		this.soLanTaiPham = soLanTaiPham;
		this.ghiChuKhac = ghiChuKhac;
	}


	public int getsTT() {
		return sTT;
	}
	public void setsTT(int sTT) {
		this.sTT = sTT;
	}
	public Date getNgayPhatSinh() {
		return ngayPhatSinh;
	}
	public void setNgayPhatSinh(Date ngayPhatSinh) {
		this.ngayPhatSinh = ngayPhatSinh;
	}
	public String getThanhPhoHuyen() {
		return thanhPhoHuyen;
	}
	public void setThanhPhoHuyen(String thanhPhoHuyen) {
		this.thanhPhoHuyen = thanhPhoHuyen;
	}
	public String getXa() {
		return xa;
	}
	public void setXa(String xa) {
		this.xa = xa;
	}
	public String getNguoiXuLy() {
		return nguoiXuLy;
	}
	public void setNguoiXuLy(String nguoiXuLy) {
		this.nguoiXuLy = nguoiXuLy;
	}
	public String getDoiXuLy() {
		return doiXuLy;
	}
	public void setDoiXuLy(String doiXuLy) {
		this.doiXuLy = doiXuLy;
	}
	public String getTuyenDuong() {
		return tuyenDuong;
	}
	public void setTuyenDuong(String tuyenDuong) {
		this.tuyenDuong = tuyenDuong;
	}
	public String getLyTrinh() {
		return lyTrinh;
	}
	public void setLyTrinh(String lyTrinh) {
		this.lyTrinh = lyTrinh;
	}
	public String getNguoiViPham() {
		return nguoiViPham;
	}
	public void setNguoiViPham(String nguoiViPham) {
		this.nguoiViPham = nguoiViPham;
	}
	public String getNoiDungViPham() {
		return noiDungViPham;
	}
	public void setNoiDungViPham(String noiDungViPham) {
		this.noiDungViPham = noiDungViPham;
	}
	public String getLoaiViPham() {
		return loaiViPham;
	}
	public void setLoaiViPham(String loaiViPham) {
		this.loaiViPham = loaiViPham;
	}
	public Date getNgayLapBBKC() {
		return ngayLapBBKC;
	}
	public void setNgayLapBBKC(Date ngayLapBBKC) {
		this.ngayLapBBKC = ngayLapBBKC;
	}
	public Date getNgayChuyenGiaoBBKC() {
		return ngayChuyenGiaoBBKC;
	}
	public void setNgayChuyenGiaoBBKC(Date ngayChuyenGiaoBBKC) {
		this.ngayChuyenGiaoBBKC = ngayChuyenGiaoBBKC;
	}
	public Date getNgayLapBBVPHC() {
		return ngayLapBBVPHC;
	}
	public void setNgayLapBBVPHC(Date ngayLapBBVPHC) {
		this.ngayLapBBVPHC = ngayLapBBVPHC;
	}
	public String getSoBBVPHC() {
		return soBBVPHC;
	}
	public void setSoBBVPHC(String soBBVPHC) {
		this.soBBVPHC = soBBVPHC;
	}
	public Date getNgayChuyenGiaoBBVPHC() {
		return ngayChuyenGiaoBBVPHC;
	}
	public void setNgayChuyenGiaoBBVPHC(Date ngayChuyenGiaoBBVPHC) {
		this.ngayChuyenGiaoBBVPHC = ngayChuyenGiaoBBVPHC;
	}
	public String getSoBBChuyenGiaoBBVPHC() {
		return soBBChuyenGiaoBBVPHC;
	}
	public void setSoBBChuyenGiaoBBVPHC(String soBBChuyenGiaoBBVPHC) {
		this.soBBChuyenGiaoBBVPHC = soBBChuyenGiaoBBVPHC;
	}
	public Date getNgayQuyetDinh() {
		return ngayQuyetDinh;
	}
	public void setNgayQuyetDinh(Date ngayQuyetDinh) {
		this.ngayQuyetDinh = ngayQuyetDinh;
	}
	public String getSoQuyetDinh() {
		return soQuyetDinh;
	}
	public void setSoQuyetDinh(String soQuyetDinh) {
		this.soQuyetDinh = soQuyetDinh;
	}
	public float getSoTien() {
		return soTien;
	}
	public void setSoTien(float soTien) {
		this.soTien = soTien;
	}
	public Date getNgayLapBBLV() {
		return ngayLapBBLV;
	}
	public void setNgayLapBBLV(Date ngayLapBBLV) {
		this.ngayLapBBLV = ngayLapBBLV;
	}
	public String getSoBBLV() {
		return soBBLV;
	}
	public void setSoBBLV(String soBBLV) {
		this.soBBLV = soBBLV;
	}
	public Date getNgayLapBBCK() {
		return ngayLapBBCK;
	}
	public void setNgayLapBBCK(Date ngayLapBBCK) {
		this.ngayLapBBCK = ngayLapBBCK;
	}
	public String getSoBBCK() {
		return soBBCK;
	}
	public void setSoBBCK(String soBBCK) {
		this.soBBCK = soBBCK;
	}
	public String getKetQuaXuLy() {
		return ketQuaXuLy;
	}
	public void setKetQuaXuLy(String ketQuaXuLy) {
		this.ketQuaXuLy = ketQuaXuLy;
	}
	public String getGhiChu() {
		return ghiChu;
	}
	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
	public String getKhongThongKe() {
		return khongThongKe;
	}
	public void setKhongThongKe(String khongThongKe) {
		this.khongThongKe = khongThongKe;
	}
	public Date getNgayThaoDo() {
		return ngayThaoDo;
	}
	public void setNgayThaoDo(Date ngayThaoDo) {
		this.ngayThaoDo = ngayThaoDo;
	}
	public Date getNgayCuongChe() {
		return ngayCuongChe;
	}
	public void setNgayCuongChe(Date ngayCuongChe) {
		this.ngayCuongChe = ngayCuongChe;
	}
	public Date getNgayTuyenTruyen() {
		return ngayTuyenTruyen;
	}
	public void setNgayTuyenTruyen(Date ngayTuyenTruyen) {
		this.ngayTuyenTruyen = ngayTuyenTruyen;
	}
	public int getSoLanTaiPham() {
		return soLanTaiPham;
	}
	public void setSoLanTaiPham(int soLanTaiPham) {
		this.soLanTaiPham = soLanTaiPham;
	}
	public String getGhiChuKhac() {
		return ghiChuKhac;
	}
	public void setGhiChuKhac(String ghiChuKhac) {
		this.ghiChuKhac = ghiChuKhac;
	}
	
	
	
	
}
