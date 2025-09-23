package sawan.dev.NutriTrackAI.dto.responseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutResponseDTO {
    private Long id;
    private String name;
    private String category;
    private double caloriesPerMinute;
}