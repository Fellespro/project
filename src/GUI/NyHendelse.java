package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modell.*;

public class NyHendelse extends JPanel implements ActionListener, ListSelectionListener, DocumentListener{
  
  //Opprette alle knappene som trengs
  private JButton hjemButton;
        
        private JLabel datoLabel;
        private JTextField datoText;
        private JLabel starttidLabel;
        private JTextField starttidText;
        private JLabel sluttidLabel;
        private JTextField sluttidText;
        
        private JLabel tittelLabel;
        private JTextField tittelText;
        private JLabel stedLabel;
        private JTextField stedText;
        private JLabel antallAndreLabel;
        private JTextField antallAndreText;
        private JLabel totaltLabel;
        private JTextField totaltText;
        
        private JLabel moteLabel;
        private JRadioButton moteRadio;
        
        private JLabel moteromLabel;
        private MoteromListe moteromList;
        private JScrollPane moteromScrollPane;
        private JButton oppdaterMoterom;
        
        private JLabel beskrivelseLabel;
        private JTextArea beskrivelseText;
        private JScrollPane beskrivelseScroll;
        
        private JButton lagreButton;
        private JButton avbrytButton;
        
        private Avtale avtale;
        
        private List<ActionListener> actionListeners;
        
        //koble til databasen. Ha det i denne klassen eller ta ut?
        private Database database;
        private Person person;
        
        private int antallAndre = 0;
        private int totaltAntall = 0;
        private int antallAnsatte = 0;
        
        private String feilmelding;
        private boolean oppdatering;
        
