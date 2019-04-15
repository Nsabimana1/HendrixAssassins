package com.example.hendrixassassins.agent;

import java.util.ArrayList;

public class AgentList {
    private ArrayList<Agent> agents;

    public AgentList(){
        agents = new ArrayList<>();
    }

    public void addAgent(Agent agent){
        agents.add(agent);
    }

    public ArrayList<Agent> getAllAgents(){
        return agents;
    }

    public ArrayList<String> getAgentEmails(ArrayList<Agent> agentList){
        ArrayList<String> emails = new ArrayList<>();
        for(Agent agent: agentList){
            emails.add(agent.getEmail());
        }
        return emails;
    }

    public ArrayList<Agent> getAgentsWithStatus(AgentStatus status){
        ArrayList<Agent> agentList = new ArrayList<>();
        for(Agent agent: agents){
            if(agent.getStatus() == status){
                agentList.add(agent);
            }
        }
        return agentList;
    }

    public ArrayList<Agent> getAgentsInStatusList(ArrayList<AgentStatus> statusList){
        ArrayList<Agent> agentList = new ArrayList<>();
        for(AgentStatus status: statusList){
            agentList.addAll(getAgentsWithStatus(status));
        }
        return agentList;
    }

    // sort on amount of personal kills

    // name alphebetically both ways

    // score both ways

    //email, subject,content for emails

}
