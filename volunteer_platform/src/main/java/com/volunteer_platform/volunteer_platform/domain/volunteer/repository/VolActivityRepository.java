package com.volunteer_platform.volunteer_platform.domain.volunteer.repository;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VolActivityRepository extends JpaRepository<VolActivity, Long> {

    @Query("select m from VolActivity m join fetch m.volOrgan where m.volOrgan.id = :organId")
    List<VolActivity> findByVolOrgan(@Param("organId") Long organId);

    @Query("select m from VolActivity m join fetch m.volOrgan where m.id = :activityId")
    Optional<VolActivity> findByIdWithOrgan(@Param("activityId") Long activityId);
}
