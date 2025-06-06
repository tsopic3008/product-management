package com.tscore.service;

public class AlertService {
    private NotificationService notificationService;

    public AlertService(NotificationService service) {
        this.notificationService = service;
    }

    public void triggerAlert(String msg) {
        notificationService.send("ALERT: " + msg);
    }
}
