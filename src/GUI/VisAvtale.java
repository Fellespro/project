package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import modell.Avtale;
import modell.Tid;

public class VisAvtale extends JPanel implements ActionListener{

        /**
         * @param args
         */
        private JButton hjem;
        
        private JLabel datoLabel;
        private JTextField datoText;
        private JLabel starttidLabel;
        private JTextField starttidText;
        private JLabel sluttidLabel;
        private JTextField sluttidText;
        
        private JLabel tittelLabel;
        private JTextField tittelText;
        private JLabel motelederLabel;
        private JTextField motelederText;
        private JLabel stedLabel;
        private JTextField stedText;
        
        private JLabel beskrivelseLabel;
        private JTextArea beskrivelseText;
        private JScrollPane beskrivelseScroll;
        
        private JButton endre;
        private JButton slett;
        private JButton avbryt;
        
        private Avtale avtale;
        
        private JFrame frame;
        
        private List<ActionListener> actionListeners;
        
        //Constructor
        public VisAvtale(){
                
                actionListeners = new ArrayList<ActionListener>();
        //Layout
                this.setLayout(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();
                c.insets = new Insets(5,2,5,2);
                c.weighty = 0;
                c.fill = GridBagConstraints.BOTH;
                c.weightx = 1;
                c.weighty = 1;
        //streker
                c.gridheight = 1;
                c.gridwidth = 9;
                c.gridx = 0;
                c.gridy = 1;
                this.add(new JSeparator(JSeparator.HORIZONTAL),c);
                c.gridwidth = 1;
                
                c.gridheight = 1;
                c.gridwidth = 9;
                c.gridx = 0;
                c.gridy = 3;
                this.add(new JSeparator(JSeparator.HORIZONTAL),c);
                c.gridwidth = 1;
                
                c.gridheight = 1;
                c.gridwidth = 9;
                c.gridx = 0;
                c.gridy = 7;
                this.add(new JSeparator(JSeparator.HORIZONTAL),c);
                c.gridwidth = 1;
                
                c.gridheight = 1;
                c.gridwidth = 9;
                c.gridx = 0;
                c.gridy = 10;
                this.add(new JSeparator(JSeparator.HORIZONTAL),c);
                c.gridwidth = 1;
                
                c.gridheight = 3;
                c.gridwidth = 1;
                c.gridx = 2;
                c.gridy = 1;
                this.add(new JSeparator(JSeparator.VERTICAL),c);
                c.gridwidth = 1;
                
                c.gridheight = 3;
                c.gridwidth = 1;
                c.gridx = 5;
                c.gridy = 1;
                this.add(new JSeparator(JSeparator.VERTICAL),c);
                c.gridwidth = 1;
                c.gridheight = 1;

                hjem.addActionListener(this);
                hjem.setName("hjem");
                c.gridx = 0;
                c.gridy = 0;
                this.add(hjem,c);
                
                
        //dato og tidspunkt
                datoLabel = new JLabel("Dato:");
                c.gridx = 0;
                c.gridy = 2;
                this.add(datoLabel,c);
                
                datoText = new JTextField("dd.mm.yyyy");
                datoText.setEditable(false);
                c.gridx = 1;
                c.gridy = 2;
                this.add(datoText,c);
                
                starttidLabel = new JLabel("Starttid:");
                c.gridx = 3;
                c.gridy = 2;
                this.add(starttidLabel,c);
                
                starttidText = new JTextField("hh.mm");
                starttidText.setEditable(false);
                c.gridx = 4;
                c.gridy = 2;
                this.add(starttidText,c);
                
                sluttidLabel = new JLabel("Sluttid:");
                c.gridx = 6;
                c.gridy = 2;
                this.add(sluttidLabel,c);
                
                sluttidText = new JTextField("hh.mm");
                sluttidText.setEditable(false);
                c.gridx = 7;
                c.gridy = 2;
                this.add(sluttidText,c);
                
        //tittel, sted, antall og radiobuttons
                tittelLabel = new JLabel("Tittel:");
                c.gridx = 0;
                c.gridy = 4;
                this.add(tittelLabel,c);
                
                tittelText = new JTextField(10);
                tittelText.setEditable(false);
                c.gridx = 1;
                c.gridy = 4;
                this.add(tittelText,c);
                
                motelederLabel = new JLabel("Møteleder:");
                c.gridx = 0;
                c.gridy = 5;
                this.add(motelederLabel,c);
                
                motelederText = new JTextField();
                motelederText.setEditable(false);
                c.gridx = 1;
                c.gridy = 5;
                this.add(motelederText,c);
                
                stedLabel = new JLabel("Sted:");
                c.gridx = 0;
                c.gridy = 6;
                this.add(stedLabel,c);
                
                stedText = new JTextField(10);
                stedText.setEditable(false);
                c.gridx = 1;
                c.gridy = 6;
                this.add(stedText,c);
                
        //Beskrivelse
                beskrivelseLabel = new JLabel("Beskrivelse");
                c.gridx = 0;
                c.gridy = 8;
                this.add(beskrivelseLabel,c);
                
                beskrivelseText = new JTextArea(3,30);
                beskrivelseText.setEditable(false);
                beskrivelseScroll = new JScrollPane(beskrivelseText);
                //beskrivelseScroll.add(beskrivelseText);
                c.gridheight = 1;
                c.gridwidth = 8;
                c.gridx = 0;
                c.gridy = 9;
                this.add(beskrivelseScroll,c);
                c.gridheight = 1;
                c.gridwidth = 1;
                
        //Lagre og avbrytbuttons
                endre = new JButton("Endre");
                endre.addActionListener(this);
                endre.setName("endre");
                c.gridx = 3;
                c.gridy = 11;
                this.add(endre,c);
                
                slett = new JButton("Slett");
                slett.addActionListener(this);
                slett.setName("slett");
                c.gridx = 4;
                c.gridy = 11;
                this.add(slett,c);
                
                avbryt = new JButton("Avbryt");
                avbryt.addActionListener(this);
                avbryt.setName("avbryt");
                c.gridx = 6;
                c.gridy = 11;
                this.add(avbryt,c);
                
                
        //Ramme
        /*      frame = new JFrame("Legg til avtale/møte");
                frame.add(this);
                frame.setVisible(true);
                frame.pack();
                frame.setSize(700,500);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
        }
        
        public void setOppforing(Avtale avtale) {
                this.avtale = avtale;
                
                datoText.setText(avtale.hentAvtaleDato().toString());
                starttidText.setText((new Tid(avtale.hentStarttid().hentTime(), avtale.hentStarttid().hentMin(), avtale.hentStarttid().hentSek())).toString());
                sluttidText.setText((new Tid(avtale.hentSluttid().hentTime(), avtale.hentSluttid().hentMin(), avtale.hentSluttid().hentSek())).toString());
                tittelText.setText(avtale.hentAvtaleNavn());
                motelederText.setText(avtale.hentOpprettetAv().getNavn());
                stedText.setText(avtale.hentRom().hentNavn());
                beskrivelseText.setText(avtale.hentBeskrivelse());
                
                
        }
        public Avtale getAvtale() {
                return avtale;
        }

        public static void main(String[] args) {
                

        }

        public void slett(){
                slett.doClick();
        }
        
        public void addActionListener(ActionListener listener) {
                this.actionListeners.add(listener);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
                if(e.getSource() == hjem){
                        for (ActionListener l : this.actionListeners) {
                                l.actionPerformed(e);
                        }
                }
                else if(e.getSource() == endre){
                        for (ActionListener l : this.actionListeners) {
                                l.actionPerformed(e);
                        }
                }
                else if(e.getSource() == slett){
                        
                        Object[] options ={"OK","Avbryt"};
                        int n = JOptionPane.showOptionDialog(frame, "Er du sikker på at du vil slette?\n", "Bekreft sletting", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                        if(n == JOptionPane.OK_OPTION){
                                for (ActionListener l : this.actionListeners) {
                                        l.actionPerformed(e);
                                }
                        }
                }
                else if(e.getSource() == avbryt){
                        for (ActionListener l : this.actionListeners) {
                                l.actionPerformed(e);
                        }
                }
                
        }

}
