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

public class AgentFileHelper {

    Context context;

    public AgentFileHelper(Context context){
        this.context = context;
    }

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
        String output = "";
        // TODO iterate through each agent and write info to file
        return output;
    }

    public AgentList getAgentListFromFile(String fileName){
        AgentList agentList = new AgentList();
        String file = readAgentFile(fileName);
        // TODO iterate through each line and construct Agents
        return agentList;
    }

    private String readAgentFile(String fileName){
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput(fileName);
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString).append("\n");
                }
                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("Exception", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Exception", "Can not read file: " + e.toString());
        }
        return ret;
    }

}
