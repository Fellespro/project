package modell;

import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseKommunikator;

public class LedigeMoterom {
	private ArrayList<Moterom> rom;
	private ArrayList<Avtale> avtaler;
	
	public ArrayList<Moterom> ledigeRom(Dato dato,Tid start, Tid Slutt){
		DatabaseKommunikator database=new DatabaseKommunikator();
		database.kobleOpp();
		rom=database.hentMoterom();
		try {
			avtaler=database.hentAlleAvtaler(database.hentPersoner(), rom);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//fjerner alle avtaler som har annen dato fra listen for å gjøre det lettere å finne ut
		//hvilke som kræsjer med tid
		for (int i=avtaler.size()-1;i>=0;i--){
			if (avtaler.get(i).hentAvtaleDato().getDag()==dato.getDag()&&
					avtaler.get(i).hentAvtaleDato().getMnd()==dato.getMnd()&&
					avtaler.get(i).hentAvtaleDato().getAar()==dato.getAar()){
				
			}else{
				avtaler.remove(i);
			}
		}
		for (int i=avtaler.size()-1;i>=0;i--){
			if(avtaler.get(i))
		}
		return null;
	}
}
