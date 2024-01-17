package com.alejandro.app.rest.Services;

import com.alejandro.app.rest.Models.Notification;
import com.alejandro.app.rest.Repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getAllNotificationsForUser(long userId) {
        return notificationRepository.findByUserId(userId);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public void deleteNotification(long notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}
