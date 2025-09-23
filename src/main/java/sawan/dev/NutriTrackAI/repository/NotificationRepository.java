package sawan.dev.NutriTrackAI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sawan.dev.NutriTrackAI.entity.Notification;
import sawan.dev.NutriTrackAI.entity.User;

import java.util.List;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Fetch all notifications for a user
    List<Notification> findByUserId(Long userId);

    // Fetch unread notifications for a user
    List<Notification> findByUserIdAndReadFalse(Long userId);
}