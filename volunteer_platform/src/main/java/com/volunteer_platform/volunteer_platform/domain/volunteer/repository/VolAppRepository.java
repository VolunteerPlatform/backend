package com.volunteer_platform.volunteer_platform.domain.volunteer.repository;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.AppHistory;
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

    List<AppHistory> findAllByMemberIdAndVolActivitySessionId(Long memberId, Long sessionId);
}
