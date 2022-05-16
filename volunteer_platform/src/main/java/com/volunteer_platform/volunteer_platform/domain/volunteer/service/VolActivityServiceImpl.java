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
import org.springframework.util.StringUtils;

import java.time.DateTimeException;
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
                                .begin(toLocalDate(form.getVolActivityForm().getActivityBegin()))
                                .end(toLocalDate(form.getVolActivityForm().getActivityEnd()))
                                .build()
                )
                .activityRecruitPeriod(
                        Period.builder()
                                .begin(toLocalDate(form.getVolActivityForm().getRecruitBegin()))
                                .end(toLocalDate(form.getVolActivityForm().getRecruitEnd()))
                                .build()
                )
                .volOrgan(volOrgan)
                .build();

        saveVolActivity(volActivity);
        return volActivity;
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
}
