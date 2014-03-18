package GUI;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import database.*;

import modell.*;


public class NyInvitasjon extends JPanel implements ActionListener{
        private JFrame frame = null;
        
        private JLabel datoLabel;
        private JTextField datoText;
        private JLabel starttidLabel;
        private JTextField starttidText;
        private JLabel sluttidLabel;
        private JTextField sluttidText;
                
        private PropertyChangeSupport support;
        
        private JLabel tittelLabel;
        private JTextField tittelText;
        private JLabel motelederLabel;
        private JTextField motelederText;
        private JLabel stedLabel;
        private JTextField stedText;
        
        private JButton godtaButton;
        private JButton avslaButton;
        private JButton svarSenere;
        
        private List<ActionListener> actionListeners;
        
        private Avtale avtale;
        private DatabaseKommunikator database;
        
        public NyInvitasjon() {
                initiateMote();
        }
        public Frame getFrame() {
                return this.frame;
        }
        public NyInvitasjon(Avtale mote){
                initiateMote();
                setMote(mote);
                frame = new JFrame("Ny invitasjon til møte");
                frame.add(this);
                frame.setVisible(true);
                frame.pack();
                frame.setSize(300,300);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.add(this);
                //playSound();
        }
        public void initiateMote() {
                actionListeners = new ArrayList<ActionListener>();
                this.database = database;
                support = new PropertyChangeSupport(this);

                
                //Layout
                this.setLayout(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();
                c.insets = new Insets(5,5,5,5);
                c.weighty = 1;
                c.weightx = 1;
                c.fill = GridBagConstraints.BOTH;
                
                c.gridwidth = 1;
                
                
        //tittel, sted, antall og radiobuttons
                tittelLabel = new JLabel("Tittel:");
                c.gridx = 0;
                c.gridy = 0;
                this.add(tittelLabel,c);
                
                tittelText = new JTextField(10);
                tittelText.setEditable(false);
                c.gridx = 1;
                c.gridy = 0;
                this.add(tittelText,c);
                
                motelederLabel = new JLabel("Møteleder:");
                c.gridx = 0;
                c.gridy = 1;
                this.add(motelederLabel,c);
                
                motelederText = new JTextField();
                motelederText.setEditable(false);
                c.gridx = 1;
                c.gridy = 1;
                this.add(motelederText,c);
                
                stedLabel = new JLabel("Sted:");
                c.gridx = 0;
                c.gridy = 2;
                this.add(stedLabel,c);
                
                stedText = new JTextField(10);
                stedText.setEditable(false);
                c.gridx = 1;
                c.gridy = 2;
                this.add(stedText,c);
        
        //dato og tidspunkt
                datoLabel = new JLabel("Dato:");
                c.gridx = 0;
                c.gridy = 3;
                this.add(datoLabel,c);
                
                datoText = new JTextField("dd.mm.yyyy");
                datoText.setEditable(false);
                c.gridx = 1;
                c.gridy = 3;
                this.add(datoText,c);
                
                starttidLabel = new JLabel("Starttid:");
                c.gridx = 0;
                c.gridy = 4;
                this.add(starttidLabel,c);
                
                starttidText = new JTextField("hh.mm");
                starttidText.setEditable(false);
                c.gridx = 1;
                c.gridy = 4;
                this.add(starttidText,c);
                
                sluttidLabel = new JLabel("Sluttid:");
                c.gridx = 0;
                c.gridy = 5;
                this.add(sluttidLabel,c);
                
                sluttidText = new JTextField("hh.mm");
                sluttidText.setEditable(false);
                c.gridx = 1;
                c.gridy = 5;
                this.add(sluttidText,c);
                
                godtaButton = new JButton("Godta");
                godtaButton.setName("godta");
                godtaButton.addActionListener(this);
                c.gridx = 0;
                c.gridy = 6;
                this.add(godtaButton,c);
                
                svarSenere = new JButton("Svar senere");
                svarSenere.setName("svarSenere");
                svarSenere.addActionListener(this);
                c.gridx = 1;
                c.gridy = 6;
                this.add(svarSenere,c);
                
                avslaButton = new JButton("Avslå");
                avslaButton.setName("avsla");
                avslaButton.addActionListener(this);
                c.gridx = 2;
                c.gridy = 6;
                this.add(avslaButton,c);
        }
        public void addActionListener(ActionListener listener) {
                this.actionListeners.add(listener);
        }
        
        public void setMote(Avtale a){
                this.avtale = a;
                tittelText.setText(a.hentAvtaleNavn());
                motelederText.setText(a.hentOpprettetAv().getNavn());
                stedText.setText(a.hentRom().getNavn());
                datoText.setText(a.hentAvtaleDato().toString());
                starttidText.setText((new Tid(a.hentStarttid().getHours(), a.hentStarttid().getMinutes(), a.hentStarttid().getSeconds()).toString()));
                sluttidText.setText((new Tid(a.hentSluttid().getHours(), a.hentSluttid().getMinutes(), a.hentSluttid().getSeconds()).toString()));
                
                
        }
        public Avtale getAvtale(){
                return this.avtale;
        }
        
        public void actionPerformed(ActionEvent e) {
                if(frame != null) frame.dispose();
                if(e.getSource() == godtaButton){                       
                        for (ActionListener l : this.actionListeners) {
                                l.actionPerformed(e);
                                support.firePropertyChange("moteInvitasjonGodta", null, avtale);
                        }
                }
                else if(e.getSource() == svarSenere){
                        //ukevisning blir åpnet
                        for (ActionListener l : this.actionListeners) {
                                l.actionPerformed(e);
                        }
                }
                else if(e.getSource() == avslaButton){
                        //TODO: send svar til databasen
                        for (ActionListener l : this.actionListeners) {
                                l.actionPerformed(e);
                                support.firePropertyChange("moteInvitasjonAvslaa", null, avtale);

                        }
                }
                
        }
        
        public void addPropertyChangeListener(PropertyChangeListener pcl) {
                this.support.addPropertyChangeListener(pcl);
        }
        public void removePropertyChangeListener(PropertyChangeListener pcl) {
                this.support.removePropertyChangeListener(pcl);
        }
        
        
        public static void main(String args[]){
                //Avtale mote = new Mote();
                //NyInvitasjon mi = new MoteInvitasjon(mote);
        }
}
/*
 r594 by haaland on Apr 1, 2011   Diff 
All revisions of this file
File info
Size: 5969 bytes, 240 lines
View raw file
File properties
svn:mime-type
text/plain
*/
