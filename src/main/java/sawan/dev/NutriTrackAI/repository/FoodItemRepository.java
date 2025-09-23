package sawan.dev.NutriTrackAI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sawan.dev.NutriTrackAI.entity.FoodItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

    // Find food item by name (exact, ignoring case)
    Optional<FoodItem> findByNameIgnoreCase(String name);

    // Find food item by name pattern (for search/autocomplete feature)
    List<FoodItem> findByNameContainingIgnoreCase(String name);

    // Find all food items under certain calories
    List<FoodItem> findByCaloriesPer100gLessThan(Double calories);

    // 🔥 Optional: Search by protein threshold (handy for fitness goals)
    // List<FoodItem> findByProteinGreaterThan(Double protein);

    // 🔥 Optional: Search by macros range
    List<FoodItem> findByProteinGreaterThanAndCarbsLessThan(Double protein, Double carbs);
}
