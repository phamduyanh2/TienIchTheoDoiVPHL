package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KetNoi {

	// HAM DE TEST KET NOI TRONG QUA TRINH LAM
	
	public static void main(String[] args) {
		Connection conn= getConnect("XPFINAL\\SQLEXPRESS", "HANHLANGATDBJAVA");
			if(conn!=null)
			{
				System.out.println("Kết nối SQL Server thành công KAKA");
			}
			else
			{
				System.out.println("Kết nối SQL Server thất bại");
			}


	}

	
	// HAM KET NOI VOI CO SO DU LIEU BEN SQL SERVER
	
	public static Connection getConnect(String strServer,String strDatabase)
	{
		Connection conn = null;

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String connectionUrl = "jdbc:sqlserver://" + strServer + ":1433;databaseName=" + strDatabase
					+ ";user=da;password=123;";

			conn = DriverManager.getConnection(connectionUrl);
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.toString());
		} catch (ClassNotFoundException cE) {
			System.out.println("Class Not Found Exception: " + cE.toString());
		}
		return conn;
	}
	
}


