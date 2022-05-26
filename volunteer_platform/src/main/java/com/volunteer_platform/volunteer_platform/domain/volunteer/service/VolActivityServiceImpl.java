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
import org.springframework.util.StringUtils;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Arrays;
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
                                .begin(toLocalDate(form.getActivityBegin()))
                                .end(toLocalDate(form.getActivityEnd()))
                                .build()
                )
                .activityRecruitPeriod(
                        Period.builder()
                                .begin(toLocalDate(form.getRecruitBegin()))
                                .end(toLocalDate(form.getRecruitEnd()))
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

    private LocalDate toLocalDate(String date) {
        if (!StringUtils.hasText(date) || date.split("/").length != 3) {
            throw new IllegalArgumentException("날짜 형식에 맞지 않습니다. ex) 2022/02/22 와 같은 형식으로 입력해주세요.");
        }

        int[] dateArray = Arrays.stream(date.split("/")).mapToInt(Integer::parseInt).toArray();
        LocalDate result;
        try {
            result = LocalDate.of(dateArray[0], dateArray[1], dateArray[2]);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("올바른 날짜를 입력해주세요.");
        }

        return result;
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
