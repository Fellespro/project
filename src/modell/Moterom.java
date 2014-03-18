

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
	
	
	
	public int getRomID() {
		return romid;
	}
	
	
	public String getNavn() {
		return navn;
	}
	
	
	public void setNavn(String navn) {
		this.navn = navn;
	}
	
	
	public void setRomid(int romid) {
		this.romid = romid;
	}
	
	
	public int getKapasitet() {
		return kapasitet;
	}
	
	
	public void setKapasitet(int kapasitet) {
		this.kapasitet = kapasitet;
	}
}