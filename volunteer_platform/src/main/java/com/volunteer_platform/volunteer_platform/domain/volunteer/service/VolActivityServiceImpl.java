package com.volunteer_platform.volunteer_platform.domain.volunteer.service;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.ActivityForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.VolActivityTimeForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.*;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.ActivityTimeStatus;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolActivityRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolActivityTimeRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolActivityService;
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
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VolActivityServiceImpl implements VolActivityService {

    private final VolActivityRepository volActivityRepository;
    private final VolActivityTimeRepository volActivityTimeRepository;

    @Override
    @Transactional
    public VolActivity createVolActivity(ActivityForm activityForm, VolOrgan volOrgan) {
        VolActivity volActivity = VolActivity.builder()
                .activityName(activityForm.getActivityName())
                .activitySummary(activityForm.getActivitySummary())
                .activityContent(activityForm.getActivityContent())
                .activityType(activityForm.getActivityType())
                .activityMethod(activityForm.getActivityMethod())
                .authorizationType(activityForm.getAuthorizationType())
                .category(activityForm.getCategory())
                .activityPeriod(new Period(activityForm.getActivityBegin(), activityForm.getActivityEnd()))
                .activityRecruitPeriod(new Period(activityForm.getRecruitBegin(), activityForm.getRecruitEnd()))
                .volOrgan(volOrgan)
                .numOfRecruit(activityForm.getNumOfRecruit())
                .build();

        List<VolActivityTimeForm> volActivityTimeForms = activityForm.getTimeList();
        createActivityDayOfWeek(volActivityTimeForms, volActivity);
        volActivityRepository.save(volActivity);

        List<VolActivityTime> activitySessions = createVolActivitySessions(volActivityTimeForms, volActivity);
        volActivityTimeRepository.saveAll(activitySessions);

        return volActivity;
    }

    // 활동의 요일과 시작, 종료시간 별도 저장
    private void createActivityDayOfWeek(List<VolActivityTimeForm> activityTimeForms, VolActivity volActivity) {
        activityTimeForms.forEach(element -> volActivity.getDayOfWeeks()
                .add(VolActivityDayOfWeek.builder()
                        .activityWeek(element.getActivityWeek())
                        .startTime(element.getStartTime())
                        .endTime(element.getEndTime())
                        .volActivity(volActivity)
                        .build()));

        volActivity.getDayOfWeeks().sort(Comparator.comparing(VolActivityDayOfWeek::getActivityWeek));
    }

    // 봉사활동 세션 만들기
    private List<VolActivityTime> createVolActivitySessions(List<VolActivityTimeForm> activityTimeForms, VolActivity volActivity) {
        EnumMap<DayOfWeek, List<VolActivityTimeForm>> mapOfActivityTimes = getMapOfActivityTimes(activityTimeForms);

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

        return volActivityTimes;
    }

    // 세션 제작을 위한 각 요일별 활동 시간들을 Map 형태로 변환
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

    public List<VolActivity> findActivitiesByOrgan(Long organId) {
        return volActivityRepository.findByVolOrgan(organId);
    }

    public VolActivity findActivityById(Long activityId) {
        return volActivityRepository.findByIdWithOrgan(activityId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 활동 ID 입니다."));
    }
}
