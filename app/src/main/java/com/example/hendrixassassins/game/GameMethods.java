package com.example.hendrixassassins.game;

import com.example.hendrixassassins.agent.Agent;
import com.example.hendrixassassins.agent.AgentList;
import com.example.hendrixassassins.agent.AgentStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;

public class GameMethods {

    AgentList agentList;

    public GameMethods(AgentList agentList){
        this.agentList = agentList;
    }

    public AgentList getAgentList(){
        return agentList;
    }

    private void assignDrawNumbersHelper(int agentIndex, int targetIndex, ArrayList<Agent> agents){
        agents.get(agentIndex).setDrawNumber(agentIndex);
        agents.get(agentIndex).setCurrentTarget(agents.get(targetIndex));
    }

    private void assignDrawNumbers(){
        ArrayList<Agent> agents = agentList.getAllAgents();
        Collections.shuffle(agents);
        for(int i = 0; i < agents.size() - 1; i++) assignDrawNumbersHelper(i, i + 1, agents);
        assignDrawNumbersHelper(agents.size() - 1, 0, agents);
    }

    public void initializeTargets(){
        assignDrawNumbers();
        agentList.sortByDrawNumber();
        ArrayList<Agent> agents = agentList.getAllAgents();
        for(int i = 0; i < agents.size() - 1; i++) agents.get(i).setCurrentTarget(agents.get(i + 1));
        agents.get(agents.size() - 1).setCurrentTarget(agents.get(0));
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

    private void reassignAgentWithTarget(Agent target){
        agentList.getAgentAssignedToKill(target).setCurrentTarget(target.getCurrentTarget());
        target.setCurrentTarget(null);
    }

    public void freezeAgent(Agent agentToFreeze){
        reassignAgentWithTarget(agentToFreeze);
        agentToFreeze.setStatus(AgentStatus.FROZEN);
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
