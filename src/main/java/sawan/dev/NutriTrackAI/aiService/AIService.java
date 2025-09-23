package sawan.dev.NutriTrackAI.aiService;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import sawan.dev.NutriTrackAI.entity.FoodItem;
import sawan.dev.NutriTrackAI.entity.User;
import sawan.dev.NutriTrackAI.entity.AiPlan;
import sawan.dev.NutriTrackAI.repository.AiPlanRepository;
import sawan.dev.NutriTrackAI.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AIService {

    private final ChatClient chatClient;
    private final AiPlanRepository aiPlanRepository;
    private final UserRepository userRepository;

    public AIService(ChatClient.Builder chatClientBuilder,
                     AiPlanRepository aiPlanRepository,
                     UserRepository userRepository) {
        this.chatClient = chatClientBuilder.build();
        this.aiPlanRepository = aiPlanRepository;
        this.userRepository = userRepository;
    }

    // Free-form advice (unchanged)
    public String generateNutritionAdvice(String query) {
        String prompt = "You are a helpful fitness and nutrition assistant. " +
                "User asked: " + query + ". Provide a practical and friendly response.";
        return chatClient.prompt().user(prompt).call().content();
    }

    // Generate a personalized AI plan and save it for the user
    public AiPlan generatePersonalAdvice(Long userId) {
        // 1. Find the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // 2. Build AI prompt
        String name = (user.getName() == null || user.getName().isBlank())
                ? "the user"
                : user.getName().trim();

        String prompt = """
                You are a friendly fitness & nutrition coach.
                Create a simple, actionable 7-day plan for %s.
                Keep it practical: 3 short bullet points per day (meals, movement, and one habit tip).
                Avoid medical claims. Keep the tone positive and motivating.
                """.formatted(name);

        // 3. Call AI
        String aiResponse = chatClient.prompt().user(prompt).call().content();

        // 4. Save AiPlan linked to the User
        AiPlan aiPlan = AiPlan.builder()
                .user(user) // mapping here ✅
                .planContent(aiResponse)
                .createdAt(LocalDateTime.now().toString())
                .build();

        return aiPlanRepository.save(aiPlan);
    }

    public FoodItem fetchNutritionFromAI(String foodName) {
        String prompt = String.format("""
        Give nutrition values for 100 grams of %s.
        Format response strictly as: calories, protein, carbs, fats
        Example: 89, 1.1, 23, 0.3
        """, foodName);

        String response = chatClient.prompt().user(prompt).call().content();

        try {
            String[] parts = response.split(",");
            double calories = Double.parseDouble(parts[0].trim());
            double protein = Double.parseDouble(parts[1].trim());
            double carbs = Double.parseDouble(parts[2].trim());
            double fats = Double.parseDouble(parts[3].trim());

            return FoodItem.builder()
                    .name(foodName.toLowerCase())
                    .caloriesPer100g(calories)
                    .protein(protein)
                    .carbs(carbs)
                    .fats(fats)
                    .source("API")
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI nutrition response: " + response);
        }
    }

    // ==============================
    // NEW: Fetch Workout Calories 🔥
    // ==============================
    public double getCaloriesBurnPerMinute(String workoutName, String category) {
        String prompt = String.format("""
                Estimate the average calories burned per minute for the exercise: %s.
                Category: %s.
                Provide ONLY a number (integer or decimal).
                Example: 7.5
                """, workoutName, category);

        String response = chatClient.prompt().user(prompt).call().content();

        try {
            return Double.parseDouble(response.trim());
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI workout response: " + response);
        }
    }


    public String generateDailyFeedback(String username, LocalDate date,
                                        int goal, double consumed, double burned, double net) {

        String prompt = String.format("""
        You are a friendly fitness coach.
        For user %s on %s:
        - Goal: %d kcal
        - Consumed: %.0f kcal
        - Burned: %.0f kcal
        - Net: %.0f kcal

        Provide:
        1) A short 1-2 sentence assessment.
        2) One practical suggestion for tomorrow to better meet the goal.

        Keep it friendly, non-medical, and short.
        """, username, date, goal, consumed, burned, net);

        return chatClient.prompt().user(prompt).call().content();
    }



}
