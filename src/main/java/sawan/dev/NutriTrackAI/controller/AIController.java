package sawan.dev.NutriTrackAI.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sawan.dev.NutriTrackAI.aiService.AIService;
import sawan.dev.NutriTrackAI.dto.responseDto.AIPlanResponseDTO;
import sawan.dev.NutriTrackAI.entity.AiPlan;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    // ✅ Generate a new 7-day plan and save it
    @PostMapping("/plan/{userId}")
    public ResponseEntity<AIPlanResponseDTO> generatePlan(@PathVariable Long userId) {
        // Generate & save plan using AIService
        AiPlan savedPlan = aiService.generatePersonalAdvice(userId);

        // Convert AI text into daily plan list (split by line breaks or "Day")
        List<String> dailyPlans = Arrays.asList(savedPlan.getPlanContent().split("\n"));

        // Prepare response DTO
        AIPlanResponseDTO response = new AIPlanResponseDTO();
        response.setUserId(savedPlan.getUser().getId());
        response.setDailyPlans(dailyPlans);

        return ResponseEntity.ok(response);
    }
}
