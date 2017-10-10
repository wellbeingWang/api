package com.fanmila.util.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;


public class MysqlConnectionUtil {

	private static String driverName = "com.mysql.jdbc.Driver";
	private static Connection connection = null;
	
	public static Connection getConnection(String ServerIp,
			String ServerPort, String databaseName, String userName, String password) {
		if (ServerIp == null || "".equals(ServerIp)
				|| ServerPort == null || "".equals(ServerPort)) {
			return null;
		}
		if (databaseName == null || "".equals(databaseName)) {
			return null;
		}
//		String hiveUrl = "jdbc:hive://" + hiveServerIp + ":" + hiveServerPort+ "/" + databaseName;
		String connUrl = "jdbc:mysql://" + ServerIp + ":" + ServerPort
				+ "/" + databaseName;
		if (null == connection) {
			try {
				System.out.println("    开始连接到： " + connUrl+" "+new Date());
				Class.forName(driverName);
				connection = DriverManager.getConnection(connUrl, userName,
						password);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
	
}
