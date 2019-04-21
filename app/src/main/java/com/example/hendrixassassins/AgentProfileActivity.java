package com.example.hendrixassassins;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hendrixassassins.agent.Agent;
import com.example.hendrixassassins.agent.AgentFileHelper;
import com.example.hendrixassassins.agent.AgentList;

import java.util.ArrayList;

public class AgentProfileActivity extends AppCompatActivity {
    private AgentList agentList = new AgentList();
    private AgentFileHelper agentFileHelper = new AgentFileHelper();
    private ArrayList<Agent> allAgents = new ArrayList<>();
    private Agent thisAgent;
    private TextView emailView, statusView, pointsView, nameView;
    private ListView agentHistory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_profile);
        loadAgentProfile();
        setUpTextViews();
        setTextViews();
        buildAgentHistory();
    }

    private void buildAgentHistory(){
        agentHistory = findViewById(R.id.agentHistoryList);
        CustomListViewAdapter adapter = new CustomListViewAdapter<>(this,
                R.layout.test_list_view, allAgents);

        agentHistory.setAdapter(adapter);
    }


    private void loadAgentProfile(){
        String userEmail = getIntent().getStringExtra("clickedUserEmail");
        roadAgents(userEmail);
    }

    private void setUpTextViews(){
        emailView = findViewById(R.id.agentEmail);
        statusView = findViewById(R.id.agentStatus);
        pointsView = findViewById(R.id.agentPoints);
        nameView =  findViewById(R.id.agentName);
    }


    private void roadAgents(String userEmail){
        agentList = agentFileHelper.getAgentListFromFile("testFile.csv", this);
        allAgents = agentList.getAllAgents();
        thisAgent = agentList.getAgentWithEmailAddress(userEmail);
    }

    private void setTextViews(){
        emailView.setText(thisAgent.getEmail());
        statusView.setText(thisAgent.getStatus().toString());
        pointsView.setText(String.valueOf(thisAgent.getPointsTotal()));
        nameView.setText(thisAgent.getName());




    }
}
