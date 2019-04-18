package com.example.hendrixassassins;


import android.content.pm.PackageInstaller;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Store;




public class GmailTestActivity extends AppCompatActivity {
    EditText userName, password, address, subject, message;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findIDs();
        //sendListener();
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Log.e("testereceive", "about to try test receive");
                    testReceive();
                } catch (MessagingException e) {
                    Log.e("testereceive", e.toString());
                }
            } }).start();

    /*    new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("HendrixAssassinsApp@gmail.com",
                            "AssassinsTest1");
                    sender.sendMail("Hello from the Hendrix Assassins Chair", "Welcome to Hendrix Assassins",
                            "HendrixAssassinsApp@gmail.com", "sandersKM@hendrix.edu");
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }

        }).start();
*/
    }
    private void findIDs(){
        setContentView(R.layout.activity_gmail_test);
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        address = findViewById(R.id.address);
        subject = findViewById(R.id.subject);
        message = findViewById(R.id.message);
        send = findViewById(R.id.send);
    }

    //From https://stackoverflow.com/questions/2020088/sending-email-in-android-using-javamail-api-without-using-the-default-built-in-a
    private void sendListener() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    GMailSender sender = new GMailSender("HendrixAssassinsApp@gmail.com", "AssassinsTest1");
                    sender.sendMail("This is Subject",
                            "This is Body",
                            "HendrixAssassinsApp@gmail.com",
                            "HendrixAssassinsApp@gmail.com");
                            Log.e("Gmailtest","tried to send");
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }
        });
    }

    private void testReceive() throws MessagingException {
        Log.e("testereceive","started test receive");
        //From https://stackoverflow.com/questions/7146706/how-to-receive-email-from-gmail-android
        Properties props = new Properties();
        //IMAPS protocol
        props.setProperty("mail.store.protocol","imaps");
        //Set host address
        props.setProperty("mail.imaps.host","imaps.gmail.com");
        //Set specified port
        props.setProperty("mail.imaps.port","993");
        //Using SSL
        props.setProperty("mail.imaps.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.imaps.socketFactory.fallback","false");
        //Setting IMAP session
        Session imapSession= Session.getInstance(props);
        Log.e("testereceive","made a session");
        Store store = imapSession.getStore("imaps");
        Log.e("testereceive","made a store");
        //Connect to server by sending username and password.
        //Example mailServer = imap.gmail.com, username = abc, password = abc
        store.connect("imap.gmail.com","HendrixAssassinsApp","AssassinsTest1");
        Log.e("testereceive","connected to store");
        //Get all mails in Inbox Folder
        Folder inbox=store.getFolder("Inbox");
        Log.e("testereceive","made an inbox");
        inbox.open(Folder.READ_ONLY);
        //Return result to array of message
        Message[]result=inbox.getMessages();
        Log.e("testereceive","about to show message subjects");
        for (int i = 0 ; i<result.length; i++) {
            Message current = result[i];
            Log.e("testereceive",current.getSubject().toString());
            Log.e("testereceive",result[i].toString());
        }
    }
}