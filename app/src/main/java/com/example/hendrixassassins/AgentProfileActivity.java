package com.example.hendrixassassins;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hendrixassassins.UItestcompnents.CustomListViewAdapter;
import com.example.hendrixassassins.agent.Agent;
import com.example.hendrixassassins.agent.AgentFileHelper;
import com.example.hendrixassassins.agent.AgentList;
import com.example.hendrixassassins.agent.AgentStatus;
import com.example.hendrixassassins.email.Email;
import com.example.hendrixassassins.email.GMailSender;
import com.example.hendrixassassins.email.GmailLogin;
import com.example.hendrixassassins.game.Game;
import com.example.hendrixassassins.game.GameMethods;
import com.example.hendrixassassins.uipages.DialogBoxes.ChangeNameDialogFragment;
import com.example.hendrixassassins.uipages.DialogBoxes.PopupChangeAgentName;
import com.example.hendrixassassins.uipages.DialogBoxes.PopupChangeAgentStatus;
import com.example.hendrixassassins.uipages.HomeActivity;

import java.util.ArrayList;

import javax.mail.AuthenticationFailedException;

public class AgentProfileActivity extends AppCompatActivity implements
        PopupChangeAgentName.DialogListener, PopupChangeAgentStatus.DialogListener {
    private TextView agentName, AgentTotalPoints, AgentPersonalKills, agentEmail,
            agentStatusCurrent, currentTarget;
    private ImageButton changeAgentName, sendEmailAgent, removeAgent, editPlayerStatus;
    private ListView agentEmailHistory, agentKillHistory;
    private Context context;
    private Game game;
    private Agent agent;
    private AgentList agentList;
    private AgentFileHelper agentFileHelper;

    // // // // // // // // //
    //   set  up  screen    //
    // // // // // // // // //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        Intent intent = getIntent();
        String handlerEmail = intent.getExtras().getString("handlerEmail");
        String agentEmail = intent.getExtras().getString("agentEmail");
        setupGame(handlerEmail);
        setupAgentList();
        setupAgent(agentEmail);
        setContentView(R.layout.activity_agent_profile);
        setupIDs();
        personalizeAgentPage();
    }

    private void setupGame(String handlerEmail){
        game = new Game(handlerEmail);
        game.readGameFromFile(context);
    }

    private void setupAgentList(){
        agentFileHelper = new AgentFileHelper();
        agentList = agentFileHelper.readFromFile(game.getAgentFileName(), context);
    }

    private void setupAgent(String agentEmail) {
        agent = agentList.getAgentWithEmailAddress(agentEmail);
    }

    private void setupIDs() {
        agentName = findViewById(R.id.agentName);
        AgentTotalPoints = findViewById(R.id.AgentTotalPoints);
        AgentPersonalKills = findViewById(R.id.AgentPersonalKills);
        agentEmail = findViewById(R.id.agentEmail);
        // This was causing errors for some reason.
        agentStatusCurrent = findViewById(R.id.agentStatus);
        changeAgentName = findViewById(R.id.changeAgentName);
        sendEmailAgent = findViewById(R.id.sendEmailAgent);
        removeAgent = findViewById(R.id.removeAgent);
        editPlayerStatus = findViewById(R.id.editPlayerStatus);
        agentEmailHistory = findViewById(R.id.agentEmailHistory);
        agentKillHistory = findViewById(R.id.agentKillHistory);
        currentTarget = findViewById(R.id.currentAgentTarget);
    }

    private void personalizeAgentPage(){
        agentName.setText(agent.getName());
        AgentTotalPoints.setText(String.valueOf(agent.getPointsTotal()));
        AgentPersonalKills.setText(String.valueOf(agent.getPersonalKills()));
        agentStatusCurrent.setText(agent.getStatus().toString());
        if(agent.getCurrentTarget() != null) {
            currentTarget.setText(agent.getCurrentTarget().getName());
        }
    }

    private void updateAgentListFile(){
        agentFileHelper.writeToFile(game.getAgentFileName(), agentList, context);
    }

    // // // // // // // // //
    // navigate off screen  //
    // // // // // // // // //

    @Override
    public void onBackPressed() {

    }

    public void goToTargetsProfile(View view) {
        gotoAgentProfile(agent.getCurrentTarget());
    }

    private void gotoAgentProfile(Agent agent) {
        Intent userListView = new Intent(AgentProfileActivity.this, AgentProfileActivity.class);
        userListView.putExtra("handlerEmail", game.getEmail());
        userListView.putExtra("agentEmail", agent.getEmail());
        startActivity(userListView);
    }

    public void goBackToFragment(View view) {
        Intent userListView = new Intent(AgentProfileActivity.this, HomeActivity.class);
        startActivity(userListView);
    }

    public void sendEmailAgentButtonListener(View view) {
        gotoSendEmail();
    }

    private void gotoSendEmail() {
        sendEmailAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //TODO: need to make sure going to the email intent won't cause crashes
    }

    // // // // // // // // //
    // change name of agent //
    // // // // // // // // //


    public void changeAgentNameButtonListener(View view) {
        changeAgentName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupChangeAgentName changeAgentName = new PopupChangeAgentName();
                changeAgentName.show(getSupportFragmentManager(), "changeName");
//                DialogFragment changeNameFragment = new ChangeNameDialogFragment();
//                changeNameFragment.show(getSupportFragmentManager(), "changeName");
            }
        });
    }

    @Override
    public void changeName(String updateName) {
        agent.setName(updateName);
        updateAgentListFile();
        agentName.setText(agent.getName());
    }

    // // // // // // //
    // change status  //
    // // // // // // //

    private void reassignAgentWithTarget(Agent target){
        Agent newKiller = agentList.getAgentAssignedToKill(target);
        newKiller.setCurrentTarget(target.getCurrentTarget());
        Log.e("QQQ", newKiller.getEmail());
        sendTargetEmail(newKiller);
        setNoTarget(target);
    }

    public void removeAgentButtonListener(View view) {
        removeAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //TODO: remove agent from game
        // why is this seperate from the status changing?
    }

    public void editPlayerStatusButtonListener(View view) {
        editPlayerStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupChangeAgentStatus changeAgentStatus = new PopupChangeAgentStatus();
                changeAgentStatus.show(getSupportFragmentManager(), "changeAgentStatus");
            }
        });
        //TODO: edit player status
    }

    @Override
    public void changeStatus(String updatedName) {
        AgentStatus newStatus = AgentStatus.valueOf(updatedName);
        AgentStatus oldStatus = agent.getStatus();
        if(newStatus == AgentStatus.FROZEN) freezeAgent(agent);
        else if(newStatus == AgentStatus.PURGED) purgeAgent(agent);
        else if(newStatus == AgentStatus.DEAD) killAgent(agent);
        else if(newStatus == AgentStatus.WITHDRAWN) withdrawAgent(agent);
        else if(oldStatus == AgentStatus.FROZEN && newStatus == AgentStatus.ALIVE) thawAgent(agent);
        agentStatusCurrent.setText(updatedName);
        updateAgentListFile();
    }

    private void purgeAgent(Agent agentToPurge) {

    }

    private void killAgent(Agent agentToKill) {

    }

    private void withdrawAgent(Agent agentToWithdraw) {

    }

    private void thawAgent(Agent agentToThaw) {

    }

    public void freezeAgent(Agent agentToFreeze){
        reassignAgentWithTarget(agentToFreeze);
        agentToFreeze.setStatus(AgentStatus.FROZEN);
        sendNotificationEmail(agentToFreeze, getFrozenEmail(agentToFreeze));
    }

    public void setNoTarget(Agent target){
        target.setCurrentTarget(null);
        currentTarget.setText("  ");
        currentTarget.setClickable(false);
    }



    // TODO change back to using GameMethods
    /*private void freezeAgent() {
        GameMethods methods = new GameMethods(agentList);;
        Agent agentWithNewTarget = agentList.getAgentAssignedToKill(agent)
        Log.e("PPP", agentWithNewTarget.getName());
        methods.freezeAgent(agent);
        agentList = methods.getAgentList();
        currentTarget.setText(agent.getCurrentTarget().getName());
        Log.e("PPP", agent.getName());
        sendFrozenEmail(agent);
        sendTargetEmail(agentWithNewTarget);
    }*/

    // // // // // // // // // // //
    //  status notification email //
    // // // // // // // // // // //

    private Email getFrozenEmail(Agent frozenAgent) {
        return new Email(frozenAgent.getEmail(), "Frozen Notification",
                writeFrozenEmail(frozenAgent));
    }

    private String writeFrozenEmail(Agent agent){
        Log.e("OOO", agent.getEmail());
        Log.e("OOO", agent.getCurrentTargetEmail());
        String salutation = "Dear Agent " + agent.getName() + ",\n\n";
        String body = "You have been frozen. You have no target and no one is targeting you. \n\n";
        String signoff = "Happy hunting,\nThe Handler";
        return salutation + body + signoff;
    }

    private void sendNotificationEmail(Agent agent, Email email){
        final Email message = email;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //GMailSender sender = new GMailSender("HendrixAssassinsApp", "AssassinsTest1");
                    // TODO uncomment this to send emails again:
                    GMailSender sender = new GMailSender();
                    sender.sendMail(message);
                } catch (AuthenticationFailedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // // // // // // //
    //  target email  //
    // // // // // // //

    private void sendTargetEmail(Agent killer){
        final Email message = getTargetEmail(killer);
        Log.e("QQQ", message.getBody());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //GMailSender sender = new GMailSender("HendrixAssassinsApp", "AssassinsTest1");
                    // TODO uncomment this to send emails again:
                    GMailSender sender = new GMailSender();
                    sender.sendMail(message);
                } catch (AuthenticationFailedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Email getTargetEmail(Agent target){
        return new Email(target.getEmail(), "Target Assignment",
                writeTargetEmail(target));
    }

    private String writeTargetEmail(Agent agent){
        Log.e("OOO", agent.getEmail());
        Log.e("OOO", agent.getCurrentTargetEmail());
        String salutation = "Dear Agent " + agent.getName() + ",\n\n";
        String body = "Your target is " + agent.getCurrentTarget().getName() +
                ". Their email is " + agent.getCurrentTargetEmail() + "\n\n";
        String signoff = "Happy hunting,\nThe Handler";
        return salutation + body + signoff;
    }


}
