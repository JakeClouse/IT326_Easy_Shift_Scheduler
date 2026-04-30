package com.EasyShiftScheduler.CalEnder.Services;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.EasyShiftScheduler.CalEnder.Entities.Notifications.EmailDetails;
import com.EasyShiftScheduler.CalEnder.Entities.Notifications.Notification;

@Service
public class EmailService implements Notification {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    // Send simple mail
    @Override
    public String sendNotification(byte[] payload){
        try {
            // De-serialize payload into EmailDetails object
            ByteArrayInputStream bais = new ByteArrayInputStream(payload);
            ObjectInputStream objectInputStream = new ObjectInputStream(bais);
            EmailDetails details = (EmailDetails) objectInputStream.readObject();

            SimpleMailMessage mailMessage =
                    new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            javaMailSender.send(mailMessage);

            return "Mail Sent Successfully";

        } catch (Exception e) {

            return "Error while sending mail";
        }
    }
}
