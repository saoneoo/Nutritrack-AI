package sawan.dev.NutriTrackAI.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "workout_entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;   // ✅ references master workout

    private int sets;          // For strength workouts
    private int reps;          // For strength workouts
    private double weightUsed; // in kg (if applicable)
    private int durationMinutes; // For cardio/flexibility

    private LocalDate date;
}
