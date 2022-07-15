package com.volunteer_platform.volunteer_platform.domain.volunteer.service;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.VolActivityTimeForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivityDayOfWeek;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivityTime;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.ActivityTimeStatus;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolActivityTimeRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolActivityTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VolActivityTimeServiceImpl implements VolActivityTimeService {

    private final VolActivityTimeRepository volActivityTimeRepository;

    @Override
    @Transactional
    public void createVolActivityTime(List<VolActivityTimeForm> volActivityTimeForms, VolActivity volActivity) {
        HashMap<DayOfWeek, VolActivityTimeForm> activityTimeMap = new HashMap<>();
        List<VolActivityTime> volActivityTimes = new ArrayList<>();

        volActivityTimeForms.forEach(form -> {
            if (activityTimeMap.get(form.getActivityWeek()) != null) {
                throw new IllegalArgumentException("요일 당 하나의 시간만 추가 가능합니다.");
            }

            activityTimeMap.put(form.getActivityWeek(), form);
        });

        activityTimeMap.forEach((key, value) -> {
            volActivity.getDayOfWeeks().add(VolActivityDayOfWeek.builder()
                    .activityWeek(key)
                    .startTime(value.getStartTime())
                    .endTime(value.getEndTime())
                    .volActivity(volActivity)
                    .build());
        });

        for (LocalDate date = volActivity.getActivityPeriod().getBegin();
             date.isBefore(volActivity.getActivityPeriod().getEnd().plusDays(1));
             date = date.plusDays(1)) {

            VolActivityTimeForm timeForm = activityTimeMap.get(date.getDayOfWeek());

            if (timeForm == null) {
                continue;
            }

            VolActivityTime activityTime = VolActivityTime.builder()
                    .volActivity(volActivity)
                    .activityWeek(date.getDayOfWeek())
                    .startTime(timeForm.getStartTime())
                    .endTime(timeForm.getEndTime())
                    .activityDate(date)
                    .numOfRecruit(volActivity.getNumOfRecruit())
                    .numOfApplicant(0)
                    .timeStatus(ActivityTimeStatus.RECRUITING)
                    .build();

            volActivityTimes.add(activityTime);
        }

        volActivityTimeRepository.saveAll(volActivityTimes);
    }
}