        //trenger en oppdatering med cascade i modell
        public NyHendelse(Database database, Person person, boolean oppdatering){
                this.database = database;
                this.person = person;
                this.oppdatering = oppdatering;
                
                actionListeners = new ArrayList<ActionListener>();
        //Layout
                this.setLayout(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();
                c.insets = new Insets(5,2,5,2);
                c.weighty = 1;
                c.weightx = 1;
                c.fill = GridBagConstraints.BOTH;
                
                
                
                
        //Streker
                /*c.gridx = 2;
                c.gridy = 0;
                c.gridheight = 20;
                this.add(new JSeparator(JSeparator.VERTICAL),c);
                */
                c.weightx = 0;
                c.weighty = 0;
                
                c.gridheight = 1;
                c.gridwidth = 11;
                c.gridx = 0;
                c.gridy = 1;
                this.add(new JSeparator(JSeparator.HORIZONTAL),c);
                c.gridwidth = 1;
                
                c.gridheight = 3;
                c.gridwidth = 1;
                c.gridx = 5;
                c.gridy = 1;
                this.add(new JSeparator(JSeparator.VERTICAL),c);
                c.gridwidth = 1;
                
                c.gridheight = 7;
                c.gridwidth = 1;
                c.gridx = 8;
                c.gridy = 1;
                this.add(new JSeparator(JSeparator.VERTICAL),c);
                c.gridwidth = 1;
                c.gridheight = 1;
                
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
                c.gridy = 7;
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
               /* ImageIcon iconHome = new ImageIcon(new ImageIcon(getClass().getResource("/res/home.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
                ImageIcon iconSave = new ImageIcon(new ImageIcon(getClass().getResource("/res/saveicon.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
                ImageIcon iconBack = new ImageIcon(new ImageIcon(getClass().getResource("/res/goback.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)); */
                
                c.fill = GridBagConstraints.VERTICAL;
                
                hjemButton = new JButton("Hjem");
                hjemButton.addActionListener(this);
                
                c.gridx = 3;
                c.gridy = 0;
                c.weightx = 0;
                c.weighty = 0;
                this.add(hjemButton,c);         
                c.weightx = 1;
                c.weighty = 1;
                
                c.fill = GridBagConstraints.BOTH;
        //dato og tidspunkt
                c.weighty = 0;
                datoLabel = new JLabel("Dato:");
                c.gridx = 3;
                c.gridy = 2;
                this.add(datoLabel,c);
                
                datoText = new JTextField("dd-mm-yyyy");
                c.gridx = 4;
                c.gridy = 2;
                this.add(datoText,c);
                
                starttidLabel = new JLabel("Starttid:");
                c.gridx = 6;
                c.gridy = 2;
                this.add(starttidLabel,c);
                
                starttidText = new JTextField("hh:mm");
                c.gridx = 7;
                c.gridy = 2;
                this.add(starttidText,c);
                
                sluttidLabel = new JLabel("Sluttid:");
                c.gridx = 9;
                c.gridy = 2;
                this.add(sluttidLabel,c);
                
                sluttidText = new JTextField("hh:mm");
                c.gridx = 10;
                c.gridy = 2;
                this.add(sluttidText,c);
                
        //tittel, sted, antall og radiobuttons
                
                c.weighty = 0;
                c.gridheight = 1;
                tittelLabel = new JLabel("Tittel:");
                c.gridx = 3;
                c.gridy = 4;
                this.add(tittelLabel,c);
                
                tittelText = new JTextField(10);
                c.gridx = 4;
                c.gridy = 4;
                this.add(tittelText,c);
                
                stedLabel = new JLabel("Sted/rom:");
                c.gridx = 3;
                c.gridy = 5;
                this.add(stedLabel,c);
                
                stedText = new JTextField(10);
                c.gridx = 4;
                c.gridy = 5;
                this.add(stedText,c);
                
                c.anchor = GridBagConstraints.WEST;
                
                moteRadio = new JRadioButton();
                moteRadio.setName("moteRadio");
                moteRadio.addActionListener(this);

                c.gridx = 7;
                c.gridy = 5;
                this.add(moteRadio,c);
                
                antallAndreLabel = new JLabel("Inviter eksterne via e-post:");
                c.gridx = 3;
                c.gridy = 6;
                this.add(antallAndreLabel,c);
                
                //c.fill = GridBagConstraints.VERTICAL;
                //c.fill = GridBagConstraints.BOTH;
                
                antallAndreText = new JTextField("0",3);
                antallAndreText.setText("0");
                antallAndreText.setEditable(false);
                antallAndreText.getDocument().addDocumentListener((this));
                c.gridx = 4;
                c.gridy = 6;
                this.add(antallAndreText,c);
                
                moteromLabel = new JLabel("Møterom:");
                oppdaterMoterom = new JButton("Oppdater møterom");
                oppdaterMoterom.setName("oppdaterMoterom");
                oppdaterMoterom.addActionListener(this);
                c.gridx = 9;
                c.gridy = 4;
                this.add(oppdaterMoterom,c);
                
                moteromList = new MoteromListe(database);
                c.gridheight = 2;
                c.gridwidth = 2;
                c.gridx = 9;
                c.gridy = 5;
                c.fill = GridBagConstraints.BOTH;
                moteromList.setVisible(false);
                moteromList.addListSelectionListener(this);
                moteromScrollPane = new JScrollPane(moteromList);
                this.add(moteromScrollPane,c);
                
        //Beskrivelse
                c.weighty = 0;
                beskrivelseLabel = new JLabel("Beskrivelse");
                c.gridx = 3;
                c.gridy = 8;
                this.add(beskrivelseLabel,c);
                
                c.weighty = 1;
                beskrivelseText = new JTextArea(3,30);
                beskrivelseText.setEditable(true);
                beskrivelseScroll = new JScrollPane(beskrivelseText);
                
                //beskrivelseScroll.add(beskrivelseText);
                c.gridheight = 2;
                c.gridwidth = 7;
                c.gridx = 3;
                c.gridy = 10;
                this.add(beskrivelseScroll,c);
                
                c.fill = GridBagConstraints.VERTICAL;
                
        //Lagre og avbrytbuttons
                c.weighty = 0;
                lagreButton = new JButton("Lagre");
                lagreButton.setName("lagre");
                lagreButton.addActionListener(this);
                c.gridx = 3;
                c.gridy = 13;
//              c.gridheight = 1;
//              c.gridwidth = 1;
                this.add(lagreButton,c);
                
                avbrytButton = new JButton("Avbryt");
                avbrytButton.setName("avbryt");
                avbrytButton.addActionListener(this);
                c.gridx = 4;
                c.gridy = 13;
                this.add(avbrytButton,c);
                
                nullstillFelt();
                setAutoOppforing();
                        
        }
        
        public void setOppdatering(boolean oppdatering) {
                this.oppdatering = oppdatering;
        }
        
        public boolean getOppdatering() {
                return oppdatering;
        }
        
        public void setAutoOppforing(){
                Calendar cal = Calendar.getInstance();
                Dato dato = new Dato(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR));
                Time startTid = new Time(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));
                Time sluttTid;
                if(cal.get(Calendar.HOUR_OF_DAY)+1==24) sluttTid = new Time(0, cal.get(Calendar.MINUTE));
                else sluttTid = new Time(cal.get(Calendar.HOUR_OF_DAY)+1,cal.get(Calendar.MINUTE));
                datoText.setText(dato.toString());
                starttidText.setText(startTid.toString());
                sluttidText.setText(sluttTid.toString());
                //tittelText.setText("møte");
                
                
        }
        
        public void setAntallAnsatte(int antallAnsatte){
                this.antallAnsatte = antallAnsatte;
                setTotaltText();
        }
        public void setTotaltText(){
                totaltAntall = antallAnsatte + antallAndre + 1;
                totaltText.setText(""+totaltAntall);
        }
        
        public Person getPerson(){
                return this.person;
        }
        public void setInviterte(AlleAnsatteListe inviterte){
                PersonListe pListe = new PersonListe();
                for(int i=0; i<inviterte.getModel().getSize();i++){
                        Person p = (Person)inviterte.getModel().getElementAt(i);
                        pListe.addPerson(p);
                }
                if(avtale instanceof Mote){
                        ((Mote)avtale).setInviterte(pListe);
                }
        }
        public PersonListe getInviterte(){
                if(avtale instanceof Mote){
                        return ((Mote)avtale).getInviterte();
                }
                return null;
        }
        
        public void lagre(){
                feilmelding = "";
                avtale.setLagetAv(person);
                
//              if(datoText.getText() != null){
                        try{
                                Dato datoen = new Dato(datoText.getText());
                                avtale.setDato(datoText.getText());
                        }
                        catch(Exception e){
                                //TODO: lag dialog
                                feilmelding += "Dato: Feil format eller ugyldig dato. Husk å skrive på formatet dd-mm-yyyy \n";
                        }
                        
//              }
//              if(starttidText.getText() != null){
                        try{
                                String[] starttidTabell = starttidText.getText().split(":");
                                int time = Integer.parseInt(starttidTabell[0]);
                                int min = Integer.parseInt(starttidTabell[1]);
                                Time tid = new Time(time, min);
                                avtale.setStarttid(tid.toString());
                        }
                        catch(Exception e){
                                feilmelding += "Starttid: Ugyldig tidspunkt. Skriv på formatet hh:mm \n";
                        }
//              }
//              if(sluttidText.getText() != null){
                        try{
                                String[] sluttidTabell = sluttidText.getText().split(":");
                                int time = Integer.parseInt(sluttidTabell[0]);
                                int min = Integer.parseInt(sluttidTabell[1]);
                                Time tid = new Time(time, min);
                                avtale.setSluttid(tid.toString());
                        }
                        catch(Exception e){
                                feilmelding += "Sluttid: Ugyldig tidspunkt. Skriv på formatet hh:mm \n";
                        }
                        
                try {
                        if (!Time.checkTimes(avtale.getStarttid(), avtale.getSluttid()))
                                feilmelding += "Klokkeslett: Sluttid skjer før starttid";
                } catch (Exception e) {
                }       
                        
//              }
                if(!tittelText.getText().equals("")){
                        avtale.setTittel(tittelText.getText());
                } 
                else{
                        feilmelding += "Tittel: Mangler tittel \n";
                }
                if(!stedText.getText().equals("")){
                        avtale.setSted(stedText.getText());
                        if(avtale instanceof Mote && !moteromList.isSelectionEmpty()){
                                ((Mote)avtale).setMoterom((Moterom)moteromList.getSelectedValue());
                        }
                }
                else{
                        feilmelding += "Sted: Mangler sted \n";
                }
                if(!beskrivelseText.getText().equals(null)){
                        avtale.setBeskrivelse(beskrivelseText.getText());
                } 
                
                
//              if(antallAndreText.getText() != null || antallAndreText.getText() != ""){
                        if(oppforing instanceof Mote){
                                try{
                                if (Integer.parseInt(antallAndreText.getText()) < 0) throw new IllegalArgumentException();
                                ((Mote) avtale).setAntallAndre(Integer.parseInt(antallAndreText.getText()));
                                }
                                catch (Exception e){
                                        feilmelding += "AntallAndre: Ugyldig format, bruk positive tall \n";
                                }
                        }
//              }
        }
        public void sendTilDatabase(boolean oppdatering){
                //              Kaller på database og send oppforing
                try {
                        if (!oppdatering) {
                                oppforing.setLagetAv(getPerson());
                                int id = database.addOppforing(oppforing);
                                if (avtale instanceof Mote) ((Mote)oppforing).setMoteId(id);
                                else ((Avtale)oppforing).setAvtaleId(id);
                        } else {
                                database.updateOppforing(getPerson(), avtale);
                        }
                        
                } catch (SQLException e) {
                        //TODO: feilmelding
                        Feilmelding.visFeilmelding(this, "Feil med database:\n" + e.getMessage(), Feilmelding.FEIL_DATABASEFEIL);
                }
        }
        public Avtale getAvtale(){
                return avtale;
        }
        
        public void nullstillFelt(){
                setAutoOppforing();
                tittelText.setText("");
                stedText.setText("");
                antallAndreText.setText("0");
                totaltText.setText("0");
                beskrivelseText.setText("");
                avtaleRadio.doClick();
        }
        
        public void setAvtale(Avtale avtale){
                if (oppdatering) this.avtale = avtale;
                if(avtale instanceof Mote){
                        datoText.setText(avtale.getDato());
                        starttidText.setText(avtale.getStarttid());
                        sluttidText.setText(avtale.getSluttid());
                        tittelText.setText(avtale.getTittel());
                        stedText.setText(avtale.getSted());
                        antallAndreText.setText(""+((Mote)avtale).getAntallAndre());
                        totaltText.setText(""+((Mote)avtale).getTotaltAntall());
                        beskrivelseText.setText(avtale.getBeskrivelse());
                        moteRadio.doClick();
                }
        
        public void addActionListener(ActionListener listener) {
                this.actionListeners.add(listener);
        }
        
        public void actionPerformed(ActionEvent e) {
                if(e.getSource() == hjemButton){
                        avtaleRadio.doClick();
                        for (ActionListener l : this.actionListeners) {
                                l.actionPerformed(e);
                        }
                }
                else if(e.getSource() == oppdaterMoterom){
                        try {
                                moteromList.hentMoterom(new Dato(datoText.getText()), new Time(starttidText.getText()), new Time(sluttidText.getText()), Integer.parseInt(totaltText.getText()));
                        } catch (IllegalArgumentException e2) {
                                Feilmelding.visFeilmelding(this, "Dato, Starttid eller Sluttid er feil utfylt", Feilmelding.FEIL_UGYLDIG_UTFYLLING);
                        }
                }
          
                else if(e.getSource() == moteRadio){
                        if(moteRadio.isSelected()){
                                if(datoText.getText() != "dd-mm-yyyy" && starttidText.getText() != "hh:mm" && sluttidText.getText() != "hh:mm"){
                                        moteromList.hentMoterom(new Dato(datoText.getText()), new Time(starttidText.getText()), new Time(sluttidText.getText()), Integer.parseInt(totaltText.getText()));
                                }
                                moteromList.setVisible(true);
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
                                        oppforing = new Mote();
                                }
                                
                        }
                //Lagrer verdier i objektet avtale
                        lagre();
                        
                        if (feilmelding.equals("")){

                                //La KalenderSystem lytte for å skifte view
                                for (ActionListener l : this.actionListeners) {
                                        l.actionPerformed(e);
                                }
                
                                //Lagre oppforing i databasen
                                sendTilDatabase(oppdatering);
                        } else {
                                System.out.println(feilmelding);
                                Feilmelding.visFeilmelding(this, feilmelding, Feilmelding.FEIL_UGYLDIG_UTFYLLING);
                        }
                }
                else if(e.getSource() == avbrytButton){
                        avtaleRadio.doClick();
                        for (ActionListener l : this.actionListeners) {
                                l.actionPerformed(e);
                        }
                }
        }
        public void valueChanged(ListSelectionEvent e) {
                if(e.getSource() == moteromList){
                        try {
                                Moterom m = (Moterom)moteromList.getSelectedValue();
                                stedText.setText(m.getNavn());
                        } catch (NullPointerException e1) {
                        }
                }
        }

        public void changedUpdate(DocumentEvent arg0) {
                // TODO Auto-generated method stub
                
        }

        public void insertUpdate(DocumentEvent e) {
                        for(int i = 0; i< antallAndreText.getText().length(); i++){
                                if(!Character.isDigit(antallAndreText.getText().charAt(i))){
                                        antallAndre = 0;
                                        antallAndreText.setText("0");
                                        setTotaltText();
                                        return;
                                }
                        }
                        antallAndre = Integer.parseInt(antallAndreText.getText());
                        setTotaltText();
        }       

        public void removeUpdate(DocumentEvent e) {
                if(antallAndreText.getText().length() != 0){
                        for(int i = 0; i< antallAndreText.getText().length(); i++){
                                if(!Character.isDigit(antallAndreText.getText().charAt(i))){
                                        antallAndre = 0;
                                        antallAndreText.setText("0");
                                        setTotaltText();
                                        return;
                                }
                        }
                        antallAndre = Integer.parseInt(antallAndreText.getText());
                        setTotaltText();
                }
                else {
                        antallAndre = 0;
                        setTotaltText();
                        return;
                
                }
        }
}
  

}
