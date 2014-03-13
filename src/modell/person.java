package modell;
public class Person {

        private String navn;
        private int personId;
        private String username;
        
        public Person() {
        	
        }
        
        public Person(String username){
        	this.username = username;
        }
        
        public Person(int personId, String navn) {
                this.personId = personId;
                this.navn = navn;
        }
        
        public void setNavn(String navn) {
                this.navn = navn;
        }
        
        public String getNavn() {
                return navn;
        }
        public int getPersonId (){
                return personId;
        }
        
        public void setPersonId(int personId){
                this.personId = personId;
        }
        
        public String getUsername(){
        	return username;
        }
        
}
