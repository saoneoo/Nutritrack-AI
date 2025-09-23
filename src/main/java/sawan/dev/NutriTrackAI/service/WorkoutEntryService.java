package sawan.dev.NutriTrackAI.service;

import sawan.dev.NutriTrackAI.dto.requestDto.WorkoutEntryRequestDTO;
import sawan.dev.NutriTrackAI.dto.responseDto.WorkoutEntryResponseDTO;
import sawan.dev.NutriTrackAI.entity.User;
import sawan.dev.NutriTrackAI.entity.WorkoutEntry;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface WorkoutEntryService {

    // ✅ Create / Add new workout entry
    WorkoutEntryResponseDTO addWorkoutEntry(WorkoutEntryRequestDTO requestDTO, User user);

    // ✅ Get all workout entries for a specific user
    List<WorkoutEntry> getWorkoutEntriesByUser(Long userId);

    // ✅ Get all workout entries for a specific user on a given date
    List<WorkoutEntry> getWorkoutEntriesByUserAndDate(Long userId, LocalDate entryDate);

    // ✅ Get workout entries for a user filtered by workout type
    List<WorkoutEntry> getWorkoutEntriesByUserAndWorkout(Long userId, Long workoutId);

    List<WorkoutEntry> getWorkoutEntriesByUserAndDate(Long userId, LocalDateTime start, LocalDateTime end);

    // ✅ Update an existing workout entry
    WorkoutEntry updateWorkoutEntry(Long entryId, WorkoutEntry updatedEntry);

    // ✅ Delete a workout entry
    void deleteWorkoutEntry(Long entryId);
}