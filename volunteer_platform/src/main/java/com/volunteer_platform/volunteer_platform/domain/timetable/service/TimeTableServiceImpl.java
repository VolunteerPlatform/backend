package com.volunteer_platform.volunteer_platform.domain.timetable.service;

import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.timetable.form.Form;
import com.volunteer_platform.volunteer_platform.domain.timetable.form.TableForm;
import com.volunteer_platform.volunteer_platform.domain.timetable.models.TimeTable;
import com.volunteer_platform.volunteer_platform.domain.timetable.repository.TimeTableRepository;
import com.volunteer_platform.volunteer_platform.domain.timetable.service.tableinterface.TimeTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TimeTableServiceImpl implements TimeTableService {

    private final TimeTableRepository timeTableRepository;

    @Override
    @Transactional
    public void createTimeTable(Form form, Member memberId) {
        for (int i = 0; i < form.getTimeTableForm().getTableForm().size(); i++) {
            TableForm tableForm = form.getTimeTableForm().getTableForm().get(i);

            TimeTable timeTable = TimeTable.builder()
                    .endTime(tableForm.getEndTime())
                    .startTime(tableForm.getStartTime())
                    .dayOfWeek(tableForm.getDayOfWeek())
                    .member(memberId)
                    .build();

            timeTableRepository.save(timeTable);
        }
    }
}
