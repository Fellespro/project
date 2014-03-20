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
		System.out.println("Antall avtaler i databasen[mkalender]: "+avtaleliste.size());	
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

	public ArrayList<Person> hentPersonliste() {
		return personliste;
	}

	public ArrayList<Gruppe> hentGruppeliste() {
		return gruppeliste;
	}

	public ArrayList<Moterom> hentMoteromliste() {
		return moteromliste;
	}

	public ArrayList<Avtale> hentPersonAvtaler(Person ansatt, int aar, int ukenr)
	{
		System.out.println(avtaleliste.size());
		ArrayList<Avtale> personAvtaler = new ArrayList<Avtale>();
		for(int i = 0;i < avtaleliste.size();i++){
			if(erPersonIAvtale(avtaleliste.get(i),ansatt)==true)
				personAvtaler.add(avtaleliste.get(i));
		}
		System.out.println(personAvtaler.size());
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
			if(ansatt.getPersonId()==tempPersonListe.get(i).getPersonId())
				return true;
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
	
	public void leggTilAvtale(Avtale a){}
	
	public void fjernAvtale(Avtale a){}
	/*
	public static void main(String[] args)
	{}
	*/
	
}
