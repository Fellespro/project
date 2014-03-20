package modell;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

public class Fabrikk {

	public static boolean inneholderMatch (ResultSet rs, String brukernavn, String passord) throws SQLException{
		boolean match = false; //Inntil motbevist
		while(rs.next()){//Sjekk resultet av sp�rringen
			match = brukernavn.equals(rs.getString(1));
			match = match && passord.equals(rs.getString(2));
		}
		return match;
	}

	public static ArrayList<Avtale> prosesserAvtaler(ResultSet rs, ResultSet inviterters, ArrayList<Person> personliste, ArrayList<Moterom> romliste) throws SQLException{
		ArrayList<Avtale> liste = new ArrayList<Avtale>();
		while(rs.next()){
			Avtale a;
			int id = rs.getInt(1);
			String navn = rs.getString(2);
			Dato dato = new Dato(rs.getString(3));
			String starts = rs.getString(4);
			String slutts = rs.getString(5);
			String altstarts = rs.getString(6);
			Time start = hentTime(starts);
			Time slutt = hentTime(slutts);
			Time alt_start = hentTime(altstarts);
			String beskrivelse = rs.getString(7); 
			String sist_endret = rs.getString(8);
			int antallDeltakere = rs.getInt(9);
			String op = rs.getString(10);
			Person oppretter = hentPerson(personliste, op);
			int romID = rs.getInt(11);
			Moterom rom = hentRom(romliste, romID);
			//String sted = rs.getString(12);
			a = new Avtale(id, navn, oppretter, dato, start, slutt, alt_start, rom, beskrivelse,
					sist_endret, Respons.kanskje, hentInviterte(id, inviterters, personliste, oppretter), antallDeltakere, new ArrayList<String>(), 0);
			liste.add(a);
		}
		return liste;
	}
	
	private static ArrayList<PersonListeElement> hentInviterte(int id,
			ResultSet inviterters, ArrayList<Person> personliste,
			Person oppretter) throws SQLException {
		ArrayList<PersonListeElement> ple = new ArrayList<PersonListeElement>();
		ple.add(new PersonListeElement(oppretter));
		while(inviterters.next()){
			String bruker = inviterters.getString(1);
			int aid = inviterters.getInt(2);
			if(aid==id){
				ple.add(new PersonListeElement(hentPerson(personliste, bruker)));
			}
		}
		return ple;
	}

	public static ArrayList<Avtale> prosesserPersonAvtaler(ResultSet rs, ArrayList<Avtale> avtaleliste, Person person) throws SQLException {
		ArrayList<Avtale> liste = new ArrayList<Avtale>();
		while(rs.next()){
			int avtaleid = rs.getInt(1);
			for(int i=0; i<avtaleliste.size(); i++){
				if(avtaleid==avtaleliste.get(i).hentAvtaleID()){
					liste.add(avtaleliste.get(i));
				}
			}
		}
		return liste;
	}
	
	public static Person hentPerson(ArrayList<Person> pliste, String key){
		for(int i=0; i<pliste.size(); i++){
			if(pliste.get(i).getBrukernavn().equals(key)){
				return pliste.get(i);
			}
		}
		return null;
	}
	
	public static Moterom hentRom(ArrayList<Moterom> romliste, int romID){
		for(int i=0; i<romliste.size(); i++){
			if(romliste.get(i).hentRomID()==romID){
				return romliste.get(i);
			}
		}
		return null;
	}
	
	public static Time hentTime(String s){
		if(s==null){
			return new Time(0,0,0);
		}
		int h = 10*(s.charAt(0)-'0')+(s.charAt(1)-'0');
		int m = 10*(s.charAt(3)-'0')+(s.charAt(4)-'0');
		int sek = 10*(s.charAt(6)-'0')+(s.charAt(7)-'0');
		@SuppressWarnings("deprecation")
		Time t = new Time(h, m, sek);
		return t;
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

	public static ArrayList<Person> prosesserPersoner(ResultSet rs) {
		ArrayList<Person> liste = new ArrayList<Person>();
		try {
			while(rs.next()){
				Person p;
				p = new Person(rs.getString(1));
				p.setNavn(rs.getString(3));
				liste.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste;
	}

	public static ArrayList<Moterom> prosesserMoterom(ResultSet rs) {
		ArrayList<Moterom> liste = new ArrayList<Moterom>();
		try {
			while(rs.next()){
				Moterom m;
				m = new Moterom(rs.getInt(1), rs.getInt(2), "...");
				liste.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return liste;
	}

	public static ArrayList<Gruppe> prosesserGrupper(ResultSet gruppers, ArrayList<Person> personliste, ResultSet gmrs) throws SQLException {
		ArrayList<Gruppe> liste = new ArrayList<Gruppe>();
		//Lag alle grupper
		while(gruppers.next()){
			liste.add(new Gruppe(gruppers.getInt(1), gruppers.getString(2)));
		}
		
		//Legg til alle gruppemedlemmer!
		while(gmrs.next()){
			Person p = hentPerson(personliste, gmrs.getString(1));
			Gruppe g = hentGruppe(liste, gmrs.getInt(2));
			g.leggTilPerson(p);
		}
		return liste;
	}

	private static Gruppe hentGruppe(ArrayList<Gruppe> liste, int gruppeid) {
		for(int i=0; i<liste.size(); i++){
			if(liste.get(i).getGruppeID()==gruppeid){
				return liste.get(i);
			}
		}
		return null;
	}

}
