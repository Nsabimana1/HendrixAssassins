package com.example.hendrixassassins.email;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hendrixassassins.R;

import java.io.IOException;
import java.util.ArrayList;

import javax.mail.MessagingException;


public class GmailTestActivity extends AppCompatActivity {
    EditText userName, password, address, subject, message;
    TextView inboxMessages;
    Button send, sendTeamCSV, sendTeamArrayList, refreshInbox;
    GMailSender sender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findIDs();
        //sendListener();
        GmailLogin login = new GmailLogin("HendrixAssassinsApp","AssassinsTest1");
        grabInbox();
        sender = new GMailSender("HendrixAssassinsApp@gmail.com", "AssassinsTest1");
        refreshInboxListener();
        sendListener();
        sendTeamCSVListener();
        sendClassArrayListListener();


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
        sendTeamCSV = findViewById(R.id.sendTeam);
        sendTeamArrayList = findViewById(R.id.sendClass);
    }


    private void sendListener() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                        @Override
                        public void run() {
                    Email email = new Email(address.getText().toString(),
                            subject.getText().toString(),message.getText().toString());
                    try {
                        sender.sendMail(email);
                    } catch (Exception e) {
                        Log.e("Gmailtest-send", e.toString());
                    }
                }}).start();
            }
        });
    }


    private void sendTeamCSVListener() {
        sendTeamCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("GmailTestActivity","clicked team comma delemited button");
                        String addresses = "Turner@hendrix.edu,PojunasDD@hendrix.edu,NsabimanaII@hendrix.edu,SandersKM@hendrix.edu";
                        Email email = new Email(addresses, subject.getText().toString(),message.getText().toString());
                        try {
                            sender.sendMail(email);
                        } catch (Exception e) {
                            Log.e("Gmailtest-sendCSV", e.toString());
                        }
                    }}).start();
            }
        });
    }

    private void sendClassArrayListListener() {
        sendTeamArrayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("GmailTestActivity","clicked team arraylist button");
                        ArrayList<String> addresses = new ArrayList<>();
                        addresses.add("Turner@hendrix.edu");
                        addresses.add("PojunasDD@hendrix.edu");
                        addresses.add("NsabimanaII@hendrix.edu");
                        addresses.add("SandersKM@hendrix.edu");
                        Email email = new Email(addresses, subject.getText().toString(),message.getText().toString());
                        try {
                            sender.sendMail(email);
                        } catch (Exception e) {
                            Log.e("Gmailtest-arraylist", e.toString());
                        }
                        //sendEmail("StoyanowAA@hendrix.edu,BellAW@hendrix.edu,HawkinsBA@hendrix.edu,Turner@hendrix.edu,PojunasDD@hendrix.edu,ferrer@hendrix.edu,SamoreGE@hendrix.edu,NsabimanaII@hendrix.edu,EarnestIA@hendrix.edu,JohnsonJW@hendrix.edu,SandersKM@hendrix.edu,FrancisRP@hendrix.edu");
                    }}).start();
            }
        });
    }


    private void refreshInboxListener() {
        refreshInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabInbox();
            }
        });
    }


    private void grabInbox() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String date, sender, subject, display;
                    ArrayList<Email> inbox = new ArrayList<>();
                    MessageReader reader = new MessageReader("HendrixAssassinsApp", "AssassinsTest1");
                    inbox = reader.getInboxMessages();
                    display="";
                    for (Email currentMessage : inbox ) {
                         date = currentMessage.getDate().toString();
                        sender = currentMessage.getSender();
                        subject = currentMessage.getSubject();
                        display += date + "\n" + sender + "\n" + subject + "\n" + "----------------\n";
                    }
                    final String showMessages = display;
                    GmailTestActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            inboxMessages.setText(showMessages);
                        }
                    });
                } catch (MessagingException | IOException e){
                    Log.e("inbox", "could not read inbox "+ e.toString());
                }
            }    }).start();


    }



    /*private void grabInbox() throws MessagingException {
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
    } */
}