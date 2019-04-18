package com.example.hendrixassassins.uipages;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hendrixassassins.R;

public class OpenScreenActivity extends AppCompatActivity {
    int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent gameIntent = new Intent(OpenScreenActivity.this, LoginActivity.class);
                startActivity(gameIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
