package com.example.hendrixassassins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import com.example.hendrixassassins.agent.Agent;
import com.example.hendrixassassins.agent.AgentFileHelper;
import com.example.hendrixassassins.agent.AgentList;

import java.util.ArrayList;

public class ListViewTestActivity extends AppCompatActivity {
    private AgentList agentList = new AgentList();
    private AgentFileHelper agentFileHelper = new AgentFileHelper();
    private ArrayList<Agent> allAgents;
    private Button sort_a_z, sort_z_a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_test_activity);
        agentList = agentFileHelper.getAgentListFromFile("testFile.csv", this);
        allAgents = agentList.getAllAgents();
        createlistViewAdapter();
        setUpSortTestButtons();


        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTest);
        AutoCompleteAgentAdapter<Agent> adapter = new AutoCompleteAgentAdapter<>(this, R.layout.auto_complete_list_test, allAgents);
        autoCompleteTextView.setAdapter(adapter);
    }

    private void setUpSortTestButtons() {
        sort_a_z = findViewById(R.id.sort_a_z);
        sort_a_z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agentList.sortNamesAlphabetically();
                rebuildListView();
            }
        });
        sort_z_a = findViewById(R.id.sort_z_a);
        sort_z_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agentList.reverse();
                rebuildListView();

            }
        });
    }

    private void rebuildListView(){
        allAgents = agentList.getAllAgents();
        createlistViewAdapter();
    }


    private void createlistViewAdapter(){
        ListView listView = findViewById(R.id.customListView_Test);
        NotificationListViewAdapter adapter = new NotificationListViewAdapter<>(this,
                R.layout.test_list_view, allAgents);

        listView.setAdapter(adapter);
        setUpItemClickListener(listView);

    }

    private void setUpItemClickListener(final ListView listView){
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String email = allAgents.get(position).getEmail();
                toAgentProfileActivity(email);

            }

        });
    }

    private void toAgentProfileActivity(String email) {
        Intent forwardIntent = new Intent(ListViewTestActivity.this, AgentProfileActivity.class);
        forwardIntent.putExtra("clickedUserEmail", email);
        startActivity(forwardIntent);
    }
}
