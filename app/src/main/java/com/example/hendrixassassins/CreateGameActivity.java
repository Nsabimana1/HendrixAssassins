package com.example.hendrixassassins;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CreateGameActivity extends AppCompatActivity {
    private Button createGameButton, verifyAllAgentsButton, addAllAgentsToGameButton;
    private ListView incomingEmails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        getIDs();
        createGameButtonListener();
        verifyAllAgentsButtonListener();
        addAllAgentsToGameButtonListener();

        //this will be called in a later state of the game
        //setupIncomingEmailsListView();


    }

    private void setupIncomingEmailsListView() {
        IncomingEmailListViewAdapter<String> incomingEmailListViewAdapter = new IncomingEmailListViewAdapter<>(this,
                R.layout.incoming_emails_start_game, new ArrayList<String>());
        incomingEmails.setAdapter(incomingEmailListViewAdapter);

    }

    private void addAllAgentsToGameButtonListener() {
        addAllAgentsToGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void verifyAllAgentsButtonListener() {
        verifyAllAgentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void createGameButtonListener() {
        createGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void getIDs() {
        createGameButton = findViewById(R.id.CreateGame);
        verifyAllAgentsButton = findViewById(R.id.verifyAllAgents);
        addAllAgentsToGameButton = findViewById(R.id.addSelectedToGame);
        incomingEmails = findViewById(R.id.listofIncomingEmails);
    }
}
