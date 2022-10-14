package com.volunteer_platform.volunteer_platform.domain.timetable.service.tableinterface;

import com.volunteer_platform.volunteer_platform.domain.timetable.dto.TimeTableElementDto;
import com.volunteer_platform.volunteer_platform.domain.timetable.form.TimeTableForm;

import java.util.List;

public interface TimeTableService {

    void createTimeTable(TimeTableForm form, Long memberId);

    List<TimeTableElementDto> findMemberTimeTable(Long id);
}
