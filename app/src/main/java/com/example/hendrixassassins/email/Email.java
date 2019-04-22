package com.example.hendrixassassins.email;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

public class Email {
    private String sender, subject, body;
    private final String gameEmail = "HendrixAssassinsApp@gmail.com";
    private ArrayList<String> recipients;
    private Date date;


    public Email(String recipient, String subject, String body){
        this.sender = gameEmail;
        recipients = new ArrayList<>();
        this.recipients.add(recipient);
        this.subject = subject;
        this.body = body;
        this.date = Calendar.getInstance().getTime();
    }

    public Email(ArrayList<String> recipients, String subject, String body){
        this.sender = gameEmail;
        this.recipients = recipients;
        this.subject = subject;
        this.body = body;
        this.date = Calendar.getInstance().getTime();
    }

    public Email(Message message) throws MessagingException, IOException {
        this.sender = ((InternetAddress) (message.getFrom())[0]).getAddress();
        this.subject = message.getSubject();
        this.body = message.getContent().toString();
        Address[] addresses = message.getAllRecipients();
        recipients = new ArrayList<>();
        for (int i = 0; i< addresses.length; i++) {
            recipients.add(((InternetAddress) (addresses[i])).getAddress());
        }
        this.date = message.getSentDate();
    }


    public String getSender() {return sender;}

    public String getSubject() {return subject;}

    public String getBody() {return body;}

    public ArrayList<String> getRecipients() {return recipients;}

    public Date getDate() {return date;}
}
