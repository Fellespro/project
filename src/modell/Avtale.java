package modell;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;


public class Avtale {

	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private static int antall_avtaler = 0;

	private final int avtaleID;
	private String avtaleNavn;
	private Person opprettetAv;

	private Dato avtaleDato;
	private Tid starttid;
	private Tid sluttid;
	private Tid alternativStarttid;
	private Moterom rom;

	private String beskrivelse;

	private String sistEndret;
	private Respons status;

	private int antallInterneDeltakere;
	private List<PersonListeElement> interneDeltakere;
	private int antallEksterneDeltakere;
	private List<String> eksterneDeltakere;
	

	Avtale (int id, String navn, Person oppretter, Dato dato, Tid start, Tid slutt, Tid alt_start, Moterom rom,
			String beskr, String sist_endret, Respons resp, ArrayList<PersonListeElement> interne, int int_dltkr,
			ArrayList<String> eksterne, int ekst_dltkr)

	{
		this.avtaleID = id;
		this.avtaleNavn = navn;
		this.opprettetAv = oppretter;

		this.avtaleDato = dato;
		this.starttid = start;
		this.sluttid = slutt;
		this.alternativStarttid = alt_start;
		this.rom = rom;

		this.beskrivelse = beskr;

		sistEndret = sist_endret;
		status = resp;

		interneDeltakere = interne;
		antallInterneDeltakere = int_dltkr;
		eksterneDeltakere = eksterne;
		antallEksterneDeltakere = ekst_dltkr;
	}
	
	public Avtale (String navn, Person oppretter, Dato dato, Tid start, Tid slutt, Tid alt_start, Moterom rom,
			String beskr, String sist_endret, Respons resp, ArrayList<PersonListeElement> interne, int int_dltkr,
			ArrayList<String> eksterne, int ekst_dltkr)

	{
		avtaleID = antall_avtaler;
		antall_avtaler ++;
		this.avtaleNavn = navn;
		this.opprettetAv = oppretter;

		this.avtaleDato = dato;
		this.starttid = start;
		this.sluttid = slutt;
		this.alternativStarttid = alt_start;
		this.rom = rom;

		this.beskrivelse = beskr;

		sistEndret = sist_endret;
		status = resp;

		interneDeltakere = interne;
		antallInterneDeltakere = int_dltkr;
		eksterneDeltakere = eksterne;
		antallEksterneDeltakere = ekst_dltkr;
	}


	public Avtale (Person oppretter, Dato dato, Tid start, Tid slutt)
	{
		avtaleID = antall_avtaler;
		antall_avtaler ++;
		avtaleNavn = new String("-");
		opprettetAv = oppretter;

		avtaleDato = dato;
		starttid = start;
		sluttid = slutt;
		rom = new Moterom();

		beskrivelse = new String("-");

		sistEndret = dateFormat.format(Calendar.getInstance().getTime());
		status = Respons.kanskje;

		interneDeltakere = new Vector<PersonListeElement>();
		interneDeltakere.add(new PersonListeElement(oppretter));
		antallInterneDeltakere = 1;
		eksterneDeltakere = new Vector<String>();
		antallEksterneDeltakere = 0;
	}


	public int hentAvtaleID()
	{
		return this.avtaleID;
	}

	public String hentAvtaleNavn()
	{
		if(avtaleNavn != null)
			return avtaleNavn;
		else
			return "";
	}

	public void settAvtaleNavn(String navn)
	{
		avtaleNavn = navn;
		settSistEndret(null);
	}

	public Person hentOpprettetAv()
	{
		return opprettetAv;
	}

	public void settOpprettetAv(Person oppretter)
	{
		opprettetAv = oppretter;
		settSistEndret(null);
	}

	public Dato hentAvtaleDato()
	{
		if(avtaleDato != null)
			return avtaleDato;
		else
			return null;
	}

	public void settAvtaleDato(Dato dato)
	{
		avtaleDato = dato;
		settSistEndret(null);
	}

