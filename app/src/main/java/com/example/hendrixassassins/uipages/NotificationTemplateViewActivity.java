package com.example.hendrixassassins.uipages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hendrixassassins.R;
import com.example.hendrixassassins.email.Email;
import com.example.hendrixassassins.email.MessageReader;

import java.util.ArrayList;

public class NotificationTemplateViewActivity extends AppCompatActivity {
    private TextView notificationHeaderView, notificationContentView;
    private TextView  senderNameView, dateView;
    private ImageButton ignoreButton, replayButton;

    private Email emailToBeReplayed;
    private ArrayList<Email> inboxEmails = new ArrayList<>();
    private String currentEmailSubject;
    private MessageReader messageReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_template_view);
        setupComponents();
        getAllEmails();
        getPassedEmailBody();
        setCurrentEmail();
        displayContent();

        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoSendEmail();
            }
        });

        ignoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getPassedEmailBody(){
        currentEmailSubject = getIntent().getStringExtra("clickedUserEmail");
    }

    private void getAllEmails(){
        Log.e("Im in here", "I am called11");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    messageReader = new MessageReader("HendrixAssassinsApp@gmail.com", "AssassinsTest1");
                    Log.e("connedted", "I connected");
                    inboxEmails = messageReader.getInboxMessages();
                    Log.e("sise of messagelist", String.valueOf(inboxEmails.size()));
                }catch (Exception e){
                    Log.e("messea fil", "message Reder failed");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void setupComponents(){
        notificationContentView = findViewById(R.id.notification_body);
        notificationHeaderView = findViewById(R.id.notication_header);
        senderNameView = findViewById(R.id.sender_name_view);
        dateView = findViewById(R.id.date_view);
        ignoreButton = findViewById(R.id.ignore_button);
        replayButton = findViewById(R.id.reply_button);
    }

    private void setCurrentEmail(){
        Log.e("inbox status", "finding curent email");
        Log.e("inbox size value", String.valueOf(inboxEmails.size()));
        for (Email e: inboxEmails){
            if(e.getSubject().equals(currentEmailSubject)){
                Log.e("inbox size", "not empty");
                emailToBeReplayed = e;
                break;
            }
        }
    }

    private void displayContent(){
        if(emailToBeReplayed != null) {
            senderNameView.setText(emailToBeReplayed.getSender().toString());
            notificationHeaderView.setText(emailToBeReplayed.getSubject().toString());
            notificationContentView.setText(emailToBeReplayed.getBody().toString());
            dateView.setText(emailToBeReplayed.getDate().toString());
        }
    }

    private void gotoSendEmail(){
        Intent sendEmailIntent = new Intent(NotificationTemplateViewActivity.this, EmailSenderActivity.class);
        sendEmailIntent.putExtra("clickedUserEmail", "emailToBeReplayed.getSender()");
        startActivity(sendEmailIntent);
    }
}
