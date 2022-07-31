package com.volunteer_platform.volunteer_platform.domain.volunteer.service;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.VolActivityForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.Period;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolOrgan;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolActivityRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public VolActivity createVolActivity(VolActivityForm form, VolOrgan volOrgan) {
        VolActivity volActivity = VolActivity.builder()
                .activityName(form.getActivityName())
                .activitySummary(form.getActivitySummary())
                .activityContent(form.getActivityContent())
                .activityMethod(form.getActivityMethod())
                .activityType(form.getActivityType())
                .authorizationType(form.getAuthorizationType())
                .category(form.getCategory())
                .activityPeriod(new Period(form.getActivityBegin(), form.getActivityEnd()))
                .activityRecruitPeriod(new Period(form.getRecruitBegin(), form.getRecruitEnd()))
                .volOrgan(volOrgan)
                .numOfRecruit(form.getNumOfRecruit())
                .build();

        saveVolActivity(volActivity);
        return volActivity;
    }

    public List<VolActivity> findActivitiesByOrgan(Long organId) {
        return volActivityRepository.findByVolOrgan(organId);
    }

    public VolActivity findActivityById(Long activityId) {
        return volActivityRepository.findByIdWithOrgan(activityId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 활동 ID 입니다."));
    }
}
