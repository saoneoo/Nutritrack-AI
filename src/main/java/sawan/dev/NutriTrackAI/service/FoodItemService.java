package sawan.dev.NutriTrackAI.service;

import sawan.dev.NutriTrackAI.entity.FoodItem;

public interface FoodItemService {

    /**
     * Get existing FoodItem by name OR fetch from AI if not present.
     */
    FoodItem getOrCreateFoodItem(String foodName);

    /**
     * Get FoodItem by ID (just a wrapper to repo).
     */
    FoodItem getFoodItemById(Long id);
}
