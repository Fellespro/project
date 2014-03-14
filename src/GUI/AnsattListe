package GUI;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modell.*;
import database.*;

public class AnsattListe extends JPanel implements ListSelectionListener, DocumentListener{
        
        private JTextField sokefelt;
        private JLabel sokLabel;
        private JLabel invitertLabel;
        private JLabel ansatteLabel;
        private AlleAnsatteListe alleAnsatteListe;
        private DefaultListModel alleAnsatteModel;
        private AlleAnsatteListe invitertListe;
        private DefaultListModel invitertModel;
        
        private JScrollPane alleAnsatteScrollPane;
        private JScrollPane invitertScrollPane;
        
        private Database database;
        
        public AnsattListe(Database database){
                this.database = database;
                invitertModel = new DefaultListModel();
                alleAnsatteListe = new AlleAnsatteListe(database);
                alleAnsatteListe.setName("alleAnsatteListe");
                alleAnsatteModel = (DefaultListModel) alleAnsatteListe.getModel();
                alleAnsatteListe.setModel(alleAnsatteModel);

                
        //Layout
                this.setLayout(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();
                c.insets = new Insets(5,2,5,2);
                c.weighty = 0;
                c.fill = GridBagConstraints.HORIZONTAL;
                
        //Streker
                c.gridheight = 1;
                c.gridwidth = 2;
                c.gridx = 0;
                c.gridy = 1;
                this.add(new JSeparator(JSeparator.HORIZONTAL),c);
                c.gridwidth = 1;
                c.gridheight = 1;
                
                c.gridheight = 1;
                c.gridwidth = 2;
                c.gridx = 0;
                c.gridy = 4;
                this.add(new JSeparator(JSeparator.HORIZONTAL),c);
                c.gridwidth = 1;
                c.gridheight = 1;
                
                c.fill = GridBagConstraints.BOTH;
                c.anchor = GridBagConstraints.NORTH;
                c.weighty = 0;
                c.weightx = 1;
        //Felt
                sokefelt = new JTextField(10);
                sokefelt.getDocument().addDocumentListener(this);
                c.gridx = 0;
                c.gridy = 0;
                c.weighty = 0;
                c.weightx = 1;
                this.add(sokefelt,c);
                
                sokLabel = new JLabel("Søk");
                sokLabel.setIcon(sokIcon);
                c.gridx = 1;
                c.gridy = 0;
                c.weighty = 0;
                c.weightx = 0.5;
                this.add(sokLabel,c);
                
                c.weighty = 0;
                c.weightx = 1;
                
                invitertLabel = new JLabel("Invitert");
                invitertLabel.setFont(new Font("Arial",Font.ITALIC,12));
                c.gridx = 0;
                c.gridy = 2;
                this.add(invitertLabel,c);
                
                c.fill = GridBagConstraints.BOTH;
                invitertListe = new AlleAnsatteListe(database);
                invitertListe.addListSelectionListener(this);
                invitertListe.setName("invitertListe");
                invitertListe.setModel(invitertModel);
                invitertScrollPane = new JScrollPane(invitertListe);
                c.gridx = 0;
                c.gridy = 3;
                c.gridwidth = 2;
                c.weighty = 0.1;
                this.add(invitertScrollPane,c);
                
                
                ansatteLabel = new JLabel("Ansatte");
                ansatteLabel.setFont(new Font("Arial",Font.ITALIC,12));
                c.weighty = 0;
                c.weightx = 1;
                c.gridx = 0;
                c.gridy = 8;
                this.add(ansatteLabel,c);
                
                
                alleAnsatteListe.addListSelectionListener(this);
                alleAnsatteScrollPane = new JScrollPane(alleAnsatteListe);
                c.weighty = 0.1;
                c.gridwidth = 2;
                c.gridx = 0;
                c.gridy = 9;
                this.add(alleAnsatteScrollPane,c);
                
                
        }
        public AlleAnsatteListe getAlleAnsatteListe(){
                if(alleAnsatteListe == null) return null;
                return alleAnsatteListe;
        }
        
        public AlleAnsatteListe getInvitertListe(){
                if(invitertListe == null) return null;
                return invitertListe;
        }
        
        
        public void nullstillLister(){
                invitertModel.removeAllElements();
                alleAnsatteListe.lagListe();
                alleAnsatteModel = (DefaultListModel)alleAnsatteListe.getModel();
                alleAnsatteListe.setModel(alleAnsatteModel);
                sokefelt.setText("");
        }

        public void valueChanged(ListSelectionEvent e) {
                
                DefaultListModel newMod = (DefaultListModel)alleAnsatteListe.getModel();
                if(e.getSource() == alleAnsatteListe){
                        Person p = (Person)alleAnsatteListe.getSelectedValue();
                        
                        if(p!=null){
                                invitertModel.addElement(p);
                                alleAnsatteModel.removeElement(p);
                                
                                if(newMod != alleAnsatteModel){
                                        newMod.removeElement(p);
                                        alleAnsatteListe.setModel(newMod);
                                }
                        }
                }
                else if(e.getSource() == invitertListe){
                        Person p = (Person)invitertListe.getSelectedValue();
                        if(p!=null){
                                alleAnsatteModel.addElement(p);
                                invitertModel.removeElement(p);
                                if(newMod != alleAnsatteModel){
                                        newMod.addElement(p);
                                        alleAnsatteListe.setModel(newMod);
                                }
                        }
                }
        }
        
        public void changedUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
        }

        public void insertUpdate(DocumentEvent e) {
                DefaultListModel newMod = new DefaultListModel();
                for(int i = 0; i < alleAnsatteListe.getModel().getSize(); i++){
                        String pName =((Person)(alleAnsatteListe.getModel().getElementAt(i))).getNavn().toLowerCase();

                        
                        if(pName.matches(".*"+sokefelt.getText().replace('*', '.').toLowerCase()+".*")){
                                newMod.addElement(alleAnsatteListe.getModel().getElementAt(i));
                        }
                        
                }
                alleAnsatteListe.setModel(newMod);
                
        }

        public void removeUpdate(DocumentEvent e) {
                alleAnsatteListe.setModel(alleAnsatteModel);
                
                DefaultListModel newMod = new DefaultListModel();
                //alleAnsatteListe.lagListe();
                
                for(int i = 0; i < alleAnsatteListe.getModel().getSize(); i++){
                        String pName =((Person)(alleAnsatteListe.getModel().getElementAt(i))).getNavn().toLowerCase();

                        
                        if(pName.matches(".*"+sokefelt.getText().replace('*', '.').toLowerCase()+".*")){
                                newMod.addElement(alleAnsatteListe.getModel().getElementAt(i));
                        }
                        
                }
                        alleAnsatteListe.setModel(newMod);
                
        }
        
                
}

