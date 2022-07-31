package com.volunteer_platform.volunteer_platform.domain.timetable.service.tableinterface;

import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.timetable.form.Form;
import com.volunteer_platform.volunteer_platform.domain.timetable.form.TimeTableForm;
import com.volunteer_platform.volunteer_platform.domain.timetable.models.TimeTable;

import java.util.List;

public interface TimeTableService {

    void createTimeTable(Form form, Member memberId);

    List<TimeTable> findMemberTimeTable(Long id);
}