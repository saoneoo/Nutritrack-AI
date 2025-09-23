package sawan.dev.NutriTrackAI.dto.responseDto;


import lombok.Data;
import java.time.LocalDate;

@Data
public class WorkoutEntryResponseDTO {

    private Long id;

    private Long userId;
    private String userName;   // extracted from User

    private String workoutName;     // from Workout entity
    private String category;        // from Workout entity
    private double caloriesPerMinute; // from Workout entity

    private int sets;
    private int reps;
    private double weightUsed;
    private int durationInMinutes;  // ✅ new field

    private LocalDate date;
}
