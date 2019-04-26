package com.example.hendrixassassins.email;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
                grabSent();
            }
        });
    }


    private void grabInbox() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String date, sender, subject, display;
                    boolean read;
                    Log.e("grabinbox: ",Integer.toString(EmailServer.get().getInboxList().size()));
                    Log.e("grabinbox: ",Integer.toString(EmailServer.get().getInboxList().size()));
                    EmailServer.get().refreshInboxMessages();
                    Log.e("grabinbox: ",Integer.toString(EmailServer.get().getInboxList().size()));
                    display="";
                    for (Email currentMessage : EmailServer.get().getInboxList()) {
                        date = currentMessage.getDate().toString();
                        sender = currentMessage.getSender();
                        subject = currentMessage.getSubject();
                        read = currentMessage.getRead();
                        display += date + "\n" + sender + "\n" + subject + "\n" + read + "\n----------------\n";
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

    private void grabSent() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String date, sender, subject, display;
                    boolean read;
                    ArrayList<Email> sent = new ArrayList<>();
                    MessageReader reader = new MessageReader("HendrixAssassinsApp", "AssassinsTest1");
                    sent = reader.getUnreadMessages();
                    display = "";
                    for (Email currentMessage : sent) {
                        date = currentMessage.getDate().toString();
                        sender = currentMessage.getSender();
                        subject = currentMessage.getSubject();
                        read = currentMessage.getRead();
                        display += date + "\n" + sender + "\n" + subject + "\n" + read + "\n----------------\n";
                    }
                    final String showMessages = display;
                    GmailTestActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            inboxMessages.setText(showMessages);
                        }
                    });
                } catch (MessagingException | IOException e) {
                    Log.e("GmailTestActivity", "Failure getting mail: " + e.toString());
                    GmailTestActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Failed to read Sent Emails", Toast.LENGTH_SHORT);
                        }
                    });
               }
            }
        }).start();
    }



}