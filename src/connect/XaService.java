package connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class XaService {
	
public static Connection connection=KetNoi.getConnect("XPFINAL\\SQLEXPRESS", "HANHLANGATDBJAVA");
	
	
	public static Vector<Xa>LayDSXa()
	{
		Vector<Xa>dsXa=new Vector<Xa>();
		try
		{
			String sql="select * from XA";
			PreparedStatement preStatement=connection.prepareStatement(sql);
			ResultSet result=preStatement.executeQuery();
			
			while (result.next()) 
			{
				Xa xa=new Xa();
				
				xa.setIdXa(result.getInt("ID_XA"));
				xa.setTenXa(result.getString("TEN_XA"));
								
				dsXa.add(xa);
								
			}
			
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dsXa;
	}
	

}
