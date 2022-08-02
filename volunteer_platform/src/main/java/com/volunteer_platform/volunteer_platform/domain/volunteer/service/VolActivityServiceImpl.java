package com.volunteer_platform.volunteer_platform.domain.volunteer.service;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.ActivityForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.ActivityTimeForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.*;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.SessionStatus;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolActivityRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolActivitySessionRepository;
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
    private final VolActivitySessionRepository volActivitySessionRepository;

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

        List<ActivityTimeForm> activityTimeForms = activityForm.getTimeList();
        createActivityDayOfWeek(activityTimeForms, volActivity);
        volActivityRepository.save(volActivity);

        List<VolActivitySession> activitySessions = createVolActivitySessions(activityTimeForms, volActivity);
        volActivitySessionRepository.saveAll(activitySessions);

        return volActivity;
    }

    // 활동의 요일과 시작, 종료시간 별도 저장
    private void createActivityDayOfWeek(List<ActivityTimeForm> activityTimeForms, VolActivity volActivity) {
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
    private List<VolActivitySession> createVolActivitySessions(List<ActivityTimeForm> activityTimeForms, VolActivity volActivity) {
        EnumMap<DayOfWeek, List<ActivityTimeForm>> mapOfActivityTimes = getMapOfActivityTimes(activityTimeForms);

        List<VolActivitySession> activitySessions = new ArrayList<>();
        LocalDate startDate = volActivity.getActivityPeriod().getBegin();
        LocalDate oneDayAfterEndDate = volActivity.getActivityPeriod().getEnd().plusDays(1);

        startDate.datesUntil(oneDayAfterEndDate).forEach(
                date -> {
                    List<ActivityTimeForm> timeForms = mapOfActivityTimes.get(date.getDayOfWeek());

                    if (!timeForms.isEmpty()) {
                        for (ActivityTimeForm timeForm : timeForms) {
                            VolActivitySession activitySession = VolActivitySession.builder()
                                    .volActivity(volActivity)
                                    .activityWeek(date.getDayOfWeek())
                                    .startTime(timeForm.getStartTime())
                                    .endTime(timeForm.getEndTime())
                                    .activityDate(date)
                                    .numOfRecruit(volActivity.getNumOfRecruit())
                                    .numOfApplicant(0)
                                    .sessionStatus(SessionStatus.RECRUITING)
                                    .build();

                            activitySessions.add(activitySession);
                        }
                    }
                });

        return activitySessions;
    }

    // 세션 제작을 위한 각 요일별 활동 시간들을 Map 형태로 변환
    private EnumMap<DayOfWeek, List<ActivityTimeForm>> getMapOfActivityTimes(List<ActivityTimeForm> activityTimeForms) {
        EnumMap<DayOfWeek, List<ActivityTimeForm>> dayOfWeekMap = new EnumMap<>(DayOfWeek.class);
        for (DayOfWeek value : DayOfWeek.values()) {
            dayOfWeekMap.put(value, new ArrayList<>());
        }

        for (ActivityTimeForm timeForm : activityTimeForms) {
            dayOfWeekMap.get(timeForm.getActivityWeek())
                    .add(ActivityTimeForm
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
