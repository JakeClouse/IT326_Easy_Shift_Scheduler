package com.EasyShiftScheduler.CalEnder.Entities.Factories;

public class Email implements Notification {
    public String emailAccount;
    private String password;
    public String notificationBody;

    @Override
    public void makeNotification(byte[] payload) {
        //TODO
    }
}
