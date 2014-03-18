package GUI;

import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modell.*;
import database.*;

public class MoteromListe extends JList implements ListSelectionListener{

        //private JList moteListe;
        
        private DefaultListModel moteListeModel;
        private DatabaseKommunikator database;
        private List<ListSelectionListener> listSelectionListeners;
        private Moterom moterom;
        
        public MoteromListe(Dato dato, Time starttid, Time sluttid, int kapasitet, DatabaseKommunikator database){
                listSelectionListeners = new ArrayList<ListSelectionListener>();
                super.addListSelectionListener(this);
                
                this.database = database;
                
                //moteListe = new JList();
                moteListeModel = new DefaultListModel();
                this.setModel(moteListeModel);
                this.setCellRenderer(new MoteromCellRenderer());
                hentMoterom(dato, starttid, sluttid, kapasitet);
        }
        public MoteromListe(DatabaseKommunikator database){
                listSelectionListeners = new ArrayList<ListSelectionListener>();
                super.addListSelectionListener(this);
                
                this.database = database;
                //moteListe = new JList();
                moteListeModel = new DefaultListModel();
                this.setModel(moteListeModel);
                this.setCellRenderer(new MoteromCellRenderer());
        }
        public void addListSelectionListener(ListSelectionListener listener) {
                this.listSelectionListeners.add(listener);
        }
        public void addMoterom(Moterom moterom){
                moteListeModel.addElement(moterom);
        }
        public void removeMoterom(Moterom moterom){
                moteListeModel.removeElement(moterom);
        }
        public void hentMoterom(Dato dato, Time starttid, Time sluttid, int kapasitet){
                
                //TODO: kall finnMoterom i Database
                moteListeModel.removeAllElements();
                try {
                        ArrayList<Moterom> databaseMoterom = database.finnMoterom(dato, starttid, sluttid, kapasitet);
                        for(Moterom mRom: databaseMoterom){
                                this.addMoterom(mRom);
                        }
                        
                } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }
        
        public Moterom getMoterom(int i){
                return (Moterom)moteListeModel.get(i);
        }
        

        public void valueChanged(ListSelectionEvent e) {
                for (ListSelectionListener l : this.listSelectionListeners) {
                        l.valueChanged(e);
                }
        }
        
}