	public Tid hentStarttid()
	{
		return starttid;
	}

	public void settStarttid(Tid tid)
	{
		starttid = tid;
		settSistEndret(null);
	}

	public Tid hentSluttid()
	{
		return sluttid;
	}

	public void settSluttid(Tid tid)
	{
		sluttid = tid;
		settSistEndret(null);
	}

	public Tid hentAlternativStarttid()
	{
		return alternativStarttid;
	}

	public void settAlternativStarttid(Tid tid)
	{
		alternativStarttid = tid;
		settSistEndret(null);
	}

	public Moterom hentRom()
	{
		return rom;
	}

	public void settRom(Moterom rom)
	{
		this.rom = rom;
	}

	public Tid hentVarighet()
	{
		Tid varighet = new Tid();
		if(starttid != null && sluttid != null)
		{
			Tid slutt = hentSluttid();
			Tid start = hentStarttid();
			int timer = slutt.hentTime() - start.hentTime();
			int minutter = slutt.hentMin() - start.hentMin();
			if(minutter<0){
				timer--;
				minutter=60-minutter;
			}
			int sekunder = slutt.hentSek() - start.hentSek();
			if(sekunder<0){
				minutter--;
				sekunder=60-sekunder;
			}
			varighet = new Tid(timer, minutter, sekunder);
		}
		return varighet;
	}

	public void settVarighet(Tid varighet)
	{
		if(starttid != null)
		{
			int timer = starttid.hentTime() + varighet.hentTime();
			int minutter = starttid.hentMin() + varighet.hentMin();
			if(minutter<0){
				timer--;
				minutter=60-minutter;
			}
			int sekunder = starttid.hentSek() + varighet.hentSek();
			if(sekunder<0){
				minutter--;
				sekunder=60-sekunder;
			}
			Tid slutt = new Tid(timer, minutter, sekunder);
			settSluttid(slutt);
		}
		else
		{
			System.out.println("Start-tid ikke satt");
		}
		settSistEndret(null);
	}

	public String hentBeskrivelse()
	{
		return beskrivelse;
	}

	public void settBeskrivelse(String b)
	{
		this.beskrivelse = b;
		settSistEndret(null);
	}

	public String hentSistEndret()
	{
		if(sistEndret != null)
			return sistEndret;
		else
			return "";
	}

	public void settSistEndret(String endret)
	{
		if(endret == null)
			sistEndret = dateFormat.format(Calendar.getInstance().getTime());
		else
			sistEndret = endret;
	}

	public String hentStatus()
	{
		if(this.status == Respons.ja)
		{
			return "Godkjent av alle";
		}
		if(this.status == Respons.nei)
		{
			return "Avslått av en eller flere";
		}
		else
		{
			return "Avventer respons";
		}
	}

	public void settStatus(Respons r)
	{
		status = r;
		settSistEndret(null);
	}

	public int hentAntallDeltakere()
	{
		return antallInterneDeltakere + antallEksterneDeltakere;
	}

	public int hentAntallInterne()
	{
		return antallInterneDeltakere;
	}

	public void settAntallInterne(int ant)
	{
		antallInterneDeltakere = ant;
	}
	
	public ArrayList<Person> hentInterneDeltakere(){
		ArrayList<Person> personliste = new ArrayList<Person>();
		for(int i=0; i<interneDeltakere.size(); i++)
			personliste.add(interneDeltakere.get(i).hentPerson());
		return personliste;
	}

	public int hentAntallEksterne()
	{
		return antallEksterneDeltakere;
	}

	public void settAntallEksterne(int ant)
	{
		antallEksterneDeltakere = ant;
	}

	public Person hentPerson(int pos)
	{
		if(antallInterneDeltakere <= pos)
		{
			pos = antallInterneDeltakere - 1;
		}
		else if(pos < 0)
		{
			pos = 0;
		}
		return interneDeltakere.get(pos).hentPerson();
	}

