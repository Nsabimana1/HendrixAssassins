package com.example.hendrixassassins.email;


import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

public class Email implements Serializable {
    private String sender, subject, body;
    private final String gameEmail = "HendrixAssassinsApp@gmail.com";
    private ArrayList<String> recipients = new ArrayList<String>();
    private Date date;
    private boolean read;


    public Email(String recipient, String subject, String body){
        this.sender = gameEmail;
        this.recipients.add(recipient);
        this.subject = subject;
        this.body = body;
        this.date = Calendar.getInstance().getTime();
        this.read = false;
    }

    public Email(ArrayList<String> recipients, String subject, String body){
        this.sender = gameEmail;
        this.recipients = recipients;
        this.subject = subject;
        this.body = body;
        this.date = Calendar.getInstance().getTime();
        this.read = false;
    }

    public Email(Message message) throws MessagingException, IOException {
        this.sender = ((InternetAddress) (message.getFrom())[0]).getAddress();
        this.subject = message.getSubject();
        this.body = message.getContent().toString();
        Address[] addresses = message.getAllRecipients();
        for (Address address : addresses) {
            recipients.add(((InternetAddress) (address)).getAddress());
        }
        this.date = message.getSentDate();
        read = message.isSet(Flags.Flag.SEEN);
    }


    public String getSender() {return sender;}

    public String getSubject() {return subject;}

    public String getBody() {return body;}

    public ArrayList<String> getRecipients() {return recipients;}

    public Date getDate() {return date;}

    public boolean getRead() {return read;}


}
