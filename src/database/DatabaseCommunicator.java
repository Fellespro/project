package database;

import java.sql.*;
import java.util.Properties;

/*
 * Klassen kobler seg til en mySQL-database på NTNUs server
 * jdbcDriver=com.mysql.jdbc.Driver
 * url=jdbc:mysql://localhost/testDB
 * user=olestes_fp
 * password=swag
 */
public class DatabaseCommunicator {
	
	private static String jdbcDriver; // String containing the driver Class name
	private static String url; // Address to the database
	private static Connection conn;
	private static String user;
	private static String password;
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		jdbcDriver = "com.mysql.jdbc.Driver";
		url = "jdbc:mysql://mysql.stud.ntnu.no/olestes_fpdb";
		user = "olestes_fp";
		password="swag";
		Properties info = new Properties();
		info.setProperty("user", user);
		info.setProperty("password", password);
		conn = DriverManager.getConnection(url, info);
		System.out.println("Tilkobling opprettet");
		conn.close();
	}
}
