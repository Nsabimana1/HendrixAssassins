package com.example.hendrixassassins.email;

import java.util.ArrayList;

public class NotificationList {
    private ArrayList<Notification> allNotifications;

    public NotificationList(){
        allNotifications = new ArrayList<>();
    }

    public void addNotification(Notification notification){
        this.allNotifications.add(notification);
    }

    public ArrayList<Notification> getAllNotifications() {
        return allNotifications;
    }
}
