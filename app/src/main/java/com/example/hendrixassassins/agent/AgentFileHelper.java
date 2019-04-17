package com.example.hendrixassassins.agent;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.util.ArrayList;

public class AgentFileHelper {

    private Context context;
    private final static int drawNumberIndex = 0, nameIndex = 1, emailIndex = 2, statusIndex = 3,
    deathTimeIndex = 4, currentTargetIndex = 5, personalKillsIndex = 6, pointsTotalIndex = 7,
    killListIndex = 8;

    public AgentFileHelper(Context context){
        this.context = context;
    }

    public String getAgentListFileString(AgentList agentList){
        StringBuilder builder = new StringBuilder();
        for(Agent agent: agentList.getAllAgents()){
            builder.append(agent.getTableRow());
        }
        return builder.toString();
    }

    // https://stackoverflow.com/questions/14376807/how-to-read-write-string-from-a-file-in-android
    public void writeAgentListToFile(AgentList agentList, String fileName){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(getAgentListFileString(agentList));
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    // https://stackoverflow.com/questions/14376807/how-to-read-write-string-from-a-file-in-android
    private ArrayList<String> readAgentFile(String fileName){
        ArrayList<String> lines = new ArrayList<>();
        try {
            InputStream inputStream = context.openFileInput(fileName);
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    lines.add(receiveString);
                }
                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("Exception", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Exception", "Can not read file: " + e.toString());
        }
        return lines;
    }

    private String[] splitLine(String line, String splitChar){
        return line.trim().split(splitChar);
    }

    private Agent setupAgent(String line){
        String[] split = splitLine(line, ",");
        Agent agent = new Agent(split[emailIndex], split[nameIndex]);
        agent.setDrawNumber(Integer.parseInt(split[drawNumberIndex]));
        agent.setStatus(AgentStatus.valueOf(split[statusIndex]));
        agent.setDeathTime(Date.valueOf(split[deathTimeIndex]));
        agent.setPersonalKills(Integer.parseInt(split[personalKillsIndex]));
        agent.setPointsTotal(Integer.parseInt(split[pointsTotalIndex]));
        return agent;
    }

    private AgentList getAgentKillList(String[] killed, AgentList agentList){
        AgentList killedList = new AgentList();
        for(String k: killed){
            killedList.addAgent( agentList.getAgentWithEmailAddress(k));
        }
        return killedList;
    }

    private AgentList setupAgentsFromFile(ArrayList<String> file){
        AgentList agentList = new AgentList();
        for(String line: file){
            agentList.addAgent(setupAgent(line));
        }
        return agentList;
    }

    private AgentList connectAgentObjects(ArrayList<String> file, AgentList agentList){
        for(String line: file){
            String[] split = splitLine(line, ",");
            Agent agent = agentList.getAgentWithEmailAddress(split[emailIndex]);
            agent.setCurrentTarget(agentList.getAgentWithEmailAddress(split[currentTargetIndex]));
            agent.extendKillList(getAgentKillList(splitLine(split[killListIndex], "-"), agentList));
        }
        return agentList;
    }

    public AgentList getAgentListFromFile(String fileName){
        ArrayList<String> file = readAgentFile(fileName);
        AgentList agentList = setupAgentsFromFile(file);
        return connectAgentObjects(file, agentList);
    }
}
