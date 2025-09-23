package sawan.dev.NutriTrackAI.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sawan.dev.NutriTrackAI.entity.Notification;
import sawan.dev.NutriTrackAI.repository.NotificationRepository;
import sawan.dev.NutriTrackAI.service.NotificationService;


import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public Notification createNotification(Notification notification) {
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false); // default unread
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public List<Notification> getUnreadNotifications(Long userId) {
        return notificationRepository.findByUserIdAndReadFalse(userId);
    }

    @Override
    public Notification markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        return notificationRepository.save(notification);
    }

    @Override
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}