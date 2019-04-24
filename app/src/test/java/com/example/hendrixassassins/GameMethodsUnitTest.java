package com.example.hendrixassassins;

import com.example.hendrixassassins.agent.Agent;
import com.example.hendrixassassins.agent.AgentList;
import com.example.hendrixassassins.agent.AgentStatus;
import com.example.hendrixassassins.game.GameMethods;

import org.junit.Before;
import org.junit.Test;

import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class GameMethodsUnitTest {
    GameMethods gameMethods;

    @Before
    public void setupAgents(){
        AgentList agentList = new AgentList();
        agentList.addAgent(new Agent("test@hendrix.edu", "test"));
        agentList.addAgent(new Agent("atest1@hendrix.edu", "atest1"));
        agentList.addAgent(new Agent("etest2@hendrix.edu", "etest2"));
        agentList.addAgent(new Agent("ztest3@hendrix.edu", "ztest3"));
        agentList.addAgent(new Agent("otest4@hendrix.edu", "otest4"));
        agentList.addAgent(new Agent("ctest5@hendrix.edu", "ctest5"));
        gameMethods = new GameMethods(agentList);
    }

    @Test
    public void initializeTargets_isCorrect() {
        AgentList oldList = new AgentList();
        oldList.getAllAgents().addAll(gameMethods.getAgentList().getAllAgents());
        gameMethods.initializeTargets();
        AgentList newList = gameMethods.getAgentList();
        newList.sortByDrawNumber();
        assertNotEquals(oldList.getAgentEmails(), newList.getAgentEmails());
        assertNotEquals(-1, newList.getAllAgents().get(0));
        assertEquals(newList.getAllAgents().get(1), newList.getAllAgents().get(0).getCurrentTarget());
        assertEquals(newList.getAllAgents().get(0), newList.getAllAgents().get(5).getCurrentTarget());
    }

    @Test
    public void freezeAndThaw_areCorrect(){
        gameMethods.initializeTargets();
        gameMethods.getAgentList().sortByDrawNumber();
        assertEquals(gameMethods.getAgentList().getAllAgents().get(1),
                gameMethods.getAgentList().getAllAgents().get(0).getCurrentTarget());
        assertEquals(AgentStatus.ALIVE, gameMethods.getAgentList().getAllAgents().get(1).getStatus());
        gameMethods.freezeAgent(gameMethods.getAgentList().getAllAgents().get(1));
        assertEquals(AgentStatus.FROZEN, gameMethods.getAgentList().getAllAgents().get(1).getStatus());
        assertEquals(gameMethods.getAgentList().getAllAgents().get(2),
                gameMethods.getAgentList().getAllAgents().get(0).getCurrentTarget());
        gameMethods.thawAgent(gameMethods.getAgentList().getAllAgents().get(1));
        assertEquals(gameMethods.getAgentList().getAllAgents().get(1),
                gameMethods.getAgentList().getAllAgents().get(0).getCurrentTarget());
        assertEquals(AgentStatus.ALIVE, gameMethods.getAgentList().getAllAgents().get(1).getStatus());
    }

    @Test
    public void purge_isCorrect(){
        gameMethods.initializeTargets();
        gameMethods.getAgentList().getAllAgents().get(0).setPointsTotal(1);
        gameMethods.getAgentList().getAllAgents().get(0).setPersonalKills(1);
        GregorianCalendar pastDate = new GregorianCalendar(2012, 0, 1);
        gameMethods.purge(pastDate);
        assertEquals(1, gameMethods.getAgentList().filterAgentsByStatus(AgentStatus.ALIVE).size());
        assertEquals(5, gameMethods.getAgentList().filterAgentsByStatus(AgentStatus.PURGED).size());
    }




}
