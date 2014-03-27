package modell;


public class PersonListeElement {
	private Person person;
	private Respons respons;
	
	public PersonListeElement(Person person)
	{
		this.person = person;
		this.respons = Respons.kanskje;
	}
	
	public void settRespons(Respons respons)
	{
		this.respons = respons;
	}
	
	public Respons hentRespons()
	{
		return this.respons;
	}
	
	public Person hentPerson()
	{
		return this.person;
	}
}
