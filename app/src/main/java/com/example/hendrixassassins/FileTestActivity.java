package com.example.hendrixassassins;

import android.content.Context;
import android.util.Log;

import com.example.hendrixassassins.agent.Agent;
import com.example.hendrixassassins.agent.AgentFileHelper;
import com.example.hendrixassassins.agent.AgentList;
import com.example.hendrixassassins.agent.AgentStatus;

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
        AgentList agentList = fileHelper.readFromFile("testFile.csv", context);
        for(Agent agent: agentList.getAllAgents()){
            Log.e("AGENT: ", String.valueOf(agent.getKillList().size()));
        }
    }

    private void test(){
        AgentList testList = new AgentList();
        String[] abc = new String[]{"a", "b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        for (String letter : abc) {
            Agent target = new Agent(letter , letter);
            testList.addAgent(new Agent(letter + "person@hendrix.edu", letter + "person"));
            testList.getAllAgents().get(testList.size() - 1).setCurrentTarget(target);
            if(letter.equals("a") || letter.equals("b") || letter.equals("c")) {
                testList.getAllAgents().get(testList.size() - 1).setStatus(AgentStatus.DEAD);
            }
            testList.getAllAgents().get(testList.size() - 1).setPointsTotal((int) Math.ceil(Math.random() * 100));
            for(Agent agent: testList.getAllAgents()){
                testList.getAllAgents().get(testList.size() - 1).addToKillList(agent);
            }
            Log.e("AAA",testList.getAllAgents().get(testList.size() - 1).getTableRow());
        }
        fileHelper.writeToFile("testFile_Agents.csv", testList, context);
    }


}
