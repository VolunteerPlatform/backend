package com.volunteer_platform.volunteer_platform.domain.volunteer.repository;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.AppHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolAppRepository extends JpaRepository<AppHistory, Long> {
}
