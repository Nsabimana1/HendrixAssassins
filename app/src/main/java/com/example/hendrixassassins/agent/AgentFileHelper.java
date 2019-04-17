package com.example.hendrixassassins.agent;

import android.content.Context;

import java.sql.Date;
import java.util.ArrayList;

public class AgentFileHelper {

    private final static int drawNumberIndex = 0, nameIndex = 1, emailIndex = 2, statusIndex = 3,
    deathTimeIndex = 4, currentTargetIndex = 5, personalKillsIndex = 6, pointsTotalIndex = 7,
    killListIndex = 8;

    public AgentFileHelper(){}

    public String getAgentListFileString(AgentList agentList){
        StringBuilder builder = new StringBuilder();
        for(Agent agent: agentList.getAllAgents()){
            builder.append(agent.getTableRow());
        }
        return builder.toString();
    }

    public void writeAgentListToFile(String fileName, AgentList agentList, Context context){
        AgentFileInteraction interaction = new AgentFileInteraction(context);
        interaction.writeAgentFile(getAgentListFileString(agentList), fileName);
    }

    public AgentList getAgentListFromFile(String fileName, Context context){
        AgentFileInteraction interaction = new AgentFileInteraction(context);
        ArrayList<String> file = interaction.readAgentFile(fileName);
        AgentList agentList = setupAgentsFromFile(file);
        return connectAgentObjects(file, agentList);
    }

    private String[] splitLine(String line, String splitChar){
        return line.trim().split(splitChar);
    }

    private Agent setupAgent(String line){
        String[] split = splitLine(line, ",");
        Agent agent = new Agent(split[emailIndex], split[nameIndex]);
        agent.setDrawNumber(Integer.parseInt(split[drawNumberIndex]));
        agent.setStatus(AgentStatus.valueOf(split[statusIndex]));
        agent.setDeathTime(Date.valueOf(split[deathTimeIndex]));
        agent.setPersonalKills(Integer.parseInt(split[personalKillsIndex]));
        agent.setPointsTotal(Integer.parseInt(split[pointsTotalIndex]));
        return agent;
    }

    private AgentList getAgentKillList(String[] killed, AgentList agentList){
        AgentList killedList = new AgentList();
        for(String k: killed){
            killedList.addAgent( agentList.getAgentWithEmailAddress(k));
        }
        return killedList;
    }

    private AgentList setupAgentsFromFile(ArrayList<String> file){
        AgentList agentList = new AgentList();
        for(String line: file){
            agentList.addAgent(setupAgent(line));
        }
        return agentList;
    }

    private AgentList connectAgentObjects(ArrayList<String> file, AgentList agentList){
        for(String line: file){
            String[] split = splitLine(line, ",");
            Agent agent = agentList.getAgentWithEmailAddress(split[emailIndex]);
            agent.setCurrentTarget(agentList.getAgentWithEmailAddress(split[currentTargetIndex]));
            agent.extendKillList(getAgentKillList(splitLine(split[killListIndex], "-"), agentList));
        }
        return agentList;
    }


}
