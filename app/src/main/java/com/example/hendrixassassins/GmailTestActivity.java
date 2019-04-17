package com.example.hendrixassassins;

import android.content.pm.PackageInstaller;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Properties;

public class GmailTestActivity extends AppCompatActivity {
    EditText userName, password, address, subject, message;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findIDs();
        //sendListener();
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

/*
    //From https://stackoverflow.com/questions/2020088/sending-email-in-android-using-javamail-api-without-using-the-default-built-in-a
    private void sendListener() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    GMailSender sender = new GMailSender("username@gmail.com", "password");
                    sender.sendMail("This is Subject",
                            "This is Body",
                            "user@gmail.com",
                            "user@yahoo.com");
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }
            });
    }

    private void testReceive(){
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
        PackageInstaller.Session imapSession=Session.getInstance(props);

        Store store = imapSession.getStore("imaps");
        //Connect to server by sending username and password.
        //Example mailServer = imap.gmail.com, username = abc, password = abc
        store.connect(mailServer,account.username,account.password);
        //Get all mails in Inbox Forlder
        inbox=store.getFolder("Inbox");
        inbox.open(Folder.READ_ONLY);
        //Return result to array of message
        Message[]result=inbox.getMessages();
    }
*/
}
