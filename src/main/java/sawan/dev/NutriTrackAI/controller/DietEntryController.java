package sawan.dev.NutriTrackAI.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import sawan.dev.NutriTrackAI.dto.DailyNutritionSummaryDTO;
import sawan.dev.NutriTrackAI.dto.requestDto.DietEntryRequestDTO;
import sawan.dev.NutriTrackAI.dto.responseDto.DietEntryResponseDTO;
import sawan.dev.NutriTrackAI.entity.DietEntry;
import sawan.dev.NutriTrackAI.entity.FoodItem;
import sawan.dev.NutriTrackAI.entity.User;
import sawan.dev.NutriTrackAI.repository.DietEntryRepository;
import sawan.dev.NutriTrackAI.repository.FoodItemRepository;
import sawan.dev.NutriTrackAI.repository.UserRepository;
import sawan.dev.NutriTrackAI.service.DietEntryService;
import sawan.dev.NutriTrackAI.service.FoodItemService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/diet-entries")
@RequiredArgsConstructor
public class DietEntryController {

    private final DietEntryRepository dietEntryRepository;
    private final UserRepository userRepository;
    private final FoodItemRepository foodItemRepository;
    private final ModelMapper modelMapper;
    private  final FoodItemService foodItemService;
    private final DietEntryService dietEntryService;

    // ✅ Add new DietEntry
    @PostMapping
    public DietEntryResponseDTO addDietEntry(@RequestBody DietEntryRequestDTO requestDTO) {
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        FoodItem foodItem;

        if (requestDTO.getFoodItemId() != null) {
            // Case 1: Use existing food item by ID
            foodItem = foodItemRepository.findById(requestDTO.getFoodItemId())
                    .orElseThrow(() -> new RuntimeException("Food item not found"));
        } else if (requestDTO.getFoodName() != null && !requestDTO.getFoodName().isBlank()) {
            // Case 2: Get or create food item by name (AI-powered)
            foodItem = foodItemService.getOrCreateFoodItem(requestDTO.getFoodName());
        } else {
            throw new RuntimeException("Either foodItemId or foodName must be provided");
        }

        // Now create the DietEntry
        DietEntry entry = new DietEntry();
        entry.setUser(user);
        entry.setFoodItem(foodItem);
        entry.setQuantityInGrams(requestDTO.getQuantity());
        entry.setConsumedAt(requestDTO.getConsumedAt() != null
                ? requestDTO.getConsumedAt()
                : java.time.LocalDate.now());

        // Calculate nutrition (scaled by grams)
        entry.setCaloriesConsumed((foodItem.getCaloriesPer100g() / 100.0) * requestDTO.getQuantity());
        entry.setProteinConsumed((foodItem.getProtein() / 100.0) * requestDTO.getQuantity());
        entry.setCarbsConsumed((foodItem.getCarbs() / 100.0) * requestDTO.getQuantity());
        entry.setFatsConsumed((foodItem.getFats() / 100.0) * requestDTO.getQuantity());

        DietEntry savedEntry = dietEntryRepository.save(entry);

        return convertToResponseDTO(savedEntry);
    }

    // ✅ Get all entries for a user
    @GetMapping("/user/{userId}")
    public List<DietEntryResponseDTO> getUserDietEntries(@PathVariable Long userId) {
        List<DietEntry> entries = dietEntryRepository.findByUserId(userId);
        return entries.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // 🔄 Helper: Entity → ResponseDTO (with calorie calculation)
    private DietEntryResponseDTO convertToResponseDTO(DietEntry dietEntry) {
        DietEntryResponseDTO dto = modelMapper.map(dietEntry, DietEntryResponseDTO.class);

        dto.setFoodName(dietEntry.getFoodItem().getName());

        // Calculate calories: (caloriesPer100g * quantity / 100)
        double totalCalories = dietEntry.getFoodItem().getCaloriesPer100g()
                * dietEntry.getQuantityInGrams() / 100.0;

        dto.setTotalCalories(totalCalories);

        return dto;
    }

    @GetMapping("/summary/{userId}")
    public DailyNutritionSummaryDTO getDailySummary(
            @PathVariable Long userId,
            @RequestParam(required = false) String date) {

        LocalDate targetDate = (date == null) ? LocalDate.now() : LocalDate.parse(date);

        return dietEntryService.getDailySummary(userId, targetDate);
    }

}
