package sawan.dev.NutriTrackAI.dto.requestDto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DietEntryRequestDTO {
    private Long userId;         // Reference to User
    private Long foodItemId;     // If client already knows FoodItem exists
    private String foodName;     // If new food, AI will calculate nutrition
    private double quantity;     // In grams
    private LocalDate consumedAt; // When food was consumed
}
