package connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class GhiChuService {
	
	
	public static Connection connection=KetNoi.getConnect("XPFINAL\\SQLEXPRESS", "HANHLANGATDBJAVA");
	
	public static Vector<GhiChu>LayDSGhiChu()
	{
		Vector<GhiChu>dsGhiChu=new Vector<GhiChu>();
		try
		{
			String sql="select * from GHICHU";
			PreparedStatement preStatement=connection.prepareStatement(sql);
			ResultSet result=preStatement.executeQuery();
			
			while (result.next()) 
			{
				GhiChu gc=new GhiChu();
				
				gc.setsTT(result.getInt("STT"));
				gc.setGhiChu(result.getString("GhiChu"));
								
				dsGhiChu.add(gc);
								
			}
			
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dsGhiChu;
	}
	
}
