package com.example.hendrixassassins.game;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Game {
    private String email;
    private String password;
    private String fileName;
    private GameStatus status;

    public Game(String email, String Password){
        this.email = email;
        this.password = password;
        this.fileName = this.email.split("@")[0];
        this.status = GameStatus.SIGNUP;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword(){
        return password;
    }

    public void resetPassword(String newPassword){
        password = newPassword;
    }

    public String getGameFileName(){
        return fileName + ".csv";
    }

    public String getAgentFileName(){
        return fileName + "_Agents.csv";
    }

    public GameStatus getStatus(){
        return status;
    }

    public void setStatus(GameStatus newStatus){
        status = newStatus;
    }

    private String getFileContents(){
        return getEmail() + getPassword() + getStatus().toString();
    }

    public void writeGameToFile(Context context){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    context.openFileOutput(getGameFileName(), Context.MODE_PRIVATE));
            outputStreamWriter.write(getFileContents());
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private void updateGameFromFile(String receiveString){
        String[] split = receiveString.trim().split(",");
        resetPassword(split[1]);
        setStatus(GameStatus.valueOf(split[2]));
    }

    public void readGameFromFile(Context context){
        try {
            InputStream inputStream = context.openFileInput(fileName);
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                if ( (receiveString = bufferedReader.readLine()) != null ){
                    updateGameFromFile(receiveString);
                }
                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("Exception", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Exception", "Can not read file: " + e.toString());
        }
    }
}
