package GUI;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

public class Kalendertabell extends JPanel implements ListSelectionListener{
	
	private JTable[] tabell;
	private String[] kolonnenavn, radnavn;
	private GronnCelle gronn;
	private BlaaCelle blaa;
	private GulCelle gul;
	private HvitCelle hvit;
	
	public Kalendertabell(){
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		lagTabell();
		settInnUkedag();
		settInnKlokkeslett();
		
		gronn = new GronnCelle();
		blaa = new BlaaCelle();
		gul = new GulCelle();
		hvit = new HvitCelle();
		
	}
	//Lager liste med ukedager og legger ukedagene inn i tabell.
	public void settInnUkedag(){
		kolonnenavn=new String[]{"Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "Lørdag", "Søndag"};
		for(int i=0;i<kolonnenavn.length; i++)
			tabell[0].setValueAt(kolonnenavn[i], 0, i+1);
	}
	
	//Lager liste med klokkeslett og legger det inn i tabell.
	public void settInnKlokkeslett(){
		radnavn=new String[16];
		for(int i=0; i<radnavn.length; i++){
			radnavn[i]=String.format("%02d", i+6)+"00";
			tabell[i+1].setValueAt(radnavn[i], 0, 0);
			//Gjør celle ikke fokuserbar.
			tabell[i+1].getColumnModel().getColumn(0).setCellRenderer(new UfokusertCelle());
		}		
	}
	
	//Lager selve tabellen og legger til ListSelectionListener.
	public void lagTabell(){
		tabell = new JTable[17];
		
		for(int i=0; i<tabell.length; i++){
		tabell[i]=new JTable(1, 8);
		tabell[i].setRowSelectionAllowed(false);
		tabell[i].setGridColor(Color.black);
		tabell[i].getSelectionModel().addListSelectionListener(this);
		add(tabell[i]);
		}
		//Gjør øverste rad ikke fokuserbar.
		tabell[0].setFocusable(false);
	}
	
	//Setter farge på avtaleområde.
	public void setFarge(int dagnr, int fraklokke, int tilklokke, int fargenr, String motenavn){
		tabell[fraklokke].setValueAt(motenavn, 0, dagnr);
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
	//Test
	/*
	public static void main(String[] args){
		JFrame f = new JFrame();
		Kalendertabell kt = new Kalendertabell();
		kt.setFarge(3, 3, 4, 2, "Møte");
		f.add(kt);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}
	*/
	
	//Farger celler blå.
	public class BlaaCelle extends DefaultTableCellRenderer {
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        cellComponent.setBackground(Color.blue);
	        return cellComponent;
	    }
	}
	//Farger celler gul.
	public class GulCelle extends DefaultTableCellRenderer {
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        cellComponent.setBackground(Color.yellow);
	        return cellComponent;
	    }
	}
	//Farger celler grønn.
	public class GronnCelle extends DefaultTableCellRenderer {
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        cellComponent.setBackground(Color.green);
	        return cellComponent;
	    }
	}
	//Farger celler hvit.
	public class HvitCelle extends DefaultTableCellRenderer {
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        cellComponent.setBackground(Color.white);
	        return cellComponent;
	    }
	}
	//Gjør celle ufokuserbar. Velger neste celle i raden istedenfor.
	class UfokusertCelle extends DefaultTableCellRenderer  
	  {  
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)  
	    {  
	      Component comp = super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);  
	      if(hasFocus) table.changeSelection(row,column+1,false,false);  
	      return comp;  
	    }  
	  } 

	//Åpner avtaleboks.
	public void valueChanged(ListSelectionEvent e) {
		System.out.println("Valgt celle");
		
	}
}
