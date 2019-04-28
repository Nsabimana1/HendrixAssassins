package com.example.hendrixassassins;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hendrixassassins.UItestcompnents.ListViewTestActivity;
import com.example.hendrixassassins.agent.AgentFileHelper;
import com.example.hendrixassassins.agent.AgentList;
import com.example.hendrixassassins.agent.AgentStatus;
import com.example.hendrixassassins.email.Email;
import com.example.hendrixassassins.email.EmailServer;
import com.example.hendrixassassins.email.GmailLogin;
import com.example.hendrixassassins.email.GmailTestActivity;
import com.example.hendrixassassins.game.Game;
import com.example.hendrixassassins.game.GameStatus;
import com.example.hendrixassassins.uipages.HomeActivity;

import com.example.hendrixassassins.uipages.HomeFragment;
import com.example.hendrixassassins.uipages.LoginActivity;

import java.io.IOException;

import javax.mail.MessagingException;

public class MainActivity extends AppCompatActivity {
    Button toGmailTest, toListViewTest, toCreateGame, toHome;
    private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findIDs();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GmailLogin login = new GmailLogin("HendrixAssassinsApp@gmail.com","AssassinsTest1");
                 } catch (Exception e) {
                    Log.e("Login Activivity", e.toString());
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Login Failed.", Toast.LENGTH_SHORT);
                            toast.show();
                        }});
                }
            }
        }).start();
        context = this.getApplicationContext();
        toGmailTestButton();
        toListViewTestButton();
        toHomeButton();
        new FileTestActivity(getBaseContext());
        grabInbox();
    }



    private void toListViewTestButton() {
        toListViewTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toListViewTestActivity();
            }
        });

    }

    private void toListViewTestActivity() {
        Game game = new Game("test@gmail.com");
        game.readGameFromFile(context);
        game.writeGameToFile(context);
        Intent forwardIntent = new Intent(MainActivity.this, HomeActivity.class);
        forwardIntent.putExtra("email", game.getEmail());
        startActivity(forwardIntent);
    }

    private void findIDs(){
        setContentView(R.layout.activity_main);
        toGmailTest = findViewById(R.id.toGmailTest);
        toListViewTest = findViewById(R.id.to_listview_test);
        toHome = findViewById(R.id.toHome);
    }

    private void toGmailTestButton() {
        toGmailTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toGmailTestActivity();
            }
        });
    }

    private void toGmailTestActivity() {
        Intent forwardIntent = new Intent(MainActivity.this, GmailTestActivity.class);
        // passing the first email in the inbox to the next activity
        // this is ONLY possible because the Email class implements serializable
        forwardIntent.putExtra("FirstEmail", EmailServer.get().getInboxList().get(0));
        startActivity(forwardIntent);
    }

    private void grabInbox() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean read;
                    EmailServer.get().refreshInboxMessages();
                    } catch (MessagingException | IOException e){
                    Log.e("inbox", "could not read inbox "+ e.toString());
                }
            }    }).start();
    }


    private void toHomeButton() {
        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Game game = new Game("HendrixAssassinsApp@gmail.com");
                game.resetPassword("AssassinsTest1");
                game.setStatus(GameStatus.SIGNUP);
                game.writeGameToFile(context);
                // go to user listview
                gotoHomeIntent(game.getEmail());
            }
        });
    }


    private Game setupGame(String email, String password) {
        Game game = new Game(email);
        game.resetPassword(password);
        game.readGameFromFile(context);
        game.writeGameToFile(context);
        Game game2 = new Game(email);
        game2.readGameFromFile(context);
        //AgentFileHelper helper = new AgentFileHelper();
        //helper.writeToFile(game.getAgentFileName(), new AgentList(), context);
        return game;
        // TODO set next screen based on game status
    }

    private void gotoHomeIntent(String email) {
        /*
        Intent userListView = new Intent(LoginActivity.this, HomeActivity.class);
        //this is where we need the game logic to tell the userListView which activity it needs to go to
         */
        Intent userListView = new Intent(MainActivity.this, SetUpGameActivity.class);
        userListView.putExtra("email", email);
        startActivity(userListView);
        finish();
    }

}
