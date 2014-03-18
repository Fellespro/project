package modell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PersonListe implements Iterable<Person>{
        private List<Person> personListe = new ArrayList<Person>();
        
        
        public PersonListe(){
        }
        /**
         * 
         * @param p
         */
        public void leggTilPerson(Person p){
                personListe.add(p);
        }
        
        public Person finnPerson(int ID)
        {
        	for(int i = 0;i < personListe.size();i++)
        	{
        		if(hentPerson(i).getPersonId() == ID)
        		{
        			return hentPerson(i);
        		}
        	}
        	return null;
        }
        
        /**
         * 
         * @param p
         */
        public void fjernPerson(Person p){
                personListe.remove(p);
        }

        /**
         * 
         * @param p
         * @return
         */
        public boolean inneholderPerson(Person p){
                return personListe.contains(p);
        }

        @Override
        public Iterator<Person> iterator() {
                return personListe.iterator();
        }
        
        public Person hentPerson(int i){
                if(personListe.isEmpty()) return null;
                return personListe.get(i);
        }
        
        public int antallPersoner() {
                return personListe.size();
        }
        
}
