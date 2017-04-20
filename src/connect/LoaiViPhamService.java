package connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Vector;

import connect.LoaiViPham;

public class LoaiViPhamService {
	
	
		
	public static Connection connection=KetNoi.getConnect("XPFINAL\\SQLEXPRESS", "HANHLANGATDBJAVA");
	
	
	public static Vector<LoaiViPham>LayDSLoaiViPham()
	{
		Vector<LoaiViPham>dsLoaiViPham=new Vector<LoaiViPham>();
		try
		{
			String sql="select * from LOAIVP";
			PreparedStatement preStatement=connection.prepareStatement(sql);
			ResultSet result=preStatement.executeQuery();
			
			while (result.next()) 
			{
				LoaiViPham lvp=new LoaiViPham();
				
				lvp.setsTT(result.getInt("Stt"));
				lvp.setLoaiViPham(result.getString("LoaiViPham"));
								
				dsLoaiViPham.add(lvp);
								
			}
			
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dsLoaiViPham;
	}
	
	// dang nhap lay thong tin
	
	
	

}
