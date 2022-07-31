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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeTableServiceImpl implements TimeTableService {

    private final TimeTableRepository timeTableRepository;

    @Override
    @Transactional
    public void createTimeTable(Form form, Member memberId) {

        ArrayList<TimeTable> timeTableList = new ArrayList<>();

        for (TableForm tableForm : form.getTimeTableForm().getTableForm()) {
            TimeTable timeTable = TimeTable.builder()
                    .endTime(tableForm.getEndTime())
                    .startTime(tableForm.getStartTime())
                    .dayOfWeek(tableForm.getDayOfWeek())
                    .member(memberId)
                    .build();

            timeTableList.add(timeTable);
        }

        timeTableRepository.saveAll(timeTableList);
    }


    /**
     * memberId를 통해 시간표 불러오기
     * @param memberId
     * @return
     */
    @Override
    public List<TimeTable> findMemberTimeTable(Long memberId) {
        return timeTableRepository.findTimetableByMemberId(memberId);
    }
}
