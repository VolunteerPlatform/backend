package com.volunteer_platform.volunteer_platform.domain.timetable.controller;

import com.volunteer_platform.volunteer_platform.config.jwt.JwtTokenService;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.service.MemberServiceImpl;
import com.volunteer_platform.volunteer_platform.domain.timetable.converter.Converter;
import com.volunteer_platform.volunteer_platform.domain.timetable.dto.TimeTableDto;
import com.volunteer_platform.volunteer_platform.domain.timetable.form.Form;
import com.volunteer_platform.volunteer_platform.domain.timetable.models.TimeTable;
import com.volunteer_platform.volunteer_platform.domain.timetable.service.tableinterface.TimeTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TimeTableController {

    private final MemberServiceImpl memberService;
    private final TimeTableService timeTableService;
    private final JwtTokenService jwtTokenService;

    // 시간표 등록
    @PostMapping("/member/timetable")
    public void createTimeTable(@RequestBody Form form, HttpServletRequest request) {

        Long memberId = jwtTokenService.tokenToUserId(request);

        timeTableService.createTimeTable(form, memberId);
    }

    // 시간표 GET
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/member/timetable")
    public Converter.TimeTable getMemberTimeTable(HttpServletRequest request) {

        Long memberId = jwtTokenService.tokenToUserId(request);

        List<TimeTable> timeTable = timeTableService.findMemberTimeTable(memberId);

        List<TimeTableDto> collect = timeTable.stream()
                .map(t -> new TimeTableDto(t.getDayOfWeek(), t.getStartTime(), t.getEndTime()))
                .collect(Collectors.toList());

        return new Converter.TimeTable(collect);
    }

}
