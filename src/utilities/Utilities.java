package utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

}
