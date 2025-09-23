package sawan.dev.NutriTrackAI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sawan.dev.NutriTrackAI.dto.GoalDTO;
import sawan.dev.NutriTrackAI.entity.Goal;
import sawan.dev.NutriTrackAI.entity.User;
import sawan.dev.NutriTrackAI.repository.GoalRepository;
import sawan.dev.NutriTrackAI.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Convert Entity → DTO
    private GoalDTO mapToDTO(Goal goal) {
        return new GoalDTO(
                goal.getId(),
                goal.getType(),
                goal.getTargetWeight(),
                goal.getTargetCalories(),
                goal.getTargetWorkouts(),
                goal.getStartDate(),
                goal.getEndDate(),
                goal.getUser() != null ? goal.getUser().getId() : null
        );
    }

    // ✅ Convert DTO → Entity
    private Goal mapToEntity(GoalDTO dto) {
        Goal goal = new Goal();
        goal.setId(dto.getId());
        goal.setType(dto.getType());
        goal.setTargetWeight(dto.getTargetWeight());
        goal.setTargetCalories(dto.getTargetCalories());
        goal.setTargetWorkouts(dto.getTargetWorkouts());
        goal.setStartDate(dto.getStartDate());
        goal.setEndDate(dto.getEndDate());

        if (dto.getUserId() != null) {
            Optional<User> user = userRepository.findById(dto.getUserId());
            user.ifPresent(goal::setUser);
        }

        return goal;
    }

    // ✅ Create Goal
    @PostMapping
    public ResponseEntity<GoalDTO> createGoal(@RequestBody GoalDTO goalDTO) {
        Goal saved = goalRepository.save(mapToEntity(goalDTO));
        return ResponseEntity.ok(mapToDTO(saved));
    }

    // ✅ Get All Goals
    @GetMapping
    public List<GoalDTO> getAllGoals() {
        return goalRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Get Goal by ID
    @GetMapping("/{id}")
    public ResponseEntity<GoalDTO> getGoalById(@PathVariable Long id) {
        return goalRepository.findById(id)
                .map(goal -> ResponseEntity.ok(mapToDTO(goal)))
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Update Goal
    @PutMapping("/{id}")
    public ResponseEntity<GoalDTO> updateGoal(@PathVariable Long id, @RequestBody GoalDTO goalDTO) {
        return goalRepository.findById(id)
                .map(existingGoal -> {
                    Goal updated = mapToEntity(goalDTO);
                    updated.setId(id);
                    Goal saved = goalRepository.save(updated);
                    return ResponseEntity.ok(mapToDTO(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Delete Goal
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGoal(@PathVariable Long id) {
        return goalRepository.findById(id)
                .map(goal -> {
                    goalRepository.delete(goal);
                    return ResponseEntity.noContent().build();  // 204 No Content
                })
                .orElse(ResponseEntity.notFound().build());     // 404 Not Found
    }

}
