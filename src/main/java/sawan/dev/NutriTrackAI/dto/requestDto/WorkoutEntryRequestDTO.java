package sawan.dev.NutriTrackAI.dto.requestDto;


import lombok.Data;
import java.time.LocalDate;

@Data
public class WorkoutEntryRequestDTO {

    private Long userId;          // ID of the user
    private String workoutName;   // e.g., "Running" (if workout doesn’t exist, AI will create it)
    private String category;      // e.g., "Cardio", "Strength"

    private int sets;
    private int reps;
    private double weightUsed;    // in kg
    private LocalDate date;       // when workout was done
    private int durationInMinutes;  // ✅ new field
}
