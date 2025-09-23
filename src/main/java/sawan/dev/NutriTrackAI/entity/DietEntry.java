package sawan.dev.NutriTrackAI.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "diet_entries")
public class DietEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relationship with User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Relationship with FoodItem
    @ManyToOne
    @JoinColumn(name = "food_item_id", nullable = false)
    private FoodItem foodItem;

    private double quantityInGrams;  // how much user consumed

    // ✅ Nutrition values per entry (calculated & stored)
    private double caloriesConsumed;
    private double proteinConsumed;
    private double carbsConsumed;
    private double fatsConsumed;

    // ✅ Add consumedAt field
    @Column(name = "consumed_at", nullable = false)
    private LocalDate consumedAt;





}
