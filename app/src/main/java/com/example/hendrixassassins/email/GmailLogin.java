package com.example.hendrixassassins.email;

import android.util.Log;

import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.Store;
import javax.mail.Transport;


public class GmailLogin {
    private Properties props;
    private Store store;
    private Session imapSession;
    public static String email, password;







    public GmailLogin(String email, String password) throws MessagingException {
        props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        props.setProperty("mail.imaps.host", "imaps.gmail.com");
        props.setProperty("mail.imaps.port", "993");
        props.setProperty("mail.imaps.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.imaps.socketFactory.fallback", "false");

        this.email = email;
        this.password = password;
        Log.e("Login",this.email+ " "+this.password);
        imapSession = Session.getInstance(props);
        store = imapSession.getStore("imaps");
        store.connect("imap.gmail.com", email, password);
    }

}
