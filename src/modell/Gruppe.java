package modell;

import java.util.ArrayList;

public class Gruppe {

	private ArrayList<Person> personListe;
	private int gruppeID;
	private String gruppeNavn;
	
	public Gruppe(int gruppeID, String gruppeNavn){
		
		this.personListe= new ArrayList<Person>();
		this.gruppeID=gruppeID;
		this.gruppeNavn=gruppeNavn;
	}
	
	public int getGruppeID() {
		return gruppeID;
	}
	
	public String getGruppeNavn() {
		return gruppeNavn;
	}

	public ArrayList<Person> getPersonListe() {
		return personListe;
	}
	
	public void leggTilPerson(Person p){
		personListe.add(p);
	}
	
}
