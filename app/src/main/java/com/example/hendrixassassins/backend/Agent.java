package com.example.hendrixassassins.backend;

import java.lang.ref.SoftReference;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Date;

public class Agent {
    private int drawNumber, personalKills, pointsTotal;
    private String email, currentTarget;
    private AgentStatus status;
    private Date deathTime;
    private ArrayList<Agent> killList;

    public Agent(String email, int drawNumber){
        this.email = email;
        this.drawNumber = drawNumber;
        this.personalKills = 0;
        this.pointsTotal = 0;
        this.status = AgentStatus.ALIVE;
        this.deathTime = null;
        this.killList = new ArrayList<Agent>();
        this.currentTarget = null;
    }

    public AgentStatus getStatus(){
        return status;
    }

    public void setStatus(AgentStatus status){
        this.status = status;
    }

    public String getEmail(){
        return email;
    }

    public int getDrawNumber(){
        return drawNumber;
    }

    public void setPersonalKills(int personalKills) {
        this.personalKills = personalKills;
    }

    public void incrementPersonalKills(){
        personalKills++;
    }

    public int getPersonalKills(){
        return personalKills;
    }

    public int getPointsTotal(){
        return pointsTotal;
    }

    public void setPointsTotal(int pointsTotal){
        this.pointsTotal = pointsTotal;
    }

    public void addToPointsTotal(int addedPoints){
        pointsTotal += addedPoints;
    }

    public void setCurrentTarget(String target){
        currentTarget = target;
    }

    public String getCurrentTarget(){
        if(currentTarget == null){
            return "NA";
        }
        return currentTarget;
    }

    public Date getDeathTime(){
        return deathTime;
    }

    public String getDeathTimeString(){
        if(deathTime==null){
            return "NA";
        }
        return deathTime.toString();
    }

    public void setDeathTime(Date time){
        deathTime = time;
    }

    public ArrayList<Agent> getKillList(){
        return killList;
    }

    public void addToKillList(ArrayList<Agent> kills){
        killList.addAll(kills);
    }

    public String getTableRow(){
        return String.valueOf(drawNumber) + "," + email +"," + status.toString()+"," +
                getDeathTimeString() + "," + currentTarget + "," + String.valueOf(personalKills) +
                "," + String.valueOf(pointsTotal) + "," + getKillListAsString();
    }

    private String getKillListAsString(){
        StringBuilder builder = new StringBuilder();
        for(Agent agent: killList){
            builder.append(agent.email);
            builder.append("-");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

}
