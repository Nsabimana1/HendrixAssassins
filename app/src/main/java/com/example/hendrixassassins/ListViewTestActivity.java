package com.example.hendrixassassins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hendrixassassins.agent.Agent;
import com.example.hendrixassassins.agent.AgentFileHelper;
import com.example.hendrixassassins.agent.AgentList;

import java.util.ArrayList;

public class ListViewTestActivity extends AppCompatActivity {
    private AgentList agentList = new AgentList();
    private AgentFileHelper agentFileHelper = new AgentFileHelper();
    private ArrayList<Agent> allAgents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_test_activity);
        agentList = agentFileHelper.getAgentListFromFile("testFile.csv", this);
        allAgents = agentList.getAllAgents();
        createlistViewAdapter();
    }


     private void createlistViewAdapter(){
        ListView listView = findViewById(R.id.customListView_Test);

//        ArrayList<String> testList = new ArrayList<>();
//        String[] abc = new String[]{"a", "b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
//
//        for (String letter : abc) {
//             testList.add(letter + "person@hendrix.edu");
//
//         }


        CustomListViewAdapter adapter = new CustomListViewAdapter<>(this,
                R.layout.test_list_view, allAgents);

        listView.setAdapter(adapter);
        setUpItemClickListener(listView);

    }

    private void setUpItemClickListener(ListView listView){
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
