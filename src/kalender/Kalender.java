package kalender;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import utilities.Utilities;

import database.*;
import GUI.*;
import modell.*;

public class Kalender implements ActionListener {

	private Person kalenderEier;	//Person sin kalender skal vises
	private Login login;
	private DatabaseKommunikator dk;
	private modell.Kalender mkalender;
	private Kalendertabell ktabell;

	//main - for � kunne kj�re applikasjonen
	public static void main(String[] args) throws InterruptedException{
		Kalender k = new Kalender();
	}

	public Kalender(){
		dk = new DatabaseKommunikator();
		kalenderEier = new Person();
		mkalender = new modell.Kalender();
		ktabell = new Kalendertabell(this);

		//Vis loginskjermen. this som parameter slik at this kan settes som actinolistener p� login
		login = new Login(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//Cancel
		if(e.getActionCommand().equals("Cancel")){
			System.exit(0);
		}
		//Logg inn
		else if(e.getActionCommand().equals("Logg inn")){

			String bruker = login.getBrukernavn();
			String passord = login.getPassord();
			dk.kobleOpp();

			if(dk.erGyldigInnlogging(bruker, passord)){
				//Hurray! F� bort login og vis kalender!
				login.lukk();
				kalenderEier = dk.hentPerson(bruker);
				JOptionPane.showMessageDialog(null, "Du er no logget inn som "+kalenderEier.getNavn());
				this.visKalender();
			}
			else{
				JOptionPane.showMessageDialog(null, "Feil brukernavn/passord!");
			}
			dk.lukk();
		}

	}

	private void visKalender() {
		ktabell.visTabell();
		ArrayList<Avtale> ukeAvtalerListe = mkalender.hentUkeAvtaler(mkalender.hentPersonAvtaler(kalenderEier,2014, Utilities.getCurrentWeek()), 2014, Utilities.getCurrentWeek());
		System.out.println("Antall ukeavtaler[kkalender] " +ukeAvtalerListe.size());
		for(int i=0; i<ukeAvtalerListe.size(); i++){
			Avtale a = ukeAvtalerListe.get(i);
<<<<<<< HEAD
			ktabell.settFarge(Utilities.getTodaysDayOfWeek(), a.hentStarttid().hentTime(), a.hentSluttid().hentTime(), 2, a.hentAvtaleNavn());
=======
			ktabell.settFarge(Utilities.getDayOfWeek(a), a.hentStarttid().getHours(), a.hentSluttid().getHours(), 2, a.hentAvtaleNavn());
>>>>>>> origin
		}
	}

}
