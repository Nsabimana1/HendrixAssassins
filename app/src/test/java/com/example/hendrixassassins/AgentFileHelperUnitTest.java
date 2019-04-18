package com.example.hendrixassassins;

import com.example.hendrixassassins.agent.Agent;
import com.example.hendrixassassins.agent.AgentFileHelper;
import com.example.hendrixassassins.agent.AgentList;
import com.example.hendrixassassins.agent.AgentStatus;

import org.junit.Test;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

public class AgentFileHelperUnitTest {
    @Test
    public void fileTranslation_isCorrect(){
        ArrayList<String> file = new ArrayList<>();
        GregorianCalendar date = new GregorianCalendar();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strDate = dateFormat.format(date.getTime());
        file.add("0,zero,zero@hendrix.edu,DEAD,"+
                strDate+ ",NA,0,0,NA\n");
        file.add("1,one,one@hendrix.edu,ALIVE,NA,four@hendrix.edu,1,1,zero@hendrix.edu\n");
        file.add("2,two,two@hendrix.edu,DEAD,"+strDate+",NA,1,1,three@hendrix.edu\n");
        file.add("3,three,three@hendrix.edu,DEAD,"+strDate+",NA,0,0,NA\n");
        file.add("4,four,four@hendrix.edu,ALIVE,NA,one@hendrix.edu,1,2,three@hendrix.edu:two@hendrix.edu\n");
        AgentFileHelper fileHelper = new AgentFileHelper();
        AgentList agentList = fileHelper.testFileReading(file);
        assertEquals(5, agentList.size());
        assertEquals("zero@hendrix.edu", agentList.getAgentEmails().get(0));
        assertEquals(AgentStatus.DEAD, agentList.getAllAgents().get(0).getStatus());
        assertEquals(1, agentList.getAllAgents().get(1).getDrawNumber());
        assertEquals("two", agentList.getAllAgents().get(2).getName());
        assertEquals(strDate, agentList.getAllAgents().get(3).getDeathTimeString());
        assertEquals("NA", agentList.getAllAgents().get(4).getDeathTimeString());
        assertEquals(1, agentList.getAllAgents().get(4).getPersonalKills());
        assertEquals(2, agentList.getAllAgents().get(4).getPointsTotal());
        ArrayList<String> killList = new ArrayList<>();
        killList.add("three@hendrix.edu");
        killList.add("two@hendrix.edu");
        assertEquals("one@hendrix.edu", agentList.getAllAgents().get(4).getCurrentTargetEmail());
        assertEquals(killList, agentList.getAllAgents().get(4).getKillList().getAgentEmails());
        assertEquals("1,one,one@hendrix.edu,ALIVE,NA,four@hendrix.edu,1,1,zero@hendrix.edu\n",
                agentList.getAllAgents().get(1).getTableRow());
    }
}