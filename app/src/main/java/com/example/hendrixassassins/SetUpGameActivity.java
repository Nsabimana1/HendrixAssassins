package com.example.hendrixassassins;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.hendrixassassins.email.Email;
import com.example.hendrixassassins.email.EmailServer;
import com.example.hendrixassassins.email.MessageReader;
import com.example.hendrixassassins.uipages.HomeActivity;
import com.example.hendrixassassins.uipages.LoginActivity;
import com.example.hendrixassassins.uipages.SetUpGameFragment;

import java.io.IOException;
import java.util.ArrayList;

import javax.mail.MessagingException;

public class SetUpGameActivity extends AppCompatActivity {
    private Button createGameButton, verifyAllAgentsButton, refreshEmails;
    private ListView incomingEmails;
    private ArrayList<Email> unread_filtered_emails;
    private IncomingEmailListViewAdapter<Email> incomingEmailListViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_game_fragment);
        unread_filtered_emails = new ArrayList<>();
        getIDs();
        createGameButtonListener();
        verifyAllAgentsButtonListener();
        refreshEmailsButtonListener();
        setupIncomingEmailsListView();

        getAllNewFilterEmails();



    }

    private void getAllNewFilterEmails(){
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                refreshEmails();
                EmailServer emailServer = EmailServer.get();
                //TODO: we want to not have magic values
                ArrayList<Email> filteredEmails = emailServer.getEmailsSubjectBeginsWith("2019");
                unread_filtered_emails.clear();
                unread_filtered_emails.addAll(filteredEmails);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        incomingEmailListViewAdapter.notifyDataSetChanged();
                    }
                });

            }
        });
        thread.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(thread.isAlive()){
                    Log.d("IM WAITING", thread.getState().toString());
                }
            }
        });


    }

    // Don't run this without threading it
    private void refreshEmails(){
        try {
            EmailServer.get().refreshInboxMessages();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void refreshEmailsButtonListener() {
        refreshEmails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getAllNewFilterEmails();
            }
        });
    }

    private void grabSent() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String date, sender, subject, display;
                    boolean read;
                    MessageReader reader = new MessageReader("HendrixAssassinsApp", "AssassinsTest1");
                    ArrayList<Email> sent = reader.getUnreadMessages();
                    unread_filtered_emails.addAll(sent);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            incomingEmailListViewAdapter.notifyDataSetChanged();
                        }
                    });
                    //this crashs the project


                } catch (MessagingException | IOException e) {
                    Log.e("SetUpGameFragment", "Failure getting mail: " + e.toString());
                }
            }
        }).start();
    }

    private void setupIncomingEmailsListView() {
        incomingEmailListViewAdapter = new IncomingEmailListViewAdapter<>(this,
                R.layout.incoming_emails_start_game, unread_filtered_emails);

        incomingEmails.setAdapter(incomingEmailListViewAdapter);

    }


    private void verifyAllAgentsButtonListener() {
        verifyAllAgentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void createGameButtonListener() {
        createGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                This is how we get to the Home Activity after we have created the game
                 */
                gotoHomeIntent();
            }
        });

    }

    private void gotoHomeIntent(){
        Intent userListView = new Intent(SetUpGameActivity.this, HomeActivity.class);
        startActivity(userListView);
        finish();
    }

    private void getIDs() {
        createGameButton = findViewById(R.id.createGame);
        verifyAllAgentsButton = findViewById(R.id.verifyAllAgents);
        incomingEmails = findViewById(R.id.listofIncomingEmails);
        refreshEmails = findViewById(R.id.refresh_emails_1);
    }


}
