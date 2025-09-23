package sawan.dev.NutriTrackAI.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sawan.dev.NutriTrackAI.aiService.AIService;
import sawan.dev.NutriTrackAI.dto.DailySummaryDTO;
import sawan.dev.NutriTrackAI.entity.*;
import sawan.dev.NutriTrackAI.entity.enums.NotificationType;
import sawan.dev.NutriTrackAI.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailySummaryServiceImpl {

    private final UserRepository userRepository;
    private final DietEntryRepository dietEntryRepository;
    private final WorkoutEntryRepository workoutEntryRepository;
    private final NotificationRepository notificationRepository;
    private final AIService aiService; // generates AI feedback
    private final AiPlanRepository aiPlanRepository; // optional: store AI responses

    // ✅ Compute summary for one user + date
    public DailySummaryDTO computeDailySummary(Long userId, LocalDate date) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found " + userId));

        // 1) Get goal calories from user
        int goalCalories = resolveDailyCalorieGoal(user);

        // 2) Sum consumed calories
        List<DietEntry> dietEntries = dietEntryRepository.findByUserIdAndConsumedAt(userId, date);
        double consumed = dietEntries.stream()
                .mapToDouble(DietEntry::getCaloriesConsumed)
                .sum();

        // 3) Sum burned calories
        List<WorkoutEntry> workouts = workoutEntryRepository.findByUserIdAndDate(userId, date);
        double burned = workouts.stream()
                .mapToDouble(w -> w.getWorkout().getCaloriesPerMinute() * w.getDurationMinutes())
                .sum();

        // 4) Net and remaining
        double net = consumed - burned;
        double remaining = goalCalories - net;

        // 5) AI feedback
        String aiFeedback = aiService.generateDailyFeedback(
                user.getName(), date, goalCalories, consumed, burned, net);

        // ✅ Build DTO
        DailySummaryDTO dto = new DailySummaryDTO();
        dto.setUserId(userId);
        dto.setDate(date);
        dto.setGoalCalories(goalCalories);
        dto.setConsumedCalories(consumed);
        dto.setBurnedCalories(burned);
        dto.setNetCalories(net);
        dto.setRemainingCalories(remaining);
        dto.setAiFeedback(aiFeedback);

        return dto;
    }

    // ✅ Helper: get user’s daily calorie goal
    private int resolveDailyCalorieGoal(User user) {
        if (user.getDailyCalorieGoal() != null) {
            return user.getDailyCalorieGoal();
        }
        return 2000; // fallback default
    }

    // ✅ Create notification based on summary
    @Transactional
    public Notification createDailyNotificationFromSummary(DailySummaryDTO summary) {
        User user = userRepository.findById(summary.getUserId()).orElseThrow();

        double net = summary.getNetCalories();
        int goal = summary.getGoalCalories();

        // Surplus / Deficit message
        String statusMsg;
        if (net > goal) {
            double surplus = net - goal;
            statusMsg = String.format("You are in a surplus of %.0f kcal today.", surplus);
        } else if (net < goal) {
            double deficit = goal - net;
            statusMsg = String.format("You are in a deficit of %.0f kcal today.", deficit);
        } else {
            statusMsg = "You perfectly matched your calorie goal today!";
        }

        // Final notification message
        String msg = String.format(
                "Daily summary %s: consumed=%.0f kcal, burned=%.0f kcal, net=%.0f kcal. %s %s",
                summary.getDate(),
                summary.getConsumedCalories(),
                summary.getBurnedCalories(),
                summary.getNetCalories(),
                statusMsg,
                summary.getAiFeedback()
        );

        Notification n = new Notification();
        n.setUser(user);
        n.setMessage(msg);
        n.setType(NotificationType.INFO);
        n.setRead(false);
        n.setCreatedAt(LocalDateTime.now());

        return notificationRepository.save(n);
    }
}
