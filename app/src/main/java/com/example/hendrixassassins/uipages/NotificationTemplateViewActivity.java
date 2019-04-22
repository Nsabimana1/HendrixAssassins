package com.example.hendrixassassins.uipages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hendrixassassins.R;

public class NotificationTemplateViewActivity extends AppCompatActivity {
    private TextView notificationHeaderView, notificationContentView;
    private TextView  senderNameView, dateView;
    private ImageButton ignoreButton, replayButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_template_view);
        setupComponents();
    }

    private void setupComponents(){
        notificationContentView = findViewById(R.id.notification_body);
        notificationHeaderView = findViewById(R.id.notication_header);
        senderNameView = findViewById(R.id.sender_name_view);
        dateView = findViewById(R.id.date_view);
        ignoreButton = findViewById(R.id.ignore_button);
        replayButton = findViewById(R.id.reply_button);
    }
}
