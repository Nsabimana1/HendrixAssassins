package com.example.hendrixassassins.uipages;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.hendrixassassins.R;
import com.example.hendrixassassins.email.Email;
import com.example.hendrixassassins.email.GMailSender;
import com.example.hendrixassassins.game.Game;

public class EmailSenderActivity extends AppCompatActivity {
    private TextInputEditText emailToReplyView, emailSubjectView, emailBodyView;
    private ImageButton replyButton;
    private Email emailTobeReplied;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_sender);
        setUpComponents();
        getPassedEmail();
        autoDisplayEmail();
        replytButtonListener();
    }

    private void getPassedEmail(){
        Intent intent = getIntent();
        emailTobeReplied = (Email) intent.getSerializableExtra("clickedUserEmail");
    }

    private void setUpComponents(){
        emailToReplyView = findViewById(R.id.emial_to_reply_view);
        emailSubjectView = findViewById(R.id.email_subject_view);
        emailBodyView = findViewById(R.id.email_body_View);
        replyButton = findViewById(R.id.reply_button);
    }

    private void autoDisplayEmail(){
        emailToReplyView.setText(emailTobeReplied.getSender());
        if (emailTobeReplied.isPremade()) {
            emailSubjectView.setText(emailTobeReplied.getSubject());
            emailBodyView.setText(emailTobeReplied.getBody());
        }
    }

    private void replytButtonListener() {
        replyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code to send an email to one person
                // you must thread this
                // you must handle the errors (see the toast in this example)
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Email email = new Email(emailToReplyView.getText().toString(),
                                emailSubjectView.getText().toString(),emailBodyView.getText().toString());
                        try {
                            GMailSender sender = new GMailSender();
                            sender.sendMail(email);
                        } catch (Exception e) {
                            Log.e("Gmailtest-send", e.toString());
                            EmailSenderActivity.this.runOnUiThread(new Runnable() {
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

}
