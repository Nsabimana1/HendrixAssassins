package com.example.hendrixassassins.email;

import java.util.ArrayList;

public class Email {
    private String sender, message;
    private ArrayList<String> to, cc, bcc;

    public Email(String sender, String message){
        this.sender = sender;
        this.message = message;
    }
}
