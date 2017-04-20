package connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import connect.TuyenDuong;

public class TuyenDuongService {
	
	
		
	public static Connection connection=KetNoi.getConnect("XPFINAL\\SQLEXPRESS", "HANHLANGATDBJAVA");
	
	
	public static Vector<TuyenDuong>LayDSTuyenDuong()
	{
		Vector<TuyenDuong>dsTuyenDuong=new Vector<TuyenDuong>();
		try
		{
			String sql="select * from DUONG";
			PreparedStatement preStatement=connection.prepareStatement(sql);
			ResultSet result=preStatement.executeQuery();
			
			while (result.next()) 
			{
				TuyenDuong td=new TuyenDuong();
										
				td.setsTT(result.getInt("Stt"));
				td.setTenDuong(result.getString("TenDuong"));
								
				dsTuyenDuong.add(td);
								
			}
			
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dsTuyenDuong;
	}
	
		
	

}
