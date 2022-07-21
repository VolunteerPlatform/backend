package com.volunteer_platform.volunteer_platform.domain.timetable.service;

import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.models.MemberInfo;
import com.volunteer_platform.volunteer_platform.domain.member.repository.MemberRepository;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.MemberService;
import com.volunteer_platform.volunteer_platform.domain.timetable.models.TimeTable;
import com.volunteer_platform.volunteer_platform.domain.timetable.repository.TimeTableRepository;
import com.volunteer_platform.volunteer_platform.domain.timetable.service.tableinterface.TimeTableService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TimeTableServiceImplTest {

    @Autowired
    TimeTableService timeTableService;

    @Autowired
    TimeTableRepository timeTableRepository;

    @Autowired
    MemberRepository memberRepository;

    Member member;
    TimeTable timeTable;

    @Test
    @DisplayName("memberId로 TimeTable List 타입으로 받아오는지 조회")
    @Transactional
    public void getTimeTableByMemberId() throws Exception {
        //given
        Member member = Member.builder()
                .id(1L)
                .build();

        memberRepository.save(member);

        ArrayList<TimeTable> timeTables = new ArrayList<>();

        TimeTable timeTable1 = TimeTable.builder()
                .member(member)
                .dayOfWeek(DayOfWeek.MONDAY)
                .startTime(1)
                .endTime(2)
                .build();

        timeTables.add(timeTable1);

        TimeTable timeTable2 = TimeTable.builder()
                .member(member)
                .dayOfWeek(DayOfWeek.WEDNESDAY)
                .startTime(10)
                .endTime(20)
                .build();

        timeTables.add(timeTable2);

        TimeTable timeTable3 = TimeTable.builder()
                .member(member)
                .dayOfWeek(DayOfWeek.FRIDAY)
                .startTime(8)
                .endTime(10)
                .build();

        timeTables.add(timeTable3);

        TimeTable timeTable4 = TimeTable.builder()
                .member(member)
                .dayOfWeek(DayOfWeek.SUNDAY)
                .startTime(5)
                .endTime(15)
                .build();

        timeTables.add(timeTable4);

        timeTableRepository.saveAll(timeTables);

        //when
        List<TimeTable> timeTable = timeTableService.findMemberTimeTable(member.getId());

        //then

        System.out.println("timeTable.getClass() = " + timeTable);

        assertEquals(timeTable.get(0).getStartTime(), 1);
        assertEquals(timeTable.get(0).getEndTime(), 2);
        assertEquals(timeTable.get(0).getDayOfWeek(), DayOfWeek.MONDAY);
        assertEquals(timeTable.get(1).getStartTime(), 10);
        assertEquals(timeTable.get(2).getDayOfWeek(), DayOfWeek.FRIDAY);
        assertEquals(timeTable.get(3).getEndTime(), 15);
     }
}