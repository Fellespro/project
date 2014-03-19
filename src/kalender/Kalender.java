package kalender;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

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
		ktabell = new Kalendertabell();

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
				JOptionPane.showMessageDialog(null, "Du er n� logget inn som "+kalenderEier.getNavn());
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
	}

}
