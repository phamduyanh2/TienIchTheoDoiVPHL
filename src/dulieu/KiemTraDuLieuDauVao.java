package dulieu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public  class KiemTraDuLieuDauVao {

	static SimpleDateFormat sdf_ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
	
	
	public static String KiemTraNgayThang2(ResultSet rs, String TenCot) throws SQLException
	{
		String ngaythangnam = null;
		String ngayChuan = "11/03/1999";		
		Date MocSoSanh = null;		
		try {
		MocSoSanh =  sdf_ddMMyyyy.parse(ngayChuan);
		
		Date ngaykiemtra = null;
		String ngaykiemtra2="";
		
		
		ngaykiemtra = rs.getDate(TenCot);
		ngaykiemtra2 = rs.getString(TenCot);
			
		// KIEM TRA XEM CO GIA TRI NULL KHONG - DAU TIEN
		if(ngaykiemtra2==null)
			{
				ngaythangnam="";
			}
			
		else if(ngaykiemtra.before(MocSoSanh))		// KIEM TRA XEM CO NAM O NGAY THANG SAU KHONG
				{
			ngaythangnam="";
				}
			
		else
		{
			ngaythangnam=sdf_ddMMyyyy.format(ngaykiemtra);
		}
		
		
		} 
		catch (ParseException e)
		{
		e.printStackTrace();
		} 
					
				
		return ngaythangnam;
	}
	

	public static String KiemTraNgayThang(java.sql.Date ngaykiemtra)
	{
		String ngayChuan = "11/03/1999";		
		Date MocSoSanh = null;		
		try {
		MocSoSanh =  sdf_ddMMyyyy.parse(ngayChuan);
		
		} catch (ParseException e) {
		e.printStackTrace();
		} 
		
	
		
		String ngaythangnam;
		
		if(ngaykiemtra.before(MocSoSanh)||ngaykiemtra==null) // dieu kien thu 2 0 day khong co tac dung
		{
			ngaythangnam="";
		}
		else
		{
			ngaythangnam=sdf_ddMMyyyy.format(ngaykiemtra);
		}
				
		return ngaythangnam;
	}
	
	// SU DUNG HAM NAY DE RUT GON HAM LAY DU LIEU 
	
	
	
	public static boolean isValidDate(String dateToValidate)
    {
		
		String dateFormat = "dd/MM/yyyy";
        // Where the date is blank, 
        //it will be valid, so you need to handle it.
        // Trường hợp ngày tháng là trống thì nó sẽ là hợp lệ, 
        // vì vậy cần phải xử lý nó.
        if(dateToValidate == null)
        {
            return false;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        // Thông thường dữ liệu đầu vào nếu không hợp lệ,
        // Java Date sẽ tự đông chuyển đổi lại cho hợp lệ,
        // Vì vây ta cần tắt chức năng này đi để dữ liệu được kiểm tra đúng đắn.
        // Specify whether or not date/time parsing is to be lenient. With lenient parsing, 
        // the parser may use heuristics to interpret inputs 
        // that do not precisely match this object's format. With strict parsing, 
        // inputs must match this object's format.
        sdf.setLenient(false);
        
        try 
        {
            //If not valid, it will to throw ParseException.
            //Trường hợp không hợp lệ, nó sẽ chuyển sang xử lý ngoại lệ.
            Date date = sdf.parse(dateToValidate);
            //System.out.println(date);
        
        } 
        catch (ParseException e) 
        {        
            //e.printStackTrace();
            return false;
        }
        
        return true;
    }
	
	
//	public static Date kiemTraVaChuyenSangNgayThang(String ngayThangCanChuyen)
//    {
//			if(isValidDate(ngayThangCanChuyen))
//			{		
//				try
//				{
//		Date NgayThanhChuyen = new SimpleDateFormat("dd/MM/yyyy").parse(ngayThangCanChuyen);
//		return NgayThanhChuyen;
//				}
//				catch(Exception ex)
//				{
//					ex.printStackTrace();
//				}
//			}
//			else 
//				{
//				JOptionPane.showMessageDialog(null, " Nhap dinh dang ngay thang dd/MM/yyyy");
//				return ;
//				}
//			
//    }
	
}
