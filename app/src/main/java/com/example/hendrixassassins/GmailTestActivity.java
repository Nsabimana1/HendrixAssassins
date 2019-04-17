package com.example.hendrixassassins;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class GmailTestActivity extends AppCompatActivity {
    EditText userName, password, address, subject, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findIDs();
    }
    private void findIDs(){
        setContentView(R.layout.activity_gmail_test);
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        address = findViewById(R.id.address);
        subject = findViewById(R.id.subject);
        message = findViewById(R.id.message);
    }

}
