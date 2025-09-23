package sawan.dev.NutriTrackAI.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sawan.dev.NutriTrackAI.dto.DailyNutritionSummaryDTO;
import sawan.dev.NutriTrackAI.entity.DietEntry;
import sawan.dev.NutriTrackAI.entity.FoodItem;
import sawan.dev.NutriTrackAI.entity.User;
import sawan.dev.NutriTrackAI.repository.DietEntryRepository;
import sawan.dev.NutriTrackAI.repository.UserRepository;
import sawan.dev.NutriTrackAI.service.DietEntryService;
import sawan.dev.NutriTrackAI.service.FoodItemService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DietEntryServiceImpl implements DietEntryService {

    private final DietEntryRepository dietEntryRepository;
    private final UserRepository userRepository;
    private final FoodItemService foodItemService;

    /**
     * Add a diet entry by food name and grams.
     * Uses FoodItemService (AI-powered) to fetch or create nutrition data.
     */
    @Override
    public DietEntry addDietEntry(Long userId, String foodName, double grams) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Ensure food exists in DB (fetch from AI if not)
        FoodItem foodItem = foodItemService.getOrCreateFoodItem(foodName);

        // Scale nutrition values based on grams
        double calories = (foodItem.getCaloriesPer100g() / 100.0) * grams;
        double protein  = (foodItem.getProtein() / 100.0) * grams;
        double carbs    = (foodItem.getCarbs() / 100.0) * grams;
        double fats     = (foodItem.getFats() / 100.0) * grams;

        DietEntry entry = new DietEntry();
        entry.setUser(user);
        entry.setFoodItem(foodItem);
        entry.setQuantityInGrams(grams);
        entry.setCaloriesConsumed(calories);
        entry.setProteinConsumed(protein);
        entry.setCarbsConsumed(carbs);
        entry.setFatsConsumed(fats);
        entry.setConsumedAt(LocalDate.now());

        return dietEntryRepository.save(entry);
    }

    public DailyNutritionSummaryDTO getDailySummary(Long userId, LocalDate date) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        double totalCalories = getTotalCaloriesForDate(userId, date);
        double totalProtein  = getTotalProteinForDate(userId, date);
        double totalCarbs    = getTotalCarbsForDate(userId, date);
        double totalFats     = getTotalFatsForDate(userId, date);

        DailyNutritionSummaryDTO summary = new DailyNutritionSummaryDTO();
        summary.setUserId(userId);
        summary.setDate(date.toString());
        summary.setTotalCalories(totalCalories);
        summary.setTotalProtein(totalProtein);
        summary.setTotalCarbs(totalCarbs);
        summary.setTotalFats(totalFats);


        return summary;
    }


    @Override
    public DietEntry getDietEntryById(Long id) {
        return dietEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DietEntry not found"));
    }

    @Override
    public List<DietEntry> getAllDietEntriesByUser(Long userId) {
        return dietEntryRepository.findByUserId(userId);
    }

    @Override
    public void deleteDietEntry(Long id) {
        dietEntryRepository.deleteById(id);
    }

    @Override
    public double getTotalCaloriesForDate(Long userId, LocalDate date) {
        return dietEntryRepository.findByUserIdAndConsumedAt(userId, date).stream()
                .mapToDouble(DietEntry::getCaloriesConsumed)
                .sum();
    }

    @Override
    public double getTotalProteinForDate(Long userId, LocalDate date) {
        return dietEntryRepository.findByUserIdAndConsumedAt(userId, date).stream()
                .mapToDouble(DietEntry::getProteinConsumed)
                .sum();
    }

    @Override
    public double getTotalCarbsForDate(Long userId, LocalDate date) {
        return dietEntryRepository.findByUserIdAndConsumedAt(userId, date).stream()
                .mapToDouble(DietEntry::getCarbsConsumed)
                .sum();
    }

    @Override
    public double getTotalFatsForDate(Long userId, LocalDate date) {
        return dietEntryRepository.findByUserIdAndConsumedAt(userId, date).stream()
                .mapToDouble(DietEntry::getFatsConsumed)
                .sum();
    }

}
