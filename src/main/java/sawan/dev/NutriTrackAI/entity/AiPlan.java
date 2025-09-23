package sawan.dev.NutriTrackAI.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "ai_plans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Many plans can belong to one User
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Lob
    private String planContent;

    private String createdAt; // optional, for tracking
}
