package sawan.dev.NutriTrackAI;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sawan.dev.NutriTrackAI.dto.DailySummaryDTO;
import sawan.dev.NutriTrackAI.entity.User;
import sawan.dev.NutriTrackAI.repository.UserRepository;
import sawan.dev.NutriTrackAI.service.DailySummaryService;
import sawan.dev.NutriTrackAI.service.impl.DailySummaryServiceImpl;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DailySummaryScheduler {

    private final UserRepository userRepository;
    private final DailySummaryServiceImpl dailySummaryService;

    // run every day at 00:05 Asia/Kolkata
    @Scheduled(cron = "0 5 0 * * *", zone = "Asia/Kolkata")
    public void runDailySummaries() {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata")).minusDays(1); // sum previous day
        List<User> users = userRepository.findAll();
        for (User user : users) {
            try {
                DailySummaryDTO summary = dailySummaryService.computeDailySummary(user.getId(), today);
                dailySummaryService.createDailyNotificationFromSummary(summary);
            } catch (Exception ex) {
                // log & continue so one failure doesn't stop the scheduler
                log.error("Failed to process daily summary for user " + user.getId(), ex);
            }
        }
    }
}
