package kalender;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import database.*;
import gui.*;
import modell.*;

public class Kalender implements ActionListener {

	private Person kalenderEier;	//Person sin kalender skal vises
	private Login login;
	private DatabaseKommunikator dk;

	//main - for å kunne kjøre applikasjonen
	public static void main(String[] args) throws InterruptedException{
		Kalender k = new Kalender();
	}

	public Kalender(){
		dk = new DatabaseKommunikator();
		kalenderEier = new Person();

		//Vis loginskjermen. this som parameter slik at this kan settes som actinolistener på login
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
				//Hurray! Få bort login og vis kalender!
				login.lukk();
				kalenderEier = dk.hentPerson(bruker);
				JOptionPane.showMessageDialog(null, "Du er nå logget inn som "+kalenderEier.getNavn());
				this.visKalender();
			}
			else{
				JOptionPane.showMessageDialog(null, "Feil brukernavn/passord!");
			}
			dk.lukk();
		}

	}

	private void visKalender() {

	}

}
