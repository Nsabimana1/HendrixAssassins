package com.example.hendrixassassins;

import com.example.hendrixassassins.agent.Agent;
import com.example.hendrixassassins.agent.AgentList;
import com.example.hendrixassassins.agent.AgentStatus;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class AgentListUnitTest {

    @Test
    public void AgentListAdding_isCorrect() {
        AgentList agentList = new AgentList();
        assertEquals(new ArrayList<>(), agentList.getAllAgents());
        agentList.addAgent(new Agent("test@hendrix.edu", "test"));
        agentList.addAgent(new Agent("atest1@hendrix.edu", "atest1"));
        agentList.addAgent(new Agent("etest2@hendrix.edu", "etest2"));
        agentList.addAgent(new Agent("ztest3@hendrix.edu", "ztest3"));
        agentList.addAgent(new Agent("otest4@hendrix.edu", "otest4"));
        agentList.addAgent(new Agent("ctest5@hendrix.edu", "ctest5"));
        assertEquals(6 , agentList.size());
    }

    @Test
    public void AgentListEmailSort_isCorrect() {
        AgentList agentList = new AgentList();
        agentList.addAgent(new Agent("test@hendrix.edu", "test"));
        agentList.addAgent(new Agent("atest1@hendrix.edu", "atest1"));
        agentList.addAgent(new Agent("etest2@hendrix.edu", "etest2"));
        agentList.addAgent(new Agent("ztest3@hendrix.edu", "ztest3"));
        agentList.addAgent(new Agent("otest4@hendrix.edu", "otest4"));
        agentList.addAgent(new Agent("ctest5@hendrix.edu", "ctest5"));
        agentList.sortEmailsAlphabetically();
        assertEquals("atest1@hendrix.edu", agentList.getAllAgents().get(0).getEmail());
        assertEquals("ztest3@hendrix.edu", agentList.getAllAgents().get(agentList.size() - 1).getEmail());
        agentList.reverse();
        assertEquals("ztest3@hendrix.edu", agentList.getAllAgents().get(0).getEmail());
        assertEquals("atest1@hendrix.edu", agentList.getAllAgents().get(agentList.size() - 1).getEmail());
    }

    @Test
    public void AgentListGetEmails_isCorrect() {
        AgentList agentList = new AgentList();
        agentList.addAgent(new Agent("test@hendrix.edu", "test"));
        agentList.addAgent(new Agent("atest1@hendrix.edu", "atest1"));
        agentList.addAgent(new Agent("etest2@hendrix.edu", "etest2"));
        agentList.addAgent(new Agent("ztest3@hendrix.edu", "ztest3"));
        agentList.addAgent(new Agent("otest4@hendrix.edu", "otest4"));
        agentList.addAgent(new Agent("ctest5@hendrix.edu", "ctest5"));
        agentList.sortEmailsAlphabetically();
        ArrayList<String> emails = agentList.getAgentEmails();
        assertEquals(6, emails.size());
        assertEquals("atest1@hendrix.edu", emails.get(0));
        assertEquals("ztest3@hendrix.edu", emails.get(emails.size() - 1));
    }

    @Test
    public void AgentListSortByStatus1_isCorrect() {
        AgentList agentList = new AgentList();
        agentList.addAgent(new Agent("test@hendrix.edu", "test"));
        agentList.addAgent(new Agent("atest1@hendrix.edu", "atest1"));
        agentList.addAgent(new Agent("etest2@hendrix.edu", "etest2"));
        agentList.addAgent(new Agent("ztest3@hendrix.edu", "ztest3"));
        agentList.addAgent(new Agent("otest4@hendrix.edu", "otest4"));
        agentList.addAgent(new Agent("ctest5@hendrix.edu", "ctest5"));
        AgentList agentList2 = agentList.filterAgentsByStatus(AgentStatus.ALIVE);
        agentList2.sortEmailsAlphabetically();
        ArrayList<String> emails = agentList2.getAgentEmails();
        assertEquals(6, emails.size());
        assertEquals("atest1@hendrix.edu", emails.get(0));
        assertEquals("ztest3@hendrix.edu", emails.get(emails.size() - 1));
        AgentList agentList3 = agentList.filterAgentsByStatus(AgentStatus.DEAD);
        assertEquals(0, agentList3.size());
    }

    @Test
    public void AgentListSortByStatus2_isCorrect() {
        AgentList agentList = new AgentList();
        agentList.addAgent(new Agent("test@hendrix.edu", "test"));
        agentList.addAgent(new Agent("atest1@hendrix.edu", "atest1"));
        agentList.addAgent(new Agent("etest2@hendrix.edu", "etest2"));
        agentList.addAgent(new Agent("ztest3@hendrix.edu", "ztest3"));
        agentList.addAgent(new Agent("otest4@hendrix.edu", "otest4"));
        agentList.addAgent(new Agent("ctest5@hendrix.edu", "ctest5"));
        agentList.getAllAgents().get(0).setStatus(AgentStatus.PURGED);
        agentList.getAllAgents().get(2).setStatus(AgentStatus.PURGED);
        agentList.getAllAgents().get(1).setStatus(AgentStatus.DEAD);
        AgentList agentList2 = agentList.filterAgentsByStatus(AgentStatus.ALIVE);
        assertEquals(3, agentList2.size());
        AgentList agentList3 = agentList.filterAgentsByStatus(AgentStatus.DEAD);
        assertEquals(1, agentList3.size());
        ArrayList<AgentStatus> statuses = new ArrayList<>();
        statuses.add(AgentStatus.ALIVE);
        statuses.add(AgentStatus.DEAD);
        AgentList agentList4 = agentList.filterAgentsByStatusList(statuses);
        assertEquals(4, agentList4.size());
    }

    @Test
    public void AgentListNameSort_isCorrect() {
        AgentList agentList = new AgentList();
        agentList.addAgent(new Agent("test@hendrix.edu", "test"));
        agentList.addAgent(new Agent("atest1@hendrix.edu", "atest1"));
        agentList.addAgent(new Agent("etest2@hendrix.edu", "etest2"));
        agentList.addAgent(new Agent("ztest3@hendrix.edu", "ztest3"));
        agentList.addAgent(new Agent("otest4@hendrix.edu", "otest4"));
        agentList.addAgent(new Agent("ctest5@hendrix.edu", "ctest5"));
        agentList.sortNamesAlphabetically();
        assertEquals("atest1", agentList.getAllAgents().get(0).getName());
        assertEquals("ztest3", agentList.getAllAgents().get(agentList.size() - 1).getName());
        agentList.reverse();
        assertEquals("ztest3", agentList.getAllAgents().get(0).getName());
        assertEquals("atest1", agentList.getAllAgents().get(agentList.size() - 1).getName());
    }

    @Test
    public void AgentListDrawNumberSort_isCorrect() {
        AgentList agentList = new AgentList();
        agentList.addAgent(new Agent("test@hendrix.edu", "test"));
        agentList.addAgent(new Agent("atest1@hendrix.edu", "atest1"));
        agentList.addAgent(new Agent("etest2@hendrix.edu", "etest2"));
        agentList.addAgent(new Agent("ztest3@hendrix.edu", "ztest3"));
        agentList.addAgent(new Agent("otest4@hendrix.edu", "otest4"));
        agentList.addAgent(new Agent("ctest5@hendrix.edu", "ctest5"));
        agentList.getAllAgents().get(0).setDrawNumber(20);
        agentList.getAllAgents().get(2).setDrawNumber(10);
        agentList.getAllAgents().get(1).setDrawNumber(100);
        agentList.getAllAgents().get(4).setDrawNumber(2);
        agentList.getAllAgents().get(5).setDrawNumber(9);
        agentList.sortByDrawNumber();
        assertEquals("atest1", agentList.getAllAgents().get(0).getName());
        assertEquals(100, agentList.getAllAgents().get(0).getDrawNumber());
        assertEquals("ztest3", agentList.getAllAgents().get(agentList.size() - 1).getName());
        assertEquals(-1, agentList.getAllAgents().get(agentList.size() - 1).getDrawNumber());
        agentList.reverse();
        assertEquals("ztest3", agentList.getAllAgents().get(0).getName());
        assertEquals("atest1", agentList.getAllAgents().get(agentList.size() - 1).getName());
    }

    @Test
    public void AgentListPersonalKillsSort_isCorrect() {
        AgentList agentList = new AgentList();
        agentList.addAgent(new Agent("test@hendrix.edu", "test"));
        agentList.addAgent(new Agent("atest1@hendrix.edu", "atest1"));
        agentList.addAgent(new Agent("etest2@hendrix.edu", "etest2"));
        agentList.addAgent(new Agent("ztest3@hendrix.edu", "ztest3"));
        agentList.addAgent(new Agent("otest4@hendrix.edu", "otest4"));
        agentList.addAgent(new Agent("ctest5@hendrix.edu", "ctest5"));
        agentList.getAllAgents().get(0).setPersonalKills(20);
        agentList.getAllAgents().get(2).setPersonalKills(10);
        agentList.getAllAgents().get(1).setPersonalKills(100);
        agentList.getAllAgents().get(4).setPersonalKills(2);
        agentList.getAllAgents().get(5).setPersonalKills(9);
        agentList.sortByPersonalKills();
        assertEquals("atest1", agentList.getAllAgents().get(0).getName());
        assertEquals(100, agentList.getAllAgents().get(0).getPersonalKills());
        assertEquals("ztest3", agentList.getAllAgents().get(agentList.size() - 1).getName());
        assertEquals(0, agentList.getAllAgents().get(agentList.size() - 1).getPersonalKills());
        agentList.reverse();
        assertEquals("ztest3", agentList.getAllAgents().get(0).getName());
        assertEquals("atest1", agentList.getAllAgents().get(agentList.size() - 1).getName());
    }

    @Test
    public void AgentListPointsTotalSort_isCorrect() {
        AgentList agentList = new AgentList();
        agentList.addAgent(new Agent("test@hendrix.edu", "test"));
        agentList.addAgent(new Agent("atest1@hendrix.edu", "atest1"));
        agentList.addAgent(new Agent("etest2@hendrix.edu", "etest2"));
        agentList.addAgent(new Agent("ztest3@hendrix.edu", "ztest3"));
        agentList.addAgent(new Agent("otest4@hendrix.edu", "otest4"));
        agentList.addAgent(new Agent("ctest5@hendrix.edu", "ctest5"));
        agentList.getAllAgents().get(0).setPointsTotal(20);
        agentList.getAllAgents().get(2).setPointsTotal(10);
        agentList.getAllAgents().get(1).setPointsTotal(100);
        agentList.getAllAgents().get(4).setPointsTotal(2);
        agentList.getAllAgents().get(5).setPointsTotal(9);
        agentList.sortByPointsTotal();
        assertEquals("atest1", agentList.getAllAgents().get(0).getName());
        assertEquals(100, agentList.getAllAgents().get(0).getPointsTotal());
        assertEquals("ztest3", agentList.getAllAgents().get(agentList.size() - 1).getName());
        assertEquals(0, agentList.getAllAgents().get(agentList.size() - 1).getPointsTotal());
        agentList.reverse();
        assertEquals("ztest3", agentList.getAllAgents().get(0).getName());
        assertEquals("atest1", agentList.getAllAgents().get(agentList.size() - 1).getName());
    }

    @Test
    public void AgentListGetAgentFromEmail_isCorrect(){
        AgentList agentList = new AgentList();
        agentList.addAgent(new Agent("test@hendrix.edu", "test"));
        agentList.addAgent(new Agent("atest1@hendrix.edu", "atest1"));
        agentList.addAgent(new Agent("etest2@hendrix.edu", "etest2"));
        agentList.addAgent(new Agent("ztest3@hendrix.edu", "ztest3"));
        agentList.addAgent(new Agent("otest4@hendrix.edu", "otest4"));
        agentList.addAgent(new Agent("ctest5@hendrix.edu", "ctest5"));
        assertEquals("ctest5", agentList.getAgentWithEmailAddress("ctest5@hendrix.edu").getName());
    }


}
