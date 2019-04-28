package com.example.hendrixassassins.email;

import android.util.Log;

import com.example.hendrixassassins.agent.Agent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.MessagingException;
import javax.mail.Store;


public class EmailServer {
    private static EmailServer instance;
    private Session imapSession;
    private Store store;
    private final String email, password;
    private Folder inbox, sent;
    private ArrayList<Email> inboxList, sentList;

    public static EmailServer get() {
        if (instance == null) {
            instance = new EmailServer();
        }
        return instance;
    }


    public EmailServer()  {
        this.email = GmailLogin.email;
        this.password = GmailLogin.password;
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
        store.connect("imap.gmail.com", email, password);
        inbox = store.getFolder("Inbox");
        inbox.open(Folder.READ_ONLY);
        Message[] messages = inbox.getMessages();
        ArrayList<Email> emailList = new ArrayList<>();
        for (int i = messages.length-1; i >= 0; i--) {
            emailList.add(new Email(messages[i]));
        }
        Log.e("EmailServer","Finished reading inbox");
        inboxList =  emailList;
    }


    public void refreshSentMessages() throws MessagingException, IOException {
        store = imapSession.getStore("imaps");
        store.connect("imap.gmail.com", email, password);
        sent = store.getFolder("[Gmail]/Sent Mail");
        sent.open(Folder.READ_ONLY);
        Message[] messages = sent.getMessages();
        ArrayList<Email> emailList = new ArrayList<>();
        for (int i = messages.length-1; i >= 0; i--) {
            emailList.add(new Email(messages[i]));
        }
        sentList = emailList;
    }

    public void setMessageRead(int messageNumber, boolean value) throws MessagingException {
        Store store = imapSession.getStore("imaps");
        store.connect("imap.gmail.com", "HendrixAssassinsApp", "AssassinsTest1");
        Folder folder = store.getFolder("Inbox");
        folder.open(folder.READ_WRITE);
        Message[] messages = folder.getMessages();
        if (messageNumber > 0) {
            messages[messageNumber - 1].setFlag(Flags.Flag.SEEN, value);
        }
        folder.close(true);
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
            for (String recipient : current.getRecipients()) {
                if (agent.getEmail().equals(recipient )) {
                    emailList.add(current);
                }
            }
        }
        return emailList;
    }

    public ArrayList<Email> getEmailsSubjectBeginsWith(String subject) {
        ArrayList<Email> emailList = new ArrayList<>();
        for (Email current : inboxList) {
            if (current.getSubject().trim().toLowerCase().startsWith(subject.trim().toLowerCase())) {
                emailList.add(current);
            }
        }
        return emailList;
    }

}
