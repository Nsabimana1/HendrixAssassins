package com.example.hendrixassassins.agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;

public class GameMethods {

    AgentList agentList;

    public GameMethods(AgentList agentList){
        this.agentList = agentList;
    }

    private void assignDrawNumbersHelper(int agentIndex, int targetIndex, ArrayList<Agent> agents){
        agents.get(agentIndex).setDrawNumber(agentIndex);
        agents.get(agentIndex).setCurrentTarget(agents.get(targetIndex));
    }

    public void assignDrawNumbers(){
        ArrayList<Agent> agents = agentList.getAllAgents();
        Collections.shuffle(agents);
        for(int i = 0; i < agents.size() - 1; i++){
            assignDrawNumbersHelper(i, i + 1, agents);
        }
        assignDrawNumbersHelper(agents.size(), 0, agents);
    }

    public AgentList getAgentList(){
        return agentList;
    }

    public void purge(GregorianCalendar purgeTime){
        GregorianCalendar now = new GregorianCalendar();
        if(purgeTime.compareTo(now) <= 0 ){
            for (Agent agent: agentList.getAllAgents()){
                if(agent.getPointsTotal() == 0){
                    agent.setStatus(AgentStatus.PURGED);
                    reassignAgentWithTarget(agent);
                }
            }
        }
    }

    public void reassignAgentWithTarget(Agent target){
        agentList.getAgentAssignedToKill(target).setCurrentTarget(target.getCurrentTarget());
        target.setCurrentTarget(null);
    }

    public void freezeAgents(AgentList agentsToFreeze){
        for(Agent agent: agentsToFreeze.getAllAgents()){
            reassignAgentWithTarget(agent);
            agent.setStatus(AgentStatus.FROZEN);
        }
    }

    public void thawAgent(Agent frozenAgent){
        AgentList livingAgents = agentList.filterAgentsByStatus(AgentStatus.ALIVE);
        livingAgents.sortByDrawNumber();
        if(frozenAgent.getDrawNumber() == 0){
            thawAgentHelper(frozenAgent, livingAgents.size(), livingAgents);
        }
        else {
            int index = 0;
            while (livingAgents.getAllAgents().get(index).getDrawNumber() < frozenAgent.getDrawNumber())
                index++;
            thawAgentHelper(frozenAgent, index, livingAgents);
        }
    }

    private void thawAgentHelper(Agent frozenAgent, int index, AgentList livingAgents){
        frozenAgent.setCurrentTarget(livingAgents.getAllAgents().get(index).getCurrentTarget());
        livingAgents.getAllAgents().get(index).setCurrentTarget(frozenAgent);
        frozenAgent.setStatus(AgentStatus.ALIVE);
    }

}
