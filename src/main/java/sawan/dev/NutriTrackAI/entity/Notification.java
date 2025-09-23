package sawan.dev.NutriTrackAI.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sawan.dev.NutriTrackAI.entity.enums.NotificationType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Each notification belongs to one user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 1000) // or even 2000 if AI messages are long
    private String message;
                                 // e.g. "Workout reminder", "Daily calorie goal reached"

    @Enumerated(EnumType.STRING)
    private NotificationType type; // INFO, REMINDER, ALERT

    @Column(name = "is_read")
    private boolean read;
    // true if user has seen the notification

    private LocalDateTime createdAt; // when notification was generated

}
