package sawan.dev.NutriTrackAI.dto.responseDto;
// UserResponseDTO.java


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private int age;
    private double height;
    private double weight;
    private int dailyCalorieGoal = 2000; // ✅ default
}
