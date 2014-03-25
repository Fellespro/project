package kalender;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;

import javax.mail.MessagingException;
import javax.swing.JFrame;
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

public class Kalender implements ActionListener, MouseListener {

	private Person kalenderEier;	//Person sin kalender skal vises
	private Login login;
	private DatabaseKommunikator dk;
	private modell.Kalender mkalender;
	private Kalendertabell ktabell;
	private VisAvtale visavtale;
	private NyHendelse nyhendelse;
	private Avtale a;
	private ArrayList<Avtale> ukeAvtalerListe;


	//main - for � kunne kj�re applikasjonen
	public static void main(String[] args) throws InterruptedException{
		Kalender k = new Kalender();
	}

	public Kalender(){
		dk = new DatabaseKommunikator();
		kalenderEier = new Person();
		mkalender = new modell.Kalender();

		//Vis loginskjermen. this som parameter slik at this kan settes som actinolistener p� login
		login = new Login(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		System.out.println(e.getActionCommand());
				
		
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
		else if(e.getActionCommand().equals("Ny avtale")){
			JFrame frame = new JFrame();
			nyhendelse = new NyHendelse(frame, this, kalenderEier, true);
			nyhendelse.setAutoOppforing();
			frame.setSize(400,600);
			frame.add(nyhendelse);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setVisible(true);
			
		}
		else if(e.getActionCommand().equals("Lagre")){
			System.out.println("Button Lagre pressed (lagrer registrert informasjon i klassevariabelen avtale)");
			Avtale a = nyhendelse.lagre();
			if(a.hentAntallEksterne()>0){
				EpostSender es;
				try {
					es = new EpostSender();
					es.kobleOpp();
					es.sendMelding(a);
					es.kobleFra();
				} catch (MessagingException e1) {
					e1.printStackTrace();
				}
			}
			
			//TODO: legg avtalen til i avtalelistene
			//TODO: vis avtalen i kalendertabell!
			
			
			//TODO: lagre til db
		}
		else if(e.getActionCommand().equals("Logg ut")){
			System.exit(0);
        }		
		else if(visavtale !=null){
			
        	if(e.getSource() == visavtale.hentEndreKnapp()){
            	System.out.println("endre");
            }
            else if(e.getSource() == visavtale.hentSlettKnapp()){
                    
                   	Object[] options ={"OK","Avbryt"};
                    int n = JOptionPane.showOptionDialog(visavtale.hentRamme(), "Er du sikker p� at du vil slette?\n", "Bekreft sletting", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                    if(n == JOptionPane.OK_OPTION){
                    	for(int i=0;i<ukeAvtalerListe.size(); i++){
                    		if(a.hentAvtaleID()==ukeAvtalerListe.get(i).hentAvtaleID()){
                    			ukeAvtalerListe.remove(i);
                    			break;
                    		}
                    	}
                    	ktabell.settFarge(Utilities.getDayOfWeek(a), a.hentStarttid().hentTime(), a.hentSluttid().hentTime(), 4, "");
                    	visavtale.fjernRamme();
                    	//Husk å fjerne avtale i modell.Kalender.avtaleliste og i database
                    }
            }
            else if(e.getSource() == visavtale.hentAvbrytKnapp()){
            	visavtale.fjernRamme();
            }
        	visavtale=null;
        }
		
        else if (e.getSource() == ktabell.getTP().getVenstrePil()) {
			System.out.println("venstre");
			ktabell.getTP().decrease();
			oppdaterKalender(ktabell.getTP().hentUkenr());
		}
		
        else if (e.getSource() == ktabell.getTP().getHoyrePil()) {
			System.out.println("hoyre");
			ktabell.getTP().increment();
			oppdaterKalender(ktabell.getTP().hentUkenr());
        }

	}

	public void visKalender() {
		ktabell = new Kalendertabell(this, kalenderEier);
		ktabell.visTabell();
		ukeAvtalerListe = mkalender.hentUkeAvtaler(mkalender.hentPersonAvtaler(kalenderEier,2014, Utilities.getCurrentWeek()), 2014, Utilities.getCurrentWeek());
		mkalender.setPersonUkeAvtaler(ukeAvtalerListe);
		mkalender.setPerson(kalenderEier);
		for(int i=0; i<ukeAvtalerListe.size(); i++){
			a = ukeAvtalerListe.get(i);
			ktabell.settFarge(Utilities.getDayOfWeek(a), a.hentStarttid().hentTime(), a.hentSluttid().hentTime(), 2, a.hentAvtaleNavn());
			//ktabell.settFarge(Utilities.getTodaysDayOfWeek(), a.hentStarttid().hentTime(), a.hentSluttid().hentTime(), 2, a.hentAvtaleNavn());
			//ktabell.settFarge(Utilities.getDayOfWeek(a), a.hentStarttid().getHours(), a.hentSluttid().getHours(), 2, a.hentAvtaleNavn());
		}
	}
	
	public void oppdaterKalender(int nr) {
		for(int i=0; i<ukeAvtalerListe.size(); i++){
			a = ukeAvtalerListe.get(i);
			ktabell.settFarge(Utilities.getDayOfWeek(a), a.hentStarttid().hentTime(), a.hentSluttid().hentTime(), 4, "");
		}
		ukeAvtalerListe = mkalender.hentUkeAvtaler(mkalender.hentPersonAvtaler(kalenderEier,2014, nr), 2014, nr);
		mkalender.setPersonUkeAvtaler(ukeAvtalerListe);
		mkalender.setPerson(kalenderEier);
		for(int i=0; i<ukeAvtalerListe.size(); i++){
			a = ukeAvtalerListe.get(i);
			ktabell.settFarge(Utilities.getDayOfWeek(a), a.hentStarttid().hentTime(), a.hentSluttid().hentTime(), 2, a.hentAvtaleNavn());
		}
		
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		
		int rad = ktabell.hentRad();
		int kolonne = ktabell.hentKolonne();
		ktabell.fjernSeleksjon();
		
		Dato d = Utilities.hentDato(mkalender.getPersonUkeAvtaler());
		
		if(!(d==null)){
			int dag = kolonne+d.getDag()-1;
			int time = rad-1;

			
			//Finn avtalen som skal vises
			for(int i=0; i<mkalender.getPersonUkeAvtaler().size(); i++){
				a = mkalender.getPersonUkeAvtaler().get(i);
				if(dag==a.hentAvtaleDato().getDag()){
					if(time==a.hentStarttid().hentTime()){
						visavtale = new VisAvtale(this);
						visavtale.setOppforing(a);
						break;
					}
				}
			}
		}
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void setWeek(int ukeNr) {
		// TODO Auto-generated method stub
		
	}

}
