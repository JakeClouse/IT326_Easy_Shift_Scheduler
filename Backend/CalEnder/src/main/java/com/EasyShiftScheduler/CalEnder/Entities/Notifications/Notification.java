package com.EasyShiftScheduler.CalEnder.Entities.Notifications;

public interface Notification {
    // Method to send simple email
    String sendNotification(byte[] payload) throws Exception;
}
