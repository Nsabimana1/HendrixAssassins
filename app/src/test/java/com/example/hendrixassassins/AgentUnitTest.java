package com.example.hendrixassassins;

import com.example.hendrixassassins.backend.Agent;
import com.example.hendrixassassins.backend.AgentStatus;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class AgentUnitTest {
    @Test
    public void agentCreation_isCorrect() {
        Agent agent = new Agent("test@hendrix.edu", 0);
        assertEquals(0, agent.getDrawNumber());
        assertEquals("test@hendrix.edu", agent.getEmail());
        assertNull(agent.getCurrentTarget());
        assertNull(agent.getDeathTime());
        assertEquals(AgentStatus.ALIVE, agent.getStatus());
        assertEquals(0, agent.getPersonalKills());
        assertEquals(0, agent.getPointsTotal());
        assertEquals(new ArrayList<Agent>(), agent.getKillList());
    }

    @Test
    public void setStatus_isCorrect() {
        Agent agent = new Agent("test@hendrix.edu", 0);
        agent.setStatus(AgentStatus.WITHDRAWN);
        assertEquals(AgentStatus.WITHDRAWN, agent.getStatus() );
        agent.setStatus(AgentStatus.DEAD);
        assertEquals(AgentStatus.DEAD , agent.getStatus());
        agent.setStatus(AgentStatus.FROZEN);
        assertEquals(AgentStatus.FROZEN, agent.getStatus() );
        agent.setStatus(AgentStatus.PURGED);
        assertEquals(AgentStatus.PURGED, agent.getStatus() );
        agent.setStatus(AgentStatus.ALIVE);
        assertEquals(AgentStatus.ALIVE, agent.getStatus() );
    }

    @Test
    public void killList_isCorrect() {
        Agent agent = new Agent("test@hendrix.edu", 0);
        Agent agent1 = new Agent("test1@hendrix.edu", 1);
        Agent agent2 = new Agent("test2@hendrix.edu", 2);
        Agent agent3 = new Agent("test3@hendrix.edu", 3);
        Agent agent4 = new Agent("test4@hendrix.edu", 4);
        Agent agent5 = new Agent("test5@hendrix.edu", 5);
        ArrayList<Agent> killList = new ArrayList<>();
        killList.add(agent1);
        killList.add(agent2);
        agent.addToKillList(killList);
        assertEquals(agent.getKillList(), killList);
        assertEquals("test1@hendrix.edu", agent.getKillList().get(0).getEmail());
        ArrayList<Agent> killList2 = new ArrayList<>();
        killList2.add(agent3);
        killList2.add(agent4);
        killList2.add(agent5);
        agent.addToKillList(killList2);
        assertEquals(killList.size() + killList2.size(), agent.getKillList().size());
        assertEquals(5, agent.getKillList().get(agent.getKillList().size() - 1).getDrawNumber());
    }

    @Test
    public void personalKills_isCorrect(){
        Agent agent = new Agent("test@hendrix.edu", 0);
        assertEquals(0, agent.getPersonalKills());
        agent.setPersonalKills(2);
        assertEquals(2, agent.getPersonalKills());
        agent.incrementPersonalKills();
        assertEquals(3, agent.getPersonalKills());
    }

    @Test
    public void pointTotal_isCorrect(){
        Agent agent = new Agent("test@hendrix.edu", 0);
        assertEquals(0, agent.getPointsTotal());
        agent.setPointsTotal(2);
        assertEquals(2, agent.getPointsTotal());
        agent.addToPointsTotal(3);
        assertEquals(5, agent.getPointsTotal());
    }

    @Test
    public void deathTime_isCorrect(){
        Agent agent = new Agent("test@hendrix.edu", 0);
        assertNull(agent.getDeathTime());
        Date now = new Date();
        now.getTime();
        agent.setDeathTime(now);
        assertEquals(now, agent.getDeathTime());
    }

    public void target_isCorrect(){
        Agent agent = new Agent("test@hendrix.edu", 0);
        assertNull(agent.getCurrentTarget());
        agent.setCurrentTarget("test1@hendrix.edu");
        assertEquals("test1@hendrix.edu", agent.getCurrentTarget());
    }

    @Test
    public void tableRow_isCorrect(){
        Agent agent = new Agent("test@hendrix.edu", 0);
        Agent agent1 = new Agent("test1@hendrix.edu", 1);
        Agent agent2 = new Agent("test2@hendrix.edu", 2);
        ArrayList<Agent> killList = new ArrayList<>();
        killList.add(agent1);
        killList.add(agent2);
        agent.setCurrentTarget("test3@hendrix.edu");
        agent.setPersonalKills(5);
        agent.setPointsTotal(10);
        agent.addToKillList(killList);
        String expected = "0,test@hendrix.edu,ALIVE,NA,test3@hendrix.edu,5,10,test1@hendrix.edu-test2@hendrix.edu";
        assertEquals(expected, agent.getTableRow());
        agent.setStatus(AgentStatus.DEAD);
        Date now = new Date();
        now.getTime();
        agent.setDeathTime(now);
        expected = "0,test@hendrix.edu,DEAD,"+now.toString()+",test3@hendrix.edu,5,10,test1@hendrix.edu-test2@hendrix.edu";
        assertEquals(expected, agent.getTableRow());
    }

}