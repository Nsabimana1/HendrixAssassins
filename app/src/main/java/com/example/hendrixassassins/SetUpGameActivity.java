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
import com.example.hendrixassassins.game.GameMethods;
import com.example.hendrixassassins.game.GameStatus;
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
    String year = String.valueOf(new GregorianCalendar().get(Calendar.YEAR));
    private Game game;
    private AgentList agentList;
    private Context context;

    // TODO we will need to rewrite the Agent file every time we change it!

    // // // // // // // // // // //
    // setup the setup activity   //
    // // // // // // // // // // //

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

    private void getIDs() {
        createGameButton = findViewById(R.id.createGame);
        createGameButton.setEnabled(false);
        verifyAllAgentsButton = findViewById(R.id.verifyAllAgents);
        verifyAllAgentsButton.setEnabled(false);
        incomingEmails = findViewById(R.id.listofIncomingEmails);
        refreshEmailsButton = findViewById(R.id.refresh_emails_1);
        setToRefreshing();
    }

    private void setupIncomingEmailsListView() {
        incomingEmailListViewAdapter = new IncomingEmailListViewAdapter<>(this,
                R.layout.incoming_emails_start_game, unread_filtered_emails);
        incomingEmails.setAdapter(incomingEmailListViewAdapter);
    }


    private void setToRefreshing(){
        refreshEmailsButton.setText(getResources().getString(R.string.refreshing));
        refreshEmailsButton.setClickable(false);
    }

    private void setToRefreshable(){
        refreshEmailsButton.setText(getResources().getString(R.string.refresh));
        refreshEmailsButton.setClickable(true);
    }

    // // // // // // // 
    // refresh emails //
    // // // // // // //

    private void refreshEmailsButtonListener() {
        refreshEmailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setToRefreshing();
                getAllNewFilterEmails();
            }
        });
    }

    private void getAllNewFilterEmails(){
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                refreshEmails();
                EmailServer emailServer = EmailServer.get();
                ArrayList<Email> filteredEmails = emailServer.getEmailsSubjectBeginsWith(year);
                unread_filtered_emails.clear();
                unread_filtered_emails.addAll(filteredEmails);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        incomingEmailListViewAdapter.notifyDataSetChanged();
                        setToRefreshable();
                        verifyAllAgentsButton.setEnabled(true);
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

    // // // // // // // //
    // Verify Agent Code //
    // // // // // // // //

    private void verifyAllAgentsButtonListener() {
        verifyAllAgentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyAllAgentsButton.setVisibility(View.INVISIBLE);
                createGameButton.setEnabled(true);
                initializeAgentList();
                final Email message = getVerificationEmail();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //GMailSender sender = new GMailSender("HendrixAssassinsApp", "AssassinsTest1");
                            GMailSender sender = new GMailSender();
                            // TODO uncomment this to send emails again:
                            //sender.sendMail(message);
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

    private void initializeAgentList(){
        agentList = new AgentList();
        for(Email email: unread_filtered_emails){
            Agent agent = new Agent(email.getSender(), getAgentNameFromSubject(email));
            Log.e("SetupGame",email.getSender()+" "+getAgentNameFromSubject(email));
            agentList.addAgent(agent);
        }
        updateAgentListFile();
    }

    private String getAgentNameFromSubject(Email email) {
        int index = email.getSubject().trim().indexOf(year);
        String name = email.getSubject().trim().substring(index+year.length());
        if (name.trim().length()<1) {
            name = email.getSender();
        }
        return name;
    }

    private Email getVerificationEmail(){
        return new Email(agentList.getAgentEmails(), "Assassins Verification",
                getResources().getString(R.string.verification_email));
    }

    private void updateAgentListFile() {
        AgentFileHelper agentFileHelper = new AgentFileHelper();
        agentFileHelper.writeToFile(game.getAgentFileName(), agentList, context);
    }

    // // // // // // // // //
    //  create game code    //
    // // // // // // // // //

    private void createGameButtonListener() {
        createGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                This is how we get to the Home Activity after we have created the game
                 */
                // TODO set game status to PrePurge and rewrite game file.
                setGameToStarted();
                initializeAgentTargets();
                gotoHomeIntent();
            }
        });
    }

    private void initializeAgentTargets(){
        GameMethods methods = new GameMethods(agentList);
        methods.initializeTargets();
        AgentFileHelper agentFileHelper = new AgentFileHelper();
        agentFileHelper.writeToFile(game.getAgentFileName(), methods.getAgentList(), context);
        for(Agent agent: methods.getAgentList().getAllAgents()){
            Log.e("ZZZ", agent.getEmail() + " " + agent.getCurrentTargetEmail());
        }
    }

    private void setGameToStarted(){
        game.setStatus(GameStatus.PREPURGE);
        //TODO set purge time with a dialog box?
        game.writeGameToFile(context);
    }

    private void gotoHomeIntent(){
        Intent userListView = new Intent(SetUpGameActivity.this, HomeActivity.class);
        userListView.putExtra("email", game.getEmail());
        startActivity(userListView);
        finish();
    }
}
