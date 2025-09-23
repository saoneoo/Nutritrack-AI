package sawan.dev.NutriTrackAI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sawan.dev.NutriTrackAI.entity.Goal;
import sawan.dev.NutriTrackAI.entity.User;
import sawan.dev.NutriTrackAI.entity.enums.GoalType;

import java.util.List;


@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {


    // Find all goals of a user
   // List<Goal> findByUser(User user);

    // Find active goals of a user
   // List<Goal> findByUserAndTargetDateAfter(User user, java.time.LocalDate today);

    // Find goal by type (e.g., WEIGHT_LOSS, WEIGHT_GAIN)
   // List<Goal> findByUserAndGoalType(User user, GoalType goalType);

    List<Goal>findByUserId( Long userId);
}