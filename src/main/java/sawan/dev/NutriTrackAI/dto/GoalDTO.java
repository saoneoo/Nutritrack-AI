package sawan.dev.NutriTrackAI.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sawan.dev.NutriTrackAI.entity.enums.GoalType;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoalDTO {
    private Long id;
    private GoalType type;
    private double targetWeight;
    private int targetCalories;
    private int targetWorkouts;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long userId;  // Instead of embedding full User, we just keep its id
}
