

import java.sql.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EpostSender {
	
	private final String bruker;
	private final String passord;
	private final String host;
	
	private Session session;
	private Transport transport;
	private InternetAddress[] adresse;

	private MimeMessage epost;
	
		
	EpostSender() throws MessagingException
	{
		bruker = "invitasjon.firmax";
		passord = "bartesam";
		host = "smtp.gmail.com";
		
		Properties egenskaper = System.getProperties();
        egenskaper.put("mail.host", host);
        egenskaper.put("mail.transport.protocol", "smtp");
        egenskaper.put("mail.smtp.port", "587");
        egenskaper.put("mail.smtp.auth", "true");
        egenskaper.put("mail.smtp.starttls.enable", "true");

        session = Session.getInstance(egenskaper, null);
        
        transport = session.getTransport("smtp");
	}
	
	public void kobleOpp() throws MessagingException
	{		
		transport.connect(host, bruker, passord);
	}
	
	public void kobleFra() throws MessagingException
	{
		transport.close();
	}
	
	public void sendMelding(Avtale avtale) throws MessagingException
	{
		String mottaker = "";
		String mld = this.lagAvtalemelding(avtale);
		for(int i = 0; i< avtale.hentAntallEksterne();i++)
		{
			mottaker = avtale.hentEmail(i);
			lagEpost(mottaker, "Invitasjon til møte: " + avtale.hentAvtaleNavn(), mld);
			transport.sendMessage(epost, adresse);
		}
	}
	
	public void lagEpost(String mottaker, String emne, String mld) throws AddressException, MessagingException
	{
		epost = new MimeMessage(session);
        epost.setFrom(new InternetAddress(bruker + "@gmail.com"));
        InternetAddress[] address1 = {new InternetAddress(mottaker)};
        adresse = address1;
        epost.setRecipients(MimeMessage.RecipientType.TO, adresse);
        epost.setSubject(emne);
        epost.setSentDate(new Date(0));
        epost.setText(mld);
	}
	
	public String lagAvtalemelding(Avtale avtale)
	{
		String oppsummering = avtale.hentOpprettetAv().getNavn() + " har invitert deg til å delta på et møte hos Firma X. \n\n\nInformasjon om møtet:\n\n";
		oppsummering += "Tittel: ";
		oppsummering += avtale.hentAvtaleNavn() + "\n";
		
		oppsummering += "\nDato: ";
		oppsummering += avtale.hentAvtaleDato() + "\n";
		oppsummering += "Starttid: ";
		oppsummering += avtale.hentStarttid() + "\n";
		oppsummering += "Sluttid: ";
		oppsummering += avtale.hentSluttid() + "\n";
		oppsummering += "Varighet: ";
		oppsummering += avtale.hentVarighet() + "\n";
		oppsummering += "Sted: ";
		oppsummering += avtale.hentRom().getNavn() + "\n";
		
		oppsummering += "\nBeskrivelse: ";
		oppsummering += avtale.hentBeskrivelse() + "\n";
		
		oppsummering += "\nAntall deltakere: ";
		oppsummering += avtale.hentAntallDeltakere() + "\n";
		oppsummering += "Interne deltakere: \n";
		if(avtale.hentAntallInterne() > 0)
		{
			for(int i = 0;i < avtale.hentAntallInterne();i++)
			{
				oppsummering += "- Navn: " + avtale.hentPerson(i).getNavn();
				if(avtale.hentPerson(i) == avtale.hentOpprettetAv() ){
					oppsummering += " (møteinnkaller)\n";
				}
				else
				{
					oppsummering += "\n";
				}
			}
		}
		else
		{
			oppsummering += "-" + "\n";
		}
		oppsummering += "Eksterne deltakere: \n";
		if(avtale.hentAntallEksterne() > 0)
		{
			for(int i = 0;i < avtale.hentAntallEksterne();i++)
			{
				oppsummering += ("- Email: " + avtale.hentEmail(i) + "\n");
			}
		}
		else
		{
			oppsummering += "-" + "\n";
		}
		oppsummering += "\n\nVennlig hilsen Firma X\n\n";
		return oppsummering;
	}
}
