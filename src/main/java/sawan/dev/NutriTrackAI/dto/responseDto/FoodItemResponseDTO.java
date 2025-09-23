package sawan.dev.NutriTrackAI.dto.responseDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemResponseDTO {

    private Long id;                 // Auto-generated ID
    private String name;
    private double caloriesPer100g;
    private double protein;
    private double carbs;
    private double fats;
    private String source;
}
