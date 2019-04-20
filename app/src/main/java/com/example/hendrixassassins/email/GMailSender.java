package com.example.hendrixassassins.email;

//From https://medium.com/@ssaurel/how-to-send-an-email-with-javamail-api-in-android-2fc405441079


import android.util.Log;

import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GMailSender extends javax.mail.Authenticator {
    private String mailhost = "smtp.gmail.com";
    private String user;
    private String password;
    private Session session;

    static {
        Security.addProvider(new JSSEProvider());
    }

    public GMailSender(String userEmailAddress, String password) {
        this.user = userEmailAddress;
        this.password = password;

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", mailhost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");

        session = Session.getDefaultInstance(props, this);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
    }

    public synchronized void sendMail(Email email) throws Exception {
        for (String address : email.getRecipients()) {
            MimeMessage message = new MimeMessage(session);
            DataHandler handler = new DataHandler(new ByteArrayDataSource(email.getBody().getBytes(), "text/plain"));
            message.setSender(new InternetAddress(email.getSender()));
            message.setSubject(email.getSubject());
            message.setDataHandler(handler);
            if (email.getRecipients().get(0).indexOf(',') > 0)
                message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(address));
            else
                message.setRecipient(Message.RecipientType.BCC, new InternetAddress(address));
            Transport.send(message);
        }
    }
}
