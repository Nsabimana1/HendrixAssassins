package com.example.hendrixassassins.uipages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hendrixassassins.AgentProfileActivity;
import com.example.hendrixassassins.R;
import com.example.hendrixassassins.email.Email;
import com.example.hendrixassassins.email.EmailServer;

import java.util.ArrayList;

public class NotificationTemplateViewActivity extends AppCompatActivity {
    private TextView notificationHeaderView, notificationContentView;
    private TextView  senderNameView, dateView;
    private Button confirmKillButton, viewProfileButton;
    private ImageButton ignoreButton, replayButton;

    private Email emailToBeReplayed;
    private ArrayList<Email> inboxEmails = new ArrayList<>();
    private Email currentEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_template_view);
        setupComponents();
        getAllEmails();
        getPassedEmailBody();
        displayContent();

        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoSendEmailPage();
            }
        });

        viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAgentProfile();
            }
        });
    }

    private void goToAgentProfile(){
        Intent agentProfileIntent = new Intent(NotificationTemplateViewActivity.this, AgentProfileActivity.class);
        agentProfileIntent.putExtra("agentEmail", emailToBeReplayed.getSender());
        startActivity(agentProfileIntent);
    }

    private void getPassedEmailBody(){
        Intent intent = getIntent();
        emailToBeReplayed = (Email) intent.getSerializableExtra("clickedUserEmail");
    }

    private void getAllEmails(){
        Log.e("Im in here", "I am called11");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EmailServer.get().refreshInboxMessages();
                    Log.e("connedted", "I connected");
                    inboxEmails = EmailServer.get().getInboxList();
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
        confirmKillButton = findViewById(R.id.confirm_kill);
        viewProfileButton = findViewById(R.id.view_record);
        replayButton = findViewById(R.id.reply_button);
    }


    private void displayContent(){
        if(emailToBeReplayed != null) {
            senderNameView.setText(emailToBeReplayed.getSender().toString());
            notificationHeaderView.setText(emailToBeReplayed.getSubject().toString());
            notificationContentView.setText(emailToBeReplayed.getBody().toString());
            dateView.setText(emailToBeReplayed.getDate().toString());
        }
    }

    private void gotoSendEmailPage(){
        Intent sendEmailIntent = new Intent(NotificationTemplateViewActivity.this, EmailSenderActivity.class);
        sendEmailIntent.putExtra("clickedUserEmail", emailToBeReplayed);
        startActivity(sendEmailIntent);
    }
}
