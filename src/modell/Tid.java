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
	public int hentTime(){
		return time;
	}
	public int hentMin(){
		return min;
	}
	public int hentSek(){
		return sec;
	}
	public Tid(String tid){
		
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
