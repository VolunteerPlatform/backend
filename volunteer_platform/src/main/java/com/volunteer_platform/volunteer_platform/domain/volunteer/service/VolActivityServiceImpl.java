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

import java.time.LocalDate;
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
                .activityPeriod(
                        Period.builder()
                                .begin(LocalDate.parse(form.getActivityBegin()))
                                .end(LocalDate.parse(form.getActivityEnd()))
                                .build()
                )
                .activityRecruitPeriod(
                        Period.builder()
                                .begin(LocalDate.parse(form.getRecruitBegin()))
                                .end(LocalDate.parse(form.getRecruitEnd()))
                                .build()
                )
                .volOrgan(volOrgan)
                .numOfRecruit(form.getNumOfRecruit())
                .build();

        isValidDate(volActivity.getActivityPeriod(), volActivity.getActivityRecruitPeriod());
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

    private void isValidDate(Period activityPeriod, Period recruitPeriod) {
        if (activityPeriod.getBegin().compareTo(activityPeriod.getEnd()) > 0) {
            throw new IllegalArgumentException("활동 시작일은 활동 종료일 이후일 수 없습니다.");
        }

        if (recruitPeriod.getBegin().compareTo(recruitPeriod.getEnd()) > 0) {
            throw new IllegalArgumentException("모집 시작일은 모집 종료일 이후일 수 없습니다.");
        }
    }
}
