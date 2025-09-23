package sawan.dev.NutriTrackAI.dto.requestDto;
// UserRequestDTO.java


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private String name;
    private String email;
    private  String password;
    private int age;
    private double height;
    private double weight;
    private int dailyCalorieGoal = 2000; // ✅ default

}
