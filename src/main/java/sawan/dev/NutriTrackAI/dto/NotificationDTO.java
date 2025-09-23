package sawan.dev.NutriTrackAI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sawan.dev.NutriTrackAI.entity.enums.NotificationType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private Long id;
    private Long userId;               // reference to User instead of full object
    private String message;
    private NotificationType type;
    private boolean read;
    private LocalDateTime createdAt;
}
