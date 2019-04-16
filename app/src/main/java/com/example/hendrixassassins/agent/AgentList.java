package com.example.hendrixassassins.agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

    public void sortByPersonalKills(){
        Collections.sort(agents, new Comparator<Agent>() {
            @Override
            public int compare(Agent o1, Agent o2) {
                return o1.getPersonalKills() - o2.getPersonalKills();
            }
        });
    }

    public void sortByPointsTotal(){
        Collections.sort(agents, new Comparator<Agent>() {
            @Override
            public int compare(Agent o1, Agent o2) {
                return o1.getPointsTotal() - o2.getPointsTotal();
            }
        });
    }

    public void sortNamesAlphabetically(){
        Collections.sort(agents, new Comparator<Agent>() {
            @Override
            public int compare(final Agent o1, final Agent o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    public void sortEmailsAlphabetically(){
        Collections.sort(agents, new Comparator<Agent>() {
            @Override
            public int compare(final Agent o1, final Agent o2) {
                return o1.getEmail().compareTo(o2.getEmail());
            }
        });
    }

    public void reverse(){
        Collections.reverse(agents);
    }


    //email, subject,content for emails

}
