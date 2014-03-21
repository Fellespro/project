package modell;

import java.sql.Time;

public class Tid {
	private int time;
	private int min;
	private int sec;
	
	public Tid(){
		Time h = new Time(0);
		
		time = h.getHours();
		min = h.getMinutes();
		sec = h.getSeconds();
		
	}
	public Tid(int h, int m, int s){
		time = h;
		min = m;
		sec = s;
	}
	public Tid(String tidspunkt){
		String[] del1 = tidspunkt.split(":");
		String[] del2 = del1[1].split(":");
		System.out.println(del1[0] + del2[0] + del2[1]);
		time = Integer.parseInt(del1[0]);
		min = Integer.parseInt(del2[0]);
		sec = Integer.parseInt(del2[1]);
	}
	public int hentTime(){
		return time;
	}
	public int hentMin(){
		return min;
	}
	public int hentSek(){
		return sec;
	}
	public void setTime(int time){
		this.time=time;
	}
	public void setMin(int min){
		this.min=min;
	}
	public void setSek(int sec){
		this.sec=sec;
	}
	
	public String toString(){
		String out = "'"+time+":"+min+":"+sec+"'";
		return out;
	}

}
