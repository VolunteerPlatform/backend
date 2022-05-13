package com.volunteer_platform.volunteer_platform.domain.volunteer.controller;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.Form;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolOrgan;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.VolActivityServiceImpl;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.VolActivityTimeServiceImpl;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.VolOrganServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VOlController {

    private final VolActivityServiceImpl volActivityService;
    private final VolActivityTimeServiceImpl volActivityTimeService;
    private final VolOrganServiceImpl volOrganService;

    @PostMapping("/create/vol")
    public String createVol(@RequestBody Form form) {

        VolOrgan volOrgan = volOrganService.createVolOrgan(form);

        VolActivity volActivity = volActivityService.createVolActivity(form, volOrgan);

        volActivityTimeService.createVolActivityTime(form, volActivity);

        return "redirect:/";
    }
}