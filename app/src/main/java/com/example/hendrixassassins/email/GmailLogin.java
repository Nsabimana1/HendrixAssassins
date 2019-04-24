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


    public GmailLogin(final String username, final String password) {
        props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        props.setProperty("mail.imaps.host", "imaps.gmail.com");
        props.setProperty("mail.imaps.port", "993");
        props.setProperty("mail.imaps.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.imaps.socketFactory.fallback", "false");
        //Setting IMAP session
        imapSession = Session.getInstance(props);
             new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        store = imapSession.getStore("imaps");
                        store.connect("imap.gmail.com", username, password);
                        // Handle Login Success Here
                        Log.e("gmail login","Login Success!");
                    } catch (MessagingException e) {
                        // Handle Login Failure HERE
                        Log.e("gmail login","Login Failure!");
                    }
                }    }).start();
    }
}
