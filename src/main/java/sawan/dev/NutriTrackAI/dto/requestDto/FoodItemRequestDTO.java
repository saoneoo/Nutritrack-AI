package sawan.dev.NutriTrackAI.dto.requestDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemRequestDTO {
    private String name;   // Example: "Banana"
   // private double grams;  // optional, e.g. 150g
}
