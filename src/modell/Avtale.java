import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.xml.soap.Text;
import java.lang.Object;


public class Avtale {
	
	private static int antall_avtaler = 0;
	
	private final int avtaleID;
	private String avtaleNavn;
	private String opprettetAv;
	/*protected Date avtaleDato;*/
<<<<<<< HEAD
	protected Calendar avtaleDato;
	protected Time starttid;
	protected Time sluttid;
	protected Time alternativStarttid;
	protected String beskrivelse;
	protected String sistEndret;
	protected List<Entry<Person, Respons>> deltakere;
	protected int antallDeltakere;
	protected Respons status;
=======
	private Calendar avtaleDato;
	private Time starttid;
	private Time sluttid;
	private Time alternativStarttid;
	private String beskrivelse;
	private String sistEndret;
	private List<Entry<Person, Respons>> deltakere;
	private int antallDeltakere;
	private Respons status;
	private String sted;
	private int rom;
>>>>>>> 0b41dad8b25f35ee9f35645b761e388f6d3e2f76
	
	Avtale () 
	{
		avtaleID = antall_avtaler;
		antall_avtaler ++;
		beskrivelse = new String("-");
		antallDeltakere = 0;
		status = Respons.kanskje;
		deltakere = new Vector<Entry<Person,Respons>>();
	}


	Avtale(boolean tomAvtale){
	//Må kunne opprette avtaler der vi bestemmer avtaleID selv, når vi skal laste inn data fra databasen!
		if(tomAvtale){
		}
	}
	
	Avtale (Time start, Time slutt)
	{
		avtaleID = antall_avtaler;
		antall_avtaler ++;
		beskrivelse = new String("-");
		starttid = start;
		sluttid = slutt;
		antallDeltakere = 0;
		status = Respons.kanskje;
	}
	
	Avtale (Calendar dato, Time start, Time slutt)
	{
		avtaleID = antall_avtaler;
		antall_avtaler ++;
		beskrivelse = new String("-");
		avtaleDato = dato;
		starttid = start;
		sluttid = slutt;
		antallDeltakere = 0;
		status = Respons.kanskje;
	}
	
	public int hentAvtaleID()
	{
		return this.avtaleID;
	}

	public void settAvtaleID(int id){
		avtaleID = id;
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
	}
	
	public String hentOpprettetav()
	{
		if(opprettetAv !=null)
		{
			return opprettetAv;
		}
		else
			return "";
	}
	
	public void settOpprettetAv(String oppretter)
	{
		opprettetAv = oppretter;
	}
	
	public Calendar hentAvtaleDato()
	{
		if(avtaleDato != null)
			return avtaleDato;
		else
			return null;
	}
	
	public void settAvtaleDato(Calendar dato)
	{
		avtaleDato = dato;
	}
	
	public Time hentStarttid()
	{
		if(starttid != null)
			return starttid;
		else
			return new Time(0,0,0);
	}
	
	public void settStarttid(Time tid)
	{
		starttid = tid;
	}
	
	public Time hentSluttid()
	{
		if(sluttid != null)
			return sluttid;
		else
			return new Time(0,0,0);
	}
	
	public void settSluttid(Time tid)
	{
		sluttid = tid;
	}
	
	public Time hentAlternativStarttid()
	{
		return alternativStarttid;
<<<<<<< HEAD
	}
	
	public void settAlternativStarttid(Time tid)
	{
		alternativStarttid = tid;
=======
>>>>>>> 0b41dad8b25f35ee9f35645b761e388f6d3e2f76
	}
	
	public void settAlternativStarttid(Time tid)
	{
		alternativStarttid = tid;
	}
	
	public String getSted(){
                return sted;
        }
        
        public void setSted(String sted){
                this.sted = sted;
        }
	
	public Time hentVarighet()
	{
		Time varighet = new Time(0,0,0);
		if(starttid != null && sluttid != null)
		{
			Time slutt = hentSluttid();
			Time start = hentStarttid();
			int timer = slutt.getHours() - start.getHours();
			int minutter = slutt.getMinutes() - start.getMinutes();
			int sekunder = slutt.getSeconds() - start.getSeconds();
			varighet = new Time(timer, minutter, sekunder);
		}
		return varighet;
	}
	
	public void settVarighet(Time varighet)
	{
		if(starttid != null)
		{
			int timer = starttid.getHours() + varighet.getHours();
			int minutter = starttid.getMinutes() + varighet.getMinutes();
			int sekunder = starttid.getSeconds() + varighet.getSeconds();
			Time slutt = new Time(timer, minutter, sekunder);
			settSluttid(slutt);
		}
		else
		{
			System.out.println("Start-tid ikke satt");
		}
	}
	
	public String hentBeskrivelse()
	{
		return beskrivelse;
	}
	
	public void settBeskrivelse(String b)
	{
		this.beskrivelse = b;
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
		sistEndret = endret;
	}
	
	public Person hentPerson(int pos)
	{
		if(antallDeltakere <= pos)
		{
			pos = antallDeltakere - 1;
		}
		else if(pos < 0)
		{
			pos = 0;
		}
		return deltakere.get(pos).getKey();
	}
	
	public void leggTilPerson(Person person)
	{
		antallDeltakere ++;
	}
	
	public int hentAntallDeltakere()
	{
		return antallDeltakere;
	}
	
	public void settAntallDeltakere(int a){
		antallDeltakere = a;
	}
	
	public String hentStatus()
	{
		if(this.status == Respons.ja)
		{
			return "Godkjent";
		}
		if(this.status == Respons.nei)
		{
			return "Avsl�tt";
		}
		else
		{
			return "Avventer";
		}
	}
	
	public void settStatus(Respons r)
	{
		status = r;
	}
	
	public int hentRomID(){
		return rom;
	}
	
	public void settRomID(int r){
		this.rom = r;
	}
	
	public void print()
	{
		System.out.print("AvtaleID:");
		System.out.println(this.hentAvtaleID());
		System.out.print("AvtaleNavn:");
		System.out.println(this.hentAvtaleNavn());
		System.out.print("Opprettet av:");
		System.out.println(this.hentOpprettetav());
		System.out.print("Sist endret:");
		System.out.println(this.hentSistEndret());
		System.out.print("AvtaleDato:");
		System.out.println(this.hentAvtaleDato());
		System.out.print("Starttid:");
		System.out.println(this.hentStarttid());
		System.out.print("Sluttid:");
		System.out.println(this.hentSluttid());
		System.out.print("Alternativ starttid:");
		System.out.print(this.hentAlternativStarttid());
		System.out.print("Varighet:");
		System.out.println(this.hentVarighet());
		System.out.print("Beskivelse: ");
		System.out.println(this.hentBeskrivelse());
		System.out.print("Status: ");
		System.out.println(this.hentStatus());
		System.out.print("Antall deltakere: ");
		System.out.println(this.hentAntallDeltakere());
		System.out.print("Deltakere: ");
		if(this.hentAntallDeltakere() > 0)
		{
			for(int i = 0;i < this.hentAntallDeltakere();i++)
			{
				System.out.println("Navn: " + deltakere.get(i).getKey().getNavn() + ", ID: " + deltakere.get(i).getKey().getNavn());
			}
		}
		else
		{
			System.out.println("-");
		}
	}
		
}
