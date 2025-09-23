package sawan.dev.NutriTrackAI.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sawan.dev.NutriTrackAI.aiService.AIService;
import sawan.dev.NutriTrackAI.entity.Workout;
import sawan.dev.NutriTrackAI.repository.WorkoutRepository;
import sawan.dev.NutriTrackAI.service.WorkoutService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final AIService aiService; // ✅ AI integration for calories/minute

    /**
     * Find a workout by name or create a new one with AI-estimated calories.
     */
    @Override
    public Workout findOrCreateWorkout(String name, String category) {
        return workoutRepository.findByNameIgnoreCase(name)
                .orElseGet(() -> {
                    double caloriesPerMin = aiService.getCaloriesBurnPerMinute(name, category);

                    Workout newWorkout = new Workout();
                    newWorkout.setName(name);
                    newWorkout.setCategory(category);
                    newWorkout.setCaloriesPerMinute(caloriesPerMin);

                    return workoutRepository.save(newWorkout);
                });
    }

    /**
     * Add a new workout manually (without AI).
     */
    @Override
    public Workout createWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    /**
     * Get workout by ID.
     */
    @Override
    public Optional<Workout> getWorkoutById(Long id) {
        return workoutRepository.findById(id);
    }

    /**
     * Get all workouts.
     */
    @Override
    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    /**
     * Update an existing workout by ID.
     */
    @Override
    public Workout updateWorkout(Long id, Workout updatedWorkout) {
        return workoutRepository.findById(id)
                .map(existingWorkout -> {
                    existingWorkout.setName(updatedWorkout.getName());
                    existingWorkout.setCategory(updatedWorkout.getCategory());
                    existingWorkout.setCaloriesPerMinute(updatedWorkout.getCaloriesPerMinute());
                    return workoutRepository.save(existingWorkout);
                })
                .orElseThrow(() -> new RuntimeException("Workout not found with id: " + id));
    }

    /**
     * Delete a workout by ID.
     */
    @Override
    public void deleteWorkout(Long id) {
        if (!workoutRepository.existsById(id)) {
            throw new RuntimeException("Workout not found with id: " + id);
        }
        workoutRepository.deleteById(id);
    }
}
