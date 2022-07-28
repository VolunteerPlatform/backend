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
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VolActivityTimeServiceImpl implements VolActivityTimeService {

    private final VolActivityTimeRepository volActivityTimeRepository;

    @Override
    public void createVolActivityTime(List<VolActivityTimeForm> volActivityTimeForms, VolActivity volActivity) {
        EnumMap<DayOfWeek, List<VolActivityTimeForm>> mapOfActivityTimes = getMapOfActivityTimes(volActivityTimeForms);
        addDayOfWeek(volActivityTimeForms, volActivity);

        List<VolActivityTime> volActivityTimes = new ArrayList<>();
        LocalDate startDate = volActivity.getActivityPeriod().getBegin();
        LocalDate oneDayAfterEndDate = volActivity.getActivityPeriod().getEnd().plusDays(1);

        startDate.datesUntil(oneDayAfterEndDate).forEach(
                date -> {
                    List<VolActivityTimeForm> timeForms = mapOfActivityTimes.get(date.getDayOfWeek());

                    if (!timeForms.isEmpty()) {
                        for (VolActivityTimeForm timeForm : timeForms) {
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
                    }
                });

        volActivityTimeRepository.saveAll(volActivityTimes);
    }

    private EnumMap<DayOfWeek, List<VolActivityTimeForm>> getMapOfActivityTimes(List<VolActivityTimeForm> volActivityTimeForms) {
        EnumMap<DayOfWeek, List<VolActivityTimeForm>> dayOfWeekMap = new EnumMap<>(DayOfWeek.class);
        for (DayOfWeek value : DayOfWeek.values()) {
            dayOfWeekMap.put(value, new ArrayList<>());
        }

        for (VolActivityTimeForm timeForm : volActivityTimeForms) {
            dayOfWeekMap.get(timeForm.getActivityWeek())
                    .add(VolActivityTimeForm
                            .builder()
                            .activityWeek(timeForm.getActivityWeek())
                            .startTime(timeForm.getStartTime())
                            .endTime(timeForm.getEndTime())
                            .build());
        }

        return dayOfWeekMap;
    }

    private void addDayOfWeek(List<VolActivityTimeForm> volActivityTimeForms, VolActivity volActivity) {
        volActivityTimeForms.forEach(element -> volActivity.getDayOfWeeks()
                .add(VolActivityDayOfWeek.builder()
                        .activityWeek(element.getActivityWeek())
                        .startTime(element.getStartTime())
                        .endTime(element.getEndTime())
                        .volActivity(volActivity)
                        .build()));

        volActivity.getDayOfWeeks().sort(Comparator.comparing(VolActivityDayOfWeek::getActivityWeek));
    }
}
