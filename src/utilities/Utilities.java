package utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import modell.Avtale;

public class Utilities {
	/*
	 * Denne klassen inneholder diverse funksjoner for å uføre diverse kodesnutter.
	 * F.eks.	- hente nåværende tidspunkt som YYYY-MM-DD-HH:MM:SS
	 * 			- regne ut stuff
	 * 			- whatnot..
	 */
	
	//main blir brukt til å teste at ting funker. Blir slettet når alt funker/er ferdig
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

	public static int getDay(ArrayList<Avtale> personUkeAvtaler) {
		if(personUkeAvtaler.size()==0){
			return -1;
		}
		else{
			return personUkeAvtaler.get(0).hentAvtaleDato().getDag()-getDayOfWeek(personUkeAvtaler.get(0))+2;
		}
	}

}
