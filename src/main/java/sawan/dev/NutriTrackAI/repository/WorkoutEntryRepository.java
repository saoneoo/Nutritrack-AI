package sawan.dev.NutriTrackAI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sawan.dev.NutriTrackAI.entity.User;
import sawan.dev.NutriTrackAI.entity.WorkoutEntry;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface WorkoutEntryRepository extends JpaRepository<WorkoutEntry, Long> {

    // Fetch all workouts for a user on a specific date
   // List<WorkoutEntry> findByUserAndDate(User user, LocalDate date);

    // Fetch workouts between two dates
   // List<WorkoutEntry> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);

    // Count total sets done by a user on a given date
   // long countByUserAndDate(User user, LocalDate date);

    // Fetch by userId in a date range
   // List<WorkoutEntry> findByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end);

    // Fetch all by userId
    List<WorkoutEntry> findByUserId(Long userId);

    List<WorkoutEntry>   findByUserIdAndDate(Long userId,  LocalDate Date);

    List<WorkoutEntry>   findByUserIdAndWorkoutId(Long userId, Long workoutId);
   // List<WorkoutEntry>   findByUserIdAndDateTimeBetween( Long userId,LocalDate start,LocalDate end);
}
