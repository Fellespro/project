package modell;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

import database.DatabaseKommunikator;

public class LedigeMoterom extends DefaultListModel{
	private ArrayList<Moterom> rom;
	private ArrayList<Avtale> avtaler;
	

	public LedigeMoterom(){
		DatabaseKommunikator database=new DatabaseKommunikator();
		database.kobleOpp();
		rom=database.hentMoterom();
		avtaler=database.hentAlleAvtaler(database.hentPersoner(), rom);
		database.lukk();
	}
	public void ledigeRom(Dato dato,Tid start, Tid slutt){
		ArrayList<Moterom> rom=new ArrayList<Moterom>(this.rom);
		ArrayList<Avtale> avtaler=new ArrayList<Avtale>(this.avtaler);
		//fjerner alle avtaler som har ikke kræsjer med fastsatt tid.
		//teller neddover fordi når det fjernes noe fra et arrayList så flyttes alt som er etter
		//fjerner alle avtaler som har ikke krï¿½sjer med fastsatt tid.
		//teller neddover fordi nï¿½r det fjernes noe fra et arrayList sï¿½ flyttes alt som er etter
		//en plass fremm, og da ville vi hoppet over plasser hvis vi gikk oppover.
		for (int i=avtaler.size()-1;i>=0;i--){
			//tester for dato
			if (avtaler.get(i).hentAvtaleDato().getDag()==dato.getDag()&&
					avtaler.get(i).hentAvtaleDato().getMnd()==dato.getMnd()&&
					avtaler.get(i).hentAvtaleDato().getAar()==dato.getAar()){
				
				//fjerner alle avtaler som slutter en av timene fï¿½r fastsatt start tid
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
					//gï¿½r gjennom listen over rom og fjerner de som er resservert for en avtaler som krï¿½sjer
					//med fastsatt start og slutt tid.
					for (int j=rom.size()-1;j>=0;j--){
						if(rom.get(j).hentRomID()==avtaler.get(i).hentRom().hentRomID()){
							rom.remove(j);
							//det hadde vï¿½rt litt mer effektivt ï¿½ skrive break her fordi det
							//ikke skal vï¿½re flere avtaler per rom, men greit ï¿½ bare sjekke
							//for sikkerhets skyld i tillfelle det er feil andre steder.
						}
					}
				}
			}else{
				avtaler.remove(i);
			}
		}
	}
	
	public ArrayList<Moterom> hentListe()
	{
		return rom;
	}
	
	public Moterom getSelectedValue()
	{
		return null;		
	}
	
	public boolean isSelectionEmpty()
	{
		if(rom.size() == 0)
		{
			return true;
		}
		return false;
	}
}
