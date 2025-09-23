package sawan.dev.NutriTrackAI.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sawan.dev.NutriTrackAI.dto.requestDto.WorkoutEntryRequestDTO;
import sawan.dev.NutriTrackAI.dto.responseDto.WorkoutEntryResponseDTO;
import sawan.dev.NutriTrackAI.entity.User;
import sawan.dev.NutriTrackAI.entity.WorkoutEntry;
import sawan.dev.NutriTrackAI.repository.UserRepository;
import sawan.dev.NutriTrackAI.service.WorkoutEntryService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/workout-entries")
public class  WorkoutEntryController {

    private final WorkoutEntryService workoutEntryService;
    private final UserRepository userRepository;

    // ✅ 1. Create new workout entry
    @PostMapping
    public ResponseEntity<WorkoutEntryResponseDTO> createWorkoutEntry(
            @RequestBody WorkoutEntryRequestDTO requestDTO) {

        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + requestDTO.getUserId()));

        WorkoutEntryResponseDTO savedEntry = workoutEntryService.addWorkoutEntry(requestDTO, user);

        return ResponseEntity.ok(savedEntry); // service already returns DTO
    }

    // ✅ 2. Get all workout entries for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WorkoutEntryResponseDTO>> getWorkoutEntriesByUser(@PathVariable Long userId) {
        List<WorkoutEntry> entries = workoutEntryService.getWorkoutEntriesByUser(userId);
        return ResponseEntity.ok(entries.stream()
                .map(this::toResponseDTO)
                .toList());
    }

    // ✅ 3. Get workout entries by user and date
    @GetMapping("/user/{userId}/date/{date}")
    public ResponseEntity<List<WorkoutEntryResponseDTO>> getWorkoutEntriesByUserAndDate(
            @PathVariable Long userId, @PathVariable String date) {
        List<WorkoutEntry> entries = workoutEntryService
                .getWorkoutEntriesByUserAndDate(userId, LocalDate.parse(date));
        return ResponseEntity.ok(entries.stream()
                .map(this::toResponseDTO)
                .toList());
    }

    // ✅ 4. Delete workout entry
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkoutEntry(@PathVariable Long id) {
        workoutEntryService.deleteWorkoutEntry(id);
        return ResponseEntity.ok("Workout entry deleted successfully with id: " + id);
    }

    // 🔄 Utility to map entity → responseDTO
    private WorkoutEntryResponseDTO toResponseDTO(WorkoutEntry entry) {
        WorkoutEntryResponseDTO dto = new WorkoutEntryResponseDTO();
        dto.setId(entry.getId());

        dto.setUserId(entry.getUser().getId());
        dto.setUserName(entry.getUser().getName());

        dto.setWorkoutName(entry.getWorkout().getName());
        dto.setCategory(entry.getWorkout().getCategory());
        dto.setCaloriesPerMinute(entry.getWorkout().getCaloriesPerMinute());

        dto.setSets(entry.getSets());
        dto.setReps(entry.getReps());
        dto.setWeightUsed(entry.getWeightUsed());
        dto.setDate(entry.getDate());

        return dto;
    }
}
