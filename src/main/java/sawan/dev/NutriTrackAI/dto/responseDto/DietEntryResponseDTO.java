package sawan.dev.NutriTrackAI.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DietEntryResponseDTO {
    private Long id;
    private String foodName;       // Human-readable food name
    private double quantityInGrams;
    private double totalCalories;  // Computed field
    private LocalDateTime consumedAt;
}
