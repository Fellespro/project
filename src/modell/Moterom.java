package modell;

public class Moterom {
	
	private int romid;
	private int kapasitet; 
	private String navn; 
	
	public Moterom(){
		romid = 0;
		kapasitet = 0;
	}
	
	public Moterom(int romid, int kapasitet, String navn) {
		this.romid=romid;
		this.kapasitet=kapasitet;
		this.navn=navn;
	}
	


	public int hentRomID() {
		return romid;
	}


	public String hentNavn() {
		return navn;
	}


	public void settNavn(String navn) {
		this.navn = navn;
	}


	public void settRomid(int romid) {
		this.romid = romid;
	}


	public int hentKapasitet() {
		return kapasitet;
	}


	public void settKapasitet(int kapasitet) {
		this.kapasitet = kapasitet;
	}
	
	public String toString(){
		return ""+romid;
	}
}