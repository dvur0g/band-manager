package ru.bruh.bandmanager.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bruh.bandmanager.model.Band;
import ru.bruh.bandmanager.model.HitParade;

import java.util.Optional;

@Repository
public interface HitParadeRepository extends JpaRepository<HitParade, Long> {

    @Query(value = "SELECT COALESCE(MAX(position), 0) + 1 AS position FROM hit_parade", nativeQuery = true)
    Long getLastPosition();

    Optional<HitParade> findByPosition(Long position);

    HitParade findByBand(Band band);

    @Query(value = "UPDATE hit_parade SET position = position + 1 WHERE position >= (:newPosition) AND position < (:oldPosition)", nativeQuery = true)
    @Modifying
    void movePositionUp(@Param("newPosition") Long newPosition, @Param("oldPosition") Long oldPosition);

    @Query(value = "UPDATE hit_parade SET position = position - 1 WHERE position > (:oldPosition) AND position <= (:newPosition)", nativeQuery = true)
    @Modifying
    void movePositionDown(@Param("newPosition") Long newPosition, @Param("oldPosition") Long oldPosition);
}
