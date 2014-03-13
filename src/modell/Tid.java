package modell;

public class Tid {
	private int hour;
	private int minute;
	private int second;
	
	public Tid(){
		
	}
	
	public String toString(){
		String out = ""+hour+":"+minute+":"+second;
		return out;
	}

}
