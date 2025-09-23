package sawan.dev.NutriTrackAI.aiService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import sawan.dev.NutriTrackAI.entity.FoodItem;

@Service
public class NutritionAIService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public NutritionAIService(ChatClient.Builder builder) {
        this.chatClient = builder.build(); // ✅ build ChatClient instance
    }

    public FoodItem fetchNutritionFromAI(String foodName) {
        String prompt = """
            Give me the nutritional values of %s per 100g 
            in JSON format with these fields:
            { "caloriesPer100g": number, "protein": number, "carbs": number, "fats": number }
            Respond ONLY with valid JSON.
            """.formatted(foodName);

        // Ask AI to return JSON mapped directly into FoodItem
        FoodItem aiFoodItem = chatClient.prompt()
                .user(prompt)
                .call()
                .entity(FoodItem.class);  // ✅ Spring AI parses into FoodItem

        aiFoodItem.setName(foodName);
        aiFoodItem.setSource("AI");

        return aiFoodItem;
    }

}

