package modell;

public class Dato {
	private int year;
	private int month;
	private int day;
	
	public Dato(){
		year = 2000;
		month = 5;
		day = 13;
		
	}
	
	public String toString(){
		//Det er mulig at dersom f.eks. day<10, så må toString() skrive ut
		//day som 0x.
		//Altså: er 2014-2-7 gyldig?
		//Eller må det skrives som: 2014-02-07 ..?
		String out = "'"+year+"-"+month+"-"+day+"'";
		return out;
	}

}


//settere, gettere og constructor. og se om det mangler i forhold til databasen. 