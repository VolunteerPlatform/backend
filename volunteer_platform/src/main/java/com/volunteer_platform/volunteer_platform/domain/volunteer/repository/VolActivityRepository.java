package com.volunteer_platform.volunteer_platform.domain.volunteer.repository;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VolActivityRepository extends JpaRepository<VolActivity, Long> {

    @Query("select m from VolActivity m join fetch m.volOrgan where m.volOrgan.id = ?1")
    List<VolActivity> findByVolOrgan(Long organId);
}
