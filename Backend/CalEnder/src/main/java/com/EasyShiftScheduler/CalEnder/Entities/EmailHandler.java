package com.EasyShiftScheduler.CalEnder.Entities;

import com.EasyShiftScheduler.CalEnder.Entities.Factories.NotificationFactory;

public class EmailHandler {
    public String emailAccount;
    private String password;
    private NotificationFactory notificationFactory;

    public void email(byte[] payload) {
        notificationFactory.makeNotification(payload);
    }
}
