package modell;

import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseKommunikator;

public class LedigeMoterom {
	private ArrayList<Moterom> rom;
	private ArrayList<Avtale> avtaler;
	
	public ArrayList<Moterom> ledigeRom(Dato dato,Tid start, Tid slutt){
		DatabaseKommunikator database=new DatabaseKommunikator();
		database.kobleOpp();
		rom=database.hentMoterom();
		avtaler=database.hentAlleAvtaler(database.hentPersoner(), rom);
		//fjerner alle avtaler som har ikke kræsjer med fastsatt tid.
		//teller neddover fordi når det fjernes noe fra et arrayList så flyttes alt som er etter
		//en plass fremm, og da ville vi hoppet over plasser hvis vi gikk oppover.
		for (int i=avtaler.size()-1;i>=0;i--){
			//tester for dato
			if (avtaler.get(i).hentAvtaleDato().getDag()==dato.getDag()&&
					avtaler.get(i).hentAvtaleDato().getMnd()==dato.getMnd()&&
					avtaler.get(i).hentAvtaleDato().getAar()==dato.getAar()){
				
				//fjerner alle avtaler som slutter en av timene før fastsatt start tid
				//eller som starter en av timene etter fastsatt slutt tid
				if(avtaler.get(i).hentStarttid().hentTime()>slutt.hentTime()||
						avtaler.get(i).hentSluttid().hentTime()<start.hentTime()){
					avtaler.remove(i);
				}
				//fjerner alle avtaler som har samme start time som fastsatt slutt time,
				//men som har start minutt til etter fastsatt slutt minutt og motsatt
				else if((avtaler.get(i).hentStarttid().hentTime()==slutt.hentTime()&&
						avtaler.get(i).hentStarttid().hentMin()>slutt.hentMin())||(
						avtaler.get(i).hentSluttid().hentTime()==start.hentTime()&&
						avtaler.get(i).hentSluttid().hentMin()<slutt.hentMin())){
					avtaler.remove(i);
				}else{
					//går gjennom listen over rom og fjerner de som er resservert for en avtaler som kræsjer
					//med fastsatt start og slutt tid.
					for (int j=rom.size()-1;j>=0;j--){
						if(rom.get(j).hentRomID()==avtaler.get(i).hentRom().hentRomID()){
							rom.remove(j);
							//det hadde vært litt mer effektivt å skrive break her fordi det
							//ikke skal være flere avtaler per rom, men greit å bare sjekke
							//for sikkerhets skyld i tillfelle det er feil andre steder.
						}
					}
				}
			}else{
				avtaler.remove(i);
			}
		}
		return null;
	}
}
