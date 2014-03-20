package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.DatabaseKommunikator;
import modell.*;

public class NyHendelse extends JPanel /*implements ActionListener, ListSelectionListener, DocumentListener, MouseListener*/ {
  
		//Opprette alle knappene som trengs
		private JButton hjemButton;
		
        private JTextField tittelText;
        
        private JTextField datoText;
        private JTextField starttidText;
        private JTextField sluttidText;
        
        private JTextField stedText;
        
        private JTextField antallAndreText;
        private JTextField totaltText;
        
        private JRadioButton moteRadio;
        
        private LedigeMoterom moteromList;
        private JScrollPane moteromScrollPane;
        private JButton oppdaterMoterom;
        
        private JTextArea beskrivelseText;
        private JScrollPane beskrivelseScroll;
        
        private JButton lagreButton;
        private JButton avbrytButton;
        
        private List<ActionListener> actionListeners;
        
        //private DatabaseKommunikator database;
        private Person person;
        private Avtale avtale;
        
        private String feilmelding;
        private boolean oppdatering;
        
        
        //trenger en oppdatering med cascade i modell
        public NyHendelse(/*DatabaseKommunikator database, */Person person, boolean oppdatering){
        	
		        //this.database = database;
		        this.person = person;
		        this.oppdatering = oppdatering;
		                
		 //Layout
                this.setLayout(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();
                c.insets = new Insets(5,2,5,2);
                c.weighty = 1;
                c.weightx = 1;
                c.fill = GridBagConstraints.BOTH;
    
         //Streker
                c.weightx = 0;
                c.weighty = 0;
                
                c.gridheight = 1;
                c.gridwidth = 11;
                c.gridx = 0;
                c.gridy = 1;
                this.add(new JSeparator(JSeparator.HORIZONTAL),c);
                c.gridwidth = 1;
                
                c.gridheight = 1;
                c.gridwidth = 9;
                c.gridx = 2;
                c.gridy = 3;
                this.add(new JSeparator(JSeparator.HORIZONTAL),c);
                c.gridwidth = 1;
                c.gridheight = 1;
                
                c.gridheight = 1;
                c.gridwidth = 11;
                c.gridx = 0;
                c.gridy = 5;
                this.add(new JSeparator(JSeparator.HORIZONTAL),c);
                c.gridwidth = 1;
                c.gridheight = 1;
                
                c.gridheight = 1;
                c.gridwidth = 9;
                c.gridx = 2;
                c.gridy = 12;
                this.add(new JSeparator(JSeparator.HORIZONTAL),c);
                c.gridwidth = 1;
                c.gridheight = 1;
                
                c.weightx = 1;
                c.weighty = 1;
                
        //felt-objekter
                
                c.fill = GridBagConstraints.VERTICAL;
                
                hjemButton = new JButton("Hjem");
                
                c.gridx = 3;
                c.gridy = 0;
                c.weightx = 0;
                c.weighty = 0;
                this.add(hjemButton,c);         
                c.weightx = 1;
                c.weighty = 1;
                
                c.fill = GridBagConstraints.BOTH;
                
        //tittel
                
                c.weighty = 0;
                c.gridheight = 1;
                c.gridx = 3;
                c.gridy = 2;
                this.add(new JLabel("Tittel:"),c);
                
                tittelText = new JTextField(10);
                c.gridx = 4;
                c.gridy = 2;
                this.add(tittelText,c);
                
        //dato og tidspunkt
                
                c.weighty = 0;
                c.gridx = 3;
                c.gridy = 4;
                this.add(new JLabel("Dato:"),c);
                
                datoText = new JTextField("dd-mm-yyyy");
                c.gridx = 4;
                c.gridy = 4;
                this.add(datoText,c);
                
                c.gridx = 3;
                c.gridy = 6;
                this.add(new JLabel("Starttid:"),c);
                
                starttidText = new JTextField("hh:mm");
                c.gridx = 4;
                c.gridy = 6;
                this.add(starttidText,c);
                
                c.gridx = 3;
                c.gridy = 8;
                this.add(new JLabel("Sluttid:"),c);
                
                sluttidText = new JTextField("hh:mm");
                c.gridx = 4;
                c.gridy = 8;
                this.add(sluttidText,c);
                
        //sted, antall og radiobuttons
                
                c.gridx = 3;
                c.gridy = 11;
                this.add(new JLabel("Sted/rom:"),c);
                
                stedText = new JTextField(10);
                c.gridx = 4;
                c.gridy = 11;
                this.add(stedText,c);
                
                c.anchor = GridBagConstraints.WEST;
                
                moteRadio = new JRadioButton();
                moteRadio.setName("moteRadio");

                c.gridx = 6;
                c.gridy = 11;
                this.add(moteRadio,c);
                
                c.gridx = 7;
                c.gridy = 11;
                this.add(new JLabel("Automatisk valg"), c);
                
                c.gridx = 3;
                c.gridy = 13;
                this.add(new JLabel("Inviter eksterne via e-post:"),c);
                
                antallAndreText = new JTextField("");
                antallAndreText.setText("");
                antallAndreText.setEditable(true);
                c.gridx = 4;
                c.gridy = 13;
                this.add(antallAndreText,c);
                
                c.gridx = 3;
                c.gridy = 15;
                this.add(new JLabel("Møterom:"), c);
                
                oppdaterMoterom = new JButton("Oppdater møterom");
                oppdaterMoterom.setName("oppdaterMoterom");
                c.gridx = 6;
                c.gridy = 15;
                this.add(oppdaterMoterom,c);
                
                moteromList = new LedigeMoterom();
                c.gridheight = 2;
                c.gridwidth = 2;
                c.gridx = 4;
                c.gridy = 15;
                c.fill = GridBagConstraints.BOTH;
                
                String[] stringliste = {"en", "to", "tre", "fire"};

                JList liste = new JList(stringliste); //((ListModel) moteromList.hentListe());
                
                moteromScrollPane = new JScrollPane();
                
                moteromScrollPane.add(liste);
                
                moteromScrollPane.setVisible(true);
                this.add(moteromScrollPane,c);
                
        //Beskrivelse
                c.weighty = 0;
                c.gridx = 3;
                c.gridy = 17;
                this.add(new JLabel("Beskrivelse"),c);
                
                c.weighty = 1;
                beskrivelseText = new JTextArea(3,30);
                beskrivelseText.setEditable(true);
                beskrivelseScroll = new JScrollPane(beskrivelseText);
                
                c.gridheight = 2;
                c.gridwidth = 7;
                c.gridx = 4;
                c.gridy = 17;
                this.add(beskrivelseScroll,c);
                
                c.fill = GridBagConstraints.VERTICAL;
                
        //Lagre og avbrytbuttons
                c.weighty = 0;
                lagreButton = new JButton("Lagre");
                lagreButton.setName("lagre");
                c.gridx = 3;
                c.gridy = 19;
//              c.gridheight = 1;
//              c.gridwidth = 1;
                this.add(lagreButton,c);
                
                avbrytButton = new JButton("Avbryt");
                avbrytButton.setName("avbryt");
                c.gridx = 4;
                c.gridy = 19;
                this.add(avbrytButton,c);
                /*
		        actionListeners = new ArrayList<ActionListener>();

                avbrytButton.addActionListener(this);
                lagreButton.addActionListener(this);
                moteromScrollPane.addMouseListener(this);
                oppdaterMoterom.addActionListener(this);
                antallAndreText.getDocument().addDocumentListener((this));
                hjemButton.addActionListener(this);
                moteRadio.addActionListener(this);
		                
		        actionListeners.add((lagreButton.getActionListeners())[0]);
		        actionListeners.add((avbrytButton.getActionListeners())[0]);
		        actionListeners.add((moteRadio.getActionListeners())[0]);
		        actionListeners.add((oppdaterMoterom.getActionListeners())[0]);
                /*
                //nullstillFelt();
                //setAutoOppforing();*/
                        
        }
        /*
        public void setOppdatering(boolean oppdatering) {
                this.oppdatering = oppdatering;
        }
        
        public boolean getOppdatering() {
                return oppdatering;
        }
        
        public void setAutoOppforing(){
                Calendar cal = Calendar.getInstance();
                Dato dato = new Dato(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR));
                Tid startTid = new Tid(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),0);
                Tid sluttTid;
                if(cal.get(Calendar.HOUR_OF_DAY)+1==24) sluttTid = new Tid(0, cal.get(Calendar.MINUTE),0);
                else sluttTid = new Tid(cal.get(Calendar.HOUR_OF_DAY)+1,cal.get(Calendar.MINUTE),0);
                datoText.setText(dato.toString());
                starttidText.setText(startTid.toString());
                sluttidText.setText(sluttTid.toString());
                tittelText.setText("møte");
        }
        */
        /*
        public void setAntallAnsatte(int antallAnsatte){
                this.antallAnsatte = antallAnsatte;
                setTotaltText();
        }
        public void setTotaltText(){
                totaltAntall = antallAnsatte + antallAndre + 1;
                totaltText.setText(""+totaltAntall);
        }
        *//*
        public Person getPerson(){
                return this.person;
        }
        public void setInviterte(PersonListe inviterte){
                PersonListe pListe = new PersonListe();
                for(int i=0; i<inviterte.antallPersoner();i++){
                        Person p = (Person)inviterte.hentPerson(i);
                        pListe.leggTilPerson(p);
                }
                for(int i = 0;i < pListe.antallPersoner();i++)
           		{
           				avtale.leggTilDeltaker(pListe.hentPerson(i));
           		}
        }
        public PersonListe getInviterte(){
        		PersonListe personListe = new PersonListe();
        		if(avtale instanceof Avtale){
        				for(int i = 0;i < avtale.hentAntallInterne();i++)
        				{
        					personListe.leggTilPerson(avtale.hentPerson(i));
        				}
        				return personListe;
        		}
        		return null;
        }
        
        public void lagre(){
        	
              feilmelding = "";
             /* String avtaleTittel = tittelText.getText(); 
              Person avtalePerson = person;
              Dato avtaleDato = new Dato(starttidText.getText()); 
              Tid avtaleStart = new Tid(starttidText.getText()); 
              Tid avtaleSlutt = new Tid(sluttidText.getText()); 
              Tid avtaleAltStart = new Tid(sluttidText.getText()); 
              Moterom avtaleRom = new Moterom();
              String avtaleBeskr = beskrivelseText.getText(); 
              String avtaleEndret = "";
              Respons avtaleResp = Respons.kanskje; 
              ArrayList<PersonListeElement> avtaleInterne = new ArrayList<PersonListeElement>();
              int avtaleAntDltkr = 0;
              ArrayList<String> avtaleEksterne = new ArrayList<String>(); 
              int avtaleEkstDltkr = 0;*/
              
              //Avtale avtale2 = new Avtale(avtaleTittel, avtalePerson, avtaleDato, avtaleStart, avtaleSlutt, avtaleAltStart, avtaleRom, avtaleBeskr,
            		  					 // avtaleEndret, avtaleResp, avtaleInterne, avtaleAntDltkr, avtaleEksterne, avtaleEkstDltkr);
              
              
              /*
              if(datoText.getText() != null){
                        try{
                        		avtaleDato = new Dato(datoText.getText());
                        	
                                Dato datoen = new Dato(datoText.getText());
                                avtale.settAvtaleDato(datoen);
                        }
                        catch(Exception e){
                                //TODO: lag dialog
                                feilmelding += "Dato: Feil format eller ugyldig dato. Husk å skrive på formatet dd-mm-yyyy \n";
                        }
                        
//             }
             if(starttidText.getText() != null){
                        try{
                        		avtaleStart = new Tid(starttidText.getText());
                        	
                                String[] starttidTabell = starttidText.getText().split(":");
                                int time = Integer.parseInt(starttidTabell[0]);
                                int min = Integer.parseInt(starttidTabell[1]);
                                @SuppressWarnings("deprecation")
								Tid tid = new Tid(time, min, 0);
                                avtale.settStarttid(tid);
                        }
                        catch(Exception e){
                                feilmelding += "Starttid: Ugyldig tidspunkt. Skriv på formatet hh:mm \n";
                        }
//              }
              if(sluttidText.getText() != null){
                        try{
                        		avtaleSlutt = new Tid(sluttidText.getText());
                        	
                                String[] sluttidTabell = sluttidText.getText().split(":");
                                int time = Integer.parseInt(sluttidTabell[0]);
                                int min = Integer.parseInt(sluttidTabell[1]);
                                Tid tid = new Tid(time, min, 0);
                                avtale.settSluttid(tid);
                        }
                        catch(Exception e){
                                feilmelding += "Sluttid: Ugyldig tidspunkt. Skriv på formatet hh:mm \n";
                        }
                        
                try {
                        if (!(avtale.hentStarttid().hentTime() < avtale.hentSluttid().hentTime()))
                                feilmelding += "Klokkeslett: Sluttid skjer før starttid";
                        else if(avtale.hentStarttid().hentTime() == avtale.hentSluttid().hentTime() &&
                        		!(avtale.hentStarttid().hentMin() < avtale.hentSluttid().hentMin()))
                        {
                        		feilmelding += "Klokkeslett: Sluttid skjer før starttid";
                        }
                } catch (Exception e) {
                }   
                if(!tittelText.getText().equals("")){
                        avtale.settAvtaleNavn(tittelText.getText());
                } 
                else{
                        feilmelding += "Tittel: Mangler tittel \n";
                }
                if(!stedText.getText().equals("")){
                       // avtale.settRom(stedText.getText()); /*sett sted tar inn et rom, ikke en tittel p� et rom*/
                    /*    if(avtale instanceof Avtale && !moteromList.isSelectionEmpty()){
                                ((Avtale)avtale).settRom((Moterom)moteromList.getSelectedValue());
                        }
                }
                else{
                        feilmelding += "Sted: Mangler sted \n";
                }
                if(!beskrivelseText.getText().equals(null)){
                        avtale.settBeskrivelse(beskrivelseText.getText());
                } 
                
                
          //   if(antallAndreText.getText() != null || antallAndreText.getText() != ""){
                        if(avtale instanceof Avtale){
                                try{
		                                if (Integer.parseInt(antallAndreText.getText()) < 0) 
		                                		throw new IllegalArgumentException();
		                                ((Avtale) avtale).settAntallEksterne(Integer.parseInt(antallAndreText.getText()));
                                }
                                catch (Exception e){
                                        feilmelding += "AntallAndre: Ugyldig format, bruk positive tall \n";
                                }
                              
                        }
             		}
              }
        }*/
       //* public void sendTilDatabase(boolean oppdatering){
                //Kaller på database og send oppforing
               // try {
                 //       if (!oppdatering) {
                   //            	avtale.settOpprettetAv(getPerson());
                     //           database.leggInnAvtale(avtale);
                       // } else {
                        //		/* m� lage oppdaterAvtale-funksjon i databasen*/
                         //       database.endreAvtale(getPerson(), avtale);
                       // }
                        
              //  } catch (SQLException e) {
               //         //TODO: feilmelding ?
              //          Feilmelding.visFeilmelding(this, "Feil med database:\n" + e.getMessage(), Feilmelding.FEIL_DATABASEFEIL);
               // }
       /* }*/
        /*
        public Avtale getAvtale(){
                return avtale;
        }
        
        public void nullstillFelt(){
                setAutoOppforing();
                tittelText.setText("");
                stedText.setText("");
                //antallAndreText.setText("");
                //totaltText.setText("");
                beskrivelseText.setText("");
        }
        
        public void setAvtale(Avtale avtale){
                if (oppdatering) 
                		this.avtale = avtale;
                if(avtale instanceof Avtale){
                        datoText.setText(avtale.hentAvtaleDato().toString());
                        starttidText.setText(avtale.hentStarttid().toString());
                        sluttidText.setText(avtale.hentSluttid().toString());
                        tittelText.setText(avtale.hentAvtaleNavn());
                        stedText.setText(avtale.hentRom().hentNavn());
                        antallAndreText.setText(""+(avtale).hentAntallEksterne());
                        totaltText.setText(""+(avtale).hentAntallDeltakere());
                        beskrivelseText.setText(avtale.hentBeskrivelse());
                        moteRadio.doClick();
                }
        
}

		@Override
		public void changedUpdate(DocumentEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == hjemButton){
                moteRadio.doClick();
                for (ActionListener l : this.actionListeners) {
                        l.actionPerformed(e);
                }
	        }
	        else if(e.getSource() == oppdaterMoterom){
	               
	        		moteromList.ledigeRom(new Dato(datoText.getText()), new Tid(starttidText.getText()), new Tid(sluttidText.getText()));
	                
	        /*}
	  
	        else if(e.getSource() == moteRadio){
	                if(moteRadio.isSelected()){
	                        if(datoText.getText() != "dd-mm-yyyy" && starttidText.getText() != "hh:mm" && sluttidText.getText() != "hh:mm"){
	                        	moteromList.ledigeRom(new Dato(datoText.getText()), new Tid(starttidText.getText()), new Tid(sluttidText.getText()));
	                      /*  }
	                        moteromScrollPane.setVisible(true);
	                        oppdaterMoterom.setEnabled(true);
	                        antallAndreText.setEditable(true);
	                        antallAndreText.setText("0");
	                }
	                for (ActionListener l : this.actionListeners) {
	                        l.actionPerformed(e);
	                }
	        }
	        else if(e.getSource() == lagreButton){
	                if (!oppdatering) {
	                        if(moteRadio.isSelected()){
	                                avtale = new Avtale(person, new Dato(datoText.getText()), new Tid(starttidText.getText()), new Tid(sluttidText.getText()));
	                        }
                        
	                }
	                //Lagrer verdier i objektet avtale
                lagre();
                
                if (feilmelding.equals("")){

                        //La CalendarSystem lytte for å skifte view
                        for (ActionListener l : this.actionListeners) {
                                l.actionPerformed(e);
                        }
        
                        //Lagre oppforing i databasen
             //           sendTilDatabase(oppdatering);
              //  } else {
             //           System.out.println(feilmelding);
             //           Feilmelding.visFeilmelding(this, feilmelding, Feilmelding.FEIL_UGYLDIG_UTFYLLING);
                }
        }
        else if(e.getSource() == avbrytButton){
                moteRadio.doClick();
                for (ActionListener l : this.actionListeners) {
                        l.actionPerformed(e);
                }
        }
}

		@Override
		public void insertUpdate(DocumentEvent e) {
			for(int i = 0; i< antallAndreText.getText().length(); i++){
                if(!Character.isDigit(antallAndreText.getText().charAt(i))){
                        //antallAndre = 0;
                        antallAndreText.setText("0");
                        //setTotaltText();
                        return;
                }
        }
        //antallAndre = Integer.parseInt(antallAndreText.getText());
        //setTotaltText();
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			if(antallAndreText.getText().length() != 0){
                for(int i = 0; i< antallAndreText.getText().length(); i++){
                        if(!Character.isDigit(antallAndreText.getText().charAt(i))){
                                //antallAndre = 0;
                                antallAndreText.setText("0");
                                //setTotaltText();
                                return;
                        }
                }
                //antallAndre = Integer.parseInt(antallAndreText.getText());
                //setTotaltText();
        }
        else {
                //antallAndre = 0;
                //setTotaltText();
                return;
        
        }
}

		@Override
		public void valueChanged(ListSelectionEvent e) {
				if(e.getSource() == moteromList){
		                try {
		                       // Moterom m = (Moterom)moteromList.getSelectedValue();
		                        //stedText.setText(m.hentNavn());
		                } catch (NullPointerException e1) {
		                		
		                }
				}
			
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}*/
			
}
