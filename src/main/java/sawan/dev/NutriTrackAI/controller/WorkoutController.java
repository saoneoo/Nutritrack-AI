package sawan.dev.NutriTrackAI.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sawan.dev.NutriTrackAI.dto.requestDto.WorkoutRequestDTO;
import sawan.dev.NutriTrackAI.dto.responseDto.WorkoutResponseDTO;
import sawan.dev.NutriTrackAI.entity.Workout;
import sawan.dev.NutriTrackAI.service.WorkoutService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;
    private final ModelMapper modelMapper;

    // ✅ Create workout
    @PostMapping
    public ResponseEntity<WorkoutResponseDTO> createWorkout(@RequestBody WorkoutRequestDTO workoutRequestDTO) {
        Workout workout = modelMapper.map(workoutRequestDTO, Workout.class);
        Workout savedWorkout = workoutService.createWorkout(workout);
        return ResponseEntity.ok(modelMapper.map(savedWorkout, WorkoutResponseDTO.class));
    }

    // ✅ Get workout by ID
    @GetMapping("/{id}")
    public ResponseEntity<WorkoutResponseDTO> getWorkoutById(@PathVariable Long id) {
        Workout workout = workoutService.getWorkoutById(id)
                .orElseThrow(() -> new RuntimeException("Workout not found with id " + id));
        return ResponseEntity.ok(modelMapper.map(workout, WorkoutResponseDTO.class));
    }

    // ✅ Get all workouts
    @GetMapping
    public ResponseEntity<List<WorkoutResponseDTO>> getAllWorkouts() {
        List<WorkoutResponseDTO> workouts = workoutService.getAllWorkouts()
                .stream()
                .map(workout -> modelMapper.map(workout, WorkoutResponseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(workouts);
    }

    // ✅ Update workout
    @PutMapping("/{id}")
    public ResponseEntity<WorkoutResponseDTO> updateWorkout(
            @PathVariable Long id,
            @RequestBody WorkoutRequestDTO workoutRequestDTO) {

        Workout updatedWorkout = modelMapper.map(workoutRequestDTO, Workout.class);
        Workout savedWorkout = workoutService.updateWorkout(id, updatedWorkout);

        return ResponseEntity.ok(modelMapper.map(savedWorkout, WorkoutResponseDTO.class));
    }

    // ✅ Delete workout
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }
}
