package com.volunteer_platform.volunteer_platform.domain.volunteer.controller;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolActivityListDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.Form;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolOrgan;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolActivityService;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolActivityTimeService;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolOrganService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class VolController {

    private final VolActivityService volActivityService;
    private final VolActivityTimeService volActivityTimeService;
    private final VolOrganService volOrganService;

    @PostMapping("/vol/create")
    public String createVol(@RequestBody Form form) {
        VolOrgan volOrgan = volOrganService.createVolOrgan(form.getVolOrganForm());
        VolActivity volActivity = volActivityService.createVolActivity(form.getVolActivityForm(), volOrgan);
        volActivityTimeService.createVolActivityTime(form.getVolActivityTimeForms(), volActivity);

        return "redirect:/";
    }

    @GetMapping(value = "/vol", params = "organ")
    public List<VolActivityListDto> findActivityByOrgan(@RequestParam("organ") Long organId) {
        return volActivityService.findActivitiesByOrgan(organId).stream()
                .map(VolActivityListDto::of).collect(Collectors.toList());
    }
}