package utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import modell.Avtale;
import modell.Dato;

public class Utilities {
	/*
	 * Denne klassen inneholder diverse funksjoner for � uf�re diverse kodesnutter.
	 * F.eks.	- hente n�v�rende tidspunkt som YYYY-MM-DD-HH:MM:SS
	 * 			- regne ut stuff
	 * 			- whatnot..
	 */
	
	//main blir brukt til � teste at ting funker. Blir slettet n�r alt funker/er ferdig
	public static void main(String[] args){
		
	}
	
	public static String getCurrentDateTime(){
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}
	
	public static int getCurrentWeek(){
		Calendar c = new GregorianCalendar();
		return c.get(Calendar.WEEK_OF_YEAR);
	}
	
	/*
	public static int getTodaysDayOfWeek(){
		Calendar c = Calendar.getInstance();
		int out = c.get(Calendar.DAY_OF_WEEK)-1;
		if(out==0){
			out = 7;
		}
		return out;
	}*/
	
	 public static boolean isInteger(String s) {
	     try { 
	         Integer.parseInt(s); 
	     } catch(NumberFormatException e) { 
	         return false; 
	     }
	     // only got here if we didn't return false
	     return true;
	 }

	public static int getDayOfWeek(Avtale a) {
		int out = -1;
		int d = a.hentAvtaleDato().getDag();
		int m = a.hentAvtaleDato().getMnd();
		int y = a.hentAvtaleDato().getAar();
		int c = 6;
		out = (d + m + y + y/4 + c )%7;
		return out;
	}

	public static Dato hentDato(ArrayList<Avtale> personUkeAvtaler) {
		if(personUkeAvtaler.size()==0){
			return null;
		}
		else{
			//Sikkert my unodvendig arbeid her...
			Avtale a = personUkeAvtaler.get(0);
			Dato d = a.hentAvtaleDato();
			Calendar avtaleC = Calendar.getInstance(Locale.GERMANY);
			avtaleC.setFirstDayOfWeek(Calendar.MONDAY);
			avtaleC.set(Calendar.YEAR, d.getAar());
			avtaleC.set(Calendar.MONTH, d.getMnd()-1);
			avtaleC.set(Calendar.DAY_OF_MONTH, d.getDag());
			
			Calendar ukeC = Calendar.getInstance(Locale.GERMANY);
			ukeC.set(Calendar.YEAR, d.getAar());
			ukeC.set(Calendar.WEEK_OF_YEAR, avtaleC.get(Calendar.WEEK_OF_YEAR));
			ukeC.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			
			System.out.println(ukeC.get(Calendar.DAY_OF_MONTH));
			System.out.println(ukeC.get(Calendar.MONTH));
			System.out.println(ukeC.get(Calendar.YEAR));
			
			return new Dato(ukeC.get(Calendar.DAY_OF_MONTH), ukeC.get(Calendar.MONTH), ukeC.get(Calendar.YEAR));
		}
	}

}
