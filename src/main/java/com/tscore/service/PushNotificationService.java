package com.tscore.service;

public class PushNotificationService implements NotificationService {

    @Override
    public void send(String message) {
        System.out.println("Sending push notification: " + message);
    }
}
