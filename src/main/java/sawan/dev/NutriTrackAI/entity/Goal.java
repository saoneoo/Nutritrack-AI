package sawan.dev.NutriTrackAI.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sawan.dev.NutriTrackAI.entity.enums.GoalType;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private GoalType type;   // e.g., WEIGHT_LOSS, WEIGHT_GAIN, MAINTAIN_WEIGHT, CALORIE, WORKOUT

    private double targetWeight;   // in kg (only relevant if type = WEIGHT_LOSS / WEIGHT_GAIN / MAINTAIN_WEIGHT)

    private int targetCalories;    // daily calorie limit (only relevant if type = CALORIE)

    private int targetWorkouts;    // per week (only relevant if type = WORKOUT)

    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    private User user;
}
