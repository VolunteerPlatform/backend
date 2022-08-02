package com.volunteer_platform.volunteer_platform.domain.volunteer.controller;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolActivityDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.ActivityForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolOrgan;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolActivityService;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolOrganService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vol/activities")
@RequiredArgsConstructor
public class VolActController {

    private final VolActivityService volActivityService;
    private final VolOrganService volOrganService;

    @PostMapping
    public VolActivityDto createActivity(@RequestBody ActivityForm activityForm) {
        VolOrgan volOrgan = volOrganService.findOrgan(activityForm.getOrganizationId());
        VolActivity volActivity = volActivityService.createVolActivity(activityForm, volOrgan);

        return VolActivityDto.of(volActivity);
    }

    @GetMapping("/{activityId}")
    public VolActivityDto findActivity(@PathVariable Long activityId) {
        return VolActivityDto.of(volActivityService.findActivityById(activityId));
    }
}