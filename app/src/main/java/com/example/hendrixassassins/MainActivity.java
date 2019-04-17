package com.example.hendrixassassins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button toGmailTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findIDs();
        toGmailTestButton();
    }

    private void findIDs(){
        setContentView(R.layout.activity_main);
        toGmailTest = findViewById(R.id.toGmailTest);
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
        startActivity(forwardIntent);
    }

}
