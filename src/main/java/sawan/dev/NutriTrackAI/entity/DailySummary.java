package sawan.dev.NutriTrackAI.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="daily_summaries")
public class DailySummary {

    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private LocalDate date;
    private double consumed;
    private double burned;
    private double net;
    private double remaining;
    private int goal;
    private String aiFeedback;
    private LocalDateTime createdAt;
}