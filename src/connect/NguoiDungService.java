package connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import connect.NguoiDung;

public class NguoiDungService {
	
	
	//public static Connection connection=MSAccessConnection.getConnection();
	
	public static Connection connection=KetNoi.getConnect("XPFINAL\\SQLEXPRESS", "HANHLANGATDBJAVA");
	
	
	public static ArrayList<NguoiDung>LayToanBoNguoiDung()
	{
		ArrayList<NguoiDung>dsNguoiDung=new ArrayList<NguoiDung>();
		try
		{
			String sql="select * from NguoiDung";
			PreparedStatement preStatement=connection.prepareStatement(sql);
			ResultSet result=preStatement.executeQuery();
			
			while (result.next()) 
			{
				NguoiDung nd=new NguoiDung();
				
				nd.setId(result.getInt("Id"));
				nd.setUserName(result.getString("UserName"));
				nd.setPassWord(result.getString("PassWord"));
				nd.setHoTen(result.getString("HoTen"));
				dsNguoiDung.add(nd);
								
			}
			
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dsNguoiDung;
	}
	
	public static NguoiDung dangNhap(String useName, String passWord)
	{
		NguoiDung nd=null;
		try
		{
			String sql="select* from NguoiDung where UserName=? and PassWord=?";
			PreparedStatement pre=connection.prepareStatement(sql);
			pre.setString(1, useName);
			pre.setString(2, passWord);
			ResultSet result=pre.executeQuery();			
			if(result.next())
			{
				nd=new NguoiDung();
				nd.setId(result.getInt("Id"));
				nd.setUserName(result.getString("UserName"));
				nd.setPassWord(result.getString("PassWord"));
				nd.setHoTen(result.getString("HoTen"));
			
			}
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return nd;
	}
	

}
