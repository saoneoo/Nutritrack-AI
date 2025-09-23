package sawan.dev.NutriTrackAI.dto;

import lombok.Data;

import java.time.LocalDate;

// dto/responseDto/DailySummaryDTO.java
@Data
public class DailySummaryDTO {
    private Long userId;
    private LocalDate date;
    private int goalCalories;          // resolved goal
    private double consumedCalories;   // sum diet entries
    private double burnedCalories;     // sum workouts
    private double netCalories;        // consumed - burned
    private double remainingCalories;  // goal - net
    private String aiFeedback;         // short text from AI
}
