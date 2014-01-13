package edu.cloud.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBUtils {
	private static String DriverClass = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/yahoodataserviceall";
	private static String user = "sigsit";
	private static String pwd = "";
	private static Connection conn = null;
	

	public static Connection getConnection(){
		try {
			Class.forName(DriverClass);
			conn = DriverManager.getConnection(url, user, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void free(ResultSet rs, PreparedStatement ps, Connection conn) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}


