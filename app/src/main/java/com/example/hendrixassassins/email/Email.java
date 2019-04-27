package com.example.hendrixassassins.email;


import android.util.Log;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;


public class Email implements Serializable {
    private String sender, subject, body;
    private final String gameEmail = "HendrixAssassinsApp@gmail.com";
    private ArrayList<String> recipients = new ArrayList<String>();
    private Date date;
    private boolean read;
    private boolean textIsHtml = false;
    private int messageNumber;


    public Email(String recipient, String subject, String body){
        this.sender = gameEmail;
        this.recipients.add(recipient);
        this.subject = subject;
        this.body = body;
        this.date = Calendar.getInstance().getTime();
        this.read = false;
        this.messageNumber = -1;
    }

    public Email(ArrayList<String> recipients, String subject, String body){
        this.sender = gameEmail;
        this.recipients = recipients;
        this.subject = subject;
        this.body = body;
        this.date = Calendar.getInstance().getTime();
        this.read = false;
        this.messageNumber = -1;
    }

    public Email(Message message) throws MessagingException, IOException {
        this.sender = ((InternetAddress) (message.getFrom())[0]).getAddress();
        this.subject = message.getSubject();
        this.body = getText(message);
        Address[] addresses = message.getAllRecipients();
        for (Address address : addresses) {
            recipients.add(((InternetAddress) (address)).getAddress());
        }
        this.date = message.getSentDate();
        read = message.isSet(Flags.Flag.SEEN);
        message.getMessageNumber();
        this.messageNumber = message.getMessageNumber();
    }


    public String getSender() {return sender;}

    public String getSubject() {return subject;}

    public String getBody() {return body;}

    public ArrayList<String> getRecipients() {return recipients;}

    public Date getDate() {return date;}

    public boolean getRead() {return read;}

    public void setRead(boolean value) throws MessagingException {
        this.read = value;
        if (messageNumber > 0 ) {
            EmailServer.get().setMessageRead(messageNumber,value);
        }

    }


    // Modified from https://javaee.github.io/javamail/FAQ#mainbody
    /**
     * Return the primary text content of the message.
     */
    private String getText(Part p) throws
            MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String)p.getContent();
            textIsHtml = p.isMimeType("text/html");
            return s;
        }

        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart)p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    //if (text == null)
                    text = getText(bp);
                    return text;
                } else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null)
                        return s;
                } else {
                    return getText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart)p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }

        return null;
    }



}
