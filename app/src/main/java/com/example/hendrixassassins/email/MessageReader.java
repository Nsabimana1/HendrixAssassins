package com.example.hendrixassassins.email;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hendrixassassins.R;
import com.example.hendrixassassins.agent.Agent;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

public class MessageReader {
    private String date, sender, subject, display;
    private Properties props;
    private Session imapSession;
    private Store store;


    public MessageReader(String userName, String password)  {
        props = new Properties();
        //IMAPS protocol
        props.setProperty("mail.store.protocol", "imaps");
        //Set host address
        props.setProperty("mail.imaps.host", "imaps.gmail.com");
        //Set specified port
        props.setProperty("mail.imaps.port", "993");
        //Using SSL
        props.setProperty("mail.imaps.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.imaps.socketFactory.fallback", "false");
        //Setting IMAP session
        imapSession = Session.getInstance(props);
        try {
            store = imapSession.getStore("imaps");
        } catch (NoSuchProviderException e) {
            Log.e("MessageReader getstore", e.toString());
        }
        try {
            store.connect("imap.gmail.com", userName, password);
        } catch (MessagingException e) {
            Log.e("Reader store connect", e.toString());
        }
    }

    public ArrayList<Email> getInboxMessages() throws MessagingException, IOException {
        Folder inbox = store.getFolder("Inbox");
        inbox.open(Folder.READ_ONLY);
        Message[] messages = inbox.getMessages();
        ArrayList<Email> emailList = new ArrayList<>();
        for (int i = 0; i < messages.length; i++) {
            emailList.add(new Email(messages[i]));
        }
        return emailList;
    }


    public ArrayList<Email> getSentMessages() throws MessagingException, IOException {
        Folder inbox = store.getFolder("[Gmail]/Sent Mail");
        inbox.open(Folder.READ_ONLY);
        Message[] messages = inbox.getMessages();
        ArrayList<Email> emailList = new ArrayList<>();
        for (int i = 0; i < messages.length; i++) {
            emailList.add(new Email(messages[i]));
        }
        return emailList;
    }

    public ArrayList<Email> getUnreadMessages() throws MessagingException, IOException {
        Folder inbox = store.getFolder("Inbox");
        inbox.open(Folder.READ_ONLY);
        Message[] messages = inbox.getMessages();
        ArrayList<Email> emailList = new ArrayList<>();
        for (Message current : messages) {
            if (current.isSet(Flags.Flag.SEEN)) {
                emailList.add(new Email(current));
            }
        }
        return emailList;
    }

    public ArrayList<Email> getReadMessages() throws MessagingException, IOException {
        Folder inbox = store.getFolder("Inbox");
        inbox.open(Folder.READ_ONLY);
        Message[] messages = inbox.getMessages();
        ArrayList<Email> emailList = new ArrayList<>();
        for (Message current : messages) {
            if (!current.isSet(Flags.Flag.SEEN)) {
                emailList.add(new Email(current));
            }
        }
        return emailList;
    }


    public ArrayList<Email> getMessagesFromAgent(Agent agent) throws MessagingException, IOException {
        Folder inbox = store.getFolder("Inbox");
        inbox.open(Folder.READ_ONLY);
        Message[] messages = inbox.getMessages();
        ArrayList<Email> emailList = new ArrayList<>();
        for (int i = 0; i < messages.length; i++) {
            if (agent.getEmail().equals(((InternetAddress) (messages[i].getFrom())[0]).getAddress()) ) {
                emailList.add(new Email(messages[i]));
            }
        }
        return emailList;
    }

    public ArrayList<Email> getMessagesToAgent(Agent agent) throws MessagingException, IOException {
        Folder inbox = store.getFolder("Inbox");
        inbox.open(Folder.READ_ONLY);
        Message[] messages = inbox.getMessages();
        ArrayList<Email> emailList = new ArrayList<>();
        for (int i = 0; i < messages.length; i++) {
            if (agent.getEmail().equals(((InternetAddress) (messages[i].getFrom())[0]).getAddress()) ) {
                emailList.add(new Email(messages[i]));
            }
        }
        return emailList;
    }

}