	public String hentEmail(int pos)
	{
		if(antallEksterneDeltakere <= pos)
		{
			pos = antallEksterneDeltakere - 1;
		}
		else if(pos < 0)
		{
			pos = 0;
		}
		return eksterneDeltakere.get(pos);
	}

	public void leggTilDeltaker(Person person)
	{
		for(int i = 0;i < antallInterneDeltakere;i++)
		{
			if(interneDeltakere.get(i).hentPerson() == person)
			{
				return;
			}
		}
		interneDeltakere.add(new PersonListeElement(person));
		antallInterneDeltakere ++;
		settSistEndret(null);
	}

	public void fjernDeltaker(Person person)
	{
		for(int i = 0;i < antallInterneDeltakere;i++)
		{
			if(interneDeltakere.get(i).hentPerson() == person)
			{
				interneDeltakere.remove(i);
				antallInterneDeltakere --;
			}
		}
		settSistEndret(null);
	}

	public void leggTilDeltaker(String email)
	{
		for(int i = 0;i < antallEksterneDeltakere;i++)
		{
			if(eksterneDeltakere.get(i) == email)
			{
				return;
			}
		}
		eksterneDeltakere.add(new String(email));
		antallEksterneDeltakere ++;
		settSistEndret(null);
	}
	
	public void fjernDeltaker(String email)
	{
		for(int i = 0;i < antallEksterneDeltakere;i++)
		{
			if(eksterneDeltakere.get(i) == email)
			{
				eksterneDeltakere.remove(i);
				antallEksterneDeltakere --;
			}
		}
		settSistEndret(null);
	}
	
	public void settInterneDeltakere(List<PersonListeElement> interne)
	{
		for(int i = 0;i < interne.size();i++)
		{
			for(int j = 0;j < this.interneDeltakere.size();i++)
			{
				if(interne.get(i).hentPerson() == this.interneDeltakere.get(j).hentPerson())
				{
					interne.get(i).settRespons(this.interneDeltakere.get(j).hentRespons());
					j = this.interneDeltakere.size();
				}
			}
		}
		this.interneDeltakere = interne;
	}
	
	public void settEksterneDeltakere(List<String> eksterne)
	{
		this.eksterneDeltakere = eksterne;
	}
	
	public boolean avtaleIUke(int ukenr, int aar)
	{
		if((avtaleDato.getAar() == aar) && (ukenr == avtaleDato.getUkenr()))
		{
			return true;
		}
		return false;
	}
	
	public boolean avtaleOverlapp(Avtale a)
	{
		//starttidspunkt for avtale a
		int start_time_a = a.hentStarttid().hentTime();
		int start_minutt_a = a.hentStarttid().hentMin();
		double start_a = start_time_a + ((double)start_minutt_a)/100;

		//sluttidspunkt for avtale a
		int slutt_time_a = a.hentSluttid().hentTime();
		int slutt_minutt_a = a.hentSluttid().hentMin();
		double slutt_a = slutt_time_a + ((double)slutt_minutt_a)/100;

		if(avtaleKrasj(start_a, slutt_a))
		{
			return true;
		}
		return false;
	}

	public boolean avtaleKrasj(double start_a, double slutt_a)
	{
		//starttidspunkt for this
		int start_time_this = hentStarttid().hentTime();
		int start_minutt_this = hentStarttid().hentMin();
		double start_this = start_time_this + ((double)start_minutt_this)/100;

		//sluttidspunkt for this
		int slutt_time_this = hentSluttid().hentTime();
		int slutt_minutt_this = hentSluttid().hentMin();
		double slutt_this = slutt_time_this + ((double)slutt_minutt_this)/100;

		if(start_this < start_a && !(slutt_this < start_a))
		{
			return true;
		}
		else if(start_a < start_this && !(slutt_a < start_this))
		{
			return true;
		}
		return false;
	}

}
