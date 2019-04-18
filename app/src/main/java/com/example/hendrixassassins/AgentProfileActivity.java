package com.example.hendrixassassins;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.hendrixassassins.agent.Agent;
import com.example.hendrixassassins.agent.AgentFileHelper;
import com.example.hendrixassassins.agent.AgentList;

import java.util.ArrayList;

public class AgentProfileActivity extends AppCompatActivity {
    private String userEmail, userStatus, userScores;
    private AgentList agentList = new AgentList();
    private AgentFileHelper agentFileHelper = new AgentFileHelper();
    private ArrayList<Agent> allAgents = new ArrayList<>();

    private Agent thisAgent;

    private TextView nameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_profile);
        userEmail = getIntent().getStringExtra("clickedUserEmail");
        nameView = findViewById(R.id.agentTest);
        roadAgents();
        setUiComponents();
    }


    private void roadAgents(){
        agentList = agentFileHelper.getAgentListFromFile("testFile.csv", this);
        allAgents = agentList.getAllAgents();
        thisAgent = agentList.getAgentWithEmailAddress(userEmail);
    }

    private void setUiComponents(){
        nameView.setText(userEmail);
    }
}
