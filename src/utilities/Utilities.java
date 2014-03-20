package utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

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
	
	 public static boolean isInteger(String s) {
	     try { 
	         Integer.parseInt(s); 
	     } catch(NumberFormatException e) { 
	         return false; 
	     }
	     // only got here if we didn't return false
	     return true;
	 }

}
