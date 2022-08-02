package com.volunteer_platform.volunteer_platform.domain.volunteer.repository;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivitySession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface VolActivitySessionRepository extends JpaRepository<VolActivitySession, Long> {
    Optional<VolActivitySession> findByActivityDateAndVolActivityId(LocalDate activityDate, Long activityId);
}
