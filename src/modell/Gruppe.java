package modell;

public class Gruppe {

	private Person[] personListe;
	private int gruppeID;
	private String gruppeNavn;
	
	public Gruppe(Person[] personListe, int gruppeID, String gruppeNavn){
		
		this.personListe=personListe;
		this.gruppeID=gruppeID;
		this.gruppeNavn=gruppeNavn;
	}
	
	public int getGruppeID() {
		return gruppeID;
	}
	
	public String getGruppeNavn() {
		return gruppeNavn;
	}

	public Person[] getPersonListe() {
		return personListe;
	}
	
	//HEI
	
	
}
