package sawan.dev.NutriTrackAI.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "workouts")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;          // Example: Running, Push-ups, Cycling
    private String category;      // Example: Cardio, Strength, Flexibility
    private double caloriesPerMinute; // Avg calories burned per minute


}
