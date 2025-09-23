package sawan.dev.NutriTrackAI.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import sawan.dev.NutriTrackAI.entity.Goal;
import sawan.dev.NutriTrackAI.entity.User;
import sawan.dev.NutriTrackAI.repository.GoalRepository;
import sawan.dev.NutriTrackAI.repository.UserRepository;
import sawan.dev.NutriTrackAI.service.GoalService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    public GoalServiceImpl(GoalRepository goalRepository, UserRepository userRepository) {
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Goal createGoal(Goal goal, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        goal.setUser(user);
        return goalRepository.save(goal);
    }

    @Override
    public Optional<Goal> getGoalById(Long id) {
        return goalRepository.findById(id);
    }

    @Override
    public List<Goal> getGoalsByUser(Long userId) {
        return goalRepository.findByUserId(userId);
    }

    @Override
    public Goal updateGoal(Long id, Goal goalDetails) {
        Goal existingGoal = goalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal not found with id: " + id));

        existingGoal.setType(goalDetails.getType());
        existingGoal.setTargetWeight(goalDetails.getTargetWeight());
        existingGoal.setTargetCalories(goalDetails.getTargetCalories());
        existingGoal.setTargetWorkouts(goalDetails.getTargetWorkouts());
        existingGoal.setStartDate(goalDetails.getStartDate());
        existingGoal.setEndDate(goalDetails.getEndDate());

        return goalRepository.save(existingGoal);
    }

    @Override
    public void deleteGoal(Long id) {
        if (!goalRepository.existsById(id)) {
            throw new RuntimeException("Goal not found with id: " + id);
        }
        goalRepository.deleteById(id);
    }
}