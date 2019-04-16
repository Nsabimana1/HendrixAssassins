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

    Context context;

    public AgentFileHelper(Context context){
        this.context = context;
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

    public String getAgentListFileString(AgentList agentList){
        StringBuilder builder = new StringBuilder();
        for(Agent agent: agentList.getAllAgents()){
            builder.append(agent.getTableRow());
        }
        return builder.toString();
    }

    private Agent makeAgent(String line){
        String[] split = line.trim().split(",");
        Agent agent = new Agent(split[2], split[1]);
        agent.setDrawNumber(Integer.parseInt(split[0]));
        agent.setStatus(AgentStatus.valueOf(split[4]));
        agent.setDeathTime(Date.valueOf(split[5]));
        //TODO turn get Target Email into an Agent?
    }

    public AgentList getAgentListFromFile(String fileName){
        AgentList agentList = new AgentList();
        ArrayList<String> file = readAgentFile(fileName);
        for(String line: file){

        }
        // TODO iterate through each line and construct Agents
        return agentList;
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

}
