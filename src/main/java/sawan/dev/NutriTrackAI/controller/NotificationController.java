package sawan.dev.NutriTrackAI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sawan.dev.NutriTrackAI.dto.NotificationDTO;
import sawan.dev.NutriTrackAI.entity.Notification;
import sawan.dev.NutriTrackAI.entity.User;
import sawan.dev.NutriTrackAI.repository.NotificationRepository;
import sawan.dev.NutriTrackAI.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Convert Entity → DTO
    private NotificationDTO mapToDTO(Notification notification) {
        return new NotificationDTO(
                notification.getId(),
                notification.getUser() != null ? notification.getUser().getId() : null,
                notification.getMessage(),
                notification.getType(),
                notification.isRead(),
                notification.getCreatedAt()
        );
    }

    // ✅ Convert DTO → Entity
    private Notification mapToEntity(NotificationDTO dto) {
        Notification notification = new Notification();
        notification.setId(dto.getId());
        notification.setMessage(dto.getMessage());
        notification.setType(dto.getType());
        notification.setRead(dto.isRead());
        notification.setCreatedAt(dto.getCreatedAt());

        if (dto.getUserId() != null) {
            Optional<User> user = userRepository.findById(dto.getUserId());
            user.ifPresent(notification::setUser);
        }

        return notification;
    }

    // ✅ Create Notification
    @PostMapping
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO dto) {
        Notification saved = notificationRepository.save(mapToEntity(dto));
        return ResponseEntity.ok(mapToDTO(saved));
    }

    // ✅ Get All Notifications
    @GetMapping
    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Get Notification by ID
    @GetMapping("/{id}")
    public ResponseEntity<NotificationDTO> getNotificationById(@PathVariable Long id) {
        return notificationRepository.findById(id)
                .map(notification -> ResponseEntity.ok(mapToDTO(notification)))
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Update Notification
    @PutMapping("/{id}")
    public ResponseEntity<NotificationDTO> updateNotification(@PathVariable Long id, @RequestBody NotificationDTO dto) {
        return notificationRepository.findById(id)
                .map(existing -> {
                    Notification updated = mapToEntity(dto);
                    updated.setId(id);
                    Notification saved = notificationRepository.save(updated);
                    return ResponseEntity.ok(mapToDTO(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Delete Notification
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteNotification(@PathVariable Long id) {
        return notificationRepository.findById(id)
                .map(notification -> {
                    notificationRepository.delete(notification);
                    return ResponseEntity.noContent().build();  // 204
                })
                .orElse(ResponseEntity.notFound().build());     // 404
    }

    // ✅ Mark Notification as Read
    @PatchMapping("/{id}/read")
    public ResponseEntity<NotificationDTO> markAsRead(@PathVariable Long id) {
        return notificationRepository.findById(id)
                .map(notification -> {
                    notification.setRead(true);
                    Notification saved = notificationRepository.save(notification);
                    return ResponseEntity.ok(mapToDTO(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
