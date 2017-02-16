package connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class KhongThongKeService {

public static Connection connection=KetNoi.getConnect("XPFINAL\\SQLEXPRESS", "HANHLANGATDBJAVA");
	
	
	public static Vector<KhongThongKe>LayDSKhongThongKe()
	{
		Vector<KhongThongKe>dsKhongThongKe=new Vector<KhongThongKe>();
		try
		{
			String sql="select * from KHONGTINH";
			PreparedStatement preStatement=connection.prepareStatement(sql);
			ResultSet result=preStatement.executeQuery();
			
			while (result.next()) 
			{
				KhongThongKe ktk=new KhongThongKe();
				
				ktk.setsTT(result.getInt("STT"));
				ktk.setKhongThongKe(result.getString("KhongThongKe"));
								
				dsKhongThongKe.add(ktk);
								
			}
			
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dsKhongThongKe;
	}
	
	// dang nhap lay thong tin
	
	
	
	
}
