package modell;

import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import database.DatabaseKommunikator;

import javax.swing.JFrame;


public class Kalender{
	
	private ArrayList<Avtale> avtaleliste;
	private ArrayList<Person> personliste;
	private ArrayList<Gruppe> gruppeliste;
	private ArrayList<Moterom> moteromliste;
	private DatabaseKommunikator dk;
	private Person person;
	private ArrayList<Avtale> personUkeAvtaler;
	

	public Kalender(){
		avtaleliste = new ArrayList<Avtale>();
		personliste = new ArrayList<Person>();
		moteromliste = new ArrayList<Moterom>();
		gruppeliste = new ArrayList<Gruppe>();
		
		dk = new DatabaseKommunikator();
		dk.kobleOpp();
		
		personliste = dk.hentPersoner();
		gruppeliste = dk.hentGrupper(personliste);
		moteromliste = dk.hentMoterom();
		avtaleliste = dk.hentAlleAvtaler(personliste, moteromliste);	
		dk.lukk();
	}
	
	/*
	public int avtaleOverlapp(Avtale a)
	{
		int antallOverlapp = 0;
		for(int i = 0;i < avtaler.size();i++)
		{
			if(avtaler.get(i).avtaleOverlapp(a))
			{
				antallOverlapp ++;
			}
		}
		return antallOverlapp;
	}
	*/
	
	public ArrayList<Avtale> hentAvtaleliste() {
		return avtaleliste;
	}
	
	public void settAvtaleliste(ArrayList<Avtale> avtaleliste){
		this.avtaleliste=avtaleliste;
	}

	public ArrayList<Person> hentPersonliste() {
		return personliste;
	}
	
	public PersonListe hentPersonListe(){
		PersonListe pl = new PersonListe();
		for(int i=0; i<personliste.size();i++){
			pl.leggTilPerson(personliste.get(i));
		}
		return pl;
	}

	public ArrayList<Gruppe> hentGruppeliste() {
		return gruppeliste;
	}

	public ArrayList<Moterom> hentMoteromliste() {
		return moteromliste;
	}
	
	public LedigeMoterom hentMoteromListe() {
		LedigeMoterom lm = new LedigeMoterom();
		for(int i=0; i<moteromliste.size(); i++){
			lm.leggTilRom(moteromliste.get(i));
		}
		return lm;
	}

	public ArrayList<Avtale> hentPersonAvtaler(Person ansatt, int aar, int ukenr)
	{
		ArrayList<Avtale> personAvtaler = new ArrayList<Avtale>();
		for(int i = 0;i < avtaleliste.size();i++){
			if(erPersonIAvtale(avtaleliste.get(i),ansatt)==true)
				personAvtaler.add(avtaleliste.get(i));
		}
		return personAvtaler;
	}
	
	public ArrayList<Avtale> hentUkeAvtaler(ArrayList<Avtale> personAvtaler, int aar, int ukenr)
	{
		ArrayList<Avtale> avtalerIUke = new ArrayList<Avtale>();
		for(int i = 0;i < personAvtaler.size();i++){
			if(personAvtaler.get(i).hentAvtaleDato().getUkenr() == ukenr && personAvtaler.get(i).hentAvtaleDato().getAar() == aar)
			{
				avtalerIUke.add(personAvtaler.get(i));
			}
		}
		return avtalerIUke;
	}
	
	public boolean erPersonIAvtale(Avtale avtale, Person ansatt){
		ArrayList<Person> tempPersonListe = avtale.hentInterneDeltakere();
		for(int i = 0; i<tempPersonListe.size(); i++){
			if(ansatt.getNavn().equals(tempPersonListe.get(i).getNavn())){
				System.out.println("Logget inn som: "+ansatt.getNavn());
				System.out.println("Sjekket: " +tempPersonListe.get(i).getNavn());
				return true;
			}
		}
		return false;
	}
	/*
	public List<Avtale> hentAvtaler(int aar, int ukenr, int dagnr, int start, int slutt)
	{
		List<Avtale> avtaler = new ArrayList<Avtale>();
		avtaler = hentAvtaler(aar, ukenr);
		Calendar kalender = Calendar.getInstance();
		kalender.setWeekDate(aar, ukenr, dagnr);
		kalender.setFirstDayOfWeek(Calendar.MONDAY);
		kalender.setMinimalDaysInFirstWeek(4);
		int maaned = kalender.getTime().getMonth() + 1;
		int dag = kalender.getTime().getDate();
		System.out.println(maaned + ", " + dag);
		for(int i = 0;i < avtaler.size();i++)
		{
			if(maaned == avtaler.get(i).hentAvtaleDato().getMnd() && dag == avtaler.get(i).hentAvtaleDato().getDag())
			{
				if(!avtaler.get(i).avtaleKrasj((double)start, (double)slutt))
				{
					fjernAvtale(avtaler.get(i));
				}
			}
		}
		return avtaler;
	}
	*/
	
	public void leggTilAvtale(Avtale a){
		avtaleliste.add(a);
	}
	
	public void fjernAvtale(Avtale a){
		//TODO
	}
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public ArrayList<Avtale> getPersonUkeAvtaler() {
		return personUkeAvtaler;
	}

	public void setPersonUkeAvtaler(ArrayList<Avtale> personUkeAvtaler) {
		this.personUkeAvtaler = personUkeAvtaler;
	}
	/*
	public static void main(String[] args)
	{}
	*/

	
}
