package com.example.hendrixassassins;

import android.util.Log;

import com.example.hendrixassassins.agent.Agent;
import com.example.hendrixassassins.agent.AgentList;
import com.example.hendrixassassins.agent.AgentStatus;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AgentListUnitTest {

    AgentList agentList;

    @Before
    public void setupAgents(){
        agentList = new AgentList();
        agentList.addAgent(new Agent("test@hendrix.edu", "test"));
        agentList.addAgent(new Agent("atest1@hendrix.edu", "atest1"));
        agentList.addAgent(new Agent("etest2@hendrix.edu", "etest2"));
        agentList.addAgent(new Agent("ztest3@hendrix.edu", "ztest3"));
        agentList.addAgent(new Agent("otest4@hendrix.edu", "otest4"));
        agentList.addAgent(new Agent("ctest5@hendrix.edu", "ctest5"));
    }

    @Test
    public void AgentListAdding_isCorrect() {
        assertEquals(new ArrayList<>(), new AgentList().getAllAgents());
        assertEquals(6 , agentList.size());
        agentList.addAgent(new Agent("test@hendrix.edu", "test"));
        assertEquals(6 , agentList.size());
    }

    @Test
    public void AgentListEmailSort_isCorrect() {
        agentList.sortEmailsAlphabetically();
        assertEquals("atest1@hendrix.edu", agentList.getAllAgents().get(0).getEmail());
        assertEquals("ztest3@hendrix.edu", agentList.getAllAgents().get(agentList.size() - 1).getEmail());
        agentList.reverse();
        assertEquals("ztest3@hendrix.edu", agentList.getAllAgents().get(0).getEmail());
        assertEquals("atest1@hendrix.edu", agentList.getAllAgents().get(agentList.size() - 1).getEmail());
    }

    @Test
    public void AgentListGetEmails_isCorrect() {
        agentList.sortEmailsAlphabetically();
        ArrayList<String> emails = agentList.getAgentEmails();
        assertEquals(6, emails.size());
        assertEquals("atest1@hendrix.edu", emails.get(0));
        assertEquals("ztest3@hendrix.edu", emails.get(emails.size() - 1));
    }

    @Test
    public void AgentListSortByStatus1_isCorrect() {
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
        agentList.sortNamesAlphabetically();
        assertEquals("atest1", agentList.getAllAgents().get(0).getName());
        assertEquals("ztest3", agentList.getAllAgents().get(agentList.size() - 1).getName());
        agentList.reverse();
        assertEquals("ztest3", agentList.getAllAgents().get(0).getName());
        assertEquals("atest1", agentList.getAllAgents().get(agentList.size() - 1).getName());
    }

    @Test
    public void AgentListDrawNumberSort_isCorrect() {
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
        assertEquals("ctest5", agentList.getAgentWithEmailAddress("ctest5@hendrix.edu").getName());
    }

    @Test
    public void AgentListGetAgentAssignedToKill_isCorrect(){
        AgentList testList = new AgentList();
        testList.addAgent(new Agent("person@hendrix.edu", "person"));
        String[] abc = new String[]{"a", "b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        for (String letter : abc) {
            Agent agent = new Agent(letter + "person@hendrix.edu", letter + "person");
            agent.setCurrentTarget(testList.getAllAgents().get(testList.size() - 1));
            testList.addAgent(agent);
        }
        assertEquals("aperson@hendrix.edu", testList.getAgentAssignedToKill(
                testList.getAllAgents().get(0)).getEmail());
        assertEquals("zperson@hendrix.edu", testList.getAgentAssignedToKill(
                testList.getAllAgents().get(testList.size() - 2)).getEmail());
        assertNull(testList.getAgentAssignedToKill(new Agent("nope", "noe")));
    }



}
