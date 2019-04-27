package com.example.hendrixassassins.email;


import android.content.Intent;
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
        testStuff();
        sendListener();
        sendTeamCSVListener();
        sendClassArrayListListener();

        //test the login verification class
        GmailLogin login = new GmailLogin("HendrixAssassinsApp","AssassinsTest1");

        //display the inbox messages in the textview
        grabInbox();



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
                // code to send an email to one person
                // you must thread this
                // you must handle the errors (see the toast in this example)
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Email email = new Email(address.getText().toString(),
                                subject.getText().toString(), message.getText().toString());
                        try {
                            sender = new GMailSender("HendrixAssassinsApp@gmail.com", "AssassinsTest1");
                            sender.sendMail(email);
                        } catch (Exception e) {
                            Log.e("Gmailtest-send", e.toString());
                            GmailTestActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast toast = Toast.makeText(getApplicationContext(),
                                            "Failed to send", Toast.LENGTH_SHORT);
                                    toast.show();
                                }});
                        }
                    }
                }).start();
            }
        });
    }


    private void sendTeamCSVListener() {
        // not recommended to send to recipients with addresses separated by , but it works
        sendTeamCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String addresses = "Turner@hendrix.edu,PojunasDD@hendrix.edu,NsabimanaII@hendrix.edu,SandersKM@hendrix.edu";
                        Email email = new Email(addresses,
                                subject.getText().toString(),message.getText().toString());
                        try {
                            sender = new GMailSender("HendrixAssassinsApp@gmail.com", "AssassinsTest1");
                            sender.sendMail(email);
                        } catch (Exception e) {
                            Log.e("Gmailtest-send csv", e.toString());
                            GmailTestActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast toast = Toast.makeText(getApplicationContext(),
                                            "Failed to send", Toast.LENGTH_SHORT);
                                    toast.show();
                                }});
                        }
                    }}).start();
            }
        });
    }

    private void sendClassArrayListListener() {
        sendTeamArrayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code to send an email to an arraylist of recipients
                // you must thread this
                // you must handle the errors (see the toast in this example)
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<String> addresses = new ArrayList<>();
                        addresses.add("Turner@hendrix.edu");
                        addresses.add("PojunasDD@hendrix.edu");
                        addresses.add("NsabimanaII@hendrix.edu");
                        addresses.add("SandersKM@hendrix.edu");
                        Email email = new Email(addresses, subject.getText().toString(),
                                message.getText().toString());
                        try {
                            sender = new GMailSender("HendrixAssassinsApp@gmail.com", "AssassinsTest1");
                            sender.sendMail(email);
                        } catch (Exception e) {
                            Log.e("Gmailtest-arraylist", e.toString());
                            GmailTestActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast toast = Toast.makeText(getApplicationContext(),
                                            "Failed to send", Toast.LENGTH_SHORT);
                                    toast.show();
                                }});
                        }
                    }}).start();
            }
        });
    }


    private void testStuff() {
        //this button is used to test various email functionality
        refreshInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        setEmailStatusTest(0, true);
                    }}).start();

            }
        });

    }

    private void setEmailStatusTest(int i, boolean value) {
        try {
            Email currentMessage = EmailServer.get().getInboxList().get(i);
            currentMessage.setRead(value);
            String date = currentMessage.getDate().toString();
            String sender = currentMessage.getSender();
            String subject = currentMessage.getSubject();
            boolean read = currentMessage.getRead();
            String body = currentMessage.getBody();
            String display = date + "\n" + sender + "\n" + subject + "\n" + read + "\n"
                    +body+"\n----------------\n";
            final String showMessages = display;
            GmailTestActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    inboxMessages.setText(showMessages);
                }
            });
        } catch (MessagingException e) {
            Log.e("readStatus", e.toString());
            GmailTestActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Failed to change read status", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
    }



    private void testAddExtraEmail() {
        String date, sender, subject, body;
        boolean read;
        //look at the first email in the inboxlist
        Email current =  EmailServer.get().getInboxList().get(0);
        sender = current.getSender();
        subject = current.getSubject();
        body = current.getBody();
        //display some stuff from the first email in the inbox
        String display = sender + "\n" + subject + "\n" + body + "\n----------------\n";

        //get the email object that was passed from the main activity with putextra
        Intent i = getIntent();
        Email rebuilt = (Email) i.getSerializableExtra("FirstEmail");
        sender = rebuilt.getSender();
        subject = rebuilt.getSubject();
        body = rebuilt.getBody();

        // display some stuff from the email that was passed from the main activity
        //Note: it worked both displayed messages are the same
        display += sender + "\n" + subject + "\n" + body + "\n----------------\n";
        final String showMessage = display;
        GmailTestActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                inboxMessages.setText(showMessage);
            }
        });
    }


    private void grabInbox() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //EmailServer.get().refreshInboxMessages();
                    displayEMails(EmailServer.get().getInboxList());
                } catch (Exception e) {
                    Log.e("Inbox", e.toString());
                    GmailTestActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Failed to read inbox", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
                }
            }
        }).start();
    }

    private void grabSent() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EmailServer.get().refreshSentMessages();
                    displayEMails(EmailServer.get().getSentList());
                } catch (Exception e) {
                    Log.e("Sent Items", e.toString());
                    GmailTestActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Failed to read sent items", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
                }
            }
        }).start();
    }

    private void displayEMails(ArrayList<Email> list){
        String date, sender, subject, body, display;
        boolean read;
        display="";
        for (Email currentMessage : list) {
            date = currentMessage.getDate().toString();
            sender = currentMessage.getSender();
            subject = currentMessage.getSubject();
            read = currentMessage.getRead();
            body = currentMessage.getBody();
            display += date + "\n" + sender + "\n" + subject + "\n" + read + "\n"
                    +body+"\n----------------\n";
        }
        final String showMessages = display;
        GmailTestActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                inboxMessages.setText(showMessages);
            }
        });
    }

}