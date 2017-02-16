package connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class DoiXuLyService {
	
public static Connection connection=KetNoi.getConnect("XPFINAL\\SQLEXPRESS", "HANHLANGATDBJAVA");
	
	
	public static Vector<DoiXuLy>LayDSDoiXuLy()
	{
		Vector<DoiXuLy>dsDoiXuLy=new Vector<DoiXuLy>();
		try
		{
			String sql="select * from DOIXL";
			PreparedStatement preStatement=connection.prepareStatement(sql);
			ResultSet result=preStatement.executeQuery();
			
			while (result.next()) 
			{
				DoiXuLy dxl=new DoiXuLy();
										
				dxl.setIdDoi(result.getString("ID_DOI"));
				dxl.setTenDoi(result.getString("TenDoi"));
								
				dsDoiXuLy.add(dxl);
								
			}
			
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dsDoiXuLy;
	}
	
}
