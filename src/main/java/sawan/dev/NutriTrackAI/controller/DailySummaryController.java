package sawan.dev.NutriTrackAI.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sawan.dev.NutriTrackAI.dto.DailySummaryDTO;
import sawan.dev.NutriTrackAI.service.DailySummaryService;
import sawan.dev.NutriTrackAI.service.impl.DailySummaryServiceImpl;

import java.time.LocalDate;
import java.time.ZoneId;

@RestController
@RequestMapping("/api/users/{userId}")
@RequiredArgsConstructor
public class DailySummaryController {

    private final DailySummaryServiceImpl dailySummaryService;
  //  private final DaNotificationService; // optional

    @GetMapping("/daily-summary")
    public ResponseEntity<DailySummaryDTO> getDailySummary(
            @PathVariable Long userId,
            @RequestParam(required = false) String date) {

        LocalDate d = (date == null) ? LocalDate.now(ZoneId.of("Asia/Kolkata")) : LocalDate.parse(date);
        DailySummaryDTO dto = dailySummaryService.computeDailySummary(userId, d);
        return ResponseEntity.ok(dto);
    }

    // manual trigger to compute & create notification
    @PostMapping("/daily-summary/notify")
    public ResponseEntity<String> computeAndNotify(@PathVariable Long userId,
                                                   @RequestParam(required = false) String date) {
        LocalDate d = (date == null) ? LocalDate.now(ZoneId.of("Asia/Kolkata")) : LocalDate.parse(date);
        DailySummaryDTO summary = dailySummaryService.computeDailySummary(userId, d);
        dailySummaryService.createDailyNotificationFromSummary(summary);
        return ResponseEntity.ok("Notification sent");
    }
}
