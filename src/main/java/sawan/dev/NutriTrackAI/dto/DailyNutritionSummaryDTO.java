package sawan.dev.NutriTrackAI.dto;


import lombok.Data;

@Data
public class DailyNutritionSummaryDTO {
    private Long userId;
    private String date;

    private double totalCalories;
    private double totalProtein;
    private double totalCarbs;
    private double totalFats;

    private double dailyGoal;
    private double remainingCalories;
}
