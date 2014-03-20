package GUI;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.*;
import modell.*;

public class Lister extends JPanel implements ListSelectionListener{
	private JList<Object> liste;
	private JCheckBox boks;
	private DatabaseKommunikator db;
	private JScrollPane pane;
	
	public Lister(){
//		db=new DatabaseKommunikator();
//		db.kobleOpp();
//		personer=db.hentPersoner();
//		personer2=new Person[personer.size()];
//		personer2=personer.toArray(personer2);
//		liste=new JList<Person>(personer2);
//		liste.addListSelectionListener(this);
//		add(liste);
	}
	public void personListe(){
		db=new DatabaseKommunikator();
		db.kobleOpp();
		lagListe(db.hentPersoner().toArray());
		db.lukk();
	}
	public void gruppeListe(){
		db=new DatabaseKommunikator();
		db.kobleOpp();
		lagListe(db.hentGrupper(db.hentPersoner()).toArray());
		db.lukk();
	}
	private void lagListe(Object[] listen){
		liste=new JList<Object>(listen);
		liste.addListSelectionListener(this);
		//liste.setAlignmentX(LEFT_ALIGNMENT);
		//liste.setFixedCellWidth(140);
		//liste.setMaximumSize(new Dimension(140,100));
		pane=new JScrollPane();
		pane.setViewportView(liste);
		pane.setMaximumSize(new Dimension(140,160));
		add(pane);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	public void romListe(Dato dato,Tid start,Tid slutt){
		LedigeMoterom rom=new LedigeMoterom();
		rom.ledigeRom(dato, start, slutt);
		lagRomListe(rom.hentListe().toArray());
	}
	private void lagRomListe(Object[] listen){
		boks=new JCheckBox("velg rom automagisk", true);
		boks.setAlignmentX(CENTER_ALIGNMENT);
		add(boks);
		lagListe(listen);
		
	}
//	public static void main (String[] args){
//		JFrame frame=new JFrame();
//		Lister person=new Lister ();
//		Lister grupper=new Lister();
//		Lister rom=new Lister();
//		grupper.gruppeListe();
//		person.personListe();
//		rom.romListe(new Dato(21,3,2014), new Tid(9,0,0), new Tid(23,59,59));
//		
//		JPanel panel=new JPanel();
//		panel.add(person);
//		panel.add(grupper);
//		panel.add(rom);
//		panel.setLayout(new BoxLayout(panel,BoxLayout.LINE_AXIS));
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	    
//	   	frame.add(panel);
//	   	frame.pack();
//	   	frame.setVisible(true);
//		frame.setSize(1200, 800);
//	}
	public void valueChanged(ListSelectionEvent e){
		boks.setSelected(false);
	}
}
