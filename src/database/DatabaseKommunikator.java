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
public class DatabaseKommunikator {
	
	private static String jdbcDriver; // String containing the driver Class name
	private String url; // Address to the database
	private String bruker;
	private String passord;
	
	private Connection conn;
	
	
	//Tilkoblingsinfo blir satt her. Hardkodet foreløbig
	public DatabaseKommunikator(){
		jdbcDriver = "com.mysql.jdbc.Driver"; // String containing the driver Class name
		url = "jdbc:mysql://mysql.stud.ntnu.no/olestes_fpdb"; // Address to the database
		bruker = "olestes_fp";
		passord = "swag";
	}
	
	//Oppretter tilkobling til databasen
	public void kobleOpp(){
		try {
			//Koble til driver (?)
			Class.forName(jdbcDriver);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException... RIOT!");
			e.printStackTrace();
		}
		try {
			//Opprett tilkobling
			conn = DriverManager.getConnection(url, bruker, passord);
			System.out.println("Tilkobling opprettet");
		} catch (SQLException e) {
			System.out.println("Nicht Güt! SQLException! Kunne ikke koble til...");
			e.printStackTrace();
		}
		
	}
	
	//Lukker tilkobling til databasen
	public void lukk(){
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
	public boolean erGyldigInnlogging(String brukernavn, String passord) throws SQLException{
		String query="Select * from Ansatt where brukernavn='"+brukernavn+"' and passord='"+passord+"'";
		ResultSet rs = this.makeSingleQuery(query); //Utfør spørring og motta resultat
		boolean match = false; //Inntil motbevist
		while(rs.next()){//Sjekk resultet av spørringen
			match = brukernavn.equals(rs.getString(1));
			match = match && passord.equals(rs.getString(2));
		}
		return match;
	}
	
	//Krav 2 - Legge inn avtale
	//sted(moterom) er en String her. Dersom et Moterom skal brukes, send Moterom.romid som en String.
	//Om det ikke skal settes et Moterom for avtalen: lag en beskrivende String.
	public void leggInnAvtale(Dato avtaledato, Tid starttid, Tid sluttid, Tid altstart, Tid varighet, 
			String beskrivelse, Moterom rom, Person admin, int antallDeltagere) throws SQLException{
		
		//MERK: AvtaleID har autoincrement
		String query = "INSERT INTO Avtale VALUES " + 
				"(0, "+avtaledato.toString()+", "+starttid.toString()+", "+sluttid.toString()+", "+
				altstart.toString()+", '"+beskrivelse+"', "+Utilities.getCurrentDateTime()+", "+
				antallDeltagere+", '"+admin.getBrukernavn()+"', "+rom.getRomID()+")";
		
		makeSingleUpdate(query);
		
		
		//TODO: lag en insertAppointment(Avtale a) som er tilsvarende denne..
		
	}
	
	/**
	 * Krav 3: Legge til deltager til avtale
	 * @param ansatt
	 * @param avtale
	 */
	public void inviterTilAvtale(Person ansatt, Avtale avtale){
		String query = "INSERT INTO Inviterte VALUES "+
				"('"+ansatt.getBrukernavn()+"', "+avtale.getAvtaleID()+")";

		makeSingleUpdate(query);
	}
	
	/**
	 * Krav 3: Fjern deltager fra avtale
	 * @param ansatt
	 * @param avtale
	 */
	public void fjernFraAvtale(Person ansatt, Avtale avtale){
		String query = "DELETE FROM Inviterte " +
				"WHERE brukernavn='"+ansatt.getBrukernavn()+"' AND avtaleid="+avtale.getAvtaleID();

		makeSingleUpdate(query);
	}
	
	/**
	 * Krav 4: Endre avtale
	 * @param avtale
	 * 
	 * Slår opp på avtelen sin id og oppdaterer alle felt
	 */
	public void endreAvtale(Avtale avtale){
		//TODO: Blir mye klipp og lim fra opprett avtale, så fullfør den først!
		//UPDATE table_name
		//SET column1=value1,column2=value2,...
		//WHERE some_column=some_value;
	}
	
	/**
	 * Krav 5: Slett avtale
	 * @param avtale
	 */
	public void slettAvtale(Avtale avtale){
		String query = "DELETE FROM Avtale " +
				"WHERE avtaleid="+avtale.getAvtaleID();
	}
	
	/**
	 * Krav 6: Reservere møterom
	 * Denne er ikke støttet av databasen...
	 */
	public void reserverMøterom(){
		System.out.println("Hold your horses! Reservasjon av møterom er ikke implementert...");
	}
	
	public ResultSet hentAvtaler(Ansatt ansatt){
		String query = "select Avtale.* " + 
				"from Avtale "+
				"inner join inviterte on (Inviterte.brukernavn='"+ansatt.getBrukernavn()+"' and Avtale.avtaleid = Inviterte.avtaleid) "+
				"union "+
				"select Avtale.* " + 
				"from Avtale "+
				"where admin='"+ansatt.getBrukernavn()+"'";
		
		return makeSingleQuery(query);
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{		
		DatabaseKommunikator dc = new DatabaseKommunikator();
		dc.kobleOpp();
		System.out.println(dc.erGyldigInnlogging("abdull", "abcd"));
		
		/* Opprett avtale test:
		Dato d = new Dato();
		Tid t = new Tid();
		Tid v = new Tid(2,0,0);
		Moterom r = new Moterom();
		Person p = new Person("abdull");
		dc.insertAppointment(d, t, t, t, v, "random avtale", r, p, 5);
		*/
		
		
		
		dc.lukk();
		
	}
}
