package com.example.hendrixassassins.email;

import android.util.Log;

import com.example.hendrixassassins.agent.Agent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.MessagingException;
import javax.mail.Store;


public class EmailServer {
    private static EmailServer instance;
    private Session imapSession;
    private Store store;
    private String username, password;
    private Folder inbox, sent;
    private ArrayList<Email> inboxList, sentList;

    public static EmailServer get() {
        if (instance == null) {
            instance = new EmailServer();
        }
        return instance;
    }


    public EmailServer()  {
        this.username = "HendrixAssassinsApp";
        this.password = "AssassinsTest1";
        inboxList = new ArrayList<>();
        sentList = new ArrayList<>();
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        props.setProperty("mail.imaps.host", "imaps.gmail.com");
        props.setProperty("mail.imaps.port", "993");
        props.setProperty("mail.imaps.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.imaps.socketFactory.fallback", "false");
        imapSession = Session.getInstance(props);
    }

    public ArrayList<Email> getInboxList() {
        return inboxList;
    }

    public ArrayList<Email> getSentList() {
        return sentList;
    }

    public void refreshInboxMessages() throws MessagingException, IOException {
        store = imapSession.getStore("imaps");
        store.connect("imap.gmail.com", username, password);
        inbox = store.getFolder("Inbox");
        inbox.open(Folder.READ_ONLY);
        Message[] messages = inbox.getMessages();
        ArrayList<Email> emailList = new ArrayList<>();
        for (Message message : messages) {
            emailList.add(new Email(message));
        }
        Log.e("EmailServer","Finished reading inbox");
        inboxList =  emailList;
    }


    public void refreshSentMessages() throws MessagingException, IOException {
        store = imapSession.getStore("imaps");
        store.connect("imap.gmail.com", username, password);
        sent = store.getFolder("[Gmail]/Sent Mail");
        sent.open(Folder.READ_ONLY);
        Message[] messages = sent.getMessages();
        ArrayList<Email> emailList = new ArrayList<>();
        for (Message message : messages) {
            emailList.add(new Email(message));
        }
        sentList = emailList;
    }

    public ArrayList<Email> getUnreadMessages() {
        ArrayList<Email> emailList = new ArrayList<>();
        for (Email current : inboxList) {
            if (!current.getRead()) {
                emailList.add(current);
            }
        }
        return emailList;
    }

    public ArrayList<Email> getReadMessages() {
         ArrayList<Email> emailList = new ArrayList<>();
        for (Email current : sentList) {
            if (current.getRead()) {
                emailList.add(current);
            }
        }
        return emailList;
    }


    public ArrayList<Email> getMessagesFromAgent(Agent agent) {
        ArrayList<Email> emailList = new ArrayList<>();
        for (Email current : inboxList) {
            if (agent.getEmail().equals(current.getSender()) ) {
                emailList.add(current);
            }
        }
        return emailList;
    }

    public ArrayList<Email> getMessagesToAgent(Agent agent) {
        ArrayList<Email> emailList = new ArrayList<>();
        for (Email current : sentList) {
            if (agent.getEmail().equals(current.getRecipients() )) {
                emailList.add(current);
            }
        }
        return emailList;
    }
}
