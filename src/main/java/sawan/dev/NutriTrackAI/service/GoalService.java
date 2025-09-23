package sawan.dev.NutriTrackAI.service;


import sawan.dev.NutriTrackAI.entity.Goal;

import java.util.List;
import java.util.Optional;

public interface GoalService {

    Goal createGoal(Goal goal, Long userId);

    Optional<Goal> getGoalById(Long id);

    List<Goal> getGoalsByUser(Long userId);

    Goal updateGoal(Long id, Goal goalDetails);

    void deleteGoal(Long id);
}