

public class Tid {
	private int hour;
	private int minute;
	private int second;
	
	public Tid(){
		hour = 13;
		minute = 30;
		second = 0;
		
	}
	public Tid(int h, int m, int s){
		hour = h;
		minute = m;
		second = s;
	}
	
	public String toString(){
		String out = "'"+hour+":"+minute+":"+second+"'";
		return out;
	}

}
