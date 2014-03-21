import javax.swing.JFrame;

import modell.Person;
import GUI.NyHendelse;


public class Test {

	
	public static void main(String[] args)
	{
		NyHendelse hendelse = new NyHendelse(/*new DatabaseKommunikator(), */new Person(1, "blabla"), true);
		hendelse.setAutoOppforing();
		JFrame frame = new JFrame();
		frame.setSize(400,600);
		frame.add(hendelse);
		frame.setVisible(true);
	}
}
