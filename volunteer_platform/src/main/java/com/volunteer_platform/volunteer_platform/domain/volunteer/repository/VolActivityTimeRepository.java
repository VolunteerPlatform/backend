package com.volunteer_platform.volunteer_platform.domain.volunteer.repository;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivityTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolActivityTimeRepository extends JpaRepository<VolActivityTime, Long> {
}
