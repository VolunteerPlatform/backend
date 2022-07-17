package com.volunteer_platform.volunteer_platform.domain.timetable.repository;

import com.volunteer_platform.volunteer_platform.domain.timetable.models.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {

}
