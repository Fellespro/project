package modell;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JFrame;


public class Kalender{
	
	private List<Avtale> avtaleliste;
	private List<Person> personliste;
	private List<Moterom> moteromliste;
	

	Kalender()
	{
		avtaleliste = new ArrayList<Avtale>();
		personliste = new ArrayList<Person>();
		moteromliste = new ArrayList<Moterom>();
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
	
	public List<Avtale> hentAvtaler(int aar, int ukenr)
	{
		List<Avtale> avtalerIUke = new ArrayList<Avtale>();
		for(int i = 0;i < avtaler.size();i++)
		{
			if(avtaler.get(i).hentAvtaleDato().getUkenr() == ukenr && avtaler.get(i).hentAvtaleDato().getAar() == aar)
			{
				avtalerIUke.add(avtaler.get(i));
			}
		}
		return avtalerIUke;
	}
	
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
	
	public void leggTilAvtale(Avtale a)
	{
		avtaler.add(a);
	}
	
	public void fjernAvtale(Avtale a)
	{
		avtaler.remove(a);
	}
	
	public static void main(String[] args)
	{
		Kalender kalender = new Kalender();
		Person person = new Person(1, "Stine Lilleborge");
		Avtale avtale = new Avtale(person, new Dato(16, 1, 2014), new Time(0), new Time(0+2));
		kalender.leggTilAvtale(avtale);
		kalender.leggTilAvtale(new Avtale(person, new Dato(13, 1, 2014), new Time(13, 15, 00), new Time(15, 00, 00)));
		kalender.leggTilAvtale(new Avtale(person, new Dato(15, 1, 2014), new Time(13, 15, 00), new Time(15, 00, 00)));
		kalender.leggTilAvtale(new Avtale(person, new Dato(10, 1, 2014), new Time(13, 15, 00), new Time(15, 00, 00)));
		kalender.leggTilAvtale(new Avtale(person, new Dato(17, 1, 2014), new Time(16, 15, 00), new Time(17, 00, 00)));
		kalender.leggTilAvtale(new Avtale(person, new Dato(9, 1, 2014), new Time(13, 15, 00), new Time(15, 00, 00)));
		kalender.fjernAvtale(avtale);
		List<Avtale> avtaleruke = kalender.hentAvtaler(2014, 3);
		for(int i = 0;i < avtaleruke.size();i++)
		{
			System.out.println(avtaleruke.get(i).hentAvtaleID() + ", " + avtaleruke.get(i).hentAvtaleDato());
		}
		System.out.println("\n");
		List<Avtale> avtalertidspunkt = kalender.hentAvtaler(2014, 3, 1, 12, 15);
		for(int i = 0;i < avtalertidspunkt.size();i++)
		{
			System.out.println(avtalertidspunkt.get(i).hentAvtaleID() + ", " + avtalertidspunkt.get(i).hentAvtaleDato());
		}
	}
	
	
}
