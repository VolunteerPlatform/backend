package com.volunteer_platform.volunteer_platform.domain.volunteer.repository;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.AppHistory;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.IsAuthorized;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface VolAppRepository extends JpaRepository<AppHistory, Long> {

    @Query("select m from AppHistory m " +
            "join fetch m.volActivityTime v " +
            "join fetch v.volActivity vo " +
            "join fetch vo.volOrgan " +
            "where m.member.id = ?1")
    List<AppHistory> findByMemberId(Long memberId);

    @Query("select m from AppHistory m " +
            "join fetch m.volActivityTime v " +
            "join fetch m.member mm " +
            "join fetch mm.memberInfo " +
            "join fetch mm.member1365Info " +
            "where v.volActivity.id = ?1 and m.isAuthorized = ?2 and v.activityDate = ?3")
    List<AppHistory> findApplicantsByCondition(Long activityId, IsAuthorized isAuthorized, LocalDate activityDate, Pageable pageable);
}
