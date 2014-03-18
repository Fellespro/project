

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
        public void addPerson(Person p){
                personListe.add(p);
        }
        
        /**
         * 
         * @param p
         */
        public void removePerson(Person p){
                personListe.remove(p);
        }

        /**
         * 
         * @param p
         * @return
         */
        public boolean containsPerson(Person p){
                return personListe.contains(p);
        }

        @Override
        public Iterator<Person> iterator() {
                return personListe.iterator();
        }
        
        public Person getPerson(int i){
                if(personListe.isEmpty()) return null;
                return personListe.get(i);
        }
        
        public int size() {
                return personListe.size();
        }
        
}
