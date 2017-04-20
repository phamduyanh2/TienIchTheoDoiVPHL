package connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import dulieu.TruongHopViPham;

public class TruongHopViPhamService2 {
	
	
		
	public static Connection connection=KetNoi.getConnect("XPFINAL\\SQLEXPRESS", "HANHLANGATDBJAVA");
	
	// khac cac truong hop khac la su dung VEC
	public static Vector<Object> LayDSTruongHopViPham()
	{
		
		
		Vector<Object>vecTruongHopViPham=new Vector<Object>();
		//vec.add(ma);
		
		try
		{
			String sql="select * from NHAPTHONGTIN";
			PreparedStatement preStatement=connection.prepareStatement(sql);
			ResultSet result=preStatement.executeQuery();
			
			while (result.next()) 
			{
				//TruongHopViPham thvp=new TruongHopViPham();
										
				vecTruongHopViPham.add(result.getInt("Stt"));
				
				vecTruongHopViPham.add(result.getDate("NgayPhatSinh"));
																	
			}
			
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return vecTruongHopViPham;
	}
	
		
	

}
