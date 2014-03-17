package modell;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 
 * Denne klassen brukes til å prosessere ResultSet fra databasespørringer og 
 * lage objekt-instanser av dem.
 * Fabrikk blir antageligvis kun brukt av DatabaseKommunikator. Andre klasser må dermed kommunisere
 * med DatabaseKommunikator.
 */

public class Fabrikk {
	
	public static boolean inneholderMatch (ResultSet rs, String brukernavn, String passord) throws SQLException{
		boolean match = false; //Inntil motbevist
		while(rs.next()){//Sjekk resultet av spørringen
			match = brukernavn.equals(rs.getString(1));
			match = match && passord.equals(rs.getString(2));
		}
		return match;
	}
	
	public static ArrayList<Avtale> prosesserAvtaler(ResultSet rs) throws SQLException{
		ArrayList<Avtale> liste = new ArrayList<Avtale>();
		while(rs.next()){
			Avtale a = new Avtale();
			a.settAvtaleID(rs.getInt(1));
			a.settAvtaleNavn(rs.getString(2));
			a.settAvtaleDato(rs.getString(3));
			a.settStarttid(rs.getString(4));
			a.settSluttid(rs.getString(5));
			a.settAlternativStarttid(rs.getString(6));
			a.settBeskrivelse(rs.getString(7));
			a.settSistEndret(rs.getString(8));
			a.settAntallDeltakere(rs.getInt(9));
			a.settOppretter(rs.getString(10));
			a.settRomID(rs.getInt(11));
			a.setSted(rs.getString(12));
			liste.add(a);
		}
		return liste;	
	}

	public static ArrayList<String> prosesserStatusForDeltagelse(ResultSet rs) throws SQLException {
		ArrayList<String> liste = new ArrayList<String>();
		while(rs.next()){
			liste.add(rs.getString(1)+rs.getInt(2));
		}
		return liste;
	}

	public static Person prosesserPerson(ResultSet rs) {
		Person p;
		try {
			while(rs.next()){
				p = new Person(rs.getString(1));
				p.setNavn(rs.getString(3));
				return p;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
