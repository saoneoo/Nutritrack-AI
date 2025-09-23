package sawan.dev.NutriTrackAI.dto.requestDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutRequestDTO {
    private String name;
    private String category;
    private double caloriesPerMinute;
}
