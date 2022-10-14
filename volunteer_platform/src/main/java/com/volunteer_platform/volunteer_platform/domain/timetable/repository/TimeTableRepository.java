package com.volunteer_platform.volunteer_platform.domain.timetable.repository;

import com.volunteer_platform.volunteer_platform.domain.timetable.models.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {

    @Query("select t from TimeTable t where t.member.id = :memberId")
    List<TimeTable> findTimetableByMemberId(@Param("memberId") Long memberId);

    @Modifying
    @Query("delete from TimeTable t where t.member.id = :memberId")
    void resetTimetableByMemberId(@Param("memberId") Long memberId);
}
