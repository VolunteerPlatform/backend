package com.volunteer_platform.volunteer_platform.domain.volunteer.controller;

import com.volunteer_platform.volunteer_platform.config.jwt.JwtTokenService;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.SearchResultDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolActivityDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolActivityIdDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolActivitySessionDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.ActivityForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.ActivityModifyForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse.DTOResponse;
import com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse.MessageResponse;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/vol/activities")
@RequiredArgsConstructor
public class VolActController {

    private final JwtTokenService jwtTokenService;
    private final HttpServletRequest request;

    private final VolActivityService volActivityService;

    @PostMapping
    public DTOResponse<VolActivityIdDto> createActivity(@RequestBody ActivityForm activityForm) {

        return new DTOResponse<>(volActivityService.createVolActivity(activityForm));
    }

    @GetMapping("/{activityId}")
    public DTOResponse<VolActivityDto> findActivity(@PathVariable Long activityId) {

        return new DTOResponse<>(volActivityService.findActivityById(activityId));
    }

    @GetMapping
    public DTOResponse<List<SearchResultDto>> searchActivity(@ModelAttribute SearchCondition searchCondition) {

        return new DTOResponse<>(volActivityService.searchActivity(getMemberId(), searchCondition));
    }

    @DeleteMapping("/{activityId}")
    public MessageResponse deleteActivity(@PathVariable Long activityId) {
        volActivityService.deleteActivity(activityId);

        return MessageResponse.defaultOkayResponse();
    }

    @GetMapping(value = "/{activityId}/sessions")
    public DTOResponse<List<VolActivitySessionDto>> findSessionsOfActivity(@PathVariable Long activityId,
                                                                           @RequestParam(name = "date", required = false)
                                                                           @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                                           LocalDate activityDate) {
        return new DTOResponse<>(volActivityService.findSessionsOfActivity(activityId, activityDate));
    }

    @PutMapping("/{activityId}")
    public MessageResponse editActivity(@PathVariable Long activityId, @RequestBody ActivityModifyForm activityModifyForm) {
        volActivityService.editActivity(activityId, activityModifyForm);
        return MessageResponse.defaultOkayResponse();
    }

    private Long getMemberId() {
        return jwtTokenService.tokenToUserId(request);
    }
}