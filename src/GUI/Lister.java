package GUI;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.*;
import modell.*;

public class Lister extends JPanel implements ListSelectionListener{
	private JList<Object> liste;
	private DatabaseKommunikator db;
	
	public Lister(){
		db=new DatabaseKommunikator();
		db.kobleOpp();
//		personer=db.hentPersoner();
//		personer2=new Person[personer.size()];
//		personer2=personer.toArray(personer2);
//		liste=new JList<Person>(personer2);
//		liste.addListSelectionListener(this);
//		add(liste);
	}
	public void personListe(){
		lagListe(db.hentPersoner().toArray());
	}
	public void romListe(){
		lagListe(db.hentMoterom().toArray());
	}
	/*public void gruppeListe(){
		lagListe(db.hentPersoner().toArray());
	}*/
	private void lagListe(Object[] listen){
		liste=new JList<Object>(listen);
		liste.addListSelectionListener(this);
		add(liste);
	}
//	public static void main (String[] args){
//		JFrame frame=new JFrame();
//		AnsattListe2 panel=new AnsattListe2 ();
//		panel.personListe();
//		
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	    
//	   	frame.add(panel);
//	   	frame.pack();
//	   	frame.setVisible(true);
//		frame.setSize(1200, 200);
//	}
	public void valueChanged(ListSelectionEvent e){
	}
}
