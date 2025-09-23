package sawan.dev.NutriTrackAI.dto.requestDto;

import lombok.Data;

@Data
public class AIPlanRequestDTO {
    private Long userId;
    private String goal;          // e.g., "weight loss", "muscle gain"
    private String dietaryPreference; // e.g., "vegetarian", "vegan", "keto"
}
