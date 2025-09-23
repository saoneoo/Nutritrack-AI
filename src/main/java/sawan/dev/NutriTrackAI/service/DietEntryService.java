package sawan.dev.NutriTrackAI.service;

import sawan.dev.NutriTrackAI.dto.DailyNutritionSummaryDTO;
import sawan.dev.NutriTrackAI.entity.DietEntry;

import java.time.LocalDate;
import java.util.List;

public interface DietEntryService {

    DietEntry addDietEntry(Long userId, String foodName, double grams);

    DietEntry getDietEntryById(Long id);
    List<DietEntry> getAllDietEntriesByUser(Long userId);
    void deleteDietEntry(Long id);

    // Extra Features
    double getTotalCaloriesForDate(Long userId, LocalDate date);
    double getTotalProteinForDate(Long userId, LocalDate date);
    double getTotalCarbsForDate(Long userId, LocalDate date);
    double getTotalFatsForDate(Long userId, LocalDate date);

    DailyNutritionSummaryDTO getDailySummary(Long userId, LocalDate targetDate);
}