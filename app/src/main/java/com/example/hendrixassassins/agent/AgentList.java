package com.example.hendrixassassins.agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AgentList {
    private ArrayList<Agent> agents;

    public AgentList(){
        agents = new ArrayList<>();
    }

    public int size(){
        return agents.size();
    }

    public void addAgent(Agent agent){
        agents.add(agent);
    }

    public ArrayList<Agent> getAllAgents(){
        return agents;
    }

    public ArrayList<String> getAgentEmails(){
        ArrayList<String> emails = new ArrayList<>();
        for(Agent agent: agents){
            emails.add(agent.getEmail());
        }
        return emails;
    }

    public AgentList filterAgentsByStatus(AgentStatus status){
        AgentList agentList = new AgentList();
        for(Agent agent: agents){
            if(agent.getStatus() == status){
                agentList.addAgent(agent);
            }
        }
        return agentList;
    }

    public AgentList filterAgentsByStatusList(ArrayList<AgentStatus> statusList){
        AgentList agentList = new AgentList();
        for(AgentStatus status: statusList){
            for(Agent agent: agents){
                if(agent.getStatus() == status){
                    agentList.addAgent(agent);
                }
            }
        }
        return agentList;
    }

    public void sortByDrawNumber(){
        Collections.sort(agents, new Comparator<Agent>() {
            @Override
            public int compare(Agent o1, Agent o2) {
                return o2.getDrawNumber() - o1.getDrawNumber();
            }
        });
    }

    public void sortByPersonalKills(){
        Collections.sort(agents, new Comparator<Agent>() {
            @Override
            public int compare(Agent o1, Agent o2) {
                return o2.getPersonalKills() - o1.getPersonalKills();
            }
        });
    }

    public void sortByPointsTotal(){
        Collections.sort(agents, new Comparator<Agent>() {
            @Override
            public int compare(Agent o1, Agent o2) {
                return o2.getPointsTotal() - o1.getPointsTotal();
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


    public Agent getAgentWithEmailAddress(String email){
        for(Agent agent: agents){
            if (agent.getEmail().equals(email)){
                return agent;
            }
        }
        return null; // I probably shouldn't do this
    }

    public Agent getAgentAssignedToKill(Agent target){
        for(Agent agent: agents){
            if (agent.getCurrentTarget().equals(target)){
                return agent;
            }
        }
        return null;
    }

}
