package database;

import java.sql.*;
import java.util.Properties;

/*
 * Klassen kobler seg til en mySQL-database på NTNUs server
 * jdbcDriver=com.mysql.jdbc.Driver
 * url=jdbc:mysql://localhost/testDB
 * user=olestes_fp
 * password=swag
 * 
 * Hvordan bruke:
 * Opprett et DatabaseCommunicator-objekt
 * DC.initialize();
 * Nå kan du bruke DC.get/set for å hente ut/sette det du vil.
 * 
 * Husk å avslutte med:
 * DC.close()!
 * 
 */
public class DatabaseCommunicator {
	
	private static String jdbcDriver; // String containing the driver Class name
	private String url; // Address to the database
	private String user;
	private String password;
	
	private Connection conn;
	
	public DatabaseCommunicator(){
		jdbcDriver = "com.mysql.jdbc.Driver"; // String containing the driver Class name
		url = "jdbc:mysql://mysql.stud.ntnu.no/olestes_fpdb"; // Address to the database
		user = "olestes_fp";
		password = "swag";
	}
	
	public void initialize(){
		try {
			//Koble til driver (?)
			Class.forName(jdbcDriver);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException... RIOT!");
			e.printStackTrace();
		}
		try {
			//Opprett tilkobling
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("Tilkobling opprettet");
		} catch (SQLException e) {
			System.out.println("Nicht Güt! SQLException! Kunne ikke koble til...");
			e.printStackTrace();
		}
		
	}
	
	public void close(){
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Epic fail! Kunne ikke lukke tilkoblingen...");
			e.printStackTrace();
		}
	}
	
	public boolean validateLogIn(String username, String password) throws SQLException{
		String query=String.format("Select * from Ansatt where brukernavn='%s' and passord='%s'",username,password);
		ResultSet rs = this.makeSingleQuery(query);
		boolean match = false;
		while(rs.next()){
			match = username.equals(rs.getString(1));
			match = match && password.equals(rs.getString(2));
		}
		return match;
	}
	
	public ResultSet makeSingleQuery(String sql) throws SQLException {
		Statement st = conn.createStatement();
		return st.executeQuery(sql);
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{		
		DatabaseCommunicator dc = new DatabaseCommunicator();
		dc.initialize();
		System.out.println(dc.validateLogIn("abdull", "abcd"));
		dc.close();
	}
}
