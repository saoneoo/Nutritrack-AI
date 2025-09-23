package sawan.dev.NutriTrackAI.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sawan.dev.NutriTrackAI.aiService.NutritionAIService;
import sawan.dev.NutriTrackAI.entity.FoodItem;
import sawan.dev.NutriTrackAI.repository.FoodItemRepository;
import sawan.dev.NutriTrackAI.service.FoodItemService;

@Service
@RequiredArgsConstructor
public class FoodItemServiceImpl implements FoodItemService {

    private final FoodItemRepository foodItemRepository;
    private final NutritionAIService nutritionAIService;
    // 👆 this is your AI client that fetches nutrition info

    @Override
    public FoodItem getOrCreateFoodItem(String foodName) {
        return foodItemRepository.findByNameIgnoreCase(foodName)
                .map(existing -> {
                    System.out.println("✅ Found in DB: " + existing.getName());
                    return existing;
                })
                .orElseGet(() -> {
                    System.out.println("🤖 Fetching from AI: " + foodName);
                    FoodItem aiFoodItem = nutritionAIService.fetchNutritionFromAI(foodName);
                    aiFoodItem.setSource("AI");
                    return foodItemRepository.save(aiFoodItem);
                });
    }


    @Override
    public FoodItem getFoodItemById(Long id) {
        return foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food item not found"));
    }
}
