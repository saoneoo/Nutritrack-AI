package sawan.dev.NutriTrackAI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sawan.dev.NutriTrackAI.entity.DietEntry;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DietEntryRepository extends JpaRepository<DietEntry, Long> {

    // ✅ Get all diet entries for a user on a specific date
    List<DietEntry> findByUserIdAndConsumedAt(Long userId, LocalDate consumedAt);

    // ✅ Get all diet entries for a user
    List<DietEntry> findByUserId(Long userId);
}
