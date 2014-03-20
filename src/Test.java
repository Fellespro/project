import javax.swing.JFrame;

import modell.Person;
import GUI.NyHendelse;


public class Test {

	
	public static void main(String[] args)
	{
		NyHendelse hendelse = new NyHendelse(/*new DatabaseKommunikator(), */new Person(1, "blabla"), true);
		JFrame frame = new JFrame();
		frame.setSize(1000,800);
		frame.add(hendelse);
		frame.setVisible(true);
	}
}
