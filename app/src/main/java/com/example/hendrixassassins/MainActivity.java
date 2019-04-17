package com.example.hendrixassassins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button toGmailTest, toListViewTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findIDs();
        toGmailTestButton();
        toListViewTestButton();
        new FileTestActivity(getBaseContext());
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
        Intent forwardIntent = new Intent(MainActivity.this, ListViewTestActivity.class);
        startActivity(forwardIntent);
    }

    private void findIDs(){
        setContentView(R.layout.activity_main);
        toGmailTest = findViewById(R.id.toGmailTest);
        toListViewTest = findViewById(R.id.to_listview_test);
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
