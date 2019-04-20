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

    public void reassignTargets(AgentList oldTargets){
        for (Agent target: oldTargets.getAllAgents()){
            reassignAgentWithTarget(target);
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

    public void thawAgents(AgentList frozenAgents){
        for(Agent agent: frozenAgents.getAllAgents()){
            thawAgent(agent);
        }
    }

    public void thawAgent(Agent frozenAgent){
        AgentList livingAgents = agentList.filterAgentsByStatus(AgentStatus.ALIVE);
        livingAgents.sortByDrawNumber();
        // TODO loop through draw numbers to find the one right after frozenAgents' number
        // TODO reassign Targets
    }

}
