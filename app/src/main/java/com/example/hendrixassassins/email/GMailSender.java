package com.example.hendrixassassins.email;

//From https://medium.com/@ssaurel/how-to-send-an-email-with-javamail-api-in-android-2fc405441079


import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GMailSender extends javax.mail.Authenticator {
    private String mailhost = "smtp.gmail.com";
    private String email;
    private String password;
    private Session session;

    static {
        Security.addProvider(new JSSEProvider());
    }

    public GMailSender() throws AuthenticationFailedException{
        this.email = GmailLogin.email;
        this.password = GmailLogin.password;

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", mailhost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");
        session = Session.getInstance(props, this);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(email, password);
    }

    public synchronized void sendMail(Email email) throws Exception {
        for (String address : email.getRecipients()) {
            MimeMessage message = new MimeMessage(session);
            DataHandler handler = new DataHandler(new ByteArrayDataSource(email.getBody().getBytes(), "text/plain"));
            message.setSender(new InternetAddress(email.getSender()));
            message.setSubject(email.getSubject());
            message.setDataHandler(handler);
            if (address.indexOf(',') > 0)
                message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(address));
            else
                message.setRecipient(Message.RecipientType.BCC, new InternetAddress(address));
            Transport.send(message);
        }
    }
}
