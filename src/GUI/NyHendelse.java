package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modell.*;

public class NyHendelse extends JPanel implements ActionListener, ListSelectionListener, PropertyChangeListener/*, DocumentListener, MouseListener*/ {
  
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
        
        //private DatabaseKommunikator database;
        private Person person;
        private Avtale avtale;
        
        private String feilmelding;
        private boolean oppdatering;
        
        
        public NyHendelse(kalender.Kalender k, Person person, boolean oppdatering){
        	
		        //this.database = database;
		        this.person = person;
		        this.oppdatering = oppdatering;

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
                inviterteScrollPane = new JScrollPane(invitertJListe);
                inviterteScrollPane.setHorizontalScrollBar(null);
                inviterteScrollPane.setVerticalScrollBar(inviterteScrollPane.createVerticalScrollBar());
                
                moteromListe = new LedigeMoterom();
                moteromJListe = new JList(moteromListe.hentListe().toArray());
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
		        
		        actionListeners = new ArrayList<ActionListener>();

                avbrytButton.addActionListener(this);
                lagreButton.addActionListener(k);
                hjemButton.addActionListener(this);
                oppdaterMoterom.addActionListener(this);
                moteRadio.addActionListener(this);
                invitertJListe.addPropertyChangeListener(this);
                invitertJListe.addListSelectionListener(this);
                moteromJListe.addListSelectionListener(this);
                //moteromScrollPane.addMouseListener(this);
                //antallAndreText.getDocument().addDocumentListener((this));

		        actionListeners.add((avbrytButton.getActionListeners())[0]);
		        actionListeners.add((lagreButton.getActionListeners())[0]);
		        actionListeners.add((hjemButton.getActionListeners())[0]);
		        actionListeners.add((oppdaterMoterom.getActionListeners())[0]);
		        actionListeners.add((moteRadio.getActionListeners())[0]);
                
                nullstillFelt();
                setAutoOppforing();
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
        }

        public void nullstillFelt(){
                setAutoOppforing();
                tittelText.setText("");
                stedText.setText("");
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
	            String avtaleBeskr = beskrivelseText.getText(); 
	            String avtaleEndret = "";
	            Respons avtaleResp = Respons.kanskje; 
	            ArrayList<PersonListeElement> avtaleInterne = new ArrayList<PersonListeElement>();
	            int avtaleAntDltkr = 0;
	            
	            ArrayList<String> avtaleEksterne = new ArrayList<String>();
	            String ekst = andreText.getText();
	            String[] resultat = ekst.split("\\;");
	            for(int i=0;i<resultat.length; i++){
	            		if(resultat[i].length()>0){
	            				avtaleEksterne.add(resultat[i]);
	            		}
	            }
	          	  
	            int avtaleEkstDltkr = avtaleEksterne.size();
	            
	            return new Avtale(avtaleTittel, avtalePerson, avtaleDato, avtaleStart, avtaleSlutt, avtaleAltStart, avtaleRom, avtaleBeskr,
          		  			  	  avtaleEndret, avtaleResp, avtaleInterne, avtaleAntDltkr, avtaleEksterne, avtaleEkstDltkr);
        }

		@Override
		public void actionPerformed(ActionEvent e) {
				switch(e.getActionCommand())
				{
				case "Avbryt":
						System.out.println("Button Avbryt pressed");
						System.exit(0);
						break;
				case "Hjem":
						System.out.println("Button Hjem pressed (skjer ikke noe enda)");
						break;
				case "Oppdater romliste":
						System.out.println("Button Oppdater romliste pressed");
		            	moteromListe.ledigeRom(new Dato(datoText.getText()), new Tid(starttidText.getText()), new Tid(sluttidText.getText()));
		            	moteromJListe = new JList(moteromListe.hentListe().toArray());
		            	if(!moteromListe.isSelectionEmpty())
			      		{
			            		//velg første rom i liste
			            }
			            else
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
			            				//velg første rom i liste
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
				System.out.println("PropertyChangeListener");
		}
        
        /*
         
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
        
		public void sendTilDatabase(boolean oppdatering){
                Kaller på database og send oppforing
                try {
                        if (!oppdatering) {
                               	avtale.settOpprettetAv(getPerson());
                                database.leggInnAvtale(avtale);
                        } else {
                        		// m� lage oppdaterAvtale-funksjon i databasen
                                database.endreAvtale(getPerson(), avtale);
                        }
                        
                } catch (SQLException e) {
                        //TODO: feilmelding ?
                        Feilmelding.visFeilmelding(this, "Feil med database:\n" + e.getMessage(), Feilmelding.FEIL_DATABASEFEIL);
                }
        }
        
        public void settAvtale(Avtale avtale){
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
		}*/
			
}
