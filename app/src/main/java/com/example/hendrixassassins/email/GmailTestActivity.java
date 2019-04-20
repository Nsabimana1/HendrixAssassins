package com.example.hendrixassassins.email;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hendrixassassins.R;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;


public class GmailTestActivity extends AppCompatActivity {
    EditText userName, password, address, subject, message;
    TextView inboxMessages;
    Button send, sendTeam, sendClass, refreshInbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findIDs();
        //sendListener();
        try {
            grabInbox();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        refreshInboxListener();
        sendListener();
        sendTeamListener();
        sendClassListener();


    }
    private void findIDs(){
        setContentView(R.layout.activity_gmail_test);
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        address = findViewById(R.id.address);
        subject = findViewById(R.id.subject);
        message = findViewById(R.id.message);
        send = findViewById(R.id.send);
        refreshInbox = findViewById(R.id.refreshInbox);
        inboxMessages = findViewById(R.id.inboxMessages);
        sendTeam = findViewById(R.id.sendTeam);
        sendClass = findViewById(R.id.sendClass);
    }


    private void sendListener() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                        @Override
                        public void run() {
                            sendEmail(address.getText().toString());


                        }}).start();
            }
        });
    }


    private void sendTeamListener() {
        sendTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendEmail("Turner@hendrix.edu,PojunasDD@hendrix.edu,NsabimanaII@hendrix.edu,SandersKM@hendrix.edu");
                    }}).start();
            }
        });
    }

    private void sendClassListener() {
        sendClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendEmail("StoyanowAA@hendrix.edu,BellAW@hendrix.edu,HawkinsBA@hendrix.edu,Turner@hendrix.edu,PojunasDD@hendrix.edu,ferrer@hendrix.edu,SamoreGE@hendrix.edu,NsabimanaII@hendrix.edu,EarnestIA@hendrix.edu,JohnsonJW@hendrix.edu,SandersKM@hendrix.edu,FrancisRP@hendrix.edu");
                    }}).start();
            }
        });
    }


    public void sendEmail(String recipients ) {
        //From https://stackoverflow.com/questions/2020088/sending-email-in-android-using-javamail-api-without-using-the-default-built-in-a
        GMailSender sender = new GMailSender("HendrixAssassinsApp@gmail.com", "AssassinsTest1");
        try {
            sender.sendMail(new Email(recipients,
                    subject.getText().toString(),
                    message.getText().toString()
                    ));
        } catch (Exception e) {
            Log.e("sending","error sending "+e.toString());
        }
    }

    private void refreshInboxListener() {
        refreshInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    grabInbox();
                } catch (MessagingException e) {
                    Log.e("inbox", "could not diplay inbos "+e.toString());
                }
            }
        });
    }

    private void grabInbox() throws MessagingException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String date, sender, subject, display;
                    //From https://stackoverflow.com/questions/7146706/how-to-receive-email-from-gmail-android
                    Properties props = new Properties();
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
                    Session imapSession = Session.getInstance(props);
                    Store store = imapSession.getStore("imaps");
                     //Connect to server by sending username and password.
                    //Example mailServer = imap.gmail.com, username = abc, password = abc
                    store.connect("imap.gmail.com", "HendrixAssassinsApp", "AssassinsTest1");
                    //Get all mails in Inbox Folder
                    Folder inbox = store.getFolder("Inbox");
                    inbox.open(Folder.READ_ONLY);
                    //Return result to array of message
                    Message[] messages = inbox.getMessages();
                    display = "";
                    for (int i = messages.length - 1; i >= 0 ; i--) {
                        Message currentMessage = messages[i];
                        date = currentMessage.getSentDate().toString();
                        sender = ((InternetAddress) (currentMessage.getFrom())[0]).getAddress();
                        subject = currentMessage.getSubject().toString();
                        display += date + "\n" + sender + "\n" + subject + "\n" + "----------------\n";
                    }
                    final String showMessages = display;
                    GmailTestActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            inboxMessages.setText(showMessages);
                        }
                    });
                } catch (MessagingException e){
                    Log.e("inbox", "could not read inbox "+ e.toString());
                }
                }    }).start();


    }
}