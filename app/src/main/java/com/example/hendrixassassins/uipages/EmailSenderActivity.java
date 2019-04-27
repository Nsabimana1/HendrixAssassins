package com.example.hendrixassassins.uipages;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.hendrixassassins.R;
import com.example.hendrixassassins.email.Email;

public class EmailSenderActivity extends AppCompatActivity {
    private TextInputEditText emailToReplayView, emailSubjectView, emailBodyView;
    private ImageButton replayButton;
    private Email emailTobeReplied;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_sender);
        setUpComponents();
        getPassedEmail();
        autoDisplayEmail();
    }

    private void getPassedEmail(){
        Intent intent = getIntent();
        emailTobeReplied = (Email) intent.getSerializableExtra("clickedUserEmail");
    }

    private void setUpComponents(){
        emailToReplayView = findViewById(R.id.emial_to_reply_view);
        emailSubjectView = findViewById(R.id.email_subject_view);
        emailBodyView = findViewById(R.id.email_body_View);
        replayButton = findViewById(R.id.reply_button);
    }

    private void autoDisplayEmail(){
        emailToReplayView.setText(emailTobeReplied.getSender());
    }
}
