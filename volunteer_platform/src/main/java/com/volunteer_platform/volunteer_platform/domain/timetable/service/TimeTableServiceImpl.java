package com.volunteer_platform.volunteer_platform.domain.timetable.service;

import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.repository.MemberRepository;
import com.volunteer_platform.volunteer_platform.domain.timetable.dto.TimeTableElementDto;
import com.volunteer_platform.volunteer_platform.domain.timetable.form.TimeTableElementForm;
import com.volunteer_platform.volunteer_platform.domain.timetable.form.TimeTableForm;
import com.volunteer_platform.volunteer_platform.domain.timetable.models.TimeTable;
import com.volunteer_platform.volunteer_platform.domain.timetable.repository.TimeTableRepository;
import com.volunteer_platform.volunteer_platform.domain.timetable.service.tableinterface.TimeTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeTableServiceImpl implements TimeTableService {

    private final TimeTableRepository timeTableRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void createTimeTable(TimeTableForm timeTableForm, Long memberId) {
        Member member = findMemberByMemberId(memberId);
        timeTableRepository.resetTimetableByMemberId(memberId);

        ArrayList<TimeTable> timeTableList = new ArrayList<>();
        for (TimeTableElementForm timeTableElementForm : timeTableForm.getElements()) {
            TimeTable timeTable = TimeTable.builder()
                    .endTime(timeTableElementForm.getEndTime())
                    .startTime(timeTableElementForm.getStartTime())
                    .dayOfWeek(timeTableElementForm.getDayOfWeek())
                    .member(member)
                    .build();

            timeTableList.add(timeTable);
        }

        timeTableRepository.saveAll(timeTableList);
    }


    /**
     * memberId를 통해 시간표 불러오기
     *
     * @param memberId
     * @return
     */
    @Override
    public List<TimeTableElementDto> findMemberTimeTable(Long memberId) {
        return timeTableRepository.findTimetableByMemberId(memberId)
                .stream()
                .map(TimeTableElementDto::of)
                .collect(Collectors.toList());
    }

    private Member findMemberByMemberId(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원입니다.")
        );
    }
}
