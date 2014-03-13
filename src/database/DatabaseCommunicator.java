package database;

import java.sql.*;
import java.util.Properties;

import utilities.Utilities;

import modell.Dato;
import modell.Moterom;
import modell.Person;
import modell.Tid;

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
 * OBS! OBS!
 * DatabaseCommunicator er veldig dum. Den sjekker ikke om avtaler o.l. som skal legges
 * inn i databasen har gyldige data. Går utifra at modellen garanterer at alt er korrekt!
 * 
 * 
 */
public class DatabaseCommunicator {
	
	private static String jdbcDriver; // String containing the driver Class name
	private String url; // Address to the database
	private String user;
	private String password;
	
	private Connection conn;
	
	
	//Tilkoblingsinfo blir satt her. Hardkodet foreløbig
	public DatabaseCommunicator(){
		jdbcDriver = "com.mysql.jdbc.Driver"; // String containing the driver Class name
		url = "jdbc:mysql://mysql.stud.ntnu.no/olestes_fpdb"; // Address to the database
		user = "olestes_fp";
		password = "swag";
	}
	
	//Oppretter tilkobling til databasen
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
	
	//Lukker tilkobling til databasen
	public void close(){
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Epic fail! Kunne ikke lukke tilkoblingen...");
			e.printStackTrace();
		}
	}
	
	/**
	 *
	 * Used for SELECT queries
	 *
	 *
	 * @param sql
	 *            The query in SQL.No semicolon (;) is needed
	 * @return A result set containing the data.
	 * @throws SQLException
	 */
	public ResultSet makeSingleQuery(String sql) throws SQLException {
		Statement st = conn.createStatement();
		return st.executeQuery(sql);
	}
	
	/**
	 * Used for single or few insertions, deletions and updates
	 *
	 * @param sql
	 *            The query in SQL.No semicolon (;) is needed.
	 * @throws SQLException
	 */
	public void makeSingleUpdate(String sql) throws SQLException {
		Statement st = conn.createStatement();
		st.executeUpdate(sql);
	}
	
	//Krav 1 - Logge på
	//Tar inn brukernavn og passord og sjekker om det finnes et brukernavn med tilhørende
	//passord i databasen.
	public boolean validateLogIn(String username, String password) throws SQLException{
		String query="Select * from Ansatt where brukernavn='"+username+"' and passord='"+password+"'";
		ResultSet rs = this.makeSingleQuery(query); //Utfør spørring og motta resultat
		boolean match = false; //Inntil motbevist
		while(rs.next()){//Sjekk resultet av spørringen
			match = username.equals(rs.getString(1));
			match = match && password.equals(rs.getString(2));
		}
		return match;
	}
	
	//Krav 2 - Legge inn avtale
	//sted(moterom) er en String her. Dersom et Moterom skal brukes, send Moterom.romid som en String.
	//Om det ikke skal settes et Moterom for avtalen: lag en beskrivende String.
	public void insertAppointment(Dato avtaledato, Tid starttid, Tid sluttid, Tid altstart, Tid varighet, 
			String beskrivelse, Moterom rom, Person admin, int antallDeltagere) throws SQLException{
		String sistEndret = Utilities.getCurrentDateTime();
		
		//MERK: AvtaleID har autoincrement
		String query = "INSERT INTO Avtale VALUES " + 
		"(0, "+avtaledato.toString()+", "+starttid.toString()+", "+sluttid.toString()+", "+
				altstart.toString()+", '"+beskrivelse+"', "+Utilities.getCurrentDateTime()+", "+
				antallDeltagere+", '"+admin.getUsername()+"', "+rom.getRomID()+")";
		
		makeSingleUpdate(query);
		
	}
	
	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{		
		DatabaseCommunicator dc = new DatabaseCommunicator();
		dc.initialize();
		System.out.println(dc.validateLogIn("abdull", "abcd"));
		
		Dato d = new Dato();
		Tid t = new Tid();
		Tid v = new Tid(2,0,0);
		Moterom r = new Moterom();
		Person p = new Person("abdull");
		dc.insertAppointment(d, t, t, t, v, "random avtale", r, p, 5);
		
		
		
		
		dc.close();
		
	}
}
