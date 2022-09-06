package com.volunteer_platform.volunteer_platform.domain.volunteer.controller;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.SearchResultDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolActivityDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.ActivityForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolOrgan;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolActivityService;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolOrganService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vol/activities")
@RequiredArgsConstructor
public class VolActController {

    private final VolActivityService volActivityService;
    private final VolOrganService volOrganService;

    @PostMapping
    public VolActivityDto createActivity(@RequestBody ActivityForm activityForm) {
        VolOrgan volOrgan = volOrganService.findOrgan(activityForm.getOrganizationId());

        return volActivityService.createVolActivity(activityForm, volOrgan);
    }

    @GetMapping("/{activityId}")
    public VolActivityDto findActivity(@PathVariable Long activityId) {
        return volActivityService.findActivityById(activityId);
    }

    @GetMapping
    public CustomResponse.DTOResponse<List<SearchResultDto>> searchActivity(@ModelAttribute SearchCondition searchCondition) {
        return new CustomResponse.DTOResponse<>(volActivityService.searchActivity(searchCondition));
    }
}