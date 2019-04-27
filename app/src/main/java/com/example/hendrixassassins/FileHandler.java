package com.example.hendrixassassins;

import android.content.Context;

import com.example.hendrixassassins.agent.Agent;
import com.example.hendrixassassins.agent.AgentFileHelper;
import com.example.hendrixassassins.agent.AgentFileInteraction;
import com.example.hendrixassassins.agent.AgentList;
import com.example.hendrixassassins.agent.AgentStatus;
import com.example.hendrixassassins.game.Game;
import com.example.hendrixassassins.game.GameStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class FileHandler {
    private File filedir;
    private File taskdir;

    private final static int drawNumberIndex = 0, nameIndex = 1, emailIndex = 2, statusIndex = 3,
            deathTimeIndex = 4, currentTargetIndex = 5, personalKillsIndex = 6, pointsTotalIndex = 7,
            killListIndex = 8;
    private final static String dateSplitChar = "-", spaceSplitChar = " ", timeSplitChar = ":";

    public FileHandler(){

    }

    public void set_filedir(File filedir, File taskdir){
        this.filedir = filedir;
        this.taskdir = taskdir;
    }

    public String getAgentListFileString(AgentList agentList){
        StringBuilder builder = new StringBuilder();
        for(Agent agent: agentList.getAllAgents()) builder.append(agent.getTableRow());
        return builder.toString();
    }

    public void writeAgentsToFile(String fileName, AgentList agentList) throws IOException{
        writeFile_filedir(fileName, getAgentListFileString(agentList));
    }

    private String getFileContents(Game game){
        return game.getEmail() + "," + game.getPassword() + "," + game.getStatus().toString() + "," + getPurgeTimeString(game.getPurgeTime());
    }

    private String getPurgeTimeString(GregorianCalendar purgeTime){
        if(purgeTime==null){
            return "NA";
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(purgeTime.getTime());
    }

    public void writeGameToFile(Game game) throws IOException{
        writeFile_filedir(game.getGameFileName(), getFileContents(game));
    }


    public void writeFile_filedir(String file_name, String text) throws IOException {
        writeFile(filedir, file_name, text);
    }

    public void writeFile_taskdir(String file_name, String text) throws IOException {
        writeFile(taskdir, file_name, text);
    }

    private void writeFile(File dir, String file_name, String text) throws IOException {
        File output = new File(dir, file_name);
        PrintWriter pw = new PrintWriter(new FileWriter(output));
        pw.println(text);
        pw.close();
    }

    private GregorianCalendar dateFromString(String string){
        if(string.equals("NA") || string.trim().length() == 0) return null;
        String[] split = string.split(spaceSplitChar);
        String[] date = split[0].split(dateSplitChar);
        String[] time = split[1].split(timeSplitChar);
        return new GregorianCalendar(Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1,
                Integer.parseInt(date[2]),  Integer.parseInt(time[0]), Integer.parseInt(time[1]),
                Integer.parseInt(time[2]));
    }

    private Game updateGameFromFile(String receiveString, Game game){
        String[] split = receiveString.trim().split(",");
        game.resetPassword(split[1]);
        game.setStatus(GameStatus.valueOf(split[2]));
        game.setPurgeTime(dateFromString(split[3]));
        return game;
    }

    public Game read_Game_file(String emailAddress) throws FileNotFoundException {
        Game game = new Game(emailAddress);
        String gameFileName = game.getGameFileName();
        File file = new File(filedir, gameFileName);
        Scanner x = new Scanner(file);
        while(x.hasNextLine()) {
            String line = x.nextLine();
            game = updateGameFromFile(line, game);
        }
        x.close();
        return game;

    }

    public AgentList read_Agent_file(String fileName) throws FileNotFoundException {
        ArrayList<String> agentsCSV = new ArrayList<>();
        Scanner x = new Scanner(new File(taskdir.getAbsoluteFile(), fileName));
        while(x.hasNextLine()) {
            String line = x.nextLine();
            agentsCSV.add(line);

        }
        x.close();
        AgentList agentList = setupAgentsFromFile(agentsCSV);
        return connectAgentObjects(agentsCSV, agentList);
    }

    private Agent setupAgent(String line){
        String[] split = splitLine(line, ",");
        Agent agent = new Agent(split[emailIndex], split[nameIndex]);
        agent.setDrawNumber(intFromString(split[drawNumberIndex]));
        agent.setStatus(AgentStatus.valueOf(split[statusIndex]));
        agent.setDeathTime(dateFromString(split[deathTimeIndex]));
        agent.setPersonalKills(intFromString(split[personalKillsIndex]));
        agent.setPointsTotal(intFromString(split[pointsTotalIndex]));
        return agent;
    }

    private String[] splitLine(String line, String splitChar){
        return line.trim().split(splitChar);
    }

    private int intFromString(String string){
        if(string.equals("NA")) return 0;
        return Integer.parseInt(string.trim());
    }

    private AgentList getAgentKillList(String[] killed, AgentList agentList){
        AgentList killedList = new AgentList();
        for(String k: killed)  killedList.addAgent( agentList.getAgentWithEmailAddress(k));
        return killedList;
    }

    private AgentList setupAgentsFromFile(ArrayList<String> file){
        AgentList agentList = new AgentList();
        for(String line: file){
            if(line.trim().length() > 0) agentList.addAgent(setupAgent(line));
        }
        return agentList;
    }

    private AgentList connectAgentObjects(ArrayList<String> file, AgentList agentList){
        for(String line: file){
            String[] split = splitLine(line, ",");
            if(split.length > 8) {
                Agent agent = agentList.getAgentWithEmailAddress(split[emailIndex]);
                agent.setCurrentTarget(agentList.getAgentWithEmailAddress(split[currentTargetIndex]));
                agent.extendKillList(getAgentKillList(splitLine(split[killListIndex], ":"), agentList));
            }
        }
        return agentList;
    }

    public AgentList testFileReading(ArrayList<String> file){
        return connectAgentObjects( file, setupAgentsFromFile(file));
    }

    public File makeDir(String namedir){
        File dir = new File(filedir, namedir);
        dir.mkdirs();
        return dir;
    }

    public boolean isGameFile(Game game){
        return (new File(taskdir, game.getGameFileName()).isFile());
    }

}
