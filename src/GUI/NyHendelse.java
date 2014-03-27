package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.DatabaseKommunikator;

import modell.*;

public class NyHendelse extends JPanel implements ActionListener, ListSelectionListener, PropertyChangeListener/*, DocumentListener, MouseListener*/ {
  
		private JFrame frame;
	
		private JButton hjemButton;
		
        private JTextField tittelText;
        
        private JTextField datoText;
        private JTextField starttidText;
        private JTextField sluttidText;
        
        private PersonListe invitertListe;
        private JList invitertJListe;
        private JScrollPane inviterteScrollPane;

        private JTextField andreText;
        
        private JTextField stedText;
        
        private JRadioButton moteRadio;
        
        private LedigeMoterom moteromListe;
        private JList moteromJListe;
        private JScrollPane moteromScrollPane;
        private JButton oppdaterMoterom;
        
        private JTextArea beskrivelseText;
        private JScrollPane beskrivelseScroll;
        
        private JButton lagreButton;
        private JButton avbrytButton;
        
        private List<ActionListener> actionListeners;
        
        private DatabaseKommunikator database;
        private Person person;
        private Avtale avtale;
        
        private String feilmelding;
        private boolean oppdatering;
        
        
        public NyHendelse(JFrame frame, DatabaseKommunikator database, kalender.Kalender k, Person person){
        	
		        this.database = database;
        		this.frame = frame;
		        this.person = person;
		        this.oppdatering = false;
		        
		        this.setupGUI();

                avbrytButton.addActionListener(this);
                lagreButton.addActionListener(k);
                hjemButton.addActionListener(this);
                oppdaterMoterom.addActionListener(this);
                moteRadio.addActionListener(this);
                invitertJListe.addPropertyChangeListener(this);
                invitertJListe.addListSelectionListener(this);
                moteromJListe.addPropertyChangeListener(this);
                moteromJListe.addListSelectionListener(this);
                moteromScrollPane.addPropertyChangeListener(this);
        }
        
        public NyHendelse(JFrame frame, Avtale avtale, DatabaseKommunikator database, kalender.Kalender k, Person person){
        	
	        this.database = database;
    		this.frame = frame;
	        this.person = person;
	        this.avtale = avtale;
	        this.oppdatering = true;

	        this.setupGUI();

            avbrytButton.addActionListener(this);
            lagreButton.addActionListener(k);
            hjemButton.addActionListener(this);
            oppdaterMoterom.addActionListener(this);
            moteRadio.addActionListener(this);
            invitertJListe.addPropertyChangeListener(this);
            invitertJListe.addListSelectionListener(this);
            moteromJListe.addPropertyChangeListener(this);
            moteromJListe.addListSelectionListener(this);
            moteromScrollPane.addPropertyChangeListener(this);
            
            tittelText.setText(avtale.hentAvtaleNavn());
            datoText.setText(avtale.hentAvtaleDato().toString());
            starttidText.setText(avtale.hentStarttid().toString());
            sluttidText.setText(avtale.hentSluttid().toString());
            beskrivelseText.setText(avtale.hentBeskrivelse());
            settInviterte();
            //eksterne er ikke lagra i databasen
            /*String eksterne = "";
            for(int i = 0;i < avtale.hentAntallEksterne();i++)
            {
            	if(i != 0)
            	{
            		eksterne += ";" + avtale.hentEmail(i);
            	}
            }
            andreText.setText(eksterne);*/
        }
        
