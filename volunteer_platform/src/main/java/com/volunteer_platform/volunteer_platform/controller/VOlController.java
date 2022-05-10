package com.volunteer_platform.volunteer_platform.controller;

import com.volunteer_platform.volunteer_platform.controller.form.Form;
import com.volunteer_platform.volunteer_platform.entity.register.Period;
import com.volunteer_platform.volunteer_platform.entity.register.VolActivity;
import com.volunteer_platform.volunteer_platform.entity.register.VolActivityTime;
import com.volunteer_platform.volunteer_platform.entity.register.VolOrgan;
import com.volunteer_platform.volunteer_platform.service.vol.VolActivityService;
import com.volunteer_platform.volunteer_platform.service.vol.VolActivityTimeService;
import com.volunteer_platform.volunteer_platform.service.vol.VolOrganService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class VOlController {

    private final VolActivityService volActivityService;
    private final VolActivityTimeService volActivityTimeService;
    private final VolOrganService volOrganService;

    @PostMapping("/create/vol")
    public String createVol(@RequestBody Form form) {

        VolOrgan volOrgan = saveVolOrgan(form);

        VolActivity volActivity = saveVolActivity(form, volOrgan);

        saveVolActivity(form, volActivity);

        return "redirect:/";
    }

    private void saveVolActivity(Form form, VolActivity volActivity) {
        for (int i = 0; i < form.getVolActivityTimeForm().getActivityWeek().size(); i++) {
            VolActivityTime volActivityTime = VolActivityTime.builder()
                    .volActivity(volActivity)
                    .activityWeek(form.getVolActivityTimeForm().getActivityWeek().get(i))
                    .startTime(form.getVolActivityTimeForm().getStartTime())
                    .endTime(form.getVolActivityTimeForm().getEndTime())
                    .build();

            volActivityTimeService.saveVolActivityTime(volActivityTime);
        }
    }

    private VolActivity saveVolActivity(Form form, VolOrgan volOrgan) {
        int[] activityBegin = Arrays.stream(form.getVolActivityForm().getActivityBegin().split("/")).mapToInt(Integer::parseInt).toArray();
        int[] activityEnd = Arrays.stream(form.getVolActivityForm().getActivityEnd().split("/")).mapToInt(Integer::parseInt).toArray();
        int[] recruitBegin = Arrays.stream(form.getVolActivityForm().getRecruitBegin().split("/")).mapToInt(Integer::parseInt).toArray();
        int[] recruitEnd = Arrays.stream(form.getVolActivityForm().getRecruitEnd().split("/")).mapToInt(Integer::parseInt).toArray();

        VolActivity volActivity = VolActivity.builder()
                .activityName(form.getVolActivityForm().getActivityName())
                .activitySummary(form.getVolActivityForm().getActivitySummary())
                .activityContent(form.getVolActivityForm().getActivityContent())
                .activityMethod(form.getVolActivityForm().getActivityMethod())
                .activityType(form.getVolActivityForm().getActivityType())
                .authorizationType(form.getVolActivityForm().getAuthorizationType())
                .category(form.getVolActivityForm().getCategory())
                .activityPeriod(
                        Period.builder()
                                .begin(LocalDate.of(activityBegin[0], activityBegin[1], activityBegin[2]))
                                .end(LocalDate.of(activityEnd[0], activityEnd[1], activityEnd[2]))
                                .build()
                )
                .activityRecruitPeriod(
                        Period.builder()
                                .begin(LocalDate.of(recruitBegin[0], recruitBegin[1], recruitBegin[2]))
                                .end(LocalDate.of(recruitEnd[0], recruitEnd[1], recruitEnd[2]))
                                .build()
                )
                .volOrgan(volOrgan)
                .build();

        volActivityService.saveVolActivity(volActivity);
        return volActivity;
    }

    private VolOrgan saveVolOrgan(Form form) {
        VolOrgan volOrgan = VolOrgan.builder()
                .manager(form.getVolOrganForm().getManager())
                .organPhoneNumber(form.getVolOrganForm().getOrganPhoneNumber())
                .address(form.getVolOrganForm().getAddress())
                .build();

        volOrganService.saveVolOrgan(volOrgan);
        return volOrgan;
    }
}