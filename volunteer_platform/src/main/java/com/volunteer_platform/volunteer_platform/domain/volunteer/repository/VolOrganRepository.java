package com.volunteer_platform.volunteer_platform.domain.volunteer.repository;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolOrgan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VolOrganRepository extends JpaRepository<VolOrgan, Long> {

    List<VolOrgan> findVolOrganByMemberId(Long memberId);
}
