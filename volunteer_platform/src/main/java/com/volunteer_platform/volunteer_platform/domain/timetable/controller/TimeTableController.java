package com.volunteer_platform.volunteer_platform.domain.timetable.controller;

import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.service.MemberServiceImpl;
import com.volunteer_platform.volunteer_platform.domain.timetable.form.Form;
import com.volunteer_platform.volunteer_platform.domain.timetable.service.tableinterface.TimeTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class TimeTableController {

    private final MemberServiceImpl memberService;
    private final TimeTableService timeTableService;

    @PostMapping("/member/timetable")
    public void createTimeTable(@RequestBody Form form, HttpServletRequest request) {

        Member member = memberService.findMemberId(request);

        timeTableService.createTimeTable(form, member);
    }
}
