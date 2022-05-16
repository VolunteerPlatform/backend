package com.volunteer_platform.volunteer_platform.domain.volunteer.controller;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.Form;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolOrgan;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolActivityService;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolActivityTimeService;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolOrganService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VOlController {

    private final VolActivityService volActivityService;
    private final VolActivityTimeService volActivityTimeService;
    private final VolOrganService volOrganService;

    @PostMapping("/create/vol")
    public String createVol(@RequestBody Form form) {

        VolOrgan volOrgan = volOrganService.createVolOrgan(form.getVolOrganForm());
        VolActivity volActivity = volActivityService.createVolActivity(form.getVolActivityForm(), volOrgan);
        volActivityTimeService.createVolActivityTime(form.getVolActivityTimeForm(), volActivity);

        return "redirect:/";
    }
}