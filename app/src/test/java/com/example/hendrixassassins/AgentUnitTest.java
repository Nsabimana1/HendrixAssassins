package com.example.hendrixassassins;

import com.example.hendrixassassins.agent.Agent;
import com.example.hendrixassassins.agent.AgentList;
import com.example.hendrixassassins.agent.AgentStatus;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class AgentUnitTest {
    @Test
    public void agentCreation_isCorrect() {
        Agent agent = new Agent("test@hendrix.edu", "test");
        assertEquals("NA", agent.getDrawNumberString());
        assertEquals("test@hendrix.edu", agent.getEmail());
        assertEquals("test", agent.getName());
        assertEquals("NA", agent.getCurrentTargetEmail());
        assertNull(agent.getCurrentTarget());
        assertNull(agent.getDeathTime());
        assertEquals(AgentStatus.ALIVE, agent.getStatus());
        assertEquals(0, agent.getPersonalKills());
        assertEquals(0, agent.getPointsTotal());
        assertEquals(new AgentList().size(), agent.getKillList().size());
    }

    @Test
    public void setStatus_isCorrect() {
        Agent agent = new Agent("test@hendrix.edu", "test");
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
        Agent agent = new Agent("test@hendrix.edu", "test");
        Agent agent1 = new Agent("test1@hendrix.edu", "test1");
        Agent agent2 = new Agent("test2@hendrix.edu", "test2");
        Agent agent3 = new Agent("test3@hendrix.edu", "test3");
        Agent agent4 = new Agent("test4@hendrix.edu", "test4");
        Agent agent5 = new Agent("test5@hendrix.edu", "test5");
        AgentList killList = new AgentList();
        killList.addAgent(agent1);
        killList.addAgent(agent2);
        agent.extendKillList(killList);
        assertEquals(agent.getKillList().size(), killList.size());
        assertEquals("test1@hendrix.edu", agent.getKillList().getAllAgents().get(0).getEmail());
        AgentList killList2 = new AgentList();
        killList2.addAgent(agent3);
        killList2.addAgent(agent4);
        killList2.addAgent(agent5);
        agent.extendKillList(killList2);
        assertEquals(killList.size() + killList2.size(), agent.getKillList().size());
        assertEquals("test5", agent.getKillList().getAllAgents().get(agent.getKillList().size() - 1).getName());
    }

    @Test
    public void personalKills_isCorrect(){
        Agent agent = new Agent("test@hendrix.edu", "test");
        assertEquals(0, agent.getPersonalKills());
        agent.setPersonalKills(2);
        assertEquals(2, agent.getPersonalKills());
        agent.incrementPersonalKills();
        assertEquals(3, agent.getPersonalKills());
    }

    @Test
    public void pointTotal_isCorrect(){
        Agent agent = new Agent("test@hendrix.edu", "test");
        assertEquals(0, agent.getPointsTotal());
        agent.setPointsTotal(2);
        assertEquals(2, agent.getPointsTotal());
        agent.addToPointsTotal(3);
        assertEquals(5, agent.getPointsTotal());
    }

    @Test
    public void deathTime_isCorrect(){
        Agent agent = new Agent("test@hendrix.edu", "test");
        assertNull(agent.getDeathTime());
        GregorianCalendar now = new GregorianCalendar();
        now.getTime();
        agent.setDeathTime(now);
        assertEquals(now, agent.getDeathTime());
    }

    public void target_isCorrect(){
        Agent agent = new Agent("test@hendrix.edu", "test");
        Agent target = new Agent("test1@hendrix.edu", "test1");
        assertNull(agent.getCurrentTarget());
        agent.setCurrentTarget(target);
        assertEquals("test1@hendrix.edu", agent.getCurrentTargetEmail());
    }

    @Test
    public void tableRow_isCorrect(){
        Agent agent = new Agent("test@hendrix.edu", "test");
        Agent agent1 = new Agent("test1@hendrix.edu", "test");
        Agent agent2 = new Agent("test2@hendrix.edu", "test");
        Agent agent3 = new Agent("test3@hendrix.edu", "test");
        AgentList killList = new AgentList();
        killList.addAgent(agent1);
        killList.addAgent(agent2);
        agent.setDrawNumber(0);
        agent.setCurrentTarget(agent3);
        agent.setPersonalKills(5);
        agent.setPointsTotal(10);
        agent.extendKillList(killList);
        String expected = "0,test,test@hendrix.edu,ALIVE,NA,test3@hendrix.edu,5,10,test1@hendrix.edu:test2@hendrix.edu\n";
        assertEquals(expected, agent.getTableRow());
        agent.setStatus(AgentStatus.DEAD);
        GregorianCalendar now = new GregorianCalendar();
        now.getTime();
        agent.setDeathTime(now);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strDate = dateFormat.format(now.getTime());
        expected = "0,test,test@hendrix.edu,DEAD,"+strDate+",test3@hendrix.edu,5,10,test1@hendrix.edu:test2@hendrix.edu\n";
        assertEquals(expected, agent.getTableRow());
    }

}