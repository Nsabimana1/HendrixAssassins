package com.example.hendrixassassins;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.hendrixassassins.agent.Agent;
import com.example.hendrixassassins.agent.AgentFileHelper;
import com.example.hendrixassassins.agent.AgentList;
import com.example.hendrixassassins.email.Email;
import com.example.hendrixassassins.email.EmailServer;
import com.example.hendrixassassins.email.GMailSender;
import com.example.hendrixassassins.game.Game;
import com.example.hendrixassassins.uipages.HomeActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

public class SetUpGameActivity extends AppCompatActivity {
    private Button createGameButton, verifyAllAgentsButton, refreshEmailsButton;
    private ListView incomingEmails;
    private ArrayList<Email> unread_filtered_emails;
    private IncomingEmailListViewAdapter<Email> incomingEmailListViewAdapter;
    private Game game;
    private AgentList agentList;
    private Context context;

    // TODO we will need to rewrite the Agent file every time we change it!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        Intent intent = getIntent();
        String handlerEmail = intent.getExtras().getString("email");
        game = new Game(handlerEmail);
        setContentView(R.layout.setup_game_fragment);
        unread_filtered_emails = new ArrayList<>();
        getIDs();
        createGameButtonListener();
        verifyAllAgentsButtonListener();
        refreshEmailsButtonListener();
        setupIncomingEmailsListView();
        getAllNewFilterEmails();
    }

    private Email getVerificationEmail(){
        agentList = new AgentList();
        for(Email email: unread_filtered_emails){
            Agent agent = new Agent(email.getSender(), "bob");
            agentList.addAgent(agent);
        }
        updateAgentListFile();
        return new Email(agentList.getAgentEmails(), "Testing Verification", "Congratulations you have signed up to play assassins!");
    }

    private void updateAgentListFile() {
        AgentFileHelper agentFileHelper = new AgentFileHelper();
        agentFileHelper.writeToFile(game.getAgentFileName(), agentList, context);
    }

    private void setToRefreshing(){
        refreshEmailsButton.setText("Retrieving Emails .....");
        refreshEmailsButton.setClickable(false);
    }

    private void setToRefreshable(){
        refreshEmailsButton.setText("Refresh");
        refreshEmailsButton.setClickable(true);
    }

    private void getAllNewFilterEmails(){
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                refreshEmails();
                EmailServer emailServer = EmailServer.get();
                String year = String.valueOf(new GregorianCalendar().get(Calendar.YEAR));
                ArrayList<Email> filteredEmails = emailServer.getEmailsSubjectBeginsWith(year);
                unread_filtered_emails.clear();
                unread_filtered_emails.addAll(filteredEmails);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        incomingEmailListViewAdapter.notifyDataSetChanged();
                        setToRefreshable();
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
        refreshEmailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setToRefreshing();
                getAllNewFilterEmails();
            }
        });
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
                verifyAllAgentsButton.setVisibility(View.INVISIBLE);
                final Email message = getVerificationEmail();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //GMailSender sender = new GMailSender("HendrixAssassinsApp", "AssassinsTest1");
                            GMailSender sender = new GMailSender(game.getEmailBeforeAtSymbol(), game.getPassword());
                            sender.sendMail(message);
                        } catch (AuthenticationFailedException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                unread_filtered_emails.clear();
                incomingEmailListViewAdapter.notifyDataSetChanged();
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
                // TODO set game status to PrePurge and rewrite game file.
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
        refreshEmailsButton = findViewById(R.id.refresh_emails_1);
        setToRefreshing();
    }
}
