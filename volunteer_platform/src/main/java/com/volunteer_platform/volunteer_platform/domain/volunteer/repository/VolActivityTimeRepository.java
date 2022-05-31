package com.volunteer_platform.volunteer_platform.domain.volunteer.repository;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivityTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface VolActivityTimeRepository extends JpaRepository<VolActivityTime, Long> {
    Optional<VolActivityTime> findByActivityDateAndVolActivityId(LocalDate activityDate, Long activityId);
}
