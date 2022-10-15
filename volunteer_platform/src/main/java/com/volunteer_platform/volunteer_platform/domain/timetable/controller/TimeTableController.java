package com.volunteer_platform.volunteer_platform.domain.timetable.controller;

import com.volunteer_platform.volunteer_platform.config.jwt.JwtTokenService;
import com.volunteer_platform.volunteer_platform.domain.timetable.dto.TimeTableElementDto;
import com.volunteer_platform.volunteer_platform.domain.timetable.form.TimeTableForm;
import com.volunteer_platform.volunteer_platform.domain.timetable.service.tableinterface.TimeTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse.DTOResponse;
import static com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse.MessageResponse;

@RestController
@RequestMapping("/members/timetable")
@RequiredArgsConstructor
public class TimeTableController {
    private final TimeTableService timeTableService;
    private final JwtTokenService jwtTokenService;

    // 시간표 등록
    @PostMapping
    public MessageResponse createTimeTable(@RequestBody TimeTableForm form, HttpServletRequest request) {
        Long memberId = jwtTokenService.tokenToUserId(request);

        timeTableService.createTimeTable(form, memberId);
        return MessageResponse.defaultOkayResponse();
    }

    // 시간표 GET
    @GetMapping
    public DTOResponse<List<TimeTableElementDto>> getMemberTimeTable(HttpServletRequest request) {
        Long memberId = jwtTokenService.tokenToUserId(request);

        return new DTOResponse<>(timeTableService.findMemberTimeTable(memberId));
    }

}
