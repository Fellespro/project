package GUI;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class Kalendertabell extends JPanel{
	
	private JTable[] tabell;
	private String[] kolonnenavn, radnavn;
	private GronnCelle gronn;
	private BlaaCelle blaa;
	private GulCelle gul;
	private HvitCelle hvit;
	
	public Kalendertabell(){
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		tabell = new JTable[17];
		
		for(int i=0; i<tabell.length; i++){
		tabell[i]=new JTable(1, 8);
		tabell[i].setRowSelectionAllowed(false);
		tabell[i].setGridColor(Color.black);
		add(tabell[i]);
		}
		
		setUkedag();
		setKlokkeslett();
		
		gronn = new GronnCelle();
		blaa = new BlaaCelle();
		gul = new GulCelle();
		hvit = new HvitCelle();
		
	}
	//Lager tabell med ukedager og legger ukedagene inn i tabell.
	public void setUkedag(){
		kolonnenavn=new String[]{"Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "Lørdag", "Søndag"};
		for(int i=0;i<kolonnenavn.length; i++)
			tabell[0].setValueAt(kolonnenavn[i], 0, i+1);
	}
	
	//Lager tabell med klokkeslett og legger det inn i tabell.
	public void setKlokkeslett(){
		radnavn=new String[16];
		for(int i=0; i<radnavn.length; i++){
			radnavn[i]=String.format("%02d", i+6)+"00";
			tabell[i+1].setValueAt(radnavn[i], 0, 0);
		}		
	}
	
	//Setter farge på avtaleområde
	public void setFarge(int dagnr, int fraklokke, int tilklokke, int fargenr){
		
		 //tabell[3].getColumnModel().getColumn(3).setCellRenderer(blaa);
		int antall = tilklokke-fraklokke;
		
		if(fargenr==1){
		for (int i = 0; i<=antall; i++)
            tabell[fraklokke+i].getColumnModel().getColumn(dagnr).setCellRenderer(gronn);
		}
		else if(fargenr==2){
			for (int i = 0; i<=antall; i++)
				tabell[fraklokke+i].getColumnModel().getColumn(dagnr).setCellRenderer(blaa);
		}
		else if(fargenr==3){
			for (int i = 0; i<=antall; i++)
				tabell[fraklokke+i].getColumnModel().getColumn(dagnr).setCellRenderer(gul);
		}
		else if(fargenr==4){
			for (int i = 0; i<=antall; i++)
				tabell[fraklokke+i].getColumnModel().getColumn(dagnr).setCellRenderer(hvit);
		}
		
	}
	
	//Åpner avtaleboks
	public void apneAvtaleBoks(){
		
	}
	
	/*public static void main(String[] args){
		JFrame f = new JFrame();
		Kalendertabell kt = new Kalendertabell();
		kt.setFarge(3, 3, 4, 2);
		f.add(kt);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}*/
	
	public class BlaaCelle extends DefaultTableCellRenderer {
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        cellComponent.setBackground(Color.blue);
	        return cellComponent;
	    }
	}
	
	public class GulCelle extends DefaultTableCellRenderer {
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        cellComponent.setBackground(Color.yellow);
	        return cellComponent;
	    }
	}
	
	public class GronnCelle extends DefaultTableCellRenderer {
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        cellComponent.setBackground(Color.green);
	        return cellComponent;
	    }
	}
	
	public class HvitCelle extends DefaultTableCellRenderer {
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        cellComponent.setBackground(Color.white);
	        return cellComponent;
	    }
	}
}
