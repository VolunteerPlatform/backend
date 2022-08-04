package com.volunteer_platform.volunteer_platform.domain.volunteer.repository;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.AppHistory;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.IsAuthorized;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VolAppRepository extends JpaRepository<AppHistory, Long> {

    @Query("select m from AppHistory m " +
            "join fetch m.volActivitySession v " +
            "join fetch v.volActivity vo " +
            "join fetch vo.volOrgan " +
            "where m.member.id = :memberId")
    List<AppHistory> findByMemberId(@Param("memberId") Long memberId);

    @Query("select m from AppHistory m " +
            "join fetch m.volActivitySession v join fetch m.member mm join fetch mm.memberInfo join fetch mm.member1365Info " +
            "where m.volActivitySession.id = :sessionId and m.isAuthorized = :isAuthorized")
    List<AppHistory> findApplicantsByCondition(@Param("sessionId") Long activityTimeId,
                                               @Param("isAuthorized") IsAuthorized isAuthorized,
                                               Pageable pageable);

    boolean existsByMemberIdAndVolActivitySessionId(Long memberId, Long sessionId);
}
