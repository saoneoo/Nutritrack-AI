package sawan.dev.NutriTrackAI.service;

import sawan.dev.NutriTrackAI.entity.Workout;

import java.util.List;
import java.util.Optional;

public interface WorkoutService {
    Workout findOrCreateWorkout(String name, String category);
    Workout createWorkout(Workout workout);
    Optional<Workout> getWorkoutById(Long id);
    List<Workout> getAllWorkouts();
    Workout updateWorkout(Long id, Workout updatedWorkout);
    void deleteWorkout(Long id);
}