package connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class HuyenService {
	
	
public static Connection connection=KetNoi.getConnect("XPFINAL\\SQLEXPRESS", "HANHLANGATDBJAVA");
	
	
	public static Vector<Huyen>LayDSHuyen()
	{
		Vector<Huyen>dsHuyen=new Vector<Huyen>();
		try
		{
			String sql="select * from HUYEN";
			PreparedStatement preStatement=connection.prepareStatement(sql);
			ResultSet result=preStatement.executeQuery();
			
			while (result.next()) 
			{
				Huyen huyen=new Huyen();
				
				huyen.setIdHuyen(result.getString("ID_HUYEN"));
				huyen.setTenHuyen(result.getString("TEN_HUYEN"));
								
				dsHuyen.add(huyen);
								
			}
			
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dsHuyen;
	}
	
	// dang nhap lay thong tin
	

}
