package modell;

import java.util.Calendar;

//mangler muligens en konstrukt�r med en string som argument. 
public class Dato {
	private int dag;
	private int mnd;
	private int aar;
	
	public Dato(){
		Calendar c = Calendar.getInstance();
		
		aar = c.getTime().getYear();
		mnd = c.getTime().getMonth();
		dag = c.getTime().getDate();
		
	}
	
	public Dato(int dag, int mnd, int aar) {
		sjekk(dag, mnd, aar);
		this.dag= dag; 
		this.mnd = mnd;
		this.aar = aar;
	}
	
	public Dato(String dato){
		if(dato==null){
			dag=0;
			mnd=0;
			aar=0;
		}
		else{
			String[] resultat = dato.split("\\.");
			aar = Integer.parseInt(resultat[0]);
			mnd = Integer.parseInt(resultat[1]);
			dag = Integer.parseInt(resultat[2]);
			
		}
		
	}
	
		
	
	public int getDag() {
		return dag;
	}

	public void setDag(int dag) {
		sjekk(dag, mnd, aar);
		this.dag = dag;
	}

	public int getMnd() {
		return mnd;
	}

	public void setMnd(int mnd) {
		sjekk(dag, mnd, aar);
		this.mnd = mnd;
	}

	public int getAar() {
		return aar;
	}

	public void setAar(int aar) {
		sjekk(dag, mnd, aar);
		this.aar = aar;
	}

	public String toString(){
		return dag+ "." + mnd + "." + aar;
	}
	
		
	private void sjekk(int dag, int mnd, int aar) {
		if (dag < 1 || dag > dagerManed(mnd, aar)){
			throw new IllegalArgumentException("Feil tall p� dag:" + dag);
		}
		if (mnd < 1 || mnd > 12) {
			throw new IllegalArgumentException("Feil tall p� m�ned:" + mnd);
		}
		
	}
	
	public int getUkenr()
	{
		Calendar kalender = Calendar.getInstance();
		kalender.set(this.getAar(), this.getMnd()-1, this.getDag());
		int uke = kalender.get(Calendar.WEEK_OF_YEAR);
		return uke;
	}


//hjelper sjekk()

private int dagerManed(int mnd, int aar){
	switch (mnd) {
	case 1: case 3: case 5: case 7: case 8: case 10: case 12:
		return 31;
	case 4: case 6: case 9: case 11:
		return 30;
	case 2: 
		if (erSkuddAar(aar)) {
			return 29;
		}
		else return 28;
	}
	return -1;
}


private boolean erSkuddAar(int aar) {
	return (aar % 4 == 0 && (aar % 400 == 0 || aar % 100 != 0));
}


}//end body 


//settere, gettere og constructor. og se om det mangler i forhold til databasen. 
