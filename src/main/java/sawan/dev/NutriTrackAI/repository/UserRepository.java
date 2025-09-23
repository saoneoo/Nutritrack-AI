package sawan.dev.NutriTrackAI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sawan.dev.NutriTrackAI.entity.Notification;
import sawan.dev.NutriTrackAI.entity.User;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    // Custom queries

    // Find user by email (useful for login/authentication)
   // Optional<User> findByEmail(String email);

    // Check if a user exists with given email (for registration validation)
   // boolean existsByEmail(String email);

    // UserRepository, NotificationRepository, AiPlanRepository already exist
    Optional<User> findById(Long id);
    Notification save(Notification n);

}