package sawan.dev.NutriTrackAI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sawan.dev.NutriTrackAI.entity.AiPlan;
import java.util.List;

public interface AiPlanRepository extends JpaRepository<AiPlan, Long> {
    List<AiPlan> findByUserId(Long userId);
}
