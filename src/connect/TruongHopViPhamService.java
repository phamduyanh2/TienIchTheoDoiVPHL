package connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dulieu.TruongHopViPham;

public class TruongHopViPhamService {
	
	
		
	public static Connection connection=KetNoi.getConnect("XPFINAL\\SQLEXPRESS", "HANHLANGATDBJAVA");
	
	// khac cac truong hop khac la su dung VEC
	public static ArrayList<TruongHopViPham>LayDSTruongHopViPham()
	{
		ArrayList<TruongHopViPham>dsTruongHopViPham=new ArrayList<TruongHopViPham>();
		try
		{
			String sql="select * from NHAPTHONGTIN";
			PreparedStatement preStatement=connection.prepareStatement(sql);
			ResultSet result=preStatement.executeQuery();
			
			while (result.next()) 
			{
				TruongHopViPham thvp=new TruongHopViPham();
										
				thvp.setsTT(result.getInt("Stt"));
				
				thvp.setNgayPhatSinh(result.getDate("NgayPhatSinh"));
								
				dsTruongHopViPham.add(thvp);
								
			}
			
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dsTruongHopViPham;
	}
	
		
	

}
