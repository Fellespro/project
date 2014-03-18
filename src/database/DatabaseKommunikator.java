package database;

import java.sql.*;
import java.util.ArrayList;
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
 *  - Opprett et DatabaseCommunicator-objekt
 *  - DC.initialize();
 *  - Do stuff
 * Avslutt med:
 *  - DC.close()!
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
		return Fabrikk.inneholderMatch(rs, brukernavn, passord);
	}
	
	/**
	 *Krav 2 - Legge inn avtale
	 *Om det ikke skal brukes et m�terom: sett m�teromid til -1, og legg inn sted i beskrivelse!
	 *For � automatisk generere en gyldig avtaleID, sett den til 0.
	 * @param avtale
	 * @throws SQLException 
	 */
	public void leggInnAvtale(Avtale avtale) throws SQLException{
		String query = "INSERT INTO Avtale VALUES " +
					"("+avtale.hentAvtaleID()+", '"+avtale.hentAvtaleNavn()+"', '" + avtale.hentAvtaleDato().toString() +"', '"+avtale.hentStarttid().toString()+"', '" +
					avtale.hentSluttid().toString() +"', '" + avtale.hentAlternativStarttid() +"', '" +
					avtale.hentBeskrivelse() + "', '" + Utilities.getCurrentDateTime() + "', "+ avtale.hentAntallDeltakere() +
					", '" + avtale.hentOpprettetav() + "', " + avtale.hentRomID() +",'"+avtale.getSted()+"')";
		
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
	 * @throws SQLException 
	 */
	public void endreAvtale(Avtale avtale) throws SQLException{
		String query = "UPDATE Avtale SET " +
					" tittel='"+avtale.hentAvtaleNavn() +
					"', dato='"+avtale.hentAvtaleDato().toString() +"', starttidspunkt='"+
				avtale.hentStarttid().toString()+"', sluttidspunkt='"+
					avtale.hentSluttid().toString() +"', alternativtid='"+
					avtale.hentAlternativStarttid() +"', beskrivelse='"+avtale.hentBeskrivelse() +
					"', sistendret='"+Utilities.getCurrentDateTime()+"', antalldeltagere='"+
					avtale.hentAntallDeltakere()+"', rom="+avtale.hentRomID() +
					", sted='"+avtale.getSted()+"' "
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
	public void reserverMoterom(){
		System.out.println("Hold your horses! Reservasjon av m�terom er ikke implementert i databasen...");
		System.out.println("Legg inn m�teromID som verdi for 'sted' i en Avtale for � reservere");
	}
	
	/**
	 * Krav 7: Hent alle avtaler relatert til en gitt person
	 * Henter unionen av alle avtaler som Person er invitert til og har opprettet.
	 * @param ansatt
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Avtale> hentAvtaler(Person ansatt) throws SQLException{
		String query = "select Avtale.* " + 
				"from Avtale "+
				"inner join inviterte on (Inviterte.brukernavn='"+ansatt.getBrukernavn()+"' and Avtale.avtaleid = Inviterte.avtaleid) "+
				"union "+
				"select Avtale.* " + 
				"from Avtale "+
				"where admin='"+ansatt.getBrukernavn()+"'";
		
		ResultSet rs = makeSingleQuery(query);
		return Fabrikk.prosesserAvtaler(rs);
	}
	
	/**
	 * Krav 8: Hent status for deltagelse for alle deltagere for et gitt m�te
	 * Status for deltagelse er en 2-bits-verdi, der verdiene betyr som f�lger:
	 * 0=Ikke svart, 1=Deltar, 2=Deltar ikke, 3=Deltar ikke og ikke synlig
	 * 
	 * Hax i ArrayListen: De f�rste 6 bokstavene utgj�r brukernavnet, den siste(7.) utgj�r status for deltagelse
	 * @param avtale
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<String> hentStatusForDeltakelse(Avtale avtale) throws SQLException{
		String query = "select brukernavn, deltagelse " +
				"from Inviterte " +
				"where avtaleid="+avtale.hentAvtaleID();
		
		ResultSet rs = makeSingleQuery(query);
		
		return Fabrikk.prosesserStatusForDeltagelse(rs);
	}
	
	/**
	 * Krav 9: Melde avbud for m�te
	 * Metoden gj�r litt mer enn det som er spurt om i dette kravet.
	 * Metoden tar inn en person, en avtale, en bolsk variabel for svar p� innkalling, og en
	 * bolsk variabel som sier om avtalen skal skjules i denne personens personlige kalender
	 * Bruk av de bolske variablene (deltar, synlig)	(X=likegyldig)
	 *  - Person takker ja til invitasjon = (true, X)
	 *  - Person takker nei til invitasjon, men vil fremdeles se avtalen = (false, true)
	 *  - Person takker nei og vil ikke se avtalen i sin kalender = (false, false)
	 * @param p
	 * @param a
	 * @throws SQLException 
	 */
	public void svarePaInvitasjon(Person p, Avtale a, boolean deltar, boolean synlig) throws SQLException{
		int svar = 0;
		if(deltar)
			svar = 1;
		else if (synlig)
			svar = 2;
		else
			svar = 3;
		
		String query = "UPDATE Inviterte SET " +
				"deltagelse="+svar+
				"WHERE brukernavn='"+p.getBrukernavn()+"' AND  avtaleid="+a.hentAvtaleID();
		
		makeSingleUpdate(query);
	}
	
	/**
	 * Krav 10: Reservere m�terom
	 * Resrevasjonen ligger i Avtale-klassen. Slettes en avtale, forsvinner reservasjonen ogs�, ettersom
	 * den er en del av avtalen.
	 * Denne metoden henter alle m�terom slik at modellen kan sjekke hva som er ledig p� et gitt tidspunkt.
	 * @throws SQLException 
	 */
	public ArrayList<Moterom> hentMoterom() throws SQLException{
		String query = "SELECT * FROM Moterom";
		ResultSet rs = makeSingleQuery(query);
		return Fabrikk.prosesserMoterom(rs);
	}
	
	
	/**
	 * Krav 11: Visning. 
	 * Dette er allerede dekket med metoden som henter ut alle avtaler for en gitt person
	 */	
	
	
	/**
	 * main metode brukt til testing
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	
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
