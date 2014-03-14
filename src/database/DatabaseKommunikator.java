package database;

import java.sql.*;
import java.util.Properties;

import utilities.Utilities;

import modell.*;

/*
 * Klassen kobler seg til en mySQL-database p� NTNUs server
 * jdbcDriver=com.mysql.jdbc.Driver
 * url=jdbc:mysql://localhost/testDB
 * user=olestes_fp
 * password=swag
 * 
 * Hvordan bruke:
 * Opprett et DatabaseCommunicator-objekt
 * DC.initialize();
 * N� kan du bruke DC.get/set for � hente ut/sette det du vil.
 * 
 * Husk � avslutte med:
 * DC.close()!
 * 
 * OBS! OBS!
 * DatabaseCommunicator er veldig dum. Den sjekker ikke om avtaler o.l. som skal legges
 * inn i databasen har gyldige data. G�r utifra at modellen garanterer at alt er korrekt!
 * 
 * 
 */
public class DatabaseKommunikator {
	
	private static String jdbcDriver; // String containing the driver Class name
	private String url; // Address to the database
	private String bruker;
	private String passord;
	
	private Connection conn;
	
	
	//Tilkoblingsinfo blir satt her. Hardkodet forel�big
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
			System.out.println("Nicht G�t! SQLException! Kunne ikke koble til...");
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
	
	//Krav 1 - Logge p�
	//Tar inn brukernavn og passord og sjekker om det finnes et brukernavn med tilh�rende
	//passord i databasen.
	public boolean erGyldigInnlogging(String brukernavn, String passord) throws SQLException{
		String query="Select * from Ansatt where brukernavn='"+brukernavn+"' and passord='"+passord+"'";
		ResultSet rs = this.makeSingleQuery(query); //Utf�r sp�rring og motta resultat
		boolean match = false; //Inntil motbevist
		while(rs.next()){//Sjekk resultet av sp�rringen
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
				altstart.toString()+", '"+beskrivelse+"', '"+Utilities.getCurrentDateTime()+"', "+
				antallDeltagere+", '"+admin.getBrukernavn()+"', "+rom.hentRomID()+")";
		
		makeSingleUpdate(query);
	}
	public void leggInnAvtale(Avtale avtale){
		String query = "INSERT INTO Avtale VALUES " +
					"(0, '" + avtale.hentAvtaleDato().toString() +"', '"+avtale.hentStarttid().toString()+"', '" +
					avtale.hentSluttid().toString() +"', '" + avtale.hentAlternativStarttid() +"', '" +
					avtale.hentBeskrivelse() + "', '" + Utilities.getCurrentDateTime() + "', "+ avtale.hentAntallDeltakere() +
					", '" + avtale.hentOpprettetav() + "', " + avtale.hentRom().hentRomID() + ")";
		
		makeSingleUpdate(query);
	}
	
	/**
	 * Krav 3: Legge til deltager til avtale
	 * @param ansatt
	 * @param avtale
	 * @throws SQLException 
	 */
	public void inviterTilAvtale(Person ansatt, Avtale avtale) throws SQLException{
		String query = "INSERT INTO Inviterte VALUES "+
				"('"+ansatt.getBrukernavn()+"', "+avtale.hentAvtaleID()+")";

		makeSingleUpdate(query);
	}
	
	/**
	 * Krav 3: Fjern deltager fra avtale
	 * @param ansatt
	 * @param avtale
	 * @throws SQLException 
	 */
	public void fjernFraAvtale(Person ansatt, Avtale avtale) throws SQLException{
		String query = "DELETE FROM Inviterte " +
				"WHERE brukernavn='"+ansatt.getBrukernavn()+"' AND avtaleid="+avtale.hentAvtaleID();

		makeSingleUpdate(query);
	}
	
	/**
	 * Krav 4: Endre avtale
	 * @param avtale
	 * 
	 * Sl�r opp p� avtelen sin id og oppdaterer alle felt
	 */
	public void endreAvtale(Avtale avtale){
		String query = "UPDATE Avtale SET " +
					" dato='"+avtale.hentAvtaleDato().toString() +"', starttidspunkt='"+
				avtale.hentStarttid().toString()+"', sluttidspunkt='"+
					avtale.hentSluttid().toString() +"', alternativtid='"+
					avtale.hentAlternativStarttid() +"', beskrivelse='"+avtale.hentBeskrivelse() +
					"', sistendret='"+Utilities.getCurrentDateTime()+"', antalldeltagere='"+
					avtale.hentAntallDeltakere()+"', rom="+avtale.hentRom().hentRomID()
				+" WHERE avtaleid="+avtale.hentAvtaleID();
		
		makeSingleUpdate(query);
	}
	
	/**
	 * Krav 5: Slett avtale
	 * @param avtale
	 * @throws SQLException 
	 */
	public void slettAvtale(Avtale avtale) throws SQLException{
		String query = "DELETE FROM Avtale " +
				"WHERE avtaleid="+avtale.hentAvtaleID();
		makeSingleUpdate(query);
	}
	
	/**
	 * Krav 6: Reservere m�terom
	 * Denne er ikke st�ttet av databasen...
	 */
	public void reserverM�terom(){
		System.out.println("Hold your horses! Reservasjon av m�terom er ikke implementert i databasen...");
	}
	
	/**
	 * Krav 7: Hent alle avtaler relatert til en gitt person
	 * Henter unionen av alle avtaler som Person er invitert til og har opprettet.
	 * @param ansatt
	 * @return
	 * @throws SQLException
	 */
	public ResultSet hentAvtaler(Person ansatt) throws SQLException{
		String query = "select Avtale.* " + 
				"from Avtale "+
				"inner join inviterte on (Inviterte.brukernavn='"+ansatt.getBrukernavn()+"' and Avtale.avtaleid = Inviterte.avtaleid) "+
				"union "+
				"select Avtale.* " + 
				"from Avtale "+
				"where admin='"+ansatt.getBrukernavn()+"'";
		
		return makeSingleQuery(query);
	}
	
	/**
	 * Krav 8: Hent status for deltagelse for alle deltagere for et gitt m�te
	 * @param avtale
	 * @return
	 * @throws SQLException
	 */
	public ResultSet hentStatusForDeltakelse(Avtale avtale) throws SQLException{
		String query = "select * " +
				"from Inviterte " +
				"where avtaleid="+avtale.hentAvtaleID();
		
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
