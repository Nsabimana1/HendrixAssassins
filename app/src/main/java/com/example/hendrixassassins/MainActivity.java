package com.example.hendrixassassins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.hendrixassassins.UItestcompnents.ListViewTestActivity;
import com.example.hendrixassassins.email.Email;
import com.example.hendrixassassins.email.EmailServer;
import com.example.hendrixassassins.email.GmailTestActivity;

import java.io.IOException;

import javax.mail.MessagingException;

public class MainActivity extends AppCompatActivity {
    Button toGmailTest, toListViewTest, toCreateGame;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findIDs();
        toGmailTestButton();
        toListViewTestButton();
        new FileTestActivity(getBaseContext());
        grabInbox();
    }



    private void toListViewTestButton() {
        toListViewTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toListViewTestActivity();
            }
        });

    }

    private void toListViewTestActivity() {
        Intent forwardIntent = new Intent(MainActivity.this, ListViewTestActivity.class);
        startActivity(forwardIntent);
    }

    private void findIDs(){
        setContentView(R.layout.activity_main);
        toGmailTest = findViewById(R.id.toGmailTest);
        toListViewTest = findViewById(R.id.to_listview_test);
    }

    private void toGmailTestButton() {
        toGmailTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toGmailTestActivity();
            }
        });
    }

    private void toGmailTestActivity() {
        Intent forwardIntent = new Intent(MainActivity.this, GmailTestActivity.class);
        // passing the first email in the inbox to the next activity
        // this is ONLY possible because the Email class implements serializable
        forwardIntent.putExtra("FirstEmail", EmailServer.get().getInboxList().get(0));
        startActivity(forwardIntent);
    }

    private void grabInbox() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean read;
                    EmailServer.get().refreshInboxMessages();
                    } catch (MessagingException | IOException e){
                    Log.e("inbox", "could not read inbox "+ e.toString());
                }
            }    }).start();
    }


}
