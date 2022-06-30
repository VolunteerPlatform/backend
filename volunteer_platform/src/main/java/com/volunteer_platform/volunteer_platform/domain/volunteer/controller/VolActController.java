package com.volunteer_platform.volunteer_platform.domain.volunteer.controller;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolActivityDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.Form;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolOrgan;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolActivityService;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolActivityTimeService;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolOrganService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vol/act")
@RequiredArgsConstructor
public class VolActController {

    private final VolActivityService volActivityService;
    private final VolActivityTimeService volActivityTimeService;
    private final VolOrganService volOrganService;

    @PostMapping
    public VolActivityDto createVol(@RequestBody Form form) {
        VolOrgan volOrgan = volOrganService.createVolOrgan(form.getVolOrganForm());
        VolActivity volActivity = volActivityService.createVolActivity(form.getVolActivityForm(), volOrgan);
        volActivityTimeService.createVolActivityTime(form.getVolActivityTimeForms(), volActivity);

        return VolActivityDto.of(volActivity);
    }

    @GetMapping("/{actId}")
    public VolActivityDto findVol(@PathVariable Long actId) {
        return VolActivityDto.of(volActivityService.findActivityById(actId));
    }
}