package com.volunteer_platform.volunteer_platform.controller;

import com.volunteer_platform.volunteer_platform.controller.form.Form;
import com.volunteer_platform.volunteer_platform.entity.register.VolActivity;
import com.volunteer_platform.volunteer_platform.entity.register.VolOrgan;
import com.volunteer_platform.volunteer_platform.service.vol.VolActivityServiceImpl;
import com.volunteer_platform.volunteer_platform.service.vol.VolActivityTimeServiceImpl;
import com.volunteer_platform.volunteer_platform.service.vol.VolOrganServiceImpl;
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