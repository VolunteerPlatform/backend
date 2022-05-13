package com.volunteer_platform.volunteer_platform.domain.volunteer.service;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.Form;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.Period;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolOrgan;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolActivityRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VolActivityServiceImpl implements VolActivityService {

    private final VolActivityRepository volActivityRepository;

    @Transactional
    public void saveVolActivity(VolActivity volActivity) {

        volActivityRepository.save(volActivity);
    }

    @Override
    @Transactional
    public VolActivity createVolActivity(Form form, VolOrgan volOrgan) {
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

        saveVolActivity(volActivity);
        return volActivity;
    }
}
