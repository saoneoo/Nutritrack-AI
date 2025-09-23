package sawan.dev.NutriTrackAI.dto.responseDto;
import lombok.Data;
import java.util.List;

@Data
public class AIPlanResponseDTO {
    private Long userId;
    private List<String> dailyPlans; // list of 7 strings (one per day)
}
