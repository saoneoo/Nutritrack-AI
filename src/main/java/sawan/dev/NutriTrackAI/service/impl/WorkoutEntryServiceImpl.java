package sawan.dev.NutriTrackAI.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sawan.dev.NutriTrackAI.dto.requestDto.WorkoutEntryRequestDTO;
import sawan.dev.NutriTrackAI.dto.responseDto.WorkoutEntryResponseDTO;
import sawan.dev.NutriTrackAI.entity.User;
import sawan.dev.NutriTrackAI.entity.Workout;
import sawan.dev.NutriTrackAI.entity.WorkoutEntry;
import sawan.dev.NutriTrackAI.repository.WorkoutEntryRepository;
import sawan.dev.NutriTrackAI.repository.WorkoutRepository;
import sawan.dev.NutriTrackAI.aiService.AIService;
import sawan.dev.NutriTrackAI.service.WorkoutEntryService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutEntryServiceImpl implements WorkoutEntryService {

    private final WorkoutEntryRepository workoutEntryRepository;
    private final WorkoutRepository workoutRepository;
    private final AIService aiService;

    // ✅ Add new workout entry (auto-create Workout if not exists)
    @Override
    @Transactional
    public WorkoutEntryResponseDTO addWorkoutEntry(WorkoutEntryRequestDTO requestDTO, User user) {

        // 1. Check if workout exists, otherwise fetch from AI
        Workout workout = workoutRepository.findByName(requestDTO.getWorkoutName())
                .orElseGet(() -> {
                    double caloriesPerMinute = aiService.getCaloriesBurnPerMinute(
                            requestDTO.getWorkoutName(),
                            requestDTO.getCategory()
                    );

                    Workout newWorkout = new Workout();
                    newWorkout.setName(requestDTO.getWorkoutName());
                    newWorkout.setCategory(requestDTO.getCategory());
                    newWorkout.setCaloriesPerMinute(caloriesPerMinute);

                    return workoutRepository.save(newWorkout);
                });

        // 2. Create WorkoutEntry
        WorkoutEntry entry = new WorkoutEntry();
        entry.setUser(user);
        entry.setWorkout(workout);

        entry.setSets(requestDTO.getSets());
        entry.setReps(requestDTO.getReps());
        entry.setWeightUsed(requestDTO.getWeightUsed());
        entry.setDurationMinutes(requestDTO.getDurationInMinutes()); // 🔹 cardio/flex
        entry.setDate(requestDTO.getDate() != null ? requestDTO.getDate() : LocalDate.now());

        WorkoutEntry savedEntry = workoutEntryRepository.save(entry);

        // 3. Map to Response DTO
        return mapToResponseDTO(savedEntry, user);
    }

    // ✅ Get all workout entries for a user
    @Override
    public List<WorkoutEntry> getWorkoutEntriesByUser(Long userId) {
        return workoutEntryRepository.findByUserId(userId);
    }

    // ✅ Get entries by user + date
    @Override
    public List<WorkoutEntry> getWorkoutEntriesByUserAndDate(Long userId, LocalDate entryDate) {
        return workoutEntryRepository.findByUserIdAndDate(userId, entryDate);
    }

    // ✅ Get entries by user + workout
    @Override
    public List<WorkoutEntry> getWorkoutEntriesByUserAndWorkout(Long userId, Long workoutId) {
        return workoutEntryRepository.findByUserIdAndWorkoutId(userId, workoutId);
    }

    @Override
    public List<WorkoutEntry> getWorkoutEntriesByUserAndDate(Long userId, LocalDateTime start, LocalDateTime end) {
        return List.of(); // implement later if needed
    }

    // ✅ Update workout entry
    @Override
    public WorkoutEntry updateWorkoutEntry(Long entryId, WorkoutEntry updatedEntry) {
        return workoutEntryRepository.findById(entryId)
                .map(existingEntry -> {
                    existingEntry.setSets(updatedEntry.getSets());
                    existingEntry.setReps(updatedEntry.getReps());
                    existingEntry.setWeightUsed(updatedEntry.getWeightUsed());
                    existingEntry.setDurationMinutes(updatedEntry.getDurationMinutes());
                    existingEntry.setDate(updatedEntry.getDate());
                    return workoutEntryRepository.save(existingEntry);
                })
                .orElseThrow(() -> new RuntimeException("Workout entry not found with id " + entryId));
    }

    // ✅ Delete workout entry
    @Override
    public void deleteWorkoutEntry(Long entryId) {
        workoutEntryRepository.deleteById(entryId);
    }

    // 🔹 Utility Mapper
    private WorkoutEntryResponseDTO mapToResponseDTO(WorkoutEntry entry, User user) {
        WorkoutEntryResponseDTO dto = new WorkoutEntryResponseDTO();
        dto.setId(entry.getId());
        dto.setUserId(user.getId());
        dto.setUserName(user.getName());

        // from Workout entity
        dto.setWorkoutName(entry.getWorkout().getName());
        dto.setCategory(entry.getWorkout().getCategory());
        dto.setCaloriesPerMinute(entry.getWorkout().getCaloriesPerMinute());

        // from WorkoutEntry entity
        dto.setSets(entry.getSets());
        dto.setReps(entry.getReps());
        dto.setWeightUsed(entry.getWeightUsed());
        dto.setDate(entry.getDate());

        return dto;
    }
}
