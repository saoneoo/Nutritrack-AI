package sawan.dev.NutriTrackAI.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private int age;
    private double weight; // in kg
    private double height; // in cm

     @Column(name = "daily_calorie_goal")
    private Integer dailyCalorieGoal;  // nullable, default e.g. 2000



    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DietEntry> dietEntries;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutEntry> workoutEntries;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Goal> goals;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AiPlan> aiPlans ;

}
