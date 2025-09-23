package sawan.dev.NutriTrackAI.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Example: "Banana"

    private double caloriesPer100g;  // calories in 100g
    private double protein;          // grams
    private double carbs;            // grams
    private double fats;              // grams

    private String source; // "DB" or "API" → tells us where this record came from
}