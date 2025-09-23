package sawan.dev.NutriTrackAI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sawan.dev.NutriTrackAI.entity.Workout;

import java.util.List;
import java.util.Optional;


@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    Optional<Workout> findByNameIgnoreCase(String name);

  //  Optional<Workout> findByWorkoutName(String workoutName);

    Optional<Workout> findByName(String workoutName);

    // Find workout by body part (e.g., Chest, Legs, Back)
   //// List<Workout> findByBodyPart(String bodyPart);

    // Find workout by difficulty
    //List<Workout> findByDifficulty(String difficulty);

    // Search workout by name
   // List<Workout> findByNameContainingIgnoreCase(String keyword);
}