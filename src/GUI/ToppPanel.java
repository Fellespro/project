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

import kalender.Kalender;


public class ToppPanel extends JPanel implements ActionListener  {
	private JLabel ukeTekst, ukeNrTekst, brukernavnTekst, brukernavn;
	private JButton knapp;
	private BasicArrowButton venstrePil, hoyrePil;
	private int ukeNr; //getter og setter
	
	
	
	public ToppPanel(String brukernavnEkstern, Kalender k) {
		// 7 deler. fra venstre mot høyre 
		
		//1
		ukeTekst = new JLabel();
		ukeTekst.setName("ukeTekst");
		ukeTekst.setText("Ukenummer: ");
		add(ukeTekst);  
		
		//2 venstre
		venstrePil = new BasicArrowButton(BasicArrowButton.WEST);
		venstrePil.addActionListener(this);
		add(venstrePil);
		
		//3 ....ukenr: feks uke 8
		ukeNr = 1; //lage begrensning? 
		ukeNrTekst = new JLabel();
		ukeNrTekst.setName("ukeNrTekst");
		ukeNrTekst.setText(String.valueOf(ukeNr)); //denne må endres
		add(ukeNrTekst);
				
		//4 hoyre
		hoyrePil = new BasicArrowButton(BasicArrowButton.EAST);
		hoyrePil.addActionListener(this);
		add(hoyrePil);
	    
		
	    // 5.........du er logget inn med med brukernavn:
		brukernavnTekst = new JLabel();
		brukernavnTekst.setName("brukernavnTekst");
		brukernavnTekst.setText("Du er logget inn med brukernavn: ");
		add(brukernavnTekst);
	    
	    //6 .......  brukernavnet
		brukernavn = new JLabel();
		brukernavn.setName("brukernavn");
		brukernavn.setText(brukernavnEkstern); //noe sånt?
		add(brukernavn);
	    
	    //7 loggutknapp 
	    knapp = new JButton();
	    knapp.setName("loggUt");
	    knapp.setText("Logg ut"); 
	    knapp.addActionListener(k); //kalender lytter på whatsuuup
	    add(knapp);
	    
	    setLayout(new GridLayout(0,7));
	}
	
	
	
//public static void main(String[] args) {
//		JFrame frame = new JFrame(); //frame ligger øverst for å kunne ha panel. knapper på panel.
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



@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	if (e.getSource() == venstrePil) {
		ukeNr--;
		System.out.println(ukeNr);
		ukeNrTekst.setText(String.valueOf(ukeNr));
	}
	if (e.getSource() == hoyrePil) {
		ukeNr++;
		System.out.println(ukeNr);
		ukeNrTekst.setText(String.valueOf(ukeNr));
	}
	if (e.getSource() == knapp) {
		System.out.println("logg ut");
		
	}
}

}
