package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicArrowButton;

import utilities.Utilities;

import kalender.Kalender;


public class ToppPanel extends JPanel{
	
	
	public BasicArrowButton getVenstrePil() {
		return venstrePil;
	}


	public BasicArrowButton getHoyrePil() {
		return hoyrePil;
	}



	private JLabel ukeTekst, ukeNrTekst, brukernavnTekst, brukernavn;
	private JButton loggUtknapp, nyAvtaleKnapp;
	private BasicArrowButton venstrePil, hoyrePil;
	private int ukeNr; //getter og setter
	private Kalender kalender;
	
	
	
	public ToppPanel(String brukernavnEkstern, Kalender k) {
		// 8 deler. fra venstre mot h�yre 
		this.kalender= k;
		//0. NyAvtaleknapp 
		nyAvtaleKnapp = new JButton();
		nyAvtaleKnapp.setName("nyAvtale");
		nyAvtaleKnapp.setText("Ny avtale"); 
		nyAvtaleKnapp.addActionListener(k); //kalender lytter p� whatsuuup
		add(nyAvtaleKnapp);

		
		//1
		ukeTekst = new JLabel();
		ukeTekst.setName("ukeTekst");
		ukeTekst.setText("Ukenummer: ");
		ukeTekst.setHorizontalAlignment(JLabel.CENTER);
		add(ukeTekst);  
		
		//2 venstre
		venstrePil = new BasicArrowButton(BasicArrowButton.WEST);
		venstrePil.addActionListener(k);
		add(venstrePil);
		
		//3 ....ukenr: feks uke 8
		ukeNr = Utilities.getCurrentWeek(); //lage begrensning? 
		ukeNrTekst = new JLabel();
		ukeNrTekst.setHorizontalAlignment(JLabel.CENTER);
		ukeNrTekst.setName("ukeNrTekst");
		ukeNrTekst.setText(String.valueOf(ukeNr)); //denne m� endres
		add(ukeNrTekst);
		
		
				
		//4 hoyre
		hoyrePil = new BasicArrowButton(BasicArrowButton.EAST);
		hoyrePil.addActionListener(k);
		add(hoyrePil);
	    
		
	    // 5.........du er logget inn med med brukernavn:
		brukernavnTekst = new JLabel();
		brukernavnTekst.setName("brukernavnTekst");
		brukernavnTekst.setText("Logget inn med: ");
		brukernavnTekst.setHorizontalAlignment(JLabel.RIGHT);
		add(brukernavnTekst);
	    
	    //6 .......  brukernavnet
		brukernavn = new JLabel();
		brukernavn.setName("brukernavn");
		brukernavn.setText(brukernavnEkstern); //noe s�nt?
		brukernavn.setHorizontalAlignment(JLabel.LEFT);
		add(brukernavn);
	    
	    //7 loggUtknapp 
	    loggUtknapp = new JButton();
	    loggUtknapp.setName("loggUt");
	    loggUtknapp.setText("Logg ut"); 
	    loggUtknapp.addActionListener(k); //kalender lytter p� whatsuuup
	    add(loggUtknapp);
	    
	    setLayout(new GridLayout(0,8));
	}
	
	
	
//public static void main(String[] args) {
//		JFrame frame = new JFrame(); //frame ligger �verst for � kunne ha panel. knapper p� panel.
//		JPanel panel = new ToppPanel("sybolt");
//		
//		
//	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	    
//	   	frame.add(panel);
//	   	frame.pack();
//	   	frame.setVisible(true);
//		frame.setSize(1200, 200);
//		
//		
//
//	}


public void increment() {
	this.ukeNr++;
	this.ukeNrTekst.setText(String.valueOf(ukeNr));
	kalender.oppdaterKalender(ukeNr);
}


public void decrease() {
	this.ukeNr--;
	this.ukeNrTekst.setText(String.valueOf(ukeNr));
	kalender.oppdaterKalender(ukeNr);
}

public int hentUkenr(){
	return ukeNr;
}
}
