package com.example.hendrixassassins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hendrixassassins.UItestcompnents.CustomListViewAdapter;
import com.example.hendrixassassins.agent.Agent;
import com.example.hendrixassassins.agent.AgentFileHelper;
import com.example.hendrixassassins.agent.AgentList;

import java.util.ArrayList;

public class AgentProfileActivity extends AppCompatActivity {
    private TextView agentName, AgentTotalPoints, AgentPersonalKills, agentEmail,
            agentStatus;
    private ImageButton changeAgentName, sendEmailAgent, removeAgent, editPlayerStatus;
    private ListView agentEmailHistory, agentKillHistory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_profile);
        setupIDs();
    }

    private void setupIDs() {
        agentName = findViewById(R.id.agentName);
        AgentTotalPoints = findViewById(R.id.AgentTotalPoints);
        AgentPersonalKills = findViewById(R.id.AgentPersonalKills);
        agentEmail = findViewById(R.id.agentEmail);
        agentStatus = findViewById(R.id.agentStatus);
        changeAgentName = findViewById(R.id.changeAgentName);
        sendEmailAgent = findViewById(R.id.sendEmailAgent);
        removeAgent = findViewById(R.id.removeAgent);
        editPlayerStatus = findViewById(R.id.editPlayerStatus);
        agentEmailHistory = findViewById(R.id.agentEmailHistory);
        agentKillHistory = findViewById(R.id.agentKillHistory);
    }


    public void changeAgentNameButtonListener(View view) {
        //TODO: change agent name
    }

    public void sendEmailAgentButtonListener(View view) {
        gotoSendEmail();
    }

    private void gotoSendEmail() {
        //TODO: need to make sure going to the email intent won't cause crashes
    }

    public void editPlayerStatusButtonListener(View view) {
        //TODO: edit player status
    }

    public void removeAgentButtonListener(View view) {
        //TODO: remove agent from game
    }
}
