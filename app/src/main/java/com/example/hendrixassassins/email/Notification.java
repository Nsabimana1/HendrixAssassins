package com.example.hendrixassassins.email;

import com.example.hendrixassassins.agent.Agent;

public class Notification {
    private Agent notifier;
    private String notificationContent;

    public Notification(Agent notifier, String notificationContent){
        this.notifier = notifier;
        this.notificationContent = notificationContent;
    }

    public Agent getNotifier() {
        return notifier;
    }

    public String getNotificationContent() {
        return notificationContent;
    }
}
