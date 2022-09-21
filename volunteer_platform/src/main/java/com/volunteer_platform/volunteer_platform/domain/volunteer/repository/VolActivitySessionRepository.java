package com.volunteer_platform.volunteer_platform.domain.volunteer.repository;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivitySession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VolActivitySessionRepository extends JpaRepository<VolActivitySession, Long> {

    @Modifying
    @Query("update VolActivitySession v set v.numOfApplicant = v.numOfApplicant + 1 where v.id = :sessionId")
    void increaseNumOfApplicant(@Param("sessionId") Long sessionId);

    @Modifying
    @Query("update VolActivitySession v set v.numOfApplicant = v.numOfApplicant - 1 where v.id = :sessionId")
    void decreaseNumOfApplicant(@Param("sessionId") Long sessionId);

    List<VolActivitySession> findAllByVolActivityId(Long activityId);
}
