package sawan.dev.NutriTrackAI.service;


import sawan.dev.NutriTrackAI.entity.Notification;

import java.util.List;

public interface NotificationService {

    // Add a new notification for a user
    Notification createNotification(Notification notification);

    // Get all notifications for a specific user
    List<Notification> getUserNotifications(Long userId);

    // Get unread notifications for a user
    List<Notification> getUnreadNotifications(Long userId);

    // Mark a notification as read
    Notification markAsRead(Long notificationId);

    // Delete a notification
    void deleteNotification(Long notificationId);
}