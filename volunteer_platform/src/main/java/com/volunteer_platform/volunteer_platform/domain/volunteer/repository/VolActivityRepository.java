package com.volunteer_platform.volunteer_platform.domain.volunteer.repository;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolActivityRepository extends JpaRepository<VolActivity, Long> {
}