        public void setAutoOppforing(){
                Calendar cal = Calendar.getInstance();
                Dato dato = new Dato(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR));
                Tid startTid = new Tid(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),00);
                Tid sluttTid;
                if(cal.get(Calendar.HOUR_OF_DAY)+1==24) sluttTid = new Tid(0, cal.get(Calendar.MINUTE),00);
                else sluttTid = new Tid(cal.get(Calendar.HOUR_OF_DAY)+1,cal.get(Calendar.MINUTE),00);
                datoText.setText(dato.toString());
                starttidText.setText(startTid.toString());
                sluttidText.setText(sluttTid.toString());
                tittelText.setText("Nytt møte");
                beskrivelseText.setText("");
        }
        
        public Avtale lagre(){
	            String avtaleTittel = tittelText.getText(); 
	            Person avtalePerson = person;
	            Dato avtaleDato = new Dato(datoText.getText()); 
	            Tid avtaleStart = new Tid(starttidText.getText()); 
	            Tid avtaleSlutt = new Tid(sluttidText.getText()); 
	            Tid avtaleAltStart = new Tid(sluttidText.getText()); 
	            Moterom avtaleRom = new Moterom();
	            int romid = moteromListe.hentListe().get(moteromJListe.getSelectedIndex()).hentRomID();
	            System.out.println("RomID: " + romid);
	            avtaleRom.settRomid(romid);
	            String avtaleBeskr = beskrivelseText.getText(); 
	            String avtaleEndret = "";
	            Respons avtaleResp = Respons.kanskje; 
	            ArrayList<PersonListeElement> avtaleInterne = new ArrayList<PersonListeElement>();
	            PersonListe personListe = getInviterte();
	            int avtaleIntDltkr = personListe.antallPersoner();
	            for(int i = 0;i < avtaleIntDltkr;i++)
	            {
	            	avtaleInterne.add(new PersonListeElement(personListe.hentPerson(i)));
	            }
	            
	            ArrayList<String> avtaleEksterne = new ArrayList<String>();
	            String ekst = andreText.getText();
	            String[] resultat = ekst.split("\\;");
	            for(int i=0;i<resultat.length; i++){
	            		if(resultat[i].length()>0){
	            				avtaleEksterne.add(resultat[i]);
	            		}
	            }
	          	  
	            int avtaleEkstDltkr = avtaleEksterne.size();
	            
	            if(!oppdatering)
	            {
		            return new Avtale(avtaleTittel, avtalePerson, avtaleDato, avtaleStart, avtaleSlutt, avtaleAltStart, avtaleRom, avtaleBeskr,
	          		  			  	  avtaleEndret, avtaleResp, avtaleInterne, avtaleIntDltkr, avtaleEksterne, avtaleEkstDltkr);
	            }
	            else
	            {
	            	avtale.settAvtaleNavn(avtaleTittel);
	            	avtale.settAvtaleDato(avtaleDato);
	            	avtale.settStarttid(avtaleStart);
	            	avtale.settSluttid(avtaleSlutt);
	            	avtale.settAlternativStarttid(avtaleAltStart);
	            	avtale.settRom(avtaleRom);
	            	avtale.settBeskrivelse(avtaleBeskr);
	            	avtale.settSistEndret(avtaleEndret);
	            	avtale.settStatus(avtaleResp);
	            	avtale.settInterneDeltakere(avtaleInterne);
	            	avtale.settAntallInterne(avtaleIntDltkr);
	            	avtale.settEksterneDeltakere(avtaleEksterne);
	            	avtale.settAntallEksterne(avtaleEkstDltkr);
	            	return avtale;
	            }
        }
		
		public void sendTilDatabase(){
                try {
                        if (!oppdatering) {
                                database.leggInnAvtale(avtale);
                        } else {
                                database.endreAvtale(avtale);
                        }
                        
                } catch (SQLException e) {
                        //TODO: feilmelding ?
                        //Feilmelding.visFeilmelding(this, "Feil med database:\n" + e.getMessage(), Feilmelding.FEIL_DATABASEFEIL);
                }
        }
        
        public void settInviterte(){
        		PersonListe inviterte = new PersonListe();
        		for(int i = 0;i < 0/*avtale.hentAntallInterne()*/;i++)
        		{
        				inviterte.leggTilPerson(avtale.hentPerson(i));
        		}
                for(int i=0; i < inviterte.antallPersoner();i++){
                        for(int j = 0;j < invitertListe.antallPersoner();i++)
                        {
	                        	if(inviterte.hentPerson(i) == invitertListe.hentPerson(j))
	                        	{
	                        			invitertJListe.setSelectedIndex(j);
	                        	}
                        }
                }
        }
        
        public PersonListe getInviterte(){
        		PersonListe personListe = new PersonListe();
        		int indices[] = invitertJListe.getSelectedIndices();
        		for(int i = 0;i < indices.length;i++)
        		{
        				personListe.leggTilPerson((Person)invitertJListe.getModel().getElementAt(indices[i]));
        		}
        		return personListe;
        }

		@Override
		public void actionPerformed(ActionEvent e) {
				switch(e.getActionCommand())
				{
				case "Avbryt":
						System.out.println("Button Avbryt pressed");
						frame.dispose();
						break;
				case "Hjem":
						System.out.println("Button Hjem pressed");
						frame.dispose();
						break;
				case "Oppdater romliste":
						System.out.println("Button Oppdater romliste pressed");
		            	moteromListe.ledigeRom(new Dato(datoText.getText()), new Tid(starttidText.getText()), new Tid(sluttidText.getText()));
		            	moteromJListe = new JList(moteromListe.hentListe().toArray());
		            	if(!moteromListe.isSelectionEmpty())
			      		{
			            		System.out.println("Ingen ledige møterom");
		            	}
						break;
				case "Automatisk valg":
						System.out.println("Button Automatisk valg pressed (må legge til at første rom i liste velges)");
		            	moteromListe.ledigeRom(new Dato(datoText.getText()), new Tid(starttidText.getText()), new Tid(sluttidText.getText()));
		            	moteromJListe = new JList(moteromListe.hentListe().toArray());
		            	if(((JRadioButton)e.getSource()).isSelected())
		            	{
			            		if(!moteromListe.isSelectionEmpty())
			            		{
			            				moteromJListe.setSelectedIndex(0);
			            		}
			            		else
			            		{
			            				System.out.println("Ingen ledige møterom");
			            		}
		            	}
						break;
				default:
						break;
				}
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
				System.out.println("ListSelectionListener");
		}

		@Override
		public void propertyChange(PropertyChangeEvent e) {
				System.out.println("PropertyChangeListener: " + ", e.getPropertyName(): " + e.getPropertyName());
		}        
		
		void setupGUI() {
			hjemButton = new JButton("Hjem");
            oppdaterMoterom = new JButton("Oppdater romliste");
            moteRadio = new JRadioButton("Automatisk valg");
            avbrytButton = new JButton("Avbryt");
            lagreButton = new JButton("Lagre");
	        
            tittelText = new JTextField(10);
            datoText = new JTextField(5);
            starttidText = new JTextField(null, 1);
            sluttidText = new JTextField(1);
	        stedText = new JTextField(5);
	        andreText = new JTextField(5);
	        
	        invitertListe = new PersonListe();
	        invitertJListe = new JList(invitertListe.hentListe().toArray());
            invitertJListe.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            inviterteScrollPane = new JScrollPane(invitertJListe);
            inviterteScrollPane.setHorizontalScrollBar(null);
            inviterteScrollPane.setVerticalScrollBar(inviterteScrollPane.createVerticalScrollBar());
            
            moteromListe = new LedigeMoterom();
            moteromJListe = new JList(moteromListe.hentListe().toArray());
            moteromJListe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            moteromScrollPane = new JScrollPane(moteromJListe);
            moteromScrollPane.setHorizontalScrollBar(null);
            moteromScrollPane.setVerticalScrollBar(moteromScrollPane.createVerticalScrollBar());
	        
            beskrivelseText = new JTextArea(3,30);
            beskrivelseText.setEditable(true);
            beskrivelseScroll = new JScrollPane(beskrivelseText);
            beskrivelseScroll.setHorizontalScrollBar(null);
            beskrivelseScroll.setVerticalScrollBar(beskrivelseScroll.createVerticalScrollBar());
                            
            JLabel tittel = new JLabel("Tittel:");
            JLabel dato = new JLabel("Dato:");
            JLabel starttid = new JLabel("Starttid:");
            JLabel sluttid = new JLabel("Sluttid:");
            JLabel deltakere = new JLabel("Deltakere:");
            JLabel interne = new JLabel("Interne:");
            JLabel eksterne = new JLabel("Inviter via epost:");
            JLabel sted = new JLabel("Sted:");
            JLabel beskrivelse = new JLabel("Beskrivelse:");
	        
	        GroupLayout layout = new GroupLayout(this);
	        layout.setAutoCreateGaps(true);
	        
	        layout.setHorizontalGroup(
	        		layout.createSequentialGroup()
        				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        					.addComponent(hjemButton)
	        				.addComponent(tittel)
	        				.addComponent(dato)
	        				.addComponent(starttid)
	        				.addComponent(sluttid)
	        				.addComponent(deltakere)
	        				.addComponent(interne)
	        				.addComponent(eksterne)
	        				.addComponent(sted)
	        				.addComponent(beskrivelse)
        					.addComponent(lagreButton)
	        			)
        				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        					.addComponent(tittelText)
        					.addComponent(datoText)
        					.addComponent(starttidText)
        					.addComponent(sluttidText)
        					.addComponent(inviterteScrollPane)
        					.addComponent(andreText)
        					.addComponent(moteRadio)
    						.addComponent(oppdaterMoterom)
        					.addComponent(moteromScrollPane)
        					.addComponent(beskrivelseScroll)
        					.addComponent(avbrytButton)
	        			)
	        );
	        
	        layout.setVerticalGroup(
	        		layout.createSequentialGroup()
	        			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
	        				.addComponent(hjemButton)
	        			)
	        			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
	        				.addComponent(tittel)
	        				.addComponent(tittelText)
	        			)
	        			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		        			.addComponent(dato)
		        			.addComponent(datoText)
		        		)
		        		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		        			.addComponent(starttid)
		        			.addComponent(starttidText)
		        		)
		        		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		        			.addComponent(sluttid)
		        			.addComponent(sluttidText)
		        		)
		        		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		        			.addComponent(deltakere)
		        		)
		        		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		        			.addComponent(interne)
		        			.addComponent(inviterteScrollPane)
		        		)
		        		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		        			.addComponent(eksterne)
		        			.addComponent(andreText)
		        		)
		        		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		        			.addComponent(sted)
		        			.addGroup(layout.createParallelGroup()
		        				.addComponent(moteRadio)
		        			)
		        		)
		        		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				        		.addComponent(oppdaterMoterom)
			        		)
		        		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			        			.addComponent(moteromScrollPane)
			        		)
		        		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			        		.addComponent(beskrivelse)
			        		.addComponent(beskrivelseScroll)
			        	)
		        		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				        		.addComponent(lagreButton)
		        				.addComponent(avbrytButton)
				        )
	        );
	        
	        this.setLayout(layout);
		}

		public void lukk() {
			frame.dispose();
		}
			
}
