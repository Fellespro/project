package utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

}
