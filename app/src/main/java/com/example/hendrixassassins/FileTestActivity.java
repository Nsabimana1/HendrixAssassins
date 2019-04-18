package com.example.hendrixassassins;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.example.hendrixassassins.agent.Agent;
import com.example.hendrixassassins.agent.AgentFileHelper;
import com.example.hendrixassassins.agent.AgentList;

import java.util.ArrayList;

public class FileTestActivity {

    AgentFileHelper fileHelper;

    Context context;

    public FileTestActivity(Context context){
        fileHelper = new AgentFileHelper();
        this.context = context;
        test();
        test2();
    }

    private void test2() {
        AgentList agentList = fileHelper.getAgentListFromFile("testFile.csv", context);
        for(Agent agent: agentList.getAllAgents()){
            Log.e("AGENT: ", agent.getName());
        }
    }

    private void test(){
        AgentList testList = new AgentList();
        String[] abc = new String[]{"a", "b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        for (String letter : abc) {
            testList.addAgent(new Agent(letter + "person@hendrix.edu", letter + "person"));
        }
        fileHelper.writeAgentListToFile("testFile.csv", testList, context);
    }


}